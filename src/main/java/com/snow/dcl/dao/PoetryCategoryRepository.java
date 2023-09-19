package com.snow.dcl.dao;

import com.snow.dcl.model.PoetryCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * (功能描述)
 * 诗词类别Repository
 * @Author:Dcl_Snow
 * @Create: 2023/9/19 10:32
 * @version: 1.0.0
 */
@Repository
public interface PoetryCategoryRepository extends JpaRepository<PoetryCategory,Long>, JpaSpecificationExecutor<PoetryCategory> {

}
