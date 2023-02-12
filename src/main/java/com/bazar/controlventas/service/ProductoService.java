package com.bazar.controlventas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazar.controlventas.exceptions.BadRequestException;
import com.bazar.controlventas.exceptions.StatusOkException;
import com.bazar.controlventas.model.Producto;
import com.bazar.controlventas.repository.IProductoRepository;
import com.bazar.controlventas.repository.IRubroRepository;

@Service
public class ProductoService implements IProductoService {

	@Autowired
	private IProductoRepository productoRepository;
	
	@Autowired
	private IRubroRepository rubroRepository;

	// Crear Productos

	@Override
	public Producto createProducto(Producto prod) {
		
		if (productoRepository.existsByNombre(prod.getNombre())) {
			throw new BadRequestException("El nombre " + prod.getNombre() + " ya existe. Ingrese un nuevo nombre");
		}
		if (prod.getUnRubro().getIdRubro() == null) {
			throw new BadRequestException("El campo idRubro no puede estar vacio. Ingrese un id válido");
		}
		if (!rubroRepository.existsById(prod.getUnRubro().getIdRubro())) {
			throw new BadRequestException("El rubro seleccionado con id " + prod.getUnRubro().getIdRubro()
											+ " no existe. Ingrese un id válido para el campo unRubro");
		}
			return productoRepository.save(prod);
	}

	// Mostrar Lista de Productos

	@Override
	public List<Producto> listaProductos() {
		
			List<Producto> listaProductos = productoRepository.findAll();
			List<Producto> mostrarProductos = new ArrayList<>();
			
		for (Producto producto : listaProductos) {
			if(producto.isEliminado() == false) {
				mostrarProductos.add(producto);
			}
		}
			return mostrarProductos;
	}

	// Mostrar Producto por ID

	@Override
	public Producto findProducto(Long idProducto) {
		
			Producto productoBD = productoRepository.findById(idProducto).orElse(null);
		
		if (!productoRepository.existsById(idProducto) || productoBD.isEliminado() == true ) {
			throw new BadRequestException("El producto con id " + idProducto + " no existe. Ingrese un id válido");
		}
			return productoBD;
	}

	// Editar Producto por ID

	@Override
	public void editProducto(Long idProducto, Producto producto) {

			Producto productoBD = productoRepository.findById(idProducto).orElse(null);
			
		if (!productoRepository.existsById(idProducto) || productoBD.isEliminado() == true) {
			throw new BadRequestException("El producto con id " + idProducto + " no existe. Ingrese un id válido");
		}
		if(!productoRepository.existsByNombre(producto.getNombre()) || productoBD.getNombre().equals(producto.getNombre())) {
			productoBD = producto;
			productoBD.setIdProducto(idProducto);
		}
		else {
			throw new BadRequestException("El producto con nombre " + producto.getNombre() + " ya existe."
																	+ " Ingrese un nombre válido");
		}	
			productoRepository.save(productoBD);
	}

	// Eliminar Producto por ID

	@Override
	public void deleteProducto(Long idProducto) {
		if (!productoRepository.existsById(idProducto)) {
			throw new BadRequestException("El id " + idProducto + " no existe. Ingrese un id válido");
		}
			Producto productoBD = this.findProducto(idProducto);
			productoBD.setEliminado(true);
			productoBD.setIdProducto(idProducto);
		
			productoRepository.save(productoBD);	
	}

	// Mostrar Productos con Stock menor a 5

	@Override
	public List<Producto> faltanteStock() {
		
			List<Producto> listaProducto = this.listaProductos();
			List<Producto> mostrarFaltantes = new ArrayList<>();

		for (Producto producto : listaProducto) {	
			if (producto.getCantidadDisponible() < 5) {	mostrarFaltantes.add(producto);}
		}
			if (!mostrarFaltantes.isEmpty()) { return mostrarFaltantes; }	
			else {throw new StatusOkException("No se necesita actualizar el stock");}	
	}
	
	// Recupera producto eliminado
	
	@Override
	public void recuperarProducto(Long idProducto) {
		
			Producto productoBD = productoRepository.findById(idProducto).orElse(null);
		
		if(!productoRepository.existsById(idProducto)) {
			throw new BadRequestException("El id " + idProducto + " no existe. Ingrese un id válido");
		}	
		if(productoRepository.existsById(idProducto) && productoBD.isEliminado() == true) {
			productoBD.setEliminado(false);
		}
			productoRepository.save(productoBD);
	}
		

}
