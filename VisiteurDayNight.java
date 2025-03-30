/**
 * VisiteurDayNight.java
 * Représente un visiteur pour le jeu de la vie qui applique les règles du jeu de la vie "Day and Night".
 * Il hérite de la classe Visiteur.
 */
public class VisiteurDayNight extends Visiteur{
    /**
     * Constructeur de la classe VisiteurDayNight.
     * @param jeu : le jeu de la vie
     */
    public VisiteurDayNight(JeuDeLaVie jeu){
        super(jeu);
    }

    /**
     * Méthode pour visiter une cellule vivante.
     * Elle applique les règles du jeu de la vie "Day and Night" sur la cellule.
     * @param cellule : la cellule vivante à visiter
     */
    @Override
    public void visiteCelluleVivante(Cellule cellule){
        int nbVoisinsVivants = cellule.nombreVoisinesVivantes(jeu);
        if(!(nbVoisinsVivants <3 || nbVoisinsVivants == 5 )){
            jeu.ajouteCommande(new CommandeMeurt(cellule));
        }

    }

    /**
     * Méthode pour visiter une cellule morte.
     * Elle applique les règles du jeu de la vie "Day and Night" sur la cellule.
     * @param cellule : la cellule morte à visiter
     */
    @Override
    public void visiteCelluleMorte(Cellule cellule){
        int nbVoisinsVivants = cellule.nombreVoisinesVivantes(jeu);
        if(nbVoisinsVivants == 3 || nbVoisinsVivants > 5 ){
            jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }
}
