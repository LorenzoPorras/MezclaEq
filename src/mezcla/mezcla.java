package mezcla;

import java.io.*;
import java.util.Arrays;

public class mezcla {
	static final int N = 6;
	static final int N2 = N / 2;
	static File f0;
	static File[] f = new File[N];
	static final int NumReg = 10;
	static final int TOPE = 10;

	public static void main(String[] a) {
		FileWriter fichero = null;
		PrintWriter pw = null;
		f0 = new File("ArchivoOrigen.txt");
		for (int i = 0; i < N; i++)
			f[i] = new File("ar" + i + ".txt");

		// se genera un archivo secuencialmente de claves enteras
		try {
			fichero = new FileWriter(f0);
			pw = new PrintWriter(fichero);
			for (int i = 0; i < NumReg; i++)
				pw.println((int) (1 + TOPE * Math.random()));
			fichero.close();
			System.out.print("Archivo original ... ");
			escribir(f0);
			mezclaEqMple();
		} catch (IOException e) {
			System.out.println("Error entrada/salida durante proceso" + " de ordenaci�n ");
			e.printStackTrace();
		}
	}

	// m�todo de ordenaci�n
	public static void mezclaEqMple() {
		int i, j, k, k1, t;
		int anterior;
		int[] c = new int[N];
		int[] cd = new int[N];
		int[] r = new int[N2];

		Object[] flujos = new Object[N];
		DataInputStream flujoEntradaActual = null;
		DataOutputStream flujoSalidaActual = null;
		boolean[] actvs = new boolean[N2];

		// distribuci�n inicial de tramos desde archivo origen
		try {
			t = distribuir();
			for (i = 0; i < N; i++)
				c[i] = i;
			// bucle hasta n�mero de tramos == 1: archivo ordenado
			do {
				k1 = (t < N2) ? t : N2;
				for (i = 0; i < k1; i++) {
					flujos[c[i]] = new DataInputStream(new BufferedInputStream(new FileInputStream(f[c[i]])));
					cd[i] = c[i];
				}
				j = N2; // �ndice de archivo de salida
				t = 0;
				for (i = j; i < N; i++)
					flujos[c[i]] = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(f[c[i]])));
				// entrada de una clave de cada flujo
				for (int n = 0; n < k1; n++) {
					flujoEntradaActual = (DataInputStream) flujos[cd[n]];
					r[n] = flujoEntradaActual.readInt();
				}

				while (k1 > 0) {
					t++; // mezcla de otro tramo
					for (i = 0; i < k1; i++)
						actvs[i] = true;
					flujoSalidaActual = (DataOutputStream) flujos[c[j]];
					while (!finDeTramos(actvs, k1)) {
						int n;
						n = minimo(r, actvs, k1);
						flujoEntradaActual = (DataInputStream) flujos[cd[n]];
						flujoSalidaActual.writeInt(r[n]);
						anterior = r[n];
						try {
							r[n] = flujoEntradaActual.readInt();
							if (anterior > r[n]) // fin de tramo
								actvs[n] = false;
						} catch (EOFException eof) {
							k1--;
							flujoEntradaActual.close();
							cd[n] = cd[k1];
							r[n] = r[k1];
							actvs[n] = actvs[k1];
							actvs[k1] = false;// no se accede a posici�n k1
						}
					}

					j = (j < N - 1) ? j + 1 : N2; // siguiente flujo de salida
				}
				for (i = N2; i < N; i++) {
					flujoSalidaActual = (DataOutputStream) flujos[c[i]];
					flujoSalidaActual.close();
				}
				/*
				 * Cambio de finalidad de los flujos: entrada<->salida
				 */
				for (i = 0; i < N2; i++) {
					int a;
					a = c[i];
					c[i] = c[i + N2];
					c[i + N2] = a;
				}
			} while (t > 1);
			System.out.print("Archivo ordenado ... ");
			escribir(f[c[0]]);
		} catch (IOException er) {
			er.printStackTrace();
		}
	}

	// distribuye tramos de flujos de entrada en flujos de salida
	public static int distribuir() throws IOException {
		int anterior, j, nt;
		int clave;

		FileWriter flujo = new FileWriter("cosa");
		PrintWriter[] flujoSalida = new PrintWriter[N2];
		;
		
		File archivo = new File("ArchivoOrigen.txt");
		FileReader fr = new FileReader(archivo);
		BufferedReader br = new BufferedReader(fr);
		for (j = 0; j < N2; j++) {
			flujoSalida[j] = new PrintWriter(f[j]);
		}
		anterior = -TOPE;
		clave = anterior + 1;
		j = 0; // indice del flujo de salida
		nt = 0;
		// bucle termina con la excepci�n fin de fichero
		try {
			while (true) {
				
				String line;
				while((line=br.readLine())!=null) {
					clave = Integer.parseInt(line);
					while (anterior <= clave) {
						flujoSalida[j].write(clave);
						anterior = clave;
						clave = Integer.parseInt(line);
					}

					nt++; // nuevo tramo
					j = (j < N2 - 1) ? j + 1 : 0; // siguiente archivo
					flujoSalida[j].write(clave);
					anterior = clave;
				}
					
				

			}
		} catch (EOFException eof) {
			nt++; // cuenta ultimo tramo
			System.out.println("\n*** N�mero de tramos: " + nt + " ***");
			flujo.close();
			for (j = 0; j < N2; j++)
				flujoSalida[j].close();
			return nt;
		}
	}

//devuelve el �ndice del menor valor del array de claves
	public static int minimo(int[] r, boolean[] activo, int n) {
		int i, indice;
		int m;
		i = indice = 0;
		m = TOPE + 1;
		for (; i < n; i++) {
			if (activo[i] && r[i] < m) {
				m = r[i];
				indice = i;
			}
		}
		return indice;
	}

//devuelve true si no hay tramo activo
	public static boolean finDeTramos(boolean[] activo, int n) {
		boolean s = true;

		for (int k = 0; k < n; k++) {
			if (activo[k])
				s = false;
		}
		return s;
	}

	// escribe las claves del archivo
	public static void escribir(File f) {

		int[] a = new int[0];
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {
			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			archivo = new File(f.getName());
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