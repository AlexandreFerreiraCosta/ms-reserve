package br.com.tecnologia.smuk.ms_reserve.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);
}
