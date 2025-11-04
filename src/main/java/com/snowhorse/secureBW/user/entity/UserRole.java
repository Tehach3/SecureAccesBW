package com.snowhorse.secureBW.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "user_roles", schema = "base")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserRole {

    @EmbeddedId
    private UserRoleID id;

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("roleId")
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "granted_at", nullable = false, columnDefinition = "timestamptz")
    private OffsetDateTime grantedAt;

    @Column(name = "granted_by", columnDefinition = "uuid")
    private java.util.UUID grantedBy;

    @PrePersist
    public void prePersist() {
        if (grantedAt == null) grantedAt = OffsetDateTime.now();
    }
}
