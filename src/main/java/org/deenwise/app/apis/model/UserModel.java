package org.deenwise.app.apis.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.deenwise.app.apis.dto.UserRole;

@Data
@Entity
public class UserModel {

    @Id
    private Long id;
    private String fullname;
    private String email;
    private UserRole userRole;
    private String password;
}
