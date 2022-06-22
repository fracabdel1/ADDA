package ejercicio3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.lsi.common.Files2;

public class datos_componentes {

	public static record Componente(String id, Integer tProd, Integer tElab) {
		private static Componente create(String s) {
			String[] v = s.split(":");
			String id = v[0].trim();

			String[] v2 = v[1].split(";");

			Integer tProd = Integer.parseInt(v2[0].replace("prod=", "").trim());
			Integer tElab = Integer.parseInt(v2[1].replace("elab=", "").trim());

			return new Componente(id, tProd, tElab);
		}

		public String toString() {
			return id();
		}
	}

	public static record Producto(String id, Integer precio, Map<Componente, Integer> numComp, Integer maxUds) {
		private static Producto create(String s) {

			String[] v = s.split("->");
			String id = v[0].trim();

			String[] v1 = v[1].split(";");
			Integer precio = Integer.parseInt(v1[0].replace("precio=", "").trim());

			// Parseamos el numero de componentes que tiene un producto como un Map

			Map<Componente, Integer> numComp = new HashMap<>();
			String[] parseaMap = v1[1].replace("comp=", "").replace("(", "").replace(")", "").split(",");

			for (String comp: parseaMap) {
				String idComp = comp.substring(0, comp.indexOf(":"));
				Componente c = componentes.stream().filter(componente -> componente.id().equals(idComp.trim())).findFirst().get();

				Integer num = Integer.valueOf(comp.substring(comp.indexOf(":") + 1, comp.length()).trim());
				numComp.put(c, num);
			}

			Integer maxUds = Integer.valueOf(v1[2].replace("max_u=", "").trim());

			return new Producto(id, precio, numComp, maxUds);
		}

		public String toString() {
			return id();
		}
	}

	public static List<Componente> componentes;
	public static List<Producto> productos;
	public static int TotalProd;
	public static int TotalManual;

	public static Integer getN() {
		return productos.size();
	}
	public static Integer getM() {
		return componentes.size();
	}
	public static Integer getTotalProd() {
		return TotalProd;
	}
	public static Integer getTotalManual() {
		return TotalManual;
	}
	public static Integer getPrecioVenta(Integer i) {
		return productos.get(i).precio();
	}
	public static Integer getMaxUds(Integer i) {
		return productos.get(i).maxUds();
	}
	public static Integer getUdsCompProd(Integer i, Integer j) {
		return productos.get(i).numComp().get(componentes.get(j));
	}
	public static Boolean tieneComponente(Integer i, Integer j) {
		return productos.get(i).numComp().keySet().contains(componentes.get(j));
	}
	public static Integer getTiempoProd(Integer j) {
		return componentes.get(j).tProd();
	}
	public static Integer getTiempoElab(Integer j) {
		return componentes.get(j).tElab();
	}

	// Este método nos ayuda a conocer cuánto tiempo se tarda en producir un producto i
	// (multiplicando el tiempo que se tarda en hacer un componente j por el numero de
	// componentes que tiene el producto)

	public static Integer getTiempoProdTotalProducto(Integer i, Integer j) {
		return getTiempoProd(j)*getUdsCompProd(i, j);
	}

	public static Integer getTiempoElabTotalProducto(Integer i, Integer j) {
		return getTiempoElab(j)*getUdsCompProd(i, j);
	}
	
	// tiempo que se tardaria en elaborar todos los componentes
	public static Integer getTiempoProdTotalProducto2(Integer i) {

		Integer res = 0;
		for (int j=0; j<componentes.size(); j++) {
			if (tieneComponente(i, j)) {
				res += getTiempoProd(j)*getUdsCompProd(i, j);
			}
		}
		return res;
	}

	public static Integer getTiempoElabTotalProducto2(Integer i) {

		Integer res = 0;
		for (int j=0; j<componentes.size(); j++) {
			if (tieneComponente(i, j)) {
				res += getTiempoElab(j)*getUdsCompProd(i, j); 
			}
		}
		return res;
	}

	// Datos productos

	public static void iniDatos(String fichero) {

		List<String> lineas = Files2.linesFromFile(fichero);

		TotalProd = Integer.valueOf(lineas.get(0).replace("T_prod = ", "").trim());
		TotalManual = Integer.valueOf(lineas.get(1).replace("T_manual = ", "").trim());

		Integer puntero1 = lineas.indexOf("// COMPONENTES");
		Integer puntero2 = lineas.indexOf("// PRODUCTOS");

		componentes = lineas.subList(puntero1 + 1, puntero2).stream().map(linea -> Componente.create(linea)).toList();
		productos = lineas.subList(puntero2 + 1, lineas.size()).stream().map(linea -> Producto.create(linea)).toList();

	}

}
