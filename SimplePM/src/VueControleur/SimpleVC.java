/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueControleur;

import java.awt.Point;
import Modele.Couloir;
import Modele.Grille;
import Modele.Images;
import Modele.MS;
import Modele.Mur;
import Modele.SimplePacMan;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
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
    Hashtable<String,Images> ensembleImage=new Hashtable<String, Images>();
    
    @Override
    
    public void start(Stage primaryStage) {
        
        // Pacman.svg.png
        ensembleImage.put("barriereFantome",new Images("images/barriereFantome.png"));// préparation des images
        ensembleImage.put("bean",new Images("images/bean.png"));
        ensembleImage.put("fan_mangeable",new Images("images/fan_mangeable.png"));
        ensembleImage.put("fan_mangeable",new Images("images/fan_mangeable.png"));
        ensembleImage.put("fantome_cyan",new Images("images/fan_cyan.png"));
        ensembleImage.put("fantome_orange",new Images("images/fantome_orange.png"));
        ensembleImage.put("fantome_rose",new Images("images/fantome_rose.png"));
        ensembleImage.put("fantome_rouge",new Images("images/fantome_rouge.png"));
        ensembleImage.put("fantomeBleu",new Images("images/fantomeBleu.png"));// préparation des images
        ensembleImage.put("fantomePeur0",new Images("images/fantomePeur0.png"));
        ensembleImage.put("fantomePeur1",new Images("images/fantomePeur1.png"));
        ensembleImage.put("fantomeRose",new Images("images/fantomeRose.png"));
        ensembleImage.put("fantomeRouge",new Images("images/fantomeRouge.png"));// préparation des images
        ensembleImage.put("fantomeVert",new Images("images/fantomeVert.png"));
        ensembleImage.put("gros_bean",new Images("images/gros_bean.png"));
        ensembleImage.put("Mort0",new Images("images/Mort0.png"));
        ensembleImage.put("Mort1",new Images("images/Mort1.png"));
        ensembleImage.put("Mort2",new Images("images/Mort2.png"));
        ensembleImage.put("Mort3",new Images("images/Mort3.png"));
        ensembleImage.put("mur",new Images("images/mur.png"));// préparation des images
        ensembleImage.put("pacman",new Images("images/pacman.png"));
        ensembleImage.put("pacman_2",new Images("images/pacman_2.png"));
        ensembleImage.put("pacman_2f",new Images("images/pacman_2f.png"));
        ensembleImage.put("pacman_3",new Images("images/pacman_3.png"));
        ensembleImage.put("pacman_3f",new Images("images/pacman_3f.png"));
        ensembleImage.put("pacman_4",new Images("images/pacman_4.png"));
        ensembleImage.put("pacman_4f",new Images("images/pacman_4f.png"));
        ensembleImage.put("pacman_f",new Images("images/pacman_f.png"));
        ensembleImage.put("pacmanBas0",new Images("images/pacmanBas0.png"));// préparation des images
        ensembleImage.put("pacmanBas1",new Images("images/pacmanBas1.png"));
        ensembleImage.put("pacmanDroite0",new Images("images/pacmanDroite0.png"));
        ensembleImage.put("pacmanDroite1",new Images("images/pacmanDroite1.png"));
        ensembleImage.put("pacmanGauche0",new Images("images/pacmanGauche0.png"));
        ensembleImage.put("pacmanGauche1",new Images("images/pacmanGauche1.png"));
        ensembleImage.put("blanc",new Images("images/blanc.png"));
        ensembleImage.put("pacmanHaut0",new Images("images/pacmanHaut0.png"));// préparation des images
        ensembleImage.put("pacmanHaut1",new Images("images/pacmanHaut1.png"));
        ensembleImage.put("pouvoir",new Images("images/pouvoir.png"));
  
        for (int i = 0; i < SIZE_Y; i++) { // initialisation de la grille (sans image)
            for (int j = 0; j < SIZE_X; j++) {
                ImageView img = new ImageView();
                
                tab[i][j] = img;
                
                grid.add(img, j, i);
            }
        }
        Button button1 = new Button("Start");
        button1.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
        grid.add(button1,30,0);
        Button button2 = new Button("Quitter");
        button2.setStyle("-fx-font: 14 arial; -fx-base: #b6e7c9;");
        grid.add(button2,30,20);
        
        TextureInit();
        
        Observer o =  new Observer() { // l'observer observe l'obervable (update est exécuté dès notifyObservers() est appelé côté modèle )
            @Override
            public void update(Observable o, Object arg) {
                TextureInit ();
            }
        };
        
        
        grille.addObserver(o);
        grille.start(); // on démarre spm
        
        StackPane root = new StackPane();
        root.getChildren().add(grid);
        
        Scene scene = new Scene(root, 1000, 1000);
        
        primaryStage.setTitle("PAC MAN!");
        primaryStage.setScene(scene);
        primaryStage.show();
        root.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() { // on écoute le clavier
            

            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                if (event.isShiftDown()) {
                    //grille.initXY(); // si on clique sur shift, on remet spm en haut à gauche
                }
            }
        });
        
        grid.requestFocus();
         
        
        
    }
    
    public void TextureInit(){
        for (int i=0; i<SIZE_Y; i++){

            for(int j=0; j<SIZE_X;j++){
            
                Point point = new Point(j,i);
                MS ms = grille.getvalueGS(point);
                
                if(ms instanceof Couloir){  
                    ImageView img;
                    img = new ImageView(ensembleImage.get("bean").getPath());
               
                    tab[i][j] = img;
                
                    grid.add(tab[i][j], j, i);
               }
                else if (ms instanceof Mur){
                    ImageView img;
         
                    img = new ImageView(ensembleImage.get("mur").getPath());
                    tab[i][j] = img;

                    grid.add(tab[i][j], j, i);
                }
                else {
                    ImageView img;
                    img = new ImageView(ensembleImage.get("blanc").getPath());
                    tab[i][j] = img;
             
                    grid.add(tab[i][j], j, i);
                }
            }
        }
    }
    
    /*public Class<? extends Hashtable> getvalue(Point p){
        return this.ensembleImage.getClass();
    }*/

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
