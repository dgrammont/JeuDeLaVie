/**
 * Classe abstraite Commande
 * Représente une commande à exécuter sur une cellule
 */

 
public abstract class Commande {
    protected Cellule cellule;

    public abstract void executer();
}
