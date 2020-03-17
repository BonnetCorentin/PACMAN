package Modele;

public class Mangeable extends MS{
	private boolean estMange = false;
        
        public void estMange (){
            estMange = true;
        }
        
        public Boolean getEstMange (){
            return estMange;
        }
}
