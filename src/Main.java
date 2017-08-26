import java.lang.*;
import java.util.*;

public class Main 
{
	public static SemaphorePelle pelle1 = new SemaphorePelle(1);		/* pour la première pelle */
	public static SemaphorePelle pelle2 = new SemaphorePelle(1);      /* pour la deuxième pelle */
	public static SemaphoreTreatment traitement1 = new SemaphoreTreatment(1);
	public static SemaphoreTreatment traitement2= new SemaphoreTreatment(1);
	/* j'ai décidé de gérer chacune des stations de traitement individuellement pour savoir 
	 * quand l'une d'elle peut commencer son traitement et quand chacune d'elles est en traitement*/

	public static void main(String[] arg)
	{
		Random r=new Random();		
		DataCollection dc = new DataCollection();		Thread S = new CreateThread(pelle1, pelle2,traitement1,traitement2,dc,r);
		S.start();
		System.out.println("Simulation started... " );
	}
}

