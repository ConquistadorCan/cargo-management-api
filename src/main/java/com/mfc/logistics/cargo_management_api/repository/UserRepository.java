package com.mfc.logistics.cargo_management_api.repository;

import com.mfc.logistics.cargo_management_api.enums.UserRoleEnum;
import com.mfc.logistics.cargo_management_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);
    public Optional<User> findByUsername(String username);
    public List<User> findByRole(UserRoleEnum role);

    @Query("SELECT u FROM User u WHERE u.role = com.mfc.logistics.cargo_management_api.enums.UserRoleEnum.STAFF AND u.id NOT IN (SELECT s.assignedTo.id FROM Shipment s WHERE s.status = com.mfc.logistics.cargo_management_api.enums.ShipmentStatusEnum.IN_TRANSIT AND s.assignedTo IS NOT NULL)")
    public List<User> findAvailableStaff();
}
