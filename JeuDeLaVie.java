
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.lang.Math;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
/**
 * Classe principale du Jeu de la Vie.
 * Elle gère la grille de cellules, les générations, et les observateurs.
 * Elle permet également de sauvegarder la configuration dans un fichier.
 * @version 1.0
 */

 
public class JeuDeLaVie implements Observable {
    int val = 100;

    int nbGenerations=0;
    private Cellule[][] grille;
    int xMax,yMax;
    private ArrayList<Observateur> observateurs;
    private ArrayList<Commande> commandes;
    private Visiteur visiteur;

    /**
     * Constructeur de la classe JeuDeLaVie
     * @param xMax : largeur de la grille
     * @param yMax : hauteur de la grille
     */
    public JeuDeLaVie(int xMax, int yMax ){
        this.xMax=xMax;
        this.yMax=yMax;
        this.grille= new Cellule[xMax][yMax];
        this.observateurs = new ArrayList<>(); 
        this.commandes = new ArrayList<>();
    }

    
    /**
     * Sauvegarde la configuration de la grille dans un fichier texte
     * @param cheminFichier : le chemin du fichier
     * @throws IOException : si une erreur d'entrée/sortie se produit
     */
    public void sauvegarderConfiguration(String cheminFichier) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cheminFichier))) {
            for (int y = 0; y < yMax; y++) {
                for (int x = 0; x < xMax; x++) {
                    writer.write(grille[x][y].estVivante() ? '1' : '0');
                }
                writer.newLine();
            }
        }
    }   

    /**
     * Initialise la grille avec des cellules vivantes ou mortes
     * @param fichier : le nom du fichier contenant la configuration
     */
    public void initialiseGrille(final String fichier) {
        if (fichier == null) {
            for(int x=0; x<xMax; x++) {
                for(int y=0; y<yMax; y++) {
                    if(Math.random() < 0.5) {
                        this.grille[x][y] = new Cellule(x, y, CelluleEtatVivant.getInstance());
                    } else {
                        this.grille[x][y] = new Cellule(x, y, CelluleEtatMort.getInstance());
                    }
                }
            }
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
                String line;
                int y = 0;
                
                for (int x = 0; x < xMax; x++) {
                    for (int yy = 0; yy < yMax; yy++) {
                        this.grille[x][yy] = new Cellule(x, yy, CelluleEtatMort.getInstance());
                    }
                }
                
                while ((line = reader.readLine()) != null && y < yMax) {
                    line = line.trim();
                    for (int x = 0; x < Math.min(line.length(), xMax); x++) {
                        char c = line.charAt(x);
                        if (c == '1') {
                            this.grille[x][y] = new Cellule(x, y, CelluleEtatVivant.getInstance());
                        }
                    }
                    y++;
                }
            } catch (IOException e) {
                System.err.println("Erreur lors de la lecture du fichier: " + e.getMessage());
                initialiseGrille(null);
            }
        }
    }
    /**
     * Retourne la largeur de la grille
     * @return : la largeur de la grille
     */
    public int getXmax(){
        return this.xMax;
    }
    /**
     * Retourne la hauteur de la grille
     * @return : la hauteur de la grille
     */
    public int getYmax(){
        return this.yMax;
    }
    /**
     * Retourne la cellule à la position (x,y)
     * @param x : coordonnée x de la cellule
     * @param y : coordonnée y de la cellule
     * @return : la cellule à la position (x,y)
     */
    public Cellule getGrille(int x, int y){
        return this.grille[x][y];
    }
    
    /**
     * permet d'ajouter un observateur
     * @param o : l'observateur à ajouter
     */
    @Override
    public void attacheObservateur(Observateur o) {
        this.observateurs.add(o);
    }
    /**
     * permet de retirer un observateur
     * @param o : l'observateur à retirer
     */
    @Override
    public void detacheObservateur(Observateur o) {
        this.observateurs.remove(o);
    }
    /**
     * notifie les observateurs
     */
    @Override
    public void notifieObservateurs(){
        this.observateurs.forEach(o -> o.actualise());
    }
    /**
     * Ajoute une commande à la liste des commandes
     * @param c : la commande à ajouter
     */
    public void ajouteCommande(Commande c){
        this.commandes.add(c);
    }
    /**
     * Exécute toutes les commandes de la liste
     */
    public void executeCommandes(){
        this.commandes.forEach(c -> c.executer());
    }

    /**
     * Distribue un visiteur à toutes les cellules de la grille
     * @param mode : le mode du visiteur (0 : Classique, 1 : DayNight, 2 : HighLife)
     */
    public void distribueVisiteur(int mode){
        switch (mode) {
            case 0:
                this.visiteur= new VisiteurClassique(this);

                break;
            case 1:
                this.visiteur= new VisiteurDayNight(this);
                break;
            case 2:
                this.visiteur= new VisiteurHighLife(this);
                break;
            default:
                break;
        }
        for(int x=0;x<xMax;x++){
            for(int y=0;y<yMax;y++){
                this.grille[x][y].accepte(this.visiteur);
            }
        }
    }
    /**
     * Charge la configuration de la grille depuis un fichier texte
     * @param cheminFichier : le chemin du fichier
     * @throws IOException : si une erreur d'entrée/sortie se produit
     */
    public static void sauvegarderConfiguration(String cheminFichier, JeuDeLaVie jeu) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(cheminFichier))) {
        for (int y = 0; y < jeu.getYmax(); y++) {
            for (int x = 0; x < jeu.getXmax(); x++) {
                writer.write(jeu.getGrille(x, y).estVivante() ? '1' : '0');
            }
            writer.newLine();
        }
    }
}

    
    public void calculerGenerationSuivante(){
        nbGenerations++;
        for(int x=0;x<xMax;x++){
            for(int y=0;y<yMax;y++){
                int nbVoisin = this.grille[x][y].nombreVoisinesVivantes(this);
                if(this.grille[x][y].estVivante()){
                    if(nbVoisin<2 || nbVoisin>3){
                        this.ajouteCommande(new CommandeMeurt(this.grille[x][y]));
                    }
                }
            }
        }
    }

    
        /**
         * Méthode principale pour lancer le jeu de la vie.
         * @param mode
         * @param xMax
         * @param yMax
         * @param ficher
         */
        public static void jeuDeLaVie(int mode,int xMax, int yMax,String ficher){
            
            JFrame fenetre = new JFrame("Jeu de la vie DayNight");
            fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            fenetre.setSize(xMax, yMax);
    
            final JeuDeLaVie jeu = new JeuDeLaVie(100, 100);
            final JeuDeLaVieUI ui = new JeuDeLaVieUI(jeu);
    
            JTextField generation = new JTextField("nb Generations : " + jeu.nbGenerations, 15);
            JPanel panelBas = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            panelBas.add(generation);
            
            JButton pauseButton = new JButton("Pause");
            panelBas.add(pauseButton);
            
            JButton stepButton = new JButton("Avancer");
            panelBas.add(stepButton);

            JButton saveButton = new JButton("Sauvegarder");
            panelBas.add(saveButton);

            fenetre.add(panelBas, BorderLayout.SOUTH);
            
            JSlider slider = new JSlider(0, 1000, 100);
            slider.setMajorTickSpacing(100);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
    
            JLabel sliderLabel = new JLabel("Vitesse : " + slider.getValue(), SwingConstants.CENTER);
    
            JPanel panelHaut = new JPanel(new BorderLayout());
            panelHaut.add(sliderLabel, BorderLayout.NORTH);
            panelHaut.add(slider, BorderLayout.CENTER);
    
            fenetre.add(panelHaut, BorderLayout.NORTH);
                saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Fichiers texte (*.txt)", "txt"));

                    int returnValue = fileChooser.showSaveDialog(fenetre);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        String chemin = fileChooser.getSelectedFile().getAbsolutePath();
                        if (!chemin.endsWith(".txt")) {
                            chemin += ".txt";
                        }

                        try {
                            jeu.sauvegarderConfiguration(chemin);
                            JOptionPane.showMessageDialog(fenetre, 
                                "Configuration sauvegardée avec succès!", 
                                "Sauvegarde", 
                                JOptionPane.INFORMATION_MESSAGE);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(fenetre, 
                                "Erreur lors de la sauvegarde: " + ex.getMessage(), 
                                "Erreur", 
                                JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });
            jeu.initialiseGrille(ficher);
            fenetre.add(ui);
            fenetre.setVisible(true);
            jeu.notifieObservateurs();
    
            switch (mode) {
                case 0:            
                    fenetre.setTitle("Classique");
                    break;
                case 1:            
                    fenetre.setTitle("DayNight");
                    break;
                case 2:            
                    fenetre.setTitle("HightLife");
                    break;
                default:
                    break;
            }
            
            Timer timer = new Timer(100, e -> {
                jeu.distribueVisiteur(mode);
                jeu.calculerGenerationSuivante();            
                generation.setText("nb Generations : "+jeu.nbGenerations);
                jeu.executeCommandes();
                jeu.notifieObservateurs();
            });
            timer.start();
    
            slider.addChangeListener(e -> timer.setDelay(slider.getValue()));
            
            pauseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(timer.isRunning()) {
                        timer.stop();
                        pauseButton.setText("Reprendre");
                    } else {
                        timer.start();
                        pauseButton.setText("Pause");
                    }
                }
            });
            
            stepButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean wasRunning = timer.isRunning();
                    if(wasRunning) {
                        timer.stop();
                        pauseButton.setText("Reprendre");
                    }
                    
                    jeu.distribueVisiteur(mode);
                    jeu.calculerGenerationSuivante();            
                    generation.setText("nb Generations : "+jeu.nbGenerations);
                    jeu.executeCommandes();
                    jeu.notifieObservateurs();
                    
                }
            });
        }
    }
