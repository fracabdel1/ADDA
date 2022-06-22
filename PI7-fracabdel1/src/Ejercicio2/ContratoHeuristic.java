package Ejercicio2;

public class ContratoHeuristic {
	
	public static Double heuristic(ContratoProblem v1) {
		
		Double h = 0.;
		if (v1.getCualidadesRestantes().isEmpty()) {
			return h;
		}
		for (int i=v1.index(); i<datos_contrataciones.candidatos.size(); i++) {
			Integer valor = datos_contrataciones.candidatos.get(i).valoracion();
			h += valor;
		}
		
		return h;
		
	}
	
	public static Double cota(ContratoProblem v1, Integer a) {
		ContratoProblem v2 = v1.vecino(a);
		return heuristic(v2) + a * datos_contrataciones.getValoracion(v1.index());
	}

}