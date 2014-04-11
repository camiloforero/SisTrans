package co.edu.uniandes.centralAbastos.vos;

public class InfoTiempoEntregaValue 
{
	int cantidad;
	String fecha_in;
	int Z;
	int S;
	int delta;
	
	
	
	public InfoTiempoEntregaValue(int cantidad, String fecha_in, int z, int s,
			int delta) {
		super();
		this.cantidad = cantidad;
		this.fecha_in = fecha_in;
		Z = z;
		S = s;
		this.delta = delta;
	}
	
	public int getCantidad() {
		return cantidad;
	}
	public String getFecha_in() {
		return fecha_in;
	}
	public int getZ() {
		return Z;
	}
	public int getS() {
		return S;
	}
	public int getDelta() {
		return delta;
	}
	
	
}
