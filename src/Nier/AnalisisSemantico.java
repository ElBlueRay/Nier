package Nier;

public class AnalisisSemantico {
	/*
	 * Mira w, nombre es el nombre de la variable tipo es el tipo de la variable
	 * vamos a comparar el token con el nombre
	 */
	private Lista<String> tokens;
	private String errores[], nombres[], tipos[], tamaños[];
	private int contErrores, contNombres, pos;
	private int contLinea; 
	private String linea[];
	private String sContL;

	public AnalisisSemantico(Lista<String> t, String nombres[], String tipos[], String tamaños[], int contNombres) {
		tokens = t;
		sContL = "";
		contErrores = -1;
		this.contLinea = contLinea;
		pos = 0;
		this.contNombres = contNombres;
		errores = new String[tokens.length()];
		linea = new String [tokens.length()];
		this.nombres = nombres;
		this.tipos = tipos;
		this.tamaños = tamaños;
	}

	public void Analizador() {
		System.out.println("\t\t*****ANÁLISIS SEMANTICO*****\n\n");

		ClassDeclaration();

		if (contErrores == -1) {
			System.out.println("*****No hubo errores SEMANTICO*****");

			System.out.println("\n\n*****Tabla de Símbolos*****");
			System.out.print("\n|          Nombre          |      Tipo      |      VALOR      |");
			System.out.print("\n----------------------------------------------------------------");
			for (int i = 0; i < contNombres + 1; i++) {
				System.out.printf("\n%-23s %-16s %-18s %-1s", "|  " + "   " + nombres[i], "   |   " + tipos[i],
						"   |   " + tamaños[i], "   |");
			}
			System.out.print("\n----------------------------------------------------------------\n\n");
		} else {

			System.out.println("\n\n*****Tabla de Símbolos*****");
			System.out.print("\n|          Nombre          |      Tipo      |      VALOR      |");
			System.out.print("\n----------------------------------------------------------------");
			for (int i = 0; i < contNombres + 1; i++) {
				System.out.printf("\n%-23s %-16s %-18s %-1s", "|  " + "   " + nombres[i], "   |   " + tipos[i],
						"   |   " + tamaños[i], "   |");
			}
			System.out.print("\n----------------------------------------------------------------\n\n");

			System.out.println("*****Lista de Errores*****");
			System.out.print("\n     | Error |");
			System.out.print("\n-------------------------------------------");
			for (int i = 0; i < contErrores + 1; i++) {
				System.out.printf("\n      |   " + errores[i] + "   |");
			}
			System.out.print("\n-------------------------------------------\n\n");

		}

		System.out.println("\n\n*****Errores encontrados*****");

		for (int i = 0; i < contErrores + 1; i++) {
			
			if (errores[i].equals("=")) {
				System.out.printf("\nHubo un error de signo en la linea " + linea[i] + " el dato es " + errores[i]);
			}
			if (errores[i].equals("(") || errores[i].equals(")")) {
				System.out.printf("\nHubo un error de parentesis en la linea " + linea[i] + " el dato es " + errores[i]);
			}
			if (errores[i].equals("int") || errores[i].equals("boolean")) {
				System.out.printf("\nHubo un error de variable en la linea " + linea[i] + " el dato es " + errores[i]);
			}
			if (errores[i].equals("{") || errores[i].equals("}")) {
				System.out.printf("\nHay parentesis de mas en la linea " + linea[i] + " el dato es " + errores[i]);
			}
		}
	}

	private void ClassDeclaration() {

		Nodo<String> Aux;
		Aux = tokens.getFrente();

		Modificadores(Aux.getInfo());
		Aux = Aux.getSig();
		if (Aux == null) {
			return;
		}
		PalabrasReservadas(Aux.getInfo());
		Aux = Aux.getSig();
		if (Aux == null) {
			return;
		}
		Identificadores(Aux.getInfo());
		Aux = Aux.getSig();
		if (Aux == null) {
			return;
		}

		SimboloEspecial(Aux.getInfo());
		Aux = Aux.getSig();
		if (Aux == null) {
			return;
		}
		Statements(Aux);

	}

