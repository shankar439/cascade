package com.HMPackage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "role")
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @NotNull
    @Column(name = "role_name")
    private String roleName;

    @Column(name = "is_active", columnDefinition = "integer default 0")
    private int isActive;

    @Column(name = "is_delete", columnDefinition = "integer default 0")
    private int isDelete;
}
