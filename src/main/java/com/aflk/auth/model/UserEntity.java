package com.aflk.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity(name = "TB_USER")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userCd")
    private long userCd;

    @Column(name = "domainCd", nullable = false)
    private String domainCd;

    @Column(name = "userID",nullable = false)
    private String userId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "userPW")
    private String userPw;

    @Column(name = "userType",nullable = true)
    private String userType;

    @Column(name = "userStatus",nullable = true)
    private String userStatus;

    @Column(name = "userNM",nullable = true)
    private String userNm;

    @Column(name = "userNickNm",nullable = true)
    private String userNickNm;

    @Column(name = "userGender",nullable = true)
    private String userGender;

    @Column(name = "userBirth",nullable = true)
    private String userBirth;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private List<AuthEntity> roles = new ArrayList<>();

    public void setRoles(List<AuthEntity> role) {
        this.roles = role;
        role.forEach(o -> o.setUserEntity(this));
    }
}