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
import Modele.Images;
import Modele.ME;
import Modele.MS;
import Modele.Mangeable;
import Modele.Mur;
import Modele.OuvertBas;
import Modele.OuvertDroite;
import Modele.OuvertGauche;
import Modele.OuvertHaut;
import Modele.PacMan;
import java.io.InputStream;
import java.util.*;
import java.util.Observable;
import javafx.scene.input.KeyCode;
import java.util.Observer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author
 */
public class SimpleVC extends Application {

    public final int SIZE_X = 21;
    public final int SIZE_Y = 21;
    Grille grille = new Grille();
    ImageView[][] tab = new ImageView[SIZE_Y][SIZE_X]; // tableau permettant de récupérer les cases graphiques lors du rafraichissement UTILITE ???????????
    GridPane grid = new GridPane(); // création de la grille 
    HashMap<String, Images> ensembleImage = new HashMap<String, Images>();
    StackPane root;
    Scene scene;
    PacMan pacman;
    Fantome fantomeRouge;
    Fantome fantomeBleu;

    @Override
    public void init() {

        initialisationImages();

        pacman = grille.getPacman();
        fantomeRouge = grille.getFantomeRouge();
        fantomeBleu = grille.getFantomeBleu();

        root = new StackPane();
        root.getChildren().add(grid);

        scene = new Scene(root, 1000, 1000);
        textureInit();

    }

    @Override

    public void start(Stage primaryStage) {
        primaryStage.setTitle("PAC MAN!");
        primaryStage.setScene(scene);
        primaryStage.show();
        Observer o;
        o = (Observable o1, Object arg) -> {
            Platform.runLater(() -> {
                System.out.println("a");
                textureInit();
                grille.passeSurNourriture(pacman);
                grid.requestFocus();
            });
        } // l'observer observe l'obervable (update est exécuté dès notifyObservers() est appelé côté modèle )
        ;
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
        grille.debutJeu();
    }

