class Token implements Elemento{
	String contenido;

	public Token(String contenido){
		this.contenido = contenido;
	}

	public boolean isNum(){
		try{
			Integer.parseInt(contenido);
			return true;
		} catch(Exception e){
			return false;
		}
	}

	@Override
	public String toString(){
		return contenido;
	}

	@Override
	public boolean isToken(){
		return true;
	}

	@Override
	public Lista toLista(){
		return null;
	}
}