/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 *
 * @author coren
 */
public class Grille extends Observable implements Runnable {

    private HashMap<ME, Point> grilleDynamique;
    private HashMap<Point, MS> grilleStatique;
    private GestionStat score;

    public Grille(PacMan p,Fantome fR,Fantome fB,Fantome fV) {
        grilleDynamique = new HashMap <>();
        initialisation (p,fR,fB,fV);
    }

    public void start() {
        new Thread(this).start();
        startThread ();
    }
    
    private void startThread (){
        grilleDynamique.keySet().stream().forEach((me) -> { //parcourt l'ensemble des modèles dynamiques de la grille
                me.start (); 
        });
    }
    
    @Override
    public void run() {
        final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        Runnable processDataCmd = new Runnable() {
            @Override
            public void run() {
                passeSurNourriture();
                if (pacmanMort ()){
                    score.setVie();
                    remettrePacMandebut ();
                }
                setChanged();
                notifyObservers(); // notification de l'observer
            }
        };
        service.scheduleAtFixedRate(processDataCmd, 0, 100, TimeUnit.MILLISECONDS); //sleep
    }

    public Point getvalueGD(Point p) {
        return this.grilleDynamique.get(p);
    }

    public MS getvalueGS(Point p) {
        return this.grilleStatique.get(p);
    }

    private void deplacementBas(ME entiteDynamique) {
        Point point = getPoint(entiteDynamique);
        point.y++;

        grilleDynamique.replace(entiteDynamique, point);
        
    }

    private void deplacementHaut(ME entiteDynamique) {
        Point point = getPoint(entiteDynamique);
        point.y--;

        grilleDynamique.replace(entiteDynamique, point);
        
    }

    private void deplacementDroite(ME entiteDynamique) {
        Point point = getPoint(entiteDynamique);
        point.x++;

        grilleDynamique.replace(entiteDynamique, point);
        
    }

    private void deplacementGauche(ME entiteDynamique) {
        Point point = getPoint(entiteDynamique);
        point.x--;
        
        grilleDynamique.replace(entiteDynamique, point);
    }
    
    private void choixDeplacement (Action action,ME entiteDynamique){
        switch (action){
                    case Bas:
                        deplacementBas (entiteDynamique);
                    break;
                    case Haut:
                        deplacementHaut (entiteDynamique);
                    break;
                    case Droite:
                        deplacementDroite (entiteDynamique);
                    break;
                    case Gauche:
                        deplacementGauche (entiteDynamique);
                    break;
                    default:
                    break;
                }
    }
    
    private void deplacementPacman (PacMan pacman){
        if (deplacementPossible (getPoint (pacman),pacman.getActionAFaire())){
            choixDeplacement (pacman.getActionAFaire(),pacman);
            pacman.setAction(pacman.getActionAFaire());
        }
        else {
            if (deplacementPossible (getPoint (pacman),pacman.getAction())){
                choixDeplacement(pacman.getAction(),pacman);
            }
                
        }
        
        mondeTorique (getPoint (pacman));
    }
    
    private void deplacementFantome (Fantome fantome){
        switch (fantome.getCouleur()){
            case "rouge":
                deplacementFantomeBleu (fantome);
            break;
            case "bleu":
                deplacementFantomeBleu (fantome);
            break;
            case "vert":
                deplacementFantomeBleu (fantome);
            break;
        }
        
        mondeTorique (getPoint (fantome));
    }
    
    private void deplacementFantomeBleu (Fantome fantome){
        if (deplacementPossible (grilleDynamique.get(fantome),fantome.getAction())){
            choixDeplacement (fantome.getAction(),fantome);
            fantome.setAction (fantome.getAction());            
        }
        else {
            Random rand = new Random ();
            int a = rand.nextInt(4);
            switch (a){
                case 0:
                    fantome.setAction(Action.Haut);
                break;
                case 1: 
                    fantome.setAction(Action.Gauche);
                break;
                case 2:
                    fantome.setAction(Action.Bas);
                break;
                case 3:
                    fantome.setAction(Action.Droite);
                break;
                default:
                    System.out.println ("a");
                break;
            }
        }
    }
    
