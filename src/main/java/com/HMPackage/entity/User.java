package com.HMPackage.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;


	@NotNull
	@Column(name="name")
	private String name;

	@NotNull
	@Column(name="password")
	private String password;

	@CreationTimestamp
	@Column(name = "user_created_time")
	private LocalDateTime createDateTime;

	@UpdateTimestamp
	@Column(name = "user_updated_time")
	private LocalDateTime upDateTime;

    @Column(name="is_active_user",columnDefinition = "integer default 0")
    private int isActive;

	@Column(name = "is_deleted_user",columnDefinition = "integer default 0")
	private int isDelete;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_fk")
	private List<Role> role;

	public User(User user){
		this.name = user.getName();
		this.password = user.getPassword();
		this.role=user.getRole();
	}
}
