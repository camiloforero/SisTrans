package co.edu.uniandes.centralAbastos.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.edu.uniandes.centralAbastos.vos.PedidoEfectivoValue;



public class DAOPedidosEfectivos extends ConsultaDAO
{
	
	private static final String q1 = 
			"SELECT PO.NOMBRE_PRODUCTO , PROD.TIPO , PO.CANTIDAD , PO.PESO_CAJA , O.FECHA_VENCIMIENTO , O.PRECIO_X_KG "
			+ "FROM PEDIDO_EFECTIVO PE JOIN OFERTA O ON(PE.ID_PEDIDO=O.ID_PEDIDO AND PE.CORREO_PROVEEDOR=O.CORREO_PROVEEDOR) JOIN (PEDIDO_OFERTA PO JOIN PRODUCTOS PROD ON PO.NOMBRE_PRODUCTO = PROD.NOMBRE) ON PE.ID_PEDIDO=PO.ID ";			
	private static final String q2 = 
			"SELECT PE.CORREO_PROVEEDOR , P.CALIFICACION , P.NUM_ENTREGAS , (PE.FECHA_LLEGADA-PO.FECHA_ENTREGA)AS DIAS_TARDANZA FROM PEDIDO_EFECTIVO PE , PEDIDO_OFERTA PO , PROVEEDORES P WHERE P.CORREO_USUARIO=PE.CORREO_PROVEEDOR";
	
	private static final String ID= "ID_PEDIDO";
	
	private static final String CORREO = "CORREO_PROVEEDOR"; 
	
	private static final String FECHA = "FECHA_LLEGADA"; 
	
	public DAOPedidosEfectivos(String ruta) {
		super(ruta);
		// TODO Auto-generated constructor stub
	}
	
	public void insertarPedidoEfectivo(String idPedido , String correo , String FechaLlegada) throws Exception
	{
		PreparedStatement prepStmt = null;
		try {
			System.out.println(idPedido + " " + correo + " " + FechaLlegada);
			
			super.ejecutarTask( "INSERT INTO PEDIDO_EFECTIVO VALUES ( '"+idPedido+"' , '"+correo+"' , "+FechaLlegada+"  )" 
					, prepStmt);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
    		super.cerrarConexion(prepStmt);
    	}
		
	}

	/**
	 * Registra la fecha de llegada de un pedido efectivo dado su id.
	 * @throws Exception 
	 */
	public void registrarFechaLlegada(String idPedidoEfectivo) throws Exception
	{
		Date today = new Date();
		DateFormat df = new SimpleDateFormat("dd/mm/yy");
		
		PreparedStatement prepStmt = null;
		try {
			
			super.ejecutarTask( " UPDATE PEDIDO_EFECTIVO SET FECHA_LLEGADA=' "+df.format(today)+" ' WHERE ID_PEDIDO = ' "+idPedidoEfectivo+" '"
					, prepStmt);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
    		super.cerrarConexion(prepStmt);
    	}
		
	}

	/**
	 * 
	 * @return pedido efectivo segun id
	 * @throws Exception 
	 */
	public PedidoEfectivoValue darPedidoEfectivo(String idPedido) throws Exception
	{
		PreparedStatement prepStmt = null;
		PedidoEfectivoValue resp = null;
		ResultSet rs;
		try {
			rs = hacerQuery(q1 + "WHERE O.ID_PEDIDO = '"+idPedido+"' " , prepStmt);
			
			while(rs.next())
			{
				resp = new PedidoEfectivoValue( rs.getString(1), rs.getString(2), rs.getInt(3), rs.getDouble(4) , rs.getString(5), rs.getDouble(6) );
				System.out.println(resp);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			super.cerrarConexion(prepStmt);
		}
		
		return resp;
		
	}
	
	/**
	 * Reporta los valores necesarios de un proveedor que entrego el pedido que entra por parametro.
	 */
	//clase interna para guardar los valores
	public class proveedorDetallesValue
	{
		public String correo;
		public int calificacion;
		public int numEntregas;
		public int diasTarde;
		public proveedorDetallesValue(String correo, int calificacion,
				int numEntregas, int diasTarde) {
			super();
			this.correo = correo;
			this.calificacion = calificacion;
			this.numEntregas = numEntregas;
			this.diasTarde = diasTarde;
		}
	}
	
	public proveedorDetallesValue darDetallesProveedorDePedidoEfectivo(String idPedido) throws SQLException
	{
		
		PreparedStatement prepStmt = null;
		ResultSet rs = super.hacerQuery(q2+" AND PE.ID_PEDIDO='"+idPedido+"' " , prepStmt);
		if(rs.next())
		{
			return new proveedorDetallesValue(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4));
		}
		else
			return null;
	}

	public void updateValoresProveedor(String correoProveedor,int calificacion) throws SQLException
	{
		PreparedStatement prepStmt = null;
		super.ejecutarTask("UPDATE PROVEEDORES SET CALIFICACION="+calificacion+", NUM_ENTREGAS=NUM_ENTREGAS+1", prepStmt);
	}


	public void tttttttttttttt()
	{
		System.out.println("pri");
	}


}
