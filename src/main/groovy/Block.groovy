



 class Block  { 

 public  boolean evaluated
 public double value
 public String outNet
 public Circuit circuit 
 public  static double fs = 1
 public int count = 0
  public Block() { 

  evaluated = false
} 

public double eval() { 
 
  
 if(evaluated) { 
    return value
  }
 

  evaluated = true
  value = getSample() 
  return value
}

 

 public  double getSample() { return 0.0 } 

 public static Map getParams(String s ) { 
 Map  params = new HashMap<String,String>() 
 
     String[] f = s.split(",")
     f.each { e -> 
        def m = e =~ /^\s*(\w+)=(\S+)\s*/ 
	if(m.matches()){
		params[m.group(1)]=m.group(2)
	}
	}
     return params
}

public double getParam(String name, Map params) { 

 return getParam(name,params,0.0)
}

public String getStringParam (String name, Map params) { 
       
       String v = params[name] 
	String v2 = circuit.params[v]
	if( v2 != null ) { 
	    v = v2 
	    } 

	return v
}
public double getParam (String name, Map params, double defVal) { 

       if(!params.containsKey(name)){
	return defVal
	}
       String s = getStringParam(name,params)
       Double v = Double.valueOf(s) 
       return v
}
public double[] getArrayParam (String name, Map params) { 

       String[] elems = params.get(name).split(/:/)
       double[] a = new double[elems.length] 
        for(int i = 0; i < elems.length; i++ ) {
       a[i] = Double.valueOf(elems[i])
       }
       
       return a
}

  public void bind(Circuit circuit) {} 

  public static Block parseBlock(Circuit circuit ,String line) { 
          
  	 def m =  line =~ /(\w+)\(.*/
	 if(m.matches()) { 
	   String prefix = m.group(1) 
	   if(prefix.equals("Sin")) { 
	   	Block sinBlock = new SinBlock()
		sinBlock.circuit = circuit 
                sinBlock.parse(line)
		sinBlock.circuit = circuit 
		return sinBlock		   
	   }
   
	   if(prefix.equals("Signal")) { 
	   	SignalBlock sigBlock = new SignalBlock()
		sigBlock.circuit = circuit 
		sigBlock.parse(line) 
		return sigBlock		   
	   }
	   if(prefix.equals("AmSin")) { 
	   	AmSinBlock sinBlock = new AmSinBlock()
		sinBlock.circuit = circuit 
		sinBlock.parse(line) 
		return sinBlock		   
	   }
	   if(prefix.equals("FmSin")) { 
	   	FmSinBlock sinBlock = new FmSinBlock()
		sinBlock.circuit = circuit 
	   	sinBlock.parse(line) 
		sinBlock.circuit = circuit 
		return sinBlock		   
	   }
	   if(prefix.equals("Fm")) { 
	   	FmBlock fmBlock = new FmBlock()
		fmBlock.circuit =  circuit
		fmBlock.parse(line) 
		return fmBlock		   
	   }
	   if(prefix.equals("UnitStep")) { 
	   	UnitStepBlock unitBlock = new UnitStepBlock()
		unitBlock.circuit = circuit 
		unitBlock.parse(line) 
		return unitBlock		   
	   }
	   if(prefix.equals("RandomSquare")) { 
	   	RandomSquareBlock squareBlock = new RandomSquareBlock()
		squareBlock.circuit = circuit 
		squareBlock.parse(line) 
		return squareBlock		   
	   }
	   if(prefix.equals("Const")) { 
	   	ConstBlock constBlock = new ConstBlock()
		constBlock.circuit = circuit 
		constBlock.parse(line)
		return constBlock		   
	   }
	   if(prefix.equals("Sinc")) { 
	        SincFilterBlock sincBlock = new SincFilterBlock()
		sincBlock.circuit = circuit 
		sincBlock.parse(line)
		return sincBlock
		
	   }
	   if(prefix.equals("Bpf")) { 
	        BpfFilterBlock bpfBlock = new BpfFilterBlock()
		bpfBlock.circuit = circuit 
		bpfBlock.parse(line)
		return bpfBlock
		
	   }
	   if(prefix.equals("Filter")) { 
	        GenFilterBlock genBlock = new GenFilterBlock()
		genBlock.circuit = circuit 
		genBlock.parse(line)
		return genBlock
		
	   }
	   if(prefix.equals("Mod1")) { 
	        Mod1Block modBlock = new Mod1Block()
		modBlock.circuit = circuit 
		modBlock.parse(line)
		return modBlock
		
	   }
	   if(prefix.equals("PD")) { 
	        PDBlock pdBlock = new PDBlock()
		pdBlock.circuit = circuit 
		pdBlock.parse(line)
		return pdBlock
		
	   }
	   if(prefix.equals("Amp")) { 
	        AmpBlock ampBlock = new AmpBlock()
		ampBlock.circuit = circuit 
		ampBlock.parse(line)
		return ampBlock
		
	   }
	   if(prefix.equals("Cos")) { 
	        CosBlock cosBlock = new CosBlock()
		cosBlock.circuit = circuit
		cosBlock.parse(line)
		return cosBlock
		
	   }
	   if(prefix.equals("Phase")) { 
	        PhaseBlock phBlock = new PhaseBlock()
		phBlock.circuit = circuit
		phBlock.parse(line)
		return phBlock
		
	   }
	   if(prefix.equals("Delay")) { 
	        DelayBlock delayBlock = new DelayBlock()
		delayBlock.circuit = circuit
		delayBlock.parse(line)
		return delayBlock
		
	   }
	   if(prefix.equals("Deriv")) { 
	        DerivBlock derivBlock = new DerivBlock()
		derivBlock.circuit = circuit 
		derivBlock.parse(line)
		return derivBlock
		
	   }
	   if(prefix.equals("Mult")) {
	        MultBlock multBlock = new MultBlock()
		multBlock.circuit = circuit 
		multBlock.parse(line) 
		return multBlock
		
	   }
	   if(prefix.equals("Subtract")) {
	        SubtractBlock subtractBlock = new SubtractBlock()
		subtractBlock.circuit = circuit 
		subtractBlock.parse(line) 
		return subtractBlock
		
	   }
	   if(prefix.equals("Add")) {
	        AddBlock addBlock = new AddBlock()
		addBlock.circuit = circuit 
		addBlock.parse(line) 
		return addBlock
		
	   }
	   if(prefix.equals("Modulus")) {
	        ModulusBlock modulusBlock = new ModulusBlock()
		modulusBlock.circuit =  circuit 
		modulusBlock.parse(line) 
		return modulusBlock
		
	   }
      }
      else { println "Nope" } 
      return null
 }


  public void reset() { 

  	 evaluated = false
	 count = 0
}
   public  Signal getSignal() { return null; } 
}




 
 
