package cn.zhang.com.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class  PageQuestionDTO<T> {
    private List<T> questionDTOS;
    private List<Integer> yigong;//一共几页



    /*计算一共多少页*/
    public void getfenyeshu(Integer integer,Integer size){
        //拿到给出的总数后取余
        List<Integer> y=new ArrayList<>();
        if (integer%size==0){
            for (int i = 1; i <= integer / size; i++) {
                y.add(i);
            }
        }else{
            for (int i = 1;i <= integer / size + 1; i++) {
                y.add(i);
            }
        }
        this.yigong=y;
    }
}
