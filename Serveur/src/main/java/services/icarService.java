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
    public String Login(@RequestParam("UserMail") String UserMail,@RequestParam("UserPassword") String UserPassword) {

        JSONObject jsonLogin = new JSONObject();
        JSONObject jsonArray = new JSONObject();
        JSONArray jsonUser = new JSONArray();

        //Connection à la base de donnée avec la variable conn
        Connection conn = getConnection();

        // On déclare les variables à utiliser
        Statement statement;
        ResultSet resultats;
        String Req;


        Req = "SELECT * FROM users WHERE mail='" + UserMail + "'AND password ='" + UserPassword + "' ;";

        try {
            statement =  conn.createStatement();
            resultats = statement.executeQuery(Req);

            if(resultats.next()) {

                jsonLogin.put("id", resultats.getString("id"));
                jsonLogin.put("status", resultats.getString("status"));
                jsonUser.put(jsonLogin);
                jsonArray.put("User",jsonUser);
                return jsonArray.toString();
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


        @RequestMapping(method = RequestMethod.GET, value ="/checkguid")
    public String getguid(@RequestParam("GUID") String Guid)
    {

        //Connection à la base de donnée avec la variable conn
        Connection  conn = getConnection();

        // On déclare les variables à utiliser
        Statement statement;
        ResultSet resultats;
        String Req;


        Req = "SELECT * FROM voiture WHERE guid='"+Guid+"'";

        try {
            statement =  conn.createStatement();
            resultats = statement.executeQuery(Req);

            if(resultats.next()) {


                return "exist";
            }
            else
            {
                return "not exist";
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @RequestMapping(method = RequestMethod.GET, value ="/ficheEntretien")
    public String getEntretien(@RequestParam("UserMail") String UserMail,@RequestParam("id_etablissement") String id_etablissement)
    {
        JSONObject jsonFiche = new JSONObject();
        JSONObject jsonArray = new JSONObject();
        JSONArray jsonNOM = new JSONArray();

        //Connection à la base de donnée avec la variable conn
        Connection  conn = getConnection();

        // On déclare les variables à utiliser
        Statement statement;
        ResultSet resultat;
        String Req;
        Req = "SELECT * FROM entretien WHERE   `id_utilisateur`= (SELECT `id`  FROM `users` WHERE `mail` = '"+UserMail+"') AND `id_etablissement` = '"+id_etablissement+"';";

        try {
            statement = conn.createStatement();
            resultat = statement.executeQuery(Req);

            while (resultat.next()) {
                jsonFiche.put("id", resultat.getInt("id"));
                jsonFiche.put("date", resultat.getDate("date_creation"));
                jsonFiche.put("id_voiture", resultat.getInt("id_voiture"));
                jsonFiche.put("id_etablissement", resultat.getInt("id_etablissement"));
                jsonFiche.put("id_utilisateur", resultat.getInt("id_utilisateur"));
                jsonFiche.put("IdDetailEntretien", resultat.getInt("IdDetailEntretien"));
                jsonNOM.put(jsonFiche);
                jsonArray.put("Fiches",jsonNOM);
            }

            return jsonArray.toString();
            } catch (SQLException e1) {
            e1.printStackTrace();
        }
         catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    @RequestMapping(method = RequestMethod.POST, value ="/ficheEntretien")
    public String PostEntretiens(@RequestParam("UserMail") String UserMail,@RequestParam("date_creation") Date date_creation, @RequestParam("id_voiture") int id_voiture,@RequestParam("id_etablissement") int id_etablissement,@RequestParam("id_utilisateur") int id_utilisateur,@RequestParam("type_entretien") String type_entretien)
    {
        //Connection à la base de donnée avec la variable conn
        Connection  conn = getConnection();

        // On déclare les variables à utiliser
        PreparedStatement PrepStat;
        String Req;

        Req = "INSERT INTO entretien ( date_creation, id_voiture, id_etablissement, id_utilisateur) VALUES ( ?, ?, ?, ?)";
        // todo Faire la requete pour l'insertion du memo dans la table memo 

        try {

            PrepStat = conn.prepareStatement(Req);

            PrepStat.setDate(1,date_creation);
            PrepStat.setInt(2,id_voiture);
            PrepStat.setInt(3,id_etablissement);
            PrepStat.setInt(4,id_utilisateur);

            int created = PrepStat.executeUpdate();
            if(created ==1)
            {
                Req = "INSERT INTO detail_entretien (type_entretien ) VALUES (?)";
                PrepStat = conn.prepareStatement(Req);
                PrepStat.setString(1,type_entretien);
               int ajoutdetail = PrepStat.executeUpdate();

                if(ajoutdetail ==1)
                    return "entretien créé";
                else
                    return "erreur de création";  
            }

            else
                return "erreur de création";
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    @RequestMapping(method = RequestMethod.POST, value ="/subscribe")
    public String Subscribe(@RequestParam("UserName") String UserName,@RequestParam("UserSurname") String UserSurname, @RequestParam("UserMail") String UserMail ,@RequestParam("UserPassword") String UserPassword,@RequestParam("UserStatut") String UserStatut,@RequestParam("Blob") String Blob)
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
                Req = "INSERT INTO users ( mail, nom, prenom, password, status, avatar) VALUES ( ?, ?, ?, ?, ?, ?)";
                PrepStat = conn.prepareStatement(Req);

                PrepStat.setString(1,UserMail);
                PrepStat.setString(2,UserName);
                PrepStat.setString(3,UserSurname);
                PrepStat.setString(4,UserPassword);
                PrepStat.setString(5,UserStatut);
                PrepStat.setString(6,Blob);

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
        JSONObject jsonArray = new JSONObject();
        JSONArray jsonNOM = new JSONArray();

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
                jsonNOM.put(jsonInfo);
                jsonArray.put("User",jsonNOM);
                return jsonArray.toString();
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
        JSONObject jsonArray = new JSONObject();
        JSONArray jsonNOM = new JSONArray();


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
                    jsonCar.put("nom",resultats.getString("nom"));
                    jsonCar.put("id",resultats.getInt("id"));
                    jsonCar.put("id_proprietaire",resultats.getInt("id_proprietaire"));
                    jsonCar.put("Immatriculation",resultats.getString("Immatriculation"));
                    jsonCar.put("marque",resultats.getString("marque"));
                    jsonCar.put("modele",resultats.getString("modele"));
                    jsonCar.put("DateImmat",resultats.getDate("DateImmat"));
                    jsonCar.put("CV",resultats.getInt("CV"));
                    jsonCar.put("urlimage",resultats.getString("urlimage"));
                    CarArray.put(jsonCar);
                    jsonNOM.put(jsonCar);
                    jsonArray.put("Cars",jsonNOM);
                }

               return jsonArray.toString();
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


    @RequestMapping(method = RequestMethod.POST, value ="/addcar")
    public String AddCar(@RequestParam("GUID") String GUID,@RequestParam("UserID") String UserId,@RequestParam("CarImmat") String CarImmat, @RequestParam("CarName") String CarName,@RequestParam("CarBrand") String CarBrand, @RequestParam("CarModel") String CarModel ,@RequestParam("DateImmat") String DateImmat,@RequestParam("CV") String CV)
    {

        //Connection à la base de donnée avec la variable conn
        Connection  conn = getConnection();

        // On déclare les variables à utiliser
        Statement statement;
        PreparedStatement PrepStat;
        ResultSet resultats;
        String Req;
        Req = "SELECT * FROM voiture WHERE GUID='"+GUID+ "' ;";

        try {
            statement =  conn.createStatement();
            resultats = statement.executeQuery(Req);

            if(resultats.next())
            {
                return "Car Already exist";
            }
            else
            {
                Req = "INSERT INTO voiture (guid,id_proprietaire, Immatriculation, nom, marque, modele, DateImmat,CV) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )";
                PrepStat = conn.prepareStatement(Req);

                PrepStat.setString(1,GUID);
                PrepStat.setString(2,UserId);
                PrepStat.setString(3,CarImmat);
                PrepStat.setString(4,CarName);
                PrepStat.setString(5,CarBrand);
                PrepStat.setString(6,CarModel);
                PrepStat.setString(7,DateImmat);
                PrepStat.setString(8,CV);


                int created = PrepStat.executeUpdate();
                if(created ==1)
                    return "voiture crée";
                else
                    return "erreur de création";
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
