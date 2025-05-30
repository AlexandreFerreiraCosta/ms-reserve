package br.com.tecnologia.smuk.ms_reserve.task;

import br.com.tecnologia.smuk.ms_reserve.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "tb_task")
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(length = 80)
    private String title;

    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String priority;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private UUID userId;
}
