package com.deenwise.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserModel
{
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Column(unique = true)
	private String email;
	private String password;
	private String role;
}
