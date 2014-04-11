package co.edu.uniandes.centralAbastos.fachada;

import java.util.ArrayList;

import co.edu.uniandes.centralAbastos.dao.DAOAlmacen;
import co.edu.uniandes.centralAbastos.dao.DAOPedidoLocal;
import co.edu.uniandes.centralAbastos.excepciones.TransactionFailedException;
import co.edu.uniandes.centralAbastos.vos.AlmacenValue;
import co.edu.uniandes.centralAbastos.vos.ItemInventarioValue;
import co.edu.uniandes.centralAbastos.vos.ItemPedidoValue;
import co.edu.uniandes.centralAbastos.vos.PedidoEfectivoValue;

/**
 * @author Personal
 *
 */
/**
 * @author Personal
 *
 */
public class ModAlmacen 
{
	
	private DAOAlmacen dao;
	private ModLocal modLocal;

	public ModAlmacen(DAOAlmacen dao, ModLocal modLocal) 
	{
		this.dao = dao;
		this.modLocal = modLocal;
	}
	
	boolean agregarBodega(AlmacenValue value) throws Exception
	{
		dao.establecerConexion();
		boolean ans = dao.agregarBodega(value);
		dao.closeConnection();
		return ans;
	}
	
	public DAOAlmacen getDaoAlmacen()
	{
		return dao;
	}
	
	////////////////////////////////////////////////////////////////////
	///////////////// NUEVA IMPLEMENTACION V.1 /////////////////////////
	///////////////////////////////////////////////////////////////////
	
	// Iter 2 - req 2
	/**
	 * Req 2.1
	 * Este metodo asigna un pedido entrante a las bodegas. 
	 * @param pedidoEntrante
	 * @throws Exception
	 */
	public boolean asignarEnBodegas(PedidoEfectivoValue pedidoEntrante) throws Exception
	{
		int num_cajas = pedidoEntrante.getCantidad();
		double Wcaja = pedidoEntrante.getPresentacion();
		ArrayList<AlmacenValue> bodegasDisp = dao.darBodegasXTipo(pedidoEntrante.getTipoProducto(), "" , " for update " ); // TODO done
		dao.selectForUpdate("item_inventario"); // lock la tabla
		
		for (AlmacenValue bod : bodegasDisp) 
		{
			double capDisp = bod.getCapacidad() - bod.getCantidad_kg();
			capDisp -= num_cajas*Wcaja;
			System.out.println(capDisp);
			if( capDisp >= 0 ){
				dao.insertarEnInventario(pedidoEntrante.getProducto(), pedidoEntrante.getTipoProducto(), bod.getCodigo(), Wcaja, num_cajas, pedidoEntrante.getFechaExpiracion());
				dao.updateAlmacen(num_cajas*Wcaja , bod.getCodigo()); // TODO : transaccionalidad 
				
				return true;
			}
		}
		
		// parte el pedido y lo almacena en distintas bodegas donde vaya encontrando espacio 
		this.almacenarEnDistintasBodegas(pedidoEntrante, bodegasDisp, num_cajas, Wcaja);
		
		return false;
	}
	
