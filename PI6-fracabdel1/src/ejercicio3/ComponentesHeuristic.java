package ejercicio3;

import java.util.function.Predicate;
import java.util.stream.IntStream;

public class ComponentesHeuristic {

	public static Double heuristic(ComponentesVertex actual, Predicate<ComponentesVertex> goal, ComponentesVertex last) {

		Integer n = datos_componentes.productos.size();
		Double h = 0.;

		if (actual.index() >= n) {
			return h;
		} else {
			h = IntStream.range(actual.index(), n).boxed().mapToDouble(p -> datos_componentes.productos.get(p).precio()*actual.getRatioUds(p)).sum();
		}
		return h;
	}
}
