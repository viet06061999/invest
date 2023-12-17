package com.vn.investion.model;

import com.vn.investion.model.define.AuditEntity;
import com.vn.investion.model.define.Role;
import com.vn.investion.model.define.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Table(name = "users")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends AuditEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 10)
    private String code;
    @Column(length = 44)
    private String firstname;
    @Column(length = 44)
    private String lastname;
    @Column(length = 128)
    private String passwd;
    @Column(unique = true, length = 12)
    private String phone;
    @Column(length = 10)
    private String refId;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Boolean isLockPoint;
    private long depositBalance = 0;
    private long availableBalance = 0;
    private String identification;
    private UserStatus status;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(role.name());
        //return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return passwd;
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
