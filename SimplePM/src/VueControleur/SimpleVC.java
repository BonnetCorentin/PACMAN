/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueControleur;

import Modele.Action;

import java.awt.Point;
import java.awt.event.ActionEvent;

import Modele.Couloir;
import Modele.Fantome;

import Modele.Grille;
import Modele.GrosBean;

import Modele.MS;
import Modele.Mangeable;
import Modele.Mur;

import Modele.PacMan;

        
import java.util.*;
import java.util.Observable;
import javafx.scene.input.KeyCode;
import java.util.Observer;

import javafx.application.Platform;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

import javafx.scene.layout.GridPane;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.WindowEvent;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author
 */
public class SimpleVC extends Application {

    public final int SIZE_X = 21;
    public final int SIZE_Y = 21;
    private static int secret = 0;
    
    Grille grille;
    ImageView[][] tab = new ImageView[SIZE_Y][SIZE_X]; // tableau permettant de rÃƒÆ’Ã‚Â©cupÃƒÆ’Ã‚Â©rer les cases graphiques lors du rafraichissement UTILITE ???????????
    GridPane grid = new GridPane(); // crÃƒÆ’Ã‚Â©ation de la grille 

    HashMap<String, Image> ensembleImage = new HashMap<>();
    StackPane root;
    
    VBox affichage;

    Button quitter;

    Image im,option;

    ImageView optionView;
    ImageView gameOverView;
    ImageView victoireView;
    ImageView scoreLabView;
    ImageView vieView;
    ImageView vieView2;
    ImageView vieView3;
    ImageView readyView;
    
    Label score;
    Image vie;
    Image vie2;
    Image vie3;
    Image ready;

    
    Scene scene;
    PacMan pacman;
    Fantome fantomeRouge;
    Fantome fantomeBleu;
    Fantome fantomeVert;
    Fantome fantomeRose;

    @Override
    public void init() {
        initialisationJeu ();
        
        initialisationImages();

        root = new StackPane();
        grid.setAlignment(Pos.TOP_CENTER);
        root.getChildren().add(grid);
        
        gameOverView.setTranslateX(-1);
        gameOverView.setTranslateY(-3);
        gameOverView.setVisible(false);
        root.getChildren().add(gameOverView);

        optionView.setTranslateX(185);
        optionView.setTranslateY(290);
        optionView.setFitHeight(150);
        optionView.setFitWidth(225);
        optionView.setVisible(true);
        root.getChildren().add(optionView);
        
        readyView.setTranslateX(5);
        readyView.setTranslateY(17);
        readyView.setFitHeight(20);
        readyView.setFitWidth(90);
        readyView.setVisible(true);
        root.getChildren().add(readyView);

        victoireView.setTranslateX(-2);
        victoireView.setTranslateY(0);
        victoireView.setVisible(false);
        root.getChildren().add(victoireView);
        
        vieView.setTranslateX(-145);
        vieView.setTranslateY(285);
        root.getChildren().add(vieView);
        vieView.setVisible(true);

        vieView2.setTranslateX(-115);
        vieView2.setTranslateY(285);
        root.getChildren().add(vieView2);
        vieView2.setVisible(true);

        vieView3.setTranslateX(-85);
        vieView3.setTranslateY(285);
        root.getChildren().add(vieView3);
        vieView3.setVisible(true);

        quitter.setTranslateX(0);
        quitter.setTranslateY(330);
        quitter.setVisible(true);
        quitter.setPrefSize(150,10);
        root.getChildren().add(quitter);
        
        scoreLabView.setTranslateX(20);
        scoreLabView.setTranslateY(565);
        affichage.getChildren().setAll(scoreLabView);         
        
        score.setFont(new Font("Arial",40));
        score.setTextFill(Color.web("#FFFFFF"));
        score.setTranslateX(150);
        score.setTranslateY(467);
        affichage.getChildren().add(score);
        
        affichage.setBackground(new Background(new BackgroundImage(im, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        
        affichage.getChildren().add(root);
        scene = new Scene(affichage, 600, 700);
        
        textureInit();

    }
    
    public void decrementeVie(){
        switch (grille.getVie()) {
            case 2:
            	vieView3.setVisible(false);
                break;
            case 1:
            	vieView3.setVisible(false);
            	vieView2.setVisible(false);
                break;
            case 0:
            	vieView3.setVisible(false);
            	vieView2.setVisible(false);
            	vieView.setVisible(false);
                gameOverView.setVisible(true);
                Modele.ME.setX(false);
                break;
        }
    }
    
   public void victoire() {
    	if(grille.getBean()<=0) {
    		victoireView.setVisible(true); 		
    	}
    }

    @Override

    public void start(Stage primaryStage) {
        Observer o = (Observable arg0, Object arg1) -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    textureInit ();
                    decrementeVie();
                    victoire();
                    score.setText(String.valueOf(grille.getScore()));
                    if(Modele.ME.getX()==true) {
                        readyView.setVisible(true);
                    }
                }
            });
        };
        // l'observer observe l'obervable (update est exÃƒÆ’Ã‚Â©cutÃƒÆ’Ã‚Â© dÃƒÆ’Ã‚Â¨s notifyObservers() est appelÃƒÆ’Ã‚Â© cÃƒÆ’Ã‚Â´tÃƒÆ’Ã‚Â© modÃƒÆ’Ã‚Â¨le )
        
