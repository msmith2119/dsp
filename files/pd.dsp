.param fs=100
Sin(ampl=1.0,f=1,T=1)  p1
Const(ampl=0.0)  p2
PD(x=0) p1 p2 out
.plot p1 1
.plot out 1
