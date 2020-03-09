package cn.zhang.com.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NoitPaginationDTO {
    private List<NotFicationAndUserDTO> notFicationAndUserDTOlist;
    private List<Integer> yigong;//一共几页



    /*计算一共多少页*/
    public void gentleness(Integer integer){
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
