package sql;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class ConnectSql {
    public static Connection ConnectToDB() throws Exception {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        String mysqlUrl = "jdbc:mysql://localhost/pokedex";
        Connection con = DriverManager.getConnection(mysqlUrl, "root", "123456");
        System.out.println("Connection established......");
        return con;
    }
    public static void main(String args[]) {
        JSONParser jsonParser = new JSONParser();
        try {
//            Parsing the contents of the JSON file
            JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader("/home/tuanhd/Documents/pokedex.json"));
            Connection con = ConnectToDB();
//            Insert DB
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO pokedex_info values (?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ?, ?)");
            for(int i=0; i < jsonArray.size();i++) {
                JSONObject record = (JSONObject) jsonArray.get(i);
                Long id = (Long) record.get("id");
                System.out.println("Id:" + id);
                JSONObject jsonname = (JSONObject) record.get("name");
                String name_english = (String) jsonname.get("english");
                String name_japanese = (String) jsonname.get("japanese");
                String name_chinese = (String) jsonname.get("chinese");
                String name_french = (String) jsonname.get("french");
                System.out.println("Name Engrisk:" + name_english);
                System.out.println("Name Arigato:" + name_japanese);
                System.out.println("Name Chinese:" + name_chinese);
                System.out.println("Name French:" + name_french);
                JSONObject jsonbase = (JSONObject) record.get("base");
                Long base_hp = (Long) jsonbase.get("HP");
                Long base_attack = (Long) jsonbase.get("Attack");
                Long base_defense = (Long) jsonbase.get("Defense");
                Long base_sp_attack = (Long) jsonbase.get("Sp. Attack");
                Long base_sp_defense = (Long) jsonbase.get("Sp. Defense");
                Long base_speed = (Long) jsonbase.get("Speed");
                System.out.println("Base HP:" + base_hp);
                System.out.println("Base Attack:" + base_attack);
                System.out.println("Base Defense:" + base_defense);
                System.out.println("Base SP Attack:" + base_sp_attack);
                System.out.println("Base SP Defense:" + base_defense);
                System.out.println("Base Speed:" + base_speed);
                JSONArray jsonType = (JSONArray) record.get("type");
                System.out.println("Type: " + jsonType.toString());
                System.out.println("\n");
                pstmt.setLong(1, id);
                pstmt.setString(2, name_english);
                pstmt.setString(3, name_japanese);
                pstmt.setString(4, name_chinese);
                pstmt.setString(5, name_french);
                pstmt.setLong(6, base_hp);
                pstmt.setLong(7, base_attack);
                pstmt.setLong(8, base_defense);
                pstmt.setLong(9, base_sp_attack);
                pstmt.setLong(10, base_sp_defense);
                pstmt.setLong(11, base_speed);
                pstmt.setObject(12, jsonType.toString());
                pstmt.executeUpdate();
            }
            System.out.println("Được của ló");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}