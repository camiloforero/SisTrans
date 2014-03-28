package co.edu.uniandes.centralAbastos.vos;

public class PedidoEfectivoValue
{
	private String id;
	
	private String producto;
	
	
	private String tipoProducto;
	
	/*
	 * Peso de la caja
	 */
	private double presentacion;
	
	private int cantidad;
	
	private double precio_X_kg;
	
	private double precioTotal;
	
	private String fechaExpiracion;
	
	private String fechaEntrega;
	
	private String correo_p ;
	

	private int calif;
	private double coeficientePrecios;
	
	
	
	
	//---------------------------------------------
	
	
	
	public PedidoEfectivoValue(String id, String producto, double presentacion,
			int cantidad, double precio_X_kg, double precioTotal,
			String fechaExpiracion, String fechaEntrega, String correo_p, int calif,double coeficientePrecios) {
		super();
		this.id = id;
		this.producto = producto;
		this.presentacion = presentacion;
		this.cantidad = cantidad;
		this.precio_X_kg = precio_X_kg;
		this.precioTotal = precioTotal;
		this.fechaExpiracion = fechaExpiracion;
		this.fechaEntrega = fechaEntrega;
		this.calif=calif;
		this.correo_p = correo_p;
		this.coeficientePrecios = coeficientePrecios;
	}
	

	public PedidoEfectivoValue(String producto, String tipoProducto,
			int cantidad, double presentacion , String fechaExpiracion, double precio_X_kg) {
		super();
		this.producto = producto;
		this.tipoProducto = tipoProducto;
		this.presentacion = presentacion;
		this.cantidad = cantidad;
		this.precio_X_kg = precio_X_kg;
		this.fechaExpiracion = fechaExpiracion;
	}


	//---------------------------------------------

	public double getCoeficientePrecios() {
		return coeficientePrecios;
	}


	public String getCorreo_p() {
		return correo_p;
	}


	public int getCalif() {
		return calif;
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

	public int getCantidad() {
		return cantidad;
	}

	public double getPrecio_X_kg() {
		return precio_X_kg;
	}

	public double getPrecioTotal() {
		return precioTotal;
	}

	public String getFechaExpiracion() {
		return fechaExpiracion;
	}

	public String getFechaEntrega() {
		return fechaEntrega;
	}

	public String getTipoProducto() {
		return tipoProducto;
	}
	
	
}
