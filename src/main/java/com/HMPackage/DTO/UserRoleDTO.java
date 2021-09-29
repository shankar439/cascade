package com.HMPackage.DTO;

import com.HMPackage.entity.UserRole;
import lombok.Data;

import java.util.List;

@Data
public class UserRoleDTO {
    private Long userRoleId;
    private List<UserRole>userRoles;
}
