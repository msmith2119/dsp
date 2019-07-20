

class PlotCommand extends Command { 

      String net 
      double maxTime 

      public PlotCommand(String net, double maxTime) { 
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
	     double dt = 1/Block.fs
	     Signal outSig = new Signal(net,n,dt)
	     BlockEval evaluator =  new BlockEval(circuit)
	     for(int i = 0; i < n; i++ ) {
	     	     evaluator.eval(outPut) 
	     	     outSig.values[i] = outPut.eval()
		     circuit.reset() 
		     }
	 outSig.plot()
	 double fmax = Block.fs/2.0
     //    outSig.plotFreq(fmax)     
      }
      public String toString() { 
      
      StringBuffer sb = new StringBuffer() 
      sb.append("Plot\n")
      sb.append("net="+net+"\n") 
      sb.append("maxTime="+maxTime+"\n")
      
}
}
