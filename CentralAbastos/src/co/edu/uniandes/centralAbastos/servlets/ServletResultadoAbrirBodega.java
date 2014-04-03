package co.edu.uniandes.centralAbastos.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.edu.uniandes.centralAbastos.fachada.CabAndes;

public class ServletResultadoAbrirBodega extends ServletTemplate
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -11355371994651496L;

	@Override
	public String darTituloPagina(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return "Resultado abrir bodega";
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
		System.out.println(request.getParameter("codigo"));
		
		try 
		{
			CabAndes.darInstancia().abrirBodega(codigo);
		}
		catch (Exception e) 
		{
			this.imprimirMensajeError(respuesta, "Error", "Hubo una excepción", e);
			e.printStackTrace();
		}
		imprimirMensajeOk(respuesta, "Success!", "La bodega ha sido abierta satisfactoriamente");				
			
		
	}

}
