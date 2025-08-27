package org.deenwise.app.apis.request;

import lombok.Data;

@Data
public class RefreshTokenRequest {

    private String email;
    private String refresh_token;
}
