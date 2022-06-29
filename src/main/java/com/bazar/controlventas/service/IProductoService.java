package com.bazar.controlventas.service;

import java.util.List;
import com.bazar.controlventas.model.Producto;

public interface IProductoService {

	public Producto createProducto(Producto prod);

	public List<Producto> listaProductos();

	public Producto findProducto(Long idProducto);

	public void deleteProducto(Long idProducto);

	public List<Producto> faltanteStock();

	public void editProducto(Long idProducto, Producto producto);

	public void recuperarProducto(Long idProducto);
}
