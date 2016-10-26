package com.sigmethod;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by morgan on 11/11/15.
 */
public class AudioUtils {


      public static OutputStream audioFileOutputStream(String path,int sampleingRate,int numSamples, int numChannels) throws IOException {

      RiffFileHeader header = new RiffFileHeader(sampleingRate,numSamples,numChannels);
      FileOutputStream fos = new FileOutputStream(path);
       fos.write(header.render());
       return  fos;
      }


    public static byte[] readHeader(FileInputStream fis) throws IOException { 
	byte[] header = new byte[RiffFileHeader.HEADER_SIZE];
               fis.read(header, 0, RiffFileHeader.HEADER_SIZE);
	return header;
    }
    public static byte[] readBody(FileInputStream fis,int size) throws IOException { 
	byte[] body = new byte[size];
               fis.read(body, 0, size);
	return body;
    }
    
      public static List<FrameBuffer> readFrames(FileInputStream audioInputStream, int frameSize) throws IOException, UnsupportedAudioFileException {

          List<FrameBuffer> frameBufs = new ArrayList<FrameBuffer>();
          int	nBytesRead = 0;
          byte[]	abData = new byte[128000];
          Remainder remainder = null;
              nBytesRead = audioInputStream.read(abData, 0, abData.length);
              if(nBytesRead > 0) {
                  FrameBuffer frameBuf = FrameBuffer.extractFrames(abData, nBytesRead, frameSize, remainder);
                  frameBufs.add(frameBuf);
                  remainder = frameBuf.remainder;
              }
  
    return frameBufs;

}
      public static List<FrameBuffer> readFrames(String fileName, int frameSize) throws IOException, UnsupportedAudioFileException {

          File soundFile = new File(fileName);
          FileInputStream audioInputStream =new FileInputStream(soundFile);
          List<FrameBuffer> frameBufs = new ArrayList<FrameBuffer>();
          int	nBytesRead = 0;
          byte[]	abData = new byte[128000];
          Remainder remainder = null;
          while (nBytesRead != -1){
              nBytesRead = audioInputStream.read(abData, 0, abData.length);
	      System.out.println("bytesRead="+nBytesRead);
              if(nBytesRead > 0) {
                  FrameBuffer frameBuf = FrameBuffer.extractFrames(abData, nBytesRead, frameSize, remainder);
                  frameBufs.add(frameBuf);
                  remainder = frameBuf.remainder;
              }
          }

    return frameBufs;

      }
    
