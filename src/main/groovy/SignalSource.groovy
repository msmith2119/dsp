

class SignalSource extends Block { 

  private Signal  y 
  int pos  

  public SignalSource(Signal y ) { 
    super()
    this.y = y
    pos = 0 
}

public double getSample() { 

   if(pos >= y.values.length)
   	  pos = 0

   return y.values[pos++]
}
}