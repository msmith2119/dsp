package com.sigmethod;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class TypeConverter {
	
	
	private double range;
	
	private double dx; 
	
	private static TypeConverter converter ; 
	
	
	
	private TypeConverter(double range) { 
			
		     this.range=range;
			
			
			 dx = (double)(Short.MAX_VALUE-Short.MIN_VALUE);
			
			
	}
	
	
    public static TypeConverter getInstance() { 
    	
    	if(converter == null)
    		converter = new TypeConverter(1.0);
    	
    	return converter;
    }

	
	public double[] convertByteArray(byte[] values) {
		double[] dvals = new double[values.length / 2];
		for (int i = 0; i < dvals.length; i++) {
		    short s = RiffFileHeader.getShort(values,2*i);
			dvals[i] = convertShortValue(s);
		}
		return dvals;
	}

	public float[] convertByteArrayFloats(byte[] values) {
		float[] dvals = new float[values.length / 2];
		for (int i = 0; i < dvals.length; i++) {
			short s = (short) (values[2 * i + 1] << 8 | values[2 * i]);
			dvals[i] = convertShortValueFloat(s);
		}
		return dvals;
	}

	public double[] convertShortArray(short[] svalues){
			
			if(svalues.length < 1 )
					return new double[0]; 
			
			double[] dvalues = new double[svalues.length];
			
			for(int i = 0; i < svalues.length;i++){
	
					    dvalues[i]=convertShortValue(svalues[i]);

			}
			
	return dvalues;		
	}
	
	public short[] convertDoubleArray(double[] dvalues){
		
		if(dvalues.length < 1 )
				return new short[0]; 
		
		short[] svalues = new short[dvalues.length];
		
		for(int i = 0; i < dvalues.length;i++){

				    svalues[i]=convertDoubleValue(dvalues[i]);

		}
		
return svalues;		
}

	public  byte[] convertDoubles(double[] values){

		byte[] bvals = new byte[values.length*2];
		for(int i = 0; i < values.length; i++){

               short s = convertDoubleValue(values[i]);
			    bvals[2*i] = (byte)(s & 0xFF);
			    bvals[2*i+1] = (byte)((s >>8) & 0xFF);

		}

		return bvals;
	}
	
	public  double convertShortValue(short value){
		
		double sin = (double)value; 
		double d  = (2*range/dx)*sin;
		return d;
	}
	public  float convertShortValueFloat(short value){

		float sin = (float)value;


		float d  = (float)(2*range/dx)*sin;

		return d;
	}
	
	
	public short convertDoubleValue(double value) { 
      
		if(value >  range)
			value=range; 
		
		if(value < -range)
			value=-range;
		
		double sd = (dx/(2*range))*value; 
		
		short s = (short)sd; 
		
		return s; 
	}

	


	public static void main(String[] args) {
		
		TypeConverter converter = new TypeConverter(1.0);
		
		short  s = 12000; 
		
		double d = converter.convertShortValue(s);
		
		System.out.println("d="+d); 
		
		short s2 = converter.convertDoubleValue(d);
		
		System.out.println("s2="+s2);
		
		
		
		
		
	}

	public  byte[] short2bytes (short value){

	    return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(value).array();
	}
}
