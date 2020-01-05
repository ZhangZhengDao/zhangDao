package cn.zhang.com.service;

import cn.zhang.com.dto.QuestionDTO;
import cn.zhang.com.mapper.Questionmapper;
import cn.zhang.com.mapper.Usermapper;
import cn.zhang.com.model.Question;
import cn.zhang.com.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class QuerstionService {

    @Autowired
    private Usermapper usermapper;
    @Autowired
    private Questionmapper questionmapper;


    public List<QuestionDTO> select(Integer page, Integer size){
        //定义格式
        page=size*(page-1);
        List<User> select = usermapper.select();
        List<Question> select1 = questionmapper.select(page,size);
        List<QuestionDTO> questionDTOS=new ArrayList<QuestionDTO>();
        for (Question question:select1){
        for (User user:select) {
                if(user.getId().equals(question.getCreator())){
                    QuestionDTO questionDTO = new QuestionDTO();
                    BeanUtils.copyProperties(question,questionDTO);
                    questionDTO.setUser(user);
                    questionDTOS.add(questionDTO);
                }
            }
        }
        for (QuestionDTO questionDTO:questionDTOS) {
            System.out.println(questionDTO.toString());
        }
        return questionDTOS;
    }

}
