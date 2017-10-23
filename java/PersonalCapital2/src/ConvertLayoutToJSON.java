import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class ConvertLayoutToJSON {

	public static void main(String[] args) {
		Scanner sc;
		try {
			sc = new Scanner(new File("../PersonalCapital2/src/resources/f_5500_2016_latest_layout.txt"));
			PrintWriter pw = new PrintWriter("layout.json", "UTF-8");
			pw.println("{");
			pw.println("\"data\" : {");
			pw.println("\"properties\" : {");
			while (sc.hasNextLine()) {
				String s = sc.nextLine();
				if (!(s.startsWith("FIELD_POSITION") || s.startsWith("======"))) {
					String data[]=s.split(",");
					String dataType = "text";
					if(data[2].equalsIgnoreCase("NUMERIC"))
						dataType="integer";
					System.out.println(data[1]+" : "+data[2]+ " : "+dataType);
					pw.println("\""+data[1].toLowerCase()+"\" : { \"type\" : \""+dataType.toLowerCase()+"\"},");
				}
			}
			pw.println("}");
			pw.println("}");
			pw.println("}");
			pw.close();
		} catch (Exception e) {
			System.out.println("Exception caught");
		}
	}
}