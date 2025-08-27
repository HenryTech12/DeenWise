package org.deenwise.app.apis.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

    private String email;
    private String access_token;
    private String refresh_token;
    private String expiry_time;
    private String refresh_expiry_time;
    private String userRole;
}