    public synchronized void textureInit() {
        for (int i = 0; i < SIZE_Y; i++) {

            for (int j = 0; j < SIZE_X; j++) {

                Point point = new Point(j, i);
                MS ms = grille.getvalueGS(point);

                if (ms instanceof Couloir) {
                    if (((Mangeable) ms).getEstMange() == false) {
                        ImageView img;

                        img = new ImageView(ensembleImage.get("gros_bean").getPath());

                        tab[i][j] = img;

                        grid.add(tab[i][j], j, i);
                    } else {
                        ImageView img;
                        img = new ImageView(ensembleImage.get("fond").getPath());
                        tab[i][j] = img;

                        grid.add(tab[i][j], j, i);
                    }

                } else if (ms instanceof GrosBean) {
                    if (((Mangeable) ms).getEstMange() == false) {
                        ImageView img;

                        img = new ImageView(ensembleImage.get("bean").getPath());

                        tab[i][j] = img;

                        grid.add(tab[i][j], j, i);
                    } else {
                        ImageView img;
                        img = new ImageView(ensembleImage.get("fond").getPath());
                        tab[i][j] = img;

                        grid.add(tab[i][j], j, i);
                    }

                } else if (ms instanceof Mur) {
                    String typeMur = ((Mur) ms).getTypeMur();
                    ImageView img;

                    switch (typeMur) {
                        case "mur":
                            img = new ImageView(ensembleImage.get("mur").getPath());
                            tab[i][j] = img;
                            break;
                        case "mur2":
                            img = new ImageView(ensembleImage.get("mur2").getPath());
                            tab[i][j] = img;
                            break;
                        case "coinD":
                            img = new ImageView(ensembleImage.get("CoinD").getPath());
                            tab[i][j] = img;
                            break;
                        case "coinD2":
                            img = new ImageView(ensembleImage.get("CoinD2").getPath());
                            tab[i][j] = img;
                            break;
                        case "coinG":
                            img = new ImageView(ensembleImage.get("CoinG").getPath());
                            tab[i][j] = img;
                            break;
                        case "coinG2":
                            img = new ImageView(ensembleImage.get("CoinG2").getPath());
                            tab[i][j] = img;
                            break;
                        case "fermeB":
                            img = new ImageView(ensembleImage.get("FermeB").getPath());
                            tab[i][j] = img;
                            break;
                        case "fermeH":
                            img = new ImageView(ensembleImage.get("FermeH").getPath());
                            tab[i][j] = img;
                            break;
                        case "fermeG":
                            img = new ImageView(ensembleImage.get("FermeG").getPath());
                            tab[i][j] = img;
                            break;
                        case "fermeD":
                            img = new ImageView(ensembleImage.get("FermeD").getPath());
                            tab[i][j] = img;
                            break;
                    }

                    grid.add(tab[i][j], j, i);

                } else if (ms instanceof OuvertBas) {
                    ImageView img;

                    img = new ImageView(ensembleImage.get("OuvertB").getPath());

                    tab[i][j] = img;

                    grid.add(tab[i][j], j, i);
                } else if (ms instanceof OuvertHaut) {
                    ImageView img;

                    img = new ImageView(ensembleImage.get("OuvertH").getPath());

                    tab[i][j] = img;

                    grid.add(tab[i][j], j, i);
                } else if (ms instanceof OuvertDroite) {
                    ImageView img;

                    img = new ImageView(ensembleImage.get("OuvertD").getPath());

                    tab[i][j] = img;

                    grid.add(tab[i][j], j, i);
                } else if (ms instanceof OuvertGauche) {
                    ImageView img;

                    img = new ImageView(ensembleImage.get("OuvertG").getPath());

                    tab[i][j] = img;

                    grid.add(tab[i][j], j, i);
                } else if (ms instanceof PorteFantome) {
                    ImageView img;

                    img = new ImageView(ensembleImage.get("PorteFantome").getPath());

                    tab[i][j] = img;

                    grid.add(tab[i][j], j, i);
                } else {
                    ImageView img;
                    img = new ImageView(ensembleImage.get("fond").getPath());
                    tab[i][j] = img;

                    grid.add(tab[i][j], j, i);
                }

            }

            Point point = grille.getGrilleDynamique().get(pacman);

            Action action = pacman.getAction();
            ImageView img;

            if (action == Action.Droite) {
                img = new ImageView(ensembleImage.get("pacmanDroite0").getPath());
            } else if (action == Action.Gauche) {
                img = new ImageView(ensembleImage.get("pacmanGauche0").getPath());
            } else if (action == Action.Bas) {
                img = new ImageView(ensembleImage.get("pacmanBas0").getPath());
            } else {
                img = new ImageView(ensembleImage.get("pacmanHaut0").getPath());
            }

            tab[point.y][point.x] = img;

            grid.add(tab[point.y][point.x], point.x, point.y);

            point = grille.getGrilleDynamique().get(fantomeRouge);

            img = new ImageView(ensembleImage.get("fantome_rouge").getPath());

            tab[point.y][point.x] = img;

            grid.add(tab[point.y][point.x], point.x, point.y);

            point = grille.getGrilleDynamique().get(fantomeBleu);

            img = new ImageView(ensembleImage.get("fantomeBleu").getPath());

            tab[point.y][point.x] = img;

            grid.add(tab[point.y][point.x], point.x, point.y);

        }

    }

