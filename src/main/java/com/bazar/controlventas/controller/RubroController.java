package com.bazar.controlventas.controller;

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

import com.bazar.controlventas.model.Rubro;
import com.bazar.controlventas.service.IRubroService;

@RestController
public class RubroController {

	@Autowired
	private IRubroService rubroService;

	// Crear Rubro

	@PostMapping("/rubros/crear")
	public ResponseEntity<String> createRubro(@Valid @RequestBody Rubro rubro) {
		rubroService.createRubro(rubro);
		return ResponseEntity.status(HttpStatus.OK).body("El rubro se creó correctamente");			
	}

	// Mostrar Rubro por ID

	@GetMapping("/rubros/traer/{id_rubro}")
	public ResponseEntity<Object> getRubro(@PathVariable Long id_rubro){
			return ResponseEntity.status(HttpStatus.OK).body(rubroService.findRubro(id_rubro));
		
	}
	
	// Mostra lista de Rubros
	
	@GetMapping("/rubros/lista_rubros")
	public ResponseEntity<Object> listaRubros(){
		return ResponseEntity.status(HttpStatus.OK).body(rubroService.listaRubros());
	}

	// Editar Rubro

	@PutMapping("/rubros/editar/{id_rubro}")
	public ResponseEntity<String> editRubro(@PathVariable Long id_rubro, @Valid @RequestBody Rubro rubro) {	
			rubroService.editNameRubro(id_rubro, rubro);
		return ResponseEntity.status(HttpStatus.OK).body("El rubro se actualizó correctamente");
	}

	// Eliminar Rubro

	@DeleteMapping("/rubros/eliminar/{id_rubro}")
	public ResponseEntity<String> deleteRubro(@PathVariable Long id_rubro) {
		rubroService.deleteRubro(id_rubro);
		return ResponseEntity.status(HttpStatus.OK).body("El rubro se eliminó correctamente");
	}
	
	// Recuperar Rubro
	
	@PutMapping("/rubros/recuperar/{id_rubro}")
	public ResponseEntity<String> recuperarRubro(@PathVariable Long id_rubro) {
			rubroService.recuperarRubro(id_rubro);
		return ResponseEntity.status(HttpStatus.OK).body("El rubro se reestableció correctamente");
	}
	
}
