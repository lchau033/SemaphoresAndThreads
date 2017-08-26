public class SemaphoreTreatment 
{
  	private int value;
  	private boolean inTreatment=false;

  	public SemaphoreTreatment(int v)
	{
		value = v;
	}
  
	public int getValue() 
	{ 
    	return value; 
	} 
  
  
    synchronized void take() 
    {
    	while(value <= 0  || this.inTreatment) 
    	{
      		try 
      		{
        		wait();
      		} 
      		catch(InterruptedException e) 
      		{
        		System.out.println(e);
      		}
    	}
    	value--;
  	}
    
    synchronized void doTreatment() 
    {
    	this.inTreatment=true;
    	while(value <= 0) 
    	{
      		try 
      		{
        		wait();
      		} 
      		catch(InterruptedException e) 
      		{
        		System.out.println(e);
      		}
    	}
    	value--;
  	}

	synchronized void release() 
	{ 		
			value=value+1;
			notifyAll();    
	}


	synchronized void releaseFromTreatment() 
	{ 		
			value=value+1;
			this.inTreatment=false;
			notifyAll();    
	}
}




