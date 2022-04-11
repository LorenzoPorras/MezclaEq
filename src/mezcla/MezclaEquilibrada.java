package mezcla;

import java.io.*;
import java.util.Arrays;

public class MezclaEquilibrada {
	static final int N = 6;
	static final int N2 = N / 2;
	static File f0;
	static File[] f = new File[N];
	static final int Cantidad = 1000;
	static final int TOPE = 1000;

	public static void main(String[] args) throws IOException {
		creacion();
		escribir(f0);
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
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			String linea;
			while ((linea = br.readLine()) != null)
				System.out.print(linea + " ");
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
