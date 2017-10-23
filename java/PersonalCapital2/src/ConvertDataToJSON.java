import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;
import org.json.simple.JSONObject;

public class ConvertDataToJSON {
	public static void main(String args[]) {
		System.out.print("Processing ");
		try {
			PrintWriter pw = new PrintWriter("data_1.json", "UTF-8");
			// using jdbc because the data itself contains ',' and a simple
			// split by ',' will not work.
			int fileNum = 2;
			Class.forName("org.relique.jdbc.csv.CsvDriver");
			Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + "../PersonalCapital2/src/resources/");
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM f_5500_2016_latest";
			ResultSet data = stmt.executeQuery(query);
			ResultSetMetaData columnNames = data.getMetaData();
			int numCol = columnNames.getColumnCount();
			// for index when uploading bulk data
			int count = 1;
			while (data.next()) {
				System.out.print(".");
				pw.println("{ \"index\" : { \"_index\": \"form\", \"_type\" : \"listings\", \"_id\" : \"" + count
						+ "\" } }");
				JSONObject jObject = new JSONObject();
				for (int i = 0; i < numCol; i++) {
					String columnName = columnNames.getColumnName(i + 1);
					Object columnData = data.getObject(columnName);
					jObject.put(columnName, columnData);
				}
				String s = jObject.toString();
				s = s.replaceAll("\"\"", "null");
				pw.println(s);
				count++;
				if (count % 2000 == 0) {
					pw.close();
					pw = new PrintWriter("data_" + fileNum + ".json", "UTF-8");
					System.out.println("File - " + fileNum);
					fileNum++;
				}
			}
			System.out.println("\nDone");
			pw.close();
		} catch (Exception e) {
			System.out.println("Exception caught" + e.toString());
		}
	}
}
