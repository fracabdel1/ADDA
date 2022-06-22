package problemas;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleWeightedGraph;
import clases.ArticuloComun;
import clases.Investigador;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.common.Pair;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;



public class Problema1 {
	
	public static void Ejercicio1(String fichero) {
		
		SimpleWeightedGraph<Investigador,ArticuloComun> grafo = lectura(fichero);
		EjercicioA(grafo);
		EjercicioB(grafo);
		EjercicioC(grafo);
		EjercicioD(grafo);
		EjercicioE(grafo);
		System.out.println("\nGrafos generados");
		
	}
	
	//Función de lectura
	
	public static SimpleWeightedGraph<Investigador, ArticuloComun> lectura(String fichero){
 
		return GraphsReader.newGraph(fichero,
				Investigador::ofFormat, 
				ArticuloComun::ofFormat,
				Graphs2::simpleWeightedGraph); 
	}

	// Ejercicio A)
		
	public static void EjercicioA(SimpleWeightedGraph<Investigador, ArticuloComun> grafo) {
		
		Predicate<Investigador> Menor1982 = e -> e.getFechaNacimiento() < 1982 || !grafo.edgesOf(e).stream()
				.filter(a -> a.getComun() > 5)
				.collect(Collectors.toList())
				.isEmpty(); // Stream para ver si tiene alguna arista mayor de 5
		Predicate<ArticuloComun> Mas5 = e -> e.getComun() > 5;
		
		GraphColors.toDot(grafo,
				"Salida/Ejercicio1A.gv",
				v -> v.getINV() + " " + v.getFechaNacimiento(),
				a -> a.getComun().toString(),
				v -> GraphColors.colorIf(Color.blue, Color.black, Menor1982.test(v)),
				a -> GraphColors.colorIf(Color.blue, Color.black, Mas5.test(a)));
		
	}
	
	// Ejercicio B)
	
	public static void EjercicioB(SimpleWeightedGraph<Investigador, ArticuloComun> grafo){
		
		Set<Investigador> vertex = grafo.vertexSet();
		List<Integer> grados = new ArrayList<>();
		Set<Investigador> res = new HashSet<>();
	
		for(Investigador v:vertex) { // Obtenemos los grados de todos los investigadores
			if(!grados.contains(grafo.degreeOf(v))) {
				grados.add(grafo.degreeOf(v));				
			}
		}
		
		grados = grados.stream().map(x -> x).sorted().collect(Collectors.toList()); // Ordenamos la lista
		
		
		for(int i = grados.size()-1; i >= 0; i--) { // Obtenemos los investigadores con los grados de mayor a menor
			for(Investigador e:vertex) {            // hasta que llenemos la lista (5 en este caso)
				if(res.size() < 5 && grafo.degreeOf(e) == grados.get(i)) {
					res.add(e);
				}
			}
		}
		
		// Imprimimos por pantalla los investigadores
		System.out.println("\nLos 5 investigadores que tienen un mayor número de investigadores colaboladores son: ");
		List<String> inv = new ArrayList<>();
		for(Investigador a:res) {
			inv.add(a.getINV());
		}
		System.out.println(inv);
		
		// Coloreamos el grafo
		
		GraphColors.toDot(grafo,
				"Salida/Ejercicio1B.gv", 
				v -> v.getINV(),
				a -> a.getComun().toString(),
				v -> GraphColors.colorIf(Color.blue, Color.green, res.contains(v)),
				a -> GraphColors.color(Color.green));
						
	}
	
	// Ejercicio C
	
