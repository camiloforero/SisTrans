package co.edu.uniandes.centralAbastos.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import co.edu.uniandes.centralAbastos.vos.AlmacenValue;
import co.edu.uniandes.centralAbastos.vos.ItemInventarioValue;

public class DAOAlmacen extends ConsultaDAO 
{
	

	
	/**
	 * 
	 */
	public final static String ALL_BODEGAS = "SELECT A.* FROM ALMACEN A JOIN BODEGAS B ON A.CODIGO=B.COD_ALMACEN "; 
	
	public final static String ITEMS_INVENTARIO = "select ii.* from item_inventario ii ";
	
	private final static String AGREGAR_BODEGA = "INSERT INTO BODEGAS VALUES ('";
	private final static String AGREGAR_ALMACEN = "INSERT INTO ALMACEN VALUES ('";
	
	private final static String ELIMINAR_BODEGA = "DELETE FROM BODEGAS WHERE COD_ALMACEN = '";
	private final static String ELIMINAR_ALMACEN = "DELETE FROM ALMACEN WHERE CODIGO = '";
	
	private static final String CERRAR_BODEGA = "UPDATE BODEGAS SET ESTADO = 'CERRADA' WHERE COD_ALMACEN = '";
	private static final String ABRIR_BODEGA = "UPDATE BODEGAS SET ESTADO = 'ABIERTA' WHERE COD_ALMACEN = '";
	
	public DAOAlmacen(String ruta) {
		super(ruta);
		
	}
	
