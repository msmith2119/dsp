

class Circuit { 

public Circuit() { blocks = new ArrayList<Block>()
 nodeMap = new HashMap<String,Block>()
 commands = new ArrayList<Command>()
 params = new HashMap<String,String>()   } 
 
List  blocks 
List commands
Map nodeMap 
Map params
List terminals

public void load(String fname) { 

    new File(fname).eachLine {l ->
  if(l.length() > 3) {
   Command command = Command.parseCommand(this,l)
   if(command != null) { 
     commands.add(command)
   } 
   Block b = Block.parseBlock(this,l)
   if(b != null) {
     nodeMap[b.outNet] = b
    blocks.add(b)      
   }

  }
 

}
   
   bind() 
}

 public void bind() { 
 	blocks.each { b -> b.bind(this) } 
 }
 public void  exec() { 
   commands.each { command -> command.exec(this) } 
 }
 public void reset() {blocks.each { b  -> b.reset() } }
 public void display() { 

 blocks.each { b -> println b.toString() } 
 commands.each { c -> println "command="+c.toString() }
 println params
 nodeMap.each { k,v -> println k +"=>"+v.name }
}
}