	public static void EjercicioC(SimpleWeightedGraph<Investigador, ArticuloComun> grafo) {
		
		Map<Investigador, List<Investigador>> dicc = new HashMap<>();
		Set<Investigador> inv = grafo.vertexSet();
		
		for(Investigador i:inv) {
			List<Investigador> lista = new ArrayList<>();
			for(Investigador e: inv) {
				if(grafo.containsEdge(e, i)) {
					// Creamos estructura de diccionario
					if(dicc.containsKey(i)) {
						lista = dicc.get(i);
						lista.add(e);
						dicc.put(i, lista);
					}else {
						lista.add(e);
						dicc.put(i, lista);
					}
				}
			}
		}
		
		// Salida por consola
		System.out.println("\nLas listas de colaboladores ordenador por artículos conjuntos para cada investigador son:");
		for(Investigador e: inv) {
			List<String> res = new ArrayList<>();
			for(Investigador Ininvest: dicc.get(e)) {
				res.add(Ininvest.getINV());
			}
			System.out.println(e.getINV() + "->" + res);
		}
		
		// Coloreado del grafo
		Map<Investigador, Investigador> dicc2 = new HashMap<>();
		
		for(Investigador e:inv) { // Vamos a sacar las aristas más grandes de cada vértice
			for(Investigador a: dicc.get(e)) {
				if(dicc2.containsKey(e)) {
					if(grafo.getEdge(e, a).getComun() > grafo.getEdge(e, dicc2.get(e)).getComun()) {
						dicc2.put(e, a);
					}
				}else {
					dicc2.put(e, a);
				}
			}
		}
		
		List<ArticuloComun> listaAristas = new ArrayList<>();
		for(Investigador clave: inv) {
			listaAristas.add(grafo.getEdge(clave, dicc2.get(clave)));
		}
		
		Predicate<ArticuloComun> max = a -> listaAristas.contains(a);
		
		GraphColors.toDot(grafo,
				"Salida/Ejercicio1C.gv",
				v -> v.getINV(),
				a -> a.getComun().toString(),
				v -> GraphColors.color(Color.black),
				a -> GraphColors.colorIf(Color.blue, Color.black, max.test(a)));
	}
	
	// Ejercicio D)
	
	public static void EjercicioD(SimpleWeightedGraph<Investigador, ArticuloComun> grafo) {
		
		// Para resolverlo, vamos a hacer un recorrido en anchura, buscando aquel que más aristas tenga
		// ya que BreadthFirstIterator(V,E) devuelve el numero de aristas que han hecho falta, por lo que
		// al resultado dado, solo habría que sumarle 1
		
		Set<Investigador> inv = grafo.vertexSet();
		List<GraphPath<Investigador,ArticuloComun>> CaminoMinimo = new ArrayList<>();
		List<ArticuloComun> articuloComunCaminoMinimo = new ArrayList<>();
		List<Investigador> investigadoresCaminoMinimo = new ArrayList<>();
		List<Pair<String, String>> investigadorOrigenDestino  = new ArrayList<>();
		
		// Para hayar el camino mínimo, vamos a usa Dijkstra ya que los pesos son no negativos, si fueran negativos usaríamos FloydWharshall
		for(Investigador vertexOrigen: inv) {
			Investigador origen = vertexOrigen;
			for(Investigador vertexDestino: inv) {
				Investigador destino = vertexDestino;
				ShortestPathAlgorithm<Investigador,ArticuloComun> a = new DijkstraShortestPath<Investigador,ArticuloComun>(grafo);
				GraphPath<Investigador,ArticuloComun> gp =  a.getPath(origen,destino);
				if(CaminoMinimo.isEmpty() || CaminoMinimo.get(0).getWeight() < gp.getWeight()) {
					CaminoMinimo.clear();
					CaminoMinimo.add(gp); // Resultado completo de Dijkstra
					investigadoresCaminoMinimo.clear();
					investigadoresCaminoMinimo.addAll(gp.getVertexList()); // Vertices camino
					articuloComunCaminoMinimo.clear();
					articuloComunCaminoMinimo.addAll(gp.getEdgeList()); // Aristas camino
					investigadorOrigenDestino.clear();
					investigadorOrigenDestino.add(Pair.of(origen.getINV(), destino.getINV())); // Par origen, final
				}
			}
			
		}

		System.out.println("\nEl par de investigadores más lejanos es: " + investigadorOrigenDestino.get(0));
		
		// Dibujamos el grafo
		GraphColors.toDot(grafo,
				"Salida/Ejercicio1D.gv",
				v -> v.getINV(),
				a -> a.getComun().toString(),
				v -> GraphColors.colorIf(Color.blue, Color.black, investigadoresCaminoMinimo.contains(v)),
				a -> GraphColors.colorIf(Color.blue, Color.black, articuloComunCaminoMinimo.contains(a)));
		
	}
	
