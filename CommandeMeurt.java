/**
 * CommandeMeurt.java
 * Représente une commande qui fait mourir une cellule.
 * Elle hérite de la classe Commande.
 */

 
public class CommandeMeurt extends Commande{
    
    /**
     * Constructeur de la commandeMeurt
     * @param c une cellule
     */
    public CommandeMeurt(Cellule c){
        this.cellule = c;
    }

    /**
     * exécute la commande
     * Elle fait mourir la cellule.
     * 
     */
    @Override
    public void executer(){
        this.cellule.meurt();
    }
}
