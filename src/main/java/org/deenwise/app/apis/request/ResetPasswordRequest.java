package org.deenwise.app.apis.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {

    private String oldPassword;
    private String newPassword;
}
