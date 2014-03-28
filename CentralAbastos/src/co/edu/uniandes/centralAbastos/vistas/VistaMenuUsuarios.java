package co.edu.uniandes.centralAbastos.vistas;

import java.io.PrintWriter;

public class VistaMenuUsuarios extends Vista {

	@Override
	public void imprimirVista(PrintWriter respuesta) 
	{
		template = engine.getTemplate("menuUsuarios.vm");        
        merge(respuesta);	

	}

}
