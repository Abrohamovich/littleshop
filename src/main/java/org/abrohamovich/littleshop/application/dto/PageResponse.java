package org.abrohamovich.littleshop.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isLast;

    public static <T> PageResponse<T> fromSpringPage(Page<T> springPage) {
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.setContent(springPage.getContent());
        pageResponse.setPageNumber(springPage.getNumber());
        pageResponse.setPageSize(springPage.getSize());
        pageResponse.setTotalElements(springPage.getTotalElements());
        pageResponse.setTotalPages(springPage.getTotalPages());
        pageResponse.setLast(springPage.isLast());
        return pageResponse;
    }
}
