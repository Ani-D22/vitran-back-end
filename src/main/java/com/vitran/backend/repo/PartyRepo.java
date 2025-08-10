package com.vitran.backend.repo;

import com.vitran.backend.model.Party;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyRepo extends JpaRepository<Party,Long> {
}
