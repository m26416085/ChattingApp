/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChattingApp;

import com.mongodb.*;
import com.mongodb.Mongo;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
//import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;
import java.awt.Component;
import java.util.ArrayList;
import static java.util.Collections.singletonList;
import java.util.List;
import javax.swing.JOptionPane;
import org.bson.Document;
import ChattingApp.RegisterData;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.bson.conversions.Bson;
/**
 *
 * @author Jeffrey
 */

public class MongoDB {
    MongoDatabase db;
    public RegisterData usernow = new RegisterData();
    private Component parentComponent;
    public MongoDB()
    {
        String url;
        url = "mongodb://admin:admin098)(*@ds042677.mlab.com:42677/proyek_java";
        MongoClient mongoClient = new MongoClient(new MongoClientURI(url));
        db = mongoClient.getDatabase("proyek_java");
        System.out.println("Connected to the database successfully");
    }
    public MongoDB (RegisterData dataregister){
    try {
            String url;
            url = "mongodb://admin:admin098)(*@ds042677.mlab.com:42677/proyek_java";
            MongoClient mongoClient = new MongoClient(new MongoClientURI(url));
            db = mongoClient.getDatabase("proyek_java");
            System.out.println("Connected to the database successfully");
            
            
            //This is how we insert Document single
            MongoCollection<Document> collection = db.getCollection("Data");
            Document User = new Document("Username", dataregister.getUsername())
                    .append("Password", dataregister.getPassword())
                    .append("Nama", dataregister.getName())
                    .append("TglLahir", dataregister.getDob())
                    .append("Status", "xxx")
                    .append("ProfilePicture", "xxx");
            
            Document Event = new Document("NamaEvent", 100)
                    .append("NamaEvent", 100)
                    .append("TglEvent", 100)
                    .append("WaktuEvent", 100)
                    .append("KeteranganEvent", 100)
                    .append("LokasiEvent", 100);
            
            Document Group = new Document("NamaGrup", 100);
            
            Group.put("Event", Event);
            User.put("Group", Group);
            
            Document Chatting = new Document("idChatting", 100);
            Chatting.put("User", User);

            collection.insertOne(User);
            
            System.out.println("Berhasil");
            //this is how we query data
            /*MongoCollection<Document> collection = db.getCollection("User");
            List<Document> documents = (List<Document>) collection.find().into(
                    new ArrayList<Document>());
            documents.forEach((document) -> {
                System.out.println(document);
            });
            */
            
        } 
        catch (MongoException e) 
        {
            System.out.println(e);
        }
    }
    public void regis(RegisterData dataregister){
        try {
            MongoCollection<Document> collection = db.getCollection("Data");
            Document User = new Document("Username", dataregister.getUsername())
                    .append("Password", dataregister.getPassword())
                    .append("Nama", dataregister.getName())
                    .append("TglLahir", dataregister.getDob())
                    .append("Status", "xxx")
                    .append("ProfilePicture", "xxx");
            
            Document Event = new Document("NamaEvent", 100)
                    .append("NamaEvent", 100)
                    .append("TglEvent", 100)
                    .append("WaktuEvent", 100)
                    .append("KeteranganEvent", 100)
                    .append("LokasiEvent", 100);
            
            
            Document Group = new Document("NamaGrup", 100);
            
            Group.put("Event", Event);
            User.put("Group", Group);
            
            Document Chatting = new Document("idChatting", 100);
            Chatting.put("User", User);
            
            collection.insertOne(User);
            
            System.out.println("Berhasil");
        } catch (MongoException e) {
        }
    }
    public void cek_data(String username, String pass) {
        Document doc = new Document();
        doc.append("Username", username);
        doc.append("Password", pass);
        MongoCollection<Document> collection = db.getCollection("Data");
        List<Document> documents = (List<Document>) collection.find(doc)/*.projection(fields(include("size.w")))*/.into(
                        new ArrayList<Document>());

        int i = 0;
        Document obj;
        for (Document document : documents) {
            //System.out.println(document);
            obj = documents.get(i);
            i++;
            JOptionPane.showMessageDialog(parentComponent, "Welcome " + obj.getString("Username"));
            System.out.print("Welcome");
            System.out.println(obj.getString("Username"));
            usernow.setName(obj.getString("Nama"));
            usernow.setUsername(obj.getString("Username"));
            usernow.setStatus_message(obj.getString("Status"));
            //usernow.setImageBytes((byte[]) obj.get("ProfilePicture"));
            //usernow.setIcon(obj.get("ProfilePicture"));
            new LoginUI().setVisible(false);
            System.out.println(usernow.getName());
            ChattingMenu chatmenu = new ChattingMenu();
            chatmenu.setUsernow(usernow);
            try {
                ServerProgram server = new ServerProgram();
                server.setUsernow(usernow);
                ServerProgram.main(null);
            } catch (Exception ex) {
                Logger.getLogger(MongoDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            chatmenu.setVisible(true);
            
        }

        if (documents.isEmpty()) {
            JOptionPane.showMessageDialog(parentComponent, "Username/Password didn't match");
        }
    }

    public boolean isRegistered(String username) {
        MongoCollection<Document> collection = db.getCollection("Data");
        List<Document> documents = (List<Document>) collection.find()/*.projection(fields(include("size.w")))*/.into(
                        new ArrayList<Document>());
        ArrayList<String> terdaftar = new ArrayList<String>();;
        int i = 0;
        for (Document document : documents) {
            Document obj = documents.get(i);
            terdaftar.add(obj.getString("Username"));
            i++;
        }

        boolean cek = false;
        for (int j = 0; j < terdaftar.size(); j++) {
            if (username.equals(terdaftar.get(j))) {
                cek = true;
                break;
            }
        }

        if (cek == true) {
            System.out.println("return true");
            return true;
        } else {
            System.out.println("belum");
            return false;
        }
        
    }
    
    public boolean isFriendFound(String username)
    {
        MongoCollection<Document> collection = db.getCollection("Data");
        List<Document> documents = (List<Document>) collection.find()/*.projection(fields(include("size.w")))*/.into(
                        new ArrayList<Document>());
        ArrayList<String> terdaftar = new ArrayList<String>();;
        
        int i = 0;
        for (Document document : documents) {
            Document obj = documents.get(i);
            terdaftar.add(obj.getString("Username"));
            i++;
        }
        
        boolean cek = false;
        for (int j = 0; j < terdaftar.size(); j++) {
            if (username.equals(terdaftar.get(j))) {
                cek = true;
                break;
            }
        }
        
        if (cek == true)
        {    
            return true;
        }
        else
        {
            return false;
        }
    }
    public ArrayList getFriend(String username){
        MongoCollection<Document> collection = db.getCollection("Data");
        List<Document> documents = (List<Document>) collection.find(new Document("Username", username))/*.projection(fields(include("size.w")))*/.into(
                        new ArrayList<Document>());
        ArrayList<String> friends_username = null;
        
        int i=0;
        for(Document document : documents){
            Document obj = documents.get(i);
            System.out.println(obj);
            friends_username = (ArrayList<String>) obj.get("Friends");
            i++;
        }
        return friends_username;
    }
    public void AddFriend(String username, String addfriend)
    {
        System.out.println("ini akun dengan username : " + username);
        MongoCollection<Document> collection = db.getCollection("Data");
        List<Document> documents = (List<Document>) collection.find(new Document("Username", username))/*.projection(fields(include("size.w")))*/.into(
                        new ArrayList<Document>());
        
        ArrayList<String> friends_username = new ArrayList<>();
        int i=0;
            for (Document document : documents) {
                //Document obj = (Document) documents.get(i).get("size");
                Document obj = documents.get(i);
                if(obj.get("Friends") == null){
                    System.out.println("belum ada teman");
                    friends_username.add(addfriend);
                    
                    Bson filter = new Document("Username", username);
                    Bson newValue = new Document("Friends", friends_username);
                    Bson updateOperationDocument = new Document("$set", newValue);
                    collection.updateOne(filter, updateOperationDocument);
                }else{
                    friends_username = (ArrayList<String>) obj.get("Friends");
                    friends_username.add(addfriend);
                    
                    Bson filter = new Document("Username", username);
                    Bson newValue = new Document("Friends", friends_username);
                    Bson updateOperationDocument = new Document("$set", newValue);
                    collection.updateOne(filter, updateOperationDocument);
                }
                i++;
            }
            
        for(int k=0; k<friends_username.size(); k++){
            System.out.println(friends_username.get(k));
        }
            
    }
    private Document doc;
    public Document getDoc(String username){
        MongoCollection<Document> collection = db.getCollection("Data");
        List<Document> documents = (List<Document>) collection.find(new Document("Username", username))/*.projection(fields(include("size.w")))*/.into(
                        new ArrayList<Document>());
        int i = 0;
        for (Document document : documents) {
            Document obj = documents.get(i);
            if(obj.getString("Username").equals(username)){
                doc = obj;
            }
            i++;
        }
        return doc;
    }
    
    public String getUsername(String username)
    {
        System.out.println("Masuk");
        Document doc = new Document();
        doc.append("Username", username);
        MongoCollection<Document> collection = db.getCollection("Data");
        List<Document> documents = (List<Document>) collection.find(doc)/*.projection(fields(include("size.w")))*/.into(
                        new ArrayList<Document>());

        int i = 0;
        Document obj;
        for (Document document : documents) {
            //System.out.println(document);
            obj = documents.get(i);
            i++;
            System.out.println("Masuk");
            username = obj.getString("Username");
            System.out.println(username);
        }
        return username;
    }
    public String editName(String name, String username, String usernow){
        MongoCollection<Document> collection = db.getCollection("Data");
        List<Document> documents = (List<Document>) collection.find(new Document("Username", username))/*.projection(fields(include("size.w")))*/.into(
                        new ArrayList<Document>());
        
        int i = 0;
        for (Document document : documents) {
            Document obj = documents.get(i);
            if(obj.getString("Username").equals(username)){
                usernow = name;
                Bson newValue = new Document("Nama", name);
                Bson filter = obj;
                Bson update = new Document("$set", newValue);
                collection.updateOne(filter, update);
            }
        }
        return usernow;
    }
    
    public String editStatus(String status, String username, String statusnow)
    {
        MongoCollection<Document> collection = db.getCollection("Data");
        List<Document> documents = (List<Document>) collection.find(new Document("Username", username))/*.projection(fields(include("size.w")))*/.into(
                        new ArrayList<Document>());
        
        int i = 0;
        for (Document document : documents) {
            Document obj = documents.get(i);
            if(obj.getString("Username").equals(username)){
                statusnow = status;
                Bson newValue = new Document("Status", status);
                Bson filter = obj;
                Bson update = new Document("$set", newValue);
                collection.updateOne(filter, update);
            }
        }
        return status;
    }
    private void append(String friends, ArrayList<String> friends0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setImage(byte[] imageBytes)
    {
        
        GridFS fs = new GridFS((DB) db);
        GridFSInputFile in = fs.createFile(imageBytes);
        in.save();
    }
}
