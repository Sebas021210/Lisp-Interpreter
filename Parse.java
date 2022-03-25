import java.util.HashMap;
import java.util.ArrayList;

class Parse{

	public Lista toLista(String a){
		boolean modo = false, activo = false, flag = true, activo2 = false;
		String inst = "", exp = "";
		int cont = 0, index = 0, cont2 = 0, index2 = 0;
		a += " ";
		ArrayList<Elemento> elems = new ArrayList<Elemento>();
		for(int i = 0; i<a.length(); i++){
			if(Character.compare(a.charAt(i), "[".charAt(0)) == 0){
				activo2=true;
			}
			if(activo2){
				if(Character.compare(a.charAt(i), "[".charAt(0)) == 0){
					index2 = i;
				} else if(Character.compare(a.charAt(i), "]".charAt(0)) == 0){
					System.out.println(a.substring(index2, i+1));
					elems.add(new Token(a.substring(index2, i+1)));
					activo2 = false;
				}
			} else if(flag && Character.compare(a.charAt(i), "(".charAt(0)) == 0){
				cont++;
				if(cont == 1){
					index = i;
				}
				activo = true;
			} else if(flag && Character.compare(a.charAt(i), ")".charAt(0)) == 0){
				cont--;
				if(cont == 0){
					inst = a.substring(index, i+1);
					flag = false;
					activo = false;
				}
			} else if(Character.compare(a.charAt(i), "(".charAt(0)) == 0){
				cont++;
				if(cont == 1){
					index = i;
				}
				activo = true;
			} else if(Character.compare(a.charAt(i), ")".charAt(0)) == 0){
				cont--;
				if(cont == 0){
					System.out.println(a.substring(index+1, i));
					elems.add(toLista(a.substring(index+1, i)));
					activo = false;
				}
			} else if(!activo && !activo2){
				if(!Character.isWhitespace(a.charAt(i))){
					if(Character.compare(a.charAt(i), '"') == 0){
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
		}
		//System.out.println(new Lista(new Token(inst), elems));
		return new Lista(new Token(inst), elems);

	}

	public String verifyLInst(Lista l, Evaluate eval){
		System.out.println(l.getInst().toLowerCase());
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

			case "cond":
				if(verCond(l)){
					return "cond";
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

	public boolean verCond(Lista l){
		if(!l.getElemAt(0).isToken()){
			if(l.getElemAt(0).toLista().getInst().charAt(0) == '(' && l.getElemAt(0).toLista().getInst().charAt(l.getElemAt(0).toLista().getInst().length() - 1) == ')'){
				if(!l.getElemAt(0).toLista().getElemAt(0).isToken()){
					return true;
				}
			}
		}
		return false;
	}
}

