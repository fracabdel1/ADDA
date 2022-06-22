package ejercicio4;

import java.util.function.Predicate;

public class ContenedorHeuristic {
	
	public static Double heuristic(ContenedorVertex actual, Predicate<ContenedorVertex> goal, ContenedorVertex last) {
		return actual.weight();
	}

}
