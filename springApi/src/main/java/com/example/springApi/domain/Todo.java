package com.example.springApi.domain;

import com.example.springApi.dto.TodoDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "todo")
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "todo_seq")
    @SequenceGenerator(name = "todo_seq", sequenceName = "todo_seq")
    @Column(name = "todo_id")
    private Long id;

    private String title;

    private String writer;

    private boolean complete;




    public static Todo createTodo(TodoDTO todoDTO){
        return Todo.builder()
                .title(todoDTO.getTitle())
                .writer(todoDTO.getWriter())
                .complete(todoDTO.isComplete())
                .dueDate(todoDTO.getDueDate())
                .build();

    }

    private LocalDate dueDate;
    public void changeTitle(String title){
        this.title = title;
    }

    public void changeComplete(boolean complete){
        this.complete = complete;
    }

    public void changeDueDate(LocalDate dueDate){
        this.dueDate = dueDate;
    }

}
