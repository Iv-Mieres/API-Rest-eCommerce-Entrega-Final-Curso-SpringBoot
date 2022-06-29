package com.bazar.controlventas.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VentasxFechaDTO {

	private LocalDate fechaVenta;
	private Long  numeroTotalVenta;
	private double montoTotal;
}
