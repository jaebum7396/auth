package auth.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedDate
    @Column(nullable = false, updatable = false, name = "INSERT_DT")
    @JsonIgnore
    private LocalDateTime insertDt;

    @CreatedBy
    @JsonIgnore
    @Column(name = "INSERT_USER_CD")
    private Long insertUserCd;

    @LastModifiedDate
    @JsonIgnore
    @Column(name = "UPDATE_DT")
    private LocalDateTime updateDt;

    @LastModifiedBy
    @JsonIgnore
    @Column(name = "UPDATE_USER_CD")
    private Long updateUserCd;

    @JsonIgnore
    @Column(name = "DELETE_YN", length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
    protected char deleteYn;

    @JsonIgnore
    @Column(name = "DELETE_DT")
    private LocalDateTime deleteDt;

    @JsonIgnore
    @Column(name = "DELETE_USER_CD")
    private Long deleteUserCd;

    @JsonIgnore
    @Column(name = "DELETE_REMARK")
    private String deleteRemark;
}
