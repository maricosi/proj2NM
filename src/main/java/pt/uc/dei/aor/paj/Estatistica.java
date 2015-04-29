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
			
	}
	
	public void recolheEstatistica(String exp){
			
		String [] op = exp.split("\\ ");
		
		for (String string : op) {
			if (string.contains("+"))
				operation.get("+").add();
			else if (string.contains("-"))
				operation.get("-").add();
			else if (string.contains("/"))
				operation.get("/").add();
			else if (string.contains("*"))
				operation.get("*").add();
			else if (string.contains("sqrt"))
				operation.get("sqrt").add();
		}
		
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
			}
		}
		
	}
	
	public void recolheInput(Input in){
		if(in.getTipo().contains("+/-"))
			operation.get("+/-").add();
		else if(in.getTipo().contains("%"))
			operation.get("%").add();
	}

	public List<Entry<String, Operador>> getEntrada() {
		entrada = new ArrayList<Entry<String, Operador>>(operation.entrySet());
		return entrada;
	}
}
