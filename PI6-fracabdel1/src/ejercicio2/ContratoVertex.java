package ejercicio2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public record ContratoVertex(Integer index, List<Integer> candidatos, Integer presupRestante) implements VirtualVertex<ContratoVertex, ContratoEdge, Integer>{

	public static Set<String> cualidadesACubrir;

	public Set<String> getCualidadesRestantes(){
		return cualidadesACubrir;
	}
	public static ContratoVertex of(Integer index, List<Integer> candidatos, Integer presupRestante) {
		return new ContratoVertex(index, candidatos, presupRestante);
	}

	public static ContratoVertex initial() {
		ContratoVertex.cualidadesACubrir = datos_contrataciones.cualidadesDeseadas.stream().collect(Collectors.toSet());
		return new ContratoVertex(0,new ArrayList<>(), datos_contrataciones.presupuestoMax);
	}
	public static Predicate<ContratoVertex> goal(){
		return v -> v.index() == datos_contrataciones.contratos.size();
	}

	// Alternativas: escojo al candidato 1, no lo escojo 0

	public List<Integer> actions() {

		List<Integer> la = List2.empty();

		if (index >= datos_contrataciones.contratos.size()) {
			return la;
		}

		if (datos_contrataciones.contratos.get(index).sueldoMin() <= presupRestante) {

			Boolean compatibilidad = true;

			for (int i=0; i<candidatos.size(); i++) {
				if (datos_contrataciones.sonCompatibles(index, candidatos.get(i))) continue;
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

	public ContratoVertex neighbor(Integer a) {

		List<Integer> as2 = List2.copy(candidatos);
		Set<String> cualidades2 = Set2.copy(cualidadesACubrir);
		Integer presup2 = presupRestante;

		if (a == 1) {
			as2.add(index);
			cualidades2.removeAll(datos_contrataciones.contratos.get(index).cualidades());
			presup2 = (int) (presup2 - datos_contrataciones.contratos.get(index).sueldoMin());
		}
		return of(index + 1, as2, presup2);
	}

	public ContratoEdge edge(Integer a) {
		return ContratoEdge.of(this, this.neighbor(a), a);
	}

}

