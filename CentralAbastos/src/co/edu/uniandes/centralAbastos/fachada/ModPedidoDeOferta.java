package co.edu.uniandes.centralAbastos.fachada;
import java.util.ArrayList;

import co.edu.uniandes.centralAbastos.dao.*;
import co.edu.uniandes.centralAbastos.vos.PedidoEfectivoValue;

public class ModPedidoDeOferta 
{
	/*
	 * Conexion con la BD
	 */
	DAOPedidosOferta dao;

	//-------------------------------
	// Constructor
	//-------------------------------
	public ModPedidoDeOferta(DAOPedidosOferta dao) {
		super();
		this.dao = dao;
	}
	
	//-------------------------------
	// Metodos
	//-------------------------------
	
	/**
	 * Devuelve la oferta ganadora de un pedido de oferta dado su identificado unico.
	 * @param idPedidoOferta - identificador del pedido de oferta
	 * @return La oferta ganadora. (!) Esta nunca deberia ser null.
	 * @throws Exception
	 */
	PedidoEfectivoValue darOfertaGanadora(String idPedidoOferta) throws Exception
	{
		// Pesos ponderacion
			double Wprice = 0.7;
			double Wcalif = 0.3;
		
		// Criterio entre 0 y 1 que se va a utilizar para seleccionar la mejor oferta.
			double max_ponderado = -1.0;
			PedidoEfectivoValue ganadora = null;
			
		ArrayList<PedidoEfectivoValue> offers = dao.darOfertas(idPedidoOferta);
		for (PedidoEfectivoValue o : offers) 
		{
			double calif = o.getCalif();
			double coef_price = o.getCoeficientePrecios();
			double ponderado = Wcalif*(calif/10) + Wprice*(coef_price);
			if(ponderado > max_ponderado)
			{
				max_ponderado = ponderado;
				ganadora = o;
			}
		}
    	
		return ganadora;
	}
	
	
}
