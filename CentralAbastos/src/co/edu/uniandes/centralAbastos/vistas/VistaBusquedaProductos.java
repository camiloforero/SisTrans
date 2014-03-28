package co.edu.uniandes.centralAbastos.vistas;

import java.io.PrintWriter;
import java.util.ArrayList;

public class VistaBusquedaProductos extends Vista {

	@Override
	public void imprimirVista(PrintWriter respuesta) 
	{
		
		template = engine.getTemplate("busquedaProductos.vm");        
        merge(respuesta);	

	}

}
