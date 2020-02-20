package cn.zhang.com.util;

import cn.zhang.com.model.Question;

import java.util.List;

public interface QuestionUtil {
    //根据分页id问题数据
    public List<Question> findQuestionLimit(Integer page, Integer size);
    public List<Question> findQuestionLike(Integer page, Integer size,String search);
    /**
     * @param page 当前页面
     * @param size 每页个数
     * @param hot 要查找的标签
     * */
    public List<Question> findQuestionLable(Integer page, Integer size,String hot);

}
