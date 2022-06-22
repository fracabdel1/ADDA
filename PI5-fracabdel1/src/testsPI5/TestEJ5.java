package testsPI5;

import java.util.List;

import datos_ejemplos.datos_ciudad;
import geneticos.GenCiudad;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;

public class TestEJ5 {
	
	/*
	 * Carreteras que dan errores
	 C06,C01,I-21,85.8
	 C12,C05,I-47,148.3
	 C18,C04,I-72,153.2
	 C20,C19,I-80,177.9
	 C21,C11,I-84,142.7
	 C24,C16,I-93,99.8
	 C24,C01,I-95,141.1
	 C24,C14,I-96,80.1
	 
	 */
	
	public static void Test5() {
List<String> ls = List.of("a", "b", "c");
		
		for (int i = 1; i<4; i++) {
			
			String fichero = "fichero/PI5Ej5DatosEntrada" + i + ".txt";
			datos_ciudad.iniDatos(fichero);
			datos_ciudad.predicadosOrigenDestino("fichero/PI5Ej5DatosEntrada.txt", i);
			AlgoritmoAG.ELITISM_RATE  = 0.30;
			AlgoritmoAG.CROSSOVER_RATE = 0.8;
			AlgoritmoAG.MUTATION_RATE = 0.7;
			AlgoritmoAG.POPULATION_SIZE = 50;
			
			StoppingConditionFactory.NUM_GENERATIONS = 10000;
			StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
			StoppingConditionFactory.FITNESS_MIN = 317.7;
			StoppingConditionFactory.stoppingConditionType = StoppingConditionFactory.StoppingConditionType.GenerationCount;
			
			var alg = AlgoritmoAG.of(GenCiudad.of());
			alg.ejecuta();
			
			System.out.println(ls.get(i - 1) + ") " + fichero.replace("ficheros/", "") + ":\n");
			if (i==1) {
				System.out.println("Predicados: Ciudad con más de " + datos_ciudad.habitantes + " hab. y Carretera con más de " + datos_ciudad.kms.intValue() + " kms:\n");
			} else if (i==2) {
				System.out.println("Predicados: Ciudad con máximo " + datos_ciudad.habitantes + " hab. y Carretera con al menos " +  datos_ciudad.kms.intValue() + " kms:\n");
			} else {
				System.out.println("Predicados: Ciudad con más de " + datos_ciudad.habitantes + " hab. y Carretera con menos de " +  datos_ciudad.kms.intValue() + " kms:\n");
			}
			System.out.println(alg.bestSolution());
			System.out.println(alg.getBestChromosome().fitness() + "\n");
		}
	}

}
