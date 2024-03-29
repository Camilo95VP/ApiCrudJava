package com.crud.api.services.businesslogic;


import com.crud.api.persistence.entities.UserEntity;
import com.crud.api.persistence.repositories.UserRepository;
import com.crud.api.services.IUserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements IUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Transactional
	@Override
	public UserEntity createUser(UserEntity user) {
		try {
			return userRepository.save(user);
		} catch (Exception e) {
			LOGGER.error("Error while creating user: {}", e.getMessage());
			throw new RuntimeException("Error creating user");
		}
	}


	@Override
	public List<UserEntity> getAllUsers() {
		try {
			return userRepository.findAll();
		} catch (Exception e) {
			LOGGER.error("Error while fetching all users: {}", e.getMessage());
			throw new RuntimeException("Error fetching users");
		}
	}


	@Override
	public Optional<UserEntity> getUserById(Long userId) {
		try {
			return userRepository.findById(userId);
		} catch (Exception e) {
			LOGGER.error("Error while fetching user by ID: {}", e.getMessage());
			throw new RuntimeException("Error fetching user by ID");
		}
	}


	@Override
	public UserEntity updateUser(Long userId, UserEntity newUser) {
		try {
			UserEntity existingUser = userRepository.findById(userId).orElse(null);
			if (existingUser != null) {
				existingUser.setFirstName(newUser.getFirstName());
				existingUser.setLastName(newUser.getLastName());
				existingUser.setEmail(newUser.getEmail());
				existingUser.setTelephone(newUser.getTelephone());
				existingUser.setAddress(newUser.getAddress());
				existingUser.setCity(newUser.getCity());

				return userRepository.save(existingUser);
			}
			throw new RuntimeException("User not found");
		} catch (Exception e) {
			LOGGER.error("Error while updating user: {}", e.getMessage());
			throw new RuntimeException("Error updating user");
		}
	}


	@Override
	public HashMap<String, String> deleteUser(Long userId) {
		try {
			HashMap<String, String> response = new HashMap<>();
			response.put("message", "User deleted succesfully!");
			userRepository.deleteById(userId);
			return response;
		} catch (Exception e) {
			LOGGER.error("Error while deleting user: {}", e.getMessage());
			throw new RuntimeException("Error deleting user");
		}
	}
}
