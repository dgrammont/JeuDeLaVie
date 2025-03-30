
/**
 * Classe CelluleEtatMort
 * Représente l'état d'une cellule morte
   * 
 */


public class CelluleEtatMort implements CelluleEtat{
    private static CelluleEtatMort instance = null;
    private CelluleEtatMort(){}

    /**
     * Constructeur de la cellule morte
     * @return : une cellule morte
     */
    public static CelluleEtatMort getInstance(){
        if(instance==null){
            instance = new CelluleEtatMort();
        }
        return instance;
    }
    /**
     * mets l'état de la cellule en vie
     * @return : une cellule vivante
     */
    @Override
    public CelluleEtat vit(){
        return CelluleEtatVivant.getInstance();
    }

    /**
     * mets l'état de la cellule en mort
     * @return : une cellule morte
     */
    @Override
    public CelluleEtat meurt(){
        return this;
    }

    /**
     * dit si la cellule est vivante ou non
     * @return : false
     */
    @Override
    public boolean estVivante(){
        return false;
    }

    /**
     * Ajouter un visiteur
     * @param visiteur : le visiteur
     * @param cellule : la cellule
     */
    @Override
    public void accepte(Visiteur visiteur,Cellule cellule){
        visiteur.visiteCelluleMorte(cellule);
    }
}