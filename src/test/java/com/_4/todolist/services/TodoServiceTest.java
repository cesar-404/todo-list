package com._4.todolist.services;

import com._4.todolist.dtos.TodoDto;
import com._4.todolist.models.Todo;
import com._4.todolist.repositories.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @InjectMocks
    private TodoService todoService;

    @Mock
    private TodoRepository todoRepository;

    private TodoDto todoDto;

    @BeforeEach
    void setUp() {
        todoDto = new TodoDto("Test", "Test desc", false, 1);
    }

    @Test
    @DisplayName("Should create a task and return a list of tasks.")
    void shouldCreateATaskAndReturnAListOfTasks() {

        var todo = new Todo();
        BeanUtils.copyProperties(todoDto, todo);
        assertAll("Todo properties should match the DTO",
                () -> assertEquals(todoDto.name(), todo.getName()),
                () -> assertEquals(todoDto.description(), todo.getDescription()),
                () -> assertEquals(todoDto.done(), todo.isDone()),
                () -> assertEquals(todoDto.priority(), todo.getPriority())
        );

        when(todoRepository.save(todo)).thenReturn(todo);
        var expectedTodo = todoRepository.save(todo);
        verify(todoRepository, times(1)).save(todo);
        assertEquals(todoDto.name(), expectedTodo.getName());
        assertEquals(todoDto.description(), expectedTodo.getDescription());
        assertEquals(todoDto.done(), expectedTodo.isDone());
        assertEquals(todoDto.priority(), expectedTodo.getPriority());

        List<Todo> todoList = List.of(todo);
        when(todoRepository.findAll(any(Sort.class))).thenReturn(todoList);

        List<Todo> result = todoService.createTask(todoDto);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertAll("Todo in result list should match the expected properties",
                () -> assertEquals(todo.getName(), result.get(0).getName()),
                () -> assertEquals(todo.getDescription(), result.get(0).getDescription()),
                () -> assertEquals(todo.isDone(), result.get(0).isDone()),
                () -> assertEquals(todo.getPriority(), result.get(0).getPriority())
        );
    }
}
