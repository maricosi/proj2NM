package pt.uc.dei.aor.paj;

public class Operador {
	
	private String op;
	private int quant = 0;
	
	public Operador(String op) {
		this.op = op;
	}
	
	public void add(){
		quant++;
	}

	public String getOp() {
		return op;
	}

	public int getQuant() {
		return quant;
	}

		
}
