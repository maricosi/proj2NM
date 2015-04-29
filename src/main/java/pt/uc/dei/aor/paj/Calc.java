package pt.uc.dei.aor.paj;


import java.io.Serializable;
import java.util.ArrayList;

import net.objecthunter.exp4j.*;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;



@Named
@SessionScoped
public class Calc implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String mostrador="";
	private String type="";
	
	@Inject
	private Expressao expressao;
	
	@Inject
	private Historico hist;
	
	@Inject 
	private Estatistica est;
	
	
	
	private boolean virgulaValida; // indica se é válido usar a vírgula na expressão
	private boolean operadorValido; // indica se é válido usar um operador na expressão
	private boolean existeVirgula; // indica se existe uma vírgula na última parte numérica da expressão
	private boolean parentsisAberto;
	
	public Calc(){
		init();
	}	
	
	public void read(ActionEvent evento){
		
		
		switch(evento.getComponent().getId()){
		case "num0": {			
			novoDigito("0");	
		}break;
		case "num1": {
			novoDigito("1");
		}break;
		case "num2": {
			novoDigito("2");
		}break;
		case "num3": {
			novoDigito("3");
		}break;
		case "num4": {
			novoDigito("4");
		}break;
		case "num5": {
			novoDigito("5");
		}break;
		case "num6": {
			novoDigito("6");
		}break;
		case "num7": {
			novoDigito("7");
		}break;
		case "num8": {
			novoDigito("8");
		}break;
		case "num9": {
			novoDigito("9");			
		}break;
		case "soma": {
			novoOperador(" + ");		
		}break;
		case "sub": {
			novoOperador(" - ");
		}break;
		case "mult": {
			novoOperador(" * ");
		}break;
		case "div": {
			novoOperador(" / ");
		}break;
		case "raizq": {
			novaRaiz(" sqrt(");
		}break;
		case "abrepar": {
			novoParentsis(" (");
		}break;
		case "fechapar": {
			novoParentsis(") ");
		}break;
		case "maismenos": {
			mudaSinal();
		}break;
		case "percent": {
			percentagem();
		}break;
		case "virg": {
			insereVirgula(".");
		} break;
		case "igual": {
			calcula();			
		} break;
		
		}
		
	}
	
	private void calcula(){
		if(operadorValido && parentsisAberto == false){
			String res = opera(mostrador, expressao);
			ArrayList<Input> inputs = expressao.getInputs();
			hist.adicionaEntrada(new Entrada(mostrador, res, inputs));
	//TODO	mostrador=res;
			mostrador = expressao.clear();
			mostrador = expressao.add(new Input("nm", res));
			init();
			operadorValido = true;
		}
	}
	
	private void percentagem(){		
		if(mostrador.length() > 0 && expressao.peekLastInput().getTipo().contains("nm")){
			String tmp = expressao.getLastInput();			
			float val = Float.parseFloat(tmp);
			mostrador = expressao.add(new Input("nm", Float.toString(val/100f)));
			est.recolheInput(new Input("%","%"));		
			
		}
	}
	
	private void mudaSinal(){		
		if(mostrador.length() > 0 && expressao.peekLastInput().getTipo().contains("nm")){
			String tmp = expressao.getLastInput();			
			if(tmp.startsWith("-")){
				tmp = tmp.substring(1);				
			} else {
				tmp = "-" + tmp;				
			}
			est.recolheInput(new Input("+/-","+/-"));
			mostrador = expressao.add(new Input("nm", tmp));
		}
	}
	
	private void insereVirgula(String v){
		if(virgulaValida && existeVirgula == false){
			virgulaValida = false;
			operadorValido = false;
			existeVirgula = true;
			mostrador = expressao.add(new Input("vg", v));
		} 
	}
	
	private void novoDigito(String d){
		operadorValido = true;
		if(existeVirgula == false){
			virgulaValida = true;
		}
		mostrador = expressao.add(new Input("nm", d));
	}
	
	private void novaRaiz(String d){
		operadorValido = true;
		parentsisAberto = true;
		if(existeVirgula == false){
			virgulaValida = true;
		}
		mostrador = expressao.add(new Input("op", d));
	}
	
	private void novoParentsis(String d){
		operadorValido = true;
		if(parentsisAberto)
			parentsisAberto = false;
		else parentsisAberto = true;
		
		if(existeVirgula == false){
			virgulaValida = true;
		}
		mostrador = expressao.add(new Input("par", d));
	}
	
	private void novoOperador(String op){
		if(operadorValido){
			operadorValido = false;
			virgulaValida = false;
			existeVirgula = false;
			mostrador = expressao.add(new Input("op", op));			
		}
	}
	
	private String opera(String exp, Expressao inputs){
		double res;
		String out;
		
		Expression e = new ExpressionBuilder(exp).build();
		
		try {
			res = e.evaluate();			
			if(res%1 != 0)		
				out = Double.toString(res);
			else {
				out = Integer.toString((int) res);
			}			
	//	TODO	est.recolheEstatistica(exp);
			est.recolheEstatistica(inputs);
	
		} catch (Exception e1) {
			// TODO apagar			
			System.out.println("erro");
			out=e1.getMessage();			
		}				
		return out;
	}
	
	private void init(){
		virgulaValida = false;
		operadorValido = false;
		existeVirgula = false;
	}
	
	public void clearAll(){
		this.mostrador="";
		expressao.clear();
		init();
	}
	
	public void clearLast(){
		if(mostrador.length() > 0)
			mostrador=expressao.remove();
	}
	
	public void reUseExp(Entrada ent){
		mostrador = ent.getExp();
		expressao.loadInputs(ent.getInputs());
	}
	
	public void reUseResult(Entrada ent){
		clearAll();
		mostrador = ent.getRes();
		expressao.add(new Input("nm", ent.getRes()));
	}

	public String getExp() {
		return mostrador;
	}
	
	public void setExp(String exp) {
		this.mostrador = exp;
	}	
	
	public Estatistica getEst() {
		return est;
	}
	
	public Historico getHist() {
		return hist;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
