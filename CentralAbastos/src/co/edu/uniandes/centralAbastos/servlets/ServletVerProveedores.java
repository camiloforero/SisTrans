package co.edu.uniandes.centralAbastos.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletVerProveedores extends ServletTemplate
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6777777854912562019L;

	@Override
	public String darTituloPagina(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return "Proveedores";
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
