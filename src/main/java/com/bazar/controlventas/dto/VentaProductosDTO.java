package com.bazar.controlventas.dto;

import java.util.List;

import com.bazar.controlventas.model.Producto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class VentaProductosDTO {
	
	private Long codigoVenta;
	
	@JsonIgnoreProperties("listaVentas")
	private List<Producto> listaProductosVenta;

}
