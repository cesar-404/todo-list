package com._4.todolist.dtos;

public record TodoDto(String name,
                      String description,
                      boolean done,
                      int priority) {
}