package co.edu.uniandes.centralAbastos.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.edu.uniandes.centralAbastos.fachada.CabAndes;
import co.edu.uniandes.centralAbastos.vistas.Vista;
import co.edu.uniandes.centralAbastos.vistas.VistaResultadosBusquedaPedidos;
import co.edu.uniandes.centralAbastos.vos.PedidosValue;

public class ServletResultadoBusquedaPedidos extends ServletTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8927703248534241912L;

	@Override
	public String darTituloPagina(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return "Pedidos";
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
		CabAndes instancia = CabAndes.darInstancia();
		String[] valores = request.getParameterValues("criterio");
		
		String tipoUsuario = request.getParameter("tipoUsuario");
		String usuario = request.getParameter(tipoUsuario);
		
		String params = "";
		
			
			//this.imprimirMensajeError(respuesta, "CATASTROPHIC ERROR", "No se llenó ninguno de los campos del formulario");
		if(valores != null)
		{
			for(String s: valores)
			{
				if(s.equals("estaSatisfecho") )
				{
					params +=(s + "," + request.getParameter(s) + ";");
				}
					
				if(s.equals("rangoFechas"))
				{
					String fechaInicio = request.getParameter("fechaInicio");
					String fechaFin = request.getParameter("fechaFin");
					if(fechaInicio.equals("") && fechaFin.equals(""))
						this.imprimirMensajeError(respuesta, "NADA", "<img src=\"http://newyorkcriminaldefenseblawg.com/wp-content/uploads/2010/06/Nullity.gif\" alt=\"NULL\"><br> No se ingres� ninguna fecha");
					
					else if(fechaInicio.compareTo(fechaFin) > 0)
						this.imprimirMensajeError(respuesta, "FACEPALM", "<img src=\"http://24.media.tumblr.com/09a86b8c5c5c0d6e19ef8fdaa04e2b61/tumblr_mgx16sRcAz1s3kq11o1_400.gif\" alt=\"FACEPALM\"><br> La fecha de inicio es mayor a la fecha de fin");
					
					else
						params += s + "," + fechaInicio + "," + fechaFin + ";";
				}
					
				
				if(s.equals("costo"))
				{
					try
					{
						double valorCosto = Double.parseDouble(request.getParameter("valorCosto").replace(',', '.'));						
						params += ( s + "," + valorCosto + "," + request.getParameter("radioCosto") + ";");
					}
					catch(NumberFormatException e)
					{
						this.imprimirMensajeError(respuesta, "ERROR LETAL", "No se ingresó un número válido", e);
					}
					
				}
				
							
			}
			
			
		}
		Vista vista = new VistaResultadosBusquedaPedidos();
		vista.put("headers", PedidosValue.darHeaders());
		try {
			vista.put("items", instancia.darResultadoBusquedaPedidos(tipoUsuario, usuario, params));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vista.imprimirVista(respuesta);
		
		

	}

}
