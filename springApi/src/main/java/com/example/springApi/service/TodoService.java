package com.example.springApi.service;

import com.example.springApi.dto.PageRequestDTO;
import com.example.springApi.dto.PageResponseDTO;
import com.example.springApi.dto.TodoDTO;

public interface TodoService {

    Long register(TodoDTO todoDTO);

     TodoDTO get(Long tno) ;

     void modify(TodoDTO todoDTO) ;

        void remove(Long tno) ;

    PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO);
}
