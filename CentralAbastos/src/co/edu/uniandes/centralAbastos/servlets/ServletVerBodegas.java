package co.edu.uniandes.centralAbastos.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.edu.uniandes.centralAbastos.vistas.Vista;
import co.edu.uniandes.centralAbastos.vistas.VistaTablaSimple;

public class ServletVerBodegas extends ServletTemplate
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5346131412288826232L;

	@Override
	public String darTituloPagina(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return "Bodegas";
	}

	@Override
	public String darImagenTitulo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void escribirContenido(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		Vista vista = new VistaTablaSimple();
		
		
		
	}

}
