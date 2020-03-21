/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueControleur;

import Modele.Action;
import java.awt.Point;
import Modele.Couloir;
import Modele.Fantome;
import Modele.Grille;
import Modele.GrosBean;

import Modele.MS;
import Modele.Mangeable;
import Modele.Mur;
import Modele.OuvertBas;
import Modele.OuvertDroite;
import Modele.OuvertGauche;
import Modele.OuvertHaut;
import Modele.PacMan;
        
import java.util.*;
import java.util.Observable;
import javafx.scene.input.KeyCode;
import java.util.Observer;
import javafx.application.Application;
import javafx.application.Platform;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author
 */
public class SimpleVC extends Application {

    public final int SIZE_X = 21;
    public final int SIZE_Y = 21;
    
    Grille grille;
    ImageView[][] tab = new ImageView[SIZE_Y][SIZE_X]; // tableau permettant de récupérer les cases graphiques lors du rafraichissement UTILITE ???????????
    GridPane grid = new GridPane(); // création de la grille 

    HashMap<String, Image> ensembleImage = new HashMap<>();
    StackPane root;
    Text t = new Text(10, 10, "0");

    
    Scene scene;
    PacMan pacman;
    Fantome fantomeRouge;
    Fantome fantomeBleu;
    Fantome fantomeVert;

    @Override
    public void init() {
        t.setFont(new Font(20));
        pacman = new PacMan ();
        fantomeRouge = new Fantome("rouge");
        fantomeBleu = new Fantome("bleu");
        fantomeVert = new Fantome("vert");
        
        grille = new Grille(pacman,fantomeRouge,fantomeBleu,fantomeVert);
               
        grille.getPacman().setGrille(grille);
        grille.getFantomeBleu().setGrille(grille);
        grille.getFantomeVert().setGrille(grille);
        grille.getFantomeRouge().setGrille(grille);
        
        initialisationImages();

        pacman = grille.getPacman();
        fantomeRouge = grille.getFantomeRouge();
        fantomeBleu = grille.getFantomeBleu();

        root = new StackPane();
        root.getChildren().add(grid);
        root.getChildren().add(t);
        scene = new Scene(root, 1000, 1000);
        
        textureInit();

    }

    @Override

