import java.lang.*;
import java.util.*;

public class DataCollection
{
	private double debitTraitementCamion = 0;
	private int numTraitement = 0;	
	private int numTrucks=0;
	private int pelle1WQ = 0, pelle2WQ = 0,traitementWQ = 0;
	private long pelle1WaitTime = 0;
	private long pelle2WaitTime = 0;
	private long traitementWaitTime = 0;
	private long StartTime, EndTime;
	private System rightNow;

	public DataCollection()
	{
		StartTime = rightNow.currentTimeMillis();
	}
	
	//méthode pour obteir le nombre de traitements
	public synchronized void incrementTreatments()
	{
		numTraitement++;
	}
	
	//method to get the number of truck loads
	public synchronized void incrementTrucks(){
		numTrucks++;
	}
	
	//méthodes pour le temps d'attente
	public synchronized void getPelle1WT(long t)
	{
		pelle1WaitTime += t;
	}
	public synchronized void getPelle2WT(long t)
	{
		pelle2WaitTime += t;
	}
	public synchronized void getTraitementWT(long t)
	{
		traitementWaitTime += t;
	}
	//méthodes pour les queux
	public synchronized void getPelle1WQ()
	{		
		this.pelle1WQ++;
	}
	public synchronized void getPelle2WQ()
	{		
		this.pelle2WQ++;
	}
	public synchronized void getTraitementWQ()
	{		
		this.traitementWQ++;
	}
	
	public synchronized void Report()
	{
		EndTime = rightNow.currentTimeMillis();
		double simTime= ((double)(EndTime-StartTime)/600); 
		this.debitTraitementCamion=(double)this.numTraitement*5/ (simTime);
		
        System.out.println("Simulation Time = " + simTime);
        
        System.out.println(" " );
        System.out.println("Le débit moyen de traitement de camion = " + this.debitTraitementCamion);
		System.out.println("Le nombre de chargement par heure = "+ this.numTrucks/simTime);
		System.out.println(" " );
		
		System.out.println(" " );
		
		System.out.println("Le temps d'attente moyen de la première pelle = " + ((double)this.pelle1WaitTime / this.pelle1WQ)/100);
		System.out.println("Le temps d'attente moyen de la deuxième pelle = " + ((double)this.pelle2WaitTime / this.pelle2WQ)/100);
		System.out.println("Le temps d'attente moyen des stations de traitement = " + ((double)this.traitementWaitTime / this.traitementWQ / 2 )/simTime);
		
		System.out.println(" " );
		
	}
}
