package com.sigmethod;

import lava.riff.RiffInputFile;
import lava.riff.wave.PcmWaveStreamReader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by morgan on 11/10/15.
 */
public class WaveUtils {

    public static Map getMetaData(String path) throws Exception{

        RiffInputFile rf = new RiffInputFile(new File(path));
        PcmWaveStreamReader reader  = new PcmWaveStreamReader(rf);
        long numSamples = reader.countSamples();
        long numChannels = reader.countChannels();
        int  channelSampleSize = reader.getChannelSampleSize();
        int bitsPerChannelSample = reader.getBitsPerChannelSample();
        int sampleRate = reader.getFormatChunk().dwSamplesPerSec;

        HashMap h = new HashMap();
        h.put("numSamples",numSamples);
        h.put("numChannels",numChannels);
        h.put("channelSampleSize",channelSampleSize);
        h.put("bitsPerChannelSample",bitsPerChannelSample);
        h.put("sampleRate",sampleRate);
        reader.close();
        return h;

    }


    public static double[] readSamples(String path,int n) throws IOException {

        RiffInputFile rf = new RiffInputFile(new File(path));
        PcmWaveStreamReader reader  = new PcmWaveStreamReader(rf);
        double[] values = new double[n];
        TypeConverter converter = TypeConverter.getInstance();
        short[] samples  = (short[])reader.readSamples(0,null,0,n);
        for(int i = 0; i < samples.length;i++){
            values[i] = converter.convertShortValue(samples[i]);
        }
        return values;
    }

}
