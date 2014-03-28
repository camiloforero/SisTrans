package co.edu.uniandes.centralAbastos.vistas;

import java.io.PrintWriter;

public class VistaListaSimple extends Vista {

	@Override
	public void imprimirVista(PrintWriter respuesta) {
		template = engine.getTemplate("listaSimple.vm");
		merge(respuesta);
		// TODO Auto-generated method stub

	}

}
