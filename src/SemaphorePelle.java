public class SemaphorePelle 
{
  	private int value;

  	public SemaphorePelle(int value)
	{
  		this.value=value;
	}
  	
  	public int getValue(){
  		return value;
  	}
  
  
    synchronized void take() 
    {
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
}




