package org.deenwise.app.apis.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private Long id;
    private String fullname;
    private String email;
    private String role;
}
