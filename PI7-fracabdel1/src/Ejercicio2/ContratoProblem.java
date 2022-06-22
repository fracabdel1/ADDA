package Ejercicio2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import us.lsi.common.List2;
import us.lsi.common.Set2;

public record ContratoProblem(Integer index, List<Integer> as, Integer presupRestante) {

	public static Set<String> cualidadesACubrir;
	
	public static void datosIniciales(String fichero) {
		
		datos_contrataciones.datos(fichero);
		ContratoProblem.cualidadesACubrir = datos_contrataciones.cualidadesDeseadas.stream().collect(Collectors.toSet());
		
	}
	
	public Set<String> getCualidadesRestantes(){
		return cualidadesACubrir;
	}
	
	public static ContratoProblem of(Integer index, List<Integer> as, Integer presupRestante) {
		return new ContratoProblem(index, as, presupRestante);
	}
	
	public static ContratoProblem initial() {
		return new ContratoProblem(0,new ArrayList<>(), datos_contrataciones.presupuesto);
	}
	
	public static Predicate<ContratoProblem> goal(){
		return v -> v.index() == datos_contrataciones.candidatos.size();
	}
	
	public List<Integer> alternativas() {
		
		List<Integer> la = List2.empty();
		
		if (index >= datos_contrataciones.getN()) {
			return la;
		}
		
		if (datos_contrataciones.candidatos.get(index).sueldoMin() <= presupRestante) {
			
			Boolean compatibilidad = true;
			
			for (int i=0; i<as.size(); i++) {
				if (!datos_contrataciones.esIncompatible(index, as.get(i))) continue;
				else {
					compatibilidad = false;
					break;
				}
			}
			
			if (compatibilidad) {
				la.add(0);
				la.add(1);
			} else {
				la.add(0);
			}
			
		} else {
			la.add(0);
		}
		
		return la;
		
	}

	public ContratoProblem vecino(Integer a) {
		
		List<Integer> as2 = List2.copy(as);
		Set<String> cualidades2 = Set2.copy(cualidadesACubrir);
		Integer presup2 = presupRestante;
		
		if (a == 1) {
			as2.add(index);
			cualidades2.removeAll(datos_contrataciones.candidatos.get(index).cualidades());
			presup2 = (int) (presup2 - datos_contrataciones.candidatos.get(index).sueldoMin());
		}
		return of(index + 1, as2, presup2);
	}
	
}