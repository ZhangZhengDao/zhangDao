package cn.zhang.com.mapper;

import cn.zhang.com.model.Question;
import cn.zhang.com.model.QuestionExample;
import org.apache.ibatis.session.RowBounds;
import sun.rmi.runtime.Log;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Integer id);
    int comm(Integer id);
    List<Question> findTag(Question question);
    List<Question> getLike(Question example, RowBounds rowBounds);
    List<Question> getLikeREmen(Question example, RowBounds rowBounds);
    List<Question> getLikeReMenChaXun(Question example, RowBounds rowBounds);
    Long getzongshu(Question example);
    Long getzongshuRUMEN(Question example);
    Long count();
}