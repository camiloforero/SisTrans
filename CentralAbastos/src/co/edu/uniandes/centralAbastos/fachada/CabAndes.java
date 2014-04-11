/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: VideoAndes.java,v 1.10 
 * Universidad de los Andes (Bogot� - Colombia)
 * Departamento de Ingenier�a de Sistemas y Computaci�n 
 *
 * Ejercicio: VideoAndes
 * Autor: Juan Diego Toro - 11-Feb-2010
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package co.edu.uniandes.centralAbastos.fachada;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import co.edu.uniandes.centralAbastos.dao.ConsultaDAO;
import co.edu.uniandes.centralAbastos.dao.DAOAlmacen;
import co.edu.uniandes.centralAbastos.dao.DAOPedido;
import co.edu.uniandes.centralAbastos.dao.DAOPedidoLocal;
import co.edu.uniandes.centralAbastos.dao.DAOPedidosEfectivos;
import co.edu.uniandes.centralAbastos.dao.DAOPedidosEfectivos.proveedorDetallesValue;
import co.edu.uniandes.centralAbastos.dao.DAOPedidosOferta;
import co.edu.uniandes.centralAbastos.dao.DAOProducto;
import co.edu.uniandes.centralAbastos.vos.AlmacenValue;
import co.edu.uniandes.centralAbastos.vos.InfoItemsSatisfacerValue;
import co.edu.uniandes.centralAbastos.vos.InfoTiempoEntregaValue;
import co.edu.uniandes.centralAbastos.vos.ItemInventarioValue;
import co.edu.uniandes.centralAbastos.vos.ItemPedidoValue;
import co.edu.uniandes.centralAbastos.vos.PedidoEfectivoValue;
import co.edu.uniandes.centralAbastos.vos.PedidoOfertaValue;
import co.edu.uniandes.centralAbastos.vos.PedidosValue;
import co.edu.uniandes.centralAbastos.vos.ProductosValue;

/**
 * Clase VideoAndes, que representa la fachada de comunicaci�n entre
 * la interfaz y la conexi�n con la base de datos. Atiende todas
 * las solicitudes.
 */
public class CabAndes 
{
	/**
	 * Conexión con la clase que maneja la base de datos
	 */
	private String ruta;
	
	/**
	 * Referencia al modulo de pedidoDeOferta 
	 */
	private ModPedidoDeOferta modPedidoDeOferta;
	
	private ModAlmacen modAlmacen;
	
	private ModLocal modLocal;
	
	private ModPedidos modPedidos;
	private ConsultaDAO dao;
	
	private DAOProducto daoProducto;
	
	private DAOPedido daoPedido;
	
	private DAOAlmacen daoAlmacen;
    
    // -----------------------------------------------------------------
    // Singleton
    // -----------------------------------------------------------------


    /**
     * Instancia �nica de la clase
     */
	public final static String PEDIDO_COMPRADOR = "pedido_comprador";
	public final static String PEDIDO_LOCAL = "pedido_local";
    private static CabAndes instancia;
    
    /**
     * Devuelve la instancia �nica de la clase
     * @return Instancia �nica de la clase
     */
    public static CabAndes darInstancia( )
    {
        if( instancia == null )
        {
            instancia = new CabAndes( );
        }
        return instancia;
    }
	
	/**
	 * contructor de la clase. Inicializa el atributo dao.
	 */
	private CabAndes()
	{
		
	}
	
	/**
	 * inicializa el dao, dándole la ruta en donde debe encontrar
	 * el archivo properties.
	 * @param ruta ruta donde se encuentra el archivo properties
	 */
	public void inicializarRuta(String ruta)
	{
		if(this.ruta == null)
		{
			this.ruta = ruta;
			dao = new ConsultaDAO(ruta);
			daoPedido = new DAOPedido(ruta);
			daoProducto = new DAOProducto(ruta);
			daoAlmacen = new DAOAlmacen(ruta);
			modPedidoDeOferta = new ModPedidoDeOferta(new DAOPedidosOferta(ruta));
			modLocal = new ModLocal(daoAlmacen, new DAOPedidoLocal(ruta));
			modAlmacen = new ModAlmacen(daoAlmacen, modLocal);
			modPedidos = new ModPedidos(new DAOPedido(ruta));
		}
		
	}
	