    public void deplacement (ME entiteDynamique){
        if (entiteDynamique instanceof PacMan)
            deplacementPacman ((PacMan)entiteDynamique);
        else
            deplacementFantome ((Fantome)entiteDynamique);
    }
    
    public void remettrePacMandebut (){
        grilleDynamique.replace(getPacman (),new Point (1,9));
    }

    private void mondeTorique(Point point){
        if (point.y == 9 && point.x == 20) {
            point.x = 0;
        }
        if (point.y == 9 && point.x == -1) {
            point.x = 19;
        }
    }
    
    public void retirerDeLenvironnement(ME me) {
        grilleDynamique.remove(me);
    }

    private Point getPoint(ME entiteDynamqique) {
        Point point = grilleDynamique.get(entiteDynamqique);
        return point;
    }

    private Boolean deplacementPossible(Point point, Action action) {
        Boolean possible = true;
        Point inter = new Point(point.x, point.y);
        
        switch (action){
            case Bas:
                inter.y++;
            break;
            case Haut:
                inter.y--;
            break;
            case Droite:
                inter.x++;
            break;
            case Gauche:
                inter.x--;
            break;
            default:
                    break;
        }

        if (grilleStatique.get(inter) instanceof Mur) {
            possible = false;
        }     
        
        return possible;
    }

    public HashMap<ME, Point> getGrilleDynamique() {
        return grilleDynamique;
    }
    
    public int getScore (){
        return this.score.getScore ();
    }

    public PacMan getPacman() {
        for (ME me : grilleDynamique.keySet()) {
            if (me instanceof PacMan) {
                return (PacMan) me;
            }

        }
        return null;
    }

    public Fantome getFantomeRouge() {
        for (ME me : grilleDynamique.keySet()) {
            if (me instanceof Fantome) {
                if ("rouge".equals(((Fantome) me).getCouleur())) {
                    return (Fantome) me;
                }
            }

        }
        return null;
    }
    
    public Fantome getFantomeVert() {
        for (ME me : grilleDynamique.keySet()) {
            if (me instanceof Fantome) {
                if ("vert".equals(((Fantome) me).getCouleur())) {
                    return (Fantome) me;
                }
            }

        }
        return null;
    }

    public Fantome getFantomeBleu() {
        for (ME me : grilleDynamique.keySet()) {
            if (me instanceof Fantome) {
                if ("bleu".equals(((Fantome) me).getCouleur())) {
                    return (Fantome) me;
                }
            }

        }
        return null;
    }

    private void passeSurNourriture() {
        Point point = new Point(grilleDynamique.get(getPacman ()));

        if (grilleStatique.get(point) instanceof Couloir) {
            if (!((Mangeable) grilleStatique.get(point)).getEstMange()){
                ((Mangeable) grilleStatique.get(point)).estMange();
                score.augmenterScore(10);
            }
            
        }
    }
    
    public Boolean pacmanMort (){
        Point point=grilleDynamique.get(getPacman ());
        if (point.equals(grilleDynamique.get(getFantomeBleu())))
            return true;
        if (point.equals(grilleDynamique.get(getFantomeVert())))
            return true;
        if (point.equals(grilleDynamique.get(getFantomeRouge())))
            return true;
        return false;
    }
    
    
    public void redemarrer (PacMan p,Fantome fR,Fantome fB,Fantome fV){
        initialisation (p,fR,fB,fV);
    }
    
    private void initialisation (PacMan p,Fantome fR,Fantome fB,Fantome fV){
        score = new GestionStat ();
        CreationTerrain creationTerrain = new CreationTerrain();
        grilleStatique = creationTerrain.getHashMap();
        
        if (!grilleDynamique.isEmpty()){
            grilleDynamique.remove (p);
            grilleDynamique.remove (fR);
            grilleDynamique.remove (fB);
            grilleDynamique.remove (fV);
        }
        
        grilleDynamique = new HashMap<>();
        grilleDynamique.put(p, new Point(1,9));
        grilleDynamique.put(fR, new Point(10,7));
        grilleDynamique.put(fB, new Point(9,7));
        grilleDynamique.put(fV, new Point(11,9));
             
    }
    
    public int getVie (){
        return score.getVie();
    }
}
