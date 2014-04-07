package co.edu.uniandes.centralAbastos.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang.NotImplementedException;

import co.edu.uniandes.centralAbastos.vos.PedidosValue;
import co.edu.uniandes.centralAbastos.vos.ProductosValue;

public class DAOPedido extends ConsultaDAO
{
	
	public static final String basePedidoComprador = 
				"SELECT PC.ID,PC.FECHA_ENTREGA,PROD.NOMBRE,IPC.CANTIDAD,IPC.PESO_CAJA,(IPC.CANTIDAD*IPC.PESO_CAJA*PROD.PRECIO_KILOGRAMO)AS COSTO, PC.STATUS "
						+ "FROM (PEDIDOS_COMPRADOR PC JOIN ITEMS_PEDIDO_COMPRADOR IPC ON PC.ID = IPC.ID_PEDIDO_COMPRADOR) JOIN PRODUCTOS PROD ON IPC.NOMBRE_PRODUCTO=PROD.NOMBRE "
						+ "WHERE CORREO_COMPRADOR = ";
	
	public static final String basePedidoLocal = 
				"SELECT PL.ID,PL.DEADLINE ,PROD.NOMBRE,IPL.CANTIDAD,IPL.PESO_CAJA,(IPL.CANTIDAD*IPL.PESO_CAJA)AS COSTO, ('Satisfecho') AS STATUS "
						+ "FROM (PEDIDO_LOCAL PL JOIN ITEMS_PEDIDO_LOCAL IPL ON PL.ID = IPL.ID_PEDIDO_LOCAL)JOIN PRODUCTOS PROD ON PROD.NOMBRE = IPL.NOMBRE_PRODUCTO "
						+ "WHERE CORREO_ADMIN_L = ";
	
	public static final String filtroSatisfecho = "AND STATUS = 'SATISFECHO' ";
	
	public static final String filtroNoSatisfecho = "AND STATUS = 'PENDIENTE' ";
	
	public static final String filtroFechaComprador2 = "AND FECHA_ENTREGA <= '";
	
	public static final String filtroFechaComprador1 = "AND FECHA_ENTREGA >= '";
	
	
	public static final String filtroFechaLocal2 = "AND DEADLINE <= '";
	
	public static final String filtroFechaLocal1 = "AND DEADLINE >= '";

	public static final String filtroPrecioTotal = "AND (IPC.CANTIDAD*IPC.PESO_CAJA*PROD.PRECIO_KILOGRAMO) < ";
	
	public static final String filtroPrecioKilogramo = "AND (PROD.PRECIO_KILOGRAMO) < ";

	
	
	
	public static final String fechaComprador = "PC.FECHA_ENTREGA";
	
	public static final String correoComprador = "CORREO_COMPRADOR";
	
	public static final String fechaLocal = "PL.DEADLINE";		
	
	public static final String correoLocal = "CORREO_ADMIN_L";
	
	
	
	
	
	

	public DAOPedido(String ruta) {
		super(ruta);
	}

	public ArrayList<PedidosValue> darPedidos(String tipoUsuario, String nombreUsuario, String parametros) throws Exception
	{
		PreparedStatement prepStmt = null;	
    	
    	ArrayList<PedidosValue> productos = new ArrayList<PedidosValue>();
    	
		String query = filtrarQuery(tipoUsuario, nombreUsuario, parametros);
		System.out.println(query + "print1");
    		
    	
		try {
			ResultSet rs = hacerQuery(query, prepStmt);
			
			while(rs.next())
			{
				
				String id = rs.getString("ID");
				String fecha = rs.getDate("FECHA_ENTREGA").toString();
				String producto = rs.getString("NOMBRE");
				int cantidad = rs.getInt("CANTIDAD");
				double presentacion = rs.getDouble("PESO_CAJA");
				double costoParcial = rs.getDouble("COSTO");
				String estado = rs.getString("STATUS");
				
				PedidosValue pv = new PedidosValue(id, estado, fecha, producto, cantidad, presentacion, costoParcial);
				productos.add(pv);
							
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(query);
			throw e;
		}finally 
		{
			cerrarStatement(prepStmt);
		}		
		return productos;
    }
 
 private String filtrarQuery(String tipoUsuario, String nombreUsuario, String parametros) throws Exception
 {
	 String ans;
	 if(tipoUsuario.equals("adminLocal"))
	 {
		ans = basePedidoLocal + "'" + nombreUsuario + "'";
	 }
	 else if(tipoUsuario.equals("comprador"))
	 {		
	 	ans = basePedidoComprador + "'" + nombreUsuario + "'";
	 }
		
	 else return null;
		 
	 String[] prmts = parametros.split(";");
	 System.out.println(Arrays.deepToString(prmts));
	 for(int i = 0; i< prmts.length; i++)
	 {
		 String[] prmts2 = prmts[i].split(",");
		 
		 if(prmts2[0].equals("estaSatisfecho"))
		 {
			 if(prmts2[1].equals("no"))
				 ans += filtroNoSatisfecho;
			 
			 else
				 ans += filtroSatisfecho;
		 }
		 if(prmts2[0].equals("rangoFechas"))
		 {
			 SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yy");
			 SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
			 if(tipoUsuario.equals("adminLocal"))
				ans += filtroFechaLocal1 + df1.format(df2.parse(prmts2[1])) + "' " + filtroFechaLocal2 + df1.format(df2.parse(prmts2[2])) + "' ";
			 
			 else if(tipoUsuario.equals("comprador"))
			 {		
				 ans += filtroFechaComprador1 + df1.format(df2.parse(prmts2[1])) + "' " + filtroFechaComprador2 + df1.format(df2.parse(prmts2[2])) + "' ";
			 }
		 }
		 
		 if(prmts2[0].equals("costo"))
		 {
			 if(prmts2[2].equals("total"))
				 ans += filtroPrecioTotal + prmts2[1];
			 else if(prmts2[2].equals("unitario"))
				 ans += filtroPrecioKilogramo + prmts2[1];
		 }
		 
	 }
	 	return ans;
	}

}
