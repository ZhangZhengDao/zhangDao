package cn.zhang.com.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhangzheng
 * @date 2020/2/28 15:35
 */
@Data
public class pageDTO {
    private Integer page;
    private Integer size;
    //总页数
    private Integer sum;
    //一共多少条数据
    private Integer count;
    /*
     * 总页数集合
     * */
    private List<Integer> sumList;


    public pageDTO(Integer page, Integer size, Integer count) {
        this.page = page;
        this.size = size;
        this.count = count;
        getSum(count);
    }


    /*
     * 计算出总页数，并赋值
     * */
    private void getSum(Integer count) {
        double ceil = Math.ceil(Double.valueOf(count) / this.size);
        Integer integer = Integer.valueOf((int) ceil);
        List<Integer> list = new ArrayList<>();
        this.sum = (int) ceil;
        int sata = calculate();
        if (integer > sata) {
            if (this.page >= 5) {
                for (int i = 1; i <= sata; i++) {
                    Integer pagedao=i + this.page - 4;
                    if (pagedao>this.sum){
                        continue;
                    }else {
                        list.add(pagedao);
                    }
                }
            } else {
                for (int i = 1; i <= sata; i++) {
                    list.add(i);
                }
            }
            this.sumList = list;
            return;
        }

        for (Integer i = 1; i <= integer; i++) {
            list.add(i);
        }

        this.sumList = list;
    }


    /*
     *
     * 计算出当前应该显示几条数据 默认为4条
     * */
    private int calculate() {
        int initial = 4;
        int i = this.page + 4 - 1;
        if (i > 7) {
            return 7;
        }
        return i;
    }

}
