package co.edu.uniandes.centralAbastos.vistas;

import java.io.PrintWriter;

public class VistaBusquedaPedidos extends Vista {

	@Override
	public void imprimirVista(PrintWriter respuesta) 
	{
		template = engine.getTemplate("busquedaPedidos.vm");
        merge(respuesta);	

	}

}
