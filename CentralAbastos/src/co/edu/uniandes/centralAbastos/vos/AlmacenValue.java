package co.edu.uniandes.centralAbastos.vos;

import java.util.ArrayList;
import java.util.Collection;

public class AlmacenValue 
{
 
		private String codigo;
		private double capacidad;
		private double cantidadKg;
		private String tipoProducto;
		/**
		 * Aplica sólo para bodegas: si está abierta o cerrada
		 */
		private String estado;
		
		public AlmacenValue(String codigo, double capacidad,
				double cantidad_kg, String tipo_producto) {
			super();
			this.codigo = codigo;
			this.capacidad = capacidad;
			this.cantidadKg = cantidad_kg;
			this.tipoProducto = tipo_producto;
		}

		public String getCodigo() {
			return codigo;
		}

		public double getCapacidad() {
			return capacidad;
		}

		public double getCantidad_kg() {
			return cantidadKg;
		}

		public String getTipoProducto() {
			return tipoProducto;
		}
		
		public void setEstado(String estado)
		{
			this.estado = estado;
		}
		
		public String getEstado() {
			return estado;
		}

		public static ArrayList<String> darHeaders() 
		{
			ArrayList<String> respuesta = new ArrayList<String>();
			respuesta.add("Código");
			respuesta.add("Capacidad");
			respuesta.add("Carga");
			respuesta.add("Tipo de producto");
			respuesta.add("Estado");
			return respuesta;
		}
		
		public ArrayList<String> toArrayList()
		{
			ArrayList<String> respuesta = new ArrayList<String>();
			respuesta.add(codigo);
			respuesta.add(capacidad+"");
			respuesta.add(cantidadKg+"");
			respuesta.add(tipoProducto);
			respuesta.add(estado);
			return respuesta;
		}
		
		
		
}
