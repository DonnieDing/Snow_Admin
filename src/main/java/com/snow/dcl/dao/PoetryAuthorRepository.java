package com.snow.dcl.dao;

import com.snow.dcl.model.PoetryAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * (功能描述)
 * 诗词作者Repository
 * @Author:Dcl_Snow
 * @Create: 2023/9/19 10:32
 * @version: 1.0.0
 */
@Repository
public interface PoetryAuthorRepository extends JpaRepository<PoetryAuthor,Long>, JpaSpecificationExecutor<PoetryAuthor> {

    PoetryAuthor findByName(String name);
}
