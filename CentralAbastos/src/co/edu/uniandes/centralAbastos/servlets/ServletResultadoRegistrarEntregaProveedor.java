package co.edu.uniandes.centralAbastos.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.edu.uniandes.centralAbastos.fachada.CabAndes;

public class ServletResultadoRegistrarEntregaProveedor extends ServletTemplate
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4663637606845387733L;

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
		String codigo = request.getParameter("item");
		System.out.println(request.getParameter("item"));
		
		try 
		{
			CabAndes.darInstancia().registrarEntregaDeProveedor(codigo);;
		}
		catch (Exception e) 
		{
			this.imprimirMensajeError(respuesta, "Error", "Hubo una excepción", e);
			e.printStackTrace();
		}
		imprimirMensajeOk(respuesta, "Success!", "Sirve");	
		
	}

}
