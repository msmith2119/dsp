

class BlockEval { 

    public Circuit circuit 

    public BlockEval(Circuit circuit) { 
    	   this.circuit  = circuit
    }
   public  List<Block> eval(Block b) {
    List terminals = new ArrayList<Block>() 
    circuit.terminals = new ArrayList<Block>() 
    b.eval() 
     circuit.terminals.each { term -> terminals.add(term) } 
    for(Block term : terminals){
    	      eval(term) 
	      }
	      
}

}
