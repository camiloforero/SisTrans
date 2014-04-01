package co.edu.uniandes.centralAbastos.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import co.edu.uniandes.centralAbastos.fachada.CabAndes;
import co.edu.uniandes.centralAbastos.vistas.Vista;
import co.edu.uniandes.centralAbastos.vistas.VistaListaSimple;

public class ServletRegistrarPedido extends ServletTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7789732812725244349L;

	@Override
	public String darTituloPagina(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return "Registrar pedido";
	}

	@Override
	public String darImagenTitulo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void escribirContenido(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		PrintWriter respuesta = response.getWriter();
		Vista vista = new VistaListaSimple();
		CabAndes instancia = CabAndes.darInstancia();
		ArrayList<String> accion = new ArrayList<String>();
		accion.add("listaPresentaciones.htm");
		vista.put("accion", accion);
		try {
			vista.put("listaOpciones", instancia.darNombresProductos());
		} catch (Exception e) {
			// TODO Cuadrar bien esta excepción
			e.printStackTrace();
		}
		vista.imprimirVista(respuesta);

	}

}
