import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class atcParser {
	public static void main(String[] args) throws IOException{

		FileReader file = new FileReader("br08303.keg");

		BufferedReader reader = new BufferedReader(file);
		String line;
		String delims = "[ ]+";

		//ArrayList<String> Name = new ArrayList();
		//ArrayList<String> Id = new ArrayList<String>();
		while ((line = reader.readLine()) != null)
		{
			String level = "";
			String name ="";
			String id = "";
			String dgid = "" ;
			String country = "" ;

			if (line.contains("Chlorhexidine"))
			{
				System.out.println(line);
				String[] tokens = line.split(" +");
				level = tokens[0];
				id = tokens[1];
				for (int i = 2 ; i < tokens.length; i++)
				{
				name = name + " " + tokens[i];
				}
				if (name.contains("["))
				{
					String[] tokens0 = name.split("\\["); 
					dgid = tokens0[1].substring(3,tokens0[1].length()-1);
					name = tokens0[0];
				}
				if (name.contains("("))
				{
					String[] tokens1 = name.split("\\("); 
					country = tokens1[1].substring(0,tokens1[1].length()-1);
					name = tokens1[0];
				}

						System.out.println("Nom :" +name + ", niveau d'imbrication : "+ level + ", id : "+ id);



			}
		}

	}
}

