package co.edu.uniandes.centralAbastos.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import co.edu.uniandes.centralAbastos.vos.AlmacenValue;

public class DAOAlmacen extends ConsultaDAO 
{
	

	
	/**
	 * 
	 */
	public final static String ALL_BODEGAS = "SELECT A.* FROM ALMACEN A JOIN BODEGAS B ON A.CODIGO=B.COD_ALMACEN ";  
	
	private final static String AGREGAR_BODEGA = "INSERT INTO BODEGAS VALUES ('";
	private final static String AGREGAR_ALMACEN = "INSERT INTO ALMACEN VALUES ('";
	
	private final static String ELIMINAR_BODEGA = "DELETE FROM BODEGAS WHERE COD_ALMACEN = '";
	private final static String ELIMINAR_ALMACEN = "DELETE FROM ALMACEN WHERE CODIGO = '";
	
	private static final String CERRAR_BODEGA = "UPDATE BODEGAS SET ESTADO = 'CERRADA' WHERE COD_ALMACEN = '";
	private static final String ABRIR_BODEGA = "UPDATE BODEGAS SET ESTADO = 'ABIERTA' WHERE COD_ALMACEN = '";
	
	public DAOAlmacen(String ruta) {
		super(ruta);
		
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
	
	public void updateAlmacen(double cantidadProducto) throws Exception
	{
		PreparedStatement prepStmt = null;
		try {
			
			super.ejecutarTask("UPDATE ALMACEN SET CANTIDAD_PRODUCTO="+cantidadProducto, prepStmt);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally{
			super.cerrarConexion(prepStmt);
		}
	}
	
	public void insertarEnInventario(String nomb_producto,String cod_almacen,double peso_caja,int cantidad,String fechaExpiracion)
	{
		PreparedStatement prepStmt = null;
		
		try {
			
			super.ejecutarTask( "INSERT INTO ITEM_INVENTARIO VALUES("+nomb_producto+","+cod_almacen+","+peso_caja+","+cantidad+","+fechaExpiracion+")"  
					, prepStmt);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		String query1 = value.getCodigo() + "'," + value.getCapacidad() + ",0,'" + value.getTipo_producto() + "')";
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
		//los productos para que entren acá.
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
		//los productos para que entren acá.
		
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
		//los productos para que entren acá.
		
	}
}
