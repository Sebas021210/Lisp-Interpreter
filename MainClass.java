import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainClass {

	public static void main(String[] args) {
		FileReader reader = new FileReader();
		ArrayList<String> CodeLines = new ArrayList<String>();
		Scanner sn = new Scanner(System.in);  
		
		System.out.println("Bienvenido al interprete de Lisp");
		System.out.println("Por favor, ingese el nombre de su archivo (ej:Ejemplo.lisp)");
		
		String file_name = sn.nextLine();
		
		//Lectura de lineas de codigo de archivo Lisp
		try {

			CodeLines = reader.readingFile(file_name);
			CodeLines.forEach((n) -> System.out.println(n));

		} 

		catch (FileNotFoundException e) {
			System.out.println("No se ha encontrado el archivo");

		}
	}
}
