package com.bazar.controlventas.service;

import java.util.List;
import com.bazar.controlventas.model.Rubro;

public interface IRubroService {

	public Rubro createRubro(Rubro rubro);
	
	public Rubro findRubro(Long idRubro);
	
	public List<Rubro> listaRubros();
	
	public void editNameRubro(Long idRubro, Rubro rubro);
	
	public void deleteRubro(Long idRubro);

	public void recuperarRubro(Long idRubro);

}
