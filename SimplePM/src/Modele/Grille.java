/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
;

/**
 *
 * @author coren
 */
public class Grille extends Observable implements Runnable {

    final private HashMap<ME, Point> grilleDynamique;
    final private HashMap<Point, MS> grilleStatique;
    private GestionStat score;

    public Grille(PacMan p,Fantome fR,Fantome fB,Fantome fV) {
        score = new GestionStat ();
        CreationTerrain creationTerrain = new CreationTerrain();
        grilleStatique = creationTerrain.getHashMap();
       

        grilleDynamique = new HashMap<>();
        grilleDynamique.put(p, new Point(1, 9));
        grilleDynamique.put(fR, new Point(10, 9));
        grilleDynamique.put(fB, new Point(9, 9));
        grilleDynamique.put(fV, new Point(11, 9));
        

    }

    public void start() {
        new Thread(this).start();
        grilleDynamique.keySet().stream().forEach((me) -> { //parcourt l'ensemble des modèles dynamiques de la grille
            me.start();
        });
    }

    @Override
    public void run() {
        final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        Runnable processDataCmd = new Runnable() {
            @Override
            public void run() {
                passeSurNourriture();
                if (pacmanMort ())
                    getPacman ().setActif();
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

    public void deplacementBas(ME entiteDynamique) {
        Point point = getPoint(entiteDynamique);
        Point inter = new Point(point.x, point.y);
        inter.y++;

        if (deplacementPossible(inter)) {
            grilleDynamique.replace(entiteDynamique, inter);
        } else {
            if (entiteDynamique instanceof Fantome) {
                ((Fantome) entiteDynamique).deplacementAleatoire();
            }
        }
    }

    public void deplacementHaut(ME entiteDynamique) {
        Point point = getPoint(entiteDynamique);
        Point inter = new Point(point.x, point.y);
        inter.y--;

        if (deplacementPossible(inter)) {
            grilleDynamique.replace(entiteDynamique, inter);
        } else {
            if (entiteDynamique instanceof Fantome) {
                ((Fantome) entiteDynamique).deplacementAleatoire();
            }
        }
    }

    public void deplacementDroite(ME entiteDynamique) {
        Point point = getPoint(entiteDynamique);
        Point inter = new Point(point.x, point.y);
        inter.x++;

        if (deplacementPossible(inter)) {
            grilleDynamique.replace(entiteDynamique, inter);
        } else {
            if (entiteDynamique instanceof Fantome) {
                ((Fantome) entiteDynamique).deplacementAleatoire();
            }
        }
    }

    public void deplacementGauche(ME entiteDynamique) {
        Point point = getPoint(entiteDynamique);
        Point inter = new Point(point.x, point.y);
        inter.x--;

        if (deplacementPossible(inter)) {
            grilleDynamique.replace(entiteDynamique, inter);
        } else {
            if (entiteDynamique instanceof Fantome) {
                ((Fantome) entiteDynamique).deplacementAleatoire();
            }
        }
    }

    public void retirerDeLenvironnement(ME me) {
        grilleDynamique.remove(me);
    }

    private Point getPoint(ME entiteDynamqique) {
        Point point = grilleDynamique.get(entiteDynamqique);
        return point;
    }

    private Boolean deplacementPossible(Point point) {
        Boolean possible = true;

        if (grilleStatique.get(point) instanceof Mur) {
            possible = false;
        }

        if (point.y == 9 && point.x == 20) {
            point.x = 0;
        }
        if (point.y == 9 && point.x == -1) {
            point.x = 19;
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
}