	// Ejercicio E

	public static void EjercicioE(SimpleWeightedGraph<Investigador, ArticuloComun> grafo) {

		Set<Investigador> inv = grafo.vertexSet();
		List<List<Investigador>> grupos = new ArrayList<>();

		for(Investigador i: inv) {
			boolean añadido = false;
			if(grupos.isEmpty()) { // Si la lista está vacia añadimos un investigador
				List<Investigador> apoyo = new ArrayList<>(); // Añadimos la primera lista
				apoyo.add(i);
				grupos.add(apoyo);
				añadido = true;
			}else {
				boolean distinto = true;
				for(int e = 0; e < grupos.size() && añadido == false; e++) { // Recorremos los grupos
					
					List<Investigador> apoyo = new ArrayList<>();
					// Recorremos cada lista del grupo
					for(int s = 0; s < grupos.get(e).size() && distinto == true; s++) {
						// Evaluamos si contiene algún investigador de la misma universidad
						if(grupos.get(e).get(s).getUniversidad().equals(i.getUniversidad()) ) {
							distinto = false;
						}
					}
					if(distinto) { // Sí no hay ninguno de la misma uni añadimos investigador
						apoyo.addAll(grupos.get(e));
						apoyo.add(i);
						grupos.remove(e); // Eliminamos la anterior lista
						grupos.add(apoyo); // Para añadir la lista ampliada
						añadido = true;
					}
				}
			}
			if(!añadido) { // Si no se ha añadido, añadimos otra lista con ese elemento
				List<Investigador> apoyo = new ArrayList<>();
				apoyo.add(i);
				grupos.add(apoyo);
			}
		}
		// Salida por consola
		System.out.println("\nLas reuniones serían:");
		for(List<Investigador> res : grupos) {
			if(res.size() > 1) {
				List<String> INV = new ArrayList<>();
				for(int i = 0; i < res.size(); i++) {
					INV.add(res.get(i).getINV());
				}
				System.out.println(INV);
			}
		}
		
		// Coloreado del grafo
		GraphColors.toDot(grafo,
				"Salida/Ejercicio1E.gv",
				v -> v.getINV() + v.getUniversidad(),
				a -> a.getComun().toString(),
				v -> GraphColors.color(Coloreado(grupos, v)),
				a -> GraphColors.color(Color.black));
	}
	
	// Función de coloreado del ejercicio 1 E
	private static Color Coloreado(List<List<Investigador>> grupos, Investigador v) {
		Color res;
		
		int numero = 0;
		boolean contiene = false;
		for(int i = 0; i < grupos.size() && contiene == false; i++) {
			if(grupos.get(i).contains(v)) contiene = true;
			numero = (i+3)%9; // Le sumamos 3 para que salga otra paleta, no me gusta la primera
		}
		
		switch(numero) {
		case 0:
			res = Color.black;
			break;
		case 1:
			res = Color.blank;
			break;
		case 2:
			res = Color.blue;
			break;
		case 3:
			res = Color.cyan;
			break;
		case 4:
			res = Color.gray;
			break;
		case 5:
			res = Color.green;
			break;
		case 6:
			res = Color.magenta;
			break;
		case 7:
			res = Color.orange;
			break;
		case 8:
			res = Color.red;
			break;
		case 9:
			res = Color.yellow;
			break;
		default:
			res = Color.red;
		}
		
		return res;
	}
}
