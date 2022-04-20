package mezcla;

import java.io.*;
import java.util.Arrays;

class nopar extends Exception {
	public nopar() {
		super("El numero no es par");
	}
}

public class MezclaComp {

	private static int N = 6;
	private static int N2 = N / 2;
	private static File f0;
	private static File fRespuesta;
	private static File[] f = new File[N];

	public static void main(String[] args) {

		String[] a = {};
		long timeInicio;
		long timeFin;
		timeInicio = System.currentTimeMillis();

		for (int i = 0; i < 50; i++) {
			a = Arrays.copyOf(a, a.length + 1);
			a[i] = "Xd-" + (int) (1 + 10 * Math.random());
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

//			escribir(f0);
//			System.out.println();
//			escribir(fRespuesta);
			for (int i = 0; i < f.length; i++) {
				f[i].delete();
			}

		} catch (nopar | IOException e) {

			System.out.println(e.getMessage());
		}

	}

	public static void Ordenar() throws IOException {
		repartir();

		String valores[] = new String[N2];
		PrintWriter salida[] = new PrintWriter[N];

		boolean[] activos = new boolean[N2];

		for (int i = N2; i < N; i++) {
			FileWriter fichero;
			fichero = new FileWriter(f[i]);
			fichero.close();

		}

		int escritura = N2;

		while (f0.length() != f[N2].length()) {
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

					int dato = pos(valores, activos);
					if (dato >= 0) {
						String sig = lecturas[dato].readLine();

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

				if (verificarExistencia(valores)) {

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

					if (f0.length() == f[N2].length()) {

						Respuesta();
					}

				}
			}
		}

	}

	public static boolean verificarExistencia(String[] a) {
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

	public static int pos(String[] a, boolean[] b) {

		int pos = -1;
		String anterior = null;
		for (int i = 0; i < a.length; i++) {
			if (a[i] != null) {

				if (anterior != null && anterior.compareTo(a[i]) >= 0 && b[i] == true) {
					anterior = a[i];
					pos = i;

				} else if (anterior == null && b[i] == true) {
					anterior = a[i];
					pos = i;
				}

			}

		}
		return pos;

//		int pos = 0;
//		int n = 0;
//		while (n < a.length && b[n] != true)
//			n++;
//		if (n < a.length) {
//			String anterior = a[n];
//
//			for (int i = 0; i < a.length; i++) {
//
//				if (a[i] != null) {
//					if (anterior.compareTo(a[i]) >= 0 && b[i] == true) {
//						anterior = a[i];
//						pos = i;
//					}
//				}
//
//			}
//			return pos;
//		} else
//			return -1;

	}

	public static void repartir() throws IOException {

		PrintWriter salida[] = new PrintWriter[N];
		for (int i = 0; i < N; i++) {
			FileWriter fichero = new FileWriter(f[i]);
			fichero.close();
			salida[i] = new PrintWriter(f[i].getName());
		}
		FileReader fr = new FileReader(f0);
		BufferedReader br = new BufferedReader(fr);
		String inicial = br.readLine();
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

	}

	public static void Respuesta() throws IOException {

		fRespuesta = new File("Respuesta.txt");
		FileWriter fichero = new FileWriter(fRespuesta);
		fichero.close();
		PrintWriter pw = new PrintWriter(fRespuesta);
		FileReader fr = new FileReader(f[N2]);
		BufferedReader br = new BufferedReader(fr);
		String a;
		while ((a = br.readLine()) != null) {

			pw.println(a);
		}
		pw.close();
		br.close();
	}

	public static void creacion(String[] a) throws nopar, IOException {

		if (N % 2 != 0 && N > 2) {
			throw new nopar();
		} else {

			f0 = new File("ArchivoOrigen.txt");
			for (int i = 0; i < N; i++)
				f[i] = new File("ar" + i + ".txt");
			FileWriter fichero = null;
			PrintWriter pw = null;
			fichero = new FileWriter(f0);
			pw = new PrintWriter(fichero);
			for (int i = 0; i < a.length; i++)
				pw.println(a[i]);
			fichero.close();
		}

	}

	public static void escribir(File f) throws IOException, FileNotFoundException {
		File archivo = f;
		FileReader fr = null;
		BufferedReader br = null;

		fr = new FileReader(archivo);
		br = new BufferedReader(fr);
		String linea;
		while ((linea = br.readLine()) != null) {
			System.out.print(linea + " ");
		}
		br.close();

	}
}
