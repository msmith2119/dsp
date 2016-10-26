.param ph=0.25,knco=2.44e-04
Const(ampl=ph) ft
Const(ampl=0) vtt
Amp(gain=knco) vtt nco_ci
Add(gain=1.0) nco_ci ft nco_n1
Add(gain=1.0) nco_n1 u nco_p1
Delay(N=1) nco_p1 nco_x
Mod1(x=0) nco_x u
Cos(x=0) u o
Phase(f=8e+06) pd_in
PD(x=0) pd_in u   pe
Filter(a=5.1:-5.0968,b=1:-1) pe  vtune
.plot u 5e-05
.plot pd_in 5e-05
.plot pe 5e-05
