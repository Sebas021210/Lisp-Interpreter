import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

class Evaluate{

	HashMap<String, Integer> vars;
	HashMap<String, ArrayList<Elemento>> funciones;
	HashMap<String, String> param;

	public Evaluate(){
		vars = new HashMap<String, Integer>();
		funciones = new HashMap<String, ArrayList<Elemento>>();
		param = new HashMap<String, String>();
	}

	public void setq(Lista l){
		try{
			int val = Integer.parseInt(l.getElemAt(1).toString());
			vars.put(l.getElemAt(0).toString(), Integer.parseInt(l.getElemAt(1).toString()));
		} catch(Exception e){
			if(l.getElemAt(1).isToken()){
				if(searchVar(l.getElemAt(1).toString()) != null){
					vars.put(l.getElemAt(0).toString(), searchVar(l.getElemAt(1).toString()));
				} else if(searchParam(l.getElemAt(1).toString()) != null){
					vars.put(l.getElemAt(0).toString(), Integer.parseInt(searchParam(l.getElemAt(1).toString())));
				} 
				else {
					System.out.println("??");
				}
			} 
			else {
				//FALTA VERIFICAR SI EXISTE LA FUNCION PERO TODAVIA NO SÉ CREAR FUNCIONES :DDDDD
				vars.put(l.getElemAt(0).toString(), operacionAritmetica(l.getElemAt(1).toLista()));
			}
		}

	}

	public HashMap<String, Integer> getVars(){
		return vars;
	}

	public Integer searchVar(String k){
		if(vars.containsKey(k)){
			return vars.get(k);
		} 
		else {
			return null;
		}
	}

	public String searchParam(String k){
		if(param.containsKey(k)){
			return param.get(k);
		} else {
			return null;
		}
	}

	public int operacionAritmetica(Lista l){
		ArrayList<Integer> operandos = new ArrayList<Integer>();
		int tot = 0;
		for(Elemento e : l.getElems()){
			//System.out.println(e.isToken());
			if(e.isToken()){
				try{
					operandos.add(Integer.parseInt(e.toString()));
				} catch(Exception err){
					if(searchVar(e.toString())!=null){
						operandos.add(searchVar(e.toString()));
					} else if(searchParam(e.toString())!=null){
						try{
							operandos.add(Integer.parseInt(searchParam(e.toString())));
						} catch(Exception err2){
							System.out.println("aiksfaiuf");
						}
						
					}
					
				}
				
			} else{
				operandos.add(operacionAritmetica(e.toLista()));
			}
		}

		switch(l.getInst()){

			case "+":
			for(int i : operandos){
				tot += i;
			}
			break;

			case "-":
			for(int i = 0; i<operandos.size(); i++){
				if(i==0){
					tot+=operandos.get(i);
				} 
				else {
					tot -= operandos.get(i);
				}
			}
			break;

			case "*":
			tot = operandos.get(0);

			for(int i = 1; i<operandos.size(); i++){
				tot *= operandos.get(i);
			}
			break;

			case "/":
			tot = operandos.get(0);

			for(int i = 1; i<operandos.size(); i++){
				tot /= operandos.get(i);
			}

		}
		return tot;
	}

	public String print(Lista l){
		String temp = l.getElemAt(0).toString();
		if(searchVar(temp)!=null){
			return Integer.toString(searchVar(temp));
		} else if(searchParam(temp)!=null){
			return searchParam(temp);
		} else{
			return temp.substring(1, temp.length()-1);
		}
	}

	public String lista(Lista l){
		String evaluacionSintaxis = l.toString();
		evaluacionSintaxis = evaluacionSintaxis.replace("list ", "");
		evaluacionSintaxis = evaluacionSintaxis.replace("\'", "");
		return evaluacionSintaxis;
	}

	public void defun(Lista l){
		ArrayList<Elemento> temp = new ArrayList<Elemento>();
		for(int i = 1; i<l.getElems().size(); i++){
			if(i==1){
				temp.add(l.getElemAt(i));
			} else{
				temp.add(l.getElemAt(i).toLista());
			}
			
		}
		funciones.put(l.getElemAt(0).toString(), temp);
	}

	private void putParam(String k, String elem){
		param.put(k, elem);
	}

	public String execFunc(String k, ArrayList<String> params){

		Evaluate eval = new Evaluate();
		ArrayList<String> funcParams = getParams(new Token(funciones.get(k).get(0).toString()));
		for(int i = 0; i<funcParams.size(); i++){
			try{
				eval.putParam(funcParams.get(i), params.get(i));
			} catch(NullPointerException e){
				System.out.println("xjfjxhxzh");
				return " ";
			}
		}

		ArrayList<Lista> instrucciones = new ArrayList<Lista>();
		for(int i = 1; i<funciones.get(k).size(); i++){
			instrucciones.add(funciones.get(k).get(i).toLista());
		}

		Parse parser = new Parse();
		for(Lista l : instrucciones){
			//System.out.println(l);
			switch(parser.verifyLInst(l, this)){
				case "setq":
					eval.setq(l);
					System.out.println(eval.getVars());
					break;

				case "a":
					System.out.println(l);
					System.out.println(eval.operacionAritmetica(l));
					break;

				case "print":
					System.out.println(eval.print(l));
					break;

				default:
					if(funciones.containsKey(l.getInst())){
						execFunc(l.getInst(), getParams(new Token(l.getElemAt(0).toString())));
					}
			}
		}
		return " ";
	}

	public ArrayList<String> getParams(Token t){
		ArrayList<String> temp = new ArrayList<String>();
		String params = t.toString().substring(1, t.toString().length()-1);
		temp = new ArrayList<>(Arrays.asList(params.split(",")));
		//System.out.println(temp);
		return temp;
	}

	public HashMap<String, ArrayList<Elemento>> getFunciones(){
		return funciones;
	}
}