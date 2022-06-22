package Ejercicio4;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.List2;

public record ContenedorProblem(Integer index, List<Integer> capRestante) {

	public static List<Integer> capacidadInicial;
	public static List<Integer> completo;
	
	public static void datosIniciales(String fichero) {
		datos_contenedores.datos(fichero);
		ContenedorProblem.capacidadInicial = datos_contenedores.contenedores.stream().map(c -> c.capacidad()).collect(Collectors.toList());
		ContenedorProblem.completo = datos_contenedores.contenedores.stream().map(i -> 0).toList();
	}
	
	public static ContenedorProblem of(Integer index, List<Integer> capRestante) {
		return new ContenedorProblem(index, capRestante);
	}
	
	public static ContenedorProblem initial() {
		return of(0, capacidadInicial);
	}
	
	public static Predicate<ContenedorProblem> goal(){
		return v -> v.index == datos_contenedores.elementos.size();
	}
	
	public static Predicate<ContenedorProblem> constraint() {
		
		return x -> IntStream.range(0, datos_contenedores.contenedores.size()).boxed()
				.allMatch(c -> x.capRestante.get(c) == 0
						|| x.capRestante.get(c) == ContenedorProblem.capacidadInicial.get(c));
	}
	
	public Integer peso() {
		return (int) capRestante.stream().filter(c -> c == 0).count();
	}
	
	public List<Integer> alternativas() {
		
		List<Integer> la = List2.empty();
		
		if (index >= datos_contenedores.getN()) {
			return la;
		}
		
		List<Integer> containers = IntStream.rangeClosed(0, datos_contenedores.contenedores.size()).boxed().collect(Collectors.toList());
		
		for (int i: containers) {
			if (i < datos_contenedores.contenedores.size()
				&& datos_contenedores.elementos.get(index).tamaño() <= capRestante.get(i)
				&& datos_contenedores.elementos.get(index).tipos().contains(datos_contenedores.contenedores.get(i).tipo())) {
				
				la.add(i);
				
			} else if (i == datos_contenedores.contenedores.size()) {
				la.add(i);
			}
		}
		
		return la;
	}

	public ContenedorProblem vecino(Integer a) {
		
		List<Integer> cap2 = List2.copy(capRestante);
		if (a < datos_contenedores.contenedores.size()) cap2.set(a, cap2.get(a) - datos_contenedores.elementos.get(index).tamaño());
		
		return of(index + 1, cap2);
	}

}
