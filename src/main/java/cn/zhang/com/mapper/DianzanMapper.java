package cn.zhang.com.mapper;

import cn.zhang.com.model.Dianzan;
import cn.zhang.com.model.DianzanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DianzanMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dianzan
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    long countByExample(DianzanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dianzan
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    int deleteByExample(DianzanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dianzan
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dianzan
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    int insert(Dianzan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dianzan
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    int insertSelective(Dianzan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dianzan
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    List<Dianzan> selectByExampleWithRowbounds(DianzanExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dianzan
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    List<Dianzan> selectByExample(DianzanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dianzan
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    Dianzan selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dianzan
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    int updateByExampleSelective(@Param("record") Dianzan record, @Param("example") DianzanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dianzan
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    int updateByExample(@Param("record") Dianzan record, @Param("example") DianzanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dianzan
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    int updateByPrimaryKeySelective(Dianzan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dianzan
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    int updateByPrimaryKey(Dianzan record);
}