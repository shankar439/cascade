package com.HMPackage.DTO;

import lombok.Data;

@Data
public class UserRoleDTO {
    private Long id;
    private String token;
    private String name;
    private String password;
}
