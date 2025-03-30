
/**
 * Classe abstraite représentant un visiteur pour le jeu de la vie.
 * Cette classe est utilisée pour appliquer des opérations sur les cellules vivantes et mortes.
 */
public abstract class Visiteur {
    protected JeuDeLaVie jeu;

    /**
     * Constructeur de la classe Visiteur.
     * @param jeu : le jeu de la vie
     */
    public Visiteur(JeuDeLaVie jeu){
        this.jeu=jeu;
    }
    /**
     * Méthode abstraite pour visiter une cellule vivante.
     * @param cellule : la cellule vivante à visiter
     */
    public abstract void visiteCelluleVivante(Cellule cellule);

    /**
     * Méthode abstraite pour visiter une cellule morte.
     * @param cellule : la cellule morte à visiter
     */
    public abstract void visiteCelluleMorte(Cellule cellule);
}
