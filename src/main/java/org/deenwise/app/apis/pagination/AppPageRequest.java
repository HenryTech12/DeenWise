package org.deenwise.app.apis.pagination;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
public class AppPageRequest {

    private int pageNo = 1;
    private int pageSize = 10;
    private Sort.Direction sortDir = Sort.Direction.ASC;
    private String sortBy;


    public Pageable getPagination() {

        return PageRequest.of(pageNo,pageSize,sortDir,sortBy);
    }
}
