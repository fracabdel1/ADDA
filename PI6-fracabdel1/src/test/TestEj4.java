package test;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import org.jgrapht.GraphPath;

import ejercicio1.FicheroVertex;
import ejercicio1.datos_ficheros;
import ejercicio4.ContenedorEdge;
import ejercicio4.ContenedorHeuristic;
import ejercicio4.ContenedorVertex;
import ejercicio4.SolucionContenedores;
import ejercicio4.datos_contenedores;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.AStar.AStarType;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestEj4 {

	public static void Ejercicio4() {

		Locale.setDefault(new Locale("en", "US"));

		IntStream.range(1, 4).forEach(i -> {
			// inicializamos los datos
			ContenedorVertex.datosIniciales("fichero/PI5Ej4DatosEntrada" + i + ".txt");

			// Buscamos el vértice inicial
			ContenedorVertex start = ContenedorVertex.initial();
			

			Predicate<ContenedorVertex> goal = v -> v.index() == datos_contenedores.elementos.size();

			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// Algoritmo A* (esta parte es la que cambiariamos, el resto de test es igual, se reutiliza)

			EGraph<ContenedorVertex, ContenedorEdge> graphA = SimpleVirtualGraph.last(ContenedorVertex.initial(), goal, v -> v.weight(),ContenedorVertex.constraint());
			AStar<ContenedorVertex, ContenedorEdge> a = AStar.of(graphA,ContenedorHeuristic::heuristic,AStarType.Max);
			Optional<GraphPath<ContenedorVertex, ContenedorEdge>> opA = a.search();

			if (opA.isPresent()) {

				System.out.println("#### Algoritmo A* ####");
				System.out.println(SolucionContenedores.create(opA.get()));

			} else {
				System.out.println("No hay solucion");
			}


			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//BackTracking

			EGraph<ContenedorVertex, ContenedorEdge> graphBT = SimpleVirtualGraph.last(ContenedorVertex.initial(), goal, v -> v.weight(), ContenedorVertex.constraint());
			BackTracking<ContenedorVertex, ContenedorEdge, SolucionContenedores> BT = BackTracking.of(graphBT, ContenedorHeuristic::heuristic, SolucionContenedores::create, BTType.Max);
			BT.search();
			Optional<SolucionContenedores> opBT = BT.getSolution();

			if (opBT.isPresent()) {

				System.out.println("#### Algoritmo BT ####");
				System.out.println(opBT.get());

			} else {
				System.out.println("No hay solucion");
			}


			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// PDR

			EGraph<ContenedorVertex, ContenedorEdge> graphPDR = SimpleVirtualGraph.last(ContenedorVertex.initial(), goal,v -> v.weight(),ContenedorVertex.constraint());
			DynamicProgrammingReduction<ContenedorVertex, ContenedorEdge> PDR = DynamicProgrammingReduction.of(graphPDR, ContenedorHeuristic::heuristic, PDType.Max);
			Optional<GraphPath<ContenedorVertex, ContenedorEdge>> opPDR = PDR.search();

			if (opPDR.isPresent()) {

				System.out.println("#### Algoritmo PD ####");
				System.out.println(SolucionContenedores.create(opPDR.get()));

			} else {
				System.out.println("No hay solución");
			}


		});
	}
}





