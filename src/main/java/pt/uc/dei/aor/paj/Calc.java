package pt.uc.dei.aor.paj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.objecthunter.exp4j.*;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

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
	private boolean parentsisAberto; // indica se existe um parentsis aberto
	private boolean graus = false;	// indica se os ângulos introduzidos são em radianos (predefinido) ou em graus
	
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
		case "sen": {
			novoOpAngulo(" sin(");
		}break;
		case "cos": {
			novoOpAngulo(" cos(");
		}break;
		case "tan": {
			novoOpAngulo(" tan(");
		}break;
		case "inverso": {
			inverso();
		}break;
		case "xquad": {
			quadrado();
		}break;
		case "xexpy": {
			novoOperador("^");
		}break;
		case "pi": {
			inserePI("pi");
		}break;
		case "neper": {
			insereE("e");
		}break;
		case "log10": {
			novoLogaritmo(" log10(");
		}break;
		case "log2": {
			novoLogaritmo(" log2(");
		}break;
		case "log": {
			novoLogaritmo(" log(");
		}break;
		case "fact": {
			factorial();
		}break;
		case "cubicrt": {
			novaRaiz(" cbrt(");
		}break;
		case "eexpx": {
			operadorValido = true;
			novoOperador(" e^");
		}break;
		case "dezexpx": {
			operadorValido = true;
			novoOperador(" 10^");
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
			if(res.contains("erros") == false){
				ArrayList<Input> inputs = expressao.getInputs();
				hist.adicionaEntrada(new Entrada(mostrador, res, inputs));
				mostrador = expressao.clear();
				mostrador = expressao.add(new Input("nm", res));
				init();
				operadorValido = true;
			}else {
				mostrador = res;
			}			
		}
	}
	
	private void factorial(){		
		if(mostrador.length() > 0 && expressao.peekLastInput().getTipo().contains("nm")){
			String tmp = expressao.peekLastInput().getConteudo();			
			double val = Double.parseDouble(tmp);
			if(val%1 == 0 && val > 0 && val <= 170){
				mostrador = expressao.add(new Input("op", "!"));
			}			
		}
	}
	
	private void novoLogaritmo(String d){
		operadorValido = true;
		parentsisAberto = true;
		if(existeVirgula == false){
			virgulaValida = true;
		}		
		mostrador = expressao.add(new Input("op", d));
	}
	
	private void insereE(String d){
		operadorValido = true;
		if(existeVirgula == false){
			virgulaValida = true;
		}
		est.recolheInput(new Input("e", d));
		mostrador = expressao.add(new Input("nm", d));
	}
	
	private void inserePI(String d){
		operadorValido = true;
		if(existeVirgula == false){
			virgulaValida = true;
		}
		est.recolheInput(new Input("pi", d));
		mostrador = expressao.add(new Input("nm", d));
	}
	
	private void quadrado(){		
		if(mostrador.length() > 0 && expressao.peekLastInput().getTipo().contains("nm")){
			String tmp = expressao.getLastInput();			
			float val = Float.parseFloat(tmp);
			mostrador = expressao.add(new Input("nm", Double.toString(Math.pow(val, 2))));
			est.recolheInput(new Input("x^2","x^2"));			
		}
	}
	
	private void inverso(){		
		if(mostrador.length() > 0 && expressao.peekLastInput().getTipo().contains("nm")){
			String tmp = expressao.getLastInput();			
			float val = Float.parseFloat(tmp);
			mostrador = expressao.add(new Input("nm", Float.toString(1/val)));
			est.recolheInput(new Input("1/x","1/x"));			
		}
	}
	
	private void novoOpAngulo(String d){
		
		parentsisAberto = true;
		if(existeVirgula == false){
			virgulaValida = true;
		}		
		if(graus)
			d =	d.replace("(", "d(");
		
		mostrador = expressao.add(new Input("op", d));
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

		// definição de novas funções
		
		
		Function cosd = new Function("cosd", 1) {
		    @Override
		    public double apply(double... args) {
		    	return Math.cos(Math.toRadians(args[0]));
		    }
		};
		
		Function sind = new Function("sind", 1) {
		    @Override
		    public double apply(double... args) {
		    	return Math.sin(Math.toRadians(args[0]));
		    }
		};
		Function tand = new Function("tand", 1) {
		    @Override
		    public double apply(double... args) {
		    	return Math.tan(Math.toRadians(args[0]));
		    }
		};
		
		Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {

		    @Override
		    public double apply(double... args) {
		        final int arg = (int) args[0];
		        double result = 1;
		        for (int i = 1; i <= arg; i++) {
		            result *= i;
		        }
		        return result;
		    }
		};
		
		// end
		
		Expression e = new ExpressionBuilder(exp)
		.function(cosd)
		.function(sind)
		.function(tand)
		.operator(factorial)
		.variables("pi", "e")
		.build()
		.setVariable("pi", Math.PI)
		.setVariable("e", Math.E);
		
		if(e.validate().isValid()){
			try {
				res = e.evaluate();			
				if(res%1 != 0)		
					out = Double.toString(res);
				else if (Double.toString(res).endsWith(".0")){
					out = Double.toString(res);
					out = out.substring(0, out.length()-2);					
				} else out = Double.toString(res);
				est.recolheEstatistica(inputs);		
			} catch (Exception e1) {
				out="erros na expressao";			
			}
			
		} else {
			out = "erros na expressao:\n";
			List<String> erros = e.validate().getErrors();
			
			for (String string : erros) {
				out += string + "\n";
				System.out.println(string);
			}
		}						
		return out;
	}
	
	private void init(){
		virgulaValida = false;
		operadorValido = false;
		existeVirgula = false;
		parentsisAberto = false;
	}
	
	public void clearAll(){
		mostrador = expressao.clear();
		init();
		operadorValido = true;
	}
	
	public void clearLast(){
		if(mostrador.length() > 0)
			mostrador=expressao.remove();
	}
	
	public void reUseExp(Entrada ent){
		mostrador = ent.getExp();
		expressao.loadInputs(ent.getInputs());
		operadorValido = true;
	}
	
	public void reUseResult(Entrada ent){
		clearAll();
		mostrador = ent.getRes();
		expressao.add(new Input("nm", ent.getRes()));
		operadorValido = true;
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

	public boolean isGraus() {
		return graus;
	}

	public void setGraus(boolean graus) {
		this.graus = graus;
	}

}
