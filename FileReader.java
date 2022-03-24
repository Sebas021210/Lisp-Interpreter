import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Universidad del Valle de Guatemala
 * Proyecto de Algoritmos y Estructuras de Datos
 * Seccion 20, 2022
 * 
 * @author Andrea Ximena Ramirez Recinos 21874
 * @author Adrian Ricardo Flores Trujillo 21500
 * @version 22/03/2022
 */

class FileReader {
	ArrayList<String> Lines = new ArrayList<String>();
	
	/**
 * Metodo para la lectura del archivo
 * 
 * @param fileName nombre del archivo a leer
 * @return Strings con las lineas del archivo en un ArrayList
 */
	public ArrayList<String> readingFile(String fileName) throws FileNotFoundException{
		
		Scanner scanner  = new Scanner(new File(fileName));
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			line = line.substring(1, line.length()-1);
			Lines.add(line);
		}
		return Lines;
	}


}
