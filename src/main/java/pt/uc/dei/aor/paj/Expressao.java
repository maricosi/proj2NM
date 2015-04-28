package pt.uc.dei.aor.paj;

/*
 * 
 * Armazena e processa a expressão contida no visor da calculadora
 * 
 * Somente os seguinte digitos são aceitos:
 * - Números 0 à 9;
 * - Vírgula;
 * - Operadores: + - * /;
 * - Backspace (B maiúsculo): remove o último digito da expressão;
 * - Clear (C maíusculo): limpa a expressão;
 * - Sinal de igual (=): processa a expressão. 
 */

public class Expressao {

	private StringBuilder expressao; // armazena a expressão do visor da
										// calculadora.
	private boolean erro; // saber se existe um erro no visor.

	public Expressao() {
		expressao = new StringBuilder();
		erro = false;
	}

	/**
	 * Recebe um caracter do painel da calculadora para ser adicionado à
	 * expressão.
	 * 
	 * @return o caractere digitado.
	 * @param digito
	 *            é caractare digitado.
	 */

	public void guardaDigito(char digito) {
		limpaErro();

		if (eNumero(digito)) { // número: 0..9
			expressao.append(digito);
		}

		// virgula e último digito é número
		else if (digito == ',' && eNumero(ultimoDigito()) && !existeVirgula()) {
			expressao.append(digito);
		}

		// vírgula e último digito não e número
		else if (digito == ',' && !eNumero(ultimoDigito()) && !existeVirgula()) {
			expressao.append('0').append(',');
		}

		else if (digito == '-' && expressao.length() == 0) {
			expressao.append('-');
		}
		// operador: + - / *
		else if (eOperador(digito) && eNumero(ultimoDigito())) {
			expressao.append(digito);
		}

		// backspace
		else if ((digito == 'B' || digito == 'b') && expressao.length() > 0) {
			expressao.deleteCharAt(expressao.length() - 1);
		}

		// clear
		else if (digito == 'C' || digito == 'c') {
			expressao.delete(0, expressao.length());
		}

		else if (digito == '=') {
			processar();
		}

	}

	/**
	 * @return String = Retorna a expressão em formato de String.
	 */
	public String getExpressao() {
		return expressao.toString();
	}

	/**
	 * @return int = Retorna o tamanho da expressão.
	 * 
	 */
	public int getLength() {
		return expressao.length();
	}

	/**
	 * @return Retorna true (verdadeiro) se o digito é um operador: + - * /
	 * @param digito
	 *            é caractere digitado.
	 */
	private boolean eOperador(char digito) {
		return ((digito == '+') || (digito == '*') || (digito == '-') || (digito == '/'));
	}

	/**
	 * @return boolean = Retorna true (verdadeiro) se digito é um caractere
	 *         entre 0 e 9
	 */
	private boolean eNumero(char digito) {
		return ((digito >= 48) && (digito <= 57));
	}

	/**
	 * 
	 * Verifica se o último digito é um operador e não permite digitar outro
	 * operador. E retorna o último caractere da expressão.
	 * 
	 * @return char = retorna um char com o último caractere digitado.
	 */
	private char ultimoDigito() {
		return ((expressao.length() > 0) ? expressao
				.charAt(expressao.length() - 1) : 0);
	}

	/**
	 * @return boolean true (verdadeiro) se já existe uma vírgula desde o último
	 *         operador.
	 */
	private boolean existeVirgula() {
		char crc; // caracter sendo testado
		boolean existe = false;

		// busca do final para o começo
		for (int i = expressao.length() - 1; i >= 0; i--) {
			crc = expressao.charAt(i);
			if (eOperador(crc)) { // operador encontrado
				break;
			} else if (crc == ',') {
				existe = true;
				break;
			}
		}
		return existe;

	}

	/**
	 * Remove a string erro da expressão
	 */
	private void limpaErro() {
		if (erro) {
			expressao.delete(0, expressao.length());
			erro = false;
		}
	}

	/**
	 * Processa a expressão retornando o resultado. Ordem de precedência dos
	 * operadores: 1.Divisão 2.Multiplicação 3.Adição 4.Subtração
	 * 
	 * Exemplo de expresão: "5+20/2*4-6" Calculadora do windows = 44 Java
	 * System.out.println = 39 O correto é 39! Por causa da precedência dos
	 * operadores.
	 * 
	 */
	private void processar() {
		if (expressao.length() > 0) {
			//insere "0" antes do "-" => "-10+5"  => "0-10+5"
			if(expressao.charAt(0) == '-'){
				expressao.insert(0, "0");
			}
			
			//Ordem de chamada dos métodos (inverso da precedência)
			//subtração => adição => multiplicação => divisão
			double resultado = processaSubtracao(expressao.toString());
			
			//"5/3" => 1.6666
			String exp = String.valueOf(resultado).replaceAll("\\.", ",");
			
			//remove o valor ",0" de inteiros
			if(Math.round(resultado) == resultado){
				exp = exp.substring(0,exp.length()-2);
			}
			
			//é valor infinitor (divisão por zero) ou não é número ("NaN")
			if(Double.isInfinite(resultado) || Double.isNaN(resultado)){
				erro = true;
				exp = "ERRO";
			}
			
			expressao = new StringBuilder(exp);
		}
	}

	/**
	 * Processa expressão contendo operadores + - * /
	 * 
	 * @param exp
	 *            é a expressão a ser calculada.
	 * @return double = Resultado da expressão.
	 */
	private double processaSubtracao(String exp) {
		exp = exp.replaceAll(",", ".");
		String[] arrayExpressao = exp.split("-");
		double resultado = processaSoma(arrayExpressao[0]);
		for (int i = 1; i < arrayExpressao.length; i++) {
			resultado -= processaSoma(arrayExpressao[i]);
		}
		return resultado;
	}

	/**
	 * Processa a expressão contendo os operadores + * /
	 * 
	 * @return double = resultado das operações.
	 * @param exp
	 *            é a expressão a ser calculada.
	 */
	private double processaSoma(String exp) {
		String[] arrayExpressao = exp.split("\\+");
		double resultado = 0;
		for (String str : arrayExpressao) {
			resultado += processaMultiplicacao(str);
		}
		return resultado;
	}

	/**
	 * Processa a operação contendo o operador "*" (multiplicação) e "/"
	 * (divisão)
	 * 
	 * @return double = retorna o resultado da multiplicação.
	 * @param exp
	 *            é a expressão a ser calculada.
	 */
	private double processaMultiplicacao(String exp) {
		String[] arrayExpressao = exp.split("\\*");
		double resultado = 1;
		for (String str : arrayExpressao) {
			resultado *= processaDivisao(str);
		}
		return resultado;
	}

	/**
	 * Processa a operação contendo apenas o operador "/" (divisão)
	 * 
	 * @return double = retorna o resultado da divisão.
	 * 
	 * @param exp
	 *            é a expressão a ser calculada.
	 */
	private double processaDivisao(String exp) {
		String[] arrayExpressao = exp.split("/");
		double resultado = Double.parseDouble(arrayExpressao[0]);
		for (int i = 1; i < arrayExpressao.length; i++) {
			resultado /= Double.parseDouble(arrayExpressao[i]);
		}
		return resultado;
	}


}
