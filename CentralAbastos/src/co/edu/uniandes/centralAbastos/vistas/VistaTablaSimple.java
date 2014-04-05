package co.edu.uniandes.centralAbastos.vistas;

import java.io.PrintWriter;

public class VistaTablaSimple extends Vista
{

	@Override
	public void imprimirVista(PrintWriter respuesta) 
	{
		template = engine.getTemplate("tablaSimple.vm");
        merge(respuesta);		
	}

}