        root.setOnKeyPressed(new EventHandler<KeyEvent>() {

            public void handle(javafx.scene.input.KeyEvent event) {
                if (event.getCode() == KeyCode.Z) {
                    pacman.setActionAFaire(Action.Haut);
                }
                else if (event.getCode() == KeyCode.Q) {
                    pacman.setActionAFaire(Action.Gauche);
                }
                else if (event.getCode() == KeyCode.D) {
                    pacman.setActionAFaire(Action.Droite);
                }
                else if (event.getCode() == KeyCode.S) {
                    pacman.setActionAFaire(Action.Bas);
                }
                else if (event.getCode () == KeyCode.P){
                    initialisationJeu ();
                    readyView.setVisible(true);
                    vieView3.setVisible(true);
                    vieView2.setVisible(true);
                    vieView.setVisible(true);
                    gameOverView.setVisible(false);
                    victoireView.setVisible(false);
                }
                else if (event.getCode () == KeyCode.ENTER){
                    Modele.ME.setActionPossible ();
                    readyView.setVisible(false);
                    Modele.ME.setX(false);
                }

                else if (event.getCode () == KeyCode.F1){
                	secret = 1;
                    initialisationJeu ();
                }
                
                quitter.setOnMouseClicked(e->{
                	primaryStage.close();
                	System.exit(0);
                });
            }
        });
        
        grille.addObserver(o);
        grille.start();
        
        primaryStage.setTitle("PAC MAN!");
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

