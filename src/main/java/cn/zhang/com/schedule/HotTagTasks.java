package cn.zhang.com.schedule;

import cn.zhang.com.dto.RedisD;
import cn.zhang.com.dto.TagsDTO;
import cn.zhang.com.mapper.QuestionMapper;
import cn.zhang.com.mapper.UserMapper;
import cn.zhang.com.model.Question;
import cn.zhang.com.model.QuestionExample;
import cn.zhang.com.model.User;
import cn.zhang.com.model.UserExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.util.StringUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

//Sprig 定时器 //查询人们标签

@Slf4j
@Controller
public class HotTagTasks {
    //每隔2秒调用一次此方法
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private HoTaagCache hoTaagCache;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HttpServletRequest request;

    //    @Scheduled(fixedRate = 1000*60*60)
    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void reportCurrentTime() {
        int offset = 0;
        int limit = 20;
        log.info("reportCurrentTime start{}", new Date());
        List<Question> list = new ArrayList<>();
        Map<String, Integer> tags = new HashMap<>();
        while (offset == 0 || list.size() == limit) {
            list = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, limit));
            for (Question quesion :
                    list) {
                //先先拿到所有标签
                String[] split = StringUtils.split(quesion.getTag(), ",");
                for (String s : split) {
                    if (!s.isEmpty()) {
                        if (tags.get(s) != null) {
                            //计算出了 每增加一个标签的热度
                            tags.put(s, Integer.valueOf(tags.get(s)) + 5 + quesion.getCommentCount() + quesion.getViewCount());
                        } else {
                            //计算出第一次增加标签的热度
                            tags.put(s, 5 + quesion.getCommentCount() + quesion.getViewCount());
                        }
                    }
                }
            }
            offset += limit;
        }
        hoTaagCache.setTags(tags);
        log.info("The time is now {}", new Date());//日志
    }

    //记录用户在线还是离线 每次运行的时候清除 redis 中未删除掉的数据
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)//每隔一分钟判断一次所有用户状态
    public void lixian() {
        List<User> users = userMapper.selectByExample(new UserExample());
        Jedis jedis = RedisD.getRedis();
        for (User user : users) {
            jedis.del(user.getAccount());
        }
    }

}
