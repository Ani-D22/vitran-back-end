package com.vitran.backend.repo;

import com.vitran.backend.model.Party;
import com.vitran.backend.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLoginRepo extends JpaRepository<UserLogin, String> {
    Optional<UserLogin> findByParty(Party party);
}
