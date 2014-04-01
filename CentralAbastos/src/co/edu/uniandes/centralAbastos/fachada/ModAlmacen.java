package co.edu.uniandes.centralAbastos.fachada;

import java.util.ArrayList;

import co.edu.uniandes.centralAbastos.dao.DAOAlmacen;
import co.edu.uniandes.centralAbastos.vos.AlmacenValue;
import co.edu.uniandes.centralAbastos.vos.PedidoEfectivoValue;

public class ModAlmacen 
{
	
	private DAOAlmacen dao;

	public ModAlmacen(DAOAlmacen dao) 
	{
		this.dao = dao;
	}
	
	boolean agregarBodega(AlmacenValue value) throws Exception
	{
		return dao.agregarBodega(value);
	}
	
	void asignarEnBodegas(PedidoEfectivoValue pedidoEntrante) throws Exception
	 {
		 double porc_min_almacenaje = 0.1; // es el porcentaje minimo en el cual se puede dividir el pedido para almacenar en distintas bodegas.
		 
		//meter lo que mas se pueda en bodegas llenas
		int num_cajas = pedidoEntrante.getCantidad();
		double Wcajas = pedidoEntrante.getPresentacion();
		ArrayList<AlmacenValue> bodegasDisponibles = dao.darBodegasXTipo(pedidoEntrante.getTipoProducto());
		ArrayList<AlmacenValue> bodegas_vacias = new ArrayList<AlmacenValue>();
		for (AlmacenValue bod : bodegasDisponibles) {
			
			double cap_disponible = bod.getCapacidad()-bod.getCantidad_kg();
			int num_cajas_disponible = (int) (cap_disponible/Wcajas);
			if(cap_disponible > 0 && num_cajas_disponible >= (int)(porc_min_almacenaje*num_cajas) && bod.getCantidad_kg()>0 ) // bodega sin llenar por completo y con espacio para guardar un porcentaje minimo del pedido
			{
				int diff = num_cajas-num_cajas_disponible;
				double x = diff*Wcajas;
				if(diff > 0) // mete solo diff.
				{
					dao.updateAlmacen(x);
					dao.insertarEnInventario(pedidoEntrante.getProducto() , bod.getCodigo() , Wcajas , (num_cajas- diff) , pedidoEntrante.getFechaExpiracion() );
					num_cajas=diff;
				}
				else // metelas todas
				{
					dao.updateAlmacen( num_cajas*Wcajas );
					dao.insertarEnInventario(pedidoEntrante.getProducto() , bod.getCodigo() , Wcajas, (num_cajas), pedidoEntrante.getFechaExpiracion() );
					break;
				}
			}
			else if(cap_disponible == bod.getCapacidad())
				bodegas_vacias.add(bod);
		}
		
		//meter lo que me queda en bodegas vacias.
		for (AlmacenValue bod : bodegas_vacias) {
			double cap_disponible = bod.getCapacidad()-bod.getCantidad_kg();
			int num_cajas_disponible = (int) (cap_disponible/Wcajas);
			int diff = num_cajas-num_cajas_disponible;
			double x = diff*Wcajas;
			if(diff > 0) // mete solo diff.
			{
				dao.updateAlmacen(x);
				dao.insertarEnInventario(pedidoEntrante.getProducto() , bod.getCodigo() , Wcajas , (num_cajas- diff) , pedidoEntrante.getFechaExpiracion() );
				num_cajas=diff;
			}
			else // metelas todas
			{
				dao.updateAlmacen( num_cajas*Wcajas );
				dao.insertarEnInventario(pedidoEntrante.getProducto() , bod.getCodigo() , Wcajas, (num_cajas), pedidoEntrante.getFechaExpiracion() );
				break;
			}
		}
		
	 }

}
