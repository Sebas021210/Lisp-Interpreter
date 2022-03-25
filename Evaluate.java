import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

class Evaluate{

	HashMap<String, Float> vars;
	HashMap<String, ArrayList<Elemento>> funciones;
	HashMap<String, String> param;

	public Evaluate(){
		vars = new HashMap<String, Float>();
		funciones = new HashMap<String, ArrayList<Elemento>>();
		param = new HashMap<String, String>();
	}

	public void setq(Lista l){
		try{
			int val = Integer.parseInt(l.getElemAt(1).toString());
			vars.put(l.getElemAt(0).toString(), Float.parseFloat(l.getElemAt(1).toString()));
		} catch(Exception e){
			if(l.getElemAt(1).isToken()){
				if(searchVar(l.getElemAt(1).toString()) != null){
					vars.put(l.getElemAt(0).toString(), searchVar(l.getElemAt(1).toString()));
				} else if(searchParam(l.getElemAt(1).toString()) != null){
					vars.put(l.getElemAt(0).toString(), Float.parseFloat(searchParam(l.getElemAt(1).toString())));
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

	public HashMap<String, Float> getVars(){
		return vars;
	}

	public Float searchVar(String k){
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

	public float operacionAritmetica(Lista l){
		ArrayList<Float> operandos = new ArrayList<Float>();
		float tot = 0;
		for(Elemento e : l.getElems()){
			if(e.isToken()){
				try{
					operandos.add(Float.parseFloat(e.toString()));
				} catch(Exception err){
					if(searchVar(e.toString())!=null){
						operandos.add(searchVar(e.toString()).floatValue());
					} else if(searchParam(e.toString())!=null){
						try{
							operandos.add(Float.parseFloat(searchParam(e.toString())));
						} catch(Exception err2){
							System.out.println("aiksfaiuf");
						}
						
					}
					
				}
				
			} else{
				Float aaaa = operacionAritmetica(e.toLista());
				operandos.add(aaaa.floatValue());
			}
		}

		switch(l.getInst()){

			case "+":
			for(float i : operandos){
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
			return Float.toString(searchVar(temp));
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

		Parse parser = new Parse();
		Evaluate eval = new Evaluate();
		ArrayList<String> funcParams = getParams(new Token(funciones.get(k).get(0).toString()));

		for(int i = 0; i<funcParams.size(); i++){
			try{
				//System.out.println(params.get(i));
				if(params.get(i).charAt(0) == '(' && params.get(i).charAt(params.get(i).length()-1) == ')'){
					//System.out.println(params.get(i).substring(1, params.get(i).length()-1));
					Lista b = parser.toLista(params.get(i).substring(1, params.get(i).length()-1));
					if(parser.verifyLInst(b, this).equals("a")){
						eval.putParam(funcParams.get(i), Float.toString(operacionAritmetica(b)));
					} else if(parser.verifyLInst(b, this).equals(b.getInst().toLowerCase())){
						eval.putParam(funcParams.get(i), execFunc(b.getInst(), getParams(new Token(b.getElemAt(0).toString()))));
					}
				} else{
					//System.out.println("aaa");
					eval.putParam(funcParams.get(i), params.get(i));
				}
				
			} catch(NullPointerException e){
				System.out.println("xjfjxhxzh");
				return " ";
			}
		}

		ArrayList<Lista> instrucciones = new ArrayList<Lista>();
		for(int i = 1; i<funciones.get(k).size(); i++){
			instrucciones.add(funciones.get(k).get(i).toLista());
		}

		for(Lista l : instrucciones){
			System.out.println(l);
			switch(parser.verifyLInst(l, this)){
				case "setq":
					eval.setq(l);
					System.out.println(eval.getVars());
					break;

				case "a":
					//System.out.println(l);
					return Float.toString(eval.operacionAritmetica(l));

				case "print":
					System.out.println(eval.print(l));
					break;

				case "cond":
					return eval.cond(l);

				default:
					if(funciones.containsKey(l.getInst())){
						return execFunc(l.getInst(), getParams(new Token(l.getElemAt(0).toString())));
					}
			}
		}
		return " ";
	}

	public ArrayList<String> getParams(Token t){
		ArrayList<String> temp = new ArrayList<String>();
		String params = t.toString().substring(1, t.toString().length()-1);
		temp = new ArrayList<>(Arrays.asList(params.split(",")));
		
		return temp;
	}

	public HashMap<String, ArrayList<Elemento>> getFunciones(){
		return funciones;
	}

	public boolean evalCond(Lista l, Evaluate eval){
		ArrayList<Float> operandos = new ArrayList<Float>();
		for(Elemento e : l.getElems()){
			//System.out.println(e);
			if(e.isToken()){
				try{
					operandos.add(Float.parseFloat(e.toString()));
				} catch(Exception err){
					if(searchVar(e.toString())!=null){
						operandos.add(eval.searchVar(e.toString()).floatValue());
					} else if(searchParam(e.toString())!=null){
						try{
							operandos.add(Float.parseFloat(eval.searchParam(e.toString())));
						} catch(Exception err2){
							System.out.println("aiksfaiuf");
						}
						
					}
					
				}
				
			} else{
				Float aaaa = operacionAritmetica(e.toLista());
				operandos.add(aaaa.floatValue());
			}
		}

		//System.out.println(l);
		//System.out.println(operandos);

		switch(l.getInst()){

			case "=":
				//System.out.println(Math.round(operandos.get(0)) == Math.round(operandos.get(1)));
				return Math.round(operandos.get(0)) == Math.round(operandos.get(1));
			case "<":
				//System.out.println(Math.round(operandos.get(0)) < Math.round(operandos.get(1)));
				return Math.round(operandos.get(0)) < Math.round(operandos.get(1));
			case ">":
				//System.out.println(Math.round(operandos.get(0)) > Math.round(operandos.get(1)));
				return Math.round(operandos.get(0)) > Math.round(operandos.get(1));
		}
		return false;
	}

	public String cond(Lista l){
		Parse parser = new Parse();
		boolean temp = false;
		for(Elemento h : l.getElems()){
			if(h.toLista().getInst().equals("t") && !temp){
				
				for(Elemento f : h.toLista().getElems()){
					switch(parser.verifyLInst(f.toLista(), this)){
						case "setq":
							setq(f.toLista());
							System.out.println(getVars());
							temp = true;
							break;

						case "a":
							System.out.println(operacionAritmetica(f.toLista()));
							temp = true;
							break;

						case "print":
							System.out.println(print(f.toLista()));
							temp = true;
							break;

						case "cond":
							cond(f.toLista());
							temp = true;
							break;

						default:
							if(funciones.containsKey(f.toLista().getInst())){
								execFunc(f.toLista().getInst(), getParams(new Token(f.toLista().getElemAt(0).toString())));
								temp = true;
							}
					}
				}

			} else if(evalCond(parser.toLista(h.toLista().getInst().substring(1, h.toLista().getInst().length())), this)){
				for(Elemento f : h.toLista().getElems()){
					switch(parser.verifyLInst(f.toLista(), this)){
						case "setq":
							setq(f.toLista());
							System.out.println(getVars());
							temp = true;
							break;

						case "a":
							System.out.println(operacionAritmetica(f.toLista()));
							temp = true;
							break;

						case "print":
							System.out.println(print(f.toLista()));
							temp = true;
							break;

						case "cond":
							cond(f.toLista());
							temp = true;
							break;

						default:
							if(funciones.containsKey(f.toLista().getInst())){
								execFunc(f.toLista().getInst(), getParams(new Token(f.toLista().getElemAt(0).toString())));
								temp = true;
							}
					}
				}
			}
		}
		return " ";
	}
}