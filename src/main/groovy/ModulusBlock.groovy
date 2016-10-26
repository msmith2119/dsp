



import java.util.regex.*


class ModulusBlock extends Block { 

public String name
public Block input1
public Block input2
public String inNet1
public String inNet2

 public static Pattern  PATT =   ~/Modulus\(([^\(\)]+)\)\s+(\w+)\s+(\w+)\s+(\w+)\s*/ 

 public MultBlock () {} 

 public void parse(String line) { 
       Matcher matcher = PATT.matcher(line)
   if(matcher.matches()) {
      name="Modulus" 
      String paramsString =  matcher.group(1)
      inNet1 = matcher.group(2)
      inNet2 = matcher.group(3)
      outNet = matcher.group(4) 
        }
	
}

    public void bind(Circuit circuit) { 
 	input1 = circuit.nodeMap[inNet1]
 	input2 = circuit.nodeMap[inNet2]
	
}


 public double getSample() { 
  
 double x1 = input1.eval()
 double x2 = input2.eval()
 double y =  1/(x1*x1 + x2*x2)
 return y 
}

  public String toString() { 
  StringBuffer sb = new StringBuffer()
   sb.append("name="+name+"\n");
   sb.append("inNet1="+inNet1+"\n");
   sb.append("inNet2="+inNet2+"\n");
   sb.append("outNet="+outNet+"\n");
  return sb.toString() 

}
}