package cn.zhang.com.model;

import lombok.Data;

@Data
public class cc {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column COMMENT.id
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column COMMENT.parent_id
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    private Long parentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column COMMENT.TYPE
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    private Integer type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column COMMENT.content
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    private String content;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column COMMENT.commentator
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    private Integer commentator;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column COMMENT.gmt_create
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    private Long gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column COMMENT.gmt_modified
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    private Long gmtModified;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column COMMENT.like_count
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    private Long likeCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column COMMENT.comment_count
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    private Integer commentCount;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column COMMENT.id
     *
     * @return the value of COMMENT.id
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column COMMENT.id
     *
     * @param id the value for COMMENT.id
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column COMMENT.parent_id
     *
     * @return the value of COMMENT.parent_id
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column COMMENT.parent_id
     *
     * @param parentId the value for COMMENT.parent_id
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column COMMENT.TYPE
     *
     * @return the value of COMMENT.TYPE
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column COMMENT.TYPE
     *
     * @param type the value for COMMENT.TYPE
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column COMMENT.content
     *
     * @return the value of COMMENT.content
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column COMMENT.content
     *
     * @param content the value for COMMENT.content
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column COMMENT.commentator
     *
     * @return the value of COMMENT.commentator
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    public Integer getCommentator() {
        return commentator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column COMMENT.commentator
     *
     * @param commentator the value for COMMENT.commentator
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    public void setCommentator(Integer commentator) {
        this.commentator = commentator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column COMMENT.gmt_create
     *
     * @return the value of COMMENT.gmt_create
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column COMMENT.gmt_create
     *
     * @param gmtCreate the value for COMMENT.gmt_create
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column COMMENT.gmt_modified
     *
     * @return the value of COMMENT.gmt_modified
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    public Long getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column COMMENT.gmt_modified
     *
     * @param gmtModified the value for COMMENT.gmt_modified
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column COMMENT.like_count
     *
     * @return the value of COMMENT.like_count
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    public Long getLikeCount() {
        return likeCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column COMMENT.like_count
     *
     * @param likeCount the value for COMMENT.like_count
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column COMMENT.comment_count
     *
     * @return the value of COMMENT.comment_count
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    public Integer getCommentCount() {
        return commentCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column COMMENT.comment_count
     *
     * @param commentCount the value for COMMENT.comment_count
     *
     * @mbg.generated Tue Jan 28 17:10:37 CST 2020
     */
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
}