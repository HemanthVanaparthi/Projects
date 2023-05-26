package com.ms.registration.rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.registration.dao.entity.User;
import com.ms.registration.dao.repository.UserRepository;
import com.ms.registration.dto.UserDTO;
import java.util.concurrent.TimeUnit;


@Service
public class UserService {
	
    @Autowired
	private UserRepository userRepository;
    
    @Autowired
    private SSNValidationService ssnValidationService;

    @Autowired
    private PhoneValidationService phoneValidationService;

    @Autowired
    private EmailValidationService emailValidationService;
	/**
	 * Method to add new app user
	 * 
	 * @param userDto
	 * @return
	 */
    
     public Object addUser(UserDTO userDto) throws InterruptedException, ExecutionException  {
    	 
    	 CompletableFuture<Boolean> ssnFuture = CompletableFuture.supplyAsync(() -> ssnValidationService.validate(userDto.getSsn()));
         CompletableFuture<Boolean> phoneFuture = CompletableFuture.supplyAsync(() -> phoneValidationService.validate(userDto.getPhone()));
         CompletableFuture<Boolean> emailFuture = CompletableFuture.supplyAsync(() -> emailValidationService.validate(userDto.getEmail()));

         // Wait for all 3 external services to complete
         CompletableFuture.allOf(ssnFuture, phoneFuture, emailFuture).join();

         // Check if all 3 are successful
         if (ssnFuture.get() && phoneFuture.get() && emailFuture.get()) {
		  User user = new User();
		  user.setName(userDto.getName());
		  user.setSSN(userDto.getSsn());
		  user.setEmail(userDto.getEmail());
		  user.setBirthDate(userDto.getBirthDate());
		  user.setPhone(userDto.getPhone());
		  user.setStatus("Active");
		  user = userRepository.save(user);
		  //user.setId(user.hashCode());
          //userRepository.save(user);
          
          return user;
         }else {
             throw new IllegalArgumentException("One or more fields are invalid");
         }
         
	}
	
    public Optional<User> getDetails(int id) {
    	Optional<User> user = Optional.ofNullable(new User());
    	user = userRepository.findById(id);
    	return user;
    }
    
    public Optional<User> getDetailsByName(String name) {
    	Optional<User> user = Optional.ofNullable(new User());
    	user = userRepository.findByName(name);
    	return user;
    }

	/**
	 * Methos to make Async calls to add User
	 * @param users
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	
	public List<Object> AsyncCall(List<UserDTO> users) throws InterruptedException, ExecutionException {
		List<Object> response = new ArrayList<>();
		ExecutorService executor = Executors.newFixedThreadPool(users.size());
		ArrayList<Callable<Object>> tasks = new ArrayList<>();
		for (UserDTO userDTO : users) {
			//Add input given objects as task in executer
			Callable<Object> userCallable = () -> addUser(userDTO);
			tasks.add(userCallable);
		}
	
		//Execute all tasks in async manner and wait for their completion or timeout which is 60 seconds
		final List<Future<Object>> list = executor.invokeAll(tasks,60,TimeUnit.SECONDS);
		for (Future<Object> future : list) {
			if (future.isDone()) {
				//Add response of thread execution in response
				response.add(future.get());	
			}
		}
		//Close executer
	    executor.shutdown();
		return response;
	}
}
