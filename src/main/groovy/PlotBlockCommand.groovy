

class PlotBlockCommand extends Command { 

       String net
       double maxTime 

      public PlotBlockCommand(String net,double maxTime) { 
      	     this.net = net
      	     this.maxTime = maxTime
      }

      public void exec(Circuit circuit) { 
      	     
	     Block outPut = circuit.nodeMap[net] 
	     if(outPut  == null) { 
	     println "Error : node not found "+net
	     return
	     }
	    
	    
	   
	     int n = maxTime*Block.fs
	     outPut.filter.plotImpulse(n)
	     outPut.filter.plotFreq(n,false)

      }
      public String toString() { 
      
      StringBuffer sb = new StringBuffer() 
      sb.append("Plot\n")
      sb.append("net="+net+"\n") 
      sb.append("maxTime="+maxTime+"\n")
      
}
}