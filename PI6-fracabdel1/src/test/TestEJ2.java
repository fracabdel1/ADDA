package test;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.GraphPath;

import ejercicio1.FicheroVertex;
import ejercicio1.datos_ficheros;
import ejercicio2.ContratoEdge;
import ejercicio2.ContratoHeuristic;
import ejercicio2.ContratoVertex;
import ejercicio2.SolucionContrataciones;
import ejercicio2.datos_contrataciones;
import us.lsi.common.List2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.alg.AStar.AStarType;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestEJ2 {
	
public static void Ejercicio2() {
		
		Locale.setDefault(new Locale("en", "US"));
		
		IntStream.range(1, 4).forEach(i -> {
			// inicializamos los datos
			datos_contrataciones.iniDatos("fichero/PI5Ej2DatosEntrada" + i + ".txt");
			
			// Buscamos el vértice inicial
			ContratoVertex start = ContratoVertex.initial();
			
			Predicate<ContratoVertex> goal = v -> v.index() == datos_contrataciones.getNumContratos();
			
			// Grafo
			EGraph<ContratoVertex, ContratoEdge> graph;
			System.out.println("## Algoritmo A* ##");
			
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// Algoritmo A* (esta parte es la que cambiariamos, el resto de test es igual, se reutiliza)
			graph = SimpleVirtualGraph.sum(start, goal, x -> x.weight());
			AStar<ContratoVertex, ContratoEdge> aStar = AStar.of(graph, ContratoHeuristic::heuristic, AStarType.Max); // Aqui minimizamos
			
			Optional<GraphPath<ContratoVertex, ContratoEdge>> gp = aStar.search();
			
			if(gp.isPresent()) { // Si hay un camino
				// Lista de aristas
				List<Integer> alternativas = List2.empty();
				for(ContratoEdge arista : gp.get().getEdgeList()) {
					alternativas.add(arista.action());
				}
				// Alternativa modo funcional
				alternativas = gp.get().getEdgeList().stream().map(a -> a.action()).collect(Collectors.toList());
				System.out.println(new SolucionContrataciones(alternativas));
			}else {
				System.out.println("No se ha encontrado una solución");
			}
			
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//BackTracking
			
			BackTracking<ContratoVertex, ContratoEdge, SolucionContrataciones> a = BackTracking.of(graph, ContratoHeuristic::heuristic, SolucionContrataciones::create, BTType.Max);
			a.search();
			Optional<SolucionContrataciones> bt = a.getSolution();
			
			if (bt.isPresent()) {
				
				System.out.println("## Algoritmo BT ##");
				System.out.println(bt.get());
				
			} else {
				System.out.println("No hay solucion");
			}
			
			
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// PDR
			
			DynamicProgrammingReduction<ContratoVertex, ContratoEdge> b = DynamicProgrammingReduction.of(graph, ContratoHeuristic::heuristic, PDType.Max);
			Optional<GraphPath<ContratoVertex, ContratoEdge>> PDR = b.search();
			
			if (PDR.isPresent()) {
				System.out.println("## Algoritmo PDR ##");
				System.out.println(SolucionContrataciones.create(PDR.get()));
			}
			else {
				System.out.println("No hay solución");
			}
			
		});
		
	}

}


