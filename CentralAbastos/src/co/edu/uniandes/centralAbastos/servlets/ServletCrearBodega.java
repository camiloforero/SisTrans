package co.edu.uniandes.centralAbastos.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletCrearBodega extends ServletTemplate 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5907222173841150583L;

	@Override
	public String darTituloPagina(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return "Registrar bodega";
	}

	@Override
	public String darImagenTitulo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void escribirContenido(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		// TODO Auto-generated method stub

	}

}
