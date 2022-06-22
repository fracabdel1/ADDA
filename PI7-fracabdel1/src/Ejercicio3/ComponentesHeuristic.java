package Ejercicio3;

import java.util.stream.IntStream;

public class ComponentesHeuristic {

	public static Double heuristic(ComponentesProblem v1) {
		
		Integer n = datos_componentes.productos.size();
		Double h = 0.;
		
		if (v1.index() >= n) {
			return h;
		} else {
			h = IntStream.range(v1.index(), n).boxed().mapToDouble(p -> datos_componentes.productos.get(p).precio()*v1.getRatioUds(p)).sum();
		}
		return h;
	}
	
	public static Double cota(ComponentesProblem v1, Integer a) {
		ComponentesProblem v2 = v1.vecino(a);
		return heuristic(v2) + a * datos_componentes.productos.get(v1.index()).precio();
	}
	
}
