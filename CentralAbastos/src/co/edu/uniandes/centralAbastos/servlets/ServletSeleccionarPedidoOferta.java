package co.edu.uniandes.centralAbastos.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.edu.uniandes.centralAbastos.fachada.CabAndes;
import co.edu.uniandes.centralAbastos.vistas.Vista;
import co.edu.uniandes.centralAbastos.vistas.VistaSeleccionTabla;
import co.edu.uniandes.centralAbastos.vos.PedidoOfertaValue;

public class ServletSeleccionarPedidoOferta extends ServletTemplate
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5992372373628866795L;

	@Override
	public String darTituloPagina(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
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
		Vista vista = new VistaSeleccionTabla();
		CabAndes instancia = CabAndes.darInstancia();
		vista.put("headers", PedidoOfertaValue.darHeaders());
		ArrayList<String> accion = new ArrayList<String>();
		accion.add("resultadoSeleccionPedidoOferta.htm");
		vista.put("accion", accion);
		try {
			vista.put("items", instancia.darPedidosDeOfertaCerrados());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vista.imprimirVista(respuesta);
		
	}

}
