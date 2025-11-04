package com.snowhorse.secureBW.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

    @Entity
    @Table(name = "users", schema = "base", uniqueConstraints = {@UniqueConstraint(name = "uk_users_username", columnNames = {"username"})
    })
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public class User{
        @Id
        @GeneratedValue
        @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
        private UUID id;

        @NotBlank
        @Size(max = 64)
        @Column(name = "username", nullable = false, length = 64)
        private String username;

        @NotBlank
        @Size(min = 40, max = 40)
        @Column(name = "gpg_fingerprint", nullable = false, columnDefinition = "char(40)")
        private String gpgFingerprint;

        @Column(name = "challenge_filename", columnDefinition = "text")
        private String challengeFilename;

        @Size(min = 64, max = 64)
        @Column(name = "challenge_sha256", columnDefinition = "char(64)")
        private String challengeSha256;

        @Column(name = "challenge_dir_override", columnDefinition = "text")
        private String challengeDirOverride;

        @Column(name = "active", nullable = false)
        private Boolean active = true;

        @Column(name = "failed_attempts", nullable = false)
        private Integer failedAttempts = 0;

        @Column(name = "locked_until")
        private OffsetDateTime lockedUntil;

        @Column(name = "last_login_at")
        private OffsetDateTime lastLoginAt;

        @Column(name = "created_at", nullable = false, columnDefinition = "timestamptz")
        private OffsetDateTime createdAt;

        @Column(name = "updated_at", nullable = false, columnDefinition = "timestamptz")
        private OffsetDateTime updatedAt;

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
        private java.util.Set<UserRole> userRoles = new java.util.HashSet<>();

        @jakarta.persistence.Transient
        public java.util.Set<String> getRoleCodes() {
            var codes = new java.util.HashSet<String>();
            for (var ur : userRoles) {
                if (ur.getRole() != null) codes.add(ur.getRole().getCode());
            }
            return codes;
        }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
    }


