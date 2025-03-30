/**
 * Classe CelluleEtatVivant
 * Représente l'état d'une cellule vivante
 */

 
public class CelluleEtatVivant implements CelluleEtat{
    private static CelluleEtatVivant instance = null;
    private CelluleEtatVivant(){}

    /**
     * Constructeur de la cellule vivante
     * @return : une cellule vivante
     */
    public static CelluleEtatVivant getInstance(){
        if(instance==null){
            instance = new CelluleEtatVivant();
        }
        return instance;
    }

    /**
     * mets l'état de la cellule en vie
     * @return : une cellule vivante
     */
    @Override
    public CelluleEtat vit(){
        return this;
    }

    /**
     * mets l'état de la cellule en mort
     * @return : une cellule morte
     */
    @Override
    public CelluleEtat meurt(){
        return CelluleEtatMort.getInstance();
    }
    /**
     * dit si la cellule est vivante ou non
     * @return : true
     */
    @Override
    public boolean estVivante(){
        return true;
    }

    /**
     * Ajouter un visiteur
     * @param visiteur : le visiteur
     * @param cellule : la cellule
     */
    @Override
    public void accepte(Visiteur visiteur,Cellule cellule){
        visiteur.visiteCelluleVivante(cellule);
    }

}