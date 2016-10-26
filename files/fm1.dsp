RandomSquare(ampl=1.0,tmin=2,tmax=4,T=30) nx
Sin(ampl=1.0,f=10,T=0.1) n0
Fm(ampl=1.0,f=1000,src=n0,pdev=1,T=1) nfm
Sin(ampl=1.0,f=1000,T=1) n1 
Sin(ampl=1.0,f=1000,phi=90,T=1) n2
FmSin(ampl=1.0,f=1000,fm=100,fdev=10,T=1.0)  nfm
Mult(gain=1.0) n1 nfm ni
Mult(gain=1.0) n2 nfm nq
Sinc(fc=300,N=40) ni qo
Sinc(fc=300,N=40) nq io
Delay(N=1) io d1 
Delay(N=1) qo d2
Delay(N=1) d1 e1
Delay(N=1) d2 e2
Subtract(gain=1) io e1 ip
Subtract(gain=1) qo e2 qp
Mult(gain=1.0) d1 qp o1
Mult(gain=1.0) d2 ip o2
Subtract(gain=1) o1 o2 out
Sinc(fc=100,N=60) out out1
.plot out1 10
