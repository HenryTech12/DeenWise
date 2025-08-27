package org.deenwise.app.apis.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordResponse {

    private String email;
    private String resetToken;
    private String resetLink;
    private boolean mailSent;
    private boolean passwordReset;
}
