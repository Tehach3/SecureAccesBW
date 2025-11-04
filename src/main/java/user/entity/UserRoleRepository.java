package user.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleID> {

    // Para idempotencia al asignar rol
    boolean existsById_UserIdAndId_RoleId(UUID userId, UUID roleId);

    // Remover vínculo user-rol
    void deleteById_UserIdAndId_RoleId(UUID userId, UUID roleId);

    // (Opcional) Consultar solo los codes de roles de un usuario (ligero)
    @Query(value = """
      select r.code
      from base.user_rol ur
      join base.rol r on r.id = ur.role_id
      where ur.user_id = :userId
      """, nativeQuery = true)
    List<String> findRoleCodesByUserId(UUID userId);
}
