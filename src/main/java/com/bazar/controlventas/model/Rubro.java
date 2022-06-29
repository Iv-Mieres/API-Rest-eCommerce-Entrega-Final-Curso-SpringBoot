package com.bazar.controlventas.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rubros")
public class Rubro {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long idRubro;

	@NotNull(message = "No puede ser nulo")
	@Size(min = 4, message = "No puede estar vacio y debe contener un minimo de 4 caracteres")
	@Column(unique = true)
	private String sector;

	@OneToMany(mappedBy = "unRubro")
	@JsonIgnoreProperties("UnRubro")
	private List<Producto> productos;
	
	private boolean eliminado;

	public Rubro() {
	}

	public Rubro(Long idRubro, String sector) {
		this.idRubro = idRubro;
		this.sector = sector;
	}

	public Rubro(Long idRubro, String sector, List<Producto> productos) {
		this.idRubro = idRubro;
		this.sector = sector;
		this.productos = productos;
		this.eliminado = false;
	}
	
	

}
