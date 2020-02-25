/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueControleur;

import Modele.SimplePacMan;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author frederic.armetta
 */
public class SimpleVC extends Application {
    
    public final int SIZE_X = 20;
    public final int SIZE_Y = 20;
    
    @Override
    public void start(Stage primaryStage) {
        
        
        SimplePacMan spm = new SimplePacMan(SIZE_X, SIZE_Y); // initialisation du modèle
        
        GridPane grid = new GridPane(); // création de la grille 
        
        // Pacman.svg.png
        Image imbarriereFantome = new Image("images/barriereFantome.png"); // préparation des images
        Image imbean = new Image("images/bean.png");
        Image imfan_mangeable = new Image("images/fan_mangeable.png"); // préparation des images
        Image imfantome_cyan = new Image("images/fantome_cyan.png");
        Image imfantome_orange = new Image("images/fantome_orange.png"); // préparation des images
        Image imfantome_rose = new Image("images/fantome_rose.png");
        Image imfantome_rouge = new Image("images/fantome_rouge.png"); // préparation des images
        Image imfantomeBleu = new Image("images/fantomeBleu.png");
        Image imFantomePeur0 = new Image("images/FantomePeur0.png"); // préparation des images
        Image imFantomePeur1 = new Image("images/FantomePeur1.png");
        Image imfantomeRose = new Image("images/fantomeRose.png"); // préparation des images
        Image imfantomeRouge = new Image("images/fantomeRouge.png");
        Image imfantomeVert = new Image("images/fantomeVert.png"); // préparation des images
        Image imgros_bean = new Image("images/gros_bean.png");
        Image imMort0 = new Image("images/Mort0.png"); // préparation des images
        Image imMort1 = new Image("images/Mort1.png");
        Image imMort2 = new Image("images/Mort2.png"); // préparation des images
        Image imMort3 = new Image("images/Mort3.png");
        Image immur = new Image("images/mur.png"); // préparation des images
        Image impacman = new Image("images/pacman.png");
        Image impacman_2 = new Image("images/pacman_2.png"); // préparation des images
        Image impacman_2f = new Image("images/pacman_2f.png");
        Image impacman_3 = new Image("images/pacman_3.png"); // préparation des images
        Image impacman_3f = new Image("images/pacman_3f.png");
        Image impacman_4 = new Image("images/pacman_4.png"); // préparation des images
        Image impacman_4f = new Image("images/pacman_4f.png");
        Image impacman_f = new Image("images/pacman_f.png"); // préparation des images
        Image impacmanBas0 = new Image("images/pacmanBas0.png");
        Image impacmanBas1 = new Image("images/pacmanBas1.png"); // préparation des images
        Image impacmanDroite0 = new Image("images/pacmanDroite0.png");
        Image impacmanDroite1 = new Image("images/pacmanDroite1.png"); // préparation des images
        Image impacmanGauche0 = new Image("images/pacmanGauche0.png");
        Image impacmanGauche1 = new Image("images/pacmanGauche1.png"); // préparation des images
        Image impacmanHaut0 = new Image("images/pacmanHaut0.png");
        Image impacmanHaut1 = new Image("images/pacmanHaut1.png"); // préparation des images
        Image impouvoir = new Image("images/pouvoir.png"); // préparation des images
        Image imvide = new Image("images/vide.png");
        
        //img.setScaleY(0.01);
        //img.setScaleX(0.01);
        
        ImageView[][] tab = new ImageView[SIZE_X][SIZE_Y]; // tableau permettant de récupérer les cases graphiques lors du rafraichissement

        for (int i = 0; i < SIZE_X; i++) { // initialisation de la grille (sans image)
            for (int j = 0; j < SIZE_Y; j++) {
                ImageView img = new ImageView();
                
                tab[i][j] = img;
                
                grid.add(img, i, j);
            }
            
        }
        
        Observer o =  new Observer() { // l'observer observe l'obervable (update est exécuté dès notifyObservers() est appelé côté modèle )
            @Override
            public void update(Observable o, Object arg) {
                for (int i = 0; i < SIZE_X; i++) { // rafraichissement graphique
                    for (int j = 0; j < SIZE_Y; j++) {

                    	if (spm.getTab(i,j) == 0) { // spm est à la position i, j => le dessiner
                            tab[i][j].setImage(imbarriereFantome);
                            
                        }
                    	if (spm.getTab(i,j) == 1) { // spm est à la position i, j => le dessiner
                            tab[i][j].setImage(imbean);
                            
                        }
                    	if (spm.getTab(i,j) == 2) { // spm est à la position i, j => le dessiner
                            tab[i][j].setImage(imfantome_cyan);
                            
                        }
                    	if (spm.getTab(i,j) == 3) { // spm est à la position i, j => le dessiner
                            tab[i][j].setImage(imfantome_orange);
                            
                        }
                    	if (spm.getTab(i,j) == 4) { // spm est à la position i, j => le dessiner
                            tab[i][j].setImage(imfantome_rose);
                            
                        }
                    	if (spm.getTab(i,j) == 5) { // spm est à la position i, j => le dessiner
                            tab[i][j].setImage(imfantome_rouge);
                            
                        }
                    	if (spm.getTab(i,j) == 6) { // spm est à la position i, j => le dessiner
                            tab[i][j].setImage(imfantomeBleu);
                            
                        }
                    	if (spm.getTab(i,j) == 7) { // spm est à la position i, j => le dessiner
                            tab[i][j].setImage(imfantomeRose);
                            
                        }
                    	if (spm.getTab(i,j) == 8) { // spm est à la position i, j => le dessiner
                            tab[i][j].setImage(imfantomeRouge);
                            
                        }
                    	if (spm.getTab(i,j) == 9) { // spm est à la position i, j => le dessiner
                            tab[i][j].setImage(imfantomeVert);
                            
                        }
                    	if (spm.getTab(i,j) == 10) { // spm est à la position i, j => le dessiner
                            tab[i][j].setImage(imgros_bean);
                            
                        }
                    	if (spm.getTab(i,j) == 11) { // spm est à la position i, j => le dessiner
                            tab[i][j].setImage(immur);
                            
                        }
                    	if (spm.getTab(i,j) == 12) { // spm est à la position i, j => le dessiner
                            tab[i][j].setImage(impacman);
                            
                        }
                    	if (spm.getTab(i,j) == 13) { // spm est à la position i, j => le dessiner
                            tab[i][j].setImage(impacman);
                            
                        }
                    	if (spm.getTab(i,j) == 14) { // spm est à la position i, j => le dessiner
                            tab[i][j].setImage(impouvoir);
                            
                        }
                    	if (spm.getTab(i,j) == 15) { // spm est à la position i, j => le dessiner
                            tab[i][j].setImage(imvide);
                            
                        }
                    }
                }    
            }
        };
        
        
        spm.addObserver(o);
        spm.start(); // on démarre spm
        
        StackPane root = new StackPane();
        root.getChildren().add(grid);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("PAC MAN!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        root.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() { // on écoute le clavier
            

            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                if (event.isShiftDown()) {
                    spm.initXY(); // si on clique sur shift, on remet spm en haut à gauche
                }
            }
        });
        
        grid.requestFocus();
         
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
