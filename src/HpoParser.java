import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class HpoParser {

	public static void main( String [] args ) throws IOException{

		FileReader file = new FileReader("hp.obo");			
		BufferedReader reader = new BufferedReader(file);

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your symptoms :  ");
		String symptom = sc.next(); 
		sc.close();
		
		String line;

		String delims = "[ ]+";

		ArrayList<String> names = new ArrayList();
		ArrayList<String> ids = new ArrayList<String>();

		while ((line = reader.readLine()) != null){

			if (line.contains("[Term]")){

				line = reader.readLine();
				String id = "";
				String name = "";

				boolean idFound = false;

				if (line.contains("id:") && !line.contains("alt_id:") && !line.contains("pmid:")){
					idFound = true;
					line = line.trim();
					String[] tokens = line.split(delims);
					id = tokens[1];
					ids.add(id);
					line = reader.readLine();					
				}
				if (line.contains("is_anonymous: true")){
					line = reader.readLine();
				}

				if (line.contains("name:")){
					idFound = false;
					line = line.trim();				
					String[] tokens = line.split(delims);
					for (int i = 1; i < tokens.length; i++){
						name = name + tokens[i] + " ";
					}
					names.add(name);


				}
				if (idFound){
					names.add(name);
				}

			}

		}

		for (int i = 0; i < names.size(); i++){
			if (names.get(i).toLowerCase().contains(symptom)){
				System.out.println(names.get(i) + " || " + ids.get(i));
			}
		}

		file.close();

	}
}
