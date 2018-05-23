package projectTable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Arquivos {
	
	private static String FILENAME = "filename.txt";
		
	public void setFilename(String name) {
		FILENAME = name;
	}
	public String getFilename() {
		return FILENAME;
	}
	
	public List<String> ler() {
		List<String> list = new ArrayList<>();

		try (BufferedReader br = Files.newBufferedReader(Paths.get(FILENAME))) {

			//br returns as stream and convert it into a List
			list = br.lines().collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}
	
		//list.forEach(System.out::println);
		return list;
	}
	
	
	public void escrever(String texto, boolean add) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		
		try {
			fw = new FileWriter(FILENAME, add);
			bw = new BufferedWriter(fw);
			bw.write(texto);
			
			System.out.println("Done");  //Debug		
			
		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
	}
}
