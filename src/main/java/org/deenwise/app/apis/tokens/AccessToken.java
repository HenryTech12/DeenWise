package org.deenwise.app.apis.tokens;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccessToken {
    private String token;
    private String expiry_time;
}
