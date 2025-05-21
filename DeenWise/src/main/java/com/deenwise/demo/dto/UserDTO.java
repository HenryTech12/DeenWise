package com.deenwise.demo.dto;

import lombok.Data;

@Data
public class UserDTO
{
	private String name;
	private String email;
	private String password;
	private String cpassword;
	private String currentPassword;
	private String role;
}
