import java.lang.*;
import java.util.*;

public class CreateThread extends Thread
{
	private static SemaphorePelle pelle1, pelle2;
	private static SemaphoreTreatment traitement1, traitement2;			
	private static DataCollection Data;
	private static System mySystem;
	private static Random r;
	private static long startTime;
	private static boolean finished=false;
	private static boolean finishThread=false;
	private static long endTime;
	// pour la question 2
	private static int numberOfTrucks=8;
	
	//pour la question 5
	//private static int numberOfTrucks=10;
	
	public CreateThread(SemaphorePelle premierePelle, SemaphorePelle deuxiemePelle, SemaphoreTreatment stationTraitement1, SemaphoreTreatment stationTraitement2,DataCollection d, Random r1)
	{
		pelle1=premierePelle;
		pelle2=deuxiemePelle;
		traitement1=stationTraitement1;
		traitement2=stationTraitement2;
		Data = d;
		r=r1;
		
	}



	public void run()
	{
		
		startTime = 0;
		endTime = 0;
		Routine routine;
		startTime = mySystem.currentTimeMillis();
		for (int i=0; i<numberOfTrucks; i++)
		{
			 routine=new Routine(pelle1,pelle2,traitement1,traitement2,i,Data,false);
			 routine.start();
		}
		
			
		while(endTime-startTime<5400){
			endTime=mySystem.currentTimeMillis();
		}

		System.out.println("Simulation time is over..." );
		System.out.println(" " );
		System.out.println("-------------------------Simulation Results -------------------------" );
		System.out.println(" " );
		Data.Report();
		System.out.println(" " );
		System.out.println("Simulation is successful!!!" );
		
	}
	
}
			
			
			
			
			
		
		