	// Iter 3  
	/**
	 * Apoyo a req 1 y 2. <br>
	 * 
	 * Reasigna las existencias de un almacen especificado por parametro. Este trata de resignarlas todas. <br>
	 * @pre: El codigo del almacen debe ser el de una bodega.
	 * @param codBodega 
	 * @return  falso si no tiene espacio para reasignarlas o verdadero dlc.
	 * @throws Exception 
	 */
    private boolean reasignarExistencias(String codBodega) throws Exception
	{
		
    	AlmacenValue bodegaToMod = dao.darBodega(codBodega);
    	if(bodegaToMod.getCantidad_kg() == 0) return true;
    	ArrayList<ItemInventarioValue> existenciasBodega = dao.darExistenciasDeUnaBodega(codBodega, " for update "); // TODO interacciones transaccionalidad
    	dao.selectForUpdate(" almacen ");// lock
    	for (ItemInventarioValue it : existenciasBodega) {
			System.out.println(existenciasBodega.size() + " " + it.getNomb_producto());
		}
		
    	double capTotalDisp = dao.darCapacidadTotalDisp(codBodega, bodegaToMod.getTipoProducto() ); 
    	System.out.println("Capacidad total disponible en otras bodegas " + capTotalDisp);
    	boolean resp = false;
    	if( (capTotalDisp-bodegaToMod.getCantidad_kg()) >= 0 ) // Hay espacio disponible en las bodegas de cabandes para almacenar el pedido. 
    	{
    		// Se almacena el pedido. 
    		for (ItemInventarioValue iiv : existenciasBodega) {
   			 	resp = this.almacenarItemEnOtrasBodegasDesdeUnaBodega(iiv, bodegaToMod.getTipoProducto());
    		}
    	}
    	System.out.println("Respuesta en reasignar existencias " + resp);
    	return resp;
	}
   
	// metodo de apoyo para el de arriba
		    /**
		     * Almacena un item inventario en bodegas, utilizando la misma politica para asignarBodegas a un pedido entrane
		     * @param item
		     * @param tipoProductoBodega es el tipo de producto de la bodega que se va a cerrar, derrumbar, mantenimiento, etc.
		     * @throws Exception 
		     */
		    private boolean almacenarItemEnOtrasBodegasDesdeUnaBodega(ItemInventarioValue item , String tipoProductoBodega) throws Exception
		    {
		    	
				int num_cajas = item.getCantidad();
				double Wcajas = item.getPresentacion();
				
				// trata de almacenarlo simplemente buscando donde cabe y listo.
				ArrayList<AlmacenValue> bodegasDisponibles = dao.darBodegasXTipo(tipoProductoBodega, item.getCod_almacen(), " for update");
				
				for (AlmacenValue bod : bodegasDisponibles) {
					double d = (bod.getCapacidad()-bod.getCantidad_kg()) - num_cajas*Wcajas;
					if(d >= 0)
					{
						
						int up = dao.updateCantidadCajas(item.getNomb_producto(), Wcajas, num_cajas, bod.getCodigo(), item.getFechaExp());
						if(up == 0)
							dao.insertarEnInventario(item.getNomb_producto(), tipoProductoBodega, bod.getCodigo(), Wcajas, num_cajas, item.getFechaExp());
						
						dao.updateAlmacen( num_cajas*Wcajas , bod.getCodigo());
						
						return true;
					}
				}
				System.out.println("Método almacenarEnOtrasBodegas");
				/**  */
				return this.almacenarEnDistintasBodegas(item, bodegasDisponibles, num_cajas, Wcajas);
				
				
		    }
		    
		    
		    /**
		     * Almacena un objeto en distintas bodegas. Especificamente el metodo toma un grupo de bodegas disponibles y a cada una le agrega una parte del producto en items, ya sea proveniente de un pedidoEntrante o de un ItemInventario 
		     * <br> @pre : Solo sirve si la regla sobre el parametro o se cumple.
		     * @param o - es un objeto in  {PedidoEfectivoValue , ItemInventario}
		     * @param bodegasDisponibles
		     * @param num_cajas
		     * @param Wcajas
		     * @throws Exception
		     * @return - El numero de cajas de "Wcajas" kg que faltaron por almacenar. En caso de que no se cupla la pre, retorna el numero -10000.0
		     */
    
