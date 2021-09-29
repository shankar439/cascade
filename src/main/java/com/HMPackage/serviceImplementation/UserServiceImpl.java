package com.HMPackage.serviceImplementation;

import java.util.Optional;
import com.HMPackage.DTO.JWTtokenDTO;
import org.springframework.data.domain.Pageable;
import com.HMPackage.baseResponse.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

	@Override
	public JWTtokenDTO login(JWTtokenDTO jWTtokenDTO) {
		Optional<User> user= userRepository.findByName(jWTtokenDTO.getName());
		try {
			if (user.isPresent()) {
				BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
				boolean chek = bcrypt.matches(jWTtokenDTO.getPassword(), user.get().getPassword());
				if (user.isPresent()) {
					String jwtt = generateToken( "user",user.get().getId(), user.get().getName());
					jWTtokenDTO.setToken(jwtt);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jWTtokenDTO;
	}

	public UserDetails loadByUserId(String id) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByName(id);
		Optional<User> obj = Optional.ofNullable(user).orElseThrow(
						() -> new UsernameNotFoundException("User Name Not Found"))
				.map(UserDetailImpl::new);
		if (obj.isPresent()) {
			return (UserDetails) obj.get();
		}
		return null;
	}

	@Override
	public User addUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
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
	        	user.get().setPassword(userDTO.getPassword());
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
