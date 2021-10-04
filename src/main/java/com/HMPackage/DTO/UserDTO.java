package com.HMPackage.DTO;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDTO {
	private long id;
	private String name;
	private String password;
	private int isActive;
	private int isDelete;
	private List<RoleDTO>roleDTO;
}
