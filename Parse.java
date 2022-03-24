import java.util.HashMap;
import java.util.ArrayList;

class Parse{

	Lista toLista(String a){
		boolean flag = true;
		String inst = "";
		String exp = "";
		a += " ";

		ArrayList<Elemento> elems = new ArrayList<Elemento>();
		for(int i = 0 ; i < a.length() ; i++){
			if(!Character.isWhitespace(a.charAt(i))){
				if(Character.compare(a.charAt(i), "(".charAt(0)) == 0){
					elems.add(toLista(a.substring(i+1, a.lastIndexOf(")"))));
					i = a.lastIndexOf(")")+1;
				} else if(Character.compare(a.charAt(i), '"') == 0){
					elems.add(new Token(a.substring(i, a.lastIndexOf('"')+1)));
					i = a.lastIndexOf('"')+1;
					exp = "";
				} else {
					exp += a.charAt(i);
				}
			} else {
				if(!exp.equals("")){
					if(flag){
						//System.out.println("Instruccion: " + exp);
						inst = exp;
						flag = false;
					
					} else {
						//System.out.println("Token: " + exp);
						elems.add(new Token(exp));
					}
					exp = "";
				}
			}
		
		}
		return new Lista(new Token(inst.toLowerCase()), elems);
	}

	public String verifyLInst(Lista l){
		switch(l.getInst().toLowerCase()){
			case "setq":
				if(verSetq(l)){
					return "setq";
				}
				break;

			case "+":
			case "-":
			case "/":
			case "*":
				return "a";

			case "print":
				if(verPrint(l)){
					return "print";
				}
				break;

			default:
				return "aksf";
		}
		return "anf";
	}

	public boolean verSetq(Lista l){
		if(l.getElemAt(0).isToken()){
			if(l.getElemAt(1).isToken()){
				return true;
			} else {
				if(verifyLInst(l.getElemAt(1).toLista()).equals("a")){
					return true;
				}
				System.out.println("Valor invalido");
				return false;
			}
		}
		System.out.println("Nombre de variable invalido");
		return false;
	}

	public boolean verPrint(Lista l){
		if(l.getElemAt(0).isToken() && l.getElems().size() == 1){
			String temp = l.getElemAt(0).toString();

			if(temp.charAt(0) == '"' && temp.charAt(temp.length()-1) == '"'){
				return true;
			}

		}
		System.out.println("????");
		return false;
		//HAY QUE REVISAR TAMBIEN SI SE PIDE QUE SE IMPRIMA EL OUTPUT DE UNA FUNCION PERO YA SABEMOS QUE ENBECES LA VIDA ES INJUSTA
	}

	public boolean verDefun(Lista l){
		if(l.getElemAt(0).isToken() && l.getElemAt(1).isToken()){
			return false;
		} else {
			System.out.println("Ha ocurrido un Error");
			return false;
		}
	}
}