    public static void writeFrames(List<FrameBuffer>  frameBuffers, String fileName ) throws FileNotFoundException,IOException{ 

           BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileName));
           byte[] buf = new byte[128000];
           int nBytesRead =  0;

	    for(FrameBuffer frameBuffer : frameBuffers) { 
	       for(Frame frame : frameBuffer.frames) { 

		   bos.write(frame.buffer,0,frame.buffer.length);
	
	        }
	   
	   if(frameBuffer.remainder != null ) { 
	       bos.write(frameBuffer.remainder.buffer,0,frameBuffer.remainder.buffer.length);
	   }
	   }
            bos.close();	
    }
      public static double[] readSamples(String fileName, int n) throws IOException, UnsupportedAudioFileException {
        byte[] buf = new byte[n];
          double[] samples = new double[n];
        File soundFile = new File(fileName);
          AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
          AudioFormat	audioFormat = audioInputStream.getFormat();
          System.out.println("audioFormat="+audioFormat);
          int bytes_read = audioInputStream.read(buf,0,buf.length);
          System.out.println("bytes_read="+bytes_read);

    return samples;


      }



      public static void playInfiniteStreamFramed(InputStream  is, int  sampleRate, int channels) throws LineUnavailableException {


          AudioFormat audioFormat = new AudioFormat(sampleRate,16,channels,true,false);
           DataLine.Info	info = new DataLine.Info(SourceDataLine.class,
                   audioFormat);
          SourceDataLine	line = null;


          line = (SourceDataLine) AudioSystem.getLine(info);


          line.open(audioFormat);
          line.start();

          int	nBytesRead = 0;
          byte[]	abData = new byte[128000];
          Remainder remainder = null;
          while (nBytesRead != -1)
          {
              try
              {
                  nBytesRead = is.read(abData, 0, abData.length);
                  System.out.println("nBytesRead = "+nBytesRead);
                  FrameBuffer frameBuf = FrameBuffer.extractFrames(abData,nBytesRead,1024,remainder);
                  remainder = frameBuf.remainder;
                  for(Frame frame:frameBuf.frames){
                      int nBytesWritten =  line.write(frame.buffer,0,frame.buffer.length);
                      System.out.println("nBytesWritten="+nBytesRead);
                  }


              }
              catch (IOException e)
              {
                  e.printStackTrace();
              }

          }

          line.drain();
          line.close();
      }

      public static void playInfiniteStream(InputStream  is, int  sampleRate, int channels) throws LineUnavailableException {


          AudioFormat audioFormat = new AudioFormat(sampleRate,16,channels,true,false);
           DataLine.Info	info = new DataLine.Info(SourceDataLine.class,
                   audioFormat);
          SourceDataLine	line = null;


          line = (SourceDataLine) AudioSystem.getLine(info);


          line.open(audioFormat);
          line.start();

          int	nBytesRead = 0;
          byte[]	abData = new byte[128000];
          while (nBytesRead != -1)
          {
              try
              {
                  nBytesRead = is.read(abData, 0, abData.length);
                  System.out.println("bytes read: "+nBytesRead);
              }
              catch (IOException e)
              {
                  e.printStackTrace();
              }
              if (nBytesRead >= 0)
              {
                  int	nBytesWritten = line.write(abData, 0, nBytesRead);
              }
          }

          line.drain();
          line.close();
      }
      public static void playStream(InputStream is) throws IOException, UnsupportedAudioFileException, LineUnavailableException {

          BufferedInputStream bis = new BufferedInputStream(is);
          AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bis);
          AudioFormat audioFormat = audioInputStream.getFormat();

          SourceDataLine	line = null;
          DataLine.Info	info = new DataLine.Info(SourceDataLine.class,
                  audioFormat);
          line = (SourceDataLine) AudioSystem.getLine(info);


          line.open(audioFormat);
          line.start();

          int	nBytesRead = 0;
          byte[]	abData = new byte[128000];
          while (nBytesRead != -1)
          {
              try
              {
                  nBytesRead = audioInputStream.read(abData, 0, abData.length);
              }
              catch (IOException e)
              {
                  e.printStackTrace();
              }
              if (nBytesRead >= 0)
              {
                  int	nBytesWritten = line.write(abData, 0, nBytesRead);
              }
          }

          line.drain();

          line.close();

      }

       public static void saveStream(InputStream s, String path) throws IOException {

           BufferedInputStream bis = new BufferedInputStream(s);
           BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path));
           byte[] buf = new byte[128000];
           int nBytesRead =  0;

           while(nBytesRead != -1){

             nBytesRead = bis.read(buf,0,buf.length);
             if(nBytesRead  >= 0 )
                 bos.write(buf,0,nBytesRead);
           }
            bis.close();
            bos.close();


       }
      public static void playFile(String path) throws IOException, UnsupportedAudioFileException, LineUnavailableException {


          File	soundFile = new File(path);
          FileInputStream fis = new FileInputStream(soundFile);

          playStream(fis);

      }




    public static double[] whiteNoiseArray(int n){

        double[] y =  new double[n];
        for(int i = 0; i < n; i++){

            y[i] = 2*Math.random()  -1;

        }
        return y;
    }
    public static short[][] whiteNoise(int n,int numChannels){

        short[][] samples = new short[n][numChannels];
        TypeConverter converter = TypeConverter.getInstance();

         for(int i = 0; i < n; i++){
             short[] sample = new short[numChannels];
             double v = 2*Math.random()  -1;
             short s = converter.convertDoubleValue(v);
             for(int j = 0; j < numChannels; j++)
                  sample[j] = s;
             samples[i] = sample;
         }

         return  samples;


    }






      public static byte[] createNoiseByteArray(int numSamples,int numChannels){

          short[][] samples = whiteNoise(numSamples,numChannels);
          byte[] buffer =  new byte[numSamples*numChannels*2];
          for(int i = 0; i < numSamples; i++){
              for(int j = 0; j < numChannels;j++){
                  byte[] ba = short2bytes(samples[i][j]);
                  buffer[ (i*numChannels + j)*2 ] = ba[0];
                  buffer[ (i*numChannels)*2 +1] = ba[1];
              }
          }
          return buffer;
      }
      public static byte[] createNoiseWaveBytes(int sampleRate,int numSamples, int numChannels){

          RiffFileHeader riff = new RiffFileHeader(sampleRate,numSamples,numChannels);

          short[][] samples = whiteNoise(numSamples,numChannels);
          byte[] buffer = new byte[RiffFileHeader.HEADER_SIZE + 2*numSamples*numChannels];
          System.arraycopy(riff.render(),0,buffer,0,RiffFileHeader.HEADER_SIZE);
          for(int i = 0; i < numSamples; i++){
              for(int j = 0; j < numChannels;j++){
                  byte[] ba = short2bytes(samples[i][j]);
                  buffer[riff.HEADER_SIZE + (i*numChannels + j)*2 ] = ba[0];
                  buffer[riff.HEADER_SIZE + (i*numChannels)*2 +1] = ba[1];
              }
          }


       return  buffer;


      }

      public static  InputStream createWaveStream(int sampleRate,short[][] samples){

	  TypeConverter converter = TypeConverter.getInstance();
        int numSamples = samples.length;
        int numChannels = samples[0].length;
	System.out.println( "numSamples="+numSamples);
	System.out.println("numChannels="+numChannels);
        RiffFileHeader riff = new RiffFileHeader(sampleRate,numSamples,numChannels);
        byte[] buffer = riff.render();
          for(int i = 0; i < numSamples; i++){
              for(int j = 0; j < numChannels;j++){
                  byte[] ba = converter.short2bytes(samples[i][j]);
                  buffer[riff.HEADER_SIZE + (i*numChannels + j)*2 ] = ba[0];
                  buffer[riff.HEADER_SIZE + (i*numChannels)*2 +1] = ba[1];
              }
          }

          return new ByteArrayInputStream(buffer);
      }

      public static InputStream createMonoStream(int sampleRate, double[] y){

          TypeConverter converter = TypeConverter.getInstance();
          short[][] samples = new short[y.length][1];

           for(int i = 0; i < y.length; i++){

               samples[i][0] = converter.convertDoubleValue(y[i]);

           }

          return createWaveStream(sampleRate,samples);
      }



  private static byte[] short2bytes(short s){
        byte[] ret = new byte[2];
        ret[0] = (byte)(s & 0xff);
        ret[1] = (byte)((s >> 8) & 0xff);
        return ret;
    }
}
