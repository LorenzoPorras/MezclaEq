package mezcla;

import java.io.*;
import java.util.*;

class nopar extends Exception {
	public nopar() {
		super("El numero no es par");
	}
}

public class MezclaEquilibrada {
	private static int N = 6;
	private static int N2 = N / 2;
	private static File f0;
	private static File[] f = new File[N];
	private static int Cantidad = 10;
	private static int TOPE = 1000;

	public static void main(String[] args) throws IOException {
		try {
			creacion();
			escribir(f0);
			Ordenar();
		} catch (nopar e) {
			System.out.println(e.getMessage());
		}

	}

	public static void Ordenar() {
		repartir();
	}

	public static void repartir() {
		try {
			PrintWriter salida[] = new PrintWriter[N2];
			for (int i = 0; i < N2; i++) {
				FileWriter fichero = new FileWriter(f[i]);
				fichero.close();
				salida[i] = new PrintWriter(f[i].getName());
			}
			FileReader fr = new FileReader(f0);
			BufferedReader br = new BufferedReader(fr);
			int anterior = -1;
			int pos = 0;
			String a;
			while ((a = br.readLine()) != null) {
				int b = Integer.parseInt(a);
				if (anterior <= b) {
					salida[pos].println(b);
					anterior = b;
				} else {
					pos = (pos < N2 - 1) ? pos + 1 : 0;
					salida[pos].println(b);
					anterior = b;
				}
			}
			for (int i = 0; i < salida.length; i++) {
				salida[i].close();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void creacion() throws nopar {

		if (N % 2 != 0 && N > 2) {
			throw new nopar();
		} else {
			FileWriter fichero = null;
			PrintWriter pw = null;
			f0 = new File("ArchivoOrigen.txt");
			for (int i = 0; i < N; i++)
				f[i] = new File("ar" + i + ".txt");
			try {
				fichero = new FileWriter(f0);
				pw = new PrintWriter(fichero);
				Random rand = new Random();
				for (int i = 0; i < Cantidad; i++)
					pw.println(rand.nextInt(TOPE));
				fichero.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
