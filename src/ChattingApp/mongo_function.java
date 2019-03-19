/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChattingApp;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.bson.Document;

/**
 *
 * @author Acer
 */
public class mongo_function {
    private MongoDatabase db;
    private Component parentComponent;
    public mongo_function() {
        try {
            String url;
            url = "mongodb://admin:admin098)(*@ds042677.mlab.com:42677/proyek_java";
            MongoClient mongoClient = new MongoClient(new MongoClientURI(url));
            db = mongoClient.getDatabase("proyek_java");
            System.out.println("Connected to the database successfully");

        } catch (MongoException e) {

        }
    }
    
    public void cek_data(String username, String pass){
        Document doc = new Document();
        doc.append("Username", username);
        doc.append("Password", pass);
        MongoCollection<Document> collection = db.getCollection("Data");
            List<Document> documents = (List<Document>) collection.find(doc)/*.projection(fields(include("size.w")))*/.into(
                    new ArrayList<Document>());
            
            int i=0;
            Document obj;
            for (Document document : documents) {
                //System.out.println(document);
                obj = documents.get(i);
                i++;
                
                JOptionPane.showMessageDialog(parentComponent, "Welcome " + obj.getString("Username"));
                System.out.print("Welcome");
                System.out.println(obj.getString("Username"));
                
            }
            
            if (documents.isEmpty())
            {
                JOptionPane.showMessageDialog(parentComponent, "Username/Password didn't match");
            }            
    }
    
    public boolean isRegistered(String username){
        MongoCollection<Document> collection = db.getCollection("Data");
        List<Document> documents = (List<Document>) collection.find()/*.projection(fields(include("size.w")))*/.into(
                        new ArrayList<Document>());
        ArrayList<String> terdaftar = new ArrayList<String>();;
        int i=0;
        for (Document document : documents) {
            Document obj = documents.get(i);
            terdaftar.add(obj.getString("Username"));
            i++;
        }
        
        boolean cek = false;
        for(int j=0; j<terdaftar.size(); j++){
            if(username.equals(terdaftar.get(j))){
                cek = true; break;
            }
        }
        
        if (cek) {
            System.out.println("username registered");
            return true;
        } else {
            System.out.println("belum");
            return false;
        }
        
    }
}
