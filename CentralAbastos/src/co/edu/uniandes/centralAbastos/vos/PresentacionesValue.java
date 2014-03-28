package co.edu.uniandes.centralAbastos.vos;

import java.util.ArrayList;

public class PresentacionesValue 
{
	public double peso = -1;
	
	public double precio = -1;
	
	public PresentacionesValue(double peso, double precio) {
		super();
		this.peso = peso;
		this.precio = precio;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	public ArrayList toArrayList()
	{
		ArrayList respuesta = new ArrayList();
		if (peso != -1)
			respuesta.add(peso);
		if (precio != -1)
			respuesta.add(precio);
			
		return respuesta;
		
	}
	
	public static ArrayList<String> darHeaders()
	{
		ArrayList<String> respuesta = new ArrayList<String>();
		respuesta.add("Peso presentación");
		respuesta.add("precio unitario");
		return respuesta;
	}

	
	
	

}
