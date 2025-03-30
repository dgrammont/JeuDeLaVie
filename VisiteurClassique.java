/**
 * VisiteurClassique.java
 * Représente un visiteur classique pour le jeu de la vie.
 * Il hérite de la classe Visiteur.
 * Cette classe est utilisée pour appliquer les règles classiques du jeu de la vie sur les cellules.
 */
public class VisiteurClassique extends Visiteur {

    /**
     * Constructeur de la classe VisiteurClassique.
     * @param jeu : le jeu de la vie
    */
    public VisiteurClassique(JeuDeLaVie jeu){
        super(jeu);
    }

    /**
     * Méthode pour visiter une cellule vivante.
     * Elle applique les règles classiques du jeu de la vie sur la cellule.
     * @param cellule : la cellule vivante à visiter
     */
    @Override
    public void visiteCelluleVivante(Cellule cellule){
        int nbVoisinsVivants = cellule.nombreVoisinesVivantes(jeu);
        if(nbVoisinsVivants < 2 || nbVoisinsVivants > 3){
            jeu.ajouteCommande(new CommandeMeurt(cellule));
        }

    }

    /**
     * Méthode pour visiter une cellule morte.
     * Elle applique les règles classiques du jeu de la vie sur la cellule.
     * @param cellule : la cellule morte à visiter
     */
    @Override
    public void visiteCelluleMorte(Cellule cellule){
        int nbVoisinsVivants = cellule.nombreVoisinesVivantes(jeu);
        if(nbVoisinsVivants == 3){
            jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }
}
