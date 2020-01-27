package cn.zhang.com.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NoitPaginationDTO {
    private List<NotFicationAndUserDTO> notFicationAndUserDTOlist;
    private List<Integer> yigong;//一共几页



    /*计算一共多少页*/
    public void getfenyeshu(Integer integer){
        //拿到给出的总数后取余
        List<Integer> y=new ArrayList<>();
        if (integer%5==0){
            for (int i = 1; i <= integer / 5; i++) {
                y.add(i);
            }
        }else{
            for (int i = 1;i <= integer / 5 + 1; i++) {
                y.add(i);
            }
        }

        this.yigong=y;


    }
}
