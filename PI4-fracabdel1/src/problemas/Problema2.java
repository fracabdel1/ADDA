package problemas;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import clases.Libro;
import clases.LibroRelacion;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.common.Files2;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class Problema2 {
	
	public static void Ejercicio2(String fichero1, String fichero2) {
		SimpleDirectedGraph<Libro, LibroRelacion> grafo = lectura(fichero1);
		List<List<Libro>> libros = lectura2(fichero2);
		
		Libro L1 = new Libro("L3");
		Libro L2 = new Libro("L9");
		Libro L3 = new Libro("L7");
		
		EjercicioA(grafo);
//		EjercicioB(grafo, libros); // No funciona por fallo de la libreria
		EjercicioC(grafo, L1);
		EjercicioC(grafo, L2);
		EjercicioC(grafo, L3);
	}
	
	// Función de lectura arbol
	public static SimpleDirectedGraph<Libro, LibroRelacion> lectura(String fichero){
		
		SimpleGraph<Libro, LibroRelacion> grafoSimple = GraphsReader.newGraph(fichero,
				Libro::ofFormat, 
				LibroRelacion::ofFormat,
				Graphs2::simpleGraph);
		
		//System.out.println(grafoSimple);
		
//		return Graphs2.simpleWeightedGraph(grafoSimple, LibroRelacion::reverse);
		
		return GraphsReader.newGraph(fichero,
				Libro::ofFormat, 
				LibroRelacion::ofFormat,
				()->new SimpleDirectedGraph<>(Libro::of, LibroRelacion::of, false));
				
	}
	
	// Funcion lectura Test
	
	public static List<List<Libro>> lectura2(String fichero){
		
		List<String> ficheroString = Files2.linesFromFile(fichero);
		List<List<Libro>> libros = new ArrayList<>();
		for(String linea: ficheroString) {
			List<Libro> apoyo = new ArrayList<>();
			for(int i = 0; i < linea.length(); i++) {
				String cadena = "";
				if(linea.charAt(i) == 'L' && linea.charAt(i+2) == ',') {
					cadena = cadena + linea.charAt(i) + linea.charAt(i+1);
					Libro lib = new Libro(cadena);
					apoyo.add(lib);
				}
				if(linea.charAt(i) == 'L' && linea.charAt(i+2) != ',') {
					cadena = cadena + linea.charAt(i) + linea.charAt(i+1) + linea.charAt(i+2);
					Libro lib = new Libro(cadena);
					apoyo.add(lib);
				}
			}
			libros.add(apoyo);
		}
		
		return libros;
	}
	
	// EjercicioA
	
	public static void EjercicioA(SimpleDirectedGraph<Libro, LibroRelacion> grafo) {
		
		Set<Libro> libros = grafo.vertexSet();
		List<Libro> lista = new ArrayList<>();
		
		for(Libro lib:libros) {
			if(grafo.incomingEdgesOf(lib).size() == 0) {
				lista.add(lib);
			}
		}
		
		// Coloreamos el grafo
		GraphColors.toDot(grafo,
				"Salida/Ejercicio2A.gv",
				v -> v.getNombre(),
				a -> "",
				v -> GraphColors.colorIf(Color.blue, Color.black, lista.contains(v)),
				a -> GraphColors.color(Color.black));
		
	}
	
	// EjercicioB
	
	public static void EjercicioB(SimpleDirectedGraph<Libro, LibroRelacion> g, List<List<Libro>> lista) {
		DijkstraShortestPath<Libro, LibroRelacion> alg = new DijkstraShortestPath<>(g); // Camino mínimo
		String recomendacion = "hay solución";
		int pos = 1;
		
		for(List<Libro> lib: lista) {
			for (int i=0;i<lib.size() - 1;i++) {	
			GraphPath<Libro, LibroRelacion> gp = alg.getPath(lib.get(i), lib.get(i + 1));
			if (gp != null) {
				recomendacion = "hay solución";
			}else {
				recomendacion = "no hay solución";
			}
		}
			System.out.println("Para el Test " + pos + lib + recomendacion);
			pos++;
		}
		
		
	}
	
	// EjercicioC
	
public static void EjercicioC(SimpleDirectedGraph<Libro, LibroRelacion> grafo, Libro libro) {
		
		// Creamos el algoritmo de Dijkstra
		DijkstraShortestPath<Libro, LibroRelacion> camino = new DijkstraShortestPath<>(grafo);
		// Lista que saca del conjunto de vertices del grafo todos los libros que esten conectados con el libro
		// y que no incluyan al libro dado como parametro
		List<Libro> ls = grafo.vertexSet().stream()
						.filter(x -> !x.equals(libro) && camino.getPath(x, libro)!=null)
						.toList();
		
		/*Recorrido en Orden Topológico:
		Es un tipo de recorrido que se aplica a grafos dirigidos. En
		este recorrido cada vértice va después que los vértices que
		le anteceden en el grafo dirigido.
		La clase TopologicalOrderIterator<V,E> implementa un
		iterador que hace el recorrido en orden topológico. 
*/
		List<Libro> res = new ArrayList<>(); 
		TopologicalOrderIterator<Libro, LibroRelacion> orden = new TopologicalOrderIterator<>(grafo);
		orden.forEachRemaining(v -> res.add(v));

		// Borramos de la lista todos aquellos libros que no no pertenezcan al grafo
		grafo.vertexSet().stream()
		.filter(x -> !ls.contains(x))
		.forEach(x -> res.remove(x));
		
		List<String> fin = new ArrayList<>();
		for(Libro res2:res) {
			fin.add(res2.getNombre());
		}
		
		System.out.println("Para " + libro.getNombre() + " previamente hay que leer " + fin);
		
	}

	

	
	

}
