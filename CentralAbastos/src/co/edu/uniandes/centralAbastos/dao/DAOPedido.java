package co.edu.uniandes.centralAbastos.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.NotImplementedException;

import co.edu.uniandes.centralAbastos.vos.InfoItemsSatisfacerValue;
import co.edu.uniandes.centralAbastos.vos.InfoTiempoEntregaValue;
import co.edu.uniandes.centralAbastos.vos.ItemPedidoValue;
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
 
 	public ArrayList<InfoTiempoEntregaValue> darProyeccionDeExistencias(String idPedidoComprador, String nombProd, double pesoCaja) throws SQLException
 	{
 		String q = 
 				"Select  * from "
 				      +" (Select * from pedidos_comprador pc join ITEMS_PEDIDO_COMPRADOR ipc on pc.id=ipc.id_pedido_comprador where pc.id='"+idPedidoComprador+"' )p join "
 				      +" (SELECT j1.fecha_entrega as Fecha_in , j2.fecha_entrega as Fecha_out, j1.nombre_producto, j1.PESO_CAJA, j1.CANTIDAD as num_cajas_in,-1*j2.CANTIDAD as num_cajas_out ,j1.Z,j2.S, (j1.Z - j2.S) as delta  from " 
 				      + " ((select po.FECHA_ENTREGA ,po.NOMBRE_PRODUCTO,po.PESO_CAJA,po.CANTIDAD , sum(po.cantidad) over (PARTITION by po.peso_caja,po.NOMBRE_PRODUCTO order by po.FECHA_ENTREGA) as Z "
 				       +" from pedido_oferta po  order by po.fecha_entrega,po.NOMBRE_PRODUCTO)j1 join "
 				       + " (select fecha_entrega,NOMBRE_PRODUCTO,PESO_CAJA,CANTIDAD , sum(cantidad) over (partition by nombre_producto,peso_caja order by fecha_entrega) AS S from "
 				       + " (select * from" 
 				              +" (select pc.fecha_entrega,NOMBRE_PRODUCTO,PESO_CAJA,CANTIDAD from items_pedido_comprador ipc join pedidos_comprador pc on  pc.id = ipc.id_pedido_comprador where pc.STATUS is not null)x "
 				              +" union all"
 				              +" (select pl.fecha_entrega,NOMBRE_PRODUCTO,PESO_CAJA,CANTIDAD from items_pedido_local ipl join pedido_local pl on pl.id = ipl.id_pedido_local where pl.STATUS is not null ) ) "
 				        +" )j2 "
 				        +" on j1.nombre_producto=j2.nombre_producto and j1.peso_caja=j2.peso_caja and j1.fecha_entrega<=j2.fecha_entrega)"
 				       + " where j1.nombre_producto= '"+nombProd+"'-- and j1.PESO_CAJA="+pesoCaja+" " 
 				       +" order by j1.fecha_entrega,j2.fecha_entrega) q on (q.nombre_producto=p.nombre_producto and q.peso_caja=p.peso_caja) ";
 		ArrayList<InfoTiempoEntregaValue> resp = new ArrayList<InfoTiempoEntregaValue>();
 		try{
	 		PreparedStatement prepStmt = null;
	 		ResultSet rs = super.hacerQuery(q, prepStmt);
	 		
	 		if(rs.first())
	 			resp.add( new InfoTiempoEntregaValue(rs.getInt(8), rs.getString(9), rs.getInt(15),  rs.getInt(16),  rs.getInt(17)));
	 		
	 		if(rs.next())
	 		{
	 			rs.last();
	 			resp.add( new InfoTiempoEntregaValue(rs.getInt(8), rs.getString(9), rs.getInt(15),  rs.getInt(16),  rs.getInt(17)));
	 		}
	 		
 		}
 		catch(SQLException e)
 		{
 			throw e;
 		}
 		
 		return resp;
 	}

 	public ArrayList<InfoTiempoEntregaValue> darProyDeExistencias(String idPedidoLocal, String nombProd, double pesoCaja) throws SQLException
 	{
 		String q = 
 				"Select  * from "
 				      +" (Select * from pedido_local pc join ITEMS_pedido_local ipc on pc.id=ipc.id_pedido_local where pc.id='"+idPedidoLocal+"' )p join "
 				      +" (SELECT j1.fecha_entrega as Fecha_in , j2.fecha_entrega as Fecha_out, j1.nombre_producto, j1.PESO_CAJA, j1.CANTIDAD as num_cajas_in,-1*j2.CANTIDAD as num_cajas_out ,j1.Z,j2.S, (j1.Z - j2.S) as delta  from " 
 				      + " ((select po.FECHA_ENTREGA ,po.NOMBRE_PRODUCTO,po.PESO_CAJA,po.CANTIDAD , sum(po.cantidad) over (PARTITION by po.peso_caja,po.NOMBRE_PRODUCTO order by po.FECHA_ENTREGA) as Z "
 				       +" from pedido_oferta po  order by po.fecha_entrega,po.NOMBRE_PRODUCTO)j1 join "
 				       + " (select fecha_entrega,NOMBRE_PRODUCTO,PESO_CAJA,CANTIDAD , sum(cantidad) over (partition by nombre_producto,peso_caja order by fecha_entrega) AS S from "
 				       + " (select * from" 
 				              +" (select pc.fecha_entrega,NOMBRE_PRODUCTO,PESO_CAJA,CANTIDAD from items_pedido_local ipc join pedido_local pc on  pc.id = ipc.id_pedido_local where pc.STATUS is not null)x "
 				              +" union all"
 				              +" (select pl.fecha_entrega,NOMBRE_PRODUCTO,PESO_CAJA,CANTIDAD from items_pedido_local ipl join pedido_local pl on pl.id = ipl.id_pedido_local where pl.STATUS is not null ) ) "
 				        +" )j2 "
 				        +" on j1.nombre_producto=j2.nombre_producto and j1.peso_caja=j2.peso_caja and j1.fecha_entrega<=j2.fecha_entrega)"
 				       + " where j1.nombre_producto= '"+nombProd+"'-- and j1.PESO_CAJA="+pesoCaja+" " 
 				       +" order by j1.fecha_entrega,j2.fecha_entrega) q on (q.nombre_producto=p.nombre_producto and q.peso_caja=p.peso_caja) ";
 		ArrayList<InfoTiempoEntregaValue> resp = new ArrayList<InfoTiempoEntregaValue>();
 		try{
	 		PreparedStatement prepStmt = null;
	 		ResultSet rs = super.hacerQuery(q, prepStmt);
	 		
	 		if(rs.first())
	 			resp.add( new InfoTiempoEntregaValue(rs.getInt(8), rs.getString(9), rs.getInt(15),  rs.getInt(16),  rs.getInt(17)));
	 		
	 		if(rs.next())
	 		{
	 			rs.last();
	 			resp.add( new InfoTiempoEntregaValue(rs.getInt(8), rs.getString(9), rs.getInt(15),  rs.getInt(16),  rs.getInt(17)));
	 		}
	 		
 		}
 		catch(SQLException e)
 		{
 			throw e;
 		}
 		
 		return resp;
 		
 	}
 	
 	
 	public ArrayList<InfoItemsSatisfacerValue> darItemsParaSatisfacer(String idPedidoComprador) throws SQLException
 	{
 		String q = "select ipc.ID_PEDIDO_COMPRADOR,j1.nomb_producto, ipc.PESO_CAJA, ipc.CANTIDAD, (ipc.CANTIDAD-j1.Q)as restante from (select nomb_producto,peso_caja, sum(cantidad) as q from ITEM_INVENTARIO group by nomb_producto,peso_caja)j1 join ITEMS_PEDIDO_COMPRADOR ipc on j1.PESO_CAJA = ipc.PESO_CAJA and j1.nomb_producto=ipc.NOMBRE_PRODUCTO "
 				+" where ipc.id_pedido_comprador = '"+idPedidoComprador+"' and q >= ipc.CANTIDAD";
 		PreparedStatement prepStmt = null;
 		ArrayList<InfoItemsSatisfacerValue> resp = new ArrayList<InfoItemsSatisfacerValue>();
 		ResultSet rs = super.hacerQuery(q, prepStmt);
 		while(rs.next())
 		{
 			int restante = rs.getInt(5);
 				resp.add( new InfoItemsSatisfacerValue(idPedidoComprador, rs.getString(2), rs.getDouble(3) , rs.getInt(5),restante));
 		}
 		return resp;
 	}
 	
 	public int insertarNuevoPedidoComprador (String correoComprador,String fechaEntrega , String idNuevo) throws Exception
	{
		
		String q = "insert into pedidos_comprador values of ('"+idNuevo+"','"+correoComprador+"','"+fechaEntrega+"' )";
		PreparedStatement prepStmt = null;
		return super.ejecutarTask(q, prepStmt);
	}
	
 	public int insertarItemPedidoComprador(ItemPedidoValue item, String id) throws Exception
 	{
 		String q = "insert into items_pedido_comprador values of ('"+id+"','"+item.getNombProducto()+"',"+item.getPesoCaja()+" , "+item.getCantidad() +" )";
 		PreparedStatement prepStmt = null;
		return super.ejecutarTask(q, prepStmt);
 	}
 	
 	public String generateNewId() throws SQLException
	{
		int resp = 0;
		PreparedStatement prepStmt = null;
		ResultSet rs = super.hacerQuery(" SELECT MAX(ID) FROM ( SELECT CAST(ID AS INTEGER) AS ID FROM PEDIDO_OFERTA ) ", prepStmt);
		if(rs.next())
			resp = rs.getInt(1);
			resp++;
		return ""+resp;
	}

	public String consultarCorreoComprador(String idPedido) throws Exception 
	{
		String q = "select correo_proveedor from pedidos_comprador where id='"+idPedido+"'";
		PreparedStatement prepStmt = null;
		ResultSet rs = super.hacerQuery(q, prepStmt);
		return rs.getString(1);
		
	}
 	
}
