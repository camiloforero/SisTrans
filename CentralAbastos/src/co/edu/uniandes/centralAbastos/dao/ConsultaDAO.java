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
package co.edu.uniandes.centralAbastos.dao;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;













import co.edu.uniandes.centralAbastos.vos.VideosValue;

/**
 * Clase ConsultaDAO, encargada de hacer las consultas básicas para el cliente
 */
public class ConsultaDAO {

	//----------------------------------------------------
	//Constantes
	//----------------------------------------------------
	/**
	 * ruta donde se encuentra el archivo de conexión.
	 */
	private static final String ARCHIVO_CONEXION = "/../conexion.properties";
	
	/**
	 * nombre de la tabla videos
	 */
	private static final String tablaVideo = "videos";
	
	
	/**
	 * nombre de la columna titulo_original en la tabla videos.
	 */
	private static final String tituloVideo = "titulo_original";
	
	/**
	 * nombre de la columna anyo en la tabla videos.
	 */
	private static final String anyoVideo = "anyo";
	
	
	
	
	private static final String producto = "NOMBRE";
	
	private static final String tipo = "TIPO"; 
	
	private static final String presentacion = "PESO"; 
	
	private static final String local = "COD_ALMACEN"; 
	
	private static final String bodega = "COD_ALMACEN"; 
	
	private static final String correo = "CORREO"; 
	
	private static final String idPedido = "ID_PEDIDO"; 
	
	
	

	//----------------------------------------------------
	//Consultas
	//----------------------------------------------------
	
	/**
	 * Consulta que devuelve isan, titulo, y a�o de los videos en orden alfabetico
	 */
	private static final String consultaVideosDefault="SELECT * FROM "+tablaVideo;
	
	private static final String consultaTipos = "SELECT * FROM TIPO_PRODUCTOS";

	private static final String listaNombresProducto = "SELECT NOMBRE FROM PRODUCTOS";
	
	private static final String listaPresentaciones = "SELECT * FROM CAJAS";
	
	private static final String listaLocales = "SELECT * FROM LOCALES ";
	
	private static final String listaBodegas = "SELECT * FROM BODEGAS ";

	private static final String listaUsuarios = "SELECT CORREO, (CASE WHEN c.CORREO_USUARIO IS NOT NULL THEN 'Comprador' WHEN u.ES_ADMINISTRADOR = 'Si' THEN 'Administrador' WHEN a.CORREO_USUARIO IS NOT NULL THEN 'Administrador local' WHEN p.CORREO_USUARIO IS NOT NULL THEN 'Proveedor' ELSE 'Otro' END) AS TIPO "
													+ "FROM USUARIOS u LEFT JOIN COMPRADORES c ON u.CORREO = c.CORREO_USUARIO LEFT JOIN ADMIN_LOCAL a ON u.CORREO = a.CORREO_USUARIO LEFT JOIN PROVEEDORES p ON u.CORREO = p.CORREO_USUARIO";
	
	
	private static final String listaPedidosEfectivos = "SELECT ID_PEDIDO FROM PEDIDO_EFECTIVO WHERE FECHA_LLEGADA IS NULL";
	
	

	
	

	//----------------------------------------------------
	//Atributos
	//----------------------------------------------------
	/**
	 * conexion con la base de datos
	 */
	private static Connection conexion;
	
	/**
	 * nombre del usuario para conectarse a la base de datos.
	 */
	private static String usuario;
	
	/**
	 * clave de conexión a la base de datos.
	 */
	private static String clave;
	
	/**
	 * URL al cual se debe conectar para acceder a la base de datos.
	 */
	private static String cadenaConexion;
	
	/**
	 * constructor de la clase. No inicializa ningun atributo.
	 */
	public ConsultaDAO(String ruta) 
	{	if(cadenaConexion == null)
			inicializar(ruta);
	}
	
	// -------------------------------------------------
    // Métodos
    // -------------------------------------------------

