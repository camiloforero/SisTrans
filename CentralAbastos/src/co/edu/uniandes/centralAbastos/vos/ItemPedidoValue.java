package co.edu.uniandes.centralAbastos.vos;

public class ItemPedidoValue 
{
	String idPedido;
	String nombProducto;
	double pesoCaja;
	int cantidad;
	
	public ItemPedidoValue(String idPedido, String nombProducto,
			double pesoCaja, int cantidad) {
		super();
		this.idPedido = idPedido;
		this.nombProducto = nombProducto;
		this.pesoCaja = pesoCaja;
		this.cantidad = cantidad;
	}

	public String getIdPedido() {
		return idPedido;
	}

	public String getNombProducto() {
		return nombProducto;
	}

	public double getPesoCaja() {
		return pesoCaja;
	}

	public int getCantidad() {
		return cantidad;
	}
	
	
}