    // ---------------------------------------------------
    // M�todos asociados a los casos de uso: Consulta
    // ---------------------------------------------------
    
	
	/**
	 * Da los tipos de producto que existen</br>
	 * @return Un ArrayList con los nombres de todos los productos que existen
	 * @throws Exception: 
	 */
	public ArrayList<String> darTipos() throws Exception
	{
		dao.establecerConexion();
		ArrayList<String> ans = dao.darListaSimple("tipos");
		dao.closeConnection();
		return ans;
	}
	
	public ArrayList<String> darNombresProductos() throws Exception
	{
		dao.establecerConexion();
		ArrayList<String> ans = dao.darListaSimple("productos");
		dao.closeConnection();
		return ans;
		
		
	}
	public ArrayList<String> darPresentaciones() throws Exception
	{
		dao.establecerConexion();
		ArrayList<String> ans = dao.darListaSimple("presentaciones");
		dao.closeConnection();
		return ans;
	}
	
	
	
	/**
	 * Da los códigos de todas las bodegas que están abiertas</br>
	 * @return ArrayList con los códigos solicitados
	 * @throws Exception
	 */
	public ArrayList<String> darCodigosBodegasAbiertas() throws Exception
	{
		dao.establecerConexion();
		ArrayList<String> ans = dao.darListaSimple("bodegasAbiertas");
		dao.closeConnection();
		return ans;		
	}
	
	public ArrayList<String> darCodigosBodegasCerradas() throws Exception
	{
		dao.establecerConexion();
		ArrayList<String> ans = dao.darListaSimple("bodegasCerradas");
		dao.closeConnection();
		return ans;
	}
	
	public ArrayList<String> darLocales() throws Exception
	{
		dao.establecerConexion();
		ArrayList<String> ans = dao.darListaSimple("locales");
		dao.closeConnection();
		return ans;
		
	}
	
	/**
	 * Método que da los códigos de todas las bodegas</br>
	 * @return ArrayList que contiene Strings con los códigos de todas las bodegas
	 * @throws Exception
	 */
	public ArrayList<String> darCodigosBodegas() throws Exception
	{
		dao.establecerConexion();
		ArrayList<String> ans = dao.darListaSimple("bodegas");
		dao.closeConnection();
		return ans;
	}
	
	/**
	 * Da una lista con los correos electrónicos de todos los usuarios</br>
	 * @return ArrayList con la lista solicitada
	 * @throws Exception
	 */
	public ArrayList<String> darUsuarios() throws Exception
	{
		dao.establecerConexion();
		ArrayList<String> ans = dao.darListaSimple("usuarios");
		dao.closeConnection();
		return ans;
	}
	
	public ArrayList<String> darIdsPedidosEfectivos() throws Exception
	{
		dao.establecerConexion();
		ArrayList<String> ans = dao.darListaSimple("pedidosEfectivos");
		dao.closeConnection();
		return ans;
	}
	
	public ArrayList<ProductosValue> darResultadoBusquedaProductos(String parametros) throws Exception
	{
		dao.establecerConexion();
		ArrayList<ProductosValue> ans = daoProducto.darProductos(parametros);
		dao.closeConnection();
		return ans;
	}
	
	public ArrayList[] darUsuariosPorTipo() throws Exception
	{
		dao.establecerConexion();
		ArrayList[] ans = dao.darUsuariosPorTipo();
		dao.closeConnection();
		return ans;
	}
	
	public ArrayList<ArrayList<String>> darResultadoBusquedaPedidos(String tipoUsuario, String nombreUsuario, String parametros) throws Exception
	{
		dao.establecerConexion();
		ArrayList<ArrayList<String>> respuesta = new ArrayList<ArrayList<String>>();
		ArrayList<PedidosValue> listaInicial = daoPedido.darPedidos(tipoUsuario, nombreUsuario, parametros);
		for(int i = 0; i < listaInicial.size(); i++)
			respuesta.add(listaInicial.get(i).toArrayList());
		dao.closeConnection();
		return respuesta;
	}
	
	/**
	 * Retorna una ArrayList que contiene más ArrayLists, las cuales contienen información sobre una bodega.</br>
	 * <b>pre: <b> </br>
	 * <b>post: <b>
	 * @return
	 * @throws Exception
	 */

