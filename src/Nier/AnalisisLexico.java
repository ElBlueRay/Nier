package Nier;

public class AnalisisLexico {

	private Lista<String> tokens;
	private boolean bandera;
	private String errores[],nombres[];
	private int contErrores,contNombres;

	public AnalisisLexico (Lista<String> t,String nombres []) {
		tokens=t;
		contErrores=-1;
		contNombres=-1;
		bandera=true;
		errores = new String [tokens.length()];
		this.nombres = nombres;
	}



	public void Analizador () {

		System.out.println("\t\t*****ANÁLISIS LÉXICO*****\n\n");
		Nodo <String> Aux;
		Aux=tokens.getFrente();
		while(Aux != null) {


			bandera = true;
			Modificadores(Aux.getInfo());
			PalabrasReservadas(Aux.getInfo());
			Tipo(Aux.getInfo());
			SimboloEspecial(Aux.getInfo());
			OperadoresRelacionales(Aux.getInfo());
			Identificadores(Aux.getInfo());

			Aux=Aux.getSig();
		}

		if(contErrores==-1) {
			System.out.println("*****No ubo errores Léxicos*****");
		}else {
			System.out.println("*****Lista de Errores*****");
			System.out.print("\n|      No.      |          Error          |");
			System.out.print("\n-------------------------------------------");
			for(int i=0;i<contErrores+1;i++) {
				System.out.printf("\n%-12s %-25s %-1s","|   "+(i+1),"   |   "+errores[i],"   |");
			}
			System.out.print("\n-------------------------------------------\n\n");
		}
		
		System.out.println("\n\n*****Tabla de Símbolos*****");
		System.out.print("\n|          Nombre          |      Tipo      |      Tamaño      |");
		System.out.print("\n----------------------------------------------------------------");
		for(int i=0;i<contNombres+1;i++) {
			System.out.printf("\n%-23s %-16s %-18s %-1s","|   "+nombres[i],"   |   "+"**","   |   "+"**","   |");
		}
		System.out.print("\n----------------------------------------------------------------\n\n");
	}

	//	Coponenetes Léxicos
	private void Modificadores (String token) {
		String v[] = {"public","private"};

		for(int i=0;i<v.length;i++) {
			if(token.equals(v[i])) {
				bandera=false;
			}
		}

	}

	private void PalabrasReservadas (String token) {
		String v[] = {"class","if","while"};

		if(bandera) {
			for(int i=0;i<v.length;i++) {
				if(token.equals(v[i])) {
					bandera=false;
				}
			}
		}


	}

	private void Tipo (String token) {
		String v[] = {"int","boolean"};

		if(bandera) {
			for(int i=0;i<v.length;i++) {
				if(token.equals(v[i])) {
					bandera=false;
				}
			}
		}
	}

	private void SimboloEspecial (String token) {
		String v[] = {"("  ,")"   ,"="   ,"{"   ,"}"   ,";"   };

		if(bandera) {
			for(int i=0;i<v.length;i++) {
				if(token.equals(v[i])) {
					bandera=false;
				}
			}
		}
	}

	private void OperadoresRelacionales (String token) {
		String v[] = {"<",">"};

		if(bandera) {
			for(int i=0;i<v.length;i++) {
				if(token.equals(v[i])) {
					bandera=false;
				}
			}
		}
	}

	private void Identificadores (String token) {
		String abcdario = "abcdefghijklmnñopqrstuvwxyz";
		String numeros = "123456789";
		String v[] = {"true","false"};
		int posSe;


		if(!bandera) {
			return;
		}

		posSe=token.indexOf("_");


		if(posSe==-1) {
			for(int i=0;i<v.length;i++) {
				if(token.equals(v[i])) {
					return;
				}
			}
			
			for(int i=0;i<token.length();i++) {
				if(numeros.indexOf(token.getBytes()[i])==-1){
					contErrores++;
					errores[contErrores]=token;
					return;
				}
			}
			return;

		}else {

			if(posSe==0) {
				contErrores++;
				errores[contErrores]=token;
				return;
			}

			for(int i=0;i<posSe;i++) {
				if(abcdario.indexOf(token.getBytes()[i])==-1){
					contErrores++;
					errores[contErrores]=token;
					return;
				}
			}

			for(int i=posSe+1;i<token.length();i++) {
				if(numeros.indexOf(token.getBytes()[i])==-1){
					contErrores++;
					errores[contErrores]=token;
					return;
				}
			}

			if(QuitarRepetidos(token)) {
				return;
			}

			contNombres++;
			nombres[contNombres]=token;
		}

	}
	
	private boolean QuitarRepetidos(String token) {
		
		for(int i=0;i<contNombres+1;i++) {
			if(token.equals(nombres[i])) {
				return true;
			}	
		}
		return false;
	}

	public int getContNombres() {
		return contNombres;
	}
}
