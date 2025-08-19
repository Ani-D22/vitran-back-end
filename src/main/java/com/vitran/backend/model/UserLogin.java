package com.vitran.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_login")
@EntityListeners(AuditingEntityListener.class)
public class UserLogin {

    @Id
    private String userLoginId;

    @Column(nullable = false)
    private String password;

    private String passwordHint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partyId", referencedColumnName = "partyId")
    private Party party;

    @NonNull
    private LocalDateTime fromDate;

    private LocalDateTime thruDate;

    /** Permissions from Enumeration table where enumTypeId = LOGIN_PERMISSION */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_login_permissions",
            joinColumns = @JoinColumn(name = "user_login_id", referencedColumnName = "userLoginId"),
            inverseJoinColumns = @JoinColumn(name = "permissionId", referencedColumnName = "enumId")
    )
    private Set<Enumeration> permissions = new HashSet<>();

    @JsonIgnore
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @JsonIgnore
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedDate;
}
