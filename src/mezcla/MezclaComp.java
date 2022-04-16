package mezcla;

import java.io.*;
import java.util.Arrays;

class nopar extends Exception {
	public nopar() {
		super("El numero no es par");
	}
}

class inicalNulo extends Exception {
	public inicalNulo() {
		super("El vector inicial es nulo");
	}
}

public class MezclaComp {

	private static int N = 6;
	private static int N2 = N / 2;
	private static File f0;
	private static File[] f = new File[N];

	public static void main(String[] args) {

		Comparable[] a = {};
		long timeInicio;
		long timeFin;
		timeInicio = System.currentTimeMillis();

		for (int i = 0; i < 10; i++) {
			a = Arrays.copyOf(a, a.length + 1);
			a[i] = new Persona1("Xd",(int) (1 + 10 * Math.random()));
		}
		timeFin = System.currentTimeMillis();
		System.out.println("Escrito en : " + (timeFin - timeInicio));
		try {

			timeInicio = System.currentTimeMillis();
			creacion(a);
			timeFin = System.currentTimeMillis();
			System.out.println("Repartido en : " + (timeFin - timeInicio));

			timeInicio = System.currentTimeMillis();
			Ordenar();
			timeFin = System.currentTimeMillis();
			System.out.println("Ordenado en : " + (timeFin - timeInicio));

			System.out.println(f0.length());

			System.out.println(f[0].length());
			escribir(f[N2]);
		} catch (nopar | IOException | inicalNulo e) {

			System.out.println(e.getMessage());
		}

	}

	public static void Ordenar() throws IOException {
		int parada = repartir();

		Comparable valores[] = new Comparable[N2];
		PrintWriter salida[] = new PrintWriter[N];

		boolean[] activos = new boolean[N2];

		for (int i = N2; i < N; i++) {
			FileWriter fichero;
			fichero = new FileWriter(f[i]);
			fichero.close();

		}

		int t = 1;
		int escritura = N2;

		while (t > 0) {
			BufferedReader[] lecturas = new BufferedReader[N2];
			for (int i = 0; i < N2; i++) {
				FileReader fr = new FileReader(f[i].getName());
				lecturas[i] = new BufferedReader(fr);

			}
			for (int i = N2; i < N; i++) {

				salida[i] = new PrintWriter(f[i].getName());

			}
			escritura = N2;

			while (escritura < N) {
				if (verificarExistencia(valores) == false) {
					for (int i = 0; i < valores.length; i++) {
						String a = lecturas[i].readLine();
						if (a != null) {
							valores[i] = a;
							activos[i] = true;

						} else {
							valores[i] = a;
							activos[i] = false;
						}
					}
				}

				while (finDeTramo(activos)) {
					t++;

					int dato = pos(valores, activos);
					if (dato >= 0) {
						Comparable sig = lecturas[dato].readLine();

						if (sig == null || valores[dato].compareTo(sig) > 0) {
							salida[escritura].println(valores[dato]);
							valores[dato] = sig;
							activos[dato] = false;

						} else if (valores[dato].compareTo(sig) <= 0) {
							salida[escritura].println(valores[dato]);
							valores[dato] = sig;
						}
					}

				}

				if (t > 0) {

					if (verificarExistencia(valores)) {
						t = t;
						for (int i = 0; i < activos.length; i++) {
							if (valores[i] != null)
								activos[i] = true;
						}
						escritura = (escritura < N - 1) ? escritura + 1 : N2;

					} else if (escritura <= N) {
						for (int i = N2; i < salida.length; i++) {
							salida[i].close();
						}
						for (int i = 0; i < lecturas.length; i++) {
							lecturas[i].close();
						}

						for (int i = 0; i < N2; i++) {
							File a;
							a = f[i];
							f[i] = f[i + N2];
							f[i + N2] = a;

						}
						escritura = N;
						if (f0.length() == f[N2].length())
							t = 0;
					}
				}
			}
		}

	}

	public static boolean verificarExistencia(Comparable[] a) {
		int i = 0;
		while (i < a.length && a[i] == null)
			i++;
		if (i < a.length)
			return true;
		else
			return false;

	}

	public static boolean finDeTramo(boolean[] a) {
		int i = 0;
		while (i < a.length && a[i] == false)
			i++;
		if (i < a.length)
			return true;
		else
			return false;
	}

	public static int pos(Comparable[] a, boolean[] b) {
		int pos = 0;
		int n = 0;
		while (n < a.length && b[n] != true)
			n++;
		if (n < a.length) {
			Comparable anterior = a[n];

			for (int i = 0; i < a.length; i++) {

				if (a[i] != null) {
					if (anterior.compareTo(a[i]) >= 0 && b[i] == true) {
						anterior = a[i];
						pos = i;
					}
				}

			}
			return pos;
		} else
			return -1;

	}

	public static int repartir() throws IOException {

		PrintWriter salida[] = new PrintWriter[N];
		for (int i = 0; i < N; i++) {
			FileWriter fichero = new FileWriter(f[i]);
			fichero.close();
			salida[i] = new PrintWriter(f[i].getName());
		}
		FileReader fr = new FileReader(f0);
		BufferedReader br = new BufferedReader(fr);
		Comparable inicial = br.readLine();
		int cantidad = 0;
		int pos = 0;
		salida[pos].println(inicial);
		String a;
		while ((a = br.readLine()) != null) {

			if (inicial.compareTo(a) <= 0) {
				salida[pos].println(a);
				inicial = a;
			} else {
				pos = (pos < N2 - 1) ? pos + 1 : 0;
				salida[pos].println(a);
				inicial = a;
			}
			cantidad++;
		}
		for (int i = 0; i < salida.length; i++) {
			salida[i].close();
		}
		br.close();
		return cantidad;
	}

	public static void creacion(Comparable[] a) throws nopar, IOException, inicalNulo {
		if (a.length == 0 || a == null)
			throw new inicalNulo();
		if (N % 2 != 0 && N > 2) {
			throw new nopar();
		} else {
			FileWriter fichero = null;
			PrintWriter pw = null;
			f0 = new File("ArchivoOrigen.txt");
			for (int i = 0; i < N; i++)
				f[i] = new File("ar" + i + ".txt");

			fichero = new FileWriter(f0);
			pw = new PrintWriter(fichero);

			for (int i = 0; i < a.length; i++)
				pw.println(a[i]);

			fichero.close();

		}

	}

	public static void escribir(File f) {
		File archivo = f;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			String linea;
			while ((linea = br.readLine()) != null) {
				System.out.print(linea + " ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

}
