package co.edu.uniandes.centralAbastos.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletResultadoRegistrarPedido extends ServletTemplate
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -24567751886620138L;

	@Override
	public String darTituloPagina(HttpServletRequest request) {
		return "Resultado registro pedido";
	}

	@Override
	public String darImagenTitulo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void escribirContenido(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter respuesta = response.getWriter();
		respuesta.println(request.getParameter("producto"));
		respuesta.println(request.getParameter("valor"));
		respuesta.println(request.getParameter("cantidad"));

		// TODO Auto-generated method stub
		
	}

}
