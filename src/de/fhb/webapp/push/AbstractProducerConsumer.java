package de.fhb.webapp.push;

import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public abstract class AbstractProducerConsumer<DATA> implements Runnable
{
	public static final int JOIN_WAIT = 1000;
	public static final int BUFFER_SIZE = 1000;

	public static final int STATE_RUN = 1;
	public static final int STATE_KILL = 2;

	protected RingBuffer ringBuffer;
	protected Timer timer;
	protected Thread threadProcess;
	protected int state = STATE_RUN;

	public AbstractProducerConsumer()
	{
		this(BUFFER_SIZE, false);
	}
	
	public AbstractProducerConsumer(int nBufferSize, boolean bOverwrite)
	{
		ringBuffer = new RingBuffer(nBufferSize, bOverwrite);
		
		timer = new Timer("Delayed Process");
		
		threadProcess = new Thread(this, getClass().getSimpleName());
		threadProcess.start();
	}
	
	public boolean pushAll(List<DATA> listData) 
	{
		if (listData == null)
			return false;
			
		for (Iterator<DATA> i = listData.iterator(); i.hasNext(); )
		{
			if (!pushOne(i.next()))
				return false;
			i.remove();
		}
		
		return true;
	}
	
	public boolean pushOne(DATA typeData) 
	{
		synchronized(ringBuffer)
        {
			if (!ringBuffer.pushData(typeData))
				return false;

			ringBuffer.notifyAll();
			return true;
        }
	}

	public boolean pushOne(final DATA typeData, final long lDelayedProcess) 
	{
		synchronized(ringBuffer)
        {
			if (!ringBuffer.pushData(typeData))
				return false;
			
			// reschedule
			timer.purge();
			timer.schedule(new TimerTask() 
					{
						public void run()
						{
							synchronized(ringBuffer)
							{
								ringBuffer.notifyAll();
							}
						}
					}, lDelayedProcess);
        }
			
		return true;
	}

	public int getSize()
	{
		return ringBuffer.getSize();
	}

	public boolean isEmpty()
	{
		return ringBuffer.isEmpty();
	}

	@SuppressWarnings("unchecked")
	public DATA popOne()
	{
		return (DATA) ringBuffer.popOne();
	}
	
	public List<DATA> popAll() 
	{
		return ringBuffer.popAll();
	}

	@Override
	public void run()
	{
		boolean bWait = false;
		
		while (true)
		{
	        try
	        {
	    		synchronized(ringBuffer)
	    		{
		        	if (bWait || ringBuffer.isEmpty())
		        	{
		        		ringBuffer.wait();
		        	}	
	    		}
	        		
        		bWait = onProcess();
	        }
	        catch (InterruptedException e) 
	        {
	        	onKill();
	        	break;
	        }
	        catch (Exception e) 
	        {
	        	if (onException(e))
	        		break;
			} 
		}
	}
	
	public void stop() 
	{
		try 
		{
			timer.cancel();

			threadProcess.interrupt();
			
			threadProcess.join();
		} 
		catch (Exception e) { }
	}
	
	protected void onKill()
	{
	}

	protected boolean onException(Exception e)
	{
		// don't terminate thread
		return false;
	}

	protected abstract boolean onProcess();
}
