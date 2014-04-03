package co.edu.uniandes.centralAbastos.vistas;

import java.io.PrintWriter;

public class VistaSeleccionTabla extends Vista
{

	@Override
	public void imprimirVista(PrintWriter respuesta) 
	{
		template = engine.getTemplate("seleccionTabla.vm");
		merge(respuesta);
	}

}
