package com.example.springApi.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResponseDTO<E> {

    private List<E> dtoList;

    private List<Integer> pageNumList;

    private PageRequestDTO pageRequestDTO;

    private boolean prev;
    private boolean next;

    private int totalCount;
    private int totalPage;
    private int nextPage;
    private int prevPage;
    private int currentPage;

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(List<E> dtoList, PageRequestDTO pageRequestDTO, long totalCount) {
        this.dtoList = dtoList;
        this.pageRequestDTO = pageRequestDTO;
        this.totalCount = (int)totalCount;
//
//        8/10.0를 계산하면 0.8이 됩니다.
//        Math.ceil(0.8)를 호출하면 올림을 수행하여 1이 됩니다.
//        마지막으로 * 10을 하면 최종 결과는 10이 됩니다.
        int end = (int)(Math.ceil(pageRequestDTO.getPage() / 10.0)) * 10;

        int start = end - 9;

        int last = (int)(Math.ceil(totalCount / (double)pageRequestDTO.getSize()));

        // 총 페이지가
        if(end > last){
            end = last;
        }

        this.prev = start > 1;

        this.next = totalCount > end * pageRequestDTO.getSize();

        this.pageNumList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());

        if(prev){
            this.prevPage = start - 1;
        }
        if(next){
            this.nextPage = end + 1;
        }


        this.totalPage = this.pageNumList.size();
        this.currentPage = pageRequestDTO.getPage();

    }




}
