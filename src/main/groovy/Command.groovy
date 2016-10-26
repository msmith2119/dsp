

class Command { 

     public static Command parseCommand(Circuit circuit, String line) { 
     	    
	    def m = line =~ /^\.(\w+)\s+.*/
	    if(m.matches() ) { 

	    	 if(m.group(1).equals("plot")){
			println "is a plot"
		       def m2 = line =~ /\.plot\s+(\w+)\s+(\S+)\s*/ 
			if(m2.matches()) {
			String net = m2.group(1)
			double maxTime = Double.valueOf(m2.group(2)) 
			return new PlotCommand(net,maxTime)
			}
                 }
	    	 if(m.group(1).equals("plotBlock")){
		       def m2 = line =~ /\.plotBlock\s+(\w+)\s+(\S+)\s*/ 
			if(m2.matches()) {
			String net = m2.group(1)
			double maxTime = Double.valueOf(m2.group(2))
			return new PlotBlockCommand(net,maxTime)
			}
		 }
	    	 if(m.group(1).equals("play")){
		       def m2 = line =~ /\.play\s+(\w+)\s+(\S+)\s*/ 
			if(m2.matches()) {
			String net = m2.group(1)
			double maxTime = Double.valueOf(m2.group(2))
			return new PlayBlockCommand(net,maxTime)
			}
		 }
		 if(m.group(1).equals("param")){
                        println "doparam"
			println "line="+line
			def m1 = line =~ /^\.param(.*)$/ 
			if(m1.matches()){
			println "matces"
			ParamCommand pCommand = new ParamCommand()
			String paramString = m1.group(1)
			println "paramString="+paramString
			Map params = Block.getParams(paramString)
		        println "params = "+params
                        pCommand.params = params
			println "ZZ params="+params
			circuit.params = params
			return pCommand
			}
		 }
	    }

	    return null
     }
 
    public void exec(Circuit circuit) {}
}

