import java.awt.*;
import javax.swing.*;

/**
 * JeuDeLaVieUI.java
 * Représente l'interface utilisateur du jeu de la vie.
 * Elle hérite de la classe JPanel et implémente l'interface Observateur.
 * @see Observateur
 * @see JeuDeLaVie
 */
public class JeuDeLaVieUI extends JPanel implements Observateur{
    private JeuDeLaVie jeu;

    /**
     * Constructeur de l'interface utilisateur
     * @param jeu : le jeu de la vie
     */
    public JeuDeLaVieUI(JeuDeLaVie jeu){
        this.jeu=jeu;
        this.jeu.attacheObservateur(this);
        
    }

    /**
     * Actualise l'interface utilisateur
     * Elle est appelée par le jeu de la vie lorsqu'il y a un changement d'état.
     */
    @Override
    public void actualise(){
        repaint();
    }

    /**
     * Dessine l'interface utilisateur
     * Elle dessine les cellules vivantes et mortes.
     * @param g : le contexte graphique
     * @see Graphics
     */
    public void paint(Graphics g){
        super.paint(g);
        int cellsize=6;
        for(int x=0;x<jeu.getXmax();x++){
            for(int y=0;y<jeu.getYmax();y++){
                Cellule c = jeu.getGrille(x, y);
                if(c!=null){
                    if(jeu.getGrille(x, y).estVivante()){
                        g.setColor(Color.WHITE);
                    }
                    else{
                        g.setColor(Color.BLACK);
                    }
                    g.fillRect(x*cellsize, y*cellsize, cellsize, cellsize);
                }
                
            }
        }
    }
}
