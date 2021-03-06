package cn.zhang.com.model;

import lombok.Data;

@Data
public class Comment {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cc.id
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cc.parent_id
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    private Long parentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cc.TYPE
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    private Integer type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cc.content
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    private String content;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cc.commentator
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    private Integer commentator;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cc.gmt_create
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    private Long gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cc.gmt_modified
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    private Long gmtModified;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cc.like_count
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    private Long likeCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cc.comment_count
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    private Integer commentCount;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cc.id
     *
     * @return the value of cc.id
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cc.id
     *
     * @param id the value for cc.id
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cc.parent_id
     *
     * @return the value of cc.parent_id
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cc.parent_id
     *
     * @param parentId the value for cc.parent_id
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cc.TYPE
     *
     * @return the value of cc.TYPE
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cc.TYPE
     *
     * @param type the value for cc.TYPE
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cc.content
     *
     * @return the value of cc.content
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cc.content
     *
     * @param content the value for cc.content
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cc.commentator
     *
     * @return the value of cc.commentator
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    public Integer getCommentator() {
        return commentator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cc.commentator
     *
     * @param commentator the value for cc.commentator
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    public void setCommentator(Integer commentator) {
        this.commentator = commentator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cc.gmt_create
     *
     * @return the value of cc.gmt_create
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cc.gmt_create
     *
     * @param gmtCreate the value for cc.gmt_create
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cc.gmt_modified
     *
     * @return the value of cc.gmt_modified
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    public Long getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cc.gmt_modified
     *
     * @param gmtModified the value for cc.gmt_modified
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cc.like_count
     *
     * @return the value of cc.like_count
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    public Long getLikeCount() {
        return likeCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cc.like_count
     *
     * @param likeCount the value for cc.like_count
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cc.comment_count
     *
     * @return the value of cc.comment_count
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    public Integer getCommentCount() {
        return commentCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cc.comment_count
     *
     * @param commentCount the value for cc.comment_count
     *
     * @mbg.generated Tue Feb 25 20:45:06 CST 2020
     */
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
}