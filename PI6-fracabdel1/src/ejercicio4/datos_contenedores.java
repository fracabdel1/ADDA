package ejercicio4;

import java.util.List;

import us.lsi.common.Files2;
import us.lsi.common.List2;

public class datos_contenedores {

	public static record Elemento(String id, Integer tamaño, List<String> tipos) {
		
		private static Elemento create(String s) {
			
			String[] v = s.split(":");
			String id = v[0].trim();
			
			String[] info = v[1].trim().split(";");
			Integer tamaño = Integer.valueOf(info[0].trim());
			List<String> tipos = List2.parse(info[1].trim().split(","), String::new);  

			return new Elemento(id, tamaño, tipos);
		}
		public String toString() {
			return id() + ": " + tamaño() + "; " + tipos();
		}
	}
		
	public static record Contenedor(String id, Integer capacidad, String tipo) {
			
		private static Contenedor create(String s) {
				
			String[] v = s.split(":");
			String id = v[0].trim();
			
			String[] info = v[1].trim().split(";");
			Integer capacidad = Integer.valueOf(info[0].replace("capacidad=", "").trim());
			String tipo = info[1].replace("tipo=", "").trim();
			return new Contenedor(id, capacidad, tipo);
			
		}
		
		public String toString() {
			return id();
		}
	}
	
	public static List<Elemento> elementos;
	public static List<Contenedor> contenedores;
	
	public static Integer getN() {
		return elementos.size();
	}
	public static Integer getM() {
		return contenedores.size();
	}
	public static Integer tamElem(Integer i) {
		return elementos.get(i).tamaño();
	}
	public static Integer capacidadContenedor(Integer j) {
		return contenedores.get(j).capacidad();
	}
	
	// Devuelve true si el elemento i se puede meter en el contenedor j
	
	public static Boolean esCompatible(Integer i, Integer j) {
		return elementos.get(i).tipos().contains(contenedores.get(j).tipo());
	}
	public static Integer tipoCompatible2(Integer i, Integer j) {
		return elementos.get(i).tipos().contains(contenedores.get(j).tipo()) ? 1 : 0;
	}
	
	// Datos contenedores
	
	public static void datos(String fichero) {
		
		List<String> lineas = Files2.linesFromFile(fichero);
		
		Integer puntero1 = lineas.indexOf("// CONTENEDORES");
		Integer puntero2 = lineas.indexOf("// ELEMENTOS");
		
		contenedores = lineas.subList(puntero1 + 1, puntero2).stream().map(linea -> Contenedor.create(linea)).toList();
		elementos = lineas.subList(puntero2 + 1, lineas.size()).stream().map(linea -> Elemento.create(linea)).toList();
		
	}

}
