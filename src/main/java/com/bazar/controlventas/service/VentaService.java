package com.bazar.controlventas.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazar.controlventas.dto.EditarVentasDTO;
import com.bazar.controlventas.dto.MontoMayorDTO;
import com.bazar.controlventas.dto.VentaProductosDTO;
import com.bazar.controlventas.dto.VentasxFechaDTO;
import com.bazar.controlventas.exceptions.BadRequestException;
import com.bazar.controlventas.exceptions.StatusOkException;
import com.bazar.controlventas.model.Cliente;
import com.bazar.controlventas.model.Producto;
import com.bazar.controlventas.model.Venta;
import com.bazar.controlventas.repository.IClienteRepository;
import com.bazar.controlventas.repository.IVentaRepository;;

@Service
public class VentaService implements IVentaService {

	@Autowired
	private IVentaRepository ventaRepository;

	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private IClienteRepository clienteRepository;

	@Autowired
	private ModelMapper modelMapper;

	// Crear Venta

	@Override
	public Venta createVenta(Venta venta) {
			List<Producto> listaProductos = productoService.listaProductos();
			Cliente clienteBD = clienteRepository.getById(venta.getUnCliente().getIdCliente());
			Venta crearVenta = venta;
			double acum = 0.0;
			
		if( Objects.isNull(venta.getUnCliente().getIdCliente()) || 
			!clienteRepository.existsById(venta.getUnCliente().getIdCliente()) ||
			clienteBD.isEliminado() == true) {
			    throw new BadRequestException("La venta no se pudo realizar: Ingrese un id de cliente válido");
			}
			
			// Se suma el costo de los productos y se asigna al total de la Venta
		for (Producto productoVenta : venta.getListaProductos()) {
			
			if(productoVenta.getIdProducto() == null) {
				throw new BadRequestException("La venta no se pudo realizar: El id de producto no puede estar vacio");
			}
			Producto productoBD = productoService.findProducto(productoVenta.getIdProducto());
			acum = acum + productoBD.getCosto();
		}	
			// Descuento de stock
		for (Producto productoBD : listaProductos) {
			
		for (Producto producto : venta.getListaProductos()) {
		
			if (productoBD.getIdProducto().compareTo(producto.getIdProducto()) == 0) {
					productoBD.setCantidadDisponible(productoBD.getCantidadDisponible() - 1);
			}
		}
			    // Se evita que se genere un descuento negativo
			if (productoBD.getCantidadDisponible() <= -1) {
				throw new BadRequestException("La venta no se pudo realizar: No hay stock para el producto con ID: "
														+ productoBD.getIdProducto());
			}
		}	
			crearVenta.setTotal(acum);
			return ventaRepository.save(crearVenta);
	}

	// Mostrar todas las Ventas
	
	@Override
	public List<Venta> listaVentas() {
			List<Venta> listaVentas = ventaRepository.findAll();
			List<Venta> mostrarVentas = new ArrayList<>();
			
		for (Venta venta : listaVentas) {
			if(venta.isCancelada() == false) {
				mostrarVentas.add(venta);
			}
		}
			return mostrarVentas;
	}

	// Mostrar Venta por ID
	
	@Override
	public Venta findVenta(Long codigoVenta) {		
		
			Venta ventaBD = ventaRepository.findById(codigoVenta).orElse(null);	
			
		if (!ventaRepository.existsById(codigoVenta) || ventaBD.isCancelada() == true) {
			throw new BadRequestException("La venta con id " + codigoVenta + " no existe. Ingrese un id válido");
		}
			return ventaBD;
	}

	// Editar productos y fecha de la venta validando al cliente

