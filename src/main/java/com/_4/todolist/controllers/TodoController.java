package com._4.todolist.controllers;

import com._4.todolist.dtos.TodoDto;
import com._4.todolist.models.Todo;
import com._4.todolist.services.TodoService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/create")
    public ResponseEntity<List<Todo>> createTask(@RequestBody @NonNull  TodoDto todoDto) {
        if (todoDto.name() == null || todoDto.name().isEmpty()) {
            throw new RuntimeException("The name field cannot be empty.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.createTask(todoDto));
    }

    @GetMapping
    public ResponseEntity<List<Todo>> listAllTasks() {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.listAllTasks());
    }

    @GetMapping("{taskId}")
    public ResponseEntity<Todo> getTaskById(@PathVariable("taskId") Long taskId) {
        if (todoService.getTaskById(taskId) == null) {
            throw new RuntimeException("The task with this id do not exist.");
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(todoService.getTaskById(taskId));
    }

    @PutMapping("{taskId}")
    public ResponseEntity<List<Todo>> updateTask(@PathVariable("taskId") Long taskId,
                                                 @RequestBody TodoDto todoDto) {
        if (todoService.getTaskById(taskId) == null) {
            throw new RuntimeException("The task with this id do not exist.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(todoService.updateTaskById(taskId, todoDto));
    }

    @DeleteMapping("{taskId}")
    public ResponseEntity<List<Todo>> deleteTask(@PathVariable("taskId") Long taskId) {
        if (todoService.getTaskById(taskId) == null) {
            throw new RuntimeException("The task with this id do not exist.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(todoService.deleteTaskById(taskId));
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<Void> deleteAllTasks() {
        todoService.deleteAllTasks();
        return ResponseEntity.noContent().build();
    }
}