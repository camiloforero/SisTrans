package co.edu.uniandes.centralAbastos.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.edu.uniandes.centralAbastos.fachada.CabAndes;
import co.edu.uniandes.centralAbastos.vistas.Vista;
import co.edu.uniandes.centralAbastos.vistas.VistaBusquedaPedidos;

public class ServletBusquedaPedidos extends ServletTemplate {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4273417600519070759L;

	@Override
	public String darTituloPagina(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return "Buscar pedido";
	}

	@Override
	public String darImagenTitulo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void escribirContenido(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		PrintWriter respuesta = response.getWriter();
		Vista vista = new VistaBusquedaPedidos(); 
		CabAndes instancia = CabAndes.darInstancia();
		try {
			ArrayList[] listaUsuarios = instancia.darUsuariosPorTipo();
			vista.put("listaCompradores", listaUsuarios[0]);
			vista.put("listaAdminslocal", listaUsuarios[1]);
			vista.put("listaAdministradores", listaUsuarios[2]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vista.imprimirVista(respuesta);

	}

}
