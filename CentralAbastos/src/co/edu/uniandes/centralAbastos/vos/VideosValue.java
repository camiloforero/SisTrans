/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: VideosValue.java,v 1.0 
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 *
 * Ejercicio: VideoAndes
 * Autor: Juan Diego Toro - 10-Feb-2010
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package co.edu.uniandes.centralAbastos.vos;

/**
 * Clase VideosValue, que representa un Value Object para un video
 */
public class VideosValue 
{
	/**
	 * Identificador universal del video
	 */
	private String isan;
	
	/**
	 * titulo original del video
	 */
	private String tituloOriginal;
	
	/**
	 * año de lanzamiento
	 */
	private int anyo;
	
	/**
	 * duracion en minutos
	 */
	private int duracion;
	
	/**
	 * clasificacion del video
	 */
	private String clasificacion;
	
	/**
	 * descripcion de la clasificacion
	 */
	private String descripcionClasificacion;
	
	/**
	 * Constructor de la clase. No inicializa ningun atributo.
	 */
	public VideosValue()
	{
		
	}

	/**
	 * retorna el isan (identificador universal) del video
	 * @return isan 
	 */
	public String getIsan() {
		return isan;
	}

	/**
	 * Modifica el isan de un video
	 * @param isan el nuevo isan del video. isan != null
	 */
	public void setIsan(String isan) {
		this.isan = isan;
	}

	/**
	 * retorna el titulo original del video
	 * @return tituloOriginal
	 */
	public String getTituloOriginal() {
		return tituloOriginal;
	}

	/**
	 * Modifica el titulo original del video
	 * @param tituloOriginal el nuevo titulo del video. isan != null
	 */
	public void setTituloOriginal(String tituloOriginal) {
		this.tituloOriginal = tituloOriginal;
	}

	/**
	 * retorna el año de lanzamiento del video
	 * @return anyo
	 */
	public int getAnyo() {
		return anyo;
	}
	
	/**
	 * Modifica el año de lanzamiento del video
	 * @param anyo el nuevo año de lanzamiento. anyo > 0;
	 */
	public void setAnyo(int anyo) {
		this.anyo = anyo;
	}

	/**
	 * retorna la duración en minutos del video
	 * @return duracion
	 */
	public int getDuracion() {
		return duracion;
	}

	/**
	 * Modifica la duración en minutos del video
	 * @param duracion la nueva duración del video. duracion >= 0
	 */
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	/**
	 * retorna la clasificación del video
	 * @return clasificacion
	 */
	public String getClasificacion() 
	{
		return clasificacion;
	}

	/**
	 * Modifica la clasificación de un video
	 * @param clasificacion la nueva clasificacion del video. clasificacion != null
	 */
	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}

	/**
	 * retorna la descripción de clasificación del video
	 * @return descripcionClasificacion
	 */
	public String getDescripcionClasificacion() {
		return descripcionClasificacion;
	}

	/**
	 * Modifica la descripción de la clasificación de un video
	 * @param descripcionClasificacion la nueva descripción de la 
	 * clasificación del video. descripcionClasificación != null
	 */
	public void setDescripcionClasificacion(String descripcionClasificacion) {
		this.descripcionClasificacion = descripcionClasificacion;
	}
	
	

}
