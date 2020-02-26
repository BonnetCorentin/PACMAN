/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueControleur;

import Modele.SimplePacMan;
import java.util.Hashtable;
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
        
        Hashtable<String,Image> ensembleImage=new Hashtable<String, Image>();
        
        // Pacman.svg.png
        
        ensembleImage.put("barriereFantome",new Image("images/barriereFantome.png"));// préparation des images
        ensembleImage.put("bean",new Image("images/bean.png"));
        ensembleImage.put("fan_mangeable",new Image("images/fan_mangeable.png"));
        ensembleImage.put("fan_mangeable",new Image("images/fan_mangeable.png"));
        ensembleImage.put("fantome_cyan",new Image("images/fan_cyan.png"));
        ensembleImage.put("fantome_orange",new Image("images/fantome_orange.png"));
        ensembleImage.put("fantome_rose",new Image("images/fantome_rose.png"));
        ensembleImage.put("fantome_rouge",new Image("images/fantome_rouge.png"));
        ensembleImage.put("fantomeBleu",new Image("images/fantomeBleu.png"));// préparation des images
        ensembleImage.put("fantomePeur0",new Image("images/fantomePeur0.png"));
        ensembleImage.put("fantomePeur1",new Image("images/fantomePeur1.png"));
        ensembleImage.put("fantomeRose",new Image("images/fantomeRose.png"));
        ensembleImage.put("fantomeRouge",new Image("images/fantomeRouge.png"));// préparation des images
        ensembleImage.put("fantomeVert",new Image("images/fantomeVert.png"));
        ensembleImage.put("gros_bean",new Image("images/gros_bean.png"));
        ensembleImage.put("Mort0",new Image("images/Mort0.png"));
        ensembleImage.put("Mort1",new Image("images/Mort1.png"));
        ensembleImage.put("Mort2",new Image("images/Mort2.png"));
        ensembleImage.put("Mort3",new Image("images/Mort3.png"));
        ensembleImage.put("mur",new Image("images/mur.png"));// préparation des images
        ensembleImage.put("pacman",new Image("images/pacman.png"));
        ensembleImage.put("pacman_2",new Image("images/pacman_2.png"));
        ensembleImage.put("pacman_2f",new Image("images/pacman_2f.png"));
        ensembleImage.put("pacman_3",new Image("images/pacman_3.png"));
        ensembleImage.put("pacman_3f",new Image("images/pacman_3f.png"));
        ensembleImage.put("pacman_4",new Image("images/pacman_4.png"));
        ensembleImage.put("pacman_4f",new Image("images/pacman_4f.png"));
        ensembleImage.put("pacman_f",new Image("images/pacman_f.png"));
        ensembleImage.put("pacmanBas0",new Image("images/pacmanBas0.png"));// préparation des images
        ensembleImage.put("pacmanBas1",new Image("images/pacmanBas1.png"));
        ensembleImage.put("pacmanDroite0",new Image("images/pacmanDroite0.png"));
        ensembleImage.put("pacmanDroite1",new Image("images/pacmanDroite1.png"));
        ensembleImage.put("pacmanGauche0",new Image("images/pacmanGauche0.png"));
        ensembleImage.put("pacmanGauche1",new Image("images/pacmanGauche1.png"));
        ensembleImage.put("pacmanHaut0",new Image("images/pacmanHaut0.png"));// préparation des images
        ensembleImage.put("pacmanHaut1",new Image("images/pacmanHaut1.png"));
        ensembleImage.put("pouvoir",new Image("images/pouvoir.png"));
        ensembleImage.put("vide",new Image("images/vide.png"));
                
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
        
        iamge(ensembleimage.get("vide").getPath());
        
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