    public void textureInit() {
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
            
            if (Fantome.estMangeable ()){
                point = grille.getGrilleDynamique().get(fantomeRouge);
                tab[point.y][point.x].setImage(ensembleImage.get("fantomeMangeable"));
                
            }else {
                point = grille.getGrilleDynamique().get(fantomeRouge);
                tab[point.y][point.x].setImage(ensembleImage.get("fantomeRouge"));
            }

            if (Fantome.estMangeable ()){
                point = grille.getGrilleDynamique().get(fantomeBleu);
                tab[point.y][point.x].setImage(ensembleImage.get("fantomeMangeable"));
                
            }else {
                point = grille.getGrilleDynamique().get(fantomeBleu);
                tab[point.y][point.x].setImage(ensembleImage.get("fantomeBleu"));
            }

            if (Fantome.estMangeable ()){
                point = grille.getGrilleDynamique().get(fantomeVert);
                tab[point.y][point.x].setImage(ensembleImage.get("fantomeMangeable"));
                
            }else {
                point = grille.getGrilleDynamique().get(fantomeVert);
                tab[point.y][point.x].setImage(ensembleImage.get("fantomeVert"));
            }
            
            if (Fantome.estMangeable ()){
                point = grille.getGrilleDynamique().get(fantomeRose);
                tab[point.y][point.x].setImage(ensembleImage.get("fantomeMangeable"));
            }else {
                point = grille.getGrilleDynamique().get(fantomeRose);
                tab[point.y][point.x].setImage(ensembleImage.get("fantomeRose"));
            }

        }

    }

    private void initialisationImages() {
        ensembleImage.put("barriereFantome", new Image("images/barriereFantome.png"));// prÃƒÆ’Ã‚Â©paration des images
        ensembleImage.put("bean", new Image("images/bean.png"));
        ensembleImage.put("fantomeBleu", new Image("images/fantomeBleu.png"));// prÃƒÆ’Ã‚Â©paration des images
        ensembleImage.put("fantomeRose", new Image("images/fantomeRose.png"));
        ensembleImage.put("fantomeRouge", new Image("images/fantomeRouge.png"));// prÃƒÆ’Ã‚Â©paration des images
        ensembleImage.put("fantomeVert", new Image("images/fantomeVert.png"));
        ensembleImage.put("gros_bean", new Image("images/gros_bean.png"));
        ensembleImage.put("mur", new Image("images/mur.png"));
        ensembleImage.put("mur2", new Image("images/mur2.png"));// prÃƒÆ’Ã‚Â©paration des images
        ensembleImage.put("pacman", new Image("images/pacman.png"));
        ensembleImage.put("fantomeMangeable", new Image("images/fan_mangeable.png"));
        ensembleImage.put("fantomeMange", new Image("images/FantomePeur0.png"));
        ensembleImage.put("pacmanBas0", new Image("images/pacmanBas0.png"));// prÃƒÆ’Ã‚Â©paration des images
        ensembleImage.put("pacmanBas1", new Image("images/pacmanBas1.png"));
        ensembleImage.put("pacmanDroite0", new Image("images/pacmanDroite0.png"));
        ensembleImage.put("pacmanDroite1", new Image("images/pacmanDroite1.png"));
        ensembleImage.put("pacmanGauche0", new Image("images/pacmanGauche0.png"));
        ensembleImage.put("pacmanGauche1", new Image("images/pacmanGauche1.png"));
        ensembleImage.put("fond", new Image("images/fond.png"));
        ensembleImage.put("pacmanHaut0", new Image("images/pacmanHaut0.png"));// prÃƒÆ’Ã‚Â©paration des images
        ensembleImage.put("pacmanHaut1", new Image("images/pacmanHaut1.png"));
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
        
        affichage = new VBox();
        quitter = new Button("Quitter");
        im =  new Image("images/fond.jpg");
        
        Image scoreLab =  new Image("images/ScorePM.jpg");
        Image gameOver =  new Image("images/GameOver.jpg"); 
        Image victoire =  new Image("images/victoire.jpg"); 
        Image option =  new Image("images/Option.png");
        
        score=new Label(" 0 ");
        vie=new Image("images/pacmanDroite1.png");
        vie2=new Image("images/pacmanDroite1.png");
        vie3=new Image("images/pacmanDroite1.png");
        ready=new Image("images/ready.png");
        
        optionView = new ImageView(option);
        gameOverView = new ImageView(gameOver);
        victoireView = new ImageView(victoire);
        scoreLabView = new ImageView(scoreLab);
        vieView=new ImageView(vie);
        vieView2=new ImageView(vie2);
        vieView3=new ImageView(vie3);
        readyView=new ImageView(ready);
        

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
    
    private void initialisationJeu (){

        pacman = new PacMan (0);
        fantomeRouge = new Fantome("rouge",1000);
        fantomeBleu = new Fantome("bleu",2000);
        fantomeVert = new Fantome("vert",3000);
        fantomeRose = new Fantome("rose",4000);
        
        if(secret == 0) {
            grille = new Grille(pacman,fantomeRouge,fantomeBleu,fantomeVert,fantomeRose,0);
        }
        else {
            grille = new Grille(pacman,fantomeRouge,fantomeBleu,fantomeVert,fantomeRose,1);
        }

        grille.getPacman().setGrille(grille);
        grille.getFantomeBleu().setGrille(grille);
        grille.getFantomeVert().setGrille(grille);
        grille.getFantomeRouge().setGrille(grille);
        grille.getFantomeRose().setGrille(grille);
   }

}
