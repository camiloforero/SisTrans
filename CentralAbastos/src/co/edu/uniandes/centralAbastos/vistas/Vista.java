package co.edu.uniandes.centralAbastos.vistas;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public abstract class Vista 
{
	/**
	 * Engine de velocity, este módulo es la base de Velocity, hace que funcione bien
	 */
	VelocityEngine engine;
	
	
	/**
	 * Template: Código html con velocity (.vm) sobre el cual se hacen las modificaciones dinámicas
	 */
	Template template;
	
	
	/**
	 * Contexto de velocity, donde se guardan todos los datos que velocity utiliza para modificar las páginas web dinámicamente
	 */
	VelocityContext context;
	
	public Vista()
	{
		engine = new VelocityEngine();
		context = new VelocityContext();
		Properties p = new Properties();  
		p.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath"); //
        p.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        //Estas dos anteriores properties se encargan de que el Resource Loader de velocity sea de tipo Classpath. Esto quiere decir que buscará
        //los recursos dentro de un Jar que estará en WEB-INF/lib; es importante que dentro del ant se cree este jar y se coloque en su sitio
        engine.init(p);		
	}
	protected void merge(PrintWriter respuesta)
	{
		StringWriter writer = new StringWriter();
        template.merge( context, writer );
        respuesta.println( writer.toString());
	}
	
	/**
	 * Coloca un elemento de tipo Iterable dentro del contexto, para que este sea utilizado más tarde por Velocity para generar la página web </br>
	 * <b>pre: <b> </br>
	 * <b>post: <b>
	 * @param nombre
	 * @param data
	 */
	public void put(String nombre, Object data)
	{
		context.put(nombre, data);
	}
	
	/**
	 * Imprime la vista, asignandole un template. </br>
	 * template = engine.getTemplate("nombreTemplate.vm"); </br>
	 * merge(respuesta); </br>
	 * <b>pre: <b>Todas las collections necesarias para generar la página están dentro del contexto, y se ha seleccionado un template </br>
	 * <b>post: <b> La página web se ha escrito en el PrintWriter del servlet
	 * @param respuesta: PrintWriter del servlet donde se escribe el contenido HTML
	 */
	public abstract void imprimirVista(PrintWriter respuesta);
	
	
	
	

}
