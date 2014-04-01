package co.edu.uniandes.centralAbastos.vos;

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

		public String getTipo_producto() {
			return tipoProducto;
		}
		
		public void setEstado(String estado)
		{
			this.estado = estado;
		}
		
		public String getEstado() {
			return estado;
		}
		
		
		
}
