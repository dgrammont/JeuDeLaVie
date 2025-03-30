/**
 * Interface CelluleEtat
 */

public interface CelluleEtat{
    /*
     * mets l'état de la cellule en vie
     */
    public CelluleEtat vit();

    /*
     * mets l'état de la cellule en mort
     */
    public CelluleEtat meurt();

    /*
     * dit si la cellule est vivante ou non
     * Renvoie un boolean
     */
    public boolean estVivante();

    /*
     * appelle la methode accpete de leurs etat
     */
    public void accepte(Visiteur visiteur,Cellule cellule);

    

}