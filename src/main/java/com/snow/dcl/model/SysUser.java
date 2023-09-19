package com.snow.dcl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * @version 1.0.0
 * @ClassName SysUser
 * (功能描述)
 * 用户实体类
 * @Author Dcl_Snow
 * @Create 2021/8/24 14:36
 */
@Entity
@Table(name = "sys_user")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class SysUser implements UserDetails, Serializable {

    private static final long serialVersionUID = 5539630790320060413L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String nickName;

    private String phone;

    private String email;

    private String gender;

    private String avatar;

    private Short status = 1;

    private Boolean isDeleted = false;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    // @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<SysRole> roleSet = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> hashSet = new HashSet<>();
        // 1.第一种常规
        // roleSet.forEach(item -> hashSet.add(new SimpleGrantedAuthority("ROLE_" + item.getRoleCode())));
        // for (SysRole sysRole : this.roleSet) {
        //     for (SysPermission sysPermission : sysRole.getPermissionSet()) {
        //         String permissionValue = sysPermission.getPermissionValue();
        //         if (permissionValue != null){
        //             hashSet.add(new SimpleGrantedAuthority(permissionValue));
        //         }
        //     }
        // }

        // 2.第二种转Stream
        // roleSet.forEach(sysRole -> sysRole.getPermissionSet().stream()
        //         .filter(sysPermission -> StringUtils.hasText(sysPermission.getPermissionValue()))
        //         .forEach(sysPermission -> hashSet.add(new SimpleGrantedAuthority(sysPermission.getPermissionValue()))));
        // return hashSet;

        // 3.第三种使用Optional
        roleSet.forEach(sysRole -> sysRole.getPermissionSet()
                .forEach(sysPermission -> hashSet.add(new SimpleGrantedAuthority(Optional.ofNullable(sysPermission.getPermissionValue()).orElse("none")))));
        return hashSet;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
