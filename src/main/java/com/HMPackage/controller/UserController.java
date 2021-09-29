package com.HMPackage.controller;

import java.util.Optional;
import com.HMPackage.DTO.JWTtokenDTO;
import com.HMPackage.baseResponse.PageResponse;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.HMPackage.DTO.UserDTO;
import com.HMPackage.baseResponse.BaseResponse;
import com.HMPackage.entity.User;
import com.HMPackage.service.UserServiceInterface;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserServiceInterface userServiceInterface;

	@GetMapping("/login")
    public BaseResponse login(@RequestBody JWTtokenDTO jWTtokenDTO){
	    BaseResponse baseResponse = null;
	    baseResponse = BaseResponse.builder().Data(userServiceInterface.login(jWTtokenDTO)).build();
	    return baseResponse;
    }

    @PostMapping("/create")
    public BaseResponse<User> addUser(@RequestBody UserDTO userDTO)   {
    	BaseResponse<User> baseResponse = null;
        baseResponse = BaseResponse.<User>builder().Data(userServiceInterface.addUser(userDTO)).build();
        return baseResponse;
    }

    @GetMapping("/get/{id}")
    @Authorization(value="Bearer")
    public BaseResponse<Optional<User>> getUserById(@PathVariable Long id){
    	BaseResponse<Optional<User>> baseResponse = null;
        baseResponse = BaseResponse.<Optional<User>>builder().Data(userServiceInterface.getUserbyId(id)).build();
    	return baseResponse;
    }

    @PutMapping("/update")
    @Authorization(value="Bearer")
    public BaseResponse<Optional<User>> update(@RequestBody UserDTO userDTO){
    	BaseResponse<Optional<User>> baseResponse = null;
        baseResponse = BaseResponse.<Optional<User>>builder().Data(userServiceInterface.updatebyId(userDTO)).build();
    	return baseResponse;
    }

    @PutMapping("/softdelete")
    @Authorization(value="Bearer")
    public BaseResponse deletesoft(@RequestBody UserDTO userDTO){
    	BaseResponse<Optional<User>> baseResponse = null;
        baseResponse = BaseResponse.<Optional<User>>builder().Data(userServiceInterface.deletesoft(userDTO)).build();
    	return baseResponse;
    }

    @GetMapping("/pagination/{pageno}/{pageSize}/{name}")
    @Authorization(value="Bearer")
    private PageResponse<User> pageByUserName(@PathVariable int pageno,@PathVariable int pageSize,@PathVariable String name){
        return userServiceInterface.pageByUserName(pageno,pageSize,name);
    }
}
