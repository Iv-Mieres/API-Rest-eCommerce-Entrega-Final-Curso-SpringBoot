package com.bazar.controlventas.dto;

import java.time.LocalDate;
import java.util.List;

import com.bazar.controlventas.model.Producto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditarVentasDTO {

	private List<Producto> listaProductosVenta;
	private LocalDate fechaVenta;

	public EditarVentasDTO() {
	}

	public EditarVentasDTO(List<Producto> listaProductosVenta, LocalDate fechaVenta) {
		this.listaProductosVenta = listaProductosVenta;
		this.fechaVenta = fechaVenta;
	}

}
