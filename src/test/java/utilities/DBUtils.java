package utilities;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class DBUtils {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

   //parametreli connection methodu
    public static Connection connectToDataBase(String hostName, String dbName,String username, String password)  {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://"+hostName+":5432/"+dbName,username,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(connection!=null){
            System.out.println("Connection Success");
        }else {
            System.out.println("Connection Fail");
        }

        return connection;
    }
    //parematresiz connection methodu
    public static void createConnection() {
        String url = "jdbc:postgresql://164.92.252.42:5432/school_management";
        String username = "select_user";
        String password = "43w5ijfso";
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //girilen queryi list halinde donen method
    public static List<List<Object>> getQueryResultList(String query) {
        executeQuery(query);
        List<List<Object>> rowList = new ArrayList<>();
        ResultSetMetaData rsmd;
        try {
            rsmd = resultSet.getMetaData();
            while (resultSet.next()) {
                List<Object> row = new ArrayList<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    row.add(resultSet.getObject(i));
                }
                rowList.add(row);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rowList;
    }

    //database baglantisini kapatan method
    public static void closeConnectionAndStatement(){

        try {
            connection.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            if(connection.isClosed()&&statement.isClosed()){
                System.out.println("Connection and statement closed!");

            }else {
                System.out.println("Connection and statement NOT closed!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }




    //3. Adım: Statement oluştur.
    public static Statement createStatement(){


        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return statement;
    }

    //4. Adım: Query çalıştır.
    public static boolean execute(String sql){
        boolean isExecute;
        try {
            isExecute = statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isExecute;
    }

    //5. Adım: Bağlantı ve Statement'ı kapat.


    //Table oluşturan method
    public static void createTable(String tableName, String... columnName_dataType ){
        StringBuilder columnName_dataValue = new StringBuilder("");

        for(String w : columnName_dataType){

            columnName_dataValue.append(w).append(",");
        }

        columnName_dataValue.deleteCharAt(columnName_dataValue.length()-1);

        try {
            statement.execute( "CREATE TABLE "+tableName+"("+columnName_dataValue+")");
            System.out.println("Table "+tableName+" successfully created!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //Ödev: ExecuteQuery, ExecuteUpdate, Table'a Değer giren,  Sütun Değerlerini List içerisine alan methodları oluşturunuz.

    public static void dropTable(String tableName){
        try {
            statement.execute("DROP TABLE " + tableName);
            System.out.println("Table " + tableName + " dropped");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //the method to create table
    public static void createTables(String tableName, String ...columnName_DataType){
        StringBuilder columnName_DataTypeString = new StringBuilder("");
        for(String w : columnName_DataType){
            columnName_DataTypeString.append(w).append(",");
        }
        columnName_DataTypeString.deleteCharAt(columnName_DataTypeString.lastIndexOf(","));

        try {
            statement.execute("CREATE TABLE " +tableName+ "(" +columnName_DataTypeString+ ")");
            System.out.println("Table " +tableName+ " created!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //the method insert into data table
    public static void insertDataIntoTable(String tableName,String ...columnName_Value){
        StringBuilder columnNames = new StringBuilder("");
        StringBuilder values = new StringBuilder("");

        for(String w : columnName_Value){
            columnNames.append(w.split(" ")[0]).append(",");
            values.append(w.split(" ")[1]).append(",");
        }
        columnNames.deleteCharAt(columnNames.lastIndexOf(","));
        values.deleteCharAt(values.lastIndexOf(","));


        String query = "INSERT INTO " +tableName+ "("+columnNames+") VALUES(" +values+")";
        try {
            statement.execute(query);
            System.out.println("Data inserted into table " +tableName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    ///YASIN HOCANIN GOSTERDIGI DISINDAKI REUSABLELAR
    /**
     * DBUtils.createConnection(); -> to connect to teh database
     */

    /**
     * DBUtils.executeQuery(String query); -> Execute the query and store is the result set object
     */
    public static void executeQuery(String query) {
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //    used to close the connectivity
    public static void closeConnection() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //used to get statement
    public static Statement getStatement() {
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return statement;
    }
    //Use this to get the ResutSet object
    public static ResultSet getResultset() {
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultSet;
    }
    // This method returns the number fo row in a table in the database
    public static int getRowCount() throws Exception {
        resultSet.last();
        int rowCount = resultSet.getRow();
        return rowCount;
    }
    /**
     * @return returns a single cell value. If the results in multiple rows and/or
     *         columns of data, only first column of the first row will be returned.
     *         The rest of the data will be ignored
     */
    public static Object getCellValue(String query) {
        return getQueryResultList(query).get(0).get(0);
    }
    /**
     * @return bir veri satırını temsil eden Dizelerin bir listesini döndürür. eğer sorgu
     *       * birden çok veri satırı ve/veya sütunuyla sonuçlanır, yalnızca ilk satır
     *       * iade edilecektir. Verilerin geri kalanı yoksayılacak
     */
    public static List<Object> getRowList(String query) {
        return getQueryResultList(query).get(0);
    }
    /**
     * @return anahtarın sütun olduğu bir veri satırını temsil eden bir harita döndürür
     *       *         isim. Sorgu, birden çok veri satırı ve/veya sütunuyla sonuçlanırsa,
     *       * sadece ilk satır döndürülür. Verilerin geri kalanı yoksayılacak
     */
    public static Map<String, Object> getRowMap(String query) {
        return getQueryResultMap(query).get(0);
    }
    /**
     * @return sorgu sonucunu, dış listenin temsil ettiği bir liste listesinde döndürür
     *       * satırların ve iç listelerin toplanması tek bir satırı temsil eder
     */

    /**
     * @return list of values of a single column from the result set
     */
    public static List<Object> getColumnData(String query, String column) {
        executeQuery(query);
        List<Object> rowList = new ArrayList<>();
        ResultSetMetaData rsmd;
        try {
            rsmd = resultSet.getMetaData();
            while (resultSet.next()) {
                rowList.add(resultSet.getObject(column));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rowList;
    }
    /**
     * @return returns query result in a list of maps where the list represents
     *         collection of rows and a map represents represent a single row with
     *         key being the column name
     */
    public static List<Map<String, Object>> getQueryResultMap(String query) {
        executeQuery(query);
        List<Map<String, Object>> rowList = new ArrayList<>();
        ResultSetMetaData rsmd;
        try {
            rsmd = resultSet.getMetaData();
            while (resultSet.next()) {
                Map<String, Object> colNameValueMap = new HashMap<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    colNameValueMap.put(rsmd.getColumnName(i), resultSet.getObject(i));
                }
                rowList.add(colNameValueMap);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rowList;
    }
    /*
     * @return List of columns returned in result set
     */
    public static List<String> getColumnNames(String query) {
        executeQuery(query);
        List<String> columns = new ArrayList<>();
        ResultSetMetaData rsmd;
        try {
            rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                columns.add(rsmd.getColumnName(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columns;
    }
}