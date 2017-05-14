package services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.sql.*;

@RestController
public class icarService {

    private static Connection connection;


    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/icarnfc";
            connection = DriverManager.getConnection(url,"root","root");
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

    @RequestMapping(method = RequestMethod.GET, value ="/voiture/{idVoiture}")
    public String getVoiture(@PathVariable("idVoiture") String idVoiture)
    {
        JSONObject jsonVoiture = new JSONObject();
        JSONObject jsonArray = new JSONObject();
        JSONArray jsonvVoitureArray = new JSONArray();


        //Connection à la base de donnée avec la variable conn
        Connection  conn = getConnection();

        // On déclare les variables à utiliser
        Statement statement;
        ResultSet resultat;
        String Req;
        Req = "SELECT * FROM voiture WHERE `id`="+idVoiture +";";

        try {
            statement = conn.createStatement();
            resultat = statement.executeQuery(Req);

            while (resultat.next()) {
                jsonVoiture.put("id", resultat.getInt("id"));
                jsonVoiture.put("guid", resultat.getString("guid"));
                jsonVoiture.put("id_proprietaire", resultat.getInt("id_proprietaire"));
                jsonVoiture.put("Immatriculation", resultat.getString("Immatriculation"));
                jsonVoiture.put("nom", resultat.getString("nom"));
                jsonVoiture.put("marque", resultat.getString("marque"));
                jsonVoiture.put("modele", resultat.getString("modele"));
                jsonVoiture.put("DateImmat", resultat.getDate("DateImmat"));
                jsonVoiture.put("CV", resultat.getString("CV"));
                jsonVoiture.put("Photo", resultat.getString("Photo"));
                jsonvVoitureArray.put(jsonVoiture);
                jsonArray.put("Voiture",jsonvVoitureArray);
                return jsonArray.toString();
            }

            return jsonVoiture.toString();

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value ="/ficheentretien/{identretien}")
    public String getFiche(@PathVariable("identretien") String identretien)
    {
        JSONObject jsonFiche = new JSONObject();
        JSONObject jsonArray = new JSONObject();
        JSONArray jsonFicheArray = new JSONArray();


        //Connection à la base de donnée avec la variable conn
        Connection  conn = getConnection();

        // On déclare les variables à utiliser
        Statement statement;
        ResultSet resultat;
        String Req;
        Req = "SELECT * FROM entretien WHERE `id`="+identretien +";";

        try {
            statement = conn.createStatement();
            resultat = statement.executeQuery(Req);

            while (resultat.next()) {
                jsonFiche.put("id", resultat.getInt("id"));
                jsonFiche.put("date_creation", resultat.getString("date_creation"));
                jsonFiche.put("id_voiture", resultat.getInt("id_voiture"));
                jsonFiche.put("id_etablissement", resultat.getString("id_etablissement"));
                jsonFiche.put("iddetailentretien", resultat.getInt("IdDetailEntretien"));
                jsonFiche.put("detailentretien", resultat.getString("detail_entretien"));
                jsonFiche.put("id", resultat.getInt("idmecanicien"));
                jsonFicheArray.put(jsonFiche);
                jsonArray.put("Entretien",jsonFicheArray);
                return jsonArray.toString();
            }

            return jsonFiche.toString();

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    @RequestMapping(method = RequestMethod.POST, value ="/deleteCar")
    public String deleteUserCar(@RequestParam("idVoiture") String idVoiture)
    {

        //Connection à la base de donnée avec la variable conn
        Connection  conn = getConnection();

        // On déclare les variables à utiliser
        PreparedStatement PrepStat;
        int resultat;

        String Req = "DELETE FROM voiture WHERE `id`="+idVoiture +";";

      try {
          PrepStat =  conn.prepareStatement(Req);
            resultat = PrepStat.executeUpdate();

              if(resultat == 1)
              {
                return "deleted";
            }
            else
            {
                return "erreur";
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
                jsonFiche.put("detail_entretien", resultat.getString("detail_entretien"));
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


    @RequestMapping(method = RequestMethod.POST, value ="/addEntretien")
    public String PostEntretiens(@RequestParam("date_creation") Date date_creation, @RequestParam("id_voiture") int id_voiture,@RequestParam("id_etablissement") int id_etablissement,@RequestParam("id_utilisateur") int id_utilisateur,@RequestParam("description") String description, @RequestParam("id_mecanicien") int idmecanicien)
    {
        //Connection à la base de donnée avec la variable conn
        Connection  conn = getConnection();

        // On déclare les variables à utiliser
        PreparedStatement PrepStat;
        String Req;

        Req = "INSERT INTO entretien ( date_creation, id_voiture, id_etablissement, id_utilisateur, detail_entretien) VALUES ( ?, ?, ?, ?,?,?)";


        try {
            PrepStat = conn.prepareStatement(Req);
            PrepStat.setDate(1,date_creation);
            PrepStat.setInt(2,id_voiture);
            PrepStat.setInt(3,id_etablissement);
            PrepStat.setInt(4,id_utilisateur);
            PrepStat.setString(5,description);
            PrepStat.setInt(6,idmecanicien);


            int created = PrepStat.executeUpdate();
            if(created ==1)
            {
                    return "entretien créé";
            }
            else
                return "erreur de création";
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @RequestMapping(method = RequestMethod.POST, value ="/subscribe")
    public String Subscribe(@RequestParam("UserName") String UserName,@RequestParam("UserSurname") String UserSurname, @RequestParam("UserMail") String UserMail ,@RequestParam("UserPassword") String UserPassword,@RequestParam("UserStatut") String UserStatut,@RequestParam("Avatar") String avatar)
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
                PrepStat.setString(6,avatar);

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

    @RequestMapping(method = RequestMethod.GET, value ="/entreprises")
    public String getEntreprise()
    {

        JSONArray EtablissementArray = new JSONArray();
        JSONObject jsonArray = new JSONObject();
        JSONArray jsonNOM = new JSONArray();


        //Connection à la base de donnée avec la variable conn
        Connection  conn = getConnection();

        // On déclare les variables à utiliser
        Statement statement;
        ResultSet resultats;
        String Req;
        Req = "SELECT * FROM etablissement;";

        try {
            statement =  conn.createStatement();
            resultats = statement.executeQuery(Req);


            if(resultats.next())
            {
                resultats.previous();

                while(resultats.next()) {
                    JSONObject jsonEnt = new JSONObject();
                    jsonEnt.put("id",resultats.getString("id"));
                    jsonEnt.put("Nom",resultats.getString("Nom"));
                    jsonEnt.put("Siren",resultats.getString("Siren"));
                    jsonEnt.put("Adresse",resultats.getString("Adresse"));
                    jsonEnt.put("Telephone",resultats.getString("Telephone"));
                    EtablissementArray.put(jsonEnt);
                    jsonNOM.put(jsonEnt);
                    jsonArray.put("Entreprise",jsonNOM);
                }

                return jsonArray.toString();
            }
            else
            {
                return "error cannot load company";
            }

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return "fail to load company";
    }



    @RequestMapping(method = RequestMethod.GET, value ="/CarsDb")
    public String GetCarsDb()
    {

        JSONArray CarsDb = new JSONArray();
        JSONObject jsonArray = new JSONObject();
        JSONArray jsonNOM = new JSONArray();


        //Connection à la base de donnée avec la variable conn
        Connection  conn = getConnection();

        // On déclare les variables à utiliser
        Statement statement;
        ResultSet resultats;
        String Req;
        Req = "SELECT * FROM voiture;";

        try {
            statement =  conn.createStatement();
            resultats = statement.executeQuery(Req);


            if(resultats.next())
            {
                resultats.previous();

                while(resultats.next()) {
                    JSONObject jsonEnt = new JSONObject();
                    jsonEnt.put("id",resultats.getString("id"));
                    jsonEnt.put("Marque",resultats.getString("marque"));
                    jsonEnt.put("Modele",resultats.getString("modele"));
                    CarsDb.put(jsonEnt);
                    jsonNOM.put(jsonEnt);
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
                    jsonCar.put("guid",resultats.getString("guid"));
                    jsonCar.put("nom",resultats.getString("nom"));
                    jsonCar.put("id",resultats.getInt("id"));
                    jsonCar.put("id_proprietaire",resultats.getInt("id_proprietaire"));
                    jsonCar.put("Immatriculation",resultats.getString("Immatriculation"));
                    jsonCar.put("marque",resultats.getString("marque"));
                    jsonCar.put("modele",resultats.getString("modele"));
                    jsonCar.put("DateImmat",resultats.getDate("DateImmat"));
                    jsonCar.put("CV",resultats.getInt("CV"));
                    jsonCar.put("Blob",resultats.getString("Photo"));
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
    public String AddCar(@RequestParam("GUID") String GUID,@RequestParam("UserID") String UserId,@RequestParam("CarImmat") String CarImmat, @RequestParam("CarName") String CarName,@RequestParam("CarBrand") String CarBrand, @RequestParam("CarModel") String CarModel ,@RequestParam("DateImmat") String DateImmat,@RequestParam("CV") String CV,@RequestParam("Photo") String Photo)
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
                Req = "INSERT INTO voiture (guid,id_proprietaire, Immatriculation, nom, marque, modele, DateImmat,CV,Photo) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? ,?)";
                PrepStat = conn.prepareStatement(Req);

                PrepStat.setString(1,GUID);
                PrepStat.setString(2,UserId);
                PrepStat.setString(3,CarImmat);
                PrepStat.setString(4,CarName);
                PrepStat.setString(5,CarBrand);
                PrepStat.setString(6,CarModel);
                PrepStat.setString(7,DateImmat);
                PrepStat.setString(8,CV);
                PrepStat.setString(9,Photo);


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
