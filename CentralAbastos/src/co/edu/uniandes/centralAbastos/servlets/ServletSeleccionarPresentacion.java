package co.edu.uniandes.centralAbastos.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.edu.uniandes.centralAbastos.fachada.CabAndes;
import co.edu.uniandes.centralAbastos.vistas.Vista;
import co.edu.uniandes.centralAbastos.vistas.VistaListaSimple;
import co.edu.uniandes.centralAbastos.vistas.VistaSeleccionarPresentacion;

public class ServletSeleccionarPresentacion extends ServletTemplate
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1238109622781723903L;

	@Override
	public String darTituloPagina(HttpServletRequest request) 
	{
		return "Seleccionar presentación";
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
		PrintWriter respuesta = response.getWriter();
		Vista vista = new VistaSeleccionarPresentacion();
		CabAndes instancia = CabAndes.darInstancia();
		//ArrayList<String> producto = new ArrayList<String>();
		//accion.add("resultadoRegistrarPedido.htm");
		vista.put("producto", request.getParameter("valor"));
		System.out.println(request.getParameter("valor"));
		try {
			vista.put("listaOpciones", instancia.darPresentaciones());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vista.imprimirVista(respuesta);
		// TODO Auto-generated method stub
		
	}

}
