package problemas;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.math.Math2;

public class Problema1 {
	
	// Ejercicio 1
	
	public static void Ejercicio1 (String file) {
		List<List<Integer>> Entrada = Lectura(file);
		
		// ⋀ conjunción lógica (and)
		// ⋁ disyunción lógica (or)
		
		for(List<Integer> e: Entrada) {
			System.out.println("\n(a, b, c) = " + e);
			System.out.println("Sol. Iterativa: " + Ej1Iterativo(e));
			System.out.println("Sol. Rec. Final: " + Ej1RecursivoFinal(e));
			System.out.println("Sol. Res. No Final: " + Ej1RecursivoNoFinal(e));
			
		}
	}
	
	// Función de lectura
	
	public static List<List<Integer>> Lectura (String File){
		
		Function<String, List<Integer>> separar = line -> // Función para separar elementos
				Arrays.stream(line.split(","))
				.map(e -> Integer.parseInt(e))
				.collect(Collectors.toList());
		
		List<List<Integer>> entrada = Files2.streamFromFile(File) // Función Stream
				.map(separar)
				.collect(Collectors.toList());
		
		return entrada;
	}
	
	// Iterativo (While)
	
	public static String Ej1Iterativo(List<Integer> entrada) {
		Integer a = entrada.get(0);
		Integer b = entrada.get(1);
		Integer c = entrada.get(2);
		String cadena = "";
		
		while(!((a<3 || b<3 || c<3) || (a<5 && b<5 && c<5))) { // 2 primeras condiciones no se cumplan
			if(Math2.esPar(a) && Math2.esPar(b) && Math2.esPar(c)) { // 3º condición
				cadena = String.format("%s%s", cadena, Integer.toString(a*b*c));
				a = a/2;
				b = b-2;
				c = c/2;
			}else {
				cadena = String.format("%s%s", cadena, Integer.toString(a+b+c));
				a = a/3;
				b = b-3;
				c = c/3;
			}
		}
		// Fuera del while
		if(a<3 && b<3 && c<3) {
			cadena = String.format("%s(%s)", cadena, Integer.toString(a*b*c));
		}else {
			cadena = String.format("%s(%s)", cadena, Integer.toString(a+b+c));
		}
		return cadena;
	}
	
	// Recursivo final
	
	public static String Ej1RecursivoFinal(List<Integer> entrada) {
		Integer a = entrada.get(0);
		Integer b = entrada.get(1);
		Integer c = entrada.get(2);
		String cadena = "";
		String res = AuxEj1RecursivoFinal(a, b, c, cadena);
		return res;
	}
	
	public static String AuxEj1RecursivoFinal(Integer a, Integer b, Integer c, String cadena) {
		if(!((a<3 || b<3 || c<3) || (a<5 && b<5 && c<5))) {
			if(Math2.esPar(a) && Math2.esPar(b) && Math2.esPar(c)) {
				cadena = String.format("%s%s", cadena, Integer.toString(a*b*c));
				return AuxEj1RecursivoFinal(a/2, b-2, c/2, cadena);
			}else {
				cadena = String.format("%s%s", cadena, Integer.toString(a+b+c));
				return AuxEj1RecursivoFinal(a/3, b-3, c/3, cadena);
			}
		}
		if(a<3 && b<3 && c<3) {
			cadena = String.format("%s(%s)", cadena, Integer.toString(a*b*c));
		}else {
			cadena = String.format("%s(%s)", cadena, Integer.toString(a+b+c));
		}
		return cadena;
	}
	
	// Recursivo no final
	
	public static String Ej1RecursivoNoFinal(List<Integer> entrada) {
		Integer a = entrada.get(0);
		Integer b = entrada.get(1);
		Integer c = entrada.get(2);
		String cadena = "";

		String res = AuxEj1RecursivoNoFinal(a, b, c, cadena);
		return res;
	}
	
	public static String AuxEj1RecursivoNoFinal(Integer a, Integer b, Integer c, String cadena) {
		String res = "";
		if(!((a<3 || b<3 || c<3) || (a<5 && b<5 && c<5))) {
			if(Math2.esPar(a) && Math2.esPar(b) && Math2.esPar(c)) {
				res = String.format("%s%s",cadena, AuxEj1RecursivoNoFinal(a/2, b-2, c/2, Integer.toString(a*b*c)));
			}else {
				res = String.format("%s%s",cadena, AuxEj1RecursivoNoFinal(a/3, b-3, c/3, Integer.toString(a+b+c)));
			}
		}
		if(res == "") {
			if(a<3 && b<3 && c<3) {
				res = String.format("%s(%s)", cadena, Integer.toString(a*b*c));
			}else {
				res = String.format("%s(%s)", cadena, Integer.toString(a+b+c));
			}
		}
		return res;
	}

}
