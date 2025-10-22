/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources.img;

import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.*;

/**
 *
 * @author matia
 */
public class ImagenMenu extends JPanel {
    private Image imagen;
    
    public ImagenMenu(){
        try {
            imagen = new ImageIcon(getClass().getResource("/resources/img/fondo_Verde_degrado_menu.jpg")).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(imagen !=null){
            g.drawImage(imagen, 0, 0,getWidth(),getHeight(),this); // Escala la imagen al tamaño del panel
        }
    }
}
