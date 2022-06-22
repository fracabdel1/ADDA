package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import problemas.Problema1;
import problemas.Problema2;
import problemas.Problema3;
import problemas.Problema4;
import us.lsi.common.Files2;
import us.lsi.iterables.Iterables;
import us.lsi.math.Math2;
import us.lsi.mochila.datos.ObjetoMochila;
import us.lsi.streams.Stream2;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("####################### \nEjercicio 1 \n#######################\n");			
		Problema1.Ejercicio1("Entradas/PI1E1_DatosEntrada.txt");
		
		System.out.println("\n####################### \nEjercicio 2 \nDatos de entrada 1 \n#######################\n");
		Problema2.problema2("Entradas/PI1E2_DatosEntrada1.txt");
		
		System.out.println("\n####################### \nEjercicio 2 \nDatos de entrada 2 \n#######################\n");
		Problema2.problema2("Entradas/PI1E2_DatosEntrada2.txt");

		System.out.println("\n####################### \nEjercicio 3 \n#######################\n");		
		Problema3.problema3("Entradas/PI1E3_DatosEntrada.txt");
		
		System.out.println("\n####################### \nEjercicio 4 \n#######################\n");		
		Problema4.problema4("Entradas/PI1E4_DatosEntrada.txt");	
			
	}
}
