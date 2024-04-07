package com.example.springApi.service;


import com.example.springApi.domain.Todo;
import com.example.springApi.dto.PageRequestDTO;
import com.example.springApi.dto.PageResponseDTO;
import com.example.springApi.dto.TodoDTO;
import com.example.springApi.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{

    private  final ModelMapper modelMapper;
    private final TodoRepository todoRepository;

    @Override
    public Long register(TodoDTO todoDTO) {
        Todo todo = modelMapper.map(todoDTO, Todo.class);
        Todo saveTodo = todoRepository.save(todo);
        return saveTodo.getId();
    }
    @Override
    public void modify(TodoDTO todoDTO) {

        Optional<Todo> result = todoRepository.findById(todoDTO.getId());

        Todo todo = result.orElseThrow();

        todo.changeTitle(todoDTO.getTitle());
        todo.changeDueDate(todoDTO.getDueDate());
        todo.changeComplete(todoDTO.isCompleted());

        todoRepository.save(todo);

    }

    @Override
    public void remove(Long tno) {

        todoRepository.deleteById(tno);

    }
    @Override
    public TodoDTO get(Long tno) {
        java.util.Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();
        TodoDTO dto = modelMapper.map(todo, TodoDTO.class);
        return dto;
    }

    @Override
    public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO) {


        PageRequest pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("id").descending()
        );

        Page<Todo> result = todoRepository.findAll(pageable);

        List<TodoDTO> dtoList = result.getContent()
                .stream()
                .map(todo -> modelMapper.map(todo, TodoDTO.class))
                .collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        PageResponseDTO<TodoDTO> responseDTO = PageResponseDTO.<TodoDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(totalCount)
                .build();


        return responseDTO;
    }
}
