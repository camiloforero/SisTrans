package co.edu.uniandes.centralAbastos.vistas;

import java.io.PrintWriter;

public class VistaFooter extends Vista {

	@Override
	public void imprimirVista(PrintWriter respuesta) 
	{
		template = engine.getTemplate("footer.vm");        
        merge(respuesta);

	}

}
