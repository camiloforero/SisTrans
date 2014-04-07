package co.edu.uniandes.centralAbastos.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOPedidoLocal extends ConsultaDAO {
	
	// cosntructor
	 public DAOPedidoLocal(String ruta) {
		super(ruta);
	}
	
	//-----------------------
	// Metodos
	//-----------------------
	 
	 public String darCodigoLocalSegunIdPedido(String idPedidoLocal)
	 {
		 String qq = "select cod_almacen from admin_local al , pedido_local pl where pl.id = ' "+idPedidoLocal+" ' and al.CORREO_USUARIO=pl.CORREO_ADMIN_L ";
		 PreparedStatement prepStmt = null;
		 try{
			 ResultSet rs = super.hacerQuery(qq, prepStmt);
			 if(rs.next())
				 return rs.getString(1);
		 }
		 catch(SQLException e )
		 {
			 e.printStackTrace();
		 }
		 
		 return "";
	 }
	
	
}
