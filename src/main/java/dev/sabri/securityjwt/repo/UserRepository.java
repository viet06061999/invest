package dev.sabri.securityjwt.repo;

import dev.sabri.securityjwt.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByPhone(String phone);

    Optional<User> findByCode(String code);
}
