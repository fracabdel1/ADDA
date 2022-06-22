package ejercicio2;

import java.util.function.Predicate;

public class ContratoHeuristic {

	public static Double heuristic(ContratoVertex actual, Predicate<ContratoVertex> goal, ContratoVertex last) {

		Double h = 0.;

		if (actual.getCualidadesRestantes().isEmpty()) {
			return h;
		}

		for (int i=actual.index(); i<datos_contrataciones.contratos.size(); i++) {
			Integer valor = datos_contrataciones.contratos.get(i).valorCV();
			h += valor*1.;
		}

		return h;

	}

}
