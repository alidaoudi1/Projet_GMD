import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class HpoParser {

	public static void main( String [] args ) throws IOException{

		FileReader file = new FileReader("/home/soufiane/workspace/GMD/hp.obo");			
		BufferedReader reader = new BufferedReader(file);

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your symptoms :  ");
		String symptom = sc.next(); 
		sc.close();
		
		String line;

		String delims = "[ ]+";

		ArrayList<String> Name = new ArrayList();
		ArrayList<String> Id = new ArrayList<String>();

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
					Id.add(id);
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
					Name.add(name);


				}
				if (idFound){
					Name.add(name);
				}

			}

		}

		for (int i = 0; i < Name.size(); i++){
			if (Name.get(i).toLowerCase().contains(symptom)){
				System.out.println(Name.get(i) + " || " + Id.get(i));
			}
		}

		file.close();

	}
}
