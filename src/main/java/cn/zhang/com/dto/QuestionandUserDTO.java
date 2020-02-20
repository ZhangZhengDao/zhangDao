package cn.zhang.com.dto;

import cn.zhang.com.model.Question;
import cn.zhang.com.model.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionandUserDTO {
    private List<Question> questions;
    private List<Integer> yigong;//一共几页
    private User user;


    /*计算一共多少页*/
    public void getfenyeshu(Integer integer){
        //拿到给出的总数后取余
        List<Integer> y=new ArrayList<>();
        if (integer%10==0){
            for (int i = 1; i <= integer / 10; i++) {
                y.add(i);
            }
        }else{
            for (int i = 1;i <= integer / 10 + 1; i++) {
                y.add(i);
            }
        }

        this.yigong=y;


    }
}
