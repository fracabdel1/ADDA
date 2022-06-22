package clases;

public class Libro {

	public static Libro of() {
		return new Libro("");
	}
	
	public static Libro ofFormat(String[] format) {
		return new Libro(format);
	}
	
	// Elementos del libro
	private String nombre;

	// Constructores
	public Libro(String nombre) {
		super();
		this.nombre = nombre;
	}

	public Libro(String[] format) {
		super();
		this.nombre = format[0];
	}

	// Setters y Getters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	//HashCode y Equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Libro other = (Libro) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	// ToString
	@Override
	public String toString() {
		return "Libro [nombre=" + nombre + "]";
	}
	
}
