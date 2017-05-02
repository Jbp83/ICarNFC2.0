package services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
    public String Login(@RequestParam("UserMail") String UserMail,@RequestParam("UserPassword") String UserPassword)
    {

        //Connection à la base de donnée avec la variable conn
        Connection  conn = getConnection();

        // On déclare les variables à utiliser
        Statement statement;
        ResultSet resultats;
        String Req;


        Req = "SELECT * FROM users WHERE mail='"+UserMail+"'AND password ='"+ UserPassword + "' ;";

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
    public String Subscribe(@RequestParam("UserName") String UserName,@RequestParam("UserSurname") String UserSurname, @RequestParam("UserMail") String UserMail ,@RequestParam("UserPassword") String UserPassword,@RequestParam("UserStatut") String UserStatut)
    {

        //Connection à la base de donnée avec la variable conn
        Connection  conn = getConnection();

        // On déclare les variables à utiliser
        Statement statement;
        PreparedStatement PrepStat;
        ResultSet resultats;
        String Req;
        Req = "SELECT * FROM users WHERE mail='"+UserMail+ "' ;";

        try {
            statement =  conn.createStatement();
            resultats = statement.executeQuery(Req);

            if(resultats.next()) {


                return "User Already exist";
            }
            else
            {
                Req = "INSERT INTO users ( mail, nom, prenom, password, status) VALUES ( ?, ?, ?, ?, ?)";
                PrepStat = conn.prepareStatement(Req);

                PrepStat.setString(1,UserMail);
                PrepStat.setString(2,UserName);
                PrepStat.setString(3,UserSurname);
                PrepStat.setString(4,UserPassword);
                PrepStat.setString(5,UserStatut);

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


    @RequestMapping(method = RequestMethod.GET, value ="/info")
    public String getInfos(@RequestParam("UserMail") String UserMail)
    {
        JSONObject jsonInfo = new JSONObject();

        //Connection à la base de donnée avec la variable conn
        Connection  conn = getConnection();

        // On déclare les variables à utiliser
        Statement statement;
        ResultSet resultats;
        String Req;
        Req = "SELECT * FROM users WHERE `mail`='"+UserMail +"';";

        try {
            statement =  conn.createStatement();
            resultats = statement.executeQuery(Req);

            if(resultats.next()) {

                jsonInfo.put("nom", resultats.getString("nom"));
                jsonInfo.put("prenom", resultats.getString("prenom"));
                jsonInfo.put("mail", resultats.getString("mail"));
                jsonInfo.put("status", resultats.getString("status"));
                return jsonInfo.toString();


            }
            else
            {
                return "error user not found";
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value ="/userCars")
    public String getCars(@RequestParam("UserMail") String UserMail)
    {
        JSONArray CarArray = new JSONArray();


        //Connection à la base de donnée avec la variable conn
        Connection  conn = getConnection();

        // On déclare les variables à utiliser
        Statement statement;
        ResultSet resultats;
        String Req;
        Req = "SELECT * FROM `voiture` WHERE `id_proprietaire` = (SELECT `id` FROM `users` WHERE `mail`='"+UserMail +"');";

        try {
            statement =  conn.createStatement();
            resultats = statement.executeQuery(Req);


            if(resultats.next())
            {
                resultats.previous();
                
                while(resultats.next()) {
                    JSONObject jsonCar = new JSONObject();
                    jsonCar.put("id",resultats.getInt("id"));
                    jsonCar.put("id_proprietaire",resultats.getInt("id_proprietaire"));
                    jsonCar.put("Immatriculation",resultats.getString("Immatriculation"));
                    jsonCar.put("Modèle",resultats.getString("Modèle"));
                    jsonCar.put("DateImmat",resultats.getDate("DateImmat"));
                    jsonCar.put("CV",resultats.getInt("CV"));
                    jsonCar.put("urlimage",resultats.getString("urlimage"));
                    CarArray.put(jsonCar);
                }

               return CarArray.toString();
            }
            else
            {
                return "error cannot load cars";
            }

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
         catch (JSONException e) {
        e.printStackTrace();
    }

        return "fail to load cars";
    }


    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";

    }


}
