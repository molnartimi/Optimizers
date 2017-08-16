library(ggplot2)

proba <- read.csv("E:/Timi/BME/6.félév/Önálló laboratórium/Optimizers/all10NEW.csv")

ggplot(proba) + 
  ylab("Function value") +
  geom_line(aes(x = Iteration, y = PSO, colour="PSO"), size=1.2) +
  geom_line(aes(x = Iteration, y = PSOM, colour="GPSO"), size=1.2) +
  geom_line(aes(x = Iteration, y = Bees, colour="Bees"), size=1.2) +
  geom_line(aes(x = Iteration, y = SimAnn, colour="SimAnn"), size=1.2) +
  geom_line(aes(x = Iteration, y = LBFGS, colour="LBFGS"), size=1.2) +
  ylim(0,0.5) +
  xlim(0,30)
