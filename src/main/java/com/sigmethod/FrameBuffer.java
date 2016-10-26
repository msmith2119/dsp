package com.sigmethod;

/**
 * Created by morgan on 11/20/15.
 */
public class FrameBuffer {


    public int frameSize;
    public  Frame[] frames;
    public  Remainder remainder;

    public FrameBuffer(int frameSize,int numFrames){
        frames = new Frame[numFrames];
        this.frameSize = frameSize;
    }
    public   static FrameBuffer  extractFrames(byte[] inBytes,int inLength,int frameSize, Remainder remainder){
        byte[] buffer =  inBytes;
        int length = inLength;
          if(remainder != null){
              buffer = new byte[remainder.buffer.length  + inLength];
              System.arraycopy(remainder.buffer,0,buffer,0,remainder.buffer.length);
              System.arraycopy(inBytes,0,buffer,remainder.buffer.length,inLength);
              length = buffer.length;
          }
          int numFrames = length/frameSize;
          FrameBuffer frameBuff = new FrameBuffer(frameSize,numFrames);
          int remainderBytes =  length - numFrames*frameSize;

          for(int i = 0; i < numFrames; i++){
              Frame frame = new Frame(frameSize);
              System.arraycopy(buffer,i*frameSize,frame.buffer,0,frameSize);
              frameBuff.frames[i] = frame;
          }

        if(remainderBytes   > 0){

            frameBuff.remainder =  new Remainder(remainderBytes);
            System.arraycopy(buffer,numFrames*frameSize,frameBuff.remainder.buffer,0,remainderBytes);
        }
         return frameBuff;
    }



}