    private void initialisationImages() {
        // Pacman.svg.png
        ensembleImage.put("barriereFantome", new Images("images/barriereFantome.png"));// préparation des images
        ensembleImage.put("bean", new Images("images/bean.png"));
        ensembleImage.put("fan_mangeable", new Images("images/fan_mangeable.png"));
        ensembleImage.put("fan_mangeable", new Images("images/fan_mangeable.png"));
        ensembleImage.put("fantome_cyan", new Images("images/fan_cyan.png"));
        ensembleImage.put("fantome_orange", new Images("images/fantome_orange.png"));
        ensembleImage.put("fantome_rose", new Images("images/fantome_rose.png"));
        ensembleImage.put("fantome_rouge", new Images("images/fantome_rouge.png"));
        ensembleImage.put("fantomeBleu", new Images("images/fantomeBleu.png"));// préparation des images
        ensembleImage.put("fantomePeur0", new Images("images/fantomePeur0.png"));
        ensembleImage.put("fantomePeur1", new Images("images/fantomePeur1.png"));
        ensembleImage.put("fantomeRose", new Images("images/fantomeRose.png"));
        ensembleImage.put("fantomeRouge", new Images("images/fantomeRouge.png"));// préparation des images
        ensembleImage.put("fantomeVert", new Images("images/fantomeVert.png"));
        ensembleImage.put("gros_bean", new Images("images/gros_bean.png"));
        ensembleImage.put("Mort0", new Images("images/Mort0.png"));
        ensembleImage.put("Mort1", new Images("images/Mort1.png"));
        ensembleImage.put("Mort2", new Images("images/Mort2.png"));
        ensembleImage.put("Mort3", new Images("images/Mort3.png"));
        ensembleImage.put("mur", new Images("images/mur.png"));
        ensembleImage.put("mur2", new Images("images/mur2.png"));// préparation des images
        ensembleImage.put("pacman", new Images("images/pacman.png"));
        ensembleImage.put("pacman_2", new Images("images/pacman_2.png"));
        ensembleImage.put("pacman_2f", new Images("images/pacman_2f.png"));
        ensembleImage.put("pacman_3", new Images("images/pacman_3.png"));
        ensembleImage.put("pacman_3f", new Images("images/pacman_3f.png"));
        ensembleImage.put("pacman_4", new Images("images/pacman_4.png"));
        ensembleImage.put("pacman_4f", new Images("images/pacman_4f.png"));
        ensembleImage.put("pacman_f", new Images("images/pacman_f.png"));
        ensembleImage.put("pacmanBas0", new Images("images/pacmanBas0.png"));// préparation des images
        ensembleImage.put("pacmanBas1", new Images("images/pacmanBas1.png"));
        ensembleImage.put("pacmanDroite0", new Images("images/pacmanDroite0.png"));
        ensembleImage.put("pacmanDroite1", new Images("images/pacmanDroite1.png"));
        ensembleImage.put("pacmanGauche0", new Images("images/pacmanGauche0.png"));
        ensembleImage.put("pacmanGauche1", new Images("images/pacmanGauche1.png"));
        ensembleImage.put("fond", new Images("images/fond.png"));
        ensembleImage.put("pacmanHaut0", new Images("images/pacmanHaut0.png"));// préparation des images
        ensembleImage.put("pacmanHaut1", new Images("images/pacmanHaut1.png"));
        ensembleImage.put("pouvoir", new Images("images/pouvoir.png"));
        ensembleImage.put("CoinD", new Images("images/coinD.png"));
        ensembleImage.put("CoinD2", new Images("images/coinD2.png"));
        ensembleImage.put("CoinG", new Images("images/coinG.png"));
        ensembleImage.put("CoinG2", new Images("images/coinG2.png"));
        ensembleImage.put("FermeD", new Images("images/fermeD.png"));
        ensembleImage.put("FermeG", new Images("images/fermeG.png"));
        ensembleImage.put("FermeH", new Images("images/fermeH.png"));
        ensembleImage.put("FermeB", new Images("images/fermeB.png"));
        ensembleImage.put("OuvertB", new Images("images/ouvertB.png"));
        ensembleImage.put("OuvertH", new Images("images/ouvertH.png"));
        ensembleImage.put("OuvertG", new Images("images/ouvertG.png"));
        ensembleImage.put("OuvertD", new Images("images/ouvertD.png"));
        ensembleImage.put("PorteFantome", new Images("images/porteFantome.png"));

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