	private boolean Modificadores(String token) {
		String v[] = { "public", "private" };

		for (int i = 0; i < v.length; i++) {
			if (token.equals(v[i])) {
				return true;
			}
		}
		return false;
	}

	private void PalabrasReservadas(String token) {
		boolean bandera = true;

		if (token.equals("class")) {
			bandera = false;
		}

		if (bandera) {
			contErrores++;
			errores[contErrores] = token;
		}

	}

	private boolean Identificadores(String token) {
		String abcdario = "abcdefghijklmnñopqrstuvwxyz";
		String numeros = "123456789";
		int posSe;

		posSe = token.indexOf("_");

		if (posSe == -1) {
			contErrores++;
			errores[contErrores] = token;
			return false;

		} else {
			if (posSe == 0) {
				contErrores++;
				errores[contErrores] = token;
				return false;
			}

			for (int i = 0; i < posSe; i++) {
				if (abcdario.indexOf(token.getBytes()[i]) == -1) {
					contErrores++;
					errores[contErrores] = token;
					return false;
				}
			}

			for (int i = posSe + 1; i < token.length(); i++) {
				if (numeros.indexOf(token.getBytes()[i]) == -1) {
					contErrores++;
					errores[contErrores] = token;
					return false;
				}
			}
		}

		return true;
	}

	private void SimboloEspecial(String token) {
		boolean bandera = true;

		if (token.equals("{")) {
			bandera = false;
		}

		if (bandera) {
			contErrores++;
			errores[contErrores] = token;
		}
	}

	private boolean Statements(Nodo<String> Aux) {

		while (Aux != null) {
			if (Modificadores(Aux.getInfo())) {
				String auxTipo = "", auxId;
				boolean bandera = false;

				Aux = Aux.getSig();
				if (Tipo(Aux.getInfo())) {
					bandera = true;
					auxTipo = Aux.getInfo();
				}
				if (!Aux.getInfo().equals(";")) {
					Aux = Aux.getSig();
					Identificadores(Aux.getInfo());
					if (bandera) {
						Buscar(Aux.getInfo());
						tipos[pos] = auxTipo;
						linea[pos] = auxTipo;
						BuscarLineas(Aux.getInfo());
					}
					auxId = Aux.getInfo();

					if (!Aux.getInfo().equals(";")) {
						Aux = Aux.getSig();
						SimboloEspecialIgual(Aux.getInfo());

						if (!Aux.getInfo().equals(";")) {
							Aux = Aux.getSig();
							if (Literals(Aux.getInfo())) {
								Buscar(auxId);
								tamaños[pos] = Aux.getInfo();
								BuscarLineas(auxId);
							//	linea[] = Aux.getInfo();
							}
							if (!Aux.getInfo().equals(";")) {
								Aux = Aux.getSig();
								SimboloEspecialPuntoComa(Aux.getInfo());
							//	contLineas(Aux.getInfo());12
								
								
							}
						}
					}
				}
			} else {
				if (PalabrasReservadasif(Aux.getInfo())) {
					Aux = Aux.getSig();
					SimboloEspecialParenAbie(Aux.getInfo());
					Aux = Aux.getSig();
					Expression(Aux.getInfo());
					Aux = Aux.getSig();
					OperadoresRelacionales(Aux.getInfo());
					Aux = Aux.getSig();
					Expression(Aux.getInfo());
					Aux = Aux.getSig();
					SimboloEspecialParenCerrado(Aux.getInfo());
				} else {
					if (PalabrasReservadaswhile(Aux.getInfo())) {
						Aux = Aux.getSig();
						SimboloEspecialParenAbie(Aux.getInfo());
						Aux = Aux.getSig();
						Expression(Aux.getInfo());
						Aux = Aux.getSig();
						OperadoresRelacionales(Aux.getInfo());
						Aux = Aux.getSig();
						Expression(Aux.getInfo());
						Aux = Aux.getSig();
						SimboloEspecialParenCerrado(Aux.getInfo());
					}
				}
			}
			Aux = Aux.getSig();
		}

		return true;
	}

