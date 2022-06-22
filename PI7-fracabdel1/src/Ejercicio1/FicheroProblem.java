package Ejercicio1;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.List2;

public record FicheroProblem(Integer index, List<Integer> capacidadRestante) {


	public static void datosIniciales(String fichero) {
		
		datos_ficheros.iniDatos(fichero);
		FicheroProblem.capacidadInicial = datos_ficheros.memorias.stream().map(m -> m.capacidad()).toList();
		
	}
	
	public static FicheroProblem of(Integer index, List<Integer> capacidadRestante) {
		return new FicheroProblem(index, capacidadRestante);
	}
	public static FicheroProblem initial() {
		return of(0, FicheroProblem.capacidadInicial);
	}
	public static Predicate<FicheroProblem> goal(){
		return v -> v.index == datos_ficheros.ficheros.size();
	}
	
	public static List<Integer> capacidadInicial;
	
	public List<Integer> actions() {
		
		List<Integer> la = List2.empty();
		
		if (index >= datos_ficheros.getNumFicheros()) {
			return la;
		} 
		
		List<Integer> memorias = IntStream.rangeClosed(0, datos_ficheros.memorias.size())
				.boxed().collect(Collectors.toList());
		
		for (int i: memorias) {
			
			// Metemos en las alternativas aquellas memorias que tengan espacio suficiente
			
			if (i<datos_ficheros.memorias.size()
				&& datos_ficheros.ficheros.get(index).tam() <= datos_ficheros.memorias.get(i).tamMax()
				&& datos_ficheros.ficheros.get(index).tam() <= capacidadRestante.get(i)) {
				
				la.add(i);
				
			// También consideramos la opción de que el archivo no se meta en ninguna memoria
				
			} else if (i == datos_ficheros.memorias.size()) {
				la.add(i);
			}
		}
		return la;
	}

	public FicheroProblem neighbor(Integer a) {
		
		List<Integer> copia = List2.copy(capacidadRestante);
		
		if (a < datos_ficheros.memorias.size()) copia.set(a, copia.get(a) - datos_ficheros.getTamFichero(index));
		
		return of(index + 1, copia);
	}
	
}


