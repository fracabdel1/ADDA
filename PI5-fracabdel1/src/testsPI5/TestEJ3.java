package testsPI5;

import java.io.IOException;
import java.util.stream.IntStream;

import datos_ejemplos.datos_componentes;
import geneticos.GenFichero;
import geneticos.SolucionComponentes;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;


public class TestEJ3 {
	
	public static void Test3() {
		testPLE();
		testAG();
	}
	
	private static void testPLE() {
			
IntStream.range(1, 4).forEach(i -> {
			
			String ruta_lsi = "lsi_models/ejercicio3.lsi";
			String ruta_gurobi = "gurobi_models/Ejercicio3-" + i + ".lp";
			
			datos_componentes.iniDatos("fichero/PI5Ej3DatosEntrada" + i + ".txt");
			
			try {
				AuxGrammar.generate(datos_componentes.class, ruta_lsi, ruta_gurobi);
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			GurobiSolution gs = GurobiLp.gurobi(ruta_gurobi);
			System.out.println("////////////////////////////////////////////////////////////////");
			//System.out.println(gs.values);
			System.out.println("////////////////////////////////////////////////////////////////");
			//System.out.println(gs.objVal);
			var res = new SolucionComponentes(gs.objVal, gs.values);
			res.toString();
			System.out.println(res);
			});
	}
	
	private static void testAG() {
			
		IntStream.range(1, 4).forEach(i -> {
			AlgoritmoAG.POPULATION_SIZE = 100;
			StoppingConditionFactory.NUM_GENERATIONS = 1000;
			StoppingConditionFactory.FITNESS_MIN = 2000.0;
			
			String file = "fichero/PI5Ej3DatosEntrada" + i + ".txt";
			datos_componentes.iniDatos(file);
			var alg = AlgoritmoAG.of(new GenFichero());
			alg.ejecuta();
			System.out.println(alg.bestSolution());
		});
	}

}
