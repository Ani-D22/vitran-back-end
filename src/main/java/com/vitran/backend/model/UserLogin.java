package com.vitran.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user_login")
@EntityListeners(AuditingEntityListener.class)
public class UserLogin {
    @Id
    private String userLoginId;

    @Column(nullable = false)
    private String password;
    private String passwordHint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partyId")
    @JsonBackReference
    private Party party;

    @OneToMany(mappedBy = "userLogin", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<UserLoginPermissionAppl> userLoginPermissionAppls;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedDate;
}
