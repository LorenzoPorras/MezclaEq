package mezcla;

import java.io.*;
import java.util.*;

public class MezclaEquilibrada {
	private static int N = 6;
	private int N2 = N / 2;
	private static File f0;
	private static File[] f = new File[N];
	private static int Cantidad = 10;
	private static int TOPE = 10;

	public static void main(String[] args) throws IOException {
		creacion();
		escribir(f0);
		Ordenar(f0, f);
	}

	public static void Ordenar(File inical, File[] ayudas) {

	}

	public static void repartir() {

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
			Random rand= new Random();
			for (int i = 0; i < Cantidad; i++)
				pw.println(rand.nextInt(TOPE));
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
