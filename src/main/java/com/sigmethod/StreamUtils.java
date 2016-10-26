package com.sigmethod;

import java.io.*;

/**
 * Created by morgan on 11/22/15.
 */
public class StreamUtils {

 public static int BUFFER_SIZE =  4096;
    public static void writeFile(InputStream stream, String path) throws IOException {


        OutputStream fstream = new BufferedOutputStream(new FileOutputStream(path));

         byte[] buffer = new byte[BUFFER_SIZE];

         int bytessRead = 0;

         while((bytessRead = stream.read(buffer)) != -1){
            fstream.write(buffer,0,bytessRead);
         }


         fstream.close();

    }


    public static void bindStreams(InputStream iStream, OutputStream oStream) throws IOException {

        byte[] buffer = new byte[BUFFER_SIZE];
         int bytesRead = 0;
         while((bytesRead = iStream.read(buffer)) != -1){
             //System.out.println("moving bytes : "+bytesRead);
             oStream.write(buffer,0,bytesRead);
             oStream.flush();
         }

    }
    public static void bindStreams(InputStream iStream, OutputStream oStream,int bufferSize) throws IOException {

        byte[] buffer = new byte[bufferSize];
        int bytesRead = 0;
        while((bytesRead = iStream.read(buffer)) != -1){
            //System.out.println("moving bytes : "+bytesRead);
            oStream.write(buffer,0,bytesRead);
            oStream.flush();
        }

    }


}
