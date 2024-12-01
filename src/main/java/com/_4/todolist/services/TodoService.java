package com._4.todolist.services;

import com._4.todolist.dtos.TodoDto;
import com._4.todolist.models.Todo;
import com._4.todolist.repositories.TodoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public List<Todo> createTask(TodoDto todoDto) {
        var todo = new Todo();
        BeanUtils.copyProperties(todoDto, todo);
        todoRepository.save(todo);
        return listAllTasks();
    }

    public List<Todo> listAllTasks() {
        Sort sort = Sort.by("priority")
                .descending()
                .and(Sort.by("name")
                        .ascending());
        return todoRepository.findAll(sort);
    }

    public Todo getTaskById(Long taskId) {
        var foundTask = todoRepository.findById(taskId);
        return foundTask.orElse(null);
    }

    public List<Todo> updateTaskById(Long id, TodoDto todoDto) {
        var todo = todoRepository.findById(id);
        BeanUtils.copyProperties(todoDto, todo);
        if (todo.isPresent()) {
            todoRepository.save(todo.get());
            return listAllTasks();
        }
        listAllTasks();
        return null;
    }

    public List<Todo> deleteTaskById(Long id) {
        var todo = todoRepository.findById(id);
        if (todo.isPresent()) {
            todoRepository.delete(todo.get());
            return listAllTasks();
        }
        listAllTasks();
        return null;
    }

    public void deleteAllTasks() {
        todoRepository.deleteAll();
    }
}