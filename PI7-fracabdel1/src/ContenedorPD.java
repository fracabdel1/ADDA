package Ejercicio4;

import java.util.*;

public class ContenedorPD {

	public static record Sp(Integer a, Integer weight) implements Comparable<Sp>{
		
		public static Sp of(Integer a, Integer weight) {
			return new Sp(a, weight);
		}

		public int compareTo(Sp sp) {
			return this.weight.compareTo(sp.weight);
		}
		
	}
	
	public static Integer maxValue = Integer.MIN_VALUE;
	public static ContenedorProblem start;
	public static Map<ContenedorProblem,Sp> memory;
	
	public static SolucionContenedor pd(List<Integer> capacidadInicial) {
		ContenedorPD.maxValue = Integer.MIN_VALUE;
		ContenedorPD.start = ContenedorProblem.of(0, capacidadInicial);
		ContenedorPD.memory = new HashMap<>();
		pd(start, 0, memory);
		return ContenedorPD.solucion();
	}
	

	public static Sp pd(ContenedorProblem vertex, Integer accumulateValue, Map<ContenedorProblem, Sp> memory) {
		
		Sp res;
		Integer n = datos_contenedores.elementos.size();
		
		if (memory.containsKey(vertex)) {
			res = memory.get(vertex);
		} else if (vertex.index() == n) {
			res = null;
			if (ContenedorProblem.constraint().test(vertex)) {
				res = Sp.of(null, 0);
				memory.put(vertex, res);
				if (accumulateValue > ContenedorPD.maxValue) ContenedorPD.maxValue = accumulateValue;
			}
			memory.put(vertex, res);
		} else {
			List<Sp> soluciones = new ArrayList<>();
			for (Integer a: vertex.alternativas()) {
				Double cota = ContenedorHeuristic.cota(vertex, a);
				if (cota < ContenedorPD.maxValue) continue;
				Sp s = pd(vertex.vecino(a), vertex.peso(), memory);
				if (s != null) {
					Sp sp = Sp.of(a, s.weight());
					soluciones.add(sp);
				}
			}
			res = soluciones.stream().max(Comparator.naturalOrder()).orElse(null);
			if (res != null) memory.put(vertex, res);
		}
		return res;
	}
	
	public static SolucionContenedor solucion() {
		List<Integer> alternativas = new ArrayList<>();
		ContenedorProblem v = ContenedorPD.start;
		Sp s = ContenedorPD.memory.get(v);
		while(s.a() != null) {
			alternativas.add(s.a());
			v = v.vecino(s.a());	
			s = ContenedorPD.memory.get(v);
		}
		return SolucionContenedor.of(ContenedorPD.start, alternativas);
	}
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		
		for (int i = 1; i<=2; i++) {
			String fichero = "fichero/PI5Ej4DatosEntrada" + i + ".txt";
			System.out.println("- Para el fichero " + fichero.replace("ficheros/", "") + "\n");
			ContenedorProblem.datosIniciales(fichero);
			ContenedorPD.pd(ContenedorProblem.capacidadInicial);
			System.out.println(ContenedorPD.solucion());
		}
		
	}
}
