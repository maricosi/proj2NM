package pt.uc.dei.aor.paj;

import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@SessionScoped
public class Historico implements Serializable {	
	
	private static final long serialVersionUID = 1L;	
	
	private ArrayList<Entrada> entradas = new ArrayList<>();

	
	public void novaEntrada(String expr, String result) {
		entradas.add(new Entrada(expr,result));
	}
	
	public ArrayList<Entrada> getEntradas() {
		return entradas;
	}

	
	
}
