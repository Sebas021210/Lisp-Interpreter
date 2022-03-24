import java.util.ArrayList;

class Lista implements Elemento{
	Token instruccion;
	ArrayList<Elemento> aigf = new ArrayList<Elemento>();

	public Lista(Token instruccion, Elemento temp){
		this.instruccion = instruccion;
		aigf.add(temp);
	}

	public Lista(Token instruccion, ArrayList<Elemento> aigf){
		this.instruccion = instruccion;
		this.aigf = aigf;
	}

	public ArrayList<Elemento> getElems(){
		return aigf;
	}

	public Elemento getElemAt(int index){
		return aigf.get(index);
	}

	public void addElem(Elemento elem){
		aigf.add(elem);
	}

	@Override
	public boolean isToken(){
		return false;
	}

	public String getInst(){
		return instruccion.toString();
	}

	public Lista toLista(){
		return this;
	}

	@Override
	public String toString(){
		String elems = "";
		for(Elemento e : aigf){
			elems += e.toString() + " ";
		}
		return "( " + instruccion + " " + elems + ")";
	}
}