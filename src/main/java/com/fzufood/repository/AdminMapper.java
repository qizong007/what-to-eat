package com.fzufood.repository;

import com.fzufood.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author buzhouI
 */
@Repository
@Mapper
public interface AdminMapper {

    /**
     * 根据adminName, adminPassword查询出admin
     * @param adminName
     * @param adminPassword
     * @return Admin
     */
    Admin getAdminByNameAndPassword(@Param("adminName") String adminName, @Param("adminPassword") String adminPassword);

    /**
     * 根据adminId, adminPassword查询出admin
     * @param adminId
     * @param adminPassword
     * @return Admin
     */
    Admin getAdminByIdAndPassword(@Param("adminId") Integer adminId, @Param("adminPassword") String adminPassword);
}
