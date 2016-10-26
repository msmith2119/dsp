package com.sigmethod;

import java.io.*;

/**
 * Created by morgan on 11/20/15.
 */
public class FrameBufferTest {


      public void test() throws IOException {
          Remainder remainder = null;
          InputStream  stream = new BufferedInputStream(new FileInputStream("tmp.wav"))  ;
          OutputStream ostream = new BufferedOutputStream(new FileOutputStream("tmp_out.wav"));
          byte[] buf = new byte[1000];
          int bytes_read = 0;
          while((bytes_read = stream.read(buf)) != -1){

              FrameBuffer frameBuf = FrameBuffer.extractFrames(buf,bytes_read,1024,remainder);
              remainder = frameBuf.remainder;
              System.out.println("extracted "+frameBuf.frames.length+" frames");
               if(remainder == null){
                   System.out.println("No remainder");
               }
              else {
                   System.out.println("remaining bytes : "+remainder.buffer.length);
               }
              remainder = frameBuf.remainder;
              for(Frame frame : frameBuf.frames){
                  ostream.write(frame.buffer);
              }

          }


           stream.close();
           ostream.close();

      }


}
