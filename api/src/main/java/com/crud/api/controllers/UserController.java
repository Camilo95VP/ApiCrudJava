package com.crud.api.controllers;


import com.crud.api.persistence.entities.UserEntity;
import com.crud.api.services.IUserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;




@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	IUserService userService;

	@Transactional
	@PostMapping("/create")
	public ResponseEntity<?> createUser(@Valid @RequestBody UserEntity user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {

			Map<String, Object> errorResponse = new HashMap<>();
			Map<String, Object> errores = new HashMap<>();
			bindingResult.getFieldErrors().forEach(error -> {
				errores.put("description", error.getDefaultMessage());
				errores.put("type", "ERROR");
			});

			errorResponse.put("errors", errores);
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}

		try {
			UserEntity createdUser = userService.createUser(user);
			Map<String, Object> successResponse = new HashMap<>();
			successResponse.put("message", "Usuario creado exitosamente");
			successResponse.put("type", "SUCCESS");

			return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@GetMapping("/get-all")
	public ResponseEntity<List<UserEntity>> getAllUsers() {
		try {
			List<UserEntity> users = userService.getAllUsers();
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@GetMapping("get/{userId}")
	public ResponseEntity<UserEntity> getUserById(@PathVariable Long userId) {
		try {
			Optional<UserEntity> user = userService.getUserById(userId);
			return user.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PutMapping("/update/{userId}")
	public ResponseEntity<UserEntity> updateUser(@PathVariable Long userId, @RequestBody UserEntity newUser) {
		try {
			UserEntity updatedUser = userService.updateUser(userId, newUser);
			if (updatedUser != null) {
				return new ResponseEntity<>(updatedUser, HttpStatus.OK);
			}
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<HashMap<String, String>> deleteUser(@PathVariable Long userId) {
		try {
			HashMap<String, String> response = userService.deleteUser(userId);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}