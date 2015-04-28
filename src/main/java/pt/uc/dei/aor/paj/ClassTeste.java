package pt.uc.dei.aor.paj;
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
		Estatistica est = new Estatistica();
//		System.out.println(fazCalculo("3 + 3"));
//		est.recolheEstatistica("3 + 2");
		
		String a = "2 + 4 + 5 + 7 + 9 + 5 + 4";
		
		
		est.recolheEstatistica(a);
		
//		for (String string : a1) {
//			est.recolheEstatistica(string);
//		}
		
		
		
		
		
	}
}
