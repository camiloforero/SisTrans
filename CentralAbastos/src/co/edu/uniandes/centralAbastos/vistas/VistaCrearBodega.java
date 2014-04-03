package co.edu.uniandes.centralAbastos.vistas;

import java.io.PrintWriter;

public class VistaCrearBodega extends Vista {

	@Override
	public void imprimirVista(PrintWriter respuesta) 
	{
		template = engine.getTemplate("crearBodega.vm");        
        merge(respuesta);

	}

}
