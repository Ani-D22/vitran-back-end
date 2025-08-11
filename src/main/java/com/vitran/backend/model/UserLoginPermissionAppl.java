package com.vitran.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user_login_permission_appl")
@EntityListeners(AuditingEntityListener.class)
public class UserLoginPermissionAppl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loginPermissionApplId; // surrogate

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userLoginId")
    private UserLogin userLogin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enumId")
    private Enumeration loginPermission;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedDate;
}
