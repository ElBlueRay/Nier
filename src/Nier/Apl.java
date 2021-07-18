package Nier;
import java.io.*;


public class Apl {

	public Apl(){
		int c,antC=-1,pos=0;
		char letra;
		String token = "", txt="ArchTexto 2.txt";
		String nombres[],tipos[],tamaños[];
		Lista<String> tokens = new Lista();

		File ArchTxt = new File (txt);

		if (ArchTxt.exists()) {
			try {
				FileReader entrada = new FileReader(txt);

				c = entrada.read();
				while (c !=-1) {

					letra = (char) c;

					if(32!=c) {

						switch (antC) {

						case 40:
							tokens.InsertarFin(token);
							token ="";
							break;
						case 41:
							tokens.InsertarFin(token);
							token ="";
							break;
						case 61:
							tokens.InsertarFin(token);
							token ="";
							break;
						case 123:
							tokens.InsertarFin(token);
							token ="";
							break;
						case 125:
							tokens.InsertarFin(token);
							token ="";
							break;
						case 59:
							tokens.InsertarFin(token);
							token ="";
							break;
						case 60:
							tokens.InsertarFin(token);
							token ="";
							break;
						case 62:
							tokens.InsertarFin(token);
							token ="";
							break;
						}

						switch (c) {

						case 40:
							if(antC!=32) {
								tokens.InsertarFin(token);
								token ="";
							}
							break;
						case 41:
							if(antC!=32) {
								tokens.InsertarFin(token);
								token ="";
							}
							break;
						case 61:
							if(antC!=32) {
								tokens.InsertarFin(token);
								token ="";
							}
							break;
						case 123:
							if(antC!=32) {
								tokens.InsertarFin(token);
								token ="";
							}
							break;
						case 125:
							if(antC!=32) {
								tokens.InsertarFin(token);
								token ="";
							}
							break;
						case 59:
							if(antC!=32) {
								tokens.InsertarFin(token);
								token ="";
							}
							break;
						case 60:
							if(antC!=32) {
								tokens.InsertarFin(token);
								token ="";
							}
							break;
						case 62:
							if(antC!=32) {
								tokens.InsertarFin(token);
								token ="";
							}
							break;
						}
						token +=letra;
					}else {
						tokens.InsertarFin(token);
						token ="";
					}
					antC=c;
					c = entrada.read();
				}
				tokens.InsertarFin(token);


			} catch (Exception e) {
				System.out.println("No lo encontró");
			}
		} else {
			System.out.println("*****El archivo no se encontró*****");
		}


		System.out.println();
		Nodo <String> Aux;
		Aux=tokens.getFrente();
		while(Aux != null) {
			pos++;
			if(Aux.getInfo().equals("")) {
				tokens.Retirar(pos);
				pos--;
			}
			Aux=Aux.getSig();
		}

		nombres = new String [tokens.length()];
		tipos = new String [tokens.length()];
		tamaños = new String [tokens.length()];
		
		AnalisisLexico Al = new AnalisisLexico(tokens,nombres);

		Al.Analizador();

		AnalisisSintactico As = new AnalisisSintactico (tokens,nombres,tipos,tamaños,Al.getContNombres());

		As.Analizador();
		
		AnalisisSemantico Ase = new AnalisisSemantico(tokens,nombres,tipos,tamaños,Al.getContNombres());
		
		Ase.Analizador();
	}


}


