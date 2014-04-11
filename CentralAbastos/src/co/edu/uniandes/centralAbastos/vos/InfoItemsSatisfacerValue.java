package co.edu.uniandes.centralAbastos.vos;

public class InfoItemsSatisfacerValue 
{
	String idPedido;
	String nombProducto;
	double pesoCaja;
	int cantidad;
	int restante;

public InfoItemsSatisfacerValue(String idPedido, String nombProducto,
			double pesoCaja, int cantidad, int restante) {
		super();
		this.idPedido = idPedido;
		this.nombProducto = nombProducto;
		this.pesoCaja = pesoCaja;
		this.cantidad = cantidad;
		this.restante = restante;
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
	public int getRestante() {
		return restante;
	}
	
	
	
}
