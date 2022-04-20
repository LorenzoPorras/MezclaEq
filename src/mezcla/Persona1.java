package mezcla;

public class Persona1 implements Comparable<Persona1> {
	private String nombre;
	private int edad;

	public Persona1(String nombre, int edad) {
		super();
		this.nombre = nombre;
		this.edad = edad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	@Override
	public int compareTo(Persona1 o) {

//		 return nombre.compareTo(o.getNombre() );
//		return edad -o.getEdad();

//		if (edad - o.getEdad() == 0)
//			return nombre.compareTo(o.getNombre());
//		else
//
//			return edad - o.getEdad();
		return 0;
	}

	@Override
	public String toString() {
		return nombre + "-" + edad;

	}

}
