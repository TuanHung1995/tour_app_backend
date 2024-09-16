package com.springboot.mvc.tour_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourResponse {

    private List<TourDto> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;

}
