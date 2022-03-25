import java.util.HashMap;
import java.util.ArrayList;

class Parse{

	Lista toLista(String a){
		boolean modo = false, activo = false, flag = true;
		String inst = "", exp = "";
		int cont = 0, index = 0;
		a += " ";
		ArrayList<Elemento> elems = new ArrayList<Elemento>();
		for(int i = 0 ; i < a.length() ; i++){
			if(modo){
				if(Character.compare(a.charAt(i), "(".charAt(0)) == 0){
					activo = true;
				}
			}
			if(modo && activo){
				if(Character.compare(a.charAt(i), "(".charAt(0)) == 0){
					cont++;
					if(cont == 1){
						index = i;
					}
					activo = true;
				} else if(Character.compare(a.charAt(i), ")".charAt(0)) == 0){
					cont--;
					if(cont == 0){
						elems.add(toLista(a.substring(index+1, i)));
						activo = false;
					}
					
				}
			}
			else if(!Character.isWhitespace(a.charAt(i))){
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
						if(inst.toLowerCase().equals("defun")){
							modo = true;
						}
					
					} else {
						//System.out.println("Token: " + exp);
						elems.add(new Token(exp));
					}
					exp = "";
				}
			}
		
		}
		//System.out.println(new Lista(new Token(inst), elems));
		return new Lista(new Token(inst), elems);
	}

	public String verifyLInst(Lista l, Evaluate eval){
		switch(l.getInst().toLowerCase()){
			case "list":
				if(evaluarLista(l)){
					return "list";
				}
				break;

			
			case "quote":
				return "quote";


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

			case "defun":
				if(verDefun(l)){
					return "defun";
				}
				break;

			default:
				if(eval.getFunciones().containsKey(l.getInst().toLowerCase())){
					return l.getInst().toLowerCase();
				}
				return "aksf";
		}
		return "anf";
	}

	public boolean verSetq(Lista l){
		if(l.getElemAt(0).isToken()){
			if(l.getElemAt(1).isToken()){
				return true;
			} else {
				if(verifyLInst(l.getElemAt(1).toLista(), new Evaluate()).equals("a")){
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
			} else{
				return true;
			}

		}
		System.out.println("????");
		return false;
	}

	public boolean verDefun(Lista l){
		if(l.getElemAt(0).isToken() && l.getElemAt(1).isToken()){
			if(l.getElemAt(1).toString().charAt(0) == '[' && l.getElemAt(1).toString().charAt(l.getElemAt(1).toString().length()-1) == ']'){
				if(l.getElems().size() > 2){
					boolean flag = true;
					for(int i = 2; i<l.getElems().size(); i++){
						if(l.getElemAt(i).isToken()){
							return false;
						}
						return true;
					}
				} else {
					return false;
				}
			}
		} else {
			System.out.println("asfbnakf");
			return false;
		}
		return false;
	}

	public boolean evaluarLista(Lista l){
		if(l.getElemAt(0).isToken()){
			String evaluacionSintaxis = l.getElemAt(0).toString();
			if(evaluacionSintaxis.charAt(0) == '\'' || evaluacionSintaxis.charAt(0) == ' '){
				return true;
			}
		}
		System.out.println("Ha ocurrido un Error");
		return false;
	}
}