	public void editVenta(Long codigoVenta, EditarVentasDTO ventaDTO) {			
			Venta ventaBD = this.findVenta(codigoVenta);
			
			if (ventaBD.getUnCliente().isConectado() == false) {
			throw new BadRequestException("Para editar datos debe iniciar sesion");
		}	
			if (!ventaRepository.existsById(codigoVenta)) {
			throw new BadRequestException("No ah realizado ninguna compra con el codigo de venta " + codigoVenta
											+ ". Ingrese un codigo de venta válido");
		}	
		// Suma al stock los productos de la venta que fueron cambiados o cancelados
		for (Producto productoVentaBD : ventaBD.getListaProductos()) {
			for (Producto productoDTO : ventaDTO.getListaProductosVenta()) {
				if(productoVentaBD.getIdProducto().compareTo(productoDTO.getIdProducto()) == 0) {	
					throw new BadRequestException("Para editar elija un nuevo producto o elimine la venta");	
				}
			}
			Producto prod = productoService.findProducto(productoVentaBD.getIdProducto());
			prod.setCantidadDisponible(prod.getCantidadDisponible() +1);
	
		}
			ventaBD.setListaProductos(ventaDTO.getListaProductosVenta());
			ventaBD.setFecha(ventaDTO.getFechaVenta());
			ventaBD.setCodigoVenta(codigoVenta);	
		    this.createVenta(ventaBD);	
	}

	// Eliminado logico de venta
	
	@Override
	public void deleteVenta(Long codigoVenta) {
			Venta ventaBD = this.findVenta(codigoVenta);
			
		if (ventaBD.getUnCliente().isConectado() == false) {
			throw new BadRequestException("Para editar datos debe iniciar sesion");
		}			
		if (!ventaRepository.existsById(codigoVenta)) {
			throw new BadRequestException("La venta con id " + codigoVenta + " no existe. Ingrese un id válido");
		}
		// Suma al stock los productos de la venta que fue eliminada
		for (Producto productoVentaBD : ventaBD.getListaProductos()) {
			Producto prod = productoService.findProducto(productoVentaBD.getIdProducto());
			prod.setCantidadDisponible(prod.getCantidadDisponible() +1);			
		}
			ventaBD.setCancelada(true);
			ventaBD.setCodigoVenta(codigoVenta);	
			ventaRepository.save(ventaBD);
	}

	// Mostrar ventas por una determinada fecha 
	
	@Override
	public VentasxFechaDTO VentasxFecha(LocalDate fecha) {	
		
			List<Venta> listaVentas = this.listaVentas();
			VentasxFechaDTO ventasxFecha = new VentasxFechaDTO();		
			double acum = 0;
			Long contador = 0L;

		for (Venta venta : listaVentas) {
			
			if (venta.getFecha().isEqual(fecha) == true && venta.isCancelada() == false) {
				acum = acum + venta.getTotal();
				ventasxFecha = modelMapper.map(venta, VentasxFechaDTO.class);
				contador++;
			}			
		}
			if (Objects.isNull(ventasxFecha.getFechaVenta())) {
					throw new StatusOkException("En el dia " + fecha + " no se registraron ventas" );
			}
				ventasxFecha.setMontoTotal(acum);
				ventasxFecha.setNumeroTotalVenta(contador);
				return ventasxFecha;
	}

	// Mostrar solo lista de productos por codigo de venta
	
	@Override
	public VentaProductosDTO ventaProductosDTO(Long codigoVenta) {
		
			if (!ventaRepository.existsById(codigoVenta)) {
				throw new BadRequestException("La venta con id " + codigoVenta + " no existe. Ingrese un id válido");
			}

			List<Venta> listaVentas = this.listaVentas();
			VentaProductosDTO ventaProductosDTO = new VentaProductosDTO();

		for (Venta venta : listaVentas) {
			if (venta.getCodigoVenta().compareTo(codigoVenta) == 0 && venta.isCancelada() == false) {
				ventaProductosDTO = modelMapper.map(venta, VentaProductosDTO.class);
			}
		}
			return ventaProductosDTO;
	}

	// Mostrar datos DTO por Monto más alto
	
	@Override
	public MontoMayorDTO montoMayorDTO() {

			List<Venta> listaVentas = this.listaVentas();
			MontoMayorDTO monto = new MontoMayorDTO();
			double montoMayor = 0;

		for (Venta venta : listaVentas) {

			if (venta.getTotal() >= montoMayor) {
				 montoMayor = venta.getTotal();
			}
			monto = modelMapper.map(venta, MontoMayorDTO.class);
			monto.setCantidadProductos(venta.getListaProductos().size());
		}
	
			return monto;
	}


}
