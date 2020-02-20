package cn.zhang.com.util.Imp;

import cn.zhang.com.mapper.QuestionExtMapper;
import cn.zhang.com.mapper.QuestionMapper;
import cn.zhang.com.mapper.UserMapper;
import cn.zhang.com.model.Question;
import cn.zhang.com.model.QuestionExample;
import cn.zhang.com.util.QuestionUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionUtilImp implements QuestionUtil {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Override
    public List<Question> findQuestionLimit(Integer page, Integer size) {
        page=size*(page-1);
        RowBounds rowBounds = new RowBounds(page,size);
        QuestionExample example = new QuestionExample();
        example.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(example, rowBounds);
        return questions;
    }

    @Override
    public List<Question> findQuestionLike(Integer page, Integer size, String search) {
        if (search.isEmpty()){
            return null;
        }
        page=size*(page-1);
        RowBounds rowBounds = new RowBounds(page,size);
        QuestionExample example = new QuestionExample();
        example.setOrderByClause("gmt_create desc");
        Question example1 = new Question();
        example1.setTitle(StringUtil.CharOfThis(search));
        List<Question> questions = questionExtMapper.getLike(example1, rowBounds);
        return questions;
    }

    @Override
    public List<Question> findQuestionLable(Integer page, Integer size, String label) {
        page=size*(page-1);
        RowBounds rowBounds = new RowBounds(page,size);
        QuestionExample example = new QuestionExample();
        example.setOrderByClause("gmt_create desc");
        Question example1 = new Question();
        example1.setTag(label);
        List<Question> questions = questionExtMapper.getLikeREmen(example1, rowBounds);
        return questions;
    }
}