    public void start(Stage primaryStage) {
        Observer o = (Observable arg0, Object arg1) -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    textureInit ();                   
                    t.setText (String.valueOf(grille.getScore()));
                }
            });
        };
        // l'observer observe l'obervable (update est exécuté dès notifyObservers() est appelé côté modèle )
        
        root.setOnKeyPressed((javafx.scene.input.KeyEvent event) -> {
            if (event.getCode() == KeyCode.Z) {
                pacman.setAction(Action.Haut);
            }
            if (event.getCode() == KeyCode.Q) {
                pacman.setAction(Action.Gauche);
            }
            if (event.getCode() == KeyCode.D) {
                pacman.setAction(Action.Droite);
            }
            if (event.getCode() == KeyCode.S) {
                pacman.setAction(Action.Bas);
            }
        } // on écoute le clavier
        );

        grille.addObserver(o);
        grille.start();
        
        primaryStage.setTitle("PAC MAN!");
        primaryStage.setScene(scene);
        primaryStage.show();
        grid.requestFocus();
    }

    public synchronized void textureInit() {
        for (int i = 0; i < SIZE_Y; i++) {

            for (int j = 0; j < SIZE_X; j++) {

                Point point = new Point(j, i);
                MS ms = grille.getvalueGS(point);

                if (ms instanceof Couloir) {
                    if (((Mangeable) ms).getEstMange() == false) {  
                        tab[i][j].setImage(ensembleImage.get("gros_bean"));
                    } else {
                        tab[i][j].setImage(ensembleImage.get("fond"));
                    }

                } else if (ms instanceof GrosBean) {
                    if (((Mangeable) ms).getEstMange() == false) {
                        tab[i][j].setImage(ensembleImage.get("bean"));
                    } else {            
                        tab[i][j].setImage(ensembleImage.get("fond"));
                    }

                } else if (ms instanceof Mur) {
                    String typeMur = ((Mur) ms).getTypeMur();

                    switch (typeMur) {
                        case "mur":
                            tab[i][j].setImage(ensembleImage.get("mur"));
                            break;
                        case "mur2":
                            tab[i][j].setImage(ensembleImage.get("mur2"));
                            break;
                        case "coinD":
                            tab[i][j].setImage(ensembleImage.get("CoinD"));
                            break;
                        case "coinD2":
                            tab[i][j].setImage(ensembleImage.get("CoinD2"));
                            break;
                        case "coinG":
                            tab[i][j].setImage(ensembleImage.get("CoinG"));
                            break;
                        case "coinG2":
                            tab[i][j].setImage(ensembleImage.get("CoinG2"));
                            break;
                        case "fermeB":
                            tab[i][j].setImage(ensembleImage.get("FermeB"));
                            break;
                        case "fermeH":
                            tab[i][j].setImage(ensembleImage.get("FermeH"));
                            break;
                        case "fermeG":
                            tab[i][j].setImage(ensembleImage.get("FermeG"));
                            break;
                        case "fermeD":
                            tab[i][j].setImage(ensembleImage.get("FermeD"));
                            break;
                    }

                } else if (ms instanceof OuvertBas) {
                    tab[i][j].setImage(ensembleImage.get("OuvertB"));
                } else if (ms instanceof OuvertHaut) {
                    tab[i][j].setImage(ensembleImage.get("OuvertH"));
                } else if (ms instanceof OuvertDroite) {
                    tab[i][j].setImage(ensembleImage.get("OuvertD"));
                } else if (ms instanceof OuvertGauche) {
                    tab[i][j].setImage(ensembleImage.get("OuvertG"));
                } else if (ms instanceof PorteFantome) {
                    tab[i][j].setImage(ensembleImage.get("PorteFantome"));
                } else {
                    tab[i][j].setImage(ensembleImage.get("fond"));
                }
            }

            Point point = grille.getGrilleDynamique().get(pacman);

            Action action = pacman.getAction();

            if (action == Action.Droite) {
                tab[point.y][point.x].setImage(ensembleImage.get("pacmanDroite0"));
            } else if (action == Action.Gauche) {
                tab[point.y][point.x].setImage(ensembleImage.get("pacmanGauche0"));
            } else if (action == Action.Bas) {
                tab[point.y][point.x].setImage(ensembleImage.get("pacmanBas0"));
            } else {
                tab[point.y][point.x].setImage(ensembleImage.get("pacmanHaut0"));
            }

            point = grille.getGrilleDynamique().get(fantomeRouge);
            
            tab[point.y][point.x].setImage(ensembleImage.get("fantomeRouge"));

            point = grille.getGrilleDynamique().get(fantomeBleu);

            tab[point.y][point.x].setImage(ensembleImage.get("fantomeBleu"));
            
            point = grille.getGrilleDynamique().get(fantomeVert);

            tab[point.y][point.x].setImage(ensembleImage.get("fantomeVert"));

        }

    }

    private void initialisationImages() {
        // Pacman.svg.png
        ensembleImage.put("barriereFantome", new Image("images/barriereFantome.png"));// préparation des images
        ensembleImage.put("bean", new Image("images/bean.png"));
        //ensembleImage.put("fan_mangeable", new Image("images/fan_mangeable.png"));
        //ensembleImage.put("fan_mangeable", new Image("images/fan_mangeable.png"));
        ensembleImage.put("fantomeBleu", new Image("images/fantomeBleu.png"));// préparation des images
        //ensembleImage.put("fantomePeur0", new ImageView("images/fantomePeur0.png"));
        //ensembleImage.put("fantomePeur1", new ImageView("images/fantomePeur1.png"));
        //ensembleImage.put("fantomeRose", new Image("images/fantomeRose.png"));
        ensembleImage.put("fantomeRouge", new Image("images/fantomeRouge.png"));// préparation des images
        ensembleImage.put("fantomeVert", new Image("images/fantomeVert.png"));
        ensembleImage.put("gros_bean", new Image("images/gros_bean.png"));
        //ensembleImage.put("Mort0", new Image("images/Mort0.png"));
        //ensembleImage.put("Mort1", new Image("images/Mort1.png"));
        //ensembleImage.put("Mort2", new Image("images/Mort2.png"));
        //ensembleImage.put("Mort3", new Image("images/Mort3.png"));
        ensembleImage.put("mur", new Image("images/mur.png"));
        ensembleImage.put("mur2", new Image("images/mur2.png"));// préparation des images
        ensembleImage.put("pacman", new Image("images/pacman.png"));
        //ensembleImage.put("pacman_2", new Image("images/pacman_2.png"));
        //ensembleImage.put("pacman_2f", new Image("images/pacman_2f.png"));
        //ensembleImage.put("pacman_3", new Image("images/pacman_3.png"));
        //ensembleImage.put("pacman_3f", new Image("images/pacman_3f.png"));
        //ensembleImage.put("pacman_4", new Image("images/pacman_4.png"));
        //ensembleImage.put("pacman_4f", new Image("images/pacman_4f.png"));
        //ensembleImage.put("pacman_f", new Image("images/pacman_f.png"));
        ensembleImage.put("pacmanBas0", new Image("images/pacmanBas0.png"));// préparation des images
        ensembleImage.put("pacmanBas1", new Image("images/pacmanBas1.png"));
        ensembleImage.put("pacmanDroite0", new Image("images/pacmanDroite0.png"));
        ensembleImage.put("pacmanDroite1", new Image("images/pacmanDroite1.png"));
        ensembleImage.put("pacmanGauche0", new Image("images/pacmanGauche0.png"));
        ensembleImage.put("pacmanGauche1", new Image("images/pacmanGauche1.png"));
        ensembleImage.put("fond", new Image("images/fond.png"));
        ensembleImage.put("pacmanHaut0", new Image("images/pacmanHaut0.png"));// préparation des images
        ensembleImage.put("pacmanHaut1", new Image("images/pacmanHaut1.png"));
        //ensembleImage.put("pouvoir", new Image("images/pouvoir.png"));
        ensembleImage.put("CoinD", new Image("images/coinD.png"));
        ensembleImage.put("CoinD2", new Image("images/coinD2.png"));
        ensembleImage.put("CoinG", new Image("images/coinG.png"));
        ensembleImage.put("CoinG2", new Image("images/coinG2.png"));
        ensembleImage.put("FermeD", new Image("images/fermeD.png"));
        ensembleImage.put("FermeG", new Image("images/fermeG.png"));
        ensembleImage.put("FermeH", new Image("images/fermeH.png"));
        ensembleImage.put("FermeB", new Image("images/fermeB.png"));
        ensembleImage.put("OuvertB", new Image("images/ouvertB.png"));
        ensembleImage.put("OuvertH", new Image("images/ouvertH.png"));
        ensembleImage.put("OuvertG", new Image("images/ouvertG.png"));
        ensembleImage.put("OuvertD", new Image("images/ouvertD.png"));
        ensembleImage.put("PorteFantome", new Image("images/porteFantome.png"));

        for (int i = 0; i < SIZE_Y; i++) { // initialisation de la grille (sans image)
            for (int j = 0; j < SIZE_X; j++) {
                ImageView img = new ImageView();

                tab[i][j] = img;

                grid.add(img, j, i);
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
