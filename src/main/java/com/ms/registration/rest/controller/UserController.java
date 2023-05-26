package com.ms.registration.rest.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ms.registration.dao.entity.User;
import com.ms.registration.dto.UserDTO;
import com.ms.registration.rest.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Validated
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE , method=RequestMethod.POST)
	@ResponseBody
	public Object register(HttpServletRequest httpReq, @RequestBody @Valid UserDTO userDto) throws InterruptedException, ExecutionException {
		return userService.addUser(userDto);
	}
	
	@RequestMapping(value = "/register/{idOrName}", method=RequestMethod.GET)
	public User getUserDetails(@PathVariable String idOrName) {
		User user;
		try {
			int id = Integer.parseInt(idOrName);
			user = userService.getDetails(id);
		} catch (NumberFormatException e) {
			user = userService.getDetailsByName(idOrName);
		}
		
		return user;
	}
	
	@RequestMapping(value = "/register/async", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE , method=RequestMethod.POST)
	@ResponseBody
	public List<Object> registerAsync(HttpServletRequest httpReq, @RequestBody @Valid List<UserDTO> users) {
		try {
			return userService.AsyncCall(users);
		} catch (InterruptedException e) {
			//TODO Send customized error response
			e.printStackTrace();
		} catch (ExecutionException e) {
			//TODO Send customized error response
			e.printStackTrace();
		}
		return null;
	}

}
