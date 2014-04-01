package co.edu.uniandes.centralAbastos.test;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import junit.framework.TestCase;

public class testUsuarios extends TestCase
{
	
	public static final String queryInsertarUsuario = 
			"INSERT INTO USUARIOS "
			+ "VALUES ('test', 'test', 'test', 'test', 'test', 'test', 'test', 'test', 'test', 'test', 'No')";
	
	public static final String queryViolarConstraint = 
			"INSERT INTO USUARIOS "
			+ "VALUES ('test', 'test', 'test', 'test', 'test', 'test', 'test', 'test', 'test', 'test', 'CONSTRAINT')";

	private static final String queryBorrarUsuario = 
			"DELETE FROM USUARIOS "
			+ "WHERE CORREO = 'test'";
	
	public static final String queryInsertarComprador = 
			"INSERT INTO COMPRADORES "
			+ "VALUES ('test')";
	
	public static final String queryInsertarCompradorNoFK = 
			"INSERT INTO COMPRADORES "
			+ "VALUES ('FAIL')";
	
	private static final String queryBorrarComprador = 
			"DELETE FROM COMPRADORES "
			+ "WHERE CORREO_USUARIO = 'test'";
	
	
	
	DAOTest dao;

	public void testPK() 
	{
		dao = new DAOTest();
		try 
		{
			dao.ejecutarQuery(queryInsertarUsuario);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			try 
			{
				dao.ejecutarQuery(queryBorrarUsuario);
				System.out.println("borrando");
			} 
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
			fail("Hubo una excepción no esperada");
			
		}
		try 
		{
			dao.ejecutarQuery(queryInsertarUsuario);
			dao.ejecutarQuery(queryBorrarUsuario);
			fail("Se violó la constraint de PK");
			
		} 
		catch (SQLException e) 
		{
			System.out.println(e.getErrorCode());
			System.out.println("----");
			System.out.println(e.getMessage());
			try {
				dao.ejecutarQuery(queryBorrarUsuario);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
				
		}
		
	}
	
	public void testConstraint()
	{
		dao = new DAOTest();
		try
		{
			dao.ejecutarQuery(queryViolarConstraint);
			fail("No detectó el error");
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void testFKCompradores() 
	{
		dao = new DAOTest();
		try 
		{
			dao.ejecutarQuery(queryInsertarUsuario);			
			dao.ejecutarQuery(queryInsertarComprador);
			dao.ejecutarQuery(queryBorrarComprador);			
			dao.ejecutarQuery(queryBorrarUsuario);
			
		} 
		catch (Exception e) 
		{
			fail("Hubo una excepción no esperada");			
		}		
		
		try 
		{
			dao.ejecutarQuery(queryInsertarCompradorNoFK);
			fail("Se violó la constraint de FK");
			
		} 
		catch (SQLException e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	

}
