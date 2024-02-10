package com.indeval.portalinternacional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.indeval.portalinternacional.middleware.services.util.ConfirmacionEfectivoComparator;
import com.indeval.portalinternacional.middleware.services.util.Utils;
import com.indeval.portalinternacional.middleware.servicios.modelo.ConfirmacionEfectivo;

public class Comparator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ConfirmacionEfectivo confirmacion1 = new ConfirmacionEfectivo();
		confirmacion1.setCantidad(1d);
		ConfirmacionEfectivo confirmacion2 = new ConfirmacionEfectivo();
		confirmacion2.setCantidad(2.01d);
		ConfirmacionEfectivo confirmacion3 = new ConfirmacionEfectivo();
		confirmacion3.setCantidad(3d);
		ConfirmacionEfectivo confirmacion4 = new ConfirmacionEfectivo();
		confirmacion4.setCantidad(2d);
		ConfirmacionEfectivo confirmacion5 = new ConfirmacionEfectivo();
		confirmacion5.setCantidad(1d);
		
		
		List<ConfirmacionEfectivo> listaConfirmacion = new ArrayList<ConfirmacionEfectivo>();
		listaConfirmacion.add(confirmacion3);
		listaConfirmacion.add(confirmacion1);
		listaConfirmacion.add(confirmacion2);
		listaConfirmacion.add(confirmacion5);
		listaConfirmacion.add(confirmacion4);
		
		List<ConfirmacionEfectivo> listaConfirmacionOrdenada = new ArrayList<ConfirmacionEfectivo>(listaConfirmacion);
		
		Collections.sort(listaConfirmacionOrdenada, new ConfirmacionEfectivoComparator());
		List<ConfirmacionEfectivo> listaConfirmacionOrdenadaB = new ArrayList<ConfirmacionEfectivo>(listaConfirmacionOrdenada);
		
		double cantidad1 = 0;
		double cantidad2 = 0;
		Double cantidadB = null;
		ConfirmacionEfectivo confirmacionB = null;
		
		for(ConfirmacionEfectivo confirmacion : listaConfirmacionOrdenada){
			cantidad1 = confirmacion.getCantidad() - .01;
			cantidad2 = confirmacion.getCantidad() + .01;
			
			for (Iterator<ConfirmacionEfectivo> i = listaConfirmacionOrdenadaB.iterator(); i.hasNext();) {
				try {
					confirmacionB = (ConfirmacionEfectivo)i.next();
				} catch(Exception e){
					e.printStackTrace();
					break;
				}
				cantidadB = confirmacionB.getCantidad();
				
				if(Utils.betweenInclusive(cantidadB, cantidad1, cantidad2) && confirmacion != confirmacionB){
					System.out.println("pareja encontrada:" + confirmacion.getCantidad() + "==" + cantidadB);
					listaConfirmacionOrdenadaB.remove(confirmacion);
					listaConfirmacionOrdenadaB.remove(confirmacionB);
					break;
				}
			}
		}
		
		for(ConfirmacionEfectivo confirmacion : listaConfirmacionOrdenadaB){
			System.out.println("sin pareja:" + confirmacion.getCantidad());
		}

	}

}
