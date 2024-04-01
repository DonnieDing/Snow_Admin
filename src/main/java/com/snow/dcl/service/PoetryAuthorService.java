package com.snow.dcl.service;

import com.snow.dcl.model.PoetryAuthor;
import org.springframework.data.domain.Page;

/**
 * (功能描述)
 * 诗词作者Service
 * @Author:Dcl_Snow
 * @Create: 2023/9/19 10:36
 * @version: 1.0.0
 */
public interface PoetryAuthorService {

    void save (PoetryAuthor poetryAuthor);

    Page<PoetryAuthor> findAll(PoetryAuthor poetryAuthor, Integer page, Integer size);

    PoetryAuthor findById(Long id);
}
