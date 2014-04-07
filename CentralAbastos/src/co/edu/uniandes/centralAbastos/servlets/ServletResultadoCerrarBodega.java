package co.edu.uniandes.centralAbastos.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.edu.uniandes.centralAbastos.fachada.CabAndes;

public class ServletResultadoCerrarBodega extends ServletTemplate
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3378478728123532764L;

	@Override
	public String darTituloPagina(HttpServletRequest request) 
	{
		return "Resultado cerrar bodega";
	}

	@Override
	public String darImagenTitulo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void escribirContenido(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		// TODO Manejar excepciones
		PrintWriter respuesta = response.getWriter();
		String codigo = request.getParameter("item");
		System.out.println(request.getParameter("item"));
		
		try 
		{
			boolean resp = CabAndes.darInstancia().cerrarBodega(codigo);
			if(resp)
				imprimirMensajeOk(respuesta, "Success!", "La bodega ha sido cerrada satisfactoriamente");
			else
				imprimirMensajeError(respuesta, "Oh no!", "No fue posible redistribuir el contenido de la bodega");
			
		}
		catch (Exception e) 
		{
			this.imprimirMensajeError(respuesta, "Error", "Hubo una excepción", e);
			e.printStackTrace();
		}
		
		
	}

}
