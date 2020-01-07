package cn.zhang.com.service;

import cn.zhang.com.dto.PaginationDTO;
import cn.zhang.com.dto.QuestionDTO;
import cn.zhang.com.mapper.Questionmapper;
import cn.zhang.com.mapper.Usermapper;
import cn.zhang.com.model.Question;
import cn.zhang.com.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
@Service
public class QuerstionService {

    @Autowired
    private Usermapper usermapper;
    @Autowired
    private Questionmapper questionmapper;


    public PaginationDTO select(String account, Integer page, Integer size){
        //定义格式
        Integer c=size*(page-1);
        List<User> select = usermapper.select();
        List<Question> select1 = questionmapper.select(c,size);
        List<QuestionDTO> questionDTOS=new ArrayList<QuestionDTO>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question:select1){
        for (User user:select) {
                if(StringUtils.equals(user.getId(),question.getCreator())){
                    //判断account是否为空，为空则表示不必根据当前用户查找值
                    if(StringUtils.isEmpty(account)) {
                        QuestionDTO questionDTO = new QuestionDTO();
                        BeanUtils.copyProperties(question, questionDTO);
                        questionDTO.setUser(user);
                        questionDTOS.add(questionDTO);
                    }
                    if(!StringUtils.isEmpty(account)) {
                        if (user.getAccount().equals(account)) {
                            QuestionDTO questionDTO = new QuestionDTO();
                            BeanUtils.copyProperties(question, questionDTO);
                            questionDTO.setUser(user);
                            questionDTOS.add(questionDTO);
                        }
                    }
                }
            }
        }
        paginationDTO.setQuestionDTOS(questionDTOS);
        Integer size1 = questionmapper.contion().size();
        paginationDTO.setPagination(size1,page,size);
        return paginationDTO;
    }
    public QuestionDTO getquestionDTO(Integer id){
        Question id1 = questionmapper.findID(id);
        List<User> select = usermapper.select();
        for (User user:select) {
            if (StringUtils.equals(id1.getCreator(),user.getId())){
                QuestionDTO questionDTO=new QuestionDTO();
                questionDTO.setUser(user);
                BeanUtils.copyProperties(id1,questionDTO);
                return questionDTO;
            }
        }
        return null;
    }

    //判断添加还是修改
    public void AddQuestionORUpdate(Question question, User user1) {
    //先判断数据库是否有当前id，有的话就表示要修该
        //等于空代表是添加，否则为修改
        if(question.getId()==null){
            questionmapper.create(question);
        }else{
           //向数据库修改
            questionmapper.Update(question);
        }

    }
}
