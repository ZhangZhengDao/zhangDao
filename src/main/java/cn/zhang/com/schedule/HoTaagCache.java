package cn.zhang.com.schedule;

import cn.zhang.com.dto.TagDTO;
import cn.zhang.com.dto.TagsDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
public class HoTaagCache {
    private  Map<String ,Integer> tags=new HashMap<>();
    public List<TagsDTO> paixu(Map<String ,Integer> map){
        List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
        Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
            //升序排序
            @Override
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }

        });
        List<TagsDTO> list1=new ArrayList<>();
        int i= map.size();
        for (int i1 = 0; i1 < i; i1++) {
            TagsDTO tagDTO=new TagsDTO();
            list1.add(tagDTO);
        }
        i=i-1;
        for(Map.Entry<String,Integer> mapping:list){
            TagsDTO tagDTO=new TagsDTO();
            tagDTO.setIs(mapping.getKey());
            tagDTO.setZh(mapping.getValue());
            list1.set(i,tagDTO);
            i--;
        }
        return list1;
    }
}
