.param ph=0.32,knco=2.44e-04
Const(ampl=ph) ft
Const(ampl=0) vtune
Amp(gain=knco) vtune nco_ci
Add(gain=1.0) nco_ci ft nco_n1
Add(gain=1.0) nco_n1 u nco_p1
Delay(N=1) nco_p1 nco_x
Mod1(x=0) nco_x u
Cos(x=0) u o
.plot u 5e-4
