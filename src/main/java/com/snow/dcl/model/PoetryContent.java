package com.snow.dcl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName PoetryAuthor
 * (功能描述)
 * 诗词作者
 * @Author Dcl_Snow
 * @Create 2023/9/19 10:18
 * @Version 1.0.0
 */
@Entity
@Table(name = "poetry_content")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class PoetryContent implements Serializable {

    private static final long serialVersionUID = -3829859489286080952L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "category_id")
    private Long categoryId;

    private String title;

    private String content;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @JoinColumn(name = "author_id",insertable = false,updatable = false)
    @OneToOne(targetEntity=PoetryAuthor.class,cascade=CascadeType.DETACH)
    private PoetryAuthor poetryAuthor;

    @JoinColumn(name = "category_id",insertable = false,updatable = false)
    @OneToOne(targetEntity=PoetryCategory.class,cascade=CascadeType.DETACH)
    private PoetryCategory poetryCategory;

}
