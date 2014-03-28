package co.edu.uniandes.centralAbastos.vos;

public class AlmacenValues 
{
 
		private String codigo;
		private double capacidad;
		private double cantidad_kg;
		private String tipo_producto;
		
		public AlmacenValues(String codigo, double capacidad,
				double cantidad_kg, String tipo_producto) {
			super();
			this.codigo = codigo;
			this.capacidad = capacidad;
			this.cantidad_kg = cantidad_kg;
			this.tipo_producto = tipo_producto;
		}

		public String getCodigo() {
			return codigo;
		}

		public double getCapacidad() {
			return capacidad;
		}

		public double getCantidad_kg() {
			return cantidad_kg;
		}

		public String getTipo_producto() {
			return tipo_producto;
		}
		
		
		
}
