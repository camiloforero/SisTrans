package co.edu.uniandes.centralAbastos.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.edu.uniandes.centralAbastos.fachada.CabAndes;
import co.edu.uniandes.centralAbastos.vistas.Vista;
import co.edu.uniandes.centralAbastos.vistas.VistaSeleccionTabla;

public class ServletRegistrarEntregaProveedor extends ServletTemplate
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4441765600535952566L;

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
		Vista vista = new VistaSeleccionTabla();
		CabAndes instancia = CabAndes.darInstancia();
		
		//Agrega el header (Nombre de la columna de la tabla)
		ArrayList<String> header = new ArrayList<String>();
		header.add("Código");
		vista.put("headers", header);
		
		//Agrega la acción a realizar cuando se le da Submit al formulario
		ArrayList<String> accion = new ArrayList<String>();
		accion.add("resultadoRegistrarEntregaProveedor.htm");
		vista.put("accion", accion);
		try 
		{	
			ArrayList<ArrayList<String>> contenido = new ArrayList<ArrayList<String>>();
			ArrayList<String> codigos = instancia.darIdsPedidosEfectivos();
			
			//El siguiente for es necesario ya que debido a la manera como está implementado el template de Velocity,
			//este no acepta las strings directamente, sino tiene que sacarlas de una ArrayList.
			for(String str: codigos)
			{
				ArrayList<String> contenedor = new ArrayList<String>();
				contenedor.add(str);
				contenido.add(contenedor);
				
			}
			
		
			vista.put("items", contenido);
		} catch (Exception e) {
			// TODO Manejar excepciones
			e.printStackTrace();
		}
		vista.imprimirVista(respuesta);
		
	}
		
	
	

}
