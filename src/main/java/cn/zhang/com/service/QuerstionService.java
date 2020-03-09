package cn.zhang.com.service;

import cn.zhang.com.dto.*;
import cn.zhang.com.exception.CustomizeErrorCode;
import cn.zhang.com.exception.CustomizeException;
import cn.zhang.com.mapper.QuestionExtMapper;
import cn.zhang.com.mapper.QuestionMapper;
import cn.zhang.com.mapper.UserMapper;
import cn.zhang.com.model.Question;
import cn.zhang.com.model.QuestionExample;
import cn.zhang.com.model.User;
import cn.zhang.com.model.UserExample;
import cn.zhang.com.schedule.HoTaagCache;
import cn.zhang.com.util.QuestionUtil;
import cn.zhang.com.util.UserUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuerstionService {

    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private QuestionMapper questionMapper;
    @Autowired(required = false)
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private QuestionUtil questionUtil;
    @Autowired
    private UserUtil userUtil;
    @Autowired
    private HoTaagCache hoTaagCache;

    public PaginationDTO select(String account, Integer page, Integer size, String sousuo, String redu, String remen) {
        //定义格式
        Integer c = size * (page - 1);
        UserExample userExample = new UserExample();
        userExample.createCriteria();
        List<User> select = userMapper.selectByExample(userExample);
        //查询所有数据
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        //分页
        //得到用户索要的数模糊查询所有有关的问题
        List<Question> select1 = new ArrayList<>();
        select1 = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(c, size));
        Long getzongshu = null;
        if (!sousuo.isEmpty()) {
            if (!StringUtils.equals(sousuo, "false")) {
                String[] sql = sousuo.split(" ");
                //用正则表达之匹配需要转以
                for (int i = 0; i < sql.length; i++) {
                    /** . ? + $ ^ [ ] ( ) { } | \ /*/
                    if (StringUtils.equals(sql[i], "*") ||
                            StringUtils.equals(sql[i], "*") ||
                            StringUtils.equals(sql[i], ".") ||
                            StringUtils.equals(sql[i], "?") ||
                            StringUtils.equals(sql[i], "$") ||
                            StringUtils.equals(sql[i], "+") ||
                            StringUtils.equals(sql[i], "^") ||
                            StringUtils.equals(sql[i], "[") ||
                            StringUtils.equals(sql[i], "]") ||
                            StringUtils.equals(sql[i], "(") ||
                            StringUtils.equals(sql[i], ")") ||
                            StringUtils.equals(sql[i], "{") ||
                            StringUtils.equals(sql[i], "}") ||
                            StringUtils.equals(sql[i], "|") ||
                            StringUtils.equals(sql[i], "\\") ||
                            StringUtils.equals(sql[i], "/")) {
                        sql[i] = "\\" + sql[i];
                    }
                }
                String collect = Arrays.stream(sql).collect(Collectors.joining("|"));
                Question question = new Question();
                question.setTitle(collect);
                getzongshu = questionExtMapper.getzongshu(question);
                select1 = questionExtMapper.getLike(question, new RowBounds(c, size));
                if (!StringUtils.equals(remen, "false")) {
                    question.setTag(remen);
                    select1 = questionExtMapper.getLikeReMenChaXun(question, new RowBounds(c, size));
                }
            }
        }
        /*热门标签查找*/
        if (!redu.isEmpty()) {
            if (!StringUtils.equals(redu, "false")) {
                Question question = new Question();
                question.setTag(redu);
                //重置个数
                getzongshu = questionExtMapper.getzongshuRUMEN(question);
                //重置列表内容
                select1 = questionExtMapper.getLikeREmen(question, new RowBounds(c, size));
            }
        }
        friendDTO friendDTO = new friendDTO();
        List<QuestionDTO> questionDTOS = new ArrayList<QuestionDTO>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : select1) {
            for (User user : select) {
                //判断account是否为空，为空则表示不必根据当前用户查找值
                if (StringUtils.isEmpty(account)) {
                    if (StringUtils.equals(user.getId(), question.getCreator())) {
                        QuestionDTO questionDTO = new QuestionDTO();
                        questionDTO.setQuestion(question);
                        questionDTO.setUser(user);
                        questionDTOS.add(questionDTO);
                    }
                }
            }
        }
        paginationDTO.setQuestionDTOS(questionDTOS);
        Integer size1 = (int) questionMapper.countByExample(new QuestionExample());
        /*判断是否时模糊查询，是的话返回的页数也将不一样*/
        if (!StringUtils.equals(sousuo, "false") && !StringUtils.isEmpty(sousuo)) {
            size1 = Math.toIntExact(getzongshu);
        }
        paginationDTO.setPagination(size1, page, size);
        return paginationDTO;
    }

    public QuestionDTO getquestionDTO(Integer id) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andIdEqualTo(id);
        List<Question> id1 = questionMapper.selectByExampleWithBLOBs(questionExample);
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestion(id1.get(0));
        if (id1.size() == 0) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        //添加阅读数_关系到并发，需要向多方面思考 --update question set view=view+1 where id=
        questionExtMapper.incView(id1.get(0).getId());
        //重新获得增加后的数据
        id1 = questionMapper.selectByExample(questionExample);
        //查询所有用户
        UserExample userExample = new UserExample();
        userExample.createCriteria();
        List<User> select = userMapper.selectByExample(userExample);
        for (User user : select) {
            if (StringUtils.equals(id1.get(0).getCreator(), user.getId())) {
                questionDTO.setUser(user);
                return questionDTO;
            }
        }
        //如果找不到用户则判断情况
        User user = new User();
        user.setName("此用户以注销");
        user.setAvatarUrl(null);
        questionDTO.setUser(user);
        questionDTO.setQuestion(id1.get(0));
        return questionDTO;
    }

    //判断添加还是修改
    public void AddQuestionORUpdate(Question question, User user1) {
        //先判断数据库是否有当前id，有的话就表示要修该
        //等于空代表是添加，否则为修改
        if (question.getId() == null) {
            questionMapper.insert(question);
        } else {
            //向数据库修改
            Question question1 = question;
            question1.setGmtCreate(null);
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int i = questionMapper.updateByExampleSelective(question1, questionExample);
            if (i < 1) {
                new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
            }
        }

    }

    public List<Question> likegetListCommentType2(QuestionDTO questionDTO) {
        //在这里数据库 有一个bug 正则表达式判断c++字符会报错，故转化
        String replace = questionDTO.getQuestion().getTag().replace("c++", "c+");
        System.out.println(replace);
        //分割tag
        String[] tag = replace.split(",");
        String collect = Arrays.stream(tag).collect(Collectors.joining("|"));
        Question question = questionDTO.getQuestion();
        question.setId(questionDTO.getQuestion().getId());
        question.setTag(collect);
        List<Question> selectlike = questionExtMapper.findTag(question);
        return selectlike;
    }


    public QuestionandUserDTO gitUserWenti(User user, Integer page, Integer size) {
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(user.getId());
        List<Question> questions = questionMapper.selectByExample(example);
        QuestionandUserDTO questionandUserDTO = new QuestionandUserDTO();
        questionandUserDTO.setQuestions(questions);
        questionandUserDTO.setUser(user);
        questionandUserDTO.getfenyeshu(questions.size());
        return questionandUserDTO;
    }

    /**
     * @param hot      热门标签
     * @param search   搜索
     * @param property 话题
     * @return 返回分页集合
     * @apiNote zhangzheng
     */
    public PageQuestionDTO friendQuestionAll(Integer page, Integer size, String search, String hot, String property) {
        PageQuestionDTO<QuestionDTO> pageQuestionDTO = new PageQuestionDTO<>();
        pageDTO pageDTO=null;
        List<Question> questionLimit = new ArrayList<>();
        //分类内容
        if (!StringUtils.equals(property, "false")) {
            questionLimit=questionUtil.classify(page,size,property);
            pageDTO=new pageDTO(page,size,questionUtil.TypeCount(property));
        }
        //搜索
        else if (!StringUtils.equals(search, "false")) {
            questionLimit = questionUtil.findQuestionLike(page, size, search);
            Question example = new Question();
            example.setTitle(search);
            pageQuestionDTO.getfenyeshu(Math.toIntExact(questionExtMapper.getzongshu(example)), size);
            pageDTO=new pageDTO(page,size,Math.toIntExact(questionExtMapper.getzongshu(example)));
        }
        //热度标签
        else if (!StringUtils.equals(hot, "false")) {
            questionLimit = questionUtil.findQuestionLabel(page, size, hot);
            Question example = new Question();
            example.setTag(hot);
            pageQuestionDTO.getfenyeshu(Math.toIntExact(questionExtMapper.getzongshuRUMEN(example)), size);
            pageDTO=new pageDTO(page,size,Math.toIntExact(questionExtMapper.getzongshuRUMEN(example)));
        }
        //默认
        else {
            questionLimit = questionUtil.findQuestionLimit(page, size);
            pageQuestionDTO.getfenyeshu(Math.toIntExact(questionExtMapper.count()), size);
            pageDTO=new pageDTO(page,size,Math.toIntExact(questionExtMapper.count()));
        }
        List<QuestionDTO> userAndQuestionAll = userUtil.findUserAndQuestionAll(questionLimit);
        pageQuestionDTO.setPageDTO(pageDTO);
        pageQuestionDTO.setQuestionDTOS(userAndQuestionAll);
        return pageQuestionDTO;
    }
}
