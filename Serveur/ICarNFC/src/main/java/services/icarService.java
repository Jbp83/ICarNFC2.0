package services;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RestController
public class icarService {

    private static Connection connection;


    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/icarnfc";
            connection = DriverManager.getConnection(url,"root","");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }


    @RequestMapping(method = RequestMethod.POST, value ="/login")
    public String getInfos(@RequestParam String UserLogin, @RequestParam String UserPassword)
    {

        //Connection à la base de donnée avec la variable conn
        Connection  conn = getConnection();

        // On déclare les variables à utiliser
        Statement statement;
        ResultSet resultats;
        String Req;
        Req = "SELECT * FROM users WHERE login='"+UserLogin+"'AND password ='"+ UserPassword+ "' ;";

        try {
            statement =  conn.createStatement();
            resultats = statement.executeQuery(Req);

            if(resultats.next()) {


                return resultats.getString("status");
            }
            else
            {
                return "fail";
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";

    }


}
