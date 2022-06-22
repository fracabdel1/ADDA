package clases;

public class LibroRelacion {
	
	// Creamos una nueva arista
	public static LibroRelacion of() {
		return new LibroRelacion("", "");
	}
	
	// Creamos una nueva arista con OfFormat
	public static LibroRelacion ofFormat(String[] format) {
		return new LibroRelacion(format);
	}
	
	// Elementos
	private String origen;
	private String destino;
	
	// Constructor
	public LibroRelacion(String origen, String destino) {
		super();
		this.origen = origen;
		this.destino = destino;
	}
	
	public LibroRelacion(String[] format) {
		super();
		this.origen = format[0];
		this.destino = format[1];
	}

	// Getter y Setter
	public String getOrigen() {
		return origen;
	}
	
	public Libro getOrigenLib() {
		Libro lib = Libro.of();
		lib.setNombre(origen);
		return lib;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}
	
	public Libro getDestinoLib() {
		Libro lib = Libro.of();
		lib.setNombre(destino);
		return lib;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	// HashCode y Equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destino == null) ? 0 : destino.hashCode());
		result = prime * result + ((origen == null) ? 0 : origen.hashCode());
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
		LibroRelacion other = (LibroRelacion) obj;
		if (destino == null) {
			if (other.destino != null)
				return false;
		} else if (!destino.equals(other.destino))
			return false;
		if (origen == null) {
			if (other.origen != null)
				return false;
		} else if (!origen.equals(other.origen))
			return false;
		return true;
	}

	// ToString
	@Override
	public String toString() {
		return "LibroRelacion [origen=" + origen + ", destino=" + destino + "]";
	}
	
}
