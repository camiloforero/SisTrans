package co.edu.uniandes.centralAbastos.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.edu.uniandes.centralAbastos.fachada.CabAndes;

public class ServletResultadoCrearBodega extends ServletTemplate
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5695942484283703454L;

	@Override
	public String darTituloPagina(HttpServletRequest request) {
		return "Crear bodega";
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
		String codigo = request.getParameter("codigo");
		double capacidad = Double.parseDouble(request.getParameter("capacidad"));
		String tipoProducto = request.getParameter("tipo");
		System.out.println(request.getParameter("codigo"));
		System.out.println(request.getParameter("capacidad"));
		System.out.println(request.getParameter("tipo"));
		
		try {
			CabAndes.darInstancia().agregarBodega(codigo, capacidad, tipoProducto);
		} catch (Exception e) 
		{
			this.imprimirMensajeError(respuesta, "Error", "Hubo una excepción", e);
			e.printStackTrace();
		}
		imprimirMensajeOk(respuesta, "Success!", "La bodega ha sido creada satisfactoriamente");
		
		// TODO Auto-generated method stub
		
	}

}
