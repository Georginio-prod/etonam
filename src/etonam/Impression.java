
package etonam;

import java.text.MessageFormat;
import javax.swing.*;


public class Impression {
    public static void imprimerJtable(JTable jt, String titre){
        MessageFormat entete = new MessageFormat(titre);
        MessageFormat bas = new MessageFormat("Page{ 1, number, integer}");
        
        try{
           jt.print(JTable.PrintMode.FIT_WIDTH, entete, bas); 
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error :\n"+e, "Impression", JOptionPane.ERROR_MESSAGE);
        }
    }
}
