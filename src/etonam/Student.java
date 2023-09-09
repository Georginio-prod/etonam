/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etonam;

import db.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author etonam
 */
public class Student 
{    
    Connection con;
    PreparedStatement ps;
    
    public Student()
    {
        try {
            con = new MyConnection().getConnection();
        } catch (SQLException  | ClassNotFoundException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // fonction d'incrémentation de l'id
    public int getMax(){
        int id = 1;
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("Select max(id) from students");
            while(rs.next()){
                id = rs.getInt(1);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id +1;    
    }
    
    //les information a enrégistré dans la base de donnée
    
    public void insert(int id, String imagePath, String nom,String prenoms, String data, String sexe, String tel, 
                 String email,String nom_du_pere_tuteur, String telephone, String nom_de_la_mere_tutrice,String  tele, String semestre, String parcours, String institue, String nationalite){
        
        String sql = "insert into students values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, imagePath);
            ps.setString(3, nom);
            ps.setString(4, prenoms);
            ps.setString(5, data);
            ps.setString(6, sexe);
            ps.setString(7, tel);
            ps.setString(8, email);
            ps.setString(9,  nom_du_pere_tuteur);
            ps.setString(10, telephone);
            ps.setString(11, nom_de_la_mere_tutrice );
            ps.setString(12, tele);
            ps.setString(13, semestre);
            ps.setString(14, parcours);
            ps.setString(15, institue);
            ps.setString(16, nationalite);
            
            
            if (ps.executeUpdate()>0){
                JOptionPane.showMessageDialog(null, "Enrégistrement réussi ");
            }
        } catch (SQLException ex) {
            System.err.println(ex);
                        System.err.println(ex.getCause());
            System.err.println(ex.getLocalizedMessage());

        // code de modification
        
        }
        
        
    }
        
    // vérifier l'email de l'étudiant 
    public boolean isEmailExit(String email){
        try {
            ps = con.prepareStatement(" select email from students where email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    // vérifier l'email de l'étudiant 
     public boolean isNumberExit(String number){
        try {
            ps = con.prepareStatement("select tel from students where tel = ?");
            ps.setString(1, number);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
     
     //les valeur a retrouvé dans le tableau 
     public void getStudentValue(JTable table, String searchValue){
         String sql = "select * from students where concat(id, first_name, last_name, semestre, institut) like ? "; 
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%"+searchValue+"%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            Object[] row;
            while(rs.next()){
                row = new Object[5];
                row[0] = rs.getInt("id");
                row[1] = rs.getString("first_name");
                row[2] = rs.getString("last_name");
                row[3] = rs.getString("semestre");
                row[4] = rs.getString("institut");
                model.addRow(row);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
     //LA NOUVELLE FONCTION de recherche
     public DefaultTableModel getStudent(String searchableValue)
     {
        Object[] obj = {"Numéro matricule", "Nom", "Prenom", "Semestre", "Institue"};
        int i = 0;
        DefaultTableModel model = new DefaultTableModel(obj, 0);
        Object[] row;
        String selectQuery = "SELECT * FROM students WHERE last_name LIKE ? OR first_name LIKE ?";
        try
        {
            ps = con.prepareStatement(selectQuery);
            ps.setString(1, "%" + searchableValue + "%");
            ps.setString(2, "%" + searchableValue + "%");
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                row = new Object[5];
                row[0] = rs.getInt("id");
                row[1] = rs.getString("first_name");
                row[2] = rs.getString("last_name");
                row[3] = rs.getString("semestre");
                row[4] = rs.getString("institut");
                model.addRow(row);
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return model;
     }
     // Code de la nom répétition des id
 public boolean isIdExit(int id){
        try {
            ps = con.prepareStatement("select id from students where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
 //code de suppression 
 public void delete(int id){
     int yesOrNO = JOptionPane.showConfirmDialog(null, "Êtes vous sûre de vouloir supprimez cette colone ? ","Student Delete", JOptionPane.OK_CANCEL_OPTION,0);
     if(yesOrNO == JOptionPane.OK_OPTION){
         try {
             ps = con.prepareStatement("delete from students where id = ?");
             ps.setInt(1, id);
             if(ps.executeUpdate()>0){
                 JOptionPane.showMessageDialog(null, " Suppression réussi ");
                 
             }
         } catch (SQLException ex) {
             Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
 }

   
 
}
