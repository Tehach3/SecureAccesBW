package com.snowhorse.secureBW.user.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleID> {


    boolean existsById_UserIdAndId_RoleId(UUID userId, UUID roleId);


    void deleteById_UserIdAndId_RoleId(UUID userId, UUID roleId);

    @Query(value = """
      select r.code
      from base.user_roles ur
      join base.roles r on r.id = ur.role_id
      where ur.user_id = :userId
      """, nativeQuery = true)
    List<String> findRoleCodesByUserId(UUID userId);
}
