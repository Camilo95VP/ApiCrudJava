package com.crud.api.persistence.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
@Entity
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull(message = "El campo 'firstName' no puede estar en blanco")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Nombre inválido, solo se permiten caracteres alfabéticos")
	private String firstName;

	@NotNull(message = "El campo 'lastName' no puede estar en blanco")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Apellido inválido, solo se permiten caracteres alfabéticos")
	private String lastName;

	@Email(message = "El campo 'email' debe ser una dirección de correo electrónico válida")
	private String email;

	@NotNull(message = "El campo 'telephone' no puede estar en blanco")
	@Pattern(regexp = "^[0-9]+$", message = "El campo 'telephone' no es válido")
	private String telephone;

	@NotBlank(message = "El campo 'address' no puede estar en blanco")
	private String address;

	@NotBlank(message = "El campo 'city' no puede estar en blanco")
	private String city;


}