	private boolean PalabrasReservadasif(String token) {

		if (token.equals("if")) {
			return true;
		}

		return false;
	}

	private boolean PalabrasReservadaswhile(String token) {

		if (token.equals("while")) {
			return true;
		}

		if (token.equals("}")) {
			return false;
		}

		contErrores++;
		errores[contErrores] = token;

		return false;
	}

	private boolean Tipo(String token) {
		String v[] = { "int", "boolean" };

		for (int i = 0; i < v.length; i++) {
			if (token.equals(v[i])) {
				return true;
			}
		}

		contErrores++;
		errores[contErrores] = token;
		return false;
	}

	private void SimboloEspecialIgual(String token) {

		if (token.equals("=")) {
			return;
		}

		contErrores++;
		errores[contErrores] = token;

	}

	private boolean Literals(String token) {
		String numeros = "123456789";

		if (token.equals("true")) {
			return true;
		}

		if (token.equals("false")) {
			return true;
		}

		for (int i = 0; i < token.length(); i++) {
			if (numeros.indexOf(token.getBytes()[i]) == -1) {
				contErrores++;
				errores[contErrores] = token;
				return false;
			}
		}

		return true;

	}
	
  /*	private void contLineas(String token) {
		for(int i=0; i<token.length();i++) {
		if (token.equals(";")) {
			contLinea++;
			sContL = contLinea+"";
			linea[i] = sContL;
			return;
			}
		linea[i] = sContL;
		}
		}
	*/

	private void SimboloEspecialPuntoComa(String token) {
		if (token.equals(";")) {
				return;
			}
		contErrores++;
		errores[contErrores] = token;
	}

	private void SimboloEspecialParenAbie(String token) {

		if (token.equals("(")) {
			return;
		}

		contErrores++;
		errores[contErrores] = token;

	}

	private void SimboloEspecialParenCerrado(String token) {

		if (token.equals(")")) {
			return;
		}

		contErrores++;
		errores[contErrores] = token;

	}

	private void Expression(String token) {

		if (Identificadores2(token)) {
			return;
		} else {
			if (IntegralLiteral(token)) {
				return;
			} else {
				contErrores++;
				errores[contErrores] = token;
			}
		}
	}

	private boolean IntegralLiteral(String token) {
		String numeros = "123456789";

		for (int i = 0; i < token.length(); i++) {
			if (numeros.indexOf(token.getBytes()[i]) == -1) {
				return false;
			}
		}

		return true;
	}

	private boolean OperadoresRelacionales(String token) {
		String v[] = { "<", ">" };

		for (int i = 0; i < v.length; i++) {
			if (token.equals(v[i])) {
				return true;
			}
		}

		contErrores++;
		errores[contErrores] = token;
		return false;
	}

	private boolean Identificadores2(String token) {
		String abcdario = "abcdefghijklmnñopqrstuvwxyz";
		String numeros = "123456789";
		int posSe;

		posSe = token.indexOf("_");

		if (posSe == -1) {
			return false;
		} else {
			if (posSe == 0) {
				return false;
			}

			for (int i = 0; i < posSe; i++) {
				if (abcdario.indexOf(token.getBytes()[i]) == -1) {
					return false;
				}
			}

			for (int i = posSe + 1; i < token.length(); i++) {
				if (numeros.indexOf(token.getBytes()[i]) == -1) {
					return false;
				}
			}
		}
		return true;
	}
	private void BuscarLineas(String token) {
		for (int i = 0; i < contLinea + 1; i++) {
			if (token.equals(linea[i])) {
				pos = i;
			}
		}
	}

	private void Buscar(String token) {
		for (int i = 0; i < contNombres + 1; i++) {
			if (token.equals(nombres[i])) {
				pos = i;
			}
		}
	}

}
