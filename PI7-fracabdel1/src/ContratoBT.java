package Ejercicio2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import us.lsi.common.List2;

public class ContratoBT {
	
	public static class StateCandidatos {
		
		ContratoProblem vertice;
		Integer valorAcumulado;
		List<Integer> acciones; 
		List<ContratoProblem> vertices;
		
		
		private StateCandidatos(ContratoProblem vertice, Integer valorAcumulado, List<Integer> acciones, List<ContratoProblem> vertices) {
			super();
			this.vertice = vertice;
			this.valorAcumulado = valorAcumulado;
			this.acciones = acciones;
			this.vertices = vertices;
		}
		
		public static StateCandidatos of(ContratoProblem vertex) {
			List<ContratoProblem> vt = List2.of(vertex);
			return new StateCandidatos(vertex,0,new ArrayList<>(),vt);
		}
		void forward(Integer a) {
			this.acciones.add(a);
			ContratoProblem vecino = this.vertice().vecino(a);		
			this.vertices.add(vecino);
			this.valorAcumulado = this.valorAcumulado() + a * datos_contrataciones.getValoracion(vertice.index()); 
			this.vertice = vecino;
		}

		void back(Integer a) {
			this.acciones.remove(acciones.size()-1);
			this.vertices.remove(vertices.size()-1);
			this.vertice = this.vertices.get(vertices.size()-1);
			this.valorAcumulado = this.valorAcumulado() - a * datos_contrataciones.getValoracion(vertice.index()); 
		}
		
		SolucionContrato solucion() {
			return SolucionContrato.of(start,this.acciones);
		}
		
		public ContratoProblem vertice() {
			return vertice;
		}

		public Integer valorAcumulado() {
			return valorAcumulado;
		}
		
	}
	
	public static ContratoProblem start;
	public static StateCandidatos estado;
	public static Integer maxValue;
	public static Set<SolucionContrato> soluciones;
	
	public static void search(Integer presupRestante) {
		ContratoBT.start = ContratoProblem.of(0, new ArrayList<>(),presupRestante);
		ContratoBT.estado = StateCandidatos.of(start);
		ContratoBT.maxValue = Integer.MIN_VALUE;
		ContratoBT.soluciones = new HashSet<>();
		search();
	}
	
	public static void search() {
		
		if (ContratoBT.estado.vertice().index() == datos_contrataciones.candidatos.size()) {
			Integer value = estado.valorAcumulado();
			if (value > ContratoBT.maxValue) {
				ContratoBT.maxValue = value;
				ContratoBT.soluciones.add(ContratoBT.estado.solucion());
			}
		} else {
			
			List<Integer> alternativas = ContratoBT.estado.vertice().alternativas();
			for (Integer a: alternativas) {
				Double cota = ContratoBT.estado.valorAcumulado() + ContratoHeuristic.cota(ContratoBT.estado.vertice(), a);
				if(cota < ContratoBT.maxValue) continue;
				ContratoBT.estado.forward(a);
				search();
				ContratoBT.estado.back(a);
			}
		}
	}
	
	public static void main(String[] args) {
		
		Locale.setDefault(new Locale("en", "US"));
		
		for (int i=1; i<=3; i++) {
			String fichero = "fichero/PI5Ej2DatosEntrada" + i + ".txt";
			System.out.println("- Para el fichero " + fichero.replace("ficheros/", "") + "\n");
			ContratoProblem.datosIniciales(fichero);
			ContratoBT.search(datos_contrataciones.presupuesto);
			System.out.println(ContratoBT.soluciones.stream().max(Comparator.comparing(x -> x.getValoracionTotal())).orElse(null));
		}
	}
}
