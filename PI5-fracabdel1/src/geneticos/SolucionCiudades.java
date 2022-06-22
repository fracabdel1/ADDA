package geneticos;

import java.util.ArrayList;
import java.util.List;

import datos_ejemplos.datos_ciudad;
import datos_ejemplos.datos_ciudad.Carretera;
import datos_ejemplos.datos_ciudad.Ciudad;

public class SolucionCiudades {
	
	private List<Ciudad> ciudades = new ArrayList<>();
	private Double kms = 0.;
	
	public static SolucionCiudades create(List<Integer> value) {
		return new SolucionCiudades(value);
	}
	private SolucionCiudades(List<Integer> ls) {
		
		ciudades = ls.stream().map(i -> datos_ciudad.graph.getVertex(i)).toList();
		
		Ciudad c1 = null;
		Ciudad c2 = null;
		
		for (int i=0; i<ciudades.size()-1; i++) {
			
			c1 = ciudades.get(i);
			c2 = ciudades.get(i + 1);
			
			if (datos_ciudad.gf.containsEdge(c1, c2)) {
				Carretera carretera = datos_ciudad.gf.getEdge(c1, c2);
				kms += carretera.km();
			}
		}
	}
	public String toString() {
		return String.format("%s; Kms: %.1f", ciudades, kms);
	}

}
