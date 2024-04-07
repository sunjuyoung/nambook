package com.example.springApi.controller;

import com.example.springApi.dto.PageRequestDTO;
import com.example.springApi.dto.PageResponseDTO;
import com.example.springApi.dto.TodoDTO;
import com.example.springApi.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService todoService;


    private final TodoService service;

    @GetMapping("/{tno}")
    public TodoDTO get(@PathVariable(name ="tno") Long tno) {

        return service.get(tno);
    }

    @GetMapping("/list")
    public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO ) {

        log.info(pageRequestDTO);

        return service.list(pageRequestDTO);
    }

    @PostMapping("/")
    public Map<String, Long> register(@RequestBody TodoDTO todoDTO){

        log.info("TodoDTO: " + todoDTO);

        Long tno = service.register(todoDTO);

        return Map.of("TNO", tno);
    }

    @PutMapping("/{tno}")
    public Map<String, String> modify(
            @PathVariable(name="tno") Long tno,
            @RequestBody TodoDTO todoDTO) {

        todoDTO.setId(tno);

        log.info("Modify: " + todoDTO);

        service.modify(todoDTO);

        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/{tno}")
    public Map<String, String> remove( @PathVariable(name="tno") Long tno ){

        log.info("Remove:  " + tno);

        service.remove(tno);

        return Map.of("RESULT", "SUCCESS");
    }
}
