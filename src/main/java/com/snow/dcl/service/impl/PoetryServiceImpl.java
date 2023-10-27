package com.snow.dcl.service.impl;

import com.snow.dcl.dao.PoetryContentRepository;
import com.snow.dcl.model.PoetryContent;
import com.snow.dcl.service.PoetryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PoetryService
 * (功能描述)
 * 诗词业务ServiceImpl
 * @Author Dcl_Snow
 * @Create 2023/9/19 13:42
 * @Version 1.0.0
 */
@Service
public class PoetryServiceImpl implements PoetryService {

    @Resource
    private PoetryContentRepository poetryContentRepository;

    @Override
    public Page<PoetryContent> findAll(PoetryContent poetryContent, Integer page, Integer size) {
        // 1.动态拼接查询条件
        Specification<PoetryContent> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!ObjectUtils.isEmpty(poetryContent.getTitle())) {
                list.add(criteriaBuilder.like(root.get("title").as(String.class), "%" + poetryContent.getTitle()
                        .replaceAll("/", "//")
                        .replaceAll("_", "/_")
                        .replaceAll("%", "/%") + "%", '/'));
            }
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        };
        // 2.构造分页
        Pageable pageable = PageRequest.of(page - 1, size);
        // 3.查询数据并返回
        Page<PoetryContent> pagePoetryContent = poetryContentRepository.findAll(specification, pageable);
        return pagePoetryContent;
    }

    @Override
    public PoetryContent findById(Long id) {
        return poetryContentRepository.findById(id).orElseGet(PoetryContent::new);
    }
}
