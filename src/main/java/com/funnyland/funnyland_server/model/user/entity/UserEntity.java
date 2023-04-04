package com.funnyland.funnyland_server.model.user.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;

import lombok.Data;

@Data
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
public class UserEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "salt")
    private String salt;

    @Column(name = "email")
    private String email;

    @Nullable
    @Column(name = "user_url")
    private String userUrl;

    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private String roles;

    // @Column(name = "created_at", insertable = false, updatable = false)
    @Column(name = "created_at")
    // @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    // @CreationTimestamp
    private Date updatedAt;

    @Column(name = "credential_created_at")
    // @CreationTimestamp
    private Date credentialCreatedAt;

    @Column(name = "credential_expire_at")
    // @CreationTimestamp
    private Date credentialExpireAt;

    @Column(name = "deleted")
    private int deleted;

    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.replaceAll(" ","").split(","));
        }
        return new ArrayList<>();
    }

}
