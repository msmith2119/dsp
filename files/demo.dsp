Sin(ampl=1.0,f=2000,T=1) n0
Sin(ampl=1.0,f=1000,T=1) n1p
Add(gain=1) n1p n0 n1
Sin(ampl=1.0,f=1000,phi=90,T=1) n2
FmSin(ampl=1.0,f=1000,fm=10,fdev=10,T=10.0)  nfm
Mult(gain=1.0) n1 nfm ni
Mult(gain=1.0) n2 nfm nq
Sinc(fc=300,N=40) ni qo
Sinc(fc=300,N=40) nq io
Deriv(x=0) io d1 
Deriv(x=0) qo d2
Delay(N=3) io iod
Delay(N=3) qo qod
Mult(gain=1.0) d1 qod m1
Mult(gain=1.0) d2 iod m2
Subtract(gain=1.0) m2 m1 out
Sinc(fc=200,N=60) out out1
.plot ni 0.5
.plot qo 0.5
//.plot out1 0.5