		    private boolean almacenarEnDistintasBodegas(Object o , ArrayList<AlmacenValue> bodegasDisponibles, int num_cajas, double Wcajas ) throws Exception
		    {
		    	int capRequeridaXcajas = num_cajas; // Numero de cajas donde hay espacio
				double capDisp_kg = -10000.0 ;
				
		    	if(o instanceof PedidoEfectivoValue)
				{	
					o = (PedidoEfectivoValue) o;
			    	for (AlmacenValue bodega : bodegasDisponibles)
			    	{
						capDisp_kg = bodega.getCapacidad()-bodega.getCantidad_kg() ;
						int capDispXcajas = (int) ( capDisp_kg/Wcajas ) ;
						int r = capRequeridaXcajas-capDispXcajas; // lo que me falta por almacenar 
						if( r > 0 )
						{
							
							// actualizo item_inventario en la bodega que cumple
							int up = dao.updateCantidadCajas(((PedidoEfectivoValue) o).getProducto() , Wcajas, capDispXcajas, bodega.getCodigo(), ((PedidoEfectivoValue) o).getFechaExpiracion());
							if(up == 0)
								dao.insertarEnInventario(((PedidoEfectivoValue) o).getProducto(),bodega.getTipoProducto(), bodega.getCodigo(), Wcajas, capDispXcajas, ((PedidoEfectivoValue) o).getFechaExpiracion());
							

							dao.updateAlmacen(capDispXcajas*Wcajas, bodega.getCodigo()); // Esta 100% llena
							capRequeridaXcajas = r;
							 

						}
						else // r<=0  
						{
							int up = dao.updateCantidadCajas(((PedidoEfectivoValue) o).getProducto() , Wcajas, capRequeridaXcajas , bodega.getCodigo(),((PedidoEfectivoValue) o).getFechaExpiracion());
							if(up == 0)
								dao.insertarEnInventario(((PedidoEfectivoValue) o).getProducto(),bodega.getTipoProducto(), bodega.getCodigo(), Wcajas , capRequeridaXcajas , ((PedidoEfectivoValue) o).getFechaExpiracion());
							

							dao.updateAlmacen( capRequeridaXcajas*Wcajas , bodega.getCodigo());
							return true;

						}
			    	}
			    	return false;
		    
				}
				else //if ( o instanceof ItemInventarioValue)
				{
					o = ((ItemInventarioValue) o);
					int r = 0;
					for (AlmacenValue bodega : bodegasDisponibles)
			    	{
						capDisp_kg = bodega.getCapacidad()-bodega.getCantidad_kg() ;
						int capDispXcajas = (int) ( capDisp_kg/Wcajas) ;
						r = capRequeridaXcajas-capDispXcajas; // Espacio disponible en terminos de cajas con presentacion Wcajas
						if( r > 0 )
						{
							
							// actualizo item_inventario en la bodega que cumple
							int up = dao.updateCantidadCajas(((ItemInventarioValue) o).getNomb_producto() , Wcajas, capDispXcajas, bodega.getCodigo(), ((ItemInventarioValue)o).getFechaExp());
							if(up == 0)
								dao.insertarEnInventario(((ItemInventarioValue) o).getNomb_producto(),bodega.getTipoProducto(), bodega.getCodigo(), Wcajas, capDispXcajas, ((ItemInventarioValue) o).getFechaExp());
							
							dao.updateAlmacen(capDispXcajas*Wcajas, bodega.getCodigo()); // Esta 100% llena
							
							capRequeridaXcajas = r ;
						}
						else // r<=0  
						{
							int up = dao.updateCantidadCajas(((ItemInventarioValue) o).getNomb_producto() , Wcajas, capRequeridaXcajas , bodega.getCodigo(),((ItemInventarioValue)o).getFechaExp() );
							if(up == 0)
								dao.insertarEnInventario(((ItemInventarioValue) o).getNomb_producto(),bodega.getTipoProducto(), bodega.getCodigo(), Wcajas , capRequeridaXcajas , ((ItemInventarioValue) o).getFechaExp());
							

							dao.updateAlmacen(capRequeridaXcajas*Wcajas , bodega.getCodigo());
							return true;
						}
			    	}
					return false;
					
				}
				
		    }
		    
