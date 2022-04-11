package mezcla;

import java.io.*;
import java.util.Arrays;

public class MezclaEquilibrada {
	static final int N = 6;
	static final int N2 = N / 2;
	static File f0;
	static File[] f = new File[N];
	static final int Cantidad = 10;
	static final int TOPE = 10;

	public static void main(String[] args) throws IOException {
		creacion();
		for (int i = 0; i < N; i++) {
			FileWriter fichero = new FileWriter(f[i]);
			PrintWriter pw = new PrintWriter(fichero);

			f[i] = new File("ar" + i + ".txt");
		}

	}

	public static void creacion() {
		FileWriter fichero = null;
		PrintWriter pw = null;

		f0 = new File("ArchivoOrigen.txt");
		for (int i = 0; i < N; i++)
			f[i] = new File("ar" + i + ".txt");

		try {
			fichero = new FileWriter(f0);
			pw = new PrintWriter(fichero);

			for (int i = 0; i < Cantidad; i++)
				pw.println((int) (1 + TOPE * Math.random()));

			fichero.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public static void escribir(File f) {

		File archivo = f;
		FileReader fr = null;
		BufferedReader br = null;

		try {
			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			// Lectura del fichero
			String linea;
			while ((linea = br.readLine()) != null)
				System.out.print(linea + " ");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// En el finally cerramos el fichero, para asegurarnos
			// que se cierra tanto si todo va bien como si salta
			// una excepcion.
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
