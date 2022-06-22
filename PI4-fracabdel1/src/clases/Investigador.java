package clases;

public class Investigador {

	// Creamos el investigador
	public static Investigador of() {
		return new Investigador(0, 0, "");
	}
	
	// Para crear un investigador
	public static Investigador ofFormat(String[] format) {
		return new Investigador(format);
	}
	

	// Constructor de Investigador
	
	// Elementos del investigador	
	private Integer ID;
	private Integer fechaNacimiento;
	private String universidad;
	
	// Constructor autogenerado 
	private Investigador(Integer iD, Integer fechaNacimiento, String universidad) {
		super();
		this.ID = iD;
		this.fechaNacimiento = fechaNacimiento;
		this.universidad = universidad;
	}

	// Constructor autogenerado para el ofFormat
	private Investigador(String[] format) {
		// TODO Auto-generated constructor stub
		super();
		this.ID = Integer.parseInt(format[0]);
		this.fechaNacimiento = Integer.parseInt(format[1]);
		this.universidad = format[2];
		
	}

	// Getters y Setters
	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public Integer getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Integer fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getUniversidad() {
		return universidad;
	}

	public void setUniversidad(String universidad) {
		this.universidad = universidad;
	}
	
	public String getINV() {
		return "INV-" + ID;
	}
	

	// HashCode y Equals

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		result = prime * result + ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		result = prime * result + ((universidad == null) ? 0 : universidad.hashCode());
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
		Investigador other = (Investigador) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		if (fechaNacimiento == null) {
			if (other.fechaNacimiento != null)
				return false;
		} else if (!fechaNacimiento.equals(other.fechaNacimiento))
			return false;
		if (universidad == null) {
			if (other.universidad != null)
				return false;
		} else if (!universidad.equals(other.universidad))
			return false;
		return true;
	}	

	// toString

	@Override
	public String toString() {
		return "Investigador [ID=" + ID + ", fechaNacimiento=" + fechaNacimiento + ", universidad=" + universidad + "]";
	}
	
}