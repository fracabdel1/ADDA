package problemas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import us.lsi.common.Matrix;
import us.lsi.iterables.Iterables;

public class Problema2 {

	public static void Ejercicio2(String fichero) {
		
		Matrix<String> matriz = lectura(fichero);
		//matriz.print("Matriz"); // Función para leer matrices
		
		Ej2Recursivo(matriz);
		
	}
	
	// Función de lectura
	
	public static Matrix<String> lectura (String file) {
		Iterator<String> fileIt = Iterables.file(file).iterator(); 
		List<String> datos = new ArrayList<>(); 
		while(fileIt.hasNext()) { 
			String[] linea = fileIt.next().split(" ");
			datos.addAll(Arrays.asList(linea));
		}

		Integer n = (int) Math.sqrt(datos.size()); //Calculamos el tamaño de la matriz cuadrada

		Matrix<String> matriz = Matrix.of(datos.toArray(String[]::new), n, n);//Creamos una matriz de tamaño mxn
		return matriz;

	}
	
	// Función Recursiva
	
	public static void Ej2Recursivo(Matrix<String> matriz) {
		List<List<String>> lista = new ArrayList<>();
		List<List<String>> res = AuxEj2Recursivo(matriz, lista);
		int i = 0;
		for(List<String> e : res) {
			System.out.println(String.format("%d)  %s%s%s%s", i+1, e.get(0), e.get(1), e.get(2), e.get(3)));
			i++;
		}		
	}
	
	public static List<List<String>> AuxEj2Recursivo(Matrix<String> matriz, List<List<String>> lista){
		
		List<String> esquinas = new ArrayList<>();
		
		esquinas = matriz.corners(); // Guerdamos en la lista las esquinas de la matriz entrante
		lista.add(esquinas);	
		
		if(matriz.nc() > 2) { // Si la matriz es de tamaño mayor que 2x2, es decir hasta 4x4 seguimos haciendo vistas			
			AuxEj2Recursivo(matriz.view(0), lista); // Esquina superior izquierda
			AuxEj2Recursivo(matriz.view(1), lista); // Esquina superior derecha
			AuxEj2Recursivo(matriz.view(2), lista); // Esquina inferior izquierda
			AuxEj2Recursivo(matriz.view(3), lista); // Esquina inferior derecha
		}		
		return lista;
	}
	
	
}
