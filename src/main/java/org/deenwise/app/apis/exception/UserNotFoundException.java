package org.deenwise.app.apis.exception;

import java.util.Objects;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message) {
        System.out.println(message);
    }
}
