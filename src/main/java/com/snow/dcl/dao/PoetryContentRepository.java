package com.snow.dcl.dao;

import com.snow.dcl.model.PoetryContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * (功能描述)
 * 诗词正文内容Repository
 * @Author:Dcl_Snow
 * @Create: 2023/9/19 10:32
 * @version: 1.0.0
 */
@Repository
public interface PoetryContentRepository extends JpaRepository<PoetryContent,Long>, JpaSpecificationExecutor<PoetryContent> {

}