	/**
	 * Busca una bodega especifica de la base de datos.
	 * @param codigo del almacen en cuestion
	 * @return la bodega si lo encuentra o null en caso de que no exista. 
	 */
	public AlmacenValue darBodega(String codigo)
	{
		PreparedStatement prepStmt = null;
		try {
			
			ResultSet rs = super.hacerQuery(ALL_BODEGAS+" where A.CODIGO=' "+codigo+"' ", prepStmt);
			if(rs.next()){
		
				return new AlmacenValue(rs.getString(1), rs.getDouble(2), rs.getDouble(3), rs.getString(4));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ArrayList<AlmacenValue>darBodegasXTipo(String tipoProd) throws Exception
	{
		PreparedStatement prepStmt = null;
		ArrayList<AlmacenValue> a = new ArrayList<AlmacenValue>();
		
		try {
		
			ResultSet rs = super.hacerQuery( ALL_BODEGAS+" WHERE A.TIPO_PRODUCTO = '"+tipoProd+"' ", prepStmt);
			
			while(rs.next()){
				
				a.add(new AlmacenValue(rs.getString(1), rs.getDouble(2), rs.getDouble(3) , null));
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			super.cerrarConexion(prepStmt);
		}
	
		return a;
	}
	
	public void updateAlmacen(double cantidadProducto, String codAlmacen) throws Exception
	{
		PreparedStatement prepStmt = null;
		try {
			
			super.ejecutarTask("UPDATE ALMACEN SET CANTIDAD_PRODUCTO+=CANTIDAD_PRODUCTO"+cantidadProducto+" where codigo ="+codAlmacen+"", prepStmt);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally{
			super.cerrarConexion(prepStmt);
		}
	}
	
	/**
	 * Intenta insertar un nuevo item en el inventario. Lo inserta sii el tipo de producto que el almacen "almacena" corresponde al tipo de producto que se intenta almacenar.
	 * @param nomb_producto
	 * @param cod_almacen
	 * @param peso_caja
	 * @param cantidad
	 * @param fechaExpiracion
	 * @return true si lo pudo almacenar o false dlc.
	 */
	public boolean insertarEnInventario(String nomb_producto, String tipoProd, String cod_almacen,double peso_caja,int cantidad,String fechaExpiracion)
	{
		PreparedStatement prepStmt = null;
		
		try {
			ResultSet rs = super.hacerQuery("Select * from almacen a where a.codigo = ' "+cod_almacen+" '  and a.TIPO_PRODUCTO = ' "+tipoProd +" ' ", prepStmt);
			if( ! rs.next() ) {
				super.ejecutarTask( "INSERT INTO ITEM_INVENTARIO VALUES("+nomb_producto+","+cod_almacen+","+peso_caja+","+cantidad+","+fechaExpiracion+")"  
					, prepStmt);
				return true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	/**
	 * Agrega una nueva bodega a CabAndes</br>
	 * <b>post: La bodega es agregada, o si ya existe un almacén con el mismo código no se hace nada<b>
	 * @param value: BodegaValue con el id de la bodega que tiene que ser agregada.
	 * @return true si la bodega fue agregada exitosamente, false de lo contrario
	 */
	public boolean agregarBodega(AlmacenValue value) throws Exception
	{
		PreparedStatement prepStmt = null;
		String query1 = value.getCodigo() + "'," + value.getCapacidad() + ",0,'" + value.getTipoProducto()+ "')";
		String query2 = value.getCodigo() + "','ABIERTA')";
		try {
			
			super.ejecutarTask(AGREGAR_ALMACEN + query1, prepStmt);
			super.ejecutarTask(AGREGAR_BODEGA + query2, prepStmt);
			
			
		} 
		catch (SQLException e) 
		{
			System.out.println(AGREGAR_ALMACEN + query1);
			System.out.println(AGREGAR_BODEGA + query2);
			e.printStackTrace();
		}finally
		{
			super.cerrarConexion(prepStmt);
		}
		return true;
		//TODO: Este método no está revisando si la bodega en cuestión ya existe o no, ni está rebalanceando
		//los productos para que entren acá.
		
	}


	public boolean eliminarBodega(String codigo) throws Exception
	{
		PreparedStatement prepStmt = null;
		try {
			
			super.ejecutarTask(ELIMINAR_BODEGA + codigo + "'", prepStmt);
			super.ejecutarTask(ELIMINAR_ALMACEN + codigo + "'", prepStmt);
			
			
		} 
		catch (SQLException e) 
		{
			System.out.println(AGREGAR_ALMACEN + codigo + "'");
			System.out.println(AGREGAR_BODEGA + codigo + "'");
			e.printStackTrace();
		}finally
		{
			super.cerrarConexion(prepStmt);
		}
		return true;
		//TODO: Este método no está rebalanceando
		//los productos para que salgan de acá.
	}


	public void cerrarBodega(String codigo) throws Exception
	{
		PreparedStatement prepStmt = null;
		try {
			
			super.ejecutarTask(CERRAR_BODEGA + codigo + "'", prepStmt);
			System.out.println(CERRAR_BODEGA + codigo + "'");
			
			
		} 
		catch (SQLException e) 
		{
			System.out.println(CERRAR_BODEGA + codigo + "'");
			e.printStackTrace();
		}finally
		{
			super.cerrarConexion(prepStmt);
		}
		//TODO: Este método no está rebalanceando
		//los productos para que salgan de acá.
		
	}

 
	public void abrirBodega(String codigo) throws Exception
	{
		PreparedStatement prepStmt = null;
		try {
			
			super.ejecutarTask(ABRIR_BODEGA + codigo + "'", prepStmt);
			
			
		} 
		catch (SQLException e) 
		{
			System.out.println(ABRIR_BODEGA + codigo + "'");
			e.printStackTrace();
		}finally
		{
			super.cerrarConexion(prepStmt);
		}
		//TODO: Este método no está rebalanceando
		//los productos para que vuelvan a entrar acá.
		
	}
	
	// Iter 3 
	
	/**
	 * Reporta los item inventario de una bodega especifica. 
	 * @param codigoBodega es el de la bodega.
	 * @return La lista si se encontro la bodega, o vacia dlc.
	 */
	public ArrayList<ItemInventarioValue> darExistenciasDeUnaBodega(String codigoBodega)
	{
		PreparedStatement prepStmt = null;
		ArrayList<ItemInventarioValue> resp = new ArrayList<ItemInventarioValue>();
		
		try {
			ResultSet rs = super.hacerQuery(ITEMS_INVENTARIO+" where cod_almacen=' "+codigoBodega+" ' ", prepStmt);
			
			while(rs.next()){
				ItemInventarioValue item = new ItemInventarioValue(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getInt(4),rs.getString(5));
				resp.add(item);
			}
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resp;
	}
	
	/**
	 * Operacion que cambia un elemento de inventario, de una bodega a otra. Puede cambiar de un local a otro pero aqui solo se utiliza con bodega.
	 */
	public void updateDueñoExistencias(String nombProducto, double presentacion, int cantidad, String codBodegaVieja, String codigoNuevaBodega)
	{
		PreparedStatement prepStmt = null;
		try {
			
			super.ejecutarTask(" UPDATE item_inventario set cod_almacen = '"+codigoNuevaBodega+"' where nomb_producto = '"+nombProducto+"' and peso_caja = '"+presentacion+"' "
					+ " and cantidad = '"+cantidad+" ' and cod_almacen = '"+codBodegaVieja+" '  "
					, prepStmt);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Operacion que modifica la cantidad de cajas para una presentacion especifica de una bodega dada, en la tabla de item inventario
	 * @param fechaExp 
	 * @return El numero de columnas que modifico. 
	 * 
	 */
	public int updateCantidadCajas(String nomb_producto, double wcajas, int cantidad_cajas, String codigoNuevaBodega, String fechaExp)
	{
		PreparedStatement prepStmt = null;
		
		try {
			
			return super.ejecutarTask(" UPDATE item_inventario set cantidad = "+cantidad_cajas+" where nomb_producto = '"+nomb_producto+"' and peso_caja = '"+wcajas+"' "
					+ " and cod_almacen = '"+codigoNuevaBodega+" '  and fecha_expiracion='"+fechaExp+"'" , prepStmt);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	/**
	 * Capacidad total disponible en todas las bodegas diferentes a la que tiene codBodega.	
	 * @return la capacidad en todas las bodegas distintas a codBodega. 
	 * 		para retornar la capacidad de todas llamar el metodo con codBodega como cadena vacia.
	 */
	public double darCapacidadTotalDisp(String codBodega)
	{
		PreparedStatement prepStmt = null;
		double resp = 0;
		try {
			ResultSet rs = super.hacerQuery( "select sum(capacidad-cantidad_producto) from almacen a join bodegas b on a.codigo=b.cod_almacen where b.cod_almacen !='"+codBodega+"'" , prepStmt);
			if(rs.next())
				resp = rs.getDouble(1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resp;
	}

	/**
	 * 
	 * @param codigo
	 * @param wcajas
	 * @return true si la bodega maneja cajas de peso Wcaja o false dlc.
	 */
	public boolean bodegaTienePresentaciones(String codigo, double wcajas) {
		String qq=
				"Select * from cajas where "+wcajas+" in (Select Distinct ii.peso_caja from (almacen a join bodegas b on a.codigo=b.cod_almacen) join item_inventario ii on a.codigo=ii.COD_ALMACEN where a.CODIGO='"+codigo+"')"; 
		
		try {
			ResultSet rs;
			PreparedStatement prepStmt = null;
			rs = super.hacerQuery(qq, prepStmt);
			if(rs.next()) // si la tabla no viene vacia
				return true;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return false;
	
	}
}
