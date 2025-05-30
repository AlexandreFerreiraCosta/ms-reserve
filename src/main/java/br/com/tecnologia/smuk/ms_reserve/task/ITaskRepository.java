package br.com.tecnologia.smuk.ms_reserve.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ITaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByUserId(UUID userId);
}
