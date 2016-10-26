
import com.sigmethod.*
import dsp.*

String fname = args[0]
int size = 500

FileInputStream fis = new FileInputStream(fname) 
byte[]  header = AudioUtils.readHeader(fis)
List<FrameBuffer> frameBufs = AudioUtils.readFrames(fis,size)

String s = RiffFileHeader.getString(header,0,4) 
short n = RiffFileHeader.getShort(header,22)
int fs = RiffFileHeader.getInt(header,24)
int ln = size - RiffFileHeader.HEADER_SIZE

println "s="+s
println "n="+n
println "fs="+fs

FirLPF lpf = new FirLPF(fs,300.0,40) 
String fileName = "out.wav"
BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileName))
bos.write(header,0,RiffFileHeader.HEADER_SIZE)	

for(FrameBuffer frameBuffer : frameBufs) { 
for(Frame f : frameBuffer.frames) { 
 processFrame(f,lpf) 
bos.write(f.buffer,0,f.buffer.length)
}
if(frameBuffer.remainder != null ) {
               bos.write(frameBuffer.remainder.buffer,0,frameBuffer.remainder.buffer.length);
           }
}

bos.close() 


def processFrame(Frame frame, AnalogFilter f) { 
   dump(frame.buffer)
   TypeConverter converter = TypeConverter.getInstance() 
   int nsamples = frame.buffer.length/2 
   for(int i = 0 ; i < frame.buffer.length; i+=2 ) { 
	byte[] b = new byte[2] 
	b[0]=frame.buffer[i] 
	b[1]=frame.buffer[i+1] 
	short s1 = RiffFileHeader.getShort(frame.buffer,i)
	 int m = i/2
	 println "s["+m+"]="+s1	 
         double d = converter.convertShortValue(s1)


	 short s2 = converter.convertDoubleValue(d)

 
       // short s2 = f.evalShort(s1) 
        byte[] bv = converter.short2bytes(s2) 
        frame.buffer[i]= bv[0] 
	frame.buffer[i+1] = bv[1] 
   }
 }
	
//println "read "+frameBufs.size()+" frameBufs"
//println "frames = "+frameBufs.get(0).frames.length
//println "remainder="+frameBufs[0].remainder.buffer.length

def dump(byte[] b ) {

  for(int i = 0; i < b.length; i++ ) {
        String.printf("%d:%x\n",i,b[i])
}
}