		/** Implementacion de requerimients 2.2-2.4 **/ 

		
		    // Req 2.2
		    /**
		     * 
		     * @param idBodega
		     * @param producto
		     * @param pesoCaja
		     * @param cajasSolicitadas
		     * @return
		     * @throws Exception 
		     */
		    public void descontarExistenciasDeBodega(String idBodega, String producto, double pesoCaja, String fechaExpProducto, int cajasSolicitadas ) throws Exception
		    {
		    	dao.updateCantidadCajas(producto, pesoCaja, -cajasSolicitadas ,idBodega,fechaExpProducto);
		   
		    	dao.updateAlmacen(-cajasSolicitadas*pesoCaja, idBodega);
		    }

		    
		    /**
		     * 
		     * @param idAlmacen - es el id del almacen donde se quieren guardar las existencias. idAlmacen es de una bodega o de un local 
		     * @param producto
		     * @param pesoCaja
		     * @param fechaExpProducto
		     * @param cajasToAdd - el numero de cajas para adicionar.
		     * @return
		     * @throws Exception 
		     */
		    public boolean adicionarExistenciasEnItemInventario(String idAlmacen, String producto, double pesoCaja, String fechaExpProducto, int cajasToAdd) throws Exception
		    {
		    	int up = dao.updateCantidadCajas(producto, pesoCaja, cajasToAdd, idAlmacen, fechaExpProducto);
		    	if( up == 0 ){
		    		return dao.insertarEnInventario(producto, dao.darTipoProducto(producto), idAlmacen, pesoCaja, cajasToAdd, fechaExpProducto);
		    	}
		    	
		    	dao.updateAlmacen(cajasToAdd*pesoCaja, idAlmacen);
		    	
		    	return true;
		    }
		    
		    

		    /**
		     * El metodo saca las existencias las existencias del inventario de bodegas, que se solicitan por los items del pedido
		     * @pre : Las existencias en bodega son suficientes para suplir los items demandados. 
		     * @param - itemBodegas:  son las existencias de las que se va a sacar los items solicitados.
		     * @param - itemSolicitados: son los items que se van a extraer. 
		     * @throws Exception 
		     */
		    public void sacarExistenciasSegunPedidoComprador(ArrayList<ItemInventarioValue> itemsBodegas , ArrayList<ItemPedidoValue> itemsSolicitados) throws Exception
		    {
		    	 // O(n^2)
		    	 for (ItemPedidoValue itemPedido : itemsSolicitados) {
		    		 if(! removerExistenciasDeUnaTupla(itemPedido, itemsBodegas) )
		    			 removerExistenciasDeVariasTuplas(itemPedido, itemsBodegas);
				}
		    }
		    
		    /**
		     * Remueve existencias de una tupla, en item Inventario,  para proporcionar el insumo requerido por itemPedido
		     * @return true si logro encontrar la tupla, o false dlc.
		     * @throws Exception 
		     */
		    private boolean removerExistenciasDeUnaTupla(ItemPedidoValue itemPedido , ArrayList<ItemInventarioValue> itemsBodegas) throws Exception
		    {
		    	for(ItemInventarioValue itemInvTemp : itemsBodegas)
		    	{
		    		if( itemInvTemp.getPresentacion() == itemPedido.getPesoCaja() && itemInvTemp.getCantidad() >= itemPedido.getCantidad() && itemInvTemp.getNomb_producto().equals(itemPedido.getNombProducto()) )
		    		{
		    			this.descontarExistenciasDeBodega(itemInvTemp.getCod_almacen(), itemPedido.getNombProducto(), itemPedido.getPesoCaja(), itemInvTemp.getFechaExp(), itemPedido.getCantidad());
		    			return true;
		    		}
		    	}
		    	return false;
		    }
		    
		    /**
		     * Remueve existencias de varias tuplas para proporcionar el insumo requerido por itemPedido.
		     * @pre: Hay suficiente insumo para satisfacer el itemPedido.
		     * @throws Exception 
		     */
		    private void removerExistenciasDeVariasTuplas( ItemPedidoValue itemPedido , ArrayList<ItemInventarioValue> itemsBodegas ) throws Exception
		    {
		    	int boxesToRemove = itemPedido.getCantidad();
		    	double Wcaja = itemPedido.getPesoCaja();
		    	for (ItemInventarioValue tempIIV : itemsBodegas) {
					if(tempIIV.getNomb_producto().equals(itemPedido.getNombProducto()) && tempIIV.getPresentacion() == Wcaja && tempIIV.getCantidad() > 0 )
					{
						int boxesAvailable = tempIIV.getCantidad(); // Cajas available en cada tupla de itemInv
						if( boxesToRemove > boxesAvailable )
						{
							descontarExistenciasDeBodega(tempIIV.getCod_almacen(), tempIIV.getNomb_producto(), Wcaja, tempIIV.getFechaExp(),  boxesAvailable  );
							boxesToRemove -= boxesAvailable;
						}
						else if( boxesToRemove <= boxesAvailable )
						{
							descontarExistenciasDeBodega(tempIIV.getCod_almacen(), tempIIV.getNomb_producto(), Wcaja, tempIIV.getFechaExp(),  boxesToRemove  );
							boxesToRemove -= boxesAvailable;
							break;
						}
					}
		    	}
		    	
		    	if( boxesToRemove > 0 )
		    		throw new Exception("Esto no debe pasar");
		    }
		    
		    
		    //// PILAS QUE LOS METODOS NO SON LO MISMOS QUE ARRIBA YA QUE EN LOS QUE VIENEN ACONTINUACION, HAY MOVIMIENTO HACIA EL LOCAL.
		    /**
		     * El metodo saca las existencias las existencias del inventario de bodegas, que se solicitan por los items del pedido
		     * @pre : Las existencias en bodega son suficientes para suplir los items demandados. 
		     * @param - itemBodegas:  son las existencias de las que se va a sacar los items solicitados.
		     * @param - itemSolicitados: son los items que se van a extraer. 
		     * @throws Exception 
		     */
		    public void moverExistenciasAlLocal(String idLocal,ArrayList<ItemInventarioValue> itemsBodegas , ArrayList<ItemPedidoValue> itemsSolicitados) throws Exception
		    {
		    	 // O(n^2)
		    	 for (ItemPedidoValue itemPedido : itemsSolicitados) {
		    		 if(! movilizarExistenciasDesdeUnaTupla(idLocal, itemPedido, itemsBodegas) )
		    			 movilizarExistenciasDesdeVariasTuplas( idLocal, itemPedido, itemsBodegas);
				}
		    }
		    
		    /**
		     * Remueve existencias de una tupla, en item Inventario,  para proporcionar el insumo requerido por itemPedido
		     * @return true si logro encontrar la tupla, o false dlc.
		     * @throws Exception 
		     */
		    private boolean movilizarExistenciasDesdeUnaTupla(String idLocal, ItemPedidoValue itemPedido , ArrayList<ItemInventarioValue> itemsBodegas) throws Exception
		    {
		    	for(ItemInventarioValue itemInvTemp : itemsBodegas)
		    	{
		    		if( itemInvTemp.getPresentacion() == itemPedido.getPesoCaja() && itemInvTemp.getCantidad() >= itemPedido.getCantidad() && itemInvTemp.getNomb_producto().equals(itemPedido.getNombProducto()) )
		    		{
		    			this.descontarExistenciasDeBodega(itemInvTemp.getCod_almacen(), itemPedido.getNombProducto(), itemPedido.getPesoCaja(), itemInvTemp.getFechaExp(), itemPedido.getCantidad());
		    			return true;
		    		}
		    	}
		    	return false;
		    }
		    
