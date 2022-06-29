package com.bazar.controlventas.service;

import java.time.LocalDate;
import java.util.List;

import com.bazar.controlventas.dto.EditarVentasDTO;
import com.bazar.controlventas.dto.MontoMayorDTO;
import com.bazar.controlventas.dto.VentaProductosDTO;
import com.bazar.controlventas.dto.VentasxFechaDTO;
import com.bazar.controlventas.model.Venta;

public interface IVentaService {

	public Venta createVenta(Venta venta);
	
	public List<Venta> listaVentas();
	
	public Venta findVenta(Long codigoVenta);
	
	public void editVenta(Long codigoVenta,EditarVentasDTO venta);
	
	public void deleteVenta(Long codigoVenta);
	
	public VentasxFechaDTO VentasxFecha(LocalDate fecha);
	
	public VentaProductosDTO ventaProductosDTO(Long codigoVenta);

	public MontoMayorDTO montoMayorDTO();
	

}
