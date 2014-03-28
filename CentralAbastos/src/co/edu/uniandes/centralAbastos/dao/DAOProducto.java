package co.edu.uniandes.centralAbastos.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang.NotImplementedException;

import co.edu.uniandes.centralAbastos.vos.ProductosValue;

public class DAOProducto extends ConsultaDAO
{
	

	//----------------------------------------------------
	//Consultas
	//----------------------------------------------------
	
	
	private static final String consultaBaseProductos = 
			
			"SELECT i.NOMB_PRODUCTO, p.TIPO, i.PESO_CAJA, i.CANTIDAD, i.FECHA_EXPIRACION, i.COD_ALMACEN, (CASE WHEN b.COD_ALMACEN IS NOT NULL THEN 'Bodega' WHEN l.COD_ALMACEN IS NOT NULL THEN 'Local' END) AS Lugar FROM ITEM_INVENTARIO i LEFT JOIN PRODUCTOS p ON i.NOMB_PRODUCTO = p.NOMBRE LEFT JOIN BODEGAS b ON i.COD_ALMACEN = b.COD_ALMACEN LEFT JOIN LOCALES l ON l.COD_ALMACEN = i.COD_ALMACEN";
			
			
	
	private static final String filtroTipoProducto = "NOMBRE = ";
	
	
	

	//----------------------------------------------------
	//Constantes de los atributos
	//----------------------------------------------------
	

	private static final String nombreProducto = "NOMB_PRODUCTO";
	
	private static final String tipoProducto = "TIPO"; 
	
	private static final String pesoCaja = "PESO_CAJA"; 
	
	private static final String cantidad = "CANTIDAD"; 
	
	private static final String fechaExpiracion = "FECHA_EXPIRACION"; 
	
	private static final String codigoAlmacen = "COD_ALMACEN"; 
	
	private static final String tipoLocal = "LUGAR"; 
	
	
	
	
	

	public DAOProducto(String ruta) 
	{
		super(ruta);
	}
	
	
	
	 public ArrayList<ProductosValue> darProductos(String parametros) throws Exception
	    {
	    	PreparedStatement prepStmt = null;	
	    	
	    	ArrayList<ProductosValue> productos = new ArrayList<ProductosValue>();
			String query = consultaBaseProductos;
	    	query += filtrarQuery(parametros);
	    		
	    	
			try {
				ResultSet rs = hacerQuery(query, prepStmt);
				
				while(rs.next())
				{
					
					String nombre = rs.getString(this.nombreProducto);
					String tipo = rs.getString(this.tipoProducto);
					double pesoCaja = rs.getDouble(this.pesoCaja);
					int cantidad = rs.getInt(this.cantidad);
					String fechaExpiracion = rs.getDate(this.fechaExpiracion).toString();
					String codigoAlmacen = rs.getString(this.codigoAlmacen);
					String tipoLocal = rs.getString(this.tipoLocal);
					
					ProductosValue prodValue = new ProductosValue(nombre, tipo, pesoCaja, cantidad, fechaExpiracion, codigoAlmacen, tipoLocal);
					
					productos.add(prodValue);
								
				}
			
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(consultaBaseProductos);
				throw e;
			}finally 
			{
				cerrarConexion(prepStmt);
			}		
			return productos;
	    }
	 
	 private String filtrarQuery(String parametros)
	 {
		 System.out.println(parametros);
		 String filtro;
		 String[] prmts = parametros.split(", ");
		 System.out.println(Arrays.deepToString(prmts));
		 if(prmts[0].equals("todos"))
			 filtro = "";
		 
		 else if(prmts[0].equals("producto"))
 		 {
 			 filtro = " WHERE NOMB_PRODUCTO = '" + prmts[1] + "'";
 		 }
		 else if(prmts[0].equals("tipoProducto"))
		 {
			 filtro = " WHERE TIPO = '" + prmts[1] + "'";
		 }
		 else if(prmts[0].equals("presentacion"))
			 filtro = " WHERE PESO_CAJA = '" + prmts[1] + "'";
		 
		 else if(prmts[0].equals("fechaExpiracion"))
			 filtro = " ORDER BY FECHA_EXPIRACION";
		 
		 else if(prmts[0].equals("local") || prmts[0].equals("bodega"))
			 filtro = " WHERE i.COD_ALMACEN = '" + prmts[1] + "'";
		 
		 else
 		 {
 			 throw new NotImplementedException();
 		 }
 		 return filtro;
	 }

}
