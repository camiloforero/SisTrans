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
import java.util.Collection;

import co.edu.uniandes.centralAbastos.dao.ConsultaDAO;
import co.edu.uniandes.centralAbastos.dao.DAOAlmacen;
import co.edu.uniandes.centralAbastos.dao.DAOPedido;
import co.edu.uniandes.centralAbastos.dao.DAOPedidosEfectivos;
import co.edu.uniandes.centralAbastos.dao.DAOPedidosEfectivos.proveedorDetallesValue;
import co.edu.uniandes.centralAbastos.dao.DAOPedidosOferta;
import co.edu.uniandes.centralAbastos.dao.DAOProducto;
import co.edu.uniandes.centralAbastos.vos.AlmacenValues;
import co.edu.uniandes.centralAbastos.vos.PedidoEfectivoValue;
import co.edu.uniandes.centralAbastos.vos.PedidoOfertaValue;
import co.edu.uniandes.centralAbastos.vos.PedidosValue;
import co.edu.uniandes.centralAbastos.vos.ProductosValue;
import co.edu.uniandes.centralAbastos.vos.VideosValue;

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
	private PedidoDeOferta modPedidoDeOferta;

    
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
		modPedidoDeOferta = new PedidoDeOferta(new DAOPedidosOferta(ruta));
	}
	
    // ---------------------------------------------------
    // M�todos asociados a los casos de uso: Consulta
    // ---------------------------------------------------
    
	/**
	 * m�todo que retorna los videos en orden alfab�tico.
	 * invoca al DAO para obtener los resultados.
	 * @return ArrayList lista con los videos ordenados alfabeticamente.
	 * @throws Exception pasa la excepci�n generada por el DAO
	 */
	public ArrayList<VideosValue> darVideosDefault() throws Exception
	{
		ConsultaDAO dao = new ConsultaDAO(ruta);
	    return dao.darVideosDefault();
	}	
	
	public ArrayList<String> darTipos() throws Exception
	{
		ConsultaDAO dao = new ConsultaDAO(ruta);
		return dao.darListaSimple("tipos");
	}
	
	public ArrayList<String> darProductos() throws Exception
	{
		ConsultaDAO dao = new ConsultaDAO(ruta);
		return dao.darListaSimple("productos");
	}
	public ArrayList<String> darPresentaciones() throws Exception
	{
		ConsultaDAO dao = new ConsultaDAO(ruta);
		return dao.darListaSimple("presentaciones");
	}
	public ArrayList<String> darLocales() throws Exception
	{
		ConsultaDAO dao = new ConsultaDAO(ruta);
		return dao.darListaSimple("locales");
	}
	public ArrayList<String> darBodegas() throws Exception
	{
		ConsultaDAO dao = new ConsultaDAO(ruta);
		return dao.darListaSimple("bodegas");
	}
	
	public ArrayList<String> darUsuarios() throws Exception
	{
		ConsultaDAO dao = new ConsultaDAO(ruta);
		return dao.darListaSimple("usuarios");
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

	public Collection darProductosTest() 
	{
		ArrayList<String> respuesta = new ArrayList<String>();
		respuesta.add("leche");
		respuesta.add("hummus");
		return respuesta;
	}
	
	//------------------- metodos pedro --------------------------------
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
	 
	 	 /**
		  * Registra la enterga de un proveedor. 
		  * @param idPedidoEfectivo
		  * @throws Exception
		  */
		 public void registrarEntregaDeProveedor(String idPedidoEfectivo) throws Exception
		 {
			DAOPedidosEfectivos daoPE = new DAOPedidosEfectivos(ruta);
			 
			daoPE.registrarFechaLlegada(idPedidoEfectivo);
			 
			PedidoEfectivoValue pedidoEntrante = daoPE.darPedidoEfectivo(idPedidoEfectivo);
			this.asignarEnBodegas(pedidoEntrante);
			
			proveedorDetallesValue pdv = daoPE.darDetallesProveedorDePedidoEfectivo(idPedidoEfectivo);
			
			// calficar al proveedor
			int nuevaCal = (5-pdv.diasTarde) > 0 ? 5-pdv.diasTarde : 0 ;
			int cal = ( (pdv.numEntregas/(pdv.numEntregas+1))*pdv.calificacion +  (1/(pdv.numEntregas+1))*nuevaCal ) ;
			daoPE.updateValoresProveedor(pdv.correo, cal);
			 
		 }
		 
		 /**
		  * Asigna el pedido a bodegas.
		  */
		 private void asignarEnBodegas(PedidoEfectivoValue pedidoEntrante) throws Exception
		 {
			 DAOAlmacen daoAlm = new DAOAlmacen(this.ruta);
			 double porc_min_almacenaje = 0.1; // es el porcentaje minimo en el cual se puede dividir el pedido para almacenar en distintas bodegas.
			 
			//meter lo que mas se pueda en bodegas llenas
			int num_cajas = pedidoEntrante.getCantidad();
			double Wcajas = pedidoEntrante.getPresentacion();
			ArrayList<AlmacenValues> bodegasDisponibles = daoAlm.darBodegasXTipo(pedidoEntrante.getTipoProducto());
			ArrayList<AlmacenValues> bodegas_vacias = new ArrayList<AlmacenValues>();
			for (AlmacenValues bod : bodegasDisponibles) {
				
				double cap_disponible = bod.getCapacidad()-bod.getCantidad_kg();
				int num_cajas_disponible = (int) (cap_disponible/Wcajas);
				if(cap_disponible > 0 && num_cajas_disponible >= (int)(porc_min_almacenaje*num_cajas) && bod.getCantidad_kg()>0 ) // bodega sin llenar por completo y con espacio para guardar un porcentaje minimo del pedido
				{
					int diff = num_cajas-num_cajas_disponible;
					double x = diff*Wcajas;
					if(diff > 0) // mete solo diff.
					{
						daoAlm.updateAlmacen(x);
						daoAlm.insertarEnInventario(pedidoEntrante.getProducto() , bod.getCodigo() , Wcajas , (num_cajas- diff) , pedidoEntrante.getFechaExpiracion() );
						num_cajas=diff;
					}
					else // metelas todas
					{
						daoAlm.updateAlmacen( num_cajas*Wcajas );
						daoAlm.insertarEnInventario(pedidoEntrante.getProducto() , bod.getCodigo() , Wcajas, (num_cajas), pedidoEntrante.getFechaExpiracion() );
						break;
					}
				}
				else if(cap_disponible == bod.getCapacidad())
					bodegas_vacias.add(bod);
			}
			
			//meter lo que me queda en bodegas vacias.
			for (AlmacenValues bod : bodegas_vacias) {
				double cap_disponible = bod.getCapacidad()-bod.getCantidad_kg();
				int num_cajas_disponible = (int) (cap_disponible/Wcajas);
				int diff = num_cajas-num_cajas_disponible;
				double x = diff*Wcajas;
				if(diff > 0) // mete solo diff.
				{
					daoAlm.updateAlmacen(x);
					daoAlm.insertarEnInventario(pedidoEntrante.getProducto() , bod.getCodigo() , Wcajas , (num_cajas- diff) , pedidoEntrante.getFechaExpiracion() );
					num_cajas=diff;
				}
				else // metelas todas
				{
					daoAlm.updateAlmacen( num_cajas*Wcajas );
					daoAlm.insertarEnInventario(pedidoEntrante.getProducto() , bod.getCodigo() , Wcajas, (num_cajas), pedidoEntrante.getFechaExpiracion() );
					break;
				}
			}
			
		 }
		 
		 
		 
		 
	 


}
