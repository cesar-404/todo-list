package com._4.todolist.controllers;

import com._4.todolist.models.Todo;
import com._4.todolist.services.TodoService;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<List<Todo>> createTask(@RequestBody Todo todo) {
        var todoList = todoService.createTask(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(todoList);
    }

    @GetMapping
    public ResponseEntity<List<Todo>> listTasks() {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.listAll());
    }

    @GetMapping("{taskId}")
    public ResponseEntity<Todo> getById(@PathVariable("taskId") Long taskId) {
        return ResponseEntity.status(HttpStatus.FOUND).body(todoService.getById(taskId));
    }

    @PutMapping()
    public ResponseEntity<List<Todo>> updateTask(@RequestBody Todo todo) {
       return ResponseEntity.status(HttpStatus.OK).body(todoService.updateTask(todo));
    }

    @DeleteMapping("{taskId}")
    public ResponseEntity<List<Todo>> deleteTask(@PathVariable("taskId") Long taskId) {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.deleteTask(taskId));
    }
}

