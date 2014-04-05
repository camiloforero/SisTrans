package co.edu.uniandes.centralAbastos.vos;

public class PedidoLocalValue
{
	String idPedido;
	double cantdadNeta;
	String deadline;
	String correoAdmin;
	String codigoLocal;
	
	public PedidoLocalValue(String idPedido, double cantdadNeta,
			String deadline, String correoAdmin, String codigoLocal) {
		super();
		this.idPedido = idPedido;
		this.cantdadNeta = cantdadNeta;
		this.deadline = deadline;
		this.correoAdmin = correoAdmin;
		this.codigoLocal = codigoLocal;
	}

	public String getIdPedido() {
		return idPedido;
	}

	public double getCantdadNeta() {
		return cantdadNeta;
	}

	public String getDeadline() {
		return deadline;
	}

	public String getCorreoAdmin() {
		return correoAdmin;
	}

	public String getCodigoLocal() {
		return codigoLocal;
	}
	
	
	
}
