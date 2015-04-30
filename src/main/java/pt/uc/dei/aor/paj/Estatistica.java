package pt.uc.dei.aor.paj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class Estatistica implements Serializable{
	
	
	private static final long serialVersionUID = -8382190444479383697L;
		
	private HashMap <String, Operador> operation = new HashMap<>();
	private List<Entry<String, Operador>> entrada;
	
	public Estatistica() {
		operation.put("+", new Operador("+"));
		operation.put("-", new Operador("-"));
		operation.put("/", new Operador("/"));
		operation.put("*", new Operador("*"));
		operation.put("sqrt", new Operador("sqrt"));
		operation.put("+/-", new Operador("+/-"));
		operation.put("%", new Operador("%"));
		operation.put("sin", new Operador("sin"));
		operation.put("cos", new Operador("cos"));
		operation.put("tan", new Operador("tan"));
		operation.put("1/x", new Operador("1/x"));
		operation.put("x^2", new Operador("x^2"));
		operation.put("^", new Operador("^"));
		operation.put("pi", new Operador("pi"));
		operation.put("e", new Operador("e"));
		operation.put("log10", new Operador("log10"));
		operation.put("log2", new Operador("log2"));
		operation.put("log", new Operador("log"));
		operation.put("!", new Operador("!"));
		operation.put("cbrt", new Operador("cbrt"));
		operation.put("e^x", new Operador("e^x"));
		operation.put("10^x", new Operador("10^x"));
			
	}
	
	public void recolheEstatistica(Expressao exp){
		
		ArrayList<Input> inputs = exp.getInputs();
		
		for (Input input : inputs) {
			if(input.getTipo().contains("op")){
				if(input.getConteudo().contains("+"))
					operation.get("+").add();
				else if(input.getConteudo().contains("-"))
					operation.get("-").add();
				else if(input.getConteudo().contains("*"))
					operation.get("*").add();
				else if(input.getConteudo().contains("/"))
					operation.get("/").add();
				else if(input.getConteudo().contains("sqrt"))
					operation.get("sqrt").add();
				else if(input.getConteudo().contains("sin"))
					operation.get("sin").add();
				else if(input.getConteudo().contains("cos"))
					operation.get("cos").add();
				else if(input.getConteudo().contains("tan"))
					operation.get("tan").add();
				else if(input.getConteudo().contains("^"))
					operation.get("^").add();
				else if(input.getConteudo().contains("log10"))
					operation.get("log10").add();
				else if(input.getConteudo().contains("log2"))
					operation.get("log2").add();
				else if(input.getConteudo().contains("log("))
					operation.get("log").add();
				else if(input.getConteudo().contains("!"))
					operation.get("!").add();
				else if(input.getConteudo().contains("cbrt"))
					operation.get("cbrt").add();
				else if(input.getConteudo().contains("e^"))
					operation.get("e^x").add();
				else if(input.getConteudo().contains("10^"))
					operation.get("10^x").add();
			}
		}
		
	}
	
	public void recolheInput(Input in){
		if(in.getTipo().contains("+/-"))
			operation.get("+/-").add();
		else if(in.getTipo().contains("%"))
			operation.get("%").add();
		else if(in.getTipo().contains("1/x"))
			operation.get("1/x").add();
		else if(in.getTipo().contains("x^2"))
			operation.get("x^2").add();
		else if(in.getTipo().contains("nm"))
			operation.get("pi").add();
		else if(in.getTipo().contains("nm"))
			operation.get("e").add();
	}

	public List<Entry<String, Operador>> getEntrada() {
		entrada = new ArrayList<Entry<String, Operador>>(operation.entrySet());
		return entrada;
	}
}
