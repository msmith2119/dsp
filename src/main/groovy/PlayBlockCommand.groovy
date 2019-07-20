

import com.sigmethod.* 


class PlayBlockCommand extends Command { 

       String net
       double maxTime 

      public PlayBlockCommand(String net,double maxTime) { 
      	     this.net = net
      	     this.maxTime = maxTime
      }

      public void exec(Circuit circuit) { 
      	     
	     Block outPut = circuit.nodeMap[net] 
	     if(outPut  == null) { 
	     println "Error : node not found "+net
	     return
	     }
	    

	    int M = maxTime*Block.fs
	    double[] y = new double[M] 

	    for(int i = 0; i < M; i++ ) { 
	    	    y[i] = outPut.eval()
		    circuit.reset()  
	    }
	    
	    int fs = (double)Block.fs
	     InputStream s = AudioUtils.createMonoStream(fs,y)
	 //    AudioUtils.saveStream(s,"tmp.wav")
	     AudioUtils.playStream(s) 
      }
      public String toString() { 
      
      StringBuffer sb = new StringBuffer() 
      sb.append("Play\n")
      sb.append("net="+net+"\n") 
      sb.append("maxTime="+maxTime+"\n")
      
}
}