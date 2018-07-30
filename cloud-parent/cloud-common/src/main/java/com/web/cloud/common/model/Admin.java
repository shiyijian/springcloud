package com.web.cloud.common.model;

import java.util.Date;

public class Admin {
    /**
     * id
     */
    private Long id;

    /**
     * 管理员的父节点id，如果是0，则是拥有所有权限的管理员
     */
    private Long parentId;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 账号状态 0禁用 1正常
     */
    private Byte status;

    /**
     * 最后登录的时间
     */
    private Date lastLoginTime;

    /**
     * 业务隔离标志码
     */
    private String bizCode;

    /**
     * 创建时间
     */
    private Date gmtCreated;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 逻辑删除标志位
     */
    private Byte deleteMark;

    /**
     * 删除时间戳
     */
    private Long deleteTimestamp;

    /**
     * id
     * @return id id
     */
    public Long getId() {
        return id;
    }

    /**
     * id
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 管理员的父节点id，如果是0，则是拥有所有权限的管理员
     * @return parent_id 管理员的父节点id，如果是0，则是拥有所有权限的管理员
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 管理员的父节点id，如果是0，则是拥有所有权限的管理员
     * @param parentId 管理员的父节点id，如果是0，则是拥有所有权限的管理员
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 账号
     * @return account 账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 账号
     * @param account 账号
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * 密码
     * @return password 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 姓名
     * @return name 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 姓名
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 角色id
     * @return role_id 角色id
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 角色id
     * @param roleId 角色id
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 账号状态 0禁用 1正常
     * @return status 账号状态 0禁用 1正常
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 账号状态 0禁用 1正常
     * @param status 账号状态 0禁用 1正常
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 最后登录的时间
     * @return last_login_time 最后登录的时间
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 最后登录的时间
     * @param lastLoginTime 最后登录的时间
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 业务隔离标志码
     * @return biz_code 业务隔离标志码
     */
    public String getBizCode() {
        return bizCode;
    }

    /**
     * 业务隔离标志码
     * @param bizCode 业务隔离标志码
     */
    public void setBizCode(String bizCode) {
        this.bizCode = bizCode == null ? null : bizCode.trim();
    }

    /**
     * 创建时间
     * @return gmt_created 创建时间
     */
    public Date getGmtCreated() {
        return gmtCreated;
    }

    /**
     * 创建时间
     * @param gmtCreated 创建时间
     */
    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    /**
     * 修改时间
     * @return gmt_modified 修改时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 修改时间
     * @param gmtModified 修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 逻辑删除标志位
     * @return delete_mark 逻辑删除标志位
     */
    public Byte getDeleteMark() {
        return deleteMark;
    }

    /**
     * 逻辑删除标志位
     * @param deleteMark 逻辑删除标志位
     */
    public void setDeleteMark(Byte deleteMark) {
        this.deleteMark = deleteMark;
    }

    /**
     * 删除时间戳
     * @return delete_timestamp 删除时间戳
     */
    public Long getDeleteTimestamp() {
        return deleteTimestamp;
    }

    /**
     * 删除时间戳
     * @param deleteTimestamp 删除时间戳
     */
    public void setDeleteTimestamp(Long deleteTimestamp) {
        this.deleteTimestamp = deleteTimestamp;
    }
}