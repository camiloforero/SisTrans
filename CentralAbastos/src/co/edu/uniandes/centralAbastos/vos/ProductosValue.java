package co.edu.uniandes.centralAbastos.vos;

import java.util.ArrayList;

public class ProductosValue 
{
	/**
	 * Nombre del producto
	 */
	private String nombre;
	
	/**
	 * Tipo del producto
	 */
	private String tipo;
	
	private double pesoCaja; 
	
	private int cantidad; 
	
	private String fechaExpiracion; 
	
	private String codigoAlmacen; 
	
	private String tipoLocal; 
	
	
	
	
	
	
	
	
	
	
	public ProductosValue(String nombre, String tipo, double pesoCaja,
			int cantidad, String fechaExpiracion, String codigoAlmacen,
			String tipoLocal) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.pesoCaja = pesoCaja;
		this.cantidad = cantidad;
		this.fechaExpiracion = fechaExpiracion;
		this.codigoAlmacen = codigoAlmacen;
		this.tipoLocal = tipoLocal;
	}

	public String darNombre()
	{
		return nombre;
	}
	
	public String darTipo()
	{
		return tipo;
	}
	
	public void setNombre(String s)
	{
		nombre = s;
	}
	
	public void setTipo(String s)
	{
		tipo = s;
	}
	
	public double darPesoCaja() {
		return pesoCaja;
	}

	public void setPesoCaja(double pesoCaja) {
		this.pesoCaja = pesoCaja;
	}

	public int darCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String darFechaExpiracion() {
		return fechaExpiracion;
	}

	public void setFechaExpiracion(String fechaExpiracion) {
		this.fechaExpiracion = fechaExpiracion;
	}

	public String darCodigoAlmacen() {
		return codigoAlmacen;
	}

	public void setCodigoAlmacen(String codigoAlmacen) {
		this.codigoAlmacen = codigoAlmacen;
	}

	public String darTipoLocal() {
		return tipoLocal;
	}

	public void setTipoLocal(String tipoLocal) {
		this.tipoLocal = tipoLocal;
	}

	public static ArrayList<String> darHeader()
	{
		ArrayList<String> ans = new ArrayList<String>();
		ans.add("Nombre");
		ans.add("Tipo");
		ans.add("Peso presentación");
		ans.add("Cantidad");
		ans.add("Fecha de expiración");
		ans.add("Código almacén");
		ans.add("Tipo locación");
		
		return ans;
	}

}
