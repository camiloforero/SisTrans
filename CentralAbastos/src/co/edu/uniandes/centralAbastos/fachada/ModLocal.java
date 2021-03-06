package co.edu.uniandes.centralAbastos.fachada;

import java.sql.SQLException;
import java.util.ArrayList;

import co.edu.uniandes.centralAbastos.dao.DAOAlmacen;
import co.edu.uniandes.centralAbastos.dao.DAOPedidoLocal;
import co.edu.uniandes.centralAbastos.vos.AlmacenValue;
import co.edu.uniandes.centralAbastos.vos.ItemInventarioValue;
import co.edu.uniandes.centralAbastos.vos.ItemPedidoValue;

public class ModLocal 
{
	private DAOAlmacen daoAlm;
	private DAOPedidoLocal daoPedLoc;
	private ModAlmacen modAlmacen;

	public ModLocal(DAOAlmacen dao, DAOPedidoLocal d) 
	{
		this.daoAlm = dao;
		this.daoPedLoc = d;
		
	}
	
	public String darCodigoDeSuLocal(String correoAdmin) throws SQLException
    {
    	return daoAlm.darCodigoDelLocal(correoAdmin);
    }
	
	public String darCodigoLocalSegunPedido(String idPedidoLocal)
	{
		return daoPedLoc.darCodigoLocalSegunIdPedido(idPedidoLocal);
	}
	
	/**
	 * El peso vendido debe ser {0.5 , 1 , 1.5 ....... }
	 * @param itemToModify
	 * @param pesoVendido
	 * @return
	 * @throws Exception
	 */
	public boolean venderProducto( String nombreProducto, String idLocal , double pesoVendido ) throws Exception
	{
		// primeoro meterla en enteraa en una caja 
		
		// buscar en la bodegaDelAlmacen y asegurarme que hay peso suficiente. 
		AlmacenValue local = daoAlm.darLocal(idLocal);
		String tipoProd = daoAlm.darTipoProducto(nombreProducto);
		ArrayList<ItemInventarioValue> itemsLocal = daoAlm.darExistenciasDeUnaBodega(idLocal," for update "); // existencias del local
		
		if( (local.getCapacidad()-local.getCantidad_kg()) >= pesoVendido )
		{
			ItemInventarioValue itemARempacar = null;
			double X_porDespachar = pesoVendido;
			double X_restanteEnCaja = 0.0;
			
			
			
			for (ItemInventarioValue item : itemsLocal) 
			{
				double pesoDespachado = item.getCantidad()*item.getPresentacion();
				double r = X_porDespachar - pesoDespachado ; 
				if( r > 0 ) // todavia falta por despachar
				{
					descontarExistenciasDeLocal(nombreProducto, tipoProd , item.getPresentacion(), item.getCantidad(), item.getFechaExp(), idLocal); // saco todo de las cajas
					X_porDespachar = r;
				}
				else if( r <= 0.0 )
				{
					pesoDespachado = X_porDespachar;
					X_restanteEnCaja = -r ;
					descontarExistenciasDeLocal(nombreProducto, tipoProd , item.getPresentacion(), item.getCantidad(), item.getFechaExp(), idLocal); // sigo sacando todo de las cajas pero lo que queda lo reempaco.
					itemARempacar = item;
					break;
				}
			}
			
			if( itemARempacar != null )
				this.reempacar(itemARempacar, tipoProd, X_restanteEnCaja);
			
			return true;
		}
		else
			return false;
		
		
	}
	

	private void reempacar(ItemInventarioValue itemModificado, String tipoProducto, double kgAReempacar) throws Exception 
	{
		ArrayList<Double> idCajas = daoAlm.darPresentaciones(tipoProducto);
		
	}
	
	
	public void descontarExistenciasDeLocal(String nombProducto, String tipoProd, double wcajas, int cantidadCajas, String fechaExp, String idLocal) throws Exception
	{
		daoAlm.updateCantidadCajas(nombProducto, wcajas, -cantidadCajas ,idLocal,fechaExp);
    	daoAlm.updateAlmacen(-cantidadCajas*wcajas, idLocal);
	}
	
	public boolean adicionarExistenciasEnItemInventario(String idLocal, String producto, double pesoCaja, String fechaExpProducto, int cajasToAdd) throws Exception
	{
	    	int up = daoAlm.updateCantidadCajas(producto, pesoCaja, cajasToAdd, idLocal, fechaExpProducto);
	    	if( up == 0 ){
	    		return daoAlm.insertarEnInventario(producto, daoAlm.darTipoProducto(producto), idLocal, pesoCaja, cajasToAdd, fechaExpProducto);
	    	}
	    	
	    	daoAlm.updateAlmacen(cajasToAdd*pesoCaja, idLocal);
	    	
	    	return true;
	 }
	
	
	
	
	/**
	 * Consulta las existencias de un local.
	 * @param idLocal
	 * @return Tuplas de un local.
	 */
	public ArrayList<ItemInventarioValue> darExistenciasDeUnLocal(String idLocal, String forUp)
	{
		return daoAlm.darExistenciasDeUnaBodega(idLocal, forUp);

	}
	
}
