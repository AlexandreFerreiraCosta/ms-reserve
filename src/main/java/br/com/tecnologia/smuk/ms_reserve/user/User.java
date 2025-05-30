package br.com.tecnologia.smuk.ms_reserve.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity(name="tb_user")
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;


    @Column(name = "username",unique = true)
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
