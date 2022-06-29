package com.bazar.controlventas.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserClienteDTO {

	private String userName;
	private String password;

	public UserClienteDTO() {

	}

	public UserClienteDTO(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

}
