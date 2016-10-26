package dsp
 /*
 * Created on Apr 2, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Morgan Smith
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SparseArray {

	private int numElems  
	private int maxIndex
	private int[] indexes 
	private double[] values  
	private Map<Integer,Double> valueMap

	public SparseArray(int n ){
		
		numElems = n
		
		indexes = new int[numElems];
		values = new double[numElems];
		maxIndex = 0; 
		valueMap  = new HashMap<Integer,Double>()		
		
		
	}
	
	public void setElem(int pos,int index,double value){
		
		if(pos >= numElems)
			return;
		
		maxIndex = Math.max(index,maxIndex)
		indexes[pos]=index
		values[pos]=value
		valueMap[index]=value
		
		
	}
	public double getElem(int pos){
		
		if(pos >=numElems)
			return 0.0
		
		return values[pos]
		
		
	}
	
        public double getValue(int n ) { 

	       Double v = valueMap[n]
	       if(v == null) 
	       	    return 0.0
		    
             return v
        }
	public int[] getIndexes() { return indexes}
	public double[] getValues() { return values}
	
	public int getMaxIndex() { return maxIndex } 
	
	public String toString() { 
		
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < indexes.length;i++){
			sb.append("a["+indexes[i]+"] = "+values[i]+"\n")
		}
		return sb.toString()
	}
	public static void main(String[] args) {
		
		SparseArray sa = new SparseArray(2)
		
		sa.setElem(0,0,1.2)
		sa.setElem(1,50000,1)
		
		 System.out.println(sa.toString())
		
	}
}
