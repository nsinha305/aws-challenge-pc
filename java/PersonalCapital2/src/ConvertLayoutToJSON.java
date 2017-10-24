import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class ConvertLayoutToJSON {

	public static void main(String[] args) {
		Scanner sc;
        try {
            sc = new Scanner(new File(Constants.LAYOUT_PATH));
            PrintWriter pw = new PrintWriter(Constants.LAYOUT_JSON, Constants.CSN);
            pw.println("{");
            pw.println("\"data\" : {");
            pw.println("\"properties\" : {");
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                if (!(s.startsWith("FIELD_POSITION") || s.startsWith("======"))) {
                    String data[] = s.split(",");
                    String dataType = "text";
                    if (data[2].equalsIgnoreCase("NUMERIC"))
                        dataType = "integer";
                    System.out.println(data[1] + " : " + data[2] + " : " + dataType);
                    pw.println("\"" + data[1].toLowerCase() + "\" : { \"type\" : \"" + dataType.toLowerCase() + "\"},");
                }
            }
            pw.println("}");
            pw.println("}");
            pw.println("}");
            pw.close();
            sc.close();
        } catch (Exception e) {
            System.out.println("Exception caught" + e.getMessage());
            e.printStackTrace();
        }
	}
}