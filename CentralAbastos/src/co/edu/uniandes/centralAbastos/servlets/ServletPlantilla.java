/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: ServletPlantilla.java,v 1.0 
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 *
 * Ejercicio: VideoAndes
 * Autor: Juan Diego Toro - 1-Marzo-2010
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package co.edu.uniandes.centralAbastos.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.edu.uniandes.centralAbastos.fachada.CabAndes;
import co.edu.uniandes.centralAbastos.vos.VideosValue;

/**
 * clase plantilla, que sirve de base para el dearrollo de
 * los servlets. Extiende el template ServerTemplate,
 * para implementar la adición del header y del footer de 
 * la página web
 */
public class ServletPlantilla extends ServletTemplate
{

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
	 * serial
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Devuelve el título de la página para el Header
     * @param request Pedido del cliente
     * @return Título de la página para el Header
     */
    public String darTituloPagina( HttpServletRequest request )
    {
        return "Resultados de Búsqueda";
    }

    /**
     * Devuelve el nombre de la imagen para el título de la página en el Header
     * @param request Pedido del cliente
     * @return Nombre de la imagen para el título de la página en el Header
     */
    public String darImagenTitulo( HttpServletRequest request )
    {
    	//TODO reemplzar por otra imagen
        return "videos.jpg";
    }

    /**
     * Escribe el contenido de la página
     * @param request Pedido del cliente
     * @param response Respuesta
     * @throws IOException Excepción de error al escribir la respuesta
     */
    public void escribirContenido( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
        
        // Saca el Printer
        PrintWriter respuesta = response.getWriter( );
        try
        {
        	//TODO
            // Saca los parámetros de la búsqueda. los parámetros
        	//coinciden con los descritos en el index.htm en el
        	//nombre del "form".
            String campo = request.getParameter( "campo" );
            String valor = request.getParameter( "valor" );
            
            //TODO obtener los datos a imprimir a partir de los llamados a la interfaz
            ArrayList<VideosValue> videos= CabAndes.darInstancia().darVideosDefault();
            //TODO cambiar los parametros para imprimir la información deseada
            imprimirDatos( respuesta, videos);
        }
        catch( Exception e )
        {
            //
            // Imprime el mensaje de la excepción
            imprimirMensajeError( respuesta, "Error al buscar videos.", "Excepción generada en la operación", e );
        }
    }

    /**
     * Imprime la tabla con los estudiantes
     * @param respuesta Respuesta al cliente
     * @param estudiantes Estudiantes a imprimir
     */
    private void imprimirDatos( PrintWriter respuesta, ArrayList<VideosValue> videos)
    {
    	//imprimir que no se encontró información
        if( videos.size( ) == 0 )
        {
            respuesta.println( "                      La búsqueda no devolvió resultados.<p>" );
            respuesta.println( "                      <a href=\"index.htm\">Volver a la página principal</a>" );
        }
        //si si habia información que desplegar, mostrar los datos.
        else
        {
  
            //TODO
        	//Agregar la cantidad de columnas necesarias con su titulo
            // Imprime el título de la tabla
            respuesta.println( "                      <table border=\"1\" width=\"543\" id=\"table5\" style=\"border-collapse: collapse\">" );
            respuesta.println( "                          <tr>" );
            respuesta.println( "                              <td width=\"106\" align=\"center\" bgcolor=\"#707070\">" );
            respuesta.println( "                              <font color=\"#FFFFFF\"><b>Info1</b></font></td>" );
            respuesta.println( "                              <td width=\"106\" align=\"center\" bgcolor=\"#707070\">" );
            respuesta.println( "                              <font color=\"#FFFFFF\"><b>Info2</b></font></td>" );
            respuesta.println( "                          </tr>" );
            
            //TODO
        	//Agregar la cantidad de columnas necesarias
            // Imprime los resultados
            for( int i = 0; i < videos.size( ); i++ )
            {
                VideosValue video = videos.get( i );
                respuesta.println( "                          <tr>" );
                respuesta.println( "                              <td width=\"71\" align=\"center\">" + video.getTituloOriginal() + "</td>" );
                respuesta.println( "                              <td width=\"71\" align=\"center\">" + video.getAnyo() + "</td>" );
                respuesta.println( "                          </tr>" );
            }
            //
            // Imprime el final de la tabla
            respuesta.println( "                      </table>" );
        }
    }
}
