package co.edu.uniandes.centralAbastos.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.edu.uniandes.centralAbastos.fachada.CabAndes;
import co.edu.uniandes.centralAbastos.vistas.Vista;
import co.edu.uniandes.centralAbastos.vistas.VistaCrearBodega;

public class ServletCrearBodega extends ServletTemplate 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5907222173841150583L;

	@Override
	public String darTituloPagina(HttpServletRequest request) {
		// TODO Auto-generated method stub
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
		Vista vista = new VistaCrearBodega();
		CabAndes instancia = CabAndes.darInstancia();
		try {
			vista.put("listaTipos", instancia.darTipos());
		} catch (Exception e) {
			e.printStackTrace();
		}
		vista.imprimirVista(respuesta);
		
		// TODO Auto-generated method stub

	}

}
