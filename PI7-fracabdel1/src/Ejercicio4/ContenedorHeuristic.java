package Ejercicio4;

public class ContenedorHeuristic {

	public static Double heuristic(ContenedorProblem actual) {
		return actual.peso()*1.0;
	}
	
	public static Double cota(ContenedorProblem v1, Integer a) {
		ContenedorProblem v2 = v1.vecino(a);
		return heuristic(v2);
	}
	
}
