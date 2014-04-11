package co.edu.uniandes.centralAbastos.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import co.edu.uniandes.centralAbastos.vos.ItemPedidoValue;
import co.edu.uniandes.centralAbastos.vos.PedidoEfectivoValue;
import co.edu.uniandes.centralAbastos.vos.PedidoOfertaValue;
import co.edu.uniandes.centralAbastos.vos.PedidosValue;
import co.edu.uniandes.centralAbastos.vos.ProductosValue;

public class DAOPedidosOferta extends ConsultaDAO
{
	//------------------
	// Constantes
	//------------------
	private static final String ID= "ID";
	
	private static final String NOMBRE_PRODUCTO = "NOMBRE_PRODUCTO"; 
	
	private static final String PESO_CAJA = "PESO_CAJA"; 
	
	private static final String CANTIDAD = "CANTIDAD"; 
	
	private static final String PRECIO_X_KG = "PRECIO_X_KG";
	
	private static final String PRECIO_TOTAL= "PRECIO_TOTAL";
	
	private static final String FECHA_ENTREGA = "FECHA_ENTREGA"; 
	
	private static final String FECHA_VENCIMIENTO = "FECHA_VENCIMIENTO";
	private static final String FECHA_CIERRE = "FECHA_CIERRE";
	private static final String CORRREO = "CORREO_PROVEEDOR";
	private static final String CALIF = "CALIFICACION";
	private static final String coef_precios = "COEFICIENTE_PRECIOS"; 
	//------------------------
	// Queries
	//------------------------
	
	private final static String ALL_PEDIDOS_OFERTA = 
							"SELECT ID, NOMBRE_PRODUCTO, PESO_CAJA, CANTIDAD, FECHA_ENTREGA, FECHA_CIERRE "
						  + "FROM PEDIDO_OFERTA ";

	private static final String ALL_OFFERS = 
			"SELECT ID_PEDIDO AS ID, PO.NOMBRE_PRODUCTO , PO.PESO_CAJA , PO.CANTIDAD , O.PRECIO_X_KG , O.PRECIO_TOTAL , PO.FECHA_ENTREGA , O.FECHA_VENCIMIENTO , O.CORREO_PROVEEDOR , P.CALIFICACION,((prod.PRECIO_KILOGRAMO-O.PRECIO_X_KG)/PROD.PRECIO_KILOGRAMO) AS COEFICIENTE_PRECIOS  "
			+ "FROM PEDIDO_OFERTA PO JOIN OFERTA O ON PO.ID=O.ID_PEDIDO JOIN PROVEEDORES P ON O.CORREO_PROVEEDOR=P.CORREO_USUARIO JOIN PRODUCTOS prod on PO.NOMBRE_PRODUCTO=prod.NOMBRE ";	
	
	//------------------------
	// Constructor
	//------------------------
	public DAOPedidosOferta(String ruta) {
		super(ruta);
		// TODO Auto-generated constructor stub
	}
	
	//------------------------
	// Metodos
	//------------------------
	
	/**
	 * 
	 * @return Los pedidos de oferta que ya no reciben mas ofertas. 
	 * @throws Exception
	 */
	public ArrayList<PedidoOfertaValue> darPedidosOfertaCerrados() throws Exception
	{
		PreparedStatement prepStmt = null;	
    	
    	ArrayList<PedidoOfertaValue> a = new ArrayList<PedidoOfertaValue>();
    	try 
    	{
			ResultSet rs = super.hacerQuery(ALL_PEDIDOS_OFERTA+"WHERE FECHA_CIERRE <= SYSDATE", prepStmt);
			while(rs.next())
			{
				
				String id = rs.getString(this.ID);
				String producto = rs.getString(this.NOMBRE_PRODUCTO);
				double presentacion = rs.getDouble(this.PESO_CAJA);
				int cantidad = rs.getInt(this.CANTIDAD);
				String fechaEntrega = rs.getDate(this.FECHA_ENTREGA).toString();
				String fechaCierre = rs.getDate(this.FECHA_CIERRE).toString();
				
				PedidoOfertaValue p = new PedidoOfertaValue(id, producto, presentacion, cantidad, fechaEntrega, fechaCierre);
				
				a.add(p);
			}
			
			
		} 
    	catch (SQLException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	finally {
    		super.cerrarStatement(prepStmt);
    	}
    	
    	return a;
    	
	}
	
	public int insertarOfertaNueva (ItemPedidoValue item ) throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, 6);
		String d = sdf.format( c.getTime() );
		c.add(Calendar.DATE, -3);
		String fc = sdf.format(c.getTime()); 
		String q = "insert into pedido_oferta values of ('"+item.getNombProducto()+"','"+d+"','"+generateNewId()+"',"+item.getCantidad()+","+item.getPesoCaja()+","+fc+" )";
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
	/**
	 * 
	 * @param idPedido
	 * @return Las posibles ofertas que pueden llegar que son pedidos efectivos tentativos.
	 * @throws Exception
	 */
	public ArrayList<PedidoEfectivoValue> darOfertas(String idPedido) throws Exception
	{
		String q = ALL_OFFERS + "WHERE ID="+idPedido;
		PreparedStatement prepStmt = null;
		ArrayList<PedidoEfectivoValue> a = new ArrayList<PedidoEfectivoValue>();
		
		ResultSet rs;
		try {
			rs = super.hacerQuery(q, prepStmt);
			while(rs.next())
			{

				String id = rs.getString(this.ID);
				String producto = rs.getString(this.NOMBRE_PRODUCTO);
				double presentacion = rs.getDouble(this.PESO_CAJA);
				int cantidad = rs.getInt(this.CANTIDAD);
				double precio_x_kg = rs.getDouble(this.PRECIO_X_KG);
				double precioTotal = rs.getDouble(this.PRECIO_TOTAL);
				String fechaEntrega = rs.getDate(this.FECHA_ENTREGA).toString();
				String fechaExpiracion = rs.getDate(this.FECHA_VENCIMIENTO).toString();
				String correo_p = rs.getString(this.CORRREO);
				int calif = rs.getInt(this.CALIF);
				double cp = rs.getDouble(this.coef_precios);
				
				PedidoEfectivoValue p = new PedidoEfectivoValue(id, producto, presentacion, cantidad, precio_x_kg, precioTotal, fechaExpiracion, fechaEntrega, correo_p, calif , cp);
				a.add(p);
			}		
				
		} 
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		finally {
    		super.cerrarStatement(prepStmt);
    	}
		
		return a ;
		
		
	}
	
	
}
