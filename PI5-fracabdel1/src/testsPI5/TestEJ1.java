package testsPI5;

import java.io.IOException;
import java.util.stream.IntStream;

import datos_ejemplos.datos_ficheros;
import geneticos.GenFichero;
import geneticos.SolucionFichero;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class TestEJ1 {
	
	public static void Test1() {
		TestPLE();
		TestAG();
	}

	private static void TestPLE() {
			
			IntStream.range(1, 4).forEach(i -> {
			
			String ruta_lsi = "lsi_models/ejercicio1.lsi";
			String ruta_gurobi = "gurobi_models/Ejercicio1-" + i + ".lp";
			
			datos_ficheros.iniDatos("fichero/PI5Ej1DatosEntrada" + i + ".txt");
			
			try {
				AuxGrammar.generate(datos_ficheros.class, ruta_lsi, ruta_gurobi);
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			GurobiSolution gs = GurobiLp.gurobi(ruta_gurobi);
			System.out.println("////////////////////////////////////////////////////////////////");
			//System.out.println(gs.values);
			System.out.println("////////////////////////////////////////////////////////////////");
			//System.out.println(gs.objVal);
			var res = new SolucionFichero(gs.objVal, gs.values);
			res.toString();
			System.out.println(res);
			});
				
		}
	
	private static void TestAG() {
		IntStream.range(1, 4).forEach(i -> {
			AlgoritmoAG.POPULATION_SIZE = 100;
			StoppingConditionFactory.NUM_GENERATIONS = 100;
			
			String file = "fichero/PI5Ej1DatosEntrada" + i + ".txt";
			datos_ficheros.iniDatos(file);
			var alg = AlgoritmoAG.of(new GenFichero());
			alg.ejecuta();
			System.out.println(alg.bestSolution());
		});
	}
	
}
