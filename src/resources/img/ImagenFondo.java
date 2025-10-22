/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources.img;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;

/**
 *
 * @author matia
 */
public class ImagenFondo extends JDesktopPane {
    private Image imagen;

    public ImagenFondo() {
        // Carga la imagen desde la carpeta "img" (dentro de Source Packages)
        imagen = new ImageIcon(getClass().getResource("/resources/img/fondo_flores.png")).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            // Dibuja la imagen escalada al tamaño actual del panel
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
