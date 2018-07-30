package com.web.cloud.common.mapper;

import com.web.cloud.common.model.Admin;

public interface AdminMapper {
    /**
     *
     * @mbggenerated 2018-07-27
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-07-27
     */
    int insert(Admin record);

    /**
     *
     * @mbggenerated 2018-07-27
     */
    int insertSelective(Admin record);

    /**
     *
     * @mbggenerated 2018-07-27
     */
    Admin selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-07-27
     */
    int updateByPrimaryKeySelective(Admin record);

    /**
     *
     * @mbggenerated 2018-07-27
     */
    int updateByPrimaryKey(Admin record);
}