	/**
	 * obtiene ls datos necesarios para establecer una conexion
	 * Los datos se obtienen a partir de un archivo properties.
	 * @param path ruta donde se encuentra el archivo properties.
	 */
	public void inicializar(String path)
	{
		try
		{
			File arch= new File(path+ARCHIVO_CONEXION);
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
			System.out.println("Se está ejecutando una conexión");
		
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
    public void establecerConexion() throws SQLException
    {
    	try
        {
    		conexion = DriverManager.getConnection(cadenaConexion, usuario, clave);
    		PrintStackTraceEstablecerConexion();
			
        }
        catch( SQLException exception )
        {
        	exception.printStackTrace();
            throw new SQLException( "ERROR: ConsultaDAO obteniendo una conexion." );
        } catch (Exception e) 
        {
			e.printStackTrace();
		}
    }
    
    private void PrintStackTraceEstablecerConexion() throws Exception
    {
    	throw new Exception("StackConexiónEstablecida");
    }
    
    /**
 	 *Cierra la conexi�n activa a la base de datos. Adem�s, con=null.
     * @param con objeto de conexi�n a la base de datos
     * @throws SistemaCinesException Si se presentan errores de conexi�n
     */
    public void closeConnection() throws Exception 
    {        
    	System.out.println("conexión cerrada");
		try 
		{
			conexion.close();
			conexion = null;
			
		} catch (SQLException exception) {
			throw new Exception("Error a la hora de cerrar una conexión");
		}
		//TODO: Crear nuevas excepciones
    } 
    
    // ---------------------------------------------------
    // Métodos asociados a los casos de uso: Consulta
    // ---------------------------------------------------
    
    
    public ResultSet hacerQuery(String statement, PreparedStatement prepStmt) throws SQLException
    {
    	prepStmt = conexion.prepareStatement(statement);		
		return prepStmt.executeQuery();
    }
    
    /**
     * Metodo que prepara un statment de insert, delete, o update.
     * @param prepStmt
     * @throws SQLException 
     * @throws Exception
     */
    public int ejecutarTask(String statement, PreparedStatement prepStmt) throws SQLException
    {
    	
    	prepStmt = conexion.prepareStatement(statement);
		return prepStmt.executeUpdate();
    }
    
    public void cerrarStatement(PreparedStatement prepStmt) throws SQLException
    {
    	if (prepStmt != null) 
		{
			prepStmt.close();
		}
		
    }
    
    
    /**
     * Método que se encarga de realizar la consulta en la base de datos
     * y retorna un ArrayList de elementos tipo VideosValue.
     * @return ArrayList lista que contiene elementos tipo VideosValue.
     * La lista contiene los videos ordenados alfabeticamente
     * @throws Exception se lanza una excepción si ocurre un error en
     * la conexión o en la consulta. 
     */
    public ArrayList<VideosValue> darVideosDefault() throws Exception
    {
    	PreparedStatement prepStmt = null;	
    	
    	ArrayList<VideosValue> videos = new ArrayList<VideosValue>();
		VideosValue vidValue = new VideosValue();
    	
		try {
			ResultSet rs = hacerQuery(consultaVideosDefault, prepStmt);
			
			while(rs.next())
			{
				
				String titVid = rs.getString(tituloVideo);
				int anyoVid = rs.getInt(anyoVideo);
				
				vidValue.setTituloOriginal(titVid);
				vidValue.setAnyo(anyoVid);	
			
				videos.add(vidValue);
				vidValue = new VideosValue();
							
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(consultaVideosDefault);
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement!!!");
		}finally 
		{
			cerrarStatement(prepStmt);
		}		
		return videos;
    }
    
    
   
    
    
    
    
    /**
     * Da una lista de Strings con cierta información dependiendo del parámetro de entrada</br>
     * <b>pre: <b>El parámetro de entrada está suportado</br>
     * @param listaPedida: La lista que se desea obtener.
     * @return Una lista de tipos, productos, bodegas, etc
     * @throws Exception: Si algo sucede dentro de la base de datos
     */
    public ArrayList<String> darListaSimple(String listaPedida) throws Exception
    {
    	PreparedStatement prepStmt = null;	
    	
    	ArrayList<String> ans = new ArrayList<String>();
    	
    	String consulta = null;
    	String buscado = null;
    	System.out.println(listaPedida);
    	
    	if(listaPedida.equals("tipos"))
    	{
    		consulta = consultaTipos;
    		buscado = tipo;
    	}
    	else if(listaPedida.equals("productos"))
    	{
    		consulta = listaNombresProducto;
    		buscado = producto;
    	}
    	else if(listaPedida.equals("presentaciones"))
    	{
    		consulta = listaPresentaciones;
    		buscado = presentacion;
    	}
    	else if(listaPedida.equals("locales"))
    	{
    		consulta = listaLocales;
    		buscado = local;    				
    	}    	
    	else if(listaPedida.equals("usuarios"))
    	{
    		consulta = listaUsuarios;
    		buscado = correo;    				
    	}
    	else if(listaPedida.equals("pedidosEfectivos"))
    	{
    		consulta = listaPedidosEfectivos;
    		buscado = idPedido;    				
    	}
    	else if(listaPedida.startsWith("bodegas"))
    	{
    		System.out.println("entra");
    		consulta = listaBodegas;
    		buscado = bodega;
    		if(listaPedida.equals("bodegasAbiertas"))
    		{
    			consulta += "WHERE ESTADO = 'ABIERTA'";
    		}
    		else if(listaPedida.equals("bodegasCerradas"))
    		{
    			consulta += "WHERE ESTADO = 'CERRADA'";
    		}
    	}
    	else throw new Exception("string invalida");
    
    			
    	
		try {
			ResultSet rs = hacerQuery(consulta, prepStmt);
				
			System.out.println(consulta + ", " + buscado);
			while(rs.next())
			{
				
				String dato = rs.getString(buscado);
				ans.add(dato);
							
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement!!!");
		}finally 
		{
			cerrarStatement(prepStmt);
		}		
		return ans;
    }

	public ArrayList[] darUsuariosPorTipo() throws Exception
	{
		ArrayList[] respuesta = new ArrayList[3]; //En la posición 0 se guardan los compradores, en la posición 1 los administradores del local y en la posición 2 los administradores de CabAndes
		for (int i = 0; i < respuesta.length ; i++)
			respuesta[i] = new ArrayList();
		
		PreparedStatement prepStmt = null;	
    	
    	try 
    	{
			ResultSet rs = hacerQuery(listaUsuarios, prepStmt);
			
			while(rs.next())
			{
				
				String nombreUsuario = rs.getString("CORREO");
				String tipoUsuario = rs.getString("TIPO");
				if(tipoUsuario.equals("Comprador"))
					respuesta[0].add(nombreUsuario);
				
				else if(tipoUsuario.equals("Administrador local"))
					respuesta[1].add(nombreUsuario);
				
				else if(tipoUsuario.equals("Administrador"))
					respuesta[2].add(nombreUsuario);				
							
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(consultaVideosDefault);
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement!!!");
		}finally 
		{
			cerrarStatement(prepStmt);
		}		
		return respuesta;
	}
    
	/**
	 * Hace commit
	 */
   public void commit()
   {
		   try {
			conexion.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }
   
   public void rollback()
   {
		   try {
			conexion.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
   }
   public void setAutocommit(boolean bol)
   {
	   try {
		conexion.setAutoCommit(bol);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
   }
   
   public void selectForUpdate(String string) throws SQLException 
	{
		PreparedStatement prepStmt = null;
		hacerQuery("select * from "+string+" for update", prepStmt);	
	}
 
}
