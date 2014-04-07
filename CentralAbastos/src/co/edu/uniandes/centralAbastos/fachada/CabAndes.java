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

import java.util.ArrayList;

import co.edu.uniandes.centralAbastos.dao.ConsultaDAO;
import co.edu.uniandes.centralAbastos.dao.DAOAlmacen;
import co.edu.uniandes.centralAbastos.dao.DAOPedido;
import co.edu.uniandes.centralAbastos.dao.DAOPedidoLocal;
import co.edu.uniandes.centralAbastos.dao.DAOPedidosEfectivos;
import co.edu.uniandes.centralAbastos.dao.DAOPedidosEfectivos.proveedorDetallesValue;
import co.edu.uniandes.centralAbastos.dao.DAOPedidosOferta;
import co.edu.uniandes.centralAbastos.dao.DAOProducto;
import co.edu.uniandes.centralAbastos.vos.AlmacenValue;
import co.edu.uniandes.centralAbastos.vos.ItemInventarioValue;
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
    
    // -----------------------------------------------------------------
    // Singleton
    // -----------------------------------------------------------------


    /**
     * Instancia �nica de la clase
     */
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
		this.ruta = ruta;
		modPedidoDeOferta = new ModPedidoDeOferta(new DAOPedidosOferta(ruta));
		modAlmacen = new ModAlmacen(new DAOAlmacen(ruta));
		modLocal = new ModLocal(new DAOAlmacen(ruta), new DAOPedidoLocal(ruta));
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
		ConsultaDAO dao = new ConsultaDAO(ruta);
		return dao.darListaSimple("tipos");
	}
	
	public ArrayList<String> darNombresProductos() throws Exception
	{
		ConsultaDAO dao = new ConsultaDAO(ruta);
		return dao.darListaSimple("productos");
	}
	public ArrayList<String> darPresentaciones() throws Exception
	{
		ConsultaDAO dao = new ConsultaDAO(ruta);
		return dao.darListaSimple("presentaciones");
	}
	
	
	
	public ArrayList<String> darCodigosBodegasAbiertas() throws Exception
	{
		ConsultaDAO dao = new ConsultaDAO(ruta);
		return dao.darListaSimple("bodegasAbiertas");
	}
	
	public ArrayList<String> darCodigosBodegasCerradas() throws Exception
	{
		ConsultaDAO dao = new ConsultaDAO(ruta);
		return dao.darListaSimple("bodegasCerradas");
	}
	
	public ArrayList<String> darLocales() throws Exception
	{
		ConsultaDAO dao = new ConsultaDAO(ruta);
		return dao.darListaSimple("locales");
	}
	
	/**
	 * Método que da los códigos de todas las bodegas</br>
	 * @return ArrayList que contiene Strings con los códigos de todas las bodegas
	 * @throws Exception
	 */
	public ArrayList<String> darCodigosBodegas() throws Exception
	{
		ConsultaDAO dao = new ConsultaDAO(ruta);
		return dao.darListaSimple("bodegas");
	}
	
	public ArrayList<String> darUsuarios() throws Exception
	{
		ConsultaDAO dao = new ConsultaDAO(ruta);
		return dao.darListaSimple("usuarios");
	}
	
	public ArrayList<String> darIdsPedidosEfectivos() throws Exception
	{
		ConsultaDAO dao = new ConsultaDAO(ruta);
		return dao.darListaSimple("pedidosEfectivos");
	}
	
	public ArrayList<ProductosValue> darResultadoBusquedaProductos(String parametros) throws Exception
	{
		DAOProducto dao = new DAOProducto(ruta);
		return dao.darProductos(parametros);
	}
	
	public ArrayList[] darUsuariosPorTipo() throws Exception
	{
		ConsultaDAO dao = new ConsultaDAO(ruta);
		return dao.darUsuariosPorTipo();
	}
	
	public ArrayList<ArrayList<String>> darResultadoBusquedaPedidos(String tipoUsuario, String nombreUsuario, String parametros) throws Exception
	{
		DAOPedido dao = new DAOPedido(ruta);
		ArrayList<ArrayList<String>> respuesta = new ArrayList<ArrayList<String>>();
		ArrayList<PedidosValue> listaInicial = dao.darPedidos(tipoUsuario, nombreUsuario, parametros);
		for(int i = 0; i < listaInicial.size(); i++)
			respuesta.add(listaInicial.get(i).toArrayList());
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
		DAOAlmacen dao = new DAOAlmacen(ruta);


		ArrayList<ArrayList<String>> respuesta = new ArrayList<ArrayList<String>>();
		for(AlmacenValue av : dao.darInformacionBodegas())
			respuesta.add(av.toArrayList());
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
		 DAOPedidosEfectivos daoPE = new DAOPedidosEfectivos(ruta);
		 PedidoEfectivoValue pedidoEntrante = daoPE.darPedidoEfectivo(idPedidoEfectivo);
		 System.out.println(pedidoEntrante.getFechaExpiracion());
		 if(this.asignarEnBodegas(pedidoEntrante))
		 {
			 daoPE.registrarFechaLlegada(idPedidoEfectivo);
			 proveedorDetallesValue pdv = daoPE.darDetallesProveedorDePedidoEfectivo(idPedidoEfectivo);
			 // calficar al proveedor
			 int nuevaCal = (5-pdv.diasTarde) > 0 ? 5-pdv.diasTarde : 0 ;
			 int cal = ( (pdv.numEntregas/(pdv.numEntregas+1))*pdv.calificacion +  (1/(pdv.numEntregas+1))*nuevaCal ) ;
			 daoPE.updateValoresProveedor(pdv.correo, cal);
		 }
		 
		 
		 
		
			 
	}
		 
		 // Req 3.2- 3.4Iter 2
		

		 /**
		  * Requerimiento 2.2
		  * @param idPedidoLocal
		  * @param idBodega
		  * @param nombProducto
		  * @param pesoCaja
		  * @param cantidad_Cajas_Pedido
		  * @param fechaExp
		  * @return
		 * @throws Exception 
		  */

		 public boolean enviarPedidoAlLocal( String idPedidoLocal, String idBodega, String nombProducto, double pesoCaja, int cantidad_Cajas_Pedido, String fechaExp ) throws Exception

		 {
			 // Sacar el id del local.
			 
			 String idLocal = modLocal.darCodigoLocalSegunPedido(idPedidoLocal);
			 
			 // descontarlas de la bodega.
			 modAlmacen.descontarExistenciasDeBodega(idBodega, nombProducto, pesoCaja, fechaExp, cantidad_Cajas_Pedido);
			 
			 //  adicionarlas al local
			return  modAlmacen.adicionarExistenciasEnItemInventario(idLocal, nombProducto, pesoCaja, fechaExp, cantidad_Cajas_Pedido);
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
		 
		 
		 // Iteracion # 3
		 /**
		  * Asigna el pedido a bodegas.
		  */
		 private boolean asignarEnBodegas(PedidoEfectivoValue pedidoEntrante) throws Exception
		 {
			 return modAlmacen.asignarEnBodegas(pedidoEntrante);
		 }

		/**
		 * Cierra la bodega cuyo código entra como parámetro</br>
		 * <b>pre: <b>La bodega debe existir, y estar abierta </br>
		 * <b>post: <b>La bodega ahora está cerrado
		 * @param codigo: ID único de la bodega
		 */
		public void cerrarBodega(String codigo) throws Exception
		{
			modAlmacen.cerrarBodega(codigo);
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
