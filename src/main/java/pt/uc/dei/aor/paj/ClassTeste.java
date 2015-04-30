package pt.uc.dei.aor.paj;
import java.util.List;

import net.objecthunter.exp4j.*;
public class ClassTeste {

	public static String fazCalculo (String x) {

		String resultado = "";
		

		Expression e = new ExpressionBuilder(x).build();

		try {

		resultado = String.valueOf(e.evaluate());

		} catch (Exception o) {
		
		}

		return resultado;

		}
	public static void main(String[] args) {
		
		Expression e = new ExpressionBuilder("3 + 45 45").build();
		if (e.validate().isValid() == false){
			List<String> erros = e.validate().getErrors();
			
			for (String string : erros) {
				System.out.println(string);
			}
		}
		System.out.println(e.validate().isValid());
		
		
		
		
		
//		System.out.println(fazCalculo("3 + 3"));
//		est.recolheEstatistica("3 + 2");
		
		
		
		
	
		
//		for (String string : a1) {
//			est.recolheEstatistica(string);
//		}
		
		
		
		
		
	}
}
