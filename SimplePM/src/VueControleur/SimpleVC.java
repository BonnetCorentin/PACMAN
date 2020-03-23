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
import java.awt.Button;
import java.awt.event.ActionListener;
        
import java.util.*;
import java.util.Observable;
import javafx.scene.input.KeyCode;
import java.util.Observer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.VPos;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
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
    StackPane root,menu;
    
//HBox menuGrid = new HBox();
    VBox affichage = new VBox();


Image im =  new Image("images/fond.jpg");
    Image scoreLab =  new Image("images/ScorePM.jpg"); 
    ImageView scoreLabView = new ImageView(scoreLab);
    Label score=new Label(" 0");
    Label vie=new Label(" ♥ ");
    Label vie2=new Label(" ♥ ");
    Label vie3=new Label(" ♥ ");

Button start = new Button();  
    Button niveau= new Button();
    Button quitter= new Button();


    
    Scene scene;
    PacMan pacman;
    Fantome fantomeRouge;
    Fantome fantomeBleu;
    Fantome fantomeVert;

    @Override
    public void init() {
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
        grid.setAlignment(Pos.TOP_CENTER);
        root.getChildren().add(grid);
        
        vie.setFont(new Font("Arial",30));
        vie.setTextFill(Color.web("#FFFFFF"));
        vie.setTranslateX(12);
        vie.setTranslateY(275);
        root.getChildren().add(vie);
        vie.setVisible(true);
        
        vie2.setFont(new Font("Arial",30));
        vie2.setTextFill(Color.web("#FFFFFF"));
        vie2.setTranslateX(36);
        vie2.setTranslateY(275);
        root.getChildren().add(vie2);
        vie2.setVisible(true);
        
        vie3.setFont(new Font("Arial",30));
        vie3.setTextFill(Color.web("#FFFFFF"));
        vie3.setTranslateX(60);
        vie3.setTranslateY(275);
        root.getChildren().add(vie3);
        vie3.setVisible(true);
        
        scoreLabView.setTranslateX(190);
        scoreLabView.setTranslateY(550);
        affichage.getChildren().setAll(scoreLabView);         
        
        score.setFont(new Font("Arial",30));
        score.setTextFill(Color.web("#FFFFFF"));
        score.setTranslateX(310);
        score.setTranslateY(458);
        affichage.getChildren().add(score);
        
        affichage.setBackground(new Background(new BackgroundImage(im, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        
        affichage.getChildren().add(root);
        scene = new Scene(affichage, 600, 700);

        //menuGrid.setSpacing(10);
        //start.addActionListener((ActionListener) this);
        //niveau.addActionListener((ActionListener) this);
        //quitter.addActionListener((ActionListener) this);
        //menuGrid.getChildren().add(start);
        //menuGrid.getChildren().add(niveau);
        //menuGrid.getChildren().add(quitter);
        //scene2 = new Scene(menuGrid, 1000,1000);
        
        textureInit();

    }

    @Override

    public void start(Stage primaryStage) {
        Observer o = (Observable arg0, Object arg1) -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    textureInit ();                   
                    score.setText(String.valueOf(grille.getScore()));
                }
            });
        };
        // l'observer observe l'obervable (update est exécuté dès notifyObservers() est appelé côté modèle )
        
        root.setOnKeyPressed(new EventHandler<KeyEvent>() {

            public void handle(javafx.scene.input.KeyEvent event) {
                if (event.getCode() == KeyCode.Z) {
                    pacman.setActionAFaire(Action.Haut);
                }
                if (event.getCode() == KeyCode.Q) {
                    pacman.setActionAFaire(Action.Gauche);
                }
                if (event.getCode() == KeyCode.D) {
                    pacman.setActionAFaire(Action.Droite);
                }
                if (event.getCode() == KeyCode.S) {
                    pacman.setActionAFaire(Action.Bas);
                }
                if (event.getCode () == KeyCode.P){
                    grille.redemarrer (pacman,fantomeRouge,fantomeBleu,fantomeVert);
                }
            } // on écoute le clavier
        });

        grille.addObserver(o);
        grille.start();
        
        //primaryStage.setScene(scene2);
        //primaryStage.show();
        
        primaryStage.setTitle("PAC MAN!");        //Créer la grille
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        @Override
            public void handle(WindowEvent event) {
                System.exit(0);
                throw new UnsupportedOperationException("Not supported yet."); 
            }
        });
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
                        case "OuvertB":
                            tab[i][j].setImage(ensembleImage.get("OuvertB"));
                            break;
                        case "OuvertH":
                            tab[i][j].setImage(ensembleImage.get("OuvertH"));
                            break;
                        case "OuvertG":
                            tab[i][j].setImage(ensembleImage.get("OuvertG"));
                            break;
                        case "OuvertD":
                            tab[i][j].setImage(ensembleImage.get("OuvertD"));
                            break;
                        case "PorteFantome":
                           tab[i][j].setImage(ensembleImage.get("PorteFantome"));
                           break;
                    }
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

    public void handle(Event event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
