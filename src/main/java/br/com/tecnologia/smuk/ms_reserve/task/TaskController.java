package br.com.tecnologia.smuk.ms_reserve.task;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private ITaskRepository iTaskRepository;

    @PostMapping
    public ResponseEntity createTask(@RequestBody Task task, HttpServletRequest request){
        task.setUserId((UUID) request.getAttribute("userId"));

        LocalDateTime currentDate = LocalDateTime.now();

        if(currentDate.isAfter(task.getStartDate()) || currentDate.isAfter(task.getEndDate())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio/término deve ser maior que a data atual.");
        }

        if(task.getStartDate().isAfter(task.getEndDate())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio deve ser anterior a data de término.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(iTaskRepository.save(task));
    }

    @GetMapping
    public ResponseEntity<List<Task>> listTask(HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(
                iTaskRepository.findByUserId((UUID) request.getAttribute("userId")));
    }

    @PutMapping("/{taskId}")
    public Task updateTask(@RequestBody Task task, @PathVariable UUID taskId, HttpServletRequest request){
        task.setId(taskId);
        task.setUserId((UUID) request.getAttribute("userId"));

        return iTaskRepository.save(task);
    }
}
