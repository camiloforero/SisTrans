package co.edu.uniandes.centralAbastos.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.edu.uniandes.centralAbastos.vistas.Vista;
import co.edu.uniandes.centralAbastos.vistas.VistaMenuUsuarios;

public class ServletMenuUsuario extends ServletTemplate {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2375837895707251754L;

	@Override
	public String darTituloPagina(HttpServletRequest request) 
	{
		return "Menú principal";
	}

	@Override
	public String darImagenTitulo(HttpServletRequest request) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void escribirContenido(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		PrintWriter respuesta = response.getWriter(); //Obtiene el PrintWriter donde se escribe la respuesta
		Vista vista = new VistaMenuUsuarios(); //Obtiene la vista sobre la cual se va a imprimir la respuesta
		vista.imprimirVista(respuesta);
		

	}

}
