import java.lang.*;
import java.util.*;

public class Treatment extends Thread 
{
	private SemaphoreTreatment treatment;
	private static Random randomT = new Random(2);
	private static DataCollection data;
	private static System currentTime;

	private static void moveToTreatment()
	{

		double treatmentTime = 600-100+2*100*randomT.nextFloat(); /* 60+-10 minutes, 1 minute = 100 ms*/
		try
		{
			sleep((long)treatmentTime);			
		}
		catch (InterruptedException e)
		{
			System.out.println(e);
		}
	}

	public Treatment(SemaphoreTreatment treatment1, DataCollection d)
	{
		treatment=treatment1;
		data=d;
		setDaemon(true);
		
	}
	
	public void run()
	{ 
		
		//move to treatment we should be able to get the treatment right away since no one can access it when it is in treatment
		treatment.doTreatment();  		
  		moveToTreatment();
  		treatment.releaseFromTreatment();
  		data.incrementTreatments();
    }
}
      		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	