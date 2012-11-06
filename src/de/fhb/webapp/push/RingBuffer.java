package de.fhb.webapp.push;

import java.util.ArrayList;
import java.util.List;

public class RingBuffer
{
	protected Object[] arrayData = null; 
	protected int nSize = 0;
	protected int nPush = 0;
	protected int nPop = 0;
	protected boolean bOverwrite;

	public RingBuffer(int nSize, boolean bOverwrite) 
	{
		setSize(nSize);
		setOverwrite(bOverwrite);
	}
	
	public synchronized void setSize(int nSize)
	{
		if (nSize < 2)
			throw new IllegalArgumentException("size < 2");

		nSize++;
		
		this.arrayData = new Object[nSize];
		this.nSize = nSize;
		this.nPush = nPop = 0;
	}
	
	public synchronized void setOverwrite(boolean bOverwrite)
	{
		this.bOverwrite = bOverwrite;
	}
	
	public synchronized boolean isEmpty()
	{
		return (nPush == nPop);
	}
	
	public synchronized int getSize()
	{
		if (nPush >= nPop)
			return nPush - nPop;
		else
			return nSize - (nPop - nPush);
	}
	
	@SuppressWarnings("unchecked")
	public synchronized <TYPE> TYPE getOne()
	{
		if (nPop == nPush)
			return null;
			
		return (TYPE) arrayData[nPop];
	}
	
	@SuppressWarnings("unchecked")
	public synchronized <TYPE> TYPE getOne(int nIndex)
	{
		if (nIndex >= getSize())
			return null;
		
		nIndex = (nPop + nIndex) % nSize;
					
		return (TYPE) arrayData[nIndex];
	}

	@SuppressWarnings("unchecked")
	public synchronized <TYPE> TYPE popOne()
	{
		if (nPop == nPush)
			return null;
			
		Object oReturn = arrayData[nPop];
		
		nPop = (nPop + 1) % nSize;
		
		return (TYPE) oReturn;
	}

	@SuppressWarnings("unchecked")
	public synchronized <TYPE> List<TYPE> popAll()
	{
		if (nPop == nPush)
			return null;
			
		List<TYPE> listReturn = new ArrayList<TYPE>();
		
		do
		{
			TYPE o =  (TYPE) arrayData[nPop];
			
			nPop = (nPop + 1) % nSize;
			
			listReturn.add(o);
		}
		while (nPop != nPush);
		
		return listReturn;
	}

	public synchronized boolean pushData(Object oData)
	{
		if (oData == null)
			return false;
	
		if ((nPush + 1) == nSize)
		{
			if (nPop == 0)
			{
				if (!bOverwrite)
					return false;
				
				nPop++;
			}
			arrayData[nPush] = oData;
			nPush = 0;
		}
		else 
		{
			if ((nPush + 1) == nPop)
			{
				if (!bOverwrite)
					return false;
				
				nPop = (nPop + 1) % nSize;
			}
			arrayData[nPush] = oData;
			nPush++;
		}
		return true;
	}
	
	public synchronized void clear()
	{
		nPush = nPop = 0;
	}

	public synchronized String toString()
	{
		StringBuilder strReturn = new StringBuilder();
		int nPop = this.nPop;

		while (nPop != nPush)
		{
			strReturn.append(arrayData[nPop]);
			strReturn.append("\n");
			
			nPop++;
			
			if (nPop >= nSize)
				nPop = 0;
		}
		return strReturn.toString();
	}
}
