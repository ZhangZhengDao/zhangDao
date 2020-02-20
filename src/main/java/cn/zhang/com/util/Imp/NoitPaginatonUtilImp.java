package cn.zhang.com.util.Imp;

import cn.zhang.com.enums.NotificationEnum;
import cn.zhang.com.mapper.NotiFicationMapper;
import cn.zhang.com.model.NotiFication;
import cn.zhang.com.model.NotiFicationExample;
import cn.zhang.com.util.NoitPaginatonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoitPaginatonUtilImp implements NoitPaginatonUtil {
    @Autowired
    private NotiFicationMapper notiFicationMapper;
    @Override
    public void Read(Integer id) {
        NotiFication notiFication = notiFicationMapper.selectByPrimaryKey(id);
        notiFication.setStatus(1);
        NotiFicationExample example = new NotiFicationExample();
        example.createCriteria().andIdEqualTo(id);
        notiFicationMapper.updateByExample(notiFication, example);
        //并删除相似通知
    }



}