	public ArrayList<ArrayList<String>> darInformacionBodegas() throws Exception
	{
		dao.establecerConexion();
		daoAlmacen = new DAOAlmacen(ruta);
		ArrayList<ArrayList<String>> respuesta = new ArrayList<ArrayList<String>>();
		for(AlmacenValue av : daoAlmacen.darInformacionBodegas())
			respuesta.add(av.toArrayList());
		dao.closeConnection();
		return respuesta;

	}
	
	

	
	
	/**
	 * Agrega una nueva bodega a CabAndes</br>
	 * <b>pre: <b> </br>
	 * <b>post: La bodega con los parámetros dados ha sido agregada satisfactoriamente <b>
	 * @param codigo
	 * @param capacidad
	 * @param cantidadKg
	 * @param tipoProducto
	 * @return
	 * @throws Exception
	 */
	public boolean agregarBodega(String codigo, double capacidad, String tipoProducto) throws Exception
	{
		return modAlmacen.agregarBodega(new AlmacenValue(codigo, capacidad, 0, tipoProducto));
	}
	
	public boolean eliminarBodega(String codigo) throws Exception
	{
		return modAlmacen.eliminarBodega(codigo);
	}
	
	//------------------- ITERACION #2 --------------------------------
	
	/**
	 * 
	 * @return Arraylist con los pedidos de oferta que ya estan cerrados y 
	 * @throws Exception 
	 */
	 public ArrayList<ArrayList<String>> darPedidosDeOfertaCerrados() throws Exception
	 {
		DAOPedidosOferta d = new DAOPedidosOferta(ruta);
		ArrayList<ArrayList<String>> respuesta = new ArrayList<ArrayList<String>>();
		ArrayList<PedidoOfertaValue> listaInicial = d.darPedidosOfertaCerrados(); 
		for(int i = 0; i < listaInicial.size(); i++)
			respuesta.add(listaInicial.get(i).toArrayList());
		return respuesta;
	 }
	 
	 /**
	  * (!) Ojo que siempre debe haber una oferta ganadora, asi que pilas porque no deberia haber null pointer.
	  * @param idPedidoOferta id del pedido, que esta cerrado, y se le quiere hallar el ganador.
	  * @return la oferta ganadora de un pedido_oferta especificado por parametro
	  * 
	  */
	 public PedidoEfectivoValue darOfertaGanadora(String idPedidoOferta) throws Exception
	 {
		 
		 PedidoEfectivoValue pev = modPedidoDeOferta.darOfertaGanadora(idPedidoOferta);
		 // agregar a a tabla de pedidos efectivos
		 DAOPedidosEfectivos d = new DAOPedidosEfectivos(this.ruta);
		 d.insertarPedidoEfectivo(pev.getId(), pev.getCorreo_p(), null);
		 // limpiar
		 
		 return pev;
	 }
	 
	 
	 	// ----- Requerimiento # 3 : Registrar movimientos -----
	 	
	 /**
		  * Registra la entrega de un proveedor. (RF 3.1)
		  * @param idPedidoEfectivo
		  * @throws Exception
		  */
	 public void registrarEntregaDeProveedor(String idPedidoEfectivo) throws Exception
	 {
		 dao.establecerConexion();
		 dao.setAutocommit(false);
		 boolean resp = false;
		 try{
			
			 // operacion
			 DAOPedidosEfectivos daoPE = new DAOPedidosEfectivos(ruta);
			 PedidoEfectivoValue pedidoEntrante = daoPE.darPedidoEfectivo(idPedidoEfectivo); 
			 System.out.println(pedidoEntrante.getFechaExpiracion());
			 if(this.asignarEnBodegas(pedidoEntrante))
			 {
				 daoPE.registrarFechaLlegada(idPedidoEfectivo); // TODO 
				 proveedorDetallesValue pdv = daoPE.darDetallesProveedorDePedidoEfectivo(idPedidoEfectivo, " for update");
				 // calficar al proveedor
				 int nuevaCal = (5-pdv.diasTarde) > 0 ? 5-pdv.diasTarde : 0 ;
				 int cal = ( (pdv.numEntregas/(pdv.numEntregas+1))*pdv.calificacion +  (1/(pdv.numEntregas+1))*nuevaCal ) ;
				 daoPE.updateValoresProveedor(pdv.correo, cal);
			 }
			 dao.commit();
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
			 dao.rollback();
		 }
		 finally{
			 dao.setAutocommit(true);
			 dao.closeConnection();
		 }
		
		 
		 
		 
		 
		 
		
			 
	}
		 
