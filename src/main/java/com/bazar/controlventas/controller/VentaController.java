package com.bazar.controlventas.controller;

import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bazar.controlventas.dto.EditarVentasDTO;
import com.bazar.controlventas.model.Venta;
import com.bazar.controlventas.service.IVentaService;

@RestController
public class VentaController {

	@Autowired
	private IVentaService ventaService;

	// Crear Venta 

	@PostMapping("/ventas/crear")
	public ResponseEntity<String> createVenta(@Valid @RequestBody Venta venta) {
		ventaService.createVenta(venta);
		return ResponseEntity.status(HttpStatus.CREATED).body("La venta se realizó correctamente");
	}

	// Mostrar Venta por ID

	@GetMapping("/ventas/traer/{id_venta}")
	public ResponseEntity<Object> getVenta(@PathVariable Long id_venta) {
		return ResponseEntity.status(HttpStatus.OK).body(ventaService.findVenta(id_venta));
	}

	// Mostrar todas las ventas

	@GetMapping("/ventas")
	public ResponseEntity<List<Venta>> listaVentas() {
		return ResponseEntity.status(HttpStatus.OK).body(ventaService.listaVentas());
	}

	// Mostrar monto total de ventas y las ventas totales de determinada fecha

	@GetMapping("/ventas/{fecha}")
	public ResponseEntity<Object> ventasxFecha(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate fecha) {
		return ResponseEntity.status(HttpStatus.OK).body(ventaService.VentasxFecha(fecha));
	}

	// Mostrar lista de Productos por codigo de Venta

	@GetMapping("/ventas/productos/{codigo_venta}")
	public ResponseEntity<Object> listaProductos(@PathVariable Long codigo_venta) {
		return ResponseEntity.status(HttpStatus.OK).body(ventaService.ventaProductosDTO(codigo_venta));
	}

	// Mostrar datos DTO por monto más alto

	@GetMapping("/ventas/mayor_venta")
	public ResponseEntity<Object> montoAltoDTO() {
		return ResponseEntity.status(HttpStatus.OK).body(ventaService.montoMayorDTO());
	}

	// Editar Ventas

	@PutMapping("/cliente/editar/{codigo_venta}")
	public ResponseEntity<String> editVenta(@PathVariable Long codigo_venta, @Valid @RequestBody EditarVentasDTO ventaDTO) {
		ventaService.editVenta(codigo_venta, ventaDTO);
		return ResponseEntity.status(HttpStatus.OK).body("La venta se actualizó correctamente");
	}

	// Eliminar Ventas por ID

	@DeleteMapping("/cliente/eliminar/{codigo_venta}")
	public ResponseEntity<String> deleteVenta(@PathVariable Long codigo_venta) {
		ventaService.deleteVenta(codigo_venta);
		return ResponseEntity.status(HttpStatus.OK).body("La Venta se eliminó correctamente");
	}
}
