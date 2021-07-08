package Nier;

public class AnalisisSemantico {
	
	private Lista<String> tokens;
	private String errores[], nombres[], tipos[], tamaños[], linea[], iguales[], palabra[], errorLinea[];
	private int contErrores, contNombres, pos, repe;

	public AnalisisSemantico(Lista<String> t, String nombres[], String tipos[], String tamaños[], int contNombres) {
		tokens = t;
		contErrores = -1;
		pos = 0;
		iguales = nombres;

		errores = new String[tokens.length()];
		linea = new String[tokens.length()];
		palabra = new String[tokens.length()];
		errorLinea = new String[tokens.length()];

		this.nombres = nombres;
		this.tipos = tipos;
		this.tamaños = tamaños;
		this.contNombres = contNombres;

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
			System.out.print("\n|Posicion |     Nombre      |      Tipo      |      VALOR      |");
			System.out.print("\n----------------------------------------------------------------");
			for (int i = 0; i < contNombres + 1; i++) {
				System.out.printf("\n%-23s %-16s %-18s %-1s", "| " + (i + 1) + "|        " + nombres[i],
						"   |   " + tipos[i], "   |   " + tamaños[i], "   |");
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
		System.out.println();
		ContarErrorLinea();
		for (int i = 0; i < errorLinea.length; i++) {
			if (errorLinea[i] == null) {

			} else {
				System.out.println(errorLinea[i]);
			}

		}
		 
		System.out.println();
		iguales();
		for (int i = 0; i < palabra.length; i++) {
			if (palabra[i] == null) {

			} else {
				System.out.println(palabra[i]);
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
							}
							if (!Aux.getInfo().equals(";")) {
								Aux = Aux.getSig();
								SimboloEspecialPuntoComa(Aux.getInfo());
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
		String v[] = { "int", "boolean", "String" };

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

	private void Buscar(String token) {
		for (int i = 0; i < contNombres + 1; i++) {
			if (token.equals(nombres[i])) {
				pos = i;
			}
		}
	}

	private void iguales() {
		iguales = nombres;
		for (int i = 0; i < nombres.length; i++) {
			repe = 0;

			for (int j = 0; j < nombres.length; j++) {

				if (iguales[i] == null) {
					break;
				}
				if (iguales[i].equals(nombres[j]) && tipos[j] == null) {
					repe++;
				}
			}

			if (repe > 1) {

				if (nombres[i] == null) {
					break;
				} else {
					if (tipos[i]==null) {
					palabra[i] = "se repite " + iguales[i] + " " + repe + " veces, se esta declarando varias veces en la posicion " + (i+1);
					}
				}
			}
			continue;

		}
		for (int i = 0; i < palabra.length; i++) {
			repe = 0;
			for (int j = 0; j < palabra.length; j++) {
				if (palabra[i] == null) {
					break;
				}
				if (palabra[i].equals(palabra[j])) {

					repe++;
					if (repe > 1) {
						palabra[j] = null;

					}
				}
			}
		}
	}

	public void ContarErrorLinea() {
		for (int i = 0; i < errores.length; i++) {
			for (int j = 0; j < errores.length; j++) {
				if (nombres[i] == null) {
					break;
				}
				if (nombres[i].equals(errores[j])) {
					if (tipos[i]==null) {
						errorLinea[i] = "hay un error en la linea: " + (i + 1) + " ,el nombre del error es: "
								+ nombres[i] + " la variable no se ha inicializado";
					} else {

						errorLinea[i] = "hay un error en la linea: " + (i + 1) + " ,el nombre del error es: "
								+ nombres[i] + " la variable es de tipo : " + tipos[i];
					}
				}

			}
		}

	}
}