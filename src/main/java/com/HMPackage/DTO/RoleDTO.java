package com.HMPackage.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class RoleDTO {
    private Long roleId;
    private String roleName;
    private int isActive;
    private int isDelete;
}
