package com.HMPackage.serviceImplementation;

import java.util.*;
import com.HMPackage.DTO.JWTtokenDTO;
import com.HMPackage.DTO.RoleDTO;
import com.HMPackage.DTO.UserRoleDTO;
import com.HMPackage.entity.Role;
import com.HMPackage.entity.UserRole;
import com.HMPackage.repository.RoleRepository;
import com.HMPackage.repository.UserRoleRepository;
import org.springframework.data.domain.Pageable;
import com.HMPackage.baseResponse.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.HMPackage.DTO.UserDTO;
import com.HMPackage.entity.User;
import com.HMPackage.repository.UserRepository;
import com.HMPackage.service.UserServiceInterface;
import static com.HMPackage.util.JwtUtils.generateToken;

@Service
public class UserServiceImpl implements UserServiceInterface{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;


	private void saveRole(List<RoleDTO> roleDTO, User user) {
		try {
			List<UserRole> userRole = new LinkedList<>();
			if (Objects.nonNull(roleDTO) && roleDTO.size() > 0) {
				roleDTO.stream().forEachOrdered(role -> {
					Role role1 = roleRepository.findById(role.getRoleId()).orElseThrow(() -> new RuntimeException("not found"));
					UserRole userRoleObj = new UserRole();
					userRoleObj.setUser(user);
					userRoleObj.setRole(role1);
					userRole.add(userRoleObj);
				});
				userRoleRepository.saveAll(userRole);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public UserRoleDTO login(UserRoleDTO userRoleDTO) {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		List<Role> roles = new LinkedList<>();
		try {
			Optional<User> user= userRepository.findByName(userRoleDTO.getName());
			boolean status = bcrypt.matches(userRoleDTO.getPassword(), user.get().getPassword());
			if (user.isPresent() && status == true) {
				List<UserRole> userRole = userRoleRepository.findByUserId(user.get().getId());
				userRole.stream().forEachOrdered(userRoleobj -> {
					Role role = userRoleobj.getRole();
					roles.add(role);
				});
				String Token = generateToken("user", user.get().getId(), user.get().getName(), roles);
				userRoleDTO.setName(user.get().getName());
				userRoleDTO.setUserRoleId(user.get().getId());
				userRoleDTO.setUserRoles(user.get().getUserRole());
				userRoleDTO.setToken(Token);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userRoleDTO;
	}

	public UserDetails loadByUserName(String name) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByName(name);
		List<Role> roles = new LinkedList<>();
		if (user == null) {
			throw new RuntimeException("NOT FOUND");
		}
		else{
			List<UserRole> userRoles = userRoleRepository.findByUserId(user.get().getId());
			userRoles.stream().forEachOrdered(userRole -> {
				Role role = userRole.getRole();
				roles.add(role);
			});
			return new org.springframework.security.core.userdetails.User(user.get().getName(), user.get().getPassword(),
					getAuthority(roles));
		}
	}

	private List getAuthority(List<Role> role){
		List authorities=new ArrayList();
		role.stream().forEachOrdered(roleget -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" +roleget.getRoleName()));
		});
		return authorities;
	}

	@Override
	public User addUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
		user.setPassword(bCrypt.encode(userDTO.getPassword()));
		saveRole(userDTO.getRoleDTO(),user);
		userRepository.save(user);
		return user;
	}

	@Override
	public Optional<User> getUserbyId(Long id) {
	    Optional<User> user=userRepository.findById(id);
        return user;
	}

	@Override
	public Optional<User> updatebyId(UserDTO userDTO) {
		   Optional<User> user = userRepository.findById(userDTO.getId());
	        if (user.isPresent())
	        {
	        	user.get().setId(userDTO.getId());
	        	user.get().setName(userDTO.getName());
	        	BCryptPasswordEncoder bcrupt=new BCryptPasswordEncoder();
	        	user.get().setPassword(bcrupt.encode(userDTO.getPassword()));
	            userRepository.save(user.get());
	        }
	        else
	        {
	            throw new RuntimeException("Enter A Valid UserId");
	        }
	        return user;
	}

	@Override
	public Optional<User> deletesoft(UserDTO userDTO) {
        Optional<User> user = userRepository.findById(userDTO.getId());
        if (user.isPresent())
        {
        	user.get().setIsDelete(1);
            userRepository.save(user.get());

        }
        else
        {
            throw new RuntimeException("data not found");
        }
        return user;
	}

	@Override
	public PageResponse<User> pageByUserName(int pageno,int pageSize,String name){
		Pageable paging= PageRequest.of(pageno,pageSize);
		Page<User> user = userRepository.searchAllByNameLike("%" + name + "%", paging);
		PageResponse pageResponse=new PageResponse();
		pageResponse.setResponse(user);
		pageResponse.setPages(user.getTotalPages());
		return pageResponse;
	}
}
