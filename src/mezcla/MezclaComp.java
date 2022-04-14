package mezcla;

import java.io.*;

class nopar extends Exception {
	public nopar() {
		super("El numero no es par");
	}
}

public class MezclaComp {

	private static int N = 6;
	private static int N2 = N / 2;
	private static File f0;
	private static File[] f = new File[N];

	public static void main(String[] args) {
		Comparable[] a = { new Persona1("Lorenzo", 2), new Persona1("Lorenzo", 1), new Persona1("Lorenzo", 8) };

		try {
			creacion(a);
			escribir(f0);
			Ordenar();

		} catch (nopar | IOException e) {

			e.printStackTrace();
		}

	}

	public static void Ordenar() throws IOException {
		repartir();

		Comparable valores[] = new Comparable[N2];
		PrintWriter salida[] = new PrintWriter[N];

		int t = 0;
		do {
			int escritura = N2;
			for (int i = N2; i < N; i++) {
				FileWriter fichero;

				fichero = new FileWriter(f[i]);
				fichero.close();
				salida[i] = new PrintWriter(f[i].getName());

			}
			boolean[] activos = new boolean[N2];
			BufferedReader[] lecturas = new BufferedReader[N2];
			for (int i = 0; i < N2; i++) {
				FileReader fr = new FileReader(f[i].getName());
				lecturas[i] = new BufferedReader(fr);

			}
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

			while (finDeTramo(activos)) {
				t++;

				
				
				
				
				
				
				int dato = pos(valores, activos);

				salida[escritura].println(valores[dato]);

				Comparable sig = lecturas[dato].readLine();
				valores[dato] = sig;
			}

			for (int i = N2; i < salida.length; i++) {
				salida[i].close();
			}
			for (int i = 0; i < lecturas.length; i++) {
				lecturas[i].close();
			}
			if(verificar(valores))t=t;else; t=0;

		} while (t > 0);

	}
	public static boolean verificar(Comparable[] a) {
		int i=0;while(i<a.length && a[i]!=null)i++;
		if(i<a.length)return true;else return false;
		
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
		while (n < a.length && a[n] == null)
			n++;
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
		Comparable inicial = br.readLine();

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
		}
		for (int i = 0; i < salida.length; i++) {
			salida[i].close();
		}
		br.close();

	}

	public static void creacion(Comparable[] a) throws nopar, IOException {

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
