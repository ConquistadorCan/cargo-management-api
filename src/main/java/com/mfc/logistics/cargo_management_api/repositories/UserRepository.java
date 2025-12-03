package com.mfc.logistics.cargo_management_api.repositories;

import com.mfc.logistics.cargo_management_api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
}
