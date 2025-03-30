/**
 * Classe Cellule
 * Représente une cellule du jeu de la vie
 * Elle contient un état (vivante ou morte) et ses coordonnées  
 */

public class Cellule{
    private CelluleEtat etat;
    private int x,y;
    
    /**
     * Constructeur de la cellule
     * @param x : coordonnée x de la cellule
     * @param y : coordonnée y de la cellule
     * @param etat : état de la cellule (vivante ou morte)
     * @see CelluleEtat
     */
    public Cellule(int x, int y, CelluleEtat etat){
        this.x = x;
        this.y = y;
        this.etat = etat;
    }

    /**
     * mets l'état de la cellule en vie
     */
    public void vit(){
        etat = etat.vit();
    }

    /**
     * mets l'état de la cellule en mort
     */
    public void meurt(){
        etat = etat.meurt();
    }

    /**
     * dit si la cellule est vivante ou non
     * @return : true si la cellule est vivante, false sinon
     */
    public boolean estVivante(){
        return this.etat.estVivante();
    }

    /**
     * dit le nombre de voisines d'un cellues
     * @param jeu : le jeu de la vie
     * @return : le nombre de voisines vivantes
     * */
    public int nombreVoisinesVivantes(JeuDeLaVie jeu){
        int nbVoisin = 0;
        for(int i=x-1;i<=x+1;i++){
            for(int j=y-1;j<=y+1;j++){
                if(i>=0 && i< jeu.xMax && j>=0 && j <jeu.yMax && !(i==x && j ==y) && jeu.getGrille(i, j).estVivante()){
                    nbVoisin++;
                }
            }   
        }
        return nbVoisin;
    }

    /**
     * Ajouter un visiteur
     * @param visiteur : le visiteur
     */
    public void accepte(Visiteur visiteur){
        etat.accepte(visiteur, this);
    }

}   