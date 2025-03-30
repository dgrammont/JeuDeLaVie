/**
 * VisiteurHighLife.java
 * Représente un visiteur pour le jeu de la vie qui applique les règles du jeu de la vie "HighLife".
 * Il hérite de la classe Visiteur.
 */
public class VisiteurHighLife  extends Visiteur {
    /**
     * Constructeur de la classe VisiteurHighLife.
     * @param jeu : le jeu de la vie
     */
    public VisiteurHighLife(JeuDeLaVie jeu){
        super(jeu);
    }

    /**
     * Méthode pour visiter une cellule vivante.
     * Elle applique les règles du jeu de la vie "HighLife" sur la cellule.
     * @param cellule : la cellule vivante à visiter
     */
    public void visiteCelluleVivante(Cellule cellule){
        int nbVoisinsVivants = cellule.nombreVoisinesVivantes(jeu);
        if(!(nbVoisinsVivants == 2 || nbVoisinsVivants == 3 )){
            jeu.ajouteCommande(new CommandeMeurt(cellule));
        }

    }

    /**
     * Méthode pour visiter une cellule morte.
     * Elle applique les règles du jeu de la vie "HighLife" sur la cellule.
     * @param cellule : la cellule morte à visiter
     */
    public void visiteCelluleMorte(Cellule cellule){
        int nbVoisinsVivants = cellule.nombreVoisinesVivantes(jeu);
        if(nbVoisinsVivants == 3 || nbVoisinsVivants == 6 ){
            jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }
}
