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
	private static int Cantidad = 5;
	private static int TOPE = 11;

	public static void main(String[] args) {
		try {
			creacion();
			escribir(f0);
			Ordenar();
		} catch (nopar e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public static void Ordenar() throws IOException {
		repartir();
		int t = N2;
		int pos = 3;
		int valores[] = new int[N2];
		PrintWriter salida[] = new PrintWriter[N];
		for (int i = N2; i < N; i++) {
			FileWriter fichero;

			fichero = new FileWriter(f[i]);
			fichero.close();
			salida[i] = new PrintWriter(f[i].getName());

		}
		boolean[] activos = new boolean[N2];

		do {
			BufferedReader[] lecturas = new BufferedReader[N2];
			for (int i = 0; i < N2; i++) {
				FileReader fr = new FileReader(f[i].getName());
				lecturas[i] = new BufferedReader(fr);
			}
			for (int i = 0; i < activos.length; i++) {
				activos[i] = true;
			}

			for (int i = 0; i < valores.length; i++) {
				String valor = lecturas[i].readLine();
				if (valor != null)
					valores[i] = Integer.parseInt(valor);
				else {
					
					activos[i] = false;
				}
				
			}

			while (verificar(activos) > 0) {
				int posValor = pos(valores, activos);
				salida[pos].println(valores[posValor]);
				String nuevo = lecturas[posValor].readLine();
				if (nuevo != null)
					valores[posValor] = Integer.parseInt(nuevo);
				if (nuevo == null) {
					activos[posValor] = false;
					valores[posValor] = 0;
				}

			}
// DE AQUI EN ADELANTE HAY QUE CAMBIAR COSAS
			
			
			
			
			
			
			
			
			
			
			
			pos = (pos < N - 1) ? pos + 1 : 0;
			for (int i = 0; i < N2; i++) {
				File a;
				a = f[i];
				f[i] = f[i + N2];
				f[i + N2] = a;
			}
		
			for (int i = 0; i < N2; i++) {
				lecturas[i].close();
			}

			t--;
		} while (t > 1);

		for (int i = N2; i < salida.length; i++) {
			salida[i].close();
		}

	}

	public static int verificar(boolean[] a) {
		int cont = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] == true)
				cont = cont + 1;
		}

		return cont;

	}

	public static int pos(int[] a, boolean[] b) {
		int pos=0;
		int anterior = a[0];
		for (int i = 0; i < a.length; i++) {
			if (anterior > a[i] && b[i] == true) {
				anterior = a[i];
				pos = i;
			}

		}
		return pos;

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

	}

	public static void creacion() throws nopar, IOException {

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
			Random rand = new Random();
			for (int i = 0; i < Cantidad; i++)
				pw.println(rand.nextInt(TOPE));

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
