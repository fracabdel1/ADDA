package ejercicio2;

import java.util.List;

import us.lsi.common.Files2;
import us.lsi.common.List2;

public class datos_contrataciones {
	
	public static record Contrato(String nombre, List<String> cualidades, Double sueldoMin, Integer valorCV, List<String> incompatibilidad) {
		
		private static Contrato create(String linea) {
			String[] apoyo = linea.split(";");
			String nombCual = apoyo[0].trim();
			Double sueldoMin = Double.parseDouble(apoyo[1].trim());
			Integer valorCV = Integer.parseInt(apoyo[2].trim());
			String incom = apoyo[3].trim();
			String[] separador = nombCual.split(":");
			String nombre = separador[0].trim();
			String cual = separador[1].trim();
			// Cualidades de los productos
			List<String> cualidades = List2.empty();
			for(String a: cual.split(",")) {
				cualidades.add(a.trim());
			}
			// Incompatibilidades de los productos
			List<String> incompatibilidad = List2.empty();
			for(String i: incom.split(",")) {
				incompatibilidad.add(i.trim());
			}
			return new Contrato(nombre, cualidades, sueldoMin, valorCV, incompatibilidad);
			
		}
	}
	
	//Listas globales
	public static List<Contrato> contratos = List2.empty();
	public static List<String> cualidadesDeseadas = List2.empty();
	public static Integer presupuestoMax = 0;

	public static void iniDatos(String fichero) {
		contratos.clear();
		cualidadesDeseadas.clear();
		presupuestoMax = 0;
		for(String linea : Files2.linesFromFile(fichero)) {
			// Añadimos cualidades a la lista
			if(linea.startsWith("Cualidades Deseadas")) {
				String[] apoyo = linea.split(":");
				for(String a: apoyo[1].trim().split(",")) {
					cualidadesDeseadas.add(a.trim());
				}
			}else
				// Añadimos el presupuesto máximo
				if(linea.startsWith("Presupuesto Máximo")) {
					String[] apoyo = linea.split(":");
					presupuestoMax = Integer.parseInt(apoyo[1].trim());
				}else {
					// Añadimos los contratos
					Contrato c = Contrato.create(linea);
					contratos.add(c);
				}			
		}
		System.out.println("Cualidades deseadas: " + cualidadesDeseadas);
		System.out.println("Presupuesto máximo: " + presupuestoMax);
		System.out.println(contratos);
	}
	
	public static Integer getValorCV(Integer i) {
		return contratos.get(i).valorCV();
	}
	public static Integer getPresupuestoMax() {
		return presupuestoMax;
	}
	public static Integer getContieneCualidades(Integer i, Integer j) {
		return contratos.get(i).cualidades.contains(cualidadesDeseadas.get(j))?1:0;
	}
	public static Integer getNumContratos() {
		return contratos.size();
	}
	public static Integer getNumCualidades() {
		return cualidadesDeseadas.size();
	}
	public static Boolean sonCompatibles(Integer i, Integer k) {
		return contratos.get(i).incompatibilidad.contains(contratos.get(k).nombre);
	}
	public static Double getSueldoMin(Integer i) {
		return contratos.get(i).sueldoMin;
	}
}
