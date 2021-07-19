package Nier;

import java.io.File;
import java.io.FileReader;
import javax.swing.JOptionPane;

public class main {

	public static void main(String[] args) {

		int c, antC = -1;
		char letra;
		String txt = "ArchTexto 4.txt", token = "";
		cuadruplos<String> tokens = new cuadruplos();
		int x = Integer
				.parseInt(JOptionPane.showInputDialog(null, "semantico, sintactico y lexico: 1 \ncuadruplos: 2"));
		switch (x) {
		case 1:
			new Apl();
			break;
		case 2:

			File ecuacion = new File(txt);

			if (ecuacion.exists()) {
				try {
					FileReader entrada = new FileReader(txt);

					c = entrada.read();
					while (c != -1) {

						letra = (char) c;

						if (32 != c) {

							switch (antC) {

							case 40:
								tokens.insertar(token);
								token = "";
								break;
							case 41:
								tokens.insertar(token);
								token = "";
								break;
							case 61:
								tokens.insertar(token);
								token = "";
								break;
							case 123:
								tokens.insertar(token);
								token = "";
								break;
							case 125:
								tokens.insertar(token);
								token = "";
								break;
							case 59:
								tokens.insertar(token);
								token = "";
								break;
							case 60:
								tokens.insertar(token);
								token = "";
								break;
							case 62:
								tokens.insertar(token);
								token = "";
								break;
							}

							switch (c) {

							case 40:
								if (antC != 32) {
									tokens.insertar(token);
									token = "";
								}
								break;
							case 41:
								if (antC != 32) {
									tokens.insertar(token);
									token = "";
								}
								break;
							case 61:
								if (antC != 32) {
									tokens.insertar(token);
									token = "";
								}
								break;
							case 123:
								if (antC != 32) {
									tokens.insertar(token);
									token = "";
								}
								break;
							case 125:
								if (antC != 32) {
									tokens.insertar(token);
									token = "";
								}
								break;
							case 59:
								if (antC != 32) {
									tokens.insertar(token);
									token = "";
								}
								break;
							case 60:
								if (antC != 32) {
									tokens.insertar(token);
									token = "";
								}
								break;
							case 62:
								if (antC != 32) {
									tokens.insertar(token);
									token = "";
								}
								break;
							}
							token += letra;
						} else {
							tokens.insertar(token);
							token = "";
						}
						antC = c;
						c = entrada.read();
					}
					tokens.insertar(token);

				} catch (Exception e) {
					System.out.println("No lo encontró");
				}
			} else {
				System.out.println("*****El archivo no se encontró*****");
			}

			/*
			 * cuadruplos abo = new cuadruplos(); abo.insertar("F"); abo.insertar("-");
			 * abo.insertar("E"); abo.insertar("/"); abo.insertar("D"); abo.insertar("*");
			 * abo.insertar("C"); abo.insertar("+"); abo.insertar("B");
			 */

			tokens.iniciar();
			System.out.println("operacion original(OG): ");
			tokens.imprimirEntre();

			System.out.println("Impresion cuadruplos: ");
			System.out.println("Resultado Operador Operando1 Operando2  ");
			tokens.contarSignos();
			tokens.imprime();

			break;
		}
	}
}