		    /**
		     * Remueve existencias de varias tuplas para proporcionar el insumo requerido por itemPedido.
		     * @pre: Hay suficiente insumo para satisfacer el itemPedido.
		     * @throws Exception 
		     */
		    private void movilizarExistenciasDesdeVariasTuplas( String idLocal, ItemPedidoValue itemPedido , ArrayList<ItemInventarioValue> itemsBodegas ) throws Exception
		    {
		    	int boxesToRemove = itemPedido.getCantidad();
		    	double Wcaja = itemPedido.getPesoCaja();
		    	for (ItemInventarioValue tempIIV : itemsBodegas) {
					if(tempIIV.getNomb_producto().equals(itemPedido.getNombProducto()) && tempIIV.getPresentacion() == Wcaja && tempIIV.getCantidad() > 0 )
					{
						int boxesAvailable = tempIIV.getCantidad(); // Cajas available en cada tupla de itemInv
						if( boxesToRemove > boxesAvailable )
						{
							descontarExistenciasDeBodega(tempIIV.getCod_almacen(), tempIIV.getNomb_producto(), Wcaja, tempIIV.getFechaExp(),  boxesAvailable  );
							modLocal.adicionarExistenciasEnItemInventario(idLocal, tempIIV.getNomb_producto(), Wcaja, tempIIV.getFechaExp(), boxesAvailable);
							boxesToRemove -= boxesAvailable;
						}
						else if( boxesToRemove <= boxesAvailable )
						{
							descontarExistenciasDeBodega(tempIIV.getCod_almacen(), tempIIV.getNomb_producto(), Wcaja, tempIIV.getFechaExp(),  boxesToRemove  );
							modLocal.adicionarExistenciasEnItemInventario(idLocal, tempIIV.getNomb_producto(), Wcaja, tempIIV.getFechaExp(), boxesToRemove );
							boxesToRemove -= boxesAvailable;
							break;
						}
					}
		    	}
		    	
		    	if( boxesToRemove > 0 )
		    		throw new Exception("Esto no debe pasar");
		    }
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
////   END  ////////////////////////////////////////////////////
///////////////// NUEVA IMPLEMENTACION V.1 END /////////////////////////
///////////////////////////////////////////////////////////////////
    /**
     * 
     * @param codigo
     * @return
     * @throws Exception
     */
	public boolean eliminarBodega(String codigo) throws Exception
	{
		// modificado para que incluya lo el movimiento de existencias
		dao.establecerConexion();
		dao.setAutocommit(false);
		try
		{
			boolean resp = this.reasignarExistencias(codigo);
			
			if(resp)
			{
				dao.eliminarBodega(codigo);
				System.out.println("commit");
				dao.commit();
				dao.setAutocommit(true);
				dao.closeConnection();
				return resp;
			}		
			else
			{
				System.out.println("Rollback");
				dao.rollback();
				return resp;
			}
			
		}
		catch(Exception e)
		{
			dao.rollback();
			dao.setAutocommit(true);
			dao.closeConnection();
			throw new TransactionFailedException("Hubo un error a la hora de realizar la transacción ", e);
		}
	}

	public boolean cerrarBodega(String codigo) throws Exception 
	{
		// modificado para que incluya lo el movimiento de existencias
		dao.establecerConexion();
		dao.setAutocommit(false);
		try
		{
			boolean resp = this.reasignarExistencias(codigo);
			
			if(resp)
			{
				dao.cerrarBodega(codigo);
				System.out.println("commit");
				dao.commit();
				dao.setAutocommit(true);
				dao.closeConnection();
				return resp;
			}		
			System.out.println("rollback");
			dao.rollback();
			dao.setAutocommit(true);
			dao.closeConnection();
			return resp;
		}
		catch(Exception e)
		{
			System.out.println("rollback");
			dao.rollback();
			dao.setAutocommit(true);
			dao.closeConnection();
			throw new TransactionFailedException("Hubo un error a la hora de realizar la transacción ", e);
		}
		
	}
	
	public void abrirBodega(String codigo) throws Exception
	{
		dao.establecerConexion();
		dao.abrirBodega(codigo);
		dao.commit();
		dao.closeConnection();
	}

}
