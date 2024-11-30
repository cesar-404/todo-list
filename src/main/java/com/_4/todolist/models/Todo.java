package com._4.todolist.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_todo")
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "done")
    private boolean done;

    @Column(name = "priority")
    private int priority;

}