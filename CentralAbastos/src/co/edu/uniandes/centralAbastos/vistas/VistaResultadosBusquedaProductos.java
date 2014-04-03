package co.edu.uniandes.centralAbastos.vistas;

import java.io.PrintWriter;

public class VistaResultadosBusquedaProductos extends Vista {

	@Override
	public void imprimirVista(PrintWriter respuesta)
	{
		template = engine.getTemplate("resultadosBusquedaProductos.vm");        
        merge(respuesta);

	}

}
