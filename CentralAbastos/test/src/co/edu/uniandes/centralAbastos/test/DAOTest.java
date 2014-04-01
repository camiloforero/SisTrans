/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: ConsultaDAO.java,v 1.10 
 * Universidad de los Andes (Bogot� - Colombia)
 * Departamento de Ingenier�a de Sistemas y Computaci�n 
 *
 * Ejercicio: VideoAndes
 * Autor: Juan Diego Toro - 1-Marzo-2010
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package co.edu.uniandes.centralAbastos.test;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;
/**
 * Clase ConsultaDAO, encargada de hacer las consultas básicas para el cliente
 */
public class DAOTest {

	//----------------------------------------------------
	//Constantes
	//----------------------------------------------------
	/**
	 * ruta donde se encuentra el archivo de conexión.
	 */
	private static final String ARCHIVO_CONEXION = "./WebContent/conexion.properties";
	


	//----------------------------------------------------
	//Atributos
	//----------------------------------------------------
	/**
	 * conexion con la base de datos
	 */
	public Connection conexion;
	
	/**
	 * nombre del usuario para conectarse a la base de datos.
	 */
	private String usuario;
	
	/**
	 * clave de conexión a la base de datos.
	 */
	private String clave;
	
	/**
	 * URL al cual se debe conectar para acceder a la base de datos.
	 */
	private String cadenaConexion;
	
	/**
	 * constructor de la clase. No inicializa ningun atributo.
	 */
	
	public DAOTest ()
	{
		inicializar();
	}
	
	// -------------------------------------------------
    // Métodos
    // -------------------------------------------------

	/**
	 * obtiene ls datos necesarios para establecer una conexion
	 * Los datos se obtienen a partir de un archivo properties.
	 * @param path ruta donde se encuentra el archivo properties.
	 */
	private void inicializar()
	{
		try
		{
			System.out.println(ARCHIVO_CONEXION);
			File arch= new File(ARCHIVO_CONEXION);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream( arch );

	        prop.load( in );
	        in.close( );

			cadenaConexion = prop.getProperty("url");	// El url, el usuario y passwd deben estar en un archivo de propiedades.
												// url: "jdbc:oracle:thin:@chie.uniandes.edu.co:1521:chie10";
			usuario = prop.getProperty("usuario");	// "s2501aXX";
			clave = prop.getProperty("clave");	// "c2501XX";
			final String driver = prop.getProperty("driver");
			Class.forName(driver);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
	}

	/**
	 * Método que se encarga de crear la conexión con el Driver Manager
	 * a partir de los parametros recibidos.
	 * @param url direccion url de la base de datos a la cual se desea conectar
	 * @param usuario nombre del usuario que se va a conectar a la base de datos
	 * @param clave clave de acceso a la base de datos
	 * @throws SQLException si ocurre un error generando la conexi�n con la base de datos.
	 */
    private void establecerConexion(String url, String usuario, String clave) throws SQLException
    {
    	try
        {
			conexion = DriverManager.getConnection(url,usuario,clave);
        }
        catch( SQLException exception )
        {
        	exception.printStackTrace();
            throw new SQLException( "ERROR: ConsultaDAO obteniendo una conexion." );
        }
    }
    
    /**
 	 *Cierra la conexi�n activa a la base de datos. Adem�s, con=null.
     * @param con objeto de conexi�n a la base de datos
     * @throws SistemaCinesException Si se presentan errores de conexi�n
     */
   
    private void cerrarConexion(PreparedStatement prepStmt) throws Exception
    {
    	if (prepStmt != null) 
		{
			try {
				prepStmt.close();
			} catch (SQLException exception) {
				
				throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexión.");
			}
		}
    	try {
			conexion.close();
			conexion = null;
		} catch (SQLException exception) {
			throw new Exception("ERROR: ConsultaDAO: closeConnection() = cerrando una conexi�n.");
		}
    }
    
    // ---------------------------------------------------
    // Métodos asociados a los casos de uso: Consulta
    // ---------------------------------------------------
    
    
    private ResultSet hacerQuery(String statement, PreparedStatement prepStmt) throws SQLException
    {
    	establecerConexion(cadenaConexion, usuario, clave);
		prepStmt = conexion.prepareStatement(statement);		
		return prepStmt.executeQuery();
    }
    
    /**
     * Metodo que prepara un statment de insert, delete, o update.
     * @param prepStmt
     * @throws SQLException 
     * @throws Exception
     */
    private void ejecutarTask(String statement, PreparedStatement prepStmt) throws SQLException
    {
    	establecerConexion(cadenaConexion, usuario, clave);
		prepStmt = conexion.prepareStatement(statement);
		prepStmt.executeUpdate();
    }
    
    
    
    /**
     * Método que se encarga de realizar la consulta en la base de datos
     * y retorna un ArrayList de elementos tipo VideosValue.
     * @return ArrayList lista que contiene elementos tipo VideosValue.
     * La lista contiene los videos ordenados alfabeticamente
     * @throws Exception se lanza una excepción si ocurre un error en
     * la conexión o en la consulta. 
     */
    public void ejecutarQuery(String query) throws SQLException
    {
    	PreparedStatement prepStmt = null;	
    	
    	ejecutarTask(query, prepStmt);	
    	System.out.println(query);
			try {
				cerrarConexion(prepStmt);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
    }
    
   

    
}
    
    
   
    
    
    