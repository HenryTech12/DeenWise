package org.deenwise.app.apis.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiErrorResponse {

    private String message;
    private String timeStamp;

}
