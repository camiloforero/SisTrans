package co.edu.uniandes.centralAbastos.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import co.edu.uniandes.centralAbastos.vos.AlmacenValues;

public class DAOAlmacen extends ConsultaDAO 
{

	public final static String ALL_BODEGAS = "SELECT A.* FROM ALMACEN A JOIN BODEGAS B ON A.CODIGO=B.COD_ALMACEN ";  
	
	public DAOAlmacen(String ruta) {
		super(ruta);
		
	}
	
	
	public ArrayList<AlmacenValues>darBodegasXTipo(String tipoProd) throws Exception
	{
		PreparedStatement prepStmt = null;
		ArrayList<AlmacenValues> a = new ArrayList<AlmacenValues>();
		
		try {
		
			ResultSet rs = super.hacerQuery( ALL_BODEGAS+" WHERE A.TIPO_PRODUCTO = '"+tipoProd+"' ", prepStmt);
			
			while(rs.next()){
				
				a.add(new AlmacenValues(rs.getString(1), rs.getDouble(2), rs.getDouble(3) , null));
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
}
