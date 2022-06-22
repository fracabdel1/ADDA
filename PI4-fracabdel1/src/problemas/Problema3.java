package problemas;

import java.util.Set;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.tour.HeldKarpTSP;
import org.jgrapht.graph.SimpleWeightedGraph;

import clases.Calle;
import clases.Interseccion;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class Problema3 {

	public static void Ejercicio3(String fichero) {
		SimpleWeightedGraph<Interseccion, Calle> grafo = lectura(fichero);
		EjercicioA(grafo, "m7", "m2");
		EjercicioA(grafo, "m4", "m9");
		EjercicioB(grafo);
	}
	
	// Funcion de lectura
	public static SimpleWeightedGraph<Interseccion, Calle> lectura(String fichero){
		
		return GraphsReader.newGraph(fichero,
				Interseccion::ofFormat,
				Calle::ofFormat,
				Graphs2::simpleWeightedGraph);
	}
	
	// Ejercicio A
	public static void EjercicioA(SimpleWeightedGraph<Interseccion, Calle> grafo, String m1, String m2) {
		
		// Buscamos cuales son las intersecciones que contienen los monumentos
		Interseccion i1 = null;
		Interseccion i2 = null;
		
		for(Interseccion i: grafo.vertexSet()) {
			if(i.getNombreMonumento().equals(m1))	i1=i;		
			if(i.getNombreMonumento().equals(m2))	i2=i;		
		}
		
		// Usaremos Dijkstra ya que los pesos de las aristas no es nunca negativo (sino usariamos Floyd-Warshall)
		var alg = new DijkstraShortestPath<>(grafo);
		GraphPath<Interseccion, Calle> camino = alg.getPath(i1, i2);// Sacamos el camino mínimo
		
		// Coloreamos el grafo
		GraphColors.toDot(grafo,
				"Salida/Ejercicio3A.gv",
				v -> "INT-" + v.getId() + ", m" + v.getRelevancia(),
				a -> a.getDuracion().toString(),
				v -> GraphColors.colorIf(Color.blue, Color.black, camino.getVertexList().contains(v)),
				a -> GraphColors.colorIf(Color.blue, Color.black, camino.getEdgeList().contains(a)));
	
	}
	
	
	// Ejercicio B
	public static void EjercicioB(SimpleWeightedGraph<Interseccion, Calle> grafo) {
		
		// Estamos ante el problema del viajero (camino Hamiltoniano)
		var alg = new HeldKarpTSP<Interseccion, Calle>();
		GraphPath<Interseccion, Calle> camino = alg.getTour(grafo);
		
		GraphColors.toDot(grafo,
				"Salida/Ejercicio3B.gv",
				v -> "INT-" + v.getId() + ", m" + v.getRelevancia(),
				a -> a.getEsfuerzo().toString(),
				v -> GraphColors.colorIf(Color.blue, Color.black, camino.getVertexList().contains(v)), // No haria falta porque están todos los vertices contenidos, pero de esta forma comprobamos que funciona bien el camino 
				a -> GraphColors.colorIf(Color.blue, Color.black, camino.getEdgeList().contains(a)));

	}
	
	
	// EjercicioC
	public static void EjercicioC(SimpleWeightedGraph<Interseccion, Calle>grafo, Set<Calle> calles) {
		
	}
	
}
