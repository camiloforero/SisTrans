package co.edu.uniandes.centralAbastos.fachada;

import java.sql.SQLException;
import java.util.ArrayList;

import co.edu.uniandes.centralAbastos.dao.DAOPedido;
import co.edu.uniandes.centralAbastos.dao.DAOPedidosOferta;
import co.edu.uniandes.centralAbastos.vos.InfoItemsSatisfacerValue;
import co.edu.uniandes.centralAbastos.vos.ItemPedidoValue;

public class ModPedidos 
{
	DAOPedido daoPedidos;
	
	
	//-------------------------------
		// Constructor
		//-------------------------------
		public ModPedidos(DAOPedido dao) {
			
			this.daoPedidos = dao;
		}


		public DAOPedido getDaoPedidos() {
			return daoPedidos;
		}
		
		// requerimiento 4 iteracion 3
		/**
		 * @throws SQLException 
		 * 
		 */
		
}
