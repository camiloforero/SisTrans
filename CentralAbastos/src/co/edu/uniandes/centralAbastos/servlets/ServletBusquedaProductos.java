package co.edu.uniandes.centralAbastos.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.edu.uniandes.centralAbastos.fachada.CabAndes;
import co.edu.uniandes.centralAbastos.vistas.Vista;
import co.edu.uniandes.centralAbastos.vistas.VistaBusquedaProductos;

public class ServletBusquedaProductos extends ServletTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2523558653706145744L;

	@Override
	public String darTituloPagina(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return "Búsqueda de productos";
	}

	@Override
	public String darImagenTitulo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void escribirContenido(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		PrintWriter respuesta = response.getWriter(); //Obtiene el PrintWriter donde se escribe la respuesta
		CabAndes instancia = CabAndes.darInstancia(); //Obtiene una instancia del mundo
		Vista vista = new VistaBusquedaProductos(); //Obtiene la vista sobre la cual se va a imprimir la respuesta
		
		try
		{
			vista.put("listaProductos", instancia.darNombresProductos());
			vista.put("listaTipos", instancia.darTipos());
			vista.put("listaPresentaciones", instancia.darPresentaciones());
			vista.put("listaLocales", instancia.darLocales());
			vista.put("listaBodegas", instancia.darCodigosBodegas());
			
			vista.imprimirVista(respuesta); //Imprime los resultados de acuerdo a la vista elegida
		}
		catch (Exception e)
		{
			imprimirMensajeError(respuesta, "ERROR", "EXCEPCION", e);
		}
		
		
		
		// TODO Auto-generated method stub

	}

}
