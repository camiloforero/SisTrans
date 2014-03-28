package co.edu.uniandes.centralAbastos.vos;

import java.util.ArrayList;

public class PedidoOfertaValue 
{
	
	private String id;
	
	private String producto;
	
	/*
	 * Peso de la caja
	 */
	private double presentacion;
	
	private int cantidad;
	
	private String fechaEntrega;
	private String fechaCierre;
	
	//-----------------------------
	// Constructor
	//-----------------------------
	public PedidoOfertaValue(String id, String producto, double presentacion,int cantidad,
			String fechaEntrega, String fechaCierre) {
		super();
		this.id = id;
		this.producto = producto;
		this.presentacion = presentacion;
		this.fechaEntrega = fechaEntrega;
		this.fechaCierre = fechaCierre;
		this.cantidad = cantidad;
	}

	public String getId() {
		return id;
	}

	public String getProducto() {
		return producto;
	}

	public double getPresentacion() {
		return presentacion;
	}

	public String getFechaEntrega() {
		return fechaEntrega;
	}

	public String getFechaCierre() {
		return fechaCierre;
	}
	
	//-----------------------------
	// Metodos
	//-----------------------------
	
	public static ArrayList<String> darHeaders()
	{
		ArrayList<String> respuesta = new ArrayList<String>();
		respuesta.add("ID");
		respuesta.add("Producto");
		respuesta.add("Presentación");
		respuesta.add("Cantidad");
		respuesta.add("Fecha de Entrega");
		respuesta.add("Fecha de cierre");
		return respuesta;
	}
	
	public ArrayList<String> toArrayList()
	{
		ArrayList<String> respuesta = new ArrayList<String>();
		respuesta.add(id);
		respuesta.add(producto);
		respuesta.add(presentacion + "");
		respuesta.add(cantidad + "");
		respuesta.add(fechaEntrega.toString());
		respuesta.add(fechaCierre.toString());
		return respuesta;
	}
	
	
}