		 // Req 3.2- 3.4Iter 2
		

			 /**
			  * Requerimiento 3.2			  
			  * @param idPedidoLocal
			  * @param idBodega
			  * @param nombProducto
			  * @param pesoCaja
			  * @param cantidad_Cajas_Pedido
			  * @param fechaExp
			  * @return
			 * @throws Exception 
			  */
	
			 public void enviarPedidoAlLocal(ArrayList<ItemPedidoValue> itemsSolicitados, String idLocal ) throws Exception
			 {
				 ArrayList<ItemInventarioValue> itemsBodegas = modAlmacen.getDaoAlmacen().darExistenciasEnBodegas(" for update");

				 modAlmacen.moverExistenciasAlLocal(idLocal, itemsBodegas, itemsSolicitados);
			 }
			
			 /* Requerimiento 3.3 : vender productos en local*/
			 /**
			  * 
			  * @param nombProducto
			  * @param idLocal
			  * @param pesoVendido
			  * @return
			  */
			 public boolean RealizarVentaEnLocal( String nombProducto, String idLocal, double  pesoVendido )
			 {
				 try {
					return modLocal.venderProducto( nombProducto,idLocal, pesoVendido );
				} 
				 catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			 }
			 
			 // Requerimiento 3,4 : Movimiento del pedido comprador
			 
			 /**
			  * Envia el pedido al comprador mayorista segun los items solicitados por este.
			  */
			 public void enviarPedidoAlComprador(ArrayList<ItemPedidoValue> itemsSolicitados) throws Exception
			 {
				 ArrayList<ItemInventarioValue> itemsBodegas = modAlmacen.getDaoAlmacen().darExistenciasEnBodegas(" for update ");
				 dao.selectForUpdate(" almacen ");
				 modAlmacen.sacarExistenciasSegunPedidoComprador(itemsBodegas, itemsSolicitados);
			 }
			 
			 // Requerimiento 4 Registrar/SatisfacerPedido
			 
			 public void saisfacerPedidoComprador(ArrayList<ItemPedidoValue> itemsPedido) throws Exception
			 {
				 dao.establecerConexion();
				 dao.setAutocommit(false);
				 boolean resp = false;
				 try{
					
					 // operacion

						ArrayList<InfoItemsSatisfacerValue> existenciasSatisfacenInfo = modPedidos.getDaoPedidos().darItemsParaSatisfacer( itemsPedido.get(0).getIdPedido() );
						ArrayList<ItemPedidoValue> itemsRestantes = new ArrayList<ItemPedidoValue>();
						for( int i = 0; i < existenciasSatisfacenInfo.size() ; i++)
						{
							InfoItemsSatisfacerValue temp = existenciasSatisfacenInfo.get(i);
							if( temp.getRestante() > 0 ){
								itemsRestantes.add(new ItemPedidoValue(temp.getIdPedido(), temp.getNombProducto(), temp.getPesoCaja(), temp.getRestante()) ); // quedan los que deben pedir auxiliar.
								itemsPedido.remove( i ); // quedan los que se pueden satisfacer
							}
						}
						
						
						// satisfago lo que puedea del pedido, que en el mejor de los casos es todo.
						// LOCKS: ITEM INVENTARIO Y ALMACEN
						this.enviarPedidoAlComprador(itemsPedido);
						
						if(!itemsRestantes.isEmpty()){
							this.generarPedidoAux(itemsRestantes , PEDIDO_COMPRADOR);
						}
			
					 dao.commit();
				 }
				 catch(Exception e)
				 {
					 e.printStackTrace();
					 dao.rollback();
				 }
				 finally{
					 dao.setAutocommit(true);
					 dao.closeConnection();
				 }
			}
			 
