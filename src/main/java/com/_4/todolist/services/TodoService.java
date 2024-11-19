package com._4.todolist.services;

import com._4.todolist.models.Todo;
import com._4.todolist.repositories.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public List<Todo> createTask(Todo todo) {
        todoRepository.save(todo);
        return listAll();
    }

    public List<Todo> listAll() {
        Sort sort = Sort.by("priority")
                .descending()
                .and(Sort.by("name")
                        .ascending());
        return todoRepository.findAll(sort);
    }

    public Todo getById(Long taskId) {
        var foundTask = todoRepository.findById(taskId);
        if (foundTask.isPresent()) {
            return foundTask.get();
        }
        throw new NoSuchElementException();
    }

    public List<Todo> updateTask(Todo todo) {
        todoRepository.save(todo);
        return listAll();
    }

    public List<Todo> deleteTask(Long id) {
        var todoOptional = todoRepository.findById(id);
        if (todoOptional.isPresent()) {
            todoRepository.delete(todoOptional.get());
            return listAll();
        }
        throw new NoSuchElementException();
    }
}
