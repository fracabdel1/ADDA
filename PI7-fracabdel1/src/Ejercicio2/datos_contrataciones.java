package Ejercicio2;

import java.util.List;

import us.lsi.common.Files2;
import us.lsi.common.List2;

public class datos_contrataciones {
	
	public static record Candidato(String id, List<String> cualidades, Double sueldoMin, Integer valoracion, List<String> incompatibilidades) {
		
		public static Candidato create(String s) {
			String[] v = s.split(":");
			String id = v[0].trim();
			
			String[] info = v[1].trim().split(";");
			List<String> cualidades = List2.parse(info[0].trim().split(","), String::new);
			Double sueldoMin = Double.valueOf(info[1].trim());
			Integer valoracion = Integer.valueOf(info[2].trim());
			List<String> incompatibilidades = List2.parse(info[3].trim().split(","), String::new); 
			
			return new Candidato(id, cualidades, sueldoMin, valoracion, incompatibilidades);
		}
		
		public String toString() {
			return String.format("%s: %s; %s; %s; %s", id(), cualidades(), sueldoMin(), valoracion(), incompatibilidades());
		}
	}
	
	public static List<String> cualidadesDeseadas;
	public static Integer presupuesto;
	public static List<Candidato> candidatos;
	
	public static Integer getN() {
		return candidatos.size();
	}
	public static Integer getM() {
		return cualidadesDeseadas.size();
	}
	public static Integer getPresupuesto() {
		return presupuesto;
	}
	public static Integer valorCandidato(Integer i) {
		return candidatos.get(i).valoracion();
	}
	public static Double sueldoMin(Integer i) {
		return candidatos.get(i).sueldoMin();
	}
	public static Integer getValoracion(Integer i) {
		return candidatos.get(i).valoracion();
	}
	
//		Compatibilidad muestra si dos candidatos i, k son compatibles
//		Devuelve true si son incompatibles y false en caso contrario
	
	public static Boolean esIncompatible(Integer i, Integer k) {
		return candidatos.get(i).incompatibilidades().contains(candidatos.get(k).id());
	}
	
//		Devolvemos 1 si la cualidad j está entre las cualidades del candidato i
	
	public static Integer cualidadCubierta(Integer i, Integer j) {
		return candidatos.get(i).cualidades().contains(cualidadesDeseadas.get(j)) ? 1 : 0;
	}
	
	// Datos candidatos
	
	public static void datos(String fichero) {
		
		List<String> lineas = Files2.linesFromFile(fichero);
		
		cualidadesDeseadas = List2.parse(lineas.get(0).replace("Cualidades Deseadas:", "").trim().split(","), String::new);
		presupuesto = Integer.valueOf(lineas.get(1).replace("Presupuesto Máximo:", "").trim());
		candidatos = lineas.subList(lineas.indexOf("Presupuesto Máximo: " + presupuesto) + 1, lineas.size()).stream().map(linea -> Candidato.create(linea)).toList();
		
	}
	
	public static void toConsole() {
		System.out.println("Candidatos:\n");
		
		for(Candidato c: candidatos) {
			System.out.println("- " + c);
		}
	}
	public static void main(String[] args) {
		datos("ficheros/PI5Ej2DatosEntrada1.txt");
		toConsole();
	}
}