		 /**
		  * Metodo de apoyo-
		  * @param itemsRestantes
		 * @throws SQLException 
		 * @throws ParseException 
		  */
			private void generarPedidoAux(ArrayList<ItemPedidoValue> itemsRestantes, String tipoPedido) throws SQLException, Exception{
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yy");
				if(tipoPedido.equals(PEDIDO_COMPRADOR)){
					
					Date maxFechaEntrega = sdf.parse("31/01/00");
					boolean sePudo = false;
					for (int i = 0 ; i < itemsRestantes.size() ; i++)
					{
						sePudo = false;
						ItemPedidoValue temp = itemsRestantes.get(i);
						ArrayList<InfoTiempoEntregaValue> infoEntregas = modPedidos.getDaoPedidos().darProyeccionDeExistencias(temp.getIdPedido(), temp.getNombProducto(), temp.getPesoCaja());
						// infoEntregas tiene maximo 2 y minimo 1. El tiene el ultimo y el primero de la tabla calculada para apoyar.
						InfoTiempoEntregaValue prim = infoEntregas.get(0);
						InfoTiempoEntregaValue ult =  infoEntregas.get(1);
						// el minimo en el rango.
						int minimo = prim.getZ()-ult.getS();
						int maximo = ult.getDelta();
						if( minimo >= temp.getCantidad() )
						{
							Date d = sdf.parse(prim.getFecha_in()); // fecha del primero
							if( d.compareTo(maxFechaEntrega) == 1 )
							{
								maxFechaEntrega=d;
							}
							sePudo = true;
						}
						else if(maximo >= temp.getCantidad() )
						{
							Date d = sdf.parse(ult.getFecha_in()); // fecha del ultimo 
							if( d.compareTo(maxFechaEntrega) == 1 )
							{
								maxFechaEntrega=d;
							}
							sePudo = true;
						}
						if( !sePudo )
							break;
					}
					
					if( !sePudo )
					{
						System.out.println("pidio lo restante al proveedor: Peor caso");
						this.pedirRestanteAProveedor(itemsRestantes);
					}
					else
					{
						// genero pedido comprador auxiliar.
						String nID = daoPedido.generateNewId();
						String correoComprador = daoPedido.consultarCorreoComprador(itemsRestantes.get(0).getIdPedido());
						
						dao.selectForUpdate(" items_pedido_comprador , pedidos_comprador "); // locks : 
						
						daoPedido.insertarNuevoPedidoComprador(correoComprador, sdf.format(maxFechaEntrega), nID);
						for(int i = 0 ; itemsRestantes.size() > i ; i++)
						{
							ItemPedidoValue temp = itemsRestantes.get(i);
							daoPedido.insertarItemPedidoComprador(temp, nID);
						}
						
					}
				}
				else if(tipoPedido.equals(PEDIDO_LOCAL))
				{
					
				}
			
			}
			
			
			
		// otro metodo de apoyo que comieza una nueva licitacion
		private void pedirRestanteAProveedor(ArrayList<ItemPedidoValue> itemsRestantes) throws Exception {
			
			// lock : ofertas
			for(int i = 0 ; i < itemsRestantes.size(); i++)
			{
				ItemPedidoValue temp = itemsRestantes.get(i);
				modPedidoDeOferta.getDao().insertarOfertaNueva(temp);
			}
		}

		// Iteracion # 3
		 /**
		  * Asigna el pedido a bodegas.
		  */
		 private boolean asignarEnBodegas(PedidoEfectivoValue pedidoEntrante) throws Exception
		 {
			 return   modAlmacen.asignarEnBodegas(pedidoEntrante);
		 }
		/**
		 * Cierra la bodega cuyo código entra como parámetro</br>
		 * <b>pre: <b>La bodega debe existir, y estar abierta </br>
		 * <b>post: <b>La bodega ahora está cerrado
		 * @param codigo: ID único de la bodega
		 */
		public boolean cerrarBodega(String codigo) throws Exception
		{
			return modAlmacen.cerrarBodega(codigo);
		}

		/**
		 * Abre la bodega cuyo código entra como parámetro</br>
		 * <b>pre: <b>La bodega debe existir, y estar cerrada </br>
		 * <b>post: <b>La bodega ahora está abierta
		 * @param codigo: ID único de la bodega
		 */
		public void abrirBodega(String codigo) throws Exception
		{
		
			modAlmacen.abrirBodega(codigo);
		}
}
