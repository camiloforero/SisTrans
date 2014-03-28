package co.edu.uniandes.centralAbastos.vistas;

import java.io.PrintWriter;

public class VistaResultadosBusquedaPedidos extends Vista {

	@Override
	public void imprimirVista(PrintWriter respuesta) 
	{
		template = engine.getTemplate("resultadosBusquedaPedidos.vm");
		merge(respuesta);
		// TODO Auto-generated method stub

	}

}
