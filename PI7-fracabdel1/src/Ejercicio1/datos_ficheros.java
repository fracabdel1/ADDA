package Ejercicio1;

import java.util.List;
import us.lsi.common.Files2;
import us.lsi.common.List2;

public class datos_ficheros {
	
	public static record Fichero(String nombre, Integer tam) { // Al ser un record, nombre y tam son las propiedades 

		public static Fichero create(String linea) {
			String[] v = linea.split(":");
			return new Fichero(v[0].trim().toString(),
					Integer.parseInt(v[1].trim()));
		}	
	}
	
	public static record Memoria(String nombre, Integer capacidad, Integer tamMax) {
		
		public static Memoria create(String linea) {
			String[] v = linea.split(":");
			return new Memoria(v[0].toString(), Integer.parseInt(v[1].trim()), Integer.parseInt(v[2].trim()));
		}
	}
	
	public static List<Fichero> ficheros = List2.empty();
	public static List<Memoria> memorias = List2.empty();
	
	public static void iniDatos(String fichero) { // Igual que PI5
		ficheros.clear();
		memorias.clear();
		for(String linea : Files2.linesFromFile(fichero)) {
			if(!linea.startsWith("//") && linea.startsWith("MEM")) {
				linea = linea.replace("capacidad=", " ");
				linea = linea.replace("tam_max=", " ");
				linea = linea.replace(";", ":");
				linea = linea.trim();
				Memoria m = Memoria.create(linea.trim());
				memorias.add(m);
			}
			if(!linea.startsWith("//") && linea.startsWith("F")) {
				linea.trim();
				Fichero f = Fichero.create(linea);
				ficheros.add(f);
			}
		}
	}
	
	// Propiedades
	public static Integer getNumFicheros() { // Numero de ficheros
		return ficheros.size();
	}
	public static Integer getNumMemorias() { // Numero de memorias
		return memorias.size();
	}
	public static Integer getTamFichero(Integer i) { // Tamaño del fichero
		return ficheros.get(i).tam;
	}
	public static Integer getTamMax(Integer j) { // Tamaño máximo del fichero para una memoria
		return memorias.get(j).tamMax;
	}
	public static Integer getTamMemoria(Integer j){ // Tamaño de la memoria
		return memorias.get(j).capacidad;
	}
	public static List<Integer> capacidadInicial(){ // Lista con el tamaño de cada memoria
		return memorias.stream().map(m -> m.capacidad()).toList();
	}

}
