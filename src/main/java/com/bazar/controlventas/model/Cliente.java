package com.bazar.controlventas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "clientes")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long idCliente;

	@NotNull(message = "No puede ser nulo")
	@Size(min = 4, max = 50, message = "No puede estar vacio y debe contener entre 4 y 50 caracteres")
	private String nombre;

	@NotNull(message = "No puede ser nulo")
	@Size(min = 4, max = 50, message = "No puede estar vacio y debe contener entre 4 y 50 caracteres")
	private String apellido;

	@NotNull(message = "No puede ser nulo")
	@Size(min = 8, max = 8, message = "No puede estar vacio y debe contener 8 caracteres")
	@Column(unique = true)
	private String dni;
	
	private boolean eliminado;
	
	@NotNull(message = "No puede ser nulo")
	@Size(min = 4, max = 15, message = "No puede estar vacio y debe contener entre 4 y 15 caracteres")
	private String userName;
	
	@NotNull(message = "No puede ser nulo")
	@Size(min = 6 , message = "No puede estar vacio y debe contener un minio de 6 caracteres")
	private String password;
	
	private boolean conectado;

	public Cliente() {
	}

	public Cliente(Long idCliente, String nombre, String apellido, String dni, String userName, String password) {
		this.idCliente = idCliente;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.eliminado = false;
		this.userName = userName;
		this.password = password;
		this.conectado = false;
	}

}
