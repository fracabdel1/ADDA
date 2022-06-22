package Ejercicio1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import us.lsi.common.List2;

public class FicheroBT {
	
	public static class StateFicheros {
		
		FicheroProblem vertice;
		Integer valorAcumulado;
		List<Integer> acciones; 
		List<FicheroProblem> vertices;
		
		private StateFicheros(FicheroProblem vertice, Integer valorAcumulado, List<Integer> acciones, List<FicheroProblem> vertices) {
			super();
			this.vertice = vertice;
			this.valorAcumulado = valorAcumulado;
			this.acciones = acciones;
			this.vertices = vertices;
		}
		
		public static StateFicheros of(FicheroProblem vertex) {
			List<FicheroProblem> vt = List2.of(vertex);
			return new StateFicheros(vertex,0,new ArrayList<>(),vt);
		}
		
		
		void forward(Integer a) {
			this.acciones.add(a); 
			FicheroProblem vecino = this.vertice().neighbor(a);		
			this.vertices.add(vecino);
			
			if (a < datos_ficheros.memorias.size()) {
				this.valorAcumulado = this.valorAcumulado() + 1; 
			} else {
				this.valorAcumulado = this.valorAcumulado();
			}
			
			this.vertice = vecino;
		}

		void back(Integer a) {
			this.acciones.remove(this.acciones.size()-1);
			this.vertices.remove(this.vertices.size()-1);
			this.vertice = this.vertices.get(this.vertices.size()-1);
			
			if (a < datos_ficheros.memorias.size()) {
				this.valorAcumulado = this.valorAcumulado() - 1; 
			} else {
				this.valorAcumulado = this.valorAcumulado();
			}
			
		}
		
		SolucionFichero solucion() {
			return SolucionFichero.of(FicheroBT.start,this.acciones);
		}
		
		public FicheroProblem vertice() {
			return vertice;
		}

		public Integer valorAcumulado() {
			return valorAcumulado;
		}
		
	}
	
	public static FicheroProblem start;
	public static StateFicheros estado;
	public static Integer maxValue;
	public static Set<SolucionFichero> soluciones;
	
	public static void search(List<Integer> capacidadInicial) {
		FicheroBT.start = FicheroProblem.of(0, capacidadInicial);
		FicheroBT.estado = StateFicheros.of(start);
		FicheroBT.maxValue = Integer.MIN_VALUE; // Para maximizar, empezamos por el mínimo y avanzamos hasta el MAXimo, si queremos minimizar lo hacemos al reves
		FicheroBT.soluciones = new HashSet<>();
		search();
	}
	
	public static void search() {
		// Si el valor del index == al numero de ficheros, hemos llegado al final
		if (FicheroBT.estado.vertice().index() == datos_ficheros.ficheros.size()) {
			Integer value = estado.valorAcumulado();
			if (value > FicheroBT.maxValue) { // Para minimizar cambiamos a <
				FicheroBT.maxValue = value;
				FicheroBT.soluciones.add(FicheroBT.estado.solucion());
			}
		} else {
			
			List<Integer> alternativas = FicheroBT.estado.vertice().actions();
			for (Integer a: alternativas) {
				Integer cota = FicheroBT.estado.valorAcumulado() + FicheroHeuristic.cota(FicheroBT.estado.vertice(), a);
				if(cota < FicheroBT.maxValue) continue;  // Para minimizar cambiamos a >
				FicheroBT.estado.forward(a);
				search();
				FicheroBT.estado.back(a);
			}
		}
	}
	
	public static void main(String[] args) {
		
		Locale.setDefault(new Locale("en", "US"));
		
		for (int i=1; i<=1; i++) {
			String fichero = "fichero/PI5Ej1DatosEntrada" + i + ".txt";
			System.out.println("- Para el fichero " + fichero.replace("ficheros/", "") + "\n");
			FicheroProblem.datosIniciales(fichero);
			FicheroBT.search(datos_ficheros.capacidadInicial());
			System.out.println(soluciones.stream().max(Comparator.comparing(x -> x.getTamaño())).orElse(null));
		}
	}
}