package com.bazar.controlventas.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ventas")
public class Venta {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long codigoVenta;
	private double total;

	@NotNull(message = "No puede ser nulo")
	@FutureOrPresent(message = "Debe ser igual o mayor a la fecha de hoy")
	private LocalDate fecha;

	@ManyToMany
	@JoinTable(
			name = "ventas_productos", 
			joinColumns = @JoinColumn(name = "fk_venta", nullable = false), 
			inverseJoinColumns = @JoinColumn(name = "fk_producto", nullable = false))
	@NotEmpty
	@NotNull(message = "Debe seleccionar al menos 1 producto")
	@JsonIgnoreProperties("listaVentas")
	private List<Producto> listaProductos;

	@OneToOne
	@JoinColumn(name = "idCliente", referencedColumnName = "idCLiente")
	@NotNull(message = "Debe contener un Cliente")
	private Cliente unCliente;
	
	private boolean cancelada;

	public Venta() {
	}

	public Venta(Long codigoVenta, double total, LocalDate fecha, List<Producto> listaProductos, Cliente unCliente) {
		this.codigoVenta = codigoVenta;
		this.total = total;
		this.fecha = fecha;
		this.listaProductos = listaProductos;
		this.unCliente = unCliente;
		this.cancelada = false;
	}

}
