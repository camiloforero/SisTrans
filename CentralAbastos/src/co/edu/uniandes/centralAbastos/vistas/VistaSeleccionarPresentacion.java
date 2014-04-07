package co.edu.uniandes.centralAbastos.vistas;

import java.io.PrintWriter;

public class VistaSeleccionarPresentacion extends Vista {

	@Override
	public void imprimirVista(PrintWriter respuesta) 
	{
		template = engine.getTemplate("seleccionarPresentacion.vm");
		merge(respuesta);
		// TODO Auto-generated method stub

	}

}
