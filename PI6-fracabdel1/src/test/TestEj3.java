package test;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.GraphPath;

import ejercicio3.ComponentesEdge;
import ejercicio3.ComponentesHeuristic;
import ejercicio3.ComponentesVertex;
import ejercicio3.SolucionComponentes;
import ejercicio3.datos_componentes;
import us.lsi.common.List2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.alg.AStar.AStarType;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestEj3 {
	
	public static void Ejercicio3() {

		Locale.setDefault(new Locale("en", "US"));

		IntStream.range(1, 4).forEach(i -> {

			String fichero = "fichero/PI5Ej3DatosEntrada" + i + ".txt";
			ComponentesVertex.datosIniciales(fichero);


			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// Algoritmo A* (esta parte es la que cambiariamos, el resto de test es igual, se reutiliza)

			EGraph<ComponentesVertex, ComponentesEdge> graphA = SimpleVirtualGraph.sum(ComponentesVertex.initial(),ComponentesVertex.goal(), e -> e.weight());
			AStar<ComponentesVertex, ComponentesEdge> a = AStar.of(graphA, ComponentesHeuristic::heuristic, AStarType.Max);
			Optional<GraphPath<ComponentesVertex, ComponentesEdge>> opA = a.search();

			System.out.println("\n#### Algoritmo A* ####");

			if (opA.isPresent()) {
				System.out.println(SolucionComponentes.create(opA.get()));

			} else {
				System.out.println("No hay solucion");
			}


			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//BackTracking

			EGraph<ComponentesVertex, ComponentesEdge> graphBT = SimpleVirtualGraph.sum(ComponentesVertex.initial(), ComponentesVertex.goal(), e -> e.weight());
			BackTracking<ComponentesVertex, ComponentesEdge, SolucionComponentes> bt = BackTracking.of(graphBT, ComponentesHeuristic::heuristic, SolucionComponentes::create, BTType.Max);
			a.search();
			Optional<SolucionComponentes> opBT = bt.getSolution();

			System.out.println("\n#### Algoritmo BT ####");

			if (opBT.isPresent()) {
				System.out.println(opBT.get());

			} else {
				System.out.println("No hay solucion");
			}


			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// PDR

			EGraph<ComponentesVertex, ComponentesEdge> graphPDR = SimpleVirtualGraph.sum(ComponentesVertex.initial(),ComponentesVertex.goal(),e->e.weight());
			DynamicProgrammingReduction<ComponentesVertex, ComponentesEdge> pdr = DynamicProgrammingReduction.of(graphPDR, ComponentesHeuristic::heuristic, PDType.Max);
			Optional<GraphPath<ComponentesVertex, ComponentesEdge>> opPDR = pdr.search();

			System.out.println("\n#### Algoritmo PD ####");

			if (opPDR.isPresent()) {
				System.out.println(SolucionComponentes.create(opPDR.get()));
			}
			else {
				System.out.println("No hay solución");
			}

		});
	}
}
