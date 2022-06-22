package clases;

public class Asiento {
	
	public static Asiento of() {
		return new Asiento("");
	}
	
	public static Asiento ofFormat(String[] format) {
		return new Asiento(format);
	}
	
	// Elementos del Asiento
	private Integer fila;
	private Integer columna;
	private boolean covid;
	
	// Constructores
	public Asiento (boolean covid, Integer filas, Integer columnas) {
		super();
		this.covid = covid;
		this.fila = filas;
		this.columna = columnas;
	}
	
	private Asiento (String[] format) {
		super();
		this.covid = format[0].contains("+") ? true : false;
		this.fila = Integer.valueOf(format[1]);
		this.columna = Integer.valueOf(format[2]);
		
	}

	public Asiento(String string) {
		
	}

	// Getter y Setter
	public Integer getFila() {
		return fila;
	}

	public void setFila(Integer fila) {
		this.fila = fila;
	}

	public Integer getColumna() {
		return columna;
	}

	public void setColumna(Integer columna) {
		this.columna = columna;
	}

	public boolean isCovid() {
		return covid;
	}

	public void setCovid(boolean covid) {
		this.covid = covid;
	}

	// Metodo Hashcode y Equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((columna == null) ? 0 : columna.hashCode());
		result = prime * result + (covid ? 1231 : 1237);
		result = prime * result + ((fila == null) ? 0 : fila.hashCode());
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
		Asiento other = (Asiento) obj;
		if (columna == null) {
			if (other.columna != null)
				return false;
		} else if (!columna.equals(other.columna))
			return false;
		if (covid != other.covid)
			return false;
		if (fila == null) {
			if (other.fila != null)
				return false;
		} else if (!fila.equals(other.fila))
			return false;
		return true;
	}

	// ToString
	@Override
	public String toString() {
		return "Asiento [fila=" + fila + ", columna=" + columna + ", covid=" + covid + "]";
	}
	
}
