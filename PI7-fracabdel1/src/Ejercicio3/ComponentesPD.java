package Ejercicio3;

import java.util.*;

public class ComponentesPD {

	public static record Sp(Integer a, Integer weight) implements Comparable<Sp> {
		
		public static Sp of(Integer a, Integer weight) {
			return new Sp(a, weight);
		}

		public int compareTo(Sp sp) {
			return this.weight.compareTo(sp.weight);
		}
	}
		
	public static Integer maxValue = Integer.MIN_VALUE; // Para maximizar, empezamos por el mínimo y avanzamos hasta el MAXimo, si queremos minimizar lo hacemos al reves
	public static ComponentesProblem start;
	public static Map<ComponentesProblem,Sp> memory;
		
	public static SolucionComponentes pd(Integer tProdInicial, Integer tElabInicial) {
		ComponentesPD.maxValue = Integer.MIN_VALUE;
		ComponentesPD.start = ComponentesProblem.of(0, tProdInicial, tElabInicial);
		ComponentesPD.memory = new HashMap<>();
		pd(start, 0, memory);
		return ComponentesPD.solucion();
	}
	
	public static Sp pd(ComponentesProblem vertex, Integer accumulateValue, Map<ComponentesProblem, Sp> memory) {
		
		Sp res;
		Integer n = datos_componentes.productos.size();
		
		if (memory.containsKey(vertex)) {
			res = memory.get(vertex);
		} else if (vertex.index() == n) {
			
			res = Sp.of(null, 0);
			memory.put(vertex, res);
			if (accumulateValue > ComponentesPD.maxValue) ComponentesPD.maxValue = accumulateValue;
			
		} else {
			
			List<Sp> soluciones = new ArrayList<>();
			for (Integer a: vertex.alternativas()) {
				
				Double cota = accumulateValue + ComponentesHeuristic.cota(vertex, a);
				if(cota < ComponentesPD.maxValue) continue;
				Sp s = pd(vertex.vecino(a), accumulateValue + a * datos_componentes.productos.get(vertex.index()).precio(), memory);
				
				if(s != null) {
					Sp sp = Sp.of(a, s.weight() + a * datos_componentes.getPrecio(vertex.index()));
					soluciones.add(sp);
				}
				
			}
			res = soluciones.stream().max(Comparator.naturalOrder()).orElse(null);
			if (res!=null) memory.put(vertex, res);
		}
		return res;
	}
	
	public static SolucionComponentes solucion() {
		
		List<Integer> alternativas = new ArrayList<>();
		ComponentesProblem v = ComponentesPD.start;
		Sp s = ComponentesPD.memory.get(v);
		while(s.a() != null) {
			alternativas.add(s.a());
			v = v.vecino(s.a());
			s = ComponentesPD.memory.get(v);
		}
		return SolucionComponentes.of(ComponentesPD.start,alternativas);
	}
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		
		for (int i = 1; i<=3; i++) {
			String fichero = "fichero/PI5Ej3DatosEntrada" + i + ".txt";
			System.out.println("- Para el fichero " + fichero.replace("ficheros/", "") + "\n");
			datos_componentes.datos(fichero);
			ComponentesPD.pd(datos_componentes.TotalProd, datos_componentes.TotalManual);
			System.out.println(ComponentesPD.solucion());
		}
	}
}