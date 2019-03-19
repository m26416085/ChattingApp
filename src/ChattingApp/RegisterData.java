/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChattingApp;

import java.awt.Image;
import javax.swing.ImageIcon;
import org.bson.Document;

/**
 *
 * @author Jeffrey
 */
public class RegisterData {
    private String username;
    private String password;
    private String dob;
    private String name;
    private String status_message;
    private Document doc;
    private byte[]imageBytes;
    private ImageIcon icon;

    public void setDoc(Document doc) {
        this.doc = doc;
    }
    
    public RegisterData()
    {}

    public RegisterData(String username, String password, String dob, String name, String status_message, Document doc, byte[]imageBytes, ImageIcon icon) {
        this.username = username;
        this.password = password;
        this.dob = dob;
        this.name = name;
        this.status_message = status_message;
        this.doc = doc;
        this.imageBytes = imageBytes;
        this.icon = icon;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public Document getDoc() {
        return doc;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }
    
    public String getStatus_message()
    {
        return status_message;
    }
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
    
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RegisterData{" + "username=" + username + ", password=" + password + ", dob=" + dob + '}';
    }
    
}
