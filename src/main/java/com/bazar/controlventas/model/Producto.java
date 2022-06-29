package com.bazar.controlventas.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "productos")
public class Producto{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long idProducto;
	
	@NotNull(message = "No puede ser nulo")
	@Size(min = 4, max = 50, message = " no puede estar vacio o nulo y debe contener entre 4 y 50 caracteres")
	@Column(unique = true)
	private String nombre;
	
	@NotNull(message = "No puede ser nulo")
	@Size(min = 4, max = 50, message = "No puede estar vacio o nulo y debe contener entre 4 y 50 caracteres")
	private String marca;
	
	@Range(min = (long)1.0 , message =  "No puede estar vacio o nulo y debe contener como minimo el valor de 1.0")
	private double costo;
	
	private double cantidadDisponible;
	
	@ManyToMany (mappedBy = "listaProductos")
	@JsonIgnoreProperties("listaProductos")
	private List<Venta> listaVentas; 
	
	private boolean eliminado;

	@ManyToOne
	@JoinColumn(name = "fk_rubro")
	@JsonIgnoreProperties("productos")
	@NotNull(message = "El campo unRubro no puede ser nulo")
	private Rubro unRubro;
	
	public Producto() {
	}

	public Producto(Long idProducto, String nombre, String marca, double costo, double cantidadDisponible, Rubro unRubro) {
		this.idProducto = idProducto;
		this.nombre = nombre;
		this.marca = marca;
		this.costo = costo;
		this.cantidadDisponible = cantidadDisponible;
		this.unRubro = unRubro;
		this.eliminado = false;
	}


	
}
