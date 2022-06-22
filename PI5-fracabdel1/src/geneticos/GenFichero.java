package geneticos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import datos_ejemplos.datos_ficheros;
import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public class GenFichero  implements ValuesInRangeData<Integer, SolucionFichero>{

	@Override
	public Integer size() {
		// TODO Auto-generated method stub
		return datos_ficheros.getNumFicheros();
	}

	@Override
	public Double fitnessFunction(List<Integer> value) { // Value es el cromosoma
		// TODO Auto-generated method stub
		// Definimos las variables goal y error inicializadas a cero
		double goal = 0.0, error = 0.0;
		// Diccionario clave memoria valor cuanta memoria lleva ocupada
		Map<Integer, Integer> dicc = new HashMap<>();
		
		// Recorremos cada uno de los ficheros
		for(int fichero = 0; fichero < value.size(); fichero++) {
			// Si la memoria del cromosoma es menor que el numero de memorias entramos a las restricciones
			if(value.get(fichero) < datos_ficheros.getNumMemorias()) { // Maximizamos los ficheros
				goal++; // Memoria valida, aumentamos goal en 1 por fichero introducido
				// Restricciones
				// TamFichero tiene que ser menor que MemTamMax()
				error += (datos_ficheros.getTamFichero(fichero) <= datos_ficheros.getTamMax(value.get(fichero)) ? 0.0:100.0); // En caso de que no se cumpla, +1 al error
				
				// Vamos a crear un diccionario, que por cada memoria, vaya guardando cuanta memoria lleva ocupada
				Integer clave = value.get(fichero); // Para simplificar 
				if(dicc.containsKey(value.get(fichero))){// Si el diccionario contiene la memoria
					dicc.put(clave, dicc.get(clave) + datos_ficheros.getTamFichero(clave));
				}else {
					dicc.put(clave, datos_ficheros.getTamFichero(clave));
				}
			}
		}
		
		// Restruccion suma tamaño ficheros < capacidad maxima de la memoria
		Set<Integer> Keys = dicc.keySet();
		for(Integer k: Keys) { // Recorremos cada memoria
			if(datos_ficheros.getTamMemoria(k) < dicc.get(k)) error+= 100.0; // Si capacidadMem < tamaño maximo penalizamos
			//error += (double) Math.abs(datos_ficheros.getTamMemoria(k) - dicc.get(k)); 
		}
		
		
		//Double penalizacion = error * datos_ficheros.getNumFicheros();
		return goal - error;
	}

	@Override
	public SolucionFichero solucion(List<Integer> value) {
		// TODO Auto-generated method stub
		return new SolucionFichero(value);
	}


	@Override
	public ChromosomeType type() {
		// TODO Auto-generated method stub
		return ChromosomeType.Range;
	}

	@Override
	public Integer max(Integer i) {
		// TODO Auto-generated method stub
		return datos_ficheros.getNumMemorias() + 1;
	}

	@Override
	public Integer min(Integer i) {
		// TODO Auto-generated method stub
		return 0;
	}


}
