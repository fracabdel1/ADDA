package Ejercicio1;

import java.util.function.Predicate;

public class FicheroHeuristic {

	public static Integer heuristic(FicheroProblem v1) {

		Integer res;
		Integer n = datos_ficheros.ficheros.size();

		if (v1.index() == n) {
			res = 0;
		} else {
			res = n - v1.index();
		}

		return res;

	}

	public static Integer cota(FicheroProblem v1, Integer a) {
		FicheroProblem v2 = v1.neighbor(a);
		Integer cota;
		// Si hay un vecino, aumentamos la cota en 1, si no lo hay paramos
		if (a < datos_ficheros.memorias.size()) {
			cota = heuristic(v2) + 1; 
		} else {
			cota = heuristic(v2);
		}
		return cota;
	}
	
	/*	
public static Double heuristic(FicheroProblem origen, Predicate<FicheroProblem> goal, FicheroProblem destino) {

		Double res;
		Integer n = datos_ficheros.ficheros.size();

		if (origen.index() == n) {
			res = 0.;
		} else {
			res = (n - origen.index()) * 1.;
		}

		return res*1.;

	}
	 */

}
