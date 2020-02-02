package cn.zhang.com.schedule;

import cn.zhang.com.dto.TagsDTO;
import cn.zhang.com.mapper.QuestionMapper;
import cn.zhang.com.model.Question;
import cn.zhang.com.model.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
//Sprig 定时器 //查询人们标签
@Controller
@Slf4j
public class HotTagTasks {
    //每隔2秒调用一次此方法
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private HoTaagCache hoTaagCache;

//    @Scheduled(fixedRate = 1000*60*60)
@Scheduled(fixedRate = 1000*60*60)
public void reportCurrentTime() {
        int offset=0;
        int limit=20;
        log.info("reportCurrentTime start{}",new Date());
        List<Question> list=new ArrayList<>();
        Map<String, Integer> tags =new HashMap<>();
        while (offset==0||list.size()==limit){
            list=questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,limit));
            for (Question quesion:
                 list) {
                //先先拿到所有标签
                String[] split = StringUtils.split(quesion.getTag(), ",");
                for (String s : split) {
                    if (!s.isEmpty()){
                        if (tags.get(s)!=null){
                            //计算出了 每增加一个标签的热度
                            tags.put(s,Integer.valueOf(tags.get(s))+5+quesion.getCommentCount()+quesion.getViewCount());
                        }else{
                            //计算出第一次增加标签的热度
                            tags.put(s,5+quesion.getCommentCount()+quesion.getViewCount());
                        }
                    }
                }
            }
            offset+=limit;
        }
    hoTaagCache.setTags(tags);
    log.info("The time is now {}", new Date());//日志
    }
}
