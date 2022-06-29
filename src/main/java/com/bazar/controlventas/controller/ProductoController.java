package com.bazar.controlventas.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bazar.controlventas.model.Producto;
import com.bazar.controlventas.service.IProductoService;

@RestController
public class ProductoController {

	@Autowired
	private IProductoService productoService;

	// Crear Producto

	@PostMapping("/productos/crear")
	public ResponseEntity<String> create(@Valid @RequestBody Producto producto) {
		productoService.createProducto(producto);
		return ResponseEntity.status(HttpStatus.CREATED).body("El producto se creó correctamente");
	}

	// Mostrar lista de productos

	@GetMapping("/productos")
	public List<Producto> listaProductos() {
		return productoService.listaProductos();
	}

	// Mostrar Producto por ID

	@GetMapping("/productos/{codigo_producto}")
	public ResponseEntity<Object> getProducto(@PathVariable Long codigo_producto) {
		return ResponseEntity.status(HttpStatus.OK).body(productoService.findProducto(codigo_producto));
	}

	// Editar Producto

	@PutMapping("/productos/editar/{codigo_producto}")
	public ResponseEntity<String> editProducto(@PathVariable Long codigo_producto, @Valid @RequestBody Producto producto){
	    productoService.editProducto(codigo_producto, producto);
		return ResponseEntity.status(HttpStatus.OK).body("El producto se editó correctamente");
	}

	// Eliminar Producto por ID

	@DeleteMapping("/productos/eliminar/{codigo_producto}")
	public ResponseEntity<String> delete( @PathVariable Long codigo_producto) {
		productoService.deleteProducto(codigo_producto);
		return ResponseEntity.status(HttpStatus.OK).body("El producto se eliminó correctamente");
	}

	// Mostrar lista de productos con stock menor a 5

	@GetMapping("/productos/falta_stock")
	public ResponseEntity<Object> stockFaltante() {
		return ResponseEntity.status(HttpStatus.OK).body(productoService.faltanteStock());
	}
	
	// Recuperar Producto eliminado
	
	@PutMapping("/productos/recuperar/{codigo_producto}")
	public ResponseEntity<String> reecuperarProducto(@PathVariable Long codigo_producto){
	    productoService.recuperarProducto(codigo_producto);
		return ResponseEntity.status(HttpStatus.OK).body("El producto se restableció correctamente");
	}
	
	
	
	

}
