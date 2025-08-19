package com.vitran.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vitran.backend.model.enums.PartyStatus;
import com.vitran.backend.model.enums.PartyType;
import com.vitran.backend.model.enums.PartyRoleType;
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
@Table(name = "party")
@EntityListeners(AuditingEntityListener.class)
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partyId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PartyType partyType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PartyStatus status;

    private String firstName;
    private String lastName;
    private String gender;

    private String orgName;

    @NonNull
    private LocalDateTime fromDate;

    private LocalDateTime thruDate;

    /** Store multiple roles in a join table */
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "party_roles",
            joinColumns = @JoinColumn(name = "party_id")
    )
    @Column(name = "role", nullable = false, length = 20)
    private Set<PartyRoleType> roles = new HashSet<>();

    @JsonIgnore
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @JsonIgnore
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedDate;
}
