package cn.zhang.com.service;

import cn.zhang.com.enums.DianzanEum;
import cn.zhang.com.mapper.Comm;
import cn.zhang.com.mapper.CommentExtMapper;
import cn.zhang.com.mapper.DianzanMapper;
import cn.zhang.com.model.Comment;
import cn.zhang.com.model.Dianzan;
import cn.zhang.com.model.DianzanExample;
import cn.zhang.com.model.User;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service
public class DianzanService {

    @Autowired
    private DianzanMapper dianzanMapper;
    @Autowired
    private Comm comm;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Transactional
    public Integer DianzanPanDuan(int id, User user) {
        //根据回复问题id去查询这个回复是否被点过赞
        DianzanExample example1 = new DianzanExample();
        example1.createCriteria().andOuteridEqualTo((long) Math.toIntExact(id)).andNotifierEqualTo(user.getId().longValue());
        List<Dianzan> dianzans = dianzanMapper.selectByExample(example1);
        System.out.println(dianzans.size()+"}");
        //当前用户未被点赞过
        if (dianzans.size() == 0) {
            //创建点赞表然后点赞
            Dianzan dianzan = new Dianzan();
            dianzan.setNotifier(user.getId().longValue());
            //回复评论id
            dianzan.setOuterid((long) Math.toIntExact(id));
            dianzan.setType(1);
            dianzan.setZhaunttai(DianzanEum.REPLY_QUESTION.getType());
            dianzan.setGmtCreate(System.currentTimeMillis());
            //向数据库添加点赞数据
            System.out.println(dianzan.toString());
            dianzanMapper.insert(dianzan);
            //增加当前回复的点赞数
            commentExtMapper.addupdate(id);
            return DianzanEum.REPLY_QUESTION.getType();
        } else {
            Dianzan dianzan = dianzans.get(0);
            //代表数据库已有点赞数据，那就拿到点赞数据判断是否点赞
            if (StringUtils.equals(dianzans.get(0).getZhaunttai(), DianzanEum.REPLY_QUESTION.getType())) {
                //如果是点赞状态则取消点赞
                dianzan.setZhaunttai(DianzanEum.REPLY_COMMENT.getType());
                //点赞数减一
                commentExtMapper.jian(id);
            } else {
                //如果是为点赞状态则点赞
                dianzan.setZhaunttai(DianzanEum.REPLY_QUESTION.getType());
                //增加当前回复的点赞数
                commentExtMapper.addupdate(id);
            }
            DianzanExample example = new DianzanExample();
            example.createCriteria().andIdEqualTo(dianzan.getId());
            dianzanMapper.updateByExample(dianzan, example);
            return dianzan.getZhaunttai();
        }
    }
}
