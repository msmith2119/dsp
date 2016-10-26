RandomSquare(ampl=1.0,tmin=1,tmax=2,T=10) n0
RandomSquare(ampl=1.0,tmin=1,tmax=2,T=10) n1
Fm(ampl=1.0,f=1000,src=n0,pdev=1,T=10) nfm1
Fm(ampl=1.0,f=2000,src=n1,pdev=1,T=10) nfm2
Add(gain=1) nfm1 nfm2 sum
Bpf(fc=1000,b=100,N=100) sum out 
.plot out 10
