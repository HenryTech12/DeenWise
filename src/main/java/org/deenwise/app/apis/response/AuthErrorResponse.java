package org.deenwise.app.apis.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthErrorResponse {

    private String message;
    private String timeStamp;
    private boolean authenticated;
}
