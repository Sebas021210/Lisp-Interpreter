import java.util.HashMap;
import java.util.ArrayList;

class Evaluate{
	
	HashMap<String, Integer> vars;

	public Evaluate(){
		vars = new HashMap<String, Integer>();
	}

	public void setq(Lista l){
		try{
			int val = Integer.parseInt(l.getElemAt(1).toString());
			vars.put(l.getElemAt(0).toString(), Integer.parseInt(l.getElemAt(1).toString()));
		} catch(Exception e){
			if(l.getElemAt(1).isToken()){
				if(searchVar(l.getElemAt(1).toString()) != null){
					vars.put(l.getElemAt(0).toString(), searchVar(l.getElemAt(1).toString()));
				} else {
					System.out.println("??");
				}
			} else {
				//FALTA VERIFICAR SI EXISTE LA FUNCION PERO TODAVIA NO SÉ CREAR FUNCIONES :DDDDD
				vars.put(l.getElemAt(0).toString(), chiquitin(l.getElemAt(1).toLista()));
			}
		}
		
	}

	public HashMap<String, Integer> getVars(){
		return vars;
	}

	public Integer searchVar(String k){
		if(vars.containsKey(k)){
			return vars.get(k);
		} else {
			return null;
		}
	}

	public int chiquitin(Lista l){
		ArrayList<Integer> operandos = new ArrayList<Integer>();
		int tot = 0;
		switch(l.getInst()){
			case "+":
				for(Elemento e : l.getElems()){
					if(e.isToken()){
						try{
							operandos.add(Integer.parseInt(e.toString()));
						} catch(Exception err){
							operandos.add(searchVar(e.toString()));
						}
						
					} else{
						operandos.add(chiquitin(e.toLista()));
					}
				}
				for(int a : operandos){
					tot += a;
				}
				break;
			case "-":
				for(Elemento e : l.getElems()){
					if(e.isToken()){
						try{
							operandos.add(Integer.parseInt(e.toString()));
						} catch(Exception err){
							operandos.add(searchVar(e.toString()));
						}
						
					} else{
						operandos.add(chiquitin(e.toLista()));
					}
				}
				for(int i = 0; i<operandos.size(); i++){
					if(i==0){
						tot+=operandos.get(i);
					} else {
						tot -= operandos.get(i);
					}
				}
				break;
		}
		return tot;
	}

	public String print(Lista l){
		String temp = l.getElemAt(0).toString();
		return temp.substring(1, temp.length()-1);
	}

}