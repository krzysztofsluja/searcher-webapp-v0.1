package org.sluja.searcher.webapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class ApiResponse<T> {

    private T data;
    private List<String> errors;

}
