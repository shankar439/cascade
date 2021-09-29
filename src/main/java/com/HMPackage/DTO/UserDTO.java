package com.HMPackage.DTO;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
	private long id;
	private String name;
	private String password;
	private List<RoleDTO>roleDTO;
}
