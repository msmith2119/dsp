package com.sigmethod;

import java.nio.ByteBuffer;

/**
 * Created by morgan on 11/12/15.
 */
public class RiffFileHeader {


    public RiffFileHeader(int samplingRate,int numSamples,int numChannels){

        this.samplingRate = samplingRate;
        this.numChannels = numChannels;
        this.numSamples = numSamples;
        dataSize = numChannels*numSamples*2;
        fileSize = dataSize + HEADER_SIZE;

    }
    public int numSamples;
    public  int samplingRate;
    public  int numChannels;
    public int fileSize;
    public int dataSize;

     public static int HEADER_SIZE = 44;


      public byte[] render(){

          int index = 0;
          byte[] header = new  byte[fileSize];

          // Marks the file as a riff file. Characters are each 1 byte long.
          byte[] bytes = "RIFF".getBytes();
          index = addStringBytes(header,bytes,index);



          // Size of the overall file - 8 bytes, in bytes (32-bit integer). Typically, you'd fill this in after creation.
          bytes = int2bytes(fileSize-8);

          index = addBytes(header,bytes,index);

          // File Type Header. For our purposes, it always equals "WAVE".
          bytes = "WAVE".getBytes();
          index = addStringBytes(header,bytes,index);


          // Format chunk marker. Includes trailing null
          bytes = "fmt ".getBytes();
          index = addStringBytes(header,bytes,index);


          // Length of format data as listed above
          bytes = int2bytes(16);
          index = addBytes(header,bytes,index);


          // Type of format (1 is PCM) - 2 byte integer
          bytes = short2bytes((short)1);
          for(byte b : bytes){
              System.out.println(b);
          }
          index = addBytes(header,bytes,index);


          // 	Number of Channels - 2 byte integer
          bytes = short2bytes((short)numChannels);
          index = addBytes(header,bytes,index);


          // Sample Rate - 32 byte integer. Common values are 44100 (CD), 48000 (DAT). Sample Rate = Number of Samples per second, or Hertz.
           bytes = int2bytes(samplingRate);
           index = addBytes(header,bytes,index);


          //  (Sample Rate * BitsPerSample * Channels) / 8.
          bytes = int2bytes(samplingRate*16*numChannels/8);
          index = addBytes(header,bytes,index);



          //(BitsPerSample * Channels) / 8
          bytes = short2bytes((short)(16*numChannels/8));
          index = addBytes(header,bytes,index);


          // Bits per sample
          bytes = short2bytes((short)16);
          index = addBytes(header,bytes,index);


          //  data" chunk header. Marks the beginning of the data section.
          bytes = "data".getBytes();
          index = addStringBytes(header,bytes,index);


          // Size of the data section.
          bytes = int2bytes(dataSize);
          index = addBytes(header,bytes,index);


          return header;

      }

    private byte[] int2bytes (int value){

        return ByteBuffer.allocate(4).putInt(value).array();
    }
    private byte[] short2bytes (short value){

        return ByteBuffer.allocate(2).putShort(value).array();
    }

     public int addBytes(byte[] header,byte[] bytes , int index){
         for( int i = bytes.length -1; i > -1; i--){
             header[index++] = bytes[i];
         }
         return index;
     }
     public int addStringBytes(byte[] header, byte[] bytes, int index){
         for(int i= 0; i < bytes.length; i++){
             header[index++] = bytes[i];
         }
         return  index;
     }
    public static String getString(byte[] header,  int index, int len){
	byte[] b = new byte[len];
	System.arraycopy(header,index,b,0,len);
	    return new String(b);   

     }
    public static int getInt(byte[]  header, int index) { 
	byte[] b = new byte[4];
	    for(int i = 0; i < b.length; i++){
		b[i] = header[index+3-i];
                
	    }
	   
	    return ByteBuffer.allocate(4).wrap(b).getInt();
    }

    public static short getShort(byte[]  header, int index) { 
	byte[] b = new byte[2];
	    for(int i = 0; i < b.length; i++){
		b[i] = header[index+1-i];
                
	    }
	   
	    return ByteBuffer.allocate(2).wrap(b).getShort();
    }
    

    public String toString() {

        StringBuffer sb = new StringBuffer();

        sb.append("numSamples="+numSamples+"\n");
        sb.append("samplingRate="+samplingRate+"\n");
        sb.append("numChannels="+numChannels+"\n");
        sb.append("fileSize="+fileSize+"\n");
        sb.append("dataSize="+dataSize+"\n");
        return sb.toString();

    }
}
