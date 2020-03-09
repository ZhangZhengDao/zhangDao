package cn.zhang.com.util.Imp;

import cn.zhang.com.dto.QuestionDTO;
import cn.zhang.com.mapper.QuestionExtMapper;
import cn.zhang.com.mapper.QuestionMapper;
import cn.zhang.com.model.Question;
import cn.zhang.com.model.QuestionExample;
import cn.zhang.com.schedule.HoTaagCache;
import cn.zhang.com.schedule.HotTagTasks;
import cn.zhang.com.util.QuestionUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionUtilImp implements QuestionUtil {
    @Autowired(required = false)
    private QuestionMapper questionMapper;
    @Autowired(required = false)
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private HoTaagCache hoTaagCache;

    @Override
    public List<Question> findQuestionLimit(Integer page, Integer size) {
        page = size * (page - 1);
        RowBounds rowBounds = new RowBounds(page, size);
        QuestionExample example = new QuestionExample();
        example.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(example, rowBounds);
        return questions;
    }

    @Override
    public List<Question> findQuestionLike(Integer page, Integer size, String search) {
        if (search.isEmpty()) {
            return null;
        }
        page = size * (page - 1);
        RowBounds rowBounds = new RowBounds(page, size);
        QuestionExample example = new QuestionExample();
        example.setOrderByClause("gmt_create desc");
        Question example1 = new Question();
        example1.setTitle(StringUtil.CharOfThis(search));
        List<Question> questions = questionExtMapper.getLike(example1, rowBounds);
        return questions;
    }

    @Override
    public List<Question> findQuestionLabel(Integer page, Integer size, String label) {
        page = size * (page - 1);
        RowBounds rowBounds = new RowBounds(page, size);
        QuestionExample example = new QuestionExample();
        example.setOrderByClause("gmt_create desc");
        Question example1 = new Question();
        example1.setTag(label);
        List<Question> questions = questionExtMapper.getLikeREmen(example1, rowBounds);
        return questions;
    }


    /*
     * 根据所给集合分页
     * */
    private List<Question> findLimit(Integer page, Integer size, List<Question> list) {
        Integer pageSecond=page;
        page = size * (page - 1);
        size=pageSecond*size;
        if (size >= list.size()) {
            size = list.size();
        }
        List<Question> ListT = new ArrayList<>();
        for (int i = page; i < size; i++) {
            Object object = new Object();
            object = list.get(i);
            ListT.add(list.get(i));
        }
        return ListT;
    }

    @Override
    public List<Question> classify(Integer page, Integer size, String property) {
        if (StringUtils.equals(property, "热门问题")) {
            List<Question> findQuestionHottest = hoTaagCache.getFindQuestionHottest();
            List<Question> limit = findLimit(page, size, findQuestionHottest);
            return limit;
        }else if (StringUtils.equals(property,"消灭零回复")){
            List<Question> findQuestionNotReply = hoTaagCache.getFindQuestionNotReply();
            List<Question> limit = findLimit(page, size, findQuestionNotReply);
            return limit;
        }else {
            return findQuestionLimit(page,size);
        }
    }

    @Override
    public Integer TypeCount(String type) {
        if (StringUtils.equals(type, "热门问题")) {
            return hoTaagCache.getFindQuestionHottest().size();
        }else if (StringUtils.equals(type,"消灭零回复")){
            return hoTaagCache.getFindQuestionNotReply().size();
        }
        return 0;
    }

}
