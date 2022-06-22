package Ejercicio3;

import java.util.*;

import Ejercicio3.datos_componentes.Producto;
import us.lsi.common.String2;

public class SolucionComponentes {
	
	private Map<Producto, Integer> map = new HashMap<>();
	
	public static SolucionComponentes of(ComponentesProblem v1, List<Integer> ls) {
		return new SolucionComponentes(v1, ls);
	}
	
	private SolucionComponentes(ComponentesProblem v1, List<Integer> alternativas) {
		
		ComponentesProblem v = v1;
		Producto producto = null;
		
		for (int i=0; i<alternativas.size(); i++) {
			
			Integer a = alternativas.get(i);
			if (a>0) {
				
				Integer e = i;
				producto = datos_componentes.productos.stream().filter(p -> Integer.valueOf(p.id().replace("P", "").trim()).equals(e + 1)).findFirst().get();
				
				map.put(producto, alternativas.get(i));
			}
			v = v.vecino(a);
		}
	}
	
	public String toString() {
		
		Double beneficio = 0.;
		for (Map.Entry<Producto, Integer> par: map.entrySet()) {
			Integer uds = par.getValue();
			beneficio += uds * par.getKey().precio();
		}
		String finalToString = "Precio Total: " + beneficio;
		finalToString += "\nProductos Seleccionados:\n";
		
		for (Map.Entry<Producto, Integer> par: map.entrySet()) {
			finalToString += par.getKey() + ": " + par.getValue() + " unidades" + "\n";
		}
		
		return finalToString;
	}
}
