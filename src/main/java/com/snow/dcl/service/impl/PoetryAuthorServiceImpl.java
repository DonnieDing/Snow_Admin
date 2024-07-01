package com.snow.dcl.service.impl;

import com.snow.dcl.dao.PoetryAuthorRepository;
import com.snow.dcl.model.PoetryAuthor;
import com.snow.dcl.service.PoetryAuthorService;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * (功能描述)
 * 诗词作者Service实现
 * @Author:Dcl_Snow
 * @Create: 2023/9/19 10:36
 * @version: 1.0.0
 */
@Service
public class PoetryAuthorServiceImpl implements PoetryAuthorService {

    @Resource
    private PoetryAuthorRepository poetryAuthorRepository;

    @Override
    public void save (PoetryAuthor poetryAuthor){
        poetryAuthorRepository.save(poetryAuthor);
    }

    @Override
    public Page<PoetryAuthor> findAll(PoetryAuthor poetryAuthor, Integer page, Integer size) {
        // 1.动态拼接查询条件
        Specification<PoetryAuthor> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!ObjectUtils.isEmpty(poetryAuthor.getName())) {
                list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + poetryAuthor.getName() + "%", '/'));
            }
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        };
        // 2.构造分页
        Pageable pageable = PageRequest.of(page - 1, size);
        // 3.查询数据并返回
        Page<PoetryAuthor> pagePoetryAuthor = poetryAuthorRepository.findAll(specification, pageable);
        return pagePoetryAuthor;
    }

    @Override
    public PoetryAuthor findById(Long id) {
        return poetryAuthorRepository.findById(id).orElseGet(PoetryAuthor::new);
    }

}
