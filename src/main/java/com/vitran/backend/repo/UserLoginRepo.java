package com.vitran.backend.repo;

import com.vitran.backend.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginRepo extends JpaRepository<UserLogin, String> {
}
