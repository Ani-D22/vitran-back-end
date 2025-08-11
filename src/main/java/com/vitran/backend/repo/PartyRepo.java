package com.vitran.backend.repo;

import com.vitran.backend.model.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PartyRepo extends JpaRepository<Party,Long> {
    @Query("SELECT p FROM Party p JOIN FETCH p.userLogins WHERE p.partyId = :id")
    Optional<Party> findByIdWithUserLogins(@Param("id") Long id);
}
