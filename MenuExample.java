import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;

/**
 * MenuExample.java
 * Représente un menu avec des boutons pour lancer différents modes du jeu de la vie.
 * Il permet également de charger et de sauvegarder des fichiers de configuration.
 */
public class MenuExample extends JFrame {
    private JTextField widthField;
    private JTextField heightField;
    private final String[] cheminFichierHolder = {null};
    
    /**
     * Constructeur de la classe MenuExample.
     * Il initialise la fenêtre, les champs de texte et les boutons.
     */
    public MenuExample() {
        setTitle("Menu avec Boutons");
        setSize(400, 400); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Fichier");
        JMenuItem quitterItem = new JMenuItem("Quitter");
        JMenuItem chargerFichier = new JMenuItem("Charger");
        quitterItem.addActionListener(e -> System.exit(0));


        JMenuItem sauvegarderFichier = new JMenuItem("Sauvegarder");
        sauvegarderFichier.addActionListener(e -> {
            try {
                int width = Integer.parseInt(widthField.getText());
                int height = Integer.parseInt(heightField.getText());
                JeuDeLaVie jeuTemp = new JeuDeLaVie(width, height);

                if (cheminFichierHolder[0] != null) {
                    jeuTemp.initialiseGrille(cheminFichierHolder[0]);
                } else {
                    jeuTemp.initialiseGrille(null);
                }
            
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Fichiers texte (*.txt)", "txt"));

                int returnValue = fileChooser.showSaveDialog(MenuExample.this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String chemin = fileChooser.getSelectedFile().getAbsolutePath();
                    if (!chemin.endsWith(".txt")) {
                        chemin += ".txt";
                    }

                    JeuDeLaVie.sauvegarderConfiguration(chemin, jeuTemp);
                    JOptionPane.showMessageDialog(MenuExample.this,
                        "Configuration sauvegardée avec succès!",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(MenuExample.this,
                    "Veuillez entrer des dimensions valides",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(MenuExample.this,
                    "Erreur lors de la sauvegarde: " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            }
        });


        fileMenu.add(sauvegarderFichier);
        chargerFichier.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Fichiers texte (*.txt)", "txt"));
        
            int returnValue = fileChooser.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File fichier = fileChooser.getSelectedFile();
                cheminFichierHolder[0] = fichier.getAbsolutePath();
        
                if (!cheminFichierHolder[0].toLowerCase().endsWith(".txt")) {
                    JOptionPane.showMessageDialog(this, 
                        "Veuillez sélectionner un fichier .txt", 
                        "Format incorrect", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                JOptionPane.showMessageDialog(this,
                    "Fichier chargé avec succès!\nCliquez sur un mode pour démarrer.",
                    "Chargement réussi",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        fileMenu.add(quitterItem);
        fileMenu.add(chargerFichier);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel sizePanel = new JPanel();
        sizePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        sizePanel.add(new JLabel("Largeur:"));
        widthField = new JTextField(5);
        widthField.setText("500");
        sizePanel.add(widthField);
        sizePanel.add(new JLabel("Hauteur:"));
        heightField = new JTextField(5);
        heightField.setText("500"); 
        sizePanel.add(heightField);

        panel.add(Box.createVerticalGlue());
        panel.add(sizePanel);
        panel.add(Box.createVerticalStrut(20));

        JButton bouton1 = new JButton("Mode Classique");
        JButton bouton2 = new JButton("Mode DayNight");
        JButton bouton3 = new JButton("Mode HighLife");

        bouton1.setAlignmentX(Component.CENTER_ALIGNMENT);
        bouton2.setAlignmentX(Component.CENTER_ALIGNMENT);
        bouton3.setAlignmentX(Component.CENTER_ALIGNMENT);
        bouton1.addActionListener(e -> lancerMode(0));
        bouton2.addActionListener(e -> lancerMode(1));
        bouton3.addActionListener(e -> lancerMode(2));

        
        panel.add(bouton1);
        panel.add(Box.createVerticalStrut(10));
        panel.add(bouton2);
        panel.add(Box.createVerticalStrut(10));
        panel.add(bouton3);
        panel.add(Box.createVerticalGlue());

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Lance le jeu de la vie avec le mode sélectionné.
     * @param mode : le mode à lancer (0 pour Classique, 1 pour DayNight, 2 pour HighLife)
     */
    private void lancerMode(int mode) {
        try {
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            String fichier = cheminFichierHolder[0]; 
            
            JeuDeLaVie.jeuDeLaVie(mode, width, height, fichier);
            
            cheminFichierHolder[0] = null;
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Veuillez entrer des dimensions valides (nombres entiers)",
                "Erreur de saisie",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Point d'entrée principal de l'application.
     * @param args : arguments de la ligne de commande
     */
    public static void main(String[] args) {
        new MenuExample();
    }
}