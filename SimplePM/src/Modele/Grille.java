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





/**
 *
 * @author coren
 */
public class Grille extends Observable implements Runnable {

    private HashMap<ME, Point> grilleDynamique;
    private HashMap<Point, MS> grilleStatique;
    private GestionStat score;
    private Boolean actif = true;

    public Grille(PacMan p, Fantome fR, Fantome fB, Fantome fV, Fantome fRo, int map) {
        actif = true;
        grilleDynamique = new HashMap<>();
        if (map == 0) {
            initialisation(p, fR, fB, fV, fRo);
        }
        else {
            initialisationSecret(p, fR, fB, fV, fRo);
        }
        Modele.ME.setActionImpossible ();
    }

    public void start() {
        new Thread(this).start();
        startThread();
    }

    private void startThread() {
        grilleDynamique.keySet().stream().forEach((me) -> { //parcourt l'ensemble des mod�les dynamiques de la grille
            me.start();
        });
    }
    @Override
    public void run() {
        
        while (keepGoing()) {
            setChanged ();
            notifyObservers ();
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {

            }
            
        }
    }
    
    public Boolean keepGoing (){
        return this.actif;
    }
    
    public void finDeJeu (){
        grilleDynamique.keySet().stream().forEach((me) -> { //parcourt l'ensemble des mod�les dynamiques de la grille
            me.stopJeu();
        });
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

    private void choixDeplacement(Action action, ME entiteDynamique) {
        switch (action) {
            case Bas:
                deplacementBas(entiteDynamique);
                break;
            case Haut:
                deplacementHaut(entiteDynamique);
                break;
            case Droite:
                deplacementDroite(entiteDynamique);
                break;
            case Gauche:
                deplacementGauche(entiteDynamique);
                break;
            default:
                break;
        }
    }

    private void deplacementPacman(PacMan pacman) {
        if (deplacementPossible(getPoint(pacman), pacman.getActionAFaire())) {
            choixDeplacement(pacman.getActionAFaire(), pacman);
            pacman.setAction(pacman.getActionAFaire());
        } else {
            if (deplacementPossible(getPoint(pacman), pacman.getAction())) {
                choixDeplacement(pacman.getAction(), pacman);
            }

        }

        mondeTorique(getPoint(pacman));
    }

    private void deplacementFantome(Fantome fantome) {
        switch (fantome.getCouleur()) {
            case "rouge":
                deplacementFantomeBleu(fantome);
                break;
            case "bleu":
                deplacementFantomeBleu(fantome);
                break;
            case "vert":
                deplacementFantomeBleu(fantome);
                break;
            case "rose":
                deplacementFantomeBleu(fantome);
                break;
        }

        mondeTorique(getPoint(fantome));
    }

    private void deplacementFantomeBleu(Fantome fantome) {
        if (deplacementPossible(grilleDynamique.get(fantome), fantome.getAction())) {
            choixDeplacement(fantome.getAction(), fantome);
            fantome.setAction(fantome.getAction());
        } else {
            Random rand = new Random();
            int a = rand.nextInt(4);
            switch (a) {
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
                    break;
            }
        }
    }

    public void deplacement(ME entiteDynamique) {
        if (entiteDynamique instanceof PacMan) {
            deplacementPacman((PacMan) entiteDynamique);
        } else {
            deplacementFantome((Fantome) entiteDynamique);
        }
    }

    public void remettrePacMandebut() {
        getPacman().setAction(Action.Droite);
        getPacman().setActionAFaire(Action.Droite);
        grilleDynamique.replace(getPacman(), new Point(1, 9));
    }

    private void mondeTorique(Point point) {
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

        switch (action) {
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
            if ("PorteFantome".equals(((Mur)grilleStatique.get(inter)).getTypeMur())){
                if (action!=Action.Haut)
                    possible = false;
            }else
                possible = false;
        }

        return possible;
    }

    public HashMap<ME, Point> getGrilleDynamique() {
        return grilleDynamique;
    }

    public HashMap<Point, MS> getGrilleStatique() {
        return grilleStatique;
    }

    public int getScore() {
        return this.score.getScore();
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

    public Fantome getFantomeRose() {
        for (ME me : grilleDynamique.keySet()) {
            if (me instanceof Fantome) {
                if ("rose".equals(((Fantome) me).getCouleur())) {
                    return (Fantome) me;
                }
            }

        }
        return null;
    }

    public Boolean pacmanMort() {
        Point point = grilleDynamique.get(getPacman());
        if (point.equals(grilleDynamique.get(getFantomeBleu()))) {
            return true;
        }
        if (point.equals(grilleDynamique.get(getFantomeVert()))) {
            return true;
        }
        if (point.equals(grilleDynamique.get(getFantomeRouge()))) {
            return true;
        }
        if (point.equals(grilleDynamique.get(getFantomeRose()))) {
            return true;
        }
        return false;
    }

    public GestionStat getGestionStat() {
        return this.score;
    }

    public void mangerFantome() {
        Point point = grilleDynamique.get(getPacman());
        if (point.equals(grilleDynamique.get(getFantomeBleu()))) {
            remettreDebut (getFantomeBleu ());
            augmenterScore(50);
        }

        if (point.equals(grilleDynamique.get(getFantomeVert()))) {
            remettreDebut (getFantomeVert ());
            augmenterScore(50);
        }

        if (point.equals(grilleDynamique.get(getFantomeRouge()))) {
            remettreDebut (getFantomeRouge ());
            augmenterScore(50);
        }

        if (point.equals(grilleDynamique.get(getFantomeRose()))) {
            remettreDebut (getFantomeRose ());
            augmenterScore(50);
        }

    }
    
    public void remettreDebut (Fantome fantome){
        grilleDynamique.replace(fantome, new Point(11, 9));
    }


    public void passeSurNourriture() {
        Point point = new Point(grilleDynamique.get(getPacman()));

        if (grilleStatique.get(point) instanceof Couloir) {
            if (!((Mangeable) grilleStatique.get(point)).getEstMange()) {
                ((Mangeable) grilleStatique.get(point)).estMange();
                augmenterScore(10);
                score.diminuerBean();
            }

        }

        if (grilleStatique.get(point) instanceof GrosBean) {
            if (!((Mangeable) grilleStatique.get(point)).getEstMange()) {
                ((Mangeable) grilleStatique.get(point)).estMange();
                augmenterScore(20);
                Fantome.setMangeable();
                score.diminuerBean();
            }
        }
    }

    private void initialisation(PacMan p, Fantome fR, Fantome fB, Fantome fV, Fantome fRo) {
        CreationTerrain creationTerrain = new CreationTerrain("src/Map/Map1.txt");
        score = new GestionStat(creationTerrain.getNbBean());
        grilleStatique = creationTerrain.getHashMap();

        grilleDynamique = new HashMap<>();
        grilleDynamique.put(p, new Point(1, 9));
        grilleDynamique.put(fR, new Point(10, 9));
        grilleDynamique.put(fB, new Point(9, 9));
        grilleDynamique.put(fV, new Point(11, 9));
        grilleDynamique.put(fRo, new Point(10, 9));   
        
    }
    
    private void initialisationSecret(PacMan p, Fantome fR, Fantome fB, Fantome fV, Fantome fRo) {
        CreationTerrain creationTerrain = new CreationTerrain("src/Map/Map2.txt");
        score = new GestionStat(creationTerrain.getNbBean());
        grilleStatique = creationTerrain.getHashMap();

       if (!grilleDynamique.isEmpty()) {
            grilleDynamique.remove(p);
            grilleDynamique.remove(fR);
            grilleDynamique.remove(fB);
            grilleDynamique.remove(fV);
            grilleDynamique.remove(fRo);
            
            p.setAction(Action.Droite);
            
            Fantome.setNonMangeable ();
        }

        grilleDynamique = new HashMap<>();
        grilleDynamique.put(p, new Point(1, 9));
        grilleDynamique.put(fR, new Point(10, 9));
        grilleDynamique.put(fB, new Point(9, 9));
        grilleDynamique.put(fV, new Point(11, 9));
        grilleDynamique.put(fRo, new Point(10, 9));     
        
    }

    public int getVie() {
        return score.getVie();
    }
    
    public void augmenterScore (int augmentation){
        this.score.augmenterScore(augmentation);
    }


    public int getBean() {
        return score.getNbBean();
    }
    
    public void finJeu (){
        grilleDynamique.keySet().stream().forEach((me) -> { //parcourt l'ensemble des mod�les dynamiques de la grille
            me.stopJeu();
        });
    }
    
}
