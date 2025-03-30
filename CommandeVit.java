/**
 * CommandeVit.java
 * Représente une commande qui fait vivre une cellule.
 * Elle hérite de la classe Commande.
 */

 
public class CommandeVit extends Commande{

    /**
     * Constructeur de la commandeVit
     * @param c une cellule
     */
    public CommandeVit(Cellule c){
        this.cellule = c;
    }

    /**
     * exécute la commande
     * Elle fait vivre la cellule.
     * 
     */
    @Override
    public void executer(){
        this.cellule.vit();
    }
}
