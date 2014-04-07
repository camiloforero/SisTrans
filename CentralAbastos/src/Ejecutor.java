import java.util.ArrayList;
import java.util.Collections;


public class Ejecutor 
{
	
	public Ejecutor ()
	{
		
		
	}
	
	public ArrayList<TuplaPresentaciones> reempacar(double cantidad, ArrayList<Double> presentaciones)
	{
		ArrayList<TuplaPresentaciones> respuesta = new ArrayList<TuplaPresentaciones>();	
		Collections.sort(presentaciones);
		reempacarAux(cantidad, respuesta, presentaciones);
		return respuesta;
		
	}
	
	public void reempacarAux(double cantidad, ArrayList<TuplaPresentaciones> acumulado, ArrayList<Double> presentaciones)
	{
		if(cantidad == 0) return;
		for(int i = presentaciones.size() - 1; i >= 0; i--)
		{
			if(cantidad >= presentaciones.get(i))
			{
				if(acumulado.size() != 0 && acumulado.get(acumulado.size()-1).darPeso() == presentaciones.get(i))
					acumulado.get(acumulado.size()-1).aumentarCantidad();
				else
					acumulado.add(new TuplaPresentaciones(presentaciones.get(i)));
				
				cantidad -= presentaciones.get(i);
				reempacarAux(cantidad, acumulado, presentaciones);
				return;
			}
		}
	}
	
	public static void main (String args[])
	{
		Ejecutor ej = new Ejecutor();
		ArrayList<Double> presentaciones = new ArrayList<Double>();
		presentaciones.add(0.5);
		presentaciones.add(1.0);
		presentaciones.add(2.0);
		presentaciones.add(5.0);
		presentaciones.add(10.0);
		//presentaciones.add(20.0);
		ArrayList<TuplaPresentaciones> respuesta = ej.reempacar(9, presentaciones);
		for(TuplaPresentaciones t: respuesta) System.out.println(t);
		
	}
	
	public class TuplaPresentaciones
	{
		/**
		 * Peso de la presentación
		 */
		private double peso;
		
		/**
		 * Cantidad de cajas de dicha presentación
		 */
		private int cantidad;
		
		/**
		 * Inicializa una nueva TuplaPresentaciones</br>
		 * <b>post: La TuplaPresentaciones ha sido agregada, con el peso dado como parámetro, y cantidad 1<b>
		 * @param peso: Peso de la presentación
		 */
		public TuplaPresentaciones(double peso)
		{
			this.peso = peso;
			cantidad = 1;
		}
		
		/**
		 * Da el peso de la presentación</br>
		 * @return Peso de una presentación dada
		 */
		public double darPeso()
		{
			return peso;
		}
		
		/**
		 * Da la cantidad de cajas de dicha presentación</br>
		 * @return: Cantidad de cajas de una presentación dada
		 */
		public int darCantidad()
		{
			return cantidad;
		}
		
		/**
		 * Aumenta la cantidad de cajas de cierta presentación en 1</br>
		 * <b>post: La cantidad ha aumentado en una unidad<b>
		 */
		public void aumentarCantidad()
		{
			cantidad++;
		}
		
		public String toString()
		{
			return "["+peso+", " + cantidad + "]";
		}
		
	}
	
	

}
