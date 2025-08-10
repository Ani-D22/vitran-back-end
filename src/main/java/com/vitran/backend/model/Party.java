package com.vitran.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "party")
@EntityListeners(AuditingEntityListener.class)
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enum_id")
    private Enumeration partyType;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "enum_id")
    private Enumeration status;

    @Column(nullable = false)
    private LocalDateTime fromDate;

    private LocalDateTime thruDate;

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<UserLogin> userLogins = new ArrayList<>();

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedDate;
}
