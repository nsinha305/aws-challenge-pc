import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.simple.JSONObject;

public class ConvertDataToJSON {
	
	public static final String QUERY = "SELECT * FROM f_5500_2016_latest";
    public static final int LIMIT = 2000;
    
	@SuppressWarnings("unchecked")
	public static void main(String args[]) {
		System.out.print("Processing ");
		try {
			PrintWriter pw = new PrintWriter(Constants.FILE_NAME, Constants.CSN);
			// using jdbc because the data itself contains ',' and a simple
			// split by ',' will not work.
			int fileNum = 2;
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet data = stmt.executeQuery(QUERY);
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
                //TODO No a good way will change later need to use Object Mapper
                String s = jObject.toString();
                s = s.replaceAll("\"\"", "null");
                pw.println(s);
                count++;
                if (count % LIMIT == 0) {
                    pw.close();
                    pw = new PrintWriter("data_" + fileNum + ".json", Constants.CSN);
                    System.out.println("File - " + fileNum);
                    fileNum++;
                }
            }
            System.out.println("\nDone");
            //TODO move things to finally
            closeResources(pw, conn, stmt);
        } catch (Exception e) {
            System.out.println("Exception caught" + e.getMessage());
        }
    }

    private static void closeResources(PrintWriter pw, Connection conn, Statement stmt) {
        try {
            pw.close();
            conn.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Exception while closing the resources" + e.getMessage());
        }
    }

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(Constants.CLASS_NAME);
        return DriverManager.getConnection(Constants.JDBC_RELIQUE + Constants.PATH);
    }
}