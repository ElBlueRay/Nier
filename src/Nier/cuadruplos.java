package Nier;

public class cuadruplos <T>{

	private String DK[], var;
	String[] Car;
	private int x, y, z;

	class Nodo {
		String info;
		Nodo izq, der;
	}

	Nodo raiz;

	public cuadruplos() {
		y = 0;
		x = 0;
		z = 0;
		var = "";
		raiz = null;
	}

	public void insertar(String info) {
		Nodo nuevo;
		nuevo = new Nodo();
		nuevo.info = info;
		nuevo.izq = null;
		nuevo.der = null;
		if (raiz == null)
			raiz = nuevo;
		else {
			Nodo anterior = null, reco;
			reco = raiz;
			while (reco != null) {
				anterior = reco;
				if (anterior.der == null) {
					x++;
					reco = reco.izq;
				}
			}

			if (anterior.izq == null) {
				x++;
				anterior.izq = nuevo;
			}
		}
	}

	private void imprimirEntre(Nodo reco) {
		if (reco != null) {
			imprimirEntre(reco.der);
			System.out.print(reco.info + " ");
			Car[y] = reco.info;
			y++;
			imprimirEntre(reco.izq);
		}
	}

	public void imprimirEntre() {
		imprimirEntre(raiz);
		System.out.println();
	}

	private void imprimircuadruplos(Nodo reco) {
		System.out.println("owo");
		if (reco != null) {
			imprimirEntre(reco.izq);
			System.out.print(reco.info + " ");
			imprimirEntre(reco.der);
		}
	}

	public void imprimircuadruplos() {
		imprimircuadruplos(raiz);

		System.out.println();
	}

	public void contarSignos() {

		String izquierda, derecha;

		for (int i = 0; i < Car.length; i++) {
			try {
				if (Car[i].equals("-")) {
					try {
						izquierda = Car[i - 1];
						derecha = Car[i + 1];
						DK[z] = "DK" + (z + 1) + "          - " + "         " + izquierda + "        " + derecha;
					} catch (ArrayIndexOutOfBoundsException e) {
						break;
					}
					z++;
					var = "DK" + (z - 1);
					Car[i] = "DK" + z;
					Car[i + 1] = "DK" + z;
					Car[i - 1] = "DK" + z;
					for (int j = 0; j < Car.length; j++) {
						if (Car[j].contains(var)) {
							Car[j] = "DK" + z;
							;
						}
					}

				}
			} catch (NullPointerException e) {

			}

			for (int a = 0; a < Car.length; a++) {
				try {
					if (Car[a].equals("+")) {
						try {
							izquierda = Car[a - 1];
							derecha = Car[a + 1];
							DK[z] = "DK" + (z + 1) + "          + " + "         " + izquierda + "        " + derecha;
						} catch (ArrayIndexOutOfBoundsException e) {
							break;
						}
						z++;
						var = "DK" + (z - 1);
						Car[a] = "DK" + z;
						Car[a + 1] = "DK" + z;
						Car[a - 1] = "DK" + z;
						for (int j = 0; j < Car.length; j++) {
							if (Car[j].contains(var)) {
								Car[j] = "DK" + z;
								;
							}
						}
					}
				} catch (NullPointerException e) {

				}

				for (int b = 0; b < Car.length; b++) {
					try {
						if (Car[b].equals("/")) {
							try {
								izquierda = Car[b - 1];
								derecha = Car[b + 1];
								DK[z] = "DK" + (z + 1) + "          / " + "         " + izquierda + "        " + derecha;
							} catch (ArrayIndexOutOfBoundsException e) {
								break;
							}
							z++;
							var = "DK" + (z - 1);
							Car[b] = "DK" + z;
							Car[b + 1] = "DK" + z;
							Car[b - 1] = "DK" + z;
							for (int j = 0; j < Car.length; j++) {
								if (Car[j].contains(var)) {
									Car[j] = "DK" + z;
								}
							}
						}
					} catch (NullPointerException e) {
					}
				}
				
				for (int c = 0; c < Car.length; c++) {
					try {
						if (Car[c].equals("*")) {
							try {
								izquierda = Car[c - 1];
								derecha = Car[c + 1];
								DK[z] = "DK" + (z + 1) + "          * " + "         " + izquierda + "        " + derecha;
							} catch (ArrayIndexOutOfBoundsException e) {
								break;
							}

							z++;
							var = "DK" + (z - 1);
							Car[c] = "DK" + z;
							Car[c + 1] = "DK" + z;
							Car[c - 1] = "DK" + z;
							for (int j = 0; j < Car.length; j++) {
								if (Car[j].contains(var)) {
									Car[j] = "DK" + z;
									;
								}
							}
						}
					} catch (NullPointerException e) {

					}
				}
			}
		}
	}

	public void iniciar() {
		DK = new String[x];
		Car = new String[x];
	}

	public void imprime() {
		for (int i = 0; i < DK.length; i++) {
			if (DK[i] == null) {
				System.out.println("A= " + Car[2]);
				break;
			} else {
				System.out.println(DK[i]);
			}

		}
	}
}