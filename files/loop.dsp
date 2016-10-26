.param kl=5.1,ki=0.0032
Sin(ampl=1.0,f=1e+06,T=1e-03) pe
Amp(gain=kl) pe loop_n1
Amp(gain=ki) pe loop_n2
Add(gain=1) loop_n2 loop_n3 loop_n4
Delay(N=1) loop_n4 loop_n3 
Add(gain=1) loop_n3 loop_n1 vtune
.plotBlock vtune 1e-03
