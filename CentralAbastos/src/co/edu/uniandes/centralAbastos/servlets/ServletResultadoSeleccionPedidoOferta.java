package co.edu.uniandes.centralAbastos.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.edu.uniandes.centralAbastos.fachada.CabAndes;

public class ServletResultadoSeleccionPedidoOferta extends ServletTemplate
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6813196340322574667L;

	@Override
	public String darTituloPagina(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return "Selección pedido de oferta";
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
		CabAndes instancia = CabAndes.darInstancia();
		try {
			instancia.darOfertaGanadora(request.getParameter("item"));
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		respuesta.print(request.getParameter("item"));
		// TODO Auto-generated method stub
		
	}

}
