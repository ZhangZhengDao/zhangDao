package cn.zhang.com.service;

import cn.zhang.com.dto.PaginationDTO;
import cn.zhang.com.dto.QuestionDTO;
import cn.zhang.com.mapper.QuestionMapper;
import cn.zhang.com.mapper.UserMapper;
import cn.zhang.com.model.Question;
import cn.zhang.com.model.QuestionExample;
import cn.zhang.com.model.User;
import cn.zhang.com.model.UserExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
@Service
public class QuerstionService {

    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private QuestionMapper questionMapper;


    public PaginationDTO select(String account, Integer page, Integer size){
        //定义格式
        Integer c=size*(page-1);
        UserExample userExample=new UserExample();
        userExample.createCriteria();//查询数据量
        List<User> select = userMapper.selectByExample(userExample);
        //查询所有数据
        QuestionExample questionExample=new QuestionExample();
        questionExample.createCriteria();
        //分页 ，未完成
        List<Question> select1 = questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds(c,size));
        List<QuestionDTO> questionDTOS=new ArrayList<QuestionDTO>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question:select1){
        for (User user:select) {
                    //判断account是否为空，为空则表示不必根据当前用户查找值
                    if(StringUtils.isEmpty(account)) {
                        QuestionDTO questionDTO=new QuestionDTO();
                        questionDTO.setQuestion(question);
                        questionDTO.setUser(user);
                        questionDTOS.add(questionDTO);
                    }
                    if(!StringUtils.isEmpty(account)) {
                        if (user.getAccount().equals(account)) {
                            QuestionDTO questionDTO=new QuestionDTO();
                            questionDTO.setQuestion(question);
                            questionDTO.setUser(user);
                            questionDTOS.add(questionDTO);
                        }
                    }
            }
        }
        paginationDTO.setQuestionDTOS(questionDTOS);
        Integer size1 = (int) questionMapper.countByExample(new QuestionExample());
        paginationDTO.setPagination(size1,page,size);
        return paginationDTO;
    }
    public QuestionDTO getquestionDTO(Integer id){
        QuestionExample questionExample=new QuestionExample();
        questionExample.createCriteria().andIdEqualTo(id);
        List<Question> id1 = questionMapper.selectByExample(questionExample);
        UserExample userExample=new UserExample();
        userExample.createCriteria();
        List<User> select = userMapper.selectByExample(userExample);
        QuestionDTO questionDTO=new QuestionDTO();
        for (User user:select) {
            if (StringUtils.equals(id1.get(0).getCreator(),user.getId())){
                questionDTO.setUser(user);
                questionDTO.setQuestion(id1.get(0));
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
        if(question.getId()==null){
            questionMapper.insert(question);
        }else{
           //向数据库修改
            Question question1=question;
            question1.setGmtCreate(null);
            QuestionExample questionExample=new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(question1,questionExample);
        }

    }
}
