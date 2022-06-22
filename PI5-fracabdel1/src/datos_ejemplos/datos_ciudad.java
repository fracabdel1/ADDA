package datos_ejemplos;

import java.util.List;
import java.util.function.Predicate;

import org.jgrapht.graph.SimpleWeightedGraph;
import us.lsi.common.Files2;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.views.IntegerVertexGraphView;

public class datos_ciudad {
	
	public static SimpleWeightedGraph<Ciudad, Carretera> gf;
	public static Integer n;
	public static Integer habitantes;
	public static Double kms;
	public static IntegerVertexGraphView<Ciudad, Carretera> graph;
	public static Predicate<Ciudad> predicadoCiudad;
	public static Predicate<Carretera> predicadoCarretera;
	public static Ciudad origen;
	public static Ciudad destino;
	
	// Datos ciudades
	
	public static void iniDatos(String fichero) {
		gf = GraphsReader.newGraph(fichero, Ciudad::ofFormat, Carretera::ofFormat, Graphs2::simpleWeightedGraph,Carretera::km);
		graph = IntegerVertexGraphView.of(gf);
		n = graph.vertexSet().size();
	}
	
	public static void predicadosOrigenDestino(String fichero, Integer i) {
		List<String> lineas = Files2.linesFromFile(fichero);
		lineas = lineas.stream().map(s -> s.trim()).toList();
		
		Integer puntero = lineas.indexOf("PI5Ej5DatosEntrada" + i + ".txt:");
		
		if (puntero == 0) {
			
			habitantes = Integer.parseInt(lineas.get(puntero + 1).replace("- Al menos una ciudad intermedia con mas de", "").replace("habitantes", "").trim());
			predicadoCiudad = v -> v.habitantes() > habitantes;
			kms = Double.parseDouble(lineas.get(puntero + 2).replace("- Al menos una carretera con mas de", "").replace("kms", "").trim());
			predicadoCarretera = e -> e.km() > kms;
			
		} else if (puntero == 5) {
			
			habitantes = Integer.parseInt(lineas.get(puntero + 1).replace("- Al menos una ciudad intermedia con máximo", "").replace("habitantes", "").trim());
			predicadoCiudad = v -> v.habitantes() <= habitantes;
			kms = Double.parseDouble(lineas.get(puntero + 2).replace("- Al menos una carretera con al menos", "").replace("kms", "").trim());
			predicadoCarretera = e -> e.km() >= kms;
			
		} else {
			
			habitantes = Integer.parseInt(lineas.get(puntero + 1).replace("- Al menos una ciudad intermedia con mas de", "").replace("habitantes", "").trim());
			predicadoCiudad = v -> v.habitantes() > habitantes;
			kms = Double.parseDouble(lineas.get(puntero + 2).replace("- Al menos una carretera con menos de", "").replace("kms", "").trim());
			predicadoCarretera = e -> e.km() < kms;
			
		}
		
		String[] origenDestino = lineas.get(puntero + 3).split(";");
		origen = gf.vertexSet().stream().filter(c -> c.nombre().equals(origenDestino[0].replace("- Origen:", "").trim())).findFirst().get();
		destino = gf.vertexSet().stream().filter(c -> c.nombre().equals(origenDestino[1].replace("Destino:", "").trim())).findFirst().get();
	}
	
	// Clase Ciudad
	public record Ciudad(String nombre, Integer habitantes) {

		public static Ciudad ofFormat(String[] formato) {
			String nombre = formato[0];
			Double hab = Double.parseDouble(formato[1]);
			Integer habitantes = hab.intValue();
			return new Ciudad(nombre,habitantes);
		}
		public static Ciudad create(String s) {
			String[] v = s.split(",");
			String nombre = v[0].trim();
			Double hab = Double.parseDouble(v[1].trim());
			Integer habitantes = hab.intValue();
			return new Ciudad(nombre, habitantes);
		}
	}
	
	// Clase carreteras
	public record Carretera(Integer id, Double km, String nombre) {

		private static int num =0;
		
		public static Carretera ofFormat(String[] formato) {
			Double km = Double.parseDouble(formato[3]);
			String nomb = formato[2];		
			Integer id = num;
			num++;
			return new Carretera(id, km, nomb);
		}
		
		public static Carretera create(String s) {
			String[] formato = s.split(",");
			Double km = Double.parseDouble(formato[3]);
			String nomb = formato[2];		
			Integer id = num;
			num++;
			return new Carretera(id, km, nomb);
		}
		
		public static Carretera of(Double kms) {
			Double km = kms;
			String nomb = null;		
			Integer id = num;
			num++;
			return new Carretera(id, km, nomb);
		}
	}

}
