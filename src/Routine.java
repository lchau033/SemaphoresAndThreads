import java.lang.*;
import java.util.*;

public class Routine extends Thread 
{
	private static SemaphorePelle pelle1, pelle2; 
	private static SemaphoreTreatment traitement1, traitement2;
	private static DataCollection Data;
	private static System currentTime;
	private static Random randomR = new Random(1);
	private static int countNumberTrucks1=0;
	private static int countNumberTrucks2=5;
	private static int countPelle1=0;
	private static int countPelle2=0;
	private boolean comingFromTreatment;
	private static boolean premierePelle=true;
	private int value;
	
	// méthode pour le remplissage des camions par une pelle
	private static void moveToShovel()
	{
		double pelle1Time = 100-50+2*50*randomR.nextFloat(); /* 10+-5 minutes, 1 minute = 100 ms*/
		try
		{
			sleep((long)pelle1Time);			
		}
		catch (InterruptedException e)
		{
			System.out.println(e);
		}
	}

	
	public Routine(SemaphorePelle premierePelle, SemaphorePelle deuxiemePelle, SemaphoreTreatment stationTraitement1, SemaphoreTreatment stationTraitement2, int value,DataCollection d,boolean comingFromTreatment)
	{
		pelle1=premierePelle;
		pelle2=deuxiemePelle;
		traitement1=stationTraitement1;
		traitement2=stationTraitement2;
		this.comingFromTreatment=comingFromTreatment;
		this.value=value;
		Data = d;
        setDaemon(true);
        

	}
	
	public void run()
	{
		long tp10, tp11, tp20, tp21, tt0, tt1;
		double travelTime1 = 200-50+2*50*randomR.nextFloat(); /* 20+-5 minutes */
		double travelTime2 = 400-100+2*100*randomR.nextFloat(); /* 40+-10 minutes */
		Treatment t;
		
		//pour la question 2
		if(value%2==0)
		// pour la question 4
		//if(value==0 || value==2 || value==4)
		// pour la question 5
		//if(value==1 || value==2 || value==3 || value==4)
		{
			// si on vient de la station de traitement on endort le thread pour le trajet de la station à la première pelle 
			if(comingFromTreatment){
				try{
					sleep((long)travelTime1);
				}
				catch(InterruptedException e){
					System.out.println(e);
				}
				
			}
			//on bouge à la première pelle
			tp10=currentTime.currentTimeMillis();
			if(pelle1.getValue()<=0){
				Data.getPelle1WQ();
			}
			pelle1.take();
			tp11=currentTime.currentTimeMillis();
			Data.getPelle1WT(tp11-tp10);
			moveToShovel();
			pelle1.release();
			Data.incrementTrucks();
			// on fait le trajet à la station de traitement 
			try
			{
				sleep((long)travelTime1);
			}
			catch (InterruptedException e){
				System.out.println(e);
			}
		}
		else{
			// si on vient de la station de traitement on endort le thread pour le trajet de la station à la deuxième pelle 
			if(comingFromTreatment){
				try{
					sleep((long)travelTime2);
				}
				catch(InterruptedException e){
					System.out.println(e);
				}
			}
			//on bouge à la deuxième pelle
			tp20=currentTime.currentTimeMillis();
			if(pelle2.getValue()<=0){
				Data.getPelle2WQ();
			}
			pelle2.take();
			tp21=currentTime.currentTimeMillis();
			Data.getPelle2WT(tp21-tp20);
			moveToShovel();
			pelle2.release();
			Data.incrementTrucks();
			
			// on fait le trajet de la deuxième pelle à la station de traitement
			try
			{
				sleep((long)travelTime2);
			}
			catch (InterruptedException e){
				System.out.println(e);
			}
		}
  		
  		//on bouge à la station de traitement
		/* Les stations de traitement alternent, on commence par remplir la première donc countNumberTrucks1=0 et l'autre 
		 * station commence avec countNumberTrucks2=5, puis lorsque la première station est remplie et que countNumberTrucks1=5, 
		 * on initialise countNumberTruck2=0, puis lorsque la deuxième station est remplie, on recommence à remplir la première 
		 * en initialisant countNumberTrucks1=0 et ainsi de suite 
		 */
		tt0=currentTime.currentTimeMillis();
		if(countNumberTrucks1<5)
		{
			if(traitement1.getValue()<=0){
				Data.getTraitementWQ();
			}
			traitement1.take();
			tt1=currentTime.currentTimeMillis();
			Data.getTraitementWT(tt1-tt0);
			traitement1.release();
			countNumberTrucks1++;
			// on vérifie s'il est temps d'effectuer un traitement
			if(countNumberTrucks1==5){
				// on mets la station en traitement, on commence le traitement et on réinitialise le nombre de camions déchargé dans la station 2
				t=new Treatment(traitement1,Data);
				t.start();
				countNumberTrucks2=0;
			}
		}
		else if(countNumberTrucks2<5){
			if(traitement2.getValue()<=0){
				Data.getTraitementWQ();
			}
			traitement2.take();
			tt1=currentTime.currentTimeMillis();
			Data.getTraitementWT(tt1-tt0);
			traitement2.release();
			countNumberTrucks2++;
			// on vérifie s'il est temps d'effectuer un traitement
			if(countNumberTrucks2==5){
				// on mets la station en traitement, on commence le traitement et on réinitialise le nombre de camions déchargé dans la station 1
				t=new Treatment(traitement2, Data);
				t.start();
				countNumberTrucks1=0;
			}
		}
		Routine r=new Routine(pelle1,pelle2,traitement1,traitement2,value,Data,true);
		r.start();
    }
	}