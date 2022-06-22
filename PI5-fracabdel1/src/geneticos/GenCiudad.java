package geneticos;

import java.util.List;
import java.util.function.Predicate;

import org.jgrapht.graph.SimpleWeightedGraph;

import datos_ejemplos.datos_ciudad;
import datos_ejemplos.datos_ciudad.Carretera;
import datos_ejemplos.datos_ciudad.Ciudad;
import us.lsi.ag.SeqNormalData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.graphs.views.IntegerVertexGraphView;

public class GenCiudad implements SeqNormalData<SolucionCiudades>{
	
	Integer n = datos_ciudad.n;
	SimpleWeightedGraph<Ciudad, Carretera> gf = datos_ciudad.gf;
	private IntegerVertexGraphView<Ciudad, Carretera> graph = datos_ciudad.graph;
	private Predicate<Ciudad> predicadoCiudad = datos_ciudad.predicadoCiudad;
	private Predicate<Carretera> predicadoCarretera = datos_ciudad.predicadoCarretera;
	
	public ChromosomeType type() {
		return ChromosomeType.PermutationSubList;
	}
	
	public Double fitnessFunction(List<Integer> value) {
		
		Double objetivo = 0.;
		Double restricciones = 0.;
		Integer testCiudad = 0;
		Integer testCarretera = 0;
		
		if(value.size()>=3) {
			
			for (int i = 0; i<value.size() - 1; i++) {
				
				Ciudad ciudadInicial = datos_ciudad.graph.getVertex(value.get(i));
				Ciudad ciudadSiguiente = datos_ciudad.graph.getVertex(value.get(i+1));
				
				if (gf.containsEdge(ciudadInicial, ciudadSiguiente)) {
					
					Carretera carretera = gf.getEdge(ciudadInicial, ciudadSiguiente);
					testCarretera += predicadoCarretera.test(carretera) ? 0 : 1;
					objetivo += carretera.km();
					
				} else {
					restricciones += 1;
				} if (!ciudadInicial.equals(datos_ciudad.origen)) {
					testCiudad += predicadoCiudad.test(ciudadInicial) ? 0 : 1;
				}
			}
			
			Integer origen = datos_ciudad.origen.equals(graph.getVertex(value.get(0))) ? 0 : 1;
			Integer destino = datos_ciudad.destino.equals(graph.getVertex(value.get(value.size() - 1))) ? 0 : 1;
			
			// Sumamos las restricciones que se hayan dado
			
			restricciones += origen + destino + testCiudad + testCarretera;
		
		// Restriccion ciudad intermedia
		
		} else {
			restricciones += 1;
		}
		return restricciones < 1 ? - objetivo : - objetivo -145689.0 * restricciones;
	}
	
	public SolucionCiudades solucion(List<Integer> value) {
		return SolucionCiudades.create(value);
	}
	
	public Integer itemsNumber() {
		// TODO Auto-generated method stub
		return n;
	}

	public static GenCiudad of() {
		return new GenCiudad();
	}

}
