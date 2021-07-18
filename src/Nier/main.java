package Nier;

import javax.swing.JOptionPane;

public class main {

	public static void main(String[] args) {

		int x = Integer
				.parseInt(JOptionPane.showInputDialog(null, "semantico, sintactico y lexico: 1 \ncuadruplos: 2"));
		switch (x) {
		case 1:
			new Apl();
			break;
		case 2:

			cuadruplos abo = new cuadruplos();
			abo.insertar("F");
			abo.insertar("-");
			abo.insertar("E");
			abo.insertar("/");
			abo.insertar("D");
			abo.insertar("*");
			abo.insertar("C");
			abo.insertar("+");
			abo.insertar("B");

			abo.iniciar();
			System.out.println("operacion original(OG): ");
			abo.imprimirEntre();
			
			

			System.out.println("Impresion cuadruplos: ");
			System.out.println("Resultado Operador Operando1 Operando2  ");
			abo.contarSignos();
			abo.imprime();

			break;
		}
	}
}
