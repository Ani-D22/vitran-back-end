package com.vitran.backend.repo;

import com.vitran.backend.model.UserLoginPermissionAppl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginPermissionApplRepo extends JpaRepository<UserLoginPermissionAppl, Long> {
}
