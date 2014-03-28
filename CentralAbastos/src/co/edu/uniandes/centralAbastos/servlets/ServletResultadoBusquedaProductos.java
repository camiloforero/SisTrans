package co.edu.uniandes.centralAbastos.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.edu.uniandes.centralAbastos.fachada.CabAndes;
import co.edu.uniandes.centralAbastos.vistas.Vista;
import co.edu.uniandes.centralAbastos.vistas.VistaResultadosBusquedaProductos;
import co.edu.uniandes.centralAbastos.vos.ProductosValue;

public class ServletResultadoBusquedaProductos extends ServletTemplate {

	@Override
	public String darTituloPagina(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return "Productos";
	}

	@Override
	public String darImagenTitulo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return "videos.jpg";
	}

	@Override
	public void escribirContenido(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		PrintWriter respuesta = response.getWriter();
		CabAndes instancia = CabAndes.darInstancia();
		Vista vista = new VistaResultadosBusquedaProductos();
		String criterio = request.getParameter("criterio");
		try
		{
			ArrayList resultados = instancia.darResultadoBusquedaProductos(criterio + ", " + request.getParameter(criterio));
			vista.put("productos", resultados);
			vista.put("headers", ProductosValue.darHeader());
			vista.imprimirVista(respuesta);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			imprimirMensajeError(respuesta, "ERROR", "Excepción", e);
		}
		

		

	}

}
