//package com.HMPackage.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import javax.persistence.*;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "user_role")
//public class UserRole {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "user_role_id")
//    private Long userRoleId;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JsonIgnore
//    @JoinColumn(name = "id_fk")
//    private User user;
//
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "roleid_fk")
//    private Role role;
//}
