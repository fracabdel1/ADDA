package ejercicio1;

import java.util.function.Predicate;

public class FicheroHeuristic {
	
public static Double heuristic(FicheroVertex origen, Predicate<FicheroVertex> goal, FicheroVertex destino) {
		
		Double res;
		Integer n = datos_ficheros.ficheros.size();
		
		if (origen.index() == n) {
			res = 0.;
		} else {
			res = (n - origen.index()) * 1.;
		}
		
		return res*1.;
		
	}

}
