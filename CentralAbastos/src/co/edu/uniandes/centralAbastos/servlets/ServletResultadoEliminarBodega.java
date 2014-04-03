package co.edu.uniandes.centralAbastos.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.edu.uniandes.centralAbastos.fachada.CabAndes;

public class ServletResultadoEliminarBodega extends ServletTemplate
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9032578974463705161L;

	@Override
	public String darTituloPagina(HttpServletRequest request) 
	{
		return "Eliminar bodega";
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
			instancia.eliminarBodega(request.getParameter("item"));
		} catch (Exception e) 
		{
			imprimirMensajeError(respuesta, "ERROR", "Excepción", e);
		}
		imprimirMensajeOk(respuesta, "Success!", "La bodega fue eliminada satisfactoriamente");
		
	}

}
