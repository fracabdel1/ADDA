package ejercicio1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ejercicio1.datos_ficheros.Fichero;
import ejercicio1.datos_ficheros.Memoria;
import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

public record FicheroVertex(Integer index, List<Integer> capacidadRestante) implements VirtualVertex<FicheroVertex, FicheroEdge, Integer>{
	
	// Listas de memorias y ficheros de la clase datos_ficheros para que sea más sencillo
	public static List<Fichero> ficheros = datos_ficheros.ficheros;
	public static List<Memoria> memorias = datos_ficheros.memorias;

	public static void datosIniciales(String fichero) {
		
		datos_ficheros.iniDatos(fichero);
		System.out.println(memorias);
		
		
	}
	
	public static FicheroVertex of(Integer index, List<Integer> capacidadRestante) { // Indice del fichero y la capacidad restante de las memorias
		return new FicheroVertex(index, capacidadRestante);
	}
	public static FicheroVertex initial() {
		FicheroVertex.capacidadInicial = memorias.stream().map(m -> m.capacidad()).toList();
		return of(0, FicheroVertex.capacidadInicial);
	}

	
	public static List<Integer> capacidadInicial;
	
	public List<Integer> actions() {
		
		List<Integer> la = List2.empty();
		
		if (index >= datos_ficheros.getNumFicheros()) { // Si es el ultimo elemento salimos
			return la;
		} 
		
		List<Integer> mem = IntStream.rangeClosed(0, memorias.size()).boxed().collect(Collectors.toList()); 
		// boxed: Without boxing the stream items, we cannot perform the regular stream operations on them. For example, we cannot collect the int values to a list, directly.
		
		for (int i: mem) {
			
			// Metemos en las alternativas aquellas memorias que tengan espacio suficiente
			
			if (i<memorias.size() && ficheros.get(index).tam() <= memorias.get(i).tamMax()
				&& ficheros.get(index).tam() <= capacidadRestante.get(i)) {
				
				la.add(i);
				
			// También consideramos la opción de que el archivo no se meta en ninguna memoria
				
			} else if (i == datos_ficheros.memorias.size()) {
				la.add(i);
			}
		}
		return la;
	}

	public FicheroVertex neighbor(Integer a) {
		
		List<Integer> copia = List2.copy(capacidadRestante);
		
		if (a < memorias.size()) copia.set(a, copia.get(a) - datos_ficheros.getTamFichero(index));
		
		return of(index + 1, copia);
	}

	public FicheroEdge edge(Integer a) {
		return FicheroEdge.of(this, this.neighbor(a), a);
	}
	
}


/*
public record FicheroVertex (Integer indice, HashMap<Memoria, List<Fichero>> FicherosXMemoria) implements VirtualVertex<FicheroVertex, FicheroEdge, Integer>{

	// Listas de memorias y ficheros de la clase datos_ficheros para que sea más sencillo
	public static List<Fichero> ficheros = datos_ficheros.ficheros;
	public static List<Memoria> memorias = datos_ficheros.memorias;
	
	// Creamos el diccionario de las memorias vacio y lista de ficheros vacia
	public static HashMap<Memoria, List<Fichero>> FicheroXMemoria = new HashMap<>();
	public static List<Fichero> ListaVacia = List2.empty();
	
	// Creamos el vértice
	public static FicheroVertex of(Integer indice, HashMap<Memoria, List<Fichero>> FicheroXMemoria) {
		return new FicheroVertex(indice, FicheroXMemoria);
	}
	
	// Creamos el vertice inicial
	public static FicheroVertex initial() { // Vertice 0 con los grupos que haya disponibles inicializados a 0
		Memoria descarte = new Memoria("Mem0", 100000000, 100000000); // Memoria de descarte, para los ficheros que no se cojan
		FicheroXMemoria.put(descarte, ListaVacia);// Añadimos la memoria de descarte
		IntStream.range(0, memorias.size()).forEach(i -> FicheroXMemoria.put(memorias.get(i), ListaVacia)); // Añadimos las memorias
		return new FicheroVertex(0, FicheroXMemoria);
	}
	
	// Acciones de cada vértice
	@Override
	public List<Integer> actions() { // son las acciones o alternativas disponibles
		// TODO Auto-generated method stub
		
		// Si ya hemos procesado todos los fichero (vértices) devolvemos una lista vacia de acciones
		if(indice >= datos_ficheros.getNumFicheros()) {
			return new ArrayList<>();
		}
		
		// Si existen vértices sin procesar, seguimos añadiendolos en las memorias
		List<Integer> res = IntStream.range(0, FicheroXMemoria.size() + 1) // +1 porque range es exclusivo
				.filter(x -> ficheros.get(indice).tam() <= memorias.get(x).tamMax()) // Restricción tamaño máximo permitido de archivo en cada memoria
				.filter(x -> ficheros.get(indice).tam() <= memoriaOcupada(memorias.get(x), FicherosXMemoria.get(memorias.get(x)))) // Restriccion de que no se superre la capacidad máxima
				.mapToObj(x->x)
				.collect(Collectors.toList());
				
		return res;
	}

	@Override
	public FicheroVertex neighbor(Integer a) { // Vértice vecino, teniendo en cuenta las acciones
		// TODO Auto-generated method stub
		
		// Copiamos el diccionario para evitar modificaciones en el diccionario fuente
		HashMap<Memoria, List<Fichero>> dicc2 = (HashMap<Memoria, List<Fichero>>) Map.copyOf(FicheroXMemoria);
		
		if(a != 0) { // 0 es la memoria de descarte
			// Metemos el fichero en la memoria
			var lista = dicc2.get(memorias.get(a - 1)); // Como el 0 es la de descarte, las memorias 1 y 2 estaran en una posición menos
			lista.add(ficheros.get(indice)); // Añadimos en dicha memoria el fichero que tenga ese indice
			dicc2.put(memorias.get(a - 1), lista); // Añadimos al diccionario los elementos modificados
		}
		
		return FicheroVertex.of(a + 1, dicc2); // Aumentamos el indice en 1
	}

	@Override
	public FicheroEdge edge(Integer a) { // Creamos la arista que une 2 vertices
		// TODO Auto-generated method stub
		
		return FicheroEdge.of(this, this.neighbor(a), a); // this es el vertice actual
	}
	
	// Método auxiliar que te devuelva la capacidad restante de la memoria seleccionada
	public static Integer memoriaOcupada(Memoria mem, List<Fichero> ficheros) { // Pasamos como parametrola memoria y la lista de ficheros de una memoria
		Integer capacidad = mem.capacidad();
		Integer ocupado = 0;
		
		for(Fichero x: ficheros) {
			ocupado += x.tam();
		}
		
		return capacidad - ocupado;
		
	}

}

*/
