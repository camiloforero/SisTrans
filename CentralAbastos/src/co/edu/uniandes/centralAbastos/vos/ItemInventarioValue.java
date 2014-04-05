package co.edu.uniandes.centralAbastos.vos;

public class ItemInventarioValue 
{
	String nomb_producto;
	String cod_almacen;
	double peso_caja;
	int cantidad;
	String fechaExpiracion;
	
	public ItemInventarioValue(String nomb_producto, String cod_almacen,
			double peso_caja, int cantidad, String fecha) {
		super();
		this.nomb_producto = nomb_producto;
		this.cod_almacen = cod_almacen;
		this.peso_caja = peso_caja;
		this.cantidad = cantidad;
		this.fechaExpiracion = fecha;
	}

	public String getNomb_producto() {
		return nomb_producto;
	}

	public String getCod_almacen() {
		return cod_almacen;
	}

	public double getPresentacion() {
		return peso_caja;
	}

	public int getCantidad() {
		return cantidad;
	}
	
	public String getFechaExp(){
		return this.fechaExpiracion;
	}
	
	
	
}
