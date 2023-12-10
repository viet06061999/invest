package com.vn.investion.model;

import com.vn.investion.model.define.AuditEntity;
import com.vn.investion.model.define.Role;
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
    @Column(length = 44)
    private String firstname;
    @Column(length = 44)
    private String lastname;
    @Column(unique = true, length = 12)
    private String phone;
    @Column(length = 10)
    private String code;
    @Column(length = 10)
    private String refId;
    private Double point;
    @Column(length = 128)
    private String passwd;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Boolean isActive;
    private Boolean isLockPoint;

//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(name = "user_package",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "package_id"))
//    private Set<InvestPackage> investPackages = new HashSet<>();
//
//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(name = "user_leader",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "package_id"))
//    private Set<InvestPackage> leaderPackages = new HashSet<>();

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
