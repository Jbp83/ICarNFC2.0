package services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
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
    public String Login(@RequestParam("UserLogin") String UserLogin,@RequestParam("UserPassword") String UserPassword)
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
                return "fail to login";
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @RequestMapping(method = RequestMethod.POST, value ="/subscribe")
    public String Subscribe(@RequestParam("UserLogin") String UserLogin,@RequestParam("UserMail") String UserMail ,@RequestParam("UserPassword") String UserPassword,@RequestParam("UserStatut") String UserStatut)
    {

        //Connection à la base de donnée avec la variable conn
        Connection  conn = getConnection();

        // On déclare les variables à utiliser
        Statement statement;
        PreparedStatement PrepStat;
        ResultSet resultats;
        String Req;
        Req = "SELECT * FROM users WHERE login='"+UserLogin+ "' ;";

        try {
            statement =  conn.createStatement();
            resultats = statement.executeQuery(Req);

            if(resultats.next()) {


                return "User Already exist";
            }
            else
            {
                Req = "INSERT INTO users ( mail, login, password, status) VALUES (?, ?, ?, ?)";
                PrepStat = conn.prepareStatement(Req);

                PrepStat.setString(1,UserMail);
                PrepStat.setString(2,UserLogin);
                PrepStat.setString(3,UserPassword);
                PrepStat.setString(4,UserStatut);

                int created = PrepStat.executeUpdate();
                if(created ==1)
                    return "utilisateur créé";
                else
                    return "erreur de création";
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @RequestMapping(method = RequestMethod.POST, value ="/info")
    public Response getInfos(@RequestParam("UserLogin") String UserLogin)
    {
        JSONObject jsonInfo = new JSONObject();

        //Connection à la base de donnée avec la variable conn
        Connection  conn = getConnection();

        // On déclare les variables à utiliser
        Statement statement;
        ResultSet resultats;
        String Req;
        Req = "SELECT * FROM users WHERE login='"+UserLogin+"' ;";

        try {
            statement =  conn.createStatement();
            resultats = statement.executeQuery(Req);

            if(resultats.next()) {


                return Response.ok(jsonInfo).build();


            }
            else
            {
                return Response.status(400).build();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @RequestMapping(method = RequestMethod.GET, value ="/userCars")
    public Response getCars(@PathParam("UserLogin") String UserLogin)
    {
        JSONObject jsonCar = new JSONObject();
        JSONArray CarArray = new JSONArray();
        //Connection à la base de donnée avec la variable conn
        Connection  conn = getConnection();

        // On déclare les variables à utiliser
        Statement statement;
        ResultSet resultats;
        String Req;
        Req = "SELECT * FROM voiture WHERE id_proprietaire=(SELECT id FROM users WHERE login='"+UserLogin +"');";

        try {
            statement =  conn.createStatement();
            resultats = statement.executeQuery(Req);

            while(resultats.next()) {

                try {
                    jsonCar.put("id",resultats.getInt("id"));
                    jsonCar.put("id_proprietaire",resultats.getInt("id_proprietaire"));
                    jsonCar.put("Immatriculation",resultats.getString("Immatriculation"));
                    jsonCar.put("Modèle",resultats.getInt("Modèle"));
                    jsonCar.put("DateImmat",resultats.getDate("DateImmat"));
                    jsonCar.put("CV",resultats.getInt("CV"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                CarArray.put(jsonCar);


            }
            return Response.ok(CarArray.toString()).build();

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
