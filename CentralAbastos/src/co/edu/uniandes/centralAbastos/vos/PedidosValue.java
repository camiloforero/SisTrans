package co.edu.uniandes.centralAbastos.vos;

import java.util.ArrayList;

public class PedidosValue 
{
	
	private String id;
	
	private String estado;
	
	private String fecha;
	
	private String producto;
	
	private int cantidad;
	
	private double presentacion;
	
	private double costoParcial;
	
	
	public PedidosValue(String id, String estado, String fecha,
			String producto, int cantidad, double presentacion, double costoParcial) {
		super();
		this.id = id;
		this.estado = estado;
		this.fecha = fecha;
		this.producto = producto;
		this.cantidad = cantidad;
		this.presentacion = presentacion;
		this.costoParcial = costoParcial;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(double presentacion) {
		this.presentacion = presentacion;
	}

	public double getcostoParcial() {
		return costoParcial;
	}

	public void setcostoParcial(int costoParcial) {
		this.costoParcial = costoParcial;
	}
	
	public ArrayList<String> toArrayList()
	{
		ArrayList<String> respuesta = new ArrayList<String>();
		respuesta.add(id);
		respuesta.add(estado);
		respuesta.add(fecha);
		respuesta.add(producto);
		respuesta.add(cantidad + "");
		respuesta.add(presentacion + "");
		respuesta.add(costoParcial + "");
		return respuesta;
	}
	
	public static ArrayList<String> darHeaders()
	{
		ArrayList<String> respuesta = new ArrayList();
		respuesta.add("ID pedido");
		respuesta.add("Estado del pedido");
		respuesta.add("Fecha de entrega");
		respuesta.add("Producto");
		respuesta.add("Cantidad pedida");
		respuesta.add("Peso de la presentación (kg)");
		respuesta.add("Costo");
		return respuesta;
	}


}
