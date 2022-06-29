package com.bazar.controlventas.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MontoMayorDTO {

	private Long codigoVenta;
	private double totalVenta;
	private int cantidadProductos;
	private String nombreCliente;
	private String apellidoCliente;

}
