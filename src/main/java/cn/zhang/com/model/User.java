package cn.zhang.com.model;

public class User {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uu.id
     *
     * @mbg.generated Tue Jan 28 18:55:57 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uu.avatar_url
     *
     * @mbg.generated Tue Jan 28 18:55:57 CST 2020
     */
    private String avatarUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uu.NAME
     *
     * @mbg.generated Tue Jan 28 18:55:57 CST 2020
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uu.account
     *
     * @mbg.generated Tue Jan 28 18:55:57 CST 2020
     */
    private String account;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uu.token
     *
     * @mbg.generated Tue Jan 28 18:55:57 CST 2020
     */
    private String token;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uu.gmt_create
     *
     * @mbg.generated Tue Jan 28 18:55:57 CST 2020
     */
    private Long gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uu.gmt_modified
     *
     * @mbg.generated Tue Jan 28 18:55:57 CST 2020
     */
    private Long gmtModified;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uu.id
     *
     * @return the value of uu.id
     *
     * @mbg.generated Tue Jan 28 18:55:57 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uu.id
     *
     * @param id the value for uu.id
     *
     * @mbg.generated Tue Jan 28 18:55:57 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uu.avatar_url
     *
     * @return the value of uu.avatar_url
     *
     * @mbg.generated Tue Jan 28 18:55:57 CST 2020
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uu.avatar_url
     *
     * @param avatarUrl the value for uu.avatar_url
     *
     * @mbg.generated Tue Jan 28 18:55:57 CST 2020
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uu.NAME
     *
     * @return the value of uu.NAME
     *
     * @mbg.generated Tue Jan 28 18:55:57 CST 2020
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uu.NAME
     *
     * @param name the value for uu.NAME
     *
     * @mbg.generated Tue Jan 28 18:55:57 CST 2020
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uu.account
     *
     * @return the value of uu.account
     *
     * @mbg.generated Tue Jan 28 18:55:57 CST 2020
     */
    public String getAccount() {
        return account;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uu.account
     *
     * @param account the value for uu.account
     *
     * @mbg.generated Tue Jan 28 18:55:57 CST 2020
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uu.token
     *
     * @return the value of uu.token
     *
     * @mbg.generated Tue Jan 28 18:55:57 CST 2020
     */
    public String getToken() {
        return token;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uu.token
     *
     * @param token the value for uu.token
     *
     * @mbg.generated Tue Jan 28 18:55:57 CST 2020
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uu.gmt_create
     *
     * @return the value of uu.gmt_create
     *
     * @mbg.generated Tue Jan 28 18:55:57 CST 2020
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uu.gmt_create
     *
     * @param gmtCreate the value for uu.gmt_create
     *
     * @mbg.generated Tue Jan 28 18:55:57 CST 2020
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uu.gmt_modified
     *
     * @return the value of uu.gmt_modified
     *
     * @mbg.generated Tue Jan 28 18:55:57 CST 2020
     */
    public Long getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uu.gmt_modified
     *
     * @param gmtModified the value for uu.gmt_modified
     *
     * @mbg.generated Tue Jan 28 18:55:57 CST 2020
     */
    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }
}