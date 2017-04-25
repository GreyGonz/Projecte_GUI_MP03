/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.JOptionPane;

/**
 * 
 * @author alumne
 */
public class Excepcio extends Exception{
    
    private int opcio;
    
    /**
     * Llança un missatge d'error depenent del paràmetre "opcio" i "message", que li envia la funció
     * "dadesCorrectes()" dins el fitxer "GUI.java"
     * @param opcio Tipus d'error
     * @param message Missatge d'error, utilitzat per mostrar la casella exacta on s'ha detectat l'error
     */
    public Excepcio(int opcio, String message) {
        super(message);
        this.opcio = opcio;
        switch (opcio) {
            case 0: JOptionPane.showMessageDialog(null, "Error0: Casella buida a \"" + message + "\"", "Introudueix un valor vàlid.", JOptionPane.WARNING_MESSAGE);
                    break;
            case 1: JOptionPane.showMessageDialog(null, "Error1: Valor invàlid a la cassella \"Joc\". Introdueix un valor entre \"1\" i \"3\".", "Introudueix un valor vàlid.", JOptionPane.WARNING_MESSAGE); 
                    break;
            case 2: JOptionPane.showMessageDialog(null, "Error2: Valor invàlid a la cassella \"Tipus Atac\". Els valors acceptats són:\n - \"m\" (màgic) \n - \"c\" (cos a cos) \n - \"a\" (armes a distància)", "Introudueix un valor vàlid.", JOptionPane.WARNING_MESSAGE); 
                    break;
            case 3: JOptionPane.showMessageDialog(null, "Error3: Valor invàlid a la cassella \"Tamany\". Introdueix un valor numéric.", "Introudueix un valor vàlid.", JOptionPane.WARNING_MESSAGE); 
                    break;
        }
        
    }
    
    
    
}
