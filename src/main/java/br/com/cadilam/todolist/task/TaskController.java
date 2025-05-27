package br.com.cadilam.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {

        // var task = taskRepository.findByTitle(taskModel.getTitle());

        // if (task != null) {
        // return ResponseEntity.status(400).body("Task already.");
        // }

        taskModel.setIdUser((UUID) request.getAttribute("idUser"));

        System.out.println("Passou pela Controller");

        var currentDate = LocalDateTime.now();

        if (currentDate.isAfter(taskModel.getStartDate()) || currentDate.isAfter(taskModel.getEndtDate())) {
            return ResponseEntity.status(400)
                    .body("A data de inicio / datade término dever ser maior do que a data atual.");
        }

        if (currentDate.isAfter(taskModel.getStartDate()) || currentDate.isAfter(taskModel.getEndtDate())) {
            return ResponseEntity.status(400)
                    .body("A data de inicio / datade término dever ser maior do que a data atual.");
        }

        if (taskModel.getStartDate().isAfter(taskModel.getEndtDate())) {
            return ResponseEntity.status(400).body("A data de inicio deve ser menor do que a data de termino.");
        }

        var taskCreated = this.taskRepository.save(taskModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request) {
        var list = this.taskRepository.findByIdUser((UUID) request.getAttribute("idUser"));
        return list;
    }
}
