package com.example.springApi.repository;

import com.example.springApi.domain.Todo;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;


@SpringBootTest
@Log4j2
class TodoRepositoryTest {


    @Autowired
    private TodoRepository todoRepository;


    @Test
    void test(){

        for(int i = 0; i < 100; i++){
            Todo todo = Todo.builder()
                    .title("title"+i)
                    .writer("writer"+i)
                    .dueDate(LocalDate.of(2021, 8, 1))
                    .build();
            todoRepository.save(todo);
        }
    }
    
    @Test
    void test2(){

        PageRequest pageable = PageRequest.of(0, 10, Sort.by("id").descending());


        Page<Todo> result = todoRepository.findAll(pageable);

        log.info(result.getTotalPages());
        log.info(result.getTotalElements());


        result.getContent().stream().forEach(todo -> log.info(todo));

    }
}