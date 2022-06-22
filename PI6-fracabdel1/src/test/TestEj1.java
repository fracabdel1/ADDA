package test;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.GraphPath;

import ejercicio1.FicheroEdge;
import ejercicio1.FicheroHeuristic;
import ejercicio1.FicheroVertex;
import ejercicio1.SolucionFichero;
import ejercicio1.datos_ficheros;
import us.lsi.common.List2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.AStar.AStarType;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;


public class TestEj1 {

	public static void Ejercicio1() {

		Locale.setDefault(new Locale("en", "US"));

		IntStream.range(1, 4).forEach(i -> {
			// inicializamos los datos
			datos_ficheros.iniDatos("fichero/PI5Ej1DatosEntrada" + i + ".txt");

			// Buscamos el vértice inicial
			FicheroVertex start = FicheroVertex.initial();

			Predicate<FicheroVertex> goal = v -> v.index() == datos_ficheros.getNumFicheros();

			// Grafo
			EGraph<FicheroVertex, FicheroEdge> graph;
			System.out.println("## Algoritmo A* ##");

			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// Algoritmo A* (esta parte es la que cambiariamos, el resto de test es igual, se reutiliza)
			graph = SimpleVirtualGraph.sum(start, goal, x -> x.weight());
			AStar<FicheroVertex, FicheroEdge> aStar = AStar.of(graph, FicheroHeuristic::heuristic, AStarType.Max); // Aqui minimizamos

			Optional<GraphPath<FicheroVertex, FicheroEdge>> gp = aStar.search();

			if(gp.isPresent()) { // Si hay un camino
				// Lista de aristas
				List<Integer> alternativas = List2.empty();
				for(FicheroEdge arista : gp.get().getEdgeList()) {
					alternativas.add(arista.action());
				}
				// Alternativa modo funcional
				alternativas = gp.get().getEdgeList().stream().map(a -> a.action()).collect(Collectors.toList());
				System.out.println(new SolucionFichero(alternativas));
			}else {
				System.out.println("No se ha encontrado una solución");
			}

			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//BackTracking

			BackTracking<FicheroVertex, FicheroEdge, SolucionFichero> a = BackTracking.of(graph, FicheroHeuristic::heuristic, SolucionFichero::create, BTType.Max);
			a.search();
			Optional<SolucionFichero> bt = a.getSolution();

			if (bt.isPresent()) {

				System.out.println("## Algoritmo BT ##");
				System.out.println(bt.get());

			} else {
				System.out.println("No hay solucion");
			}


			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// PDR

			DynamicProgrammingReduction<FicheroVertex, FicheroEdge> b = DynamicProgrammingReduction.of(graph, FicheroHeuristic::heuristic, PDType.Max);
			Optional<GraphPath<FicheroVertex, FicheroEdge>> PDR = b.search();

			if (PDR.isPresent()) {
				System.out.println("## Algoritmo PDR ##");
				System.out.println(SolucionFichero.create(PDR.get()));
			}
			else {
				System.out.println("No hay solución");
			}

		});

	}

}
