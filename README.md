# Processor scheduling sim
 Simulacion de calendarizacion de procesos

El programa corre en java desde una linea de comandos, para compilarlo se compila con el siguiente comando:

javac Colas.java



Para ejecutarlo es con el siguiente comando:

java Colas



El programa consta de 5 tipos de calendarizacion de procesos, en orden 0 FCFS, 1 LJF, 2 SJF, 3 LCFS, 4 RR, se preguntará cual de estas políticas desea ver luego de haber ingresado las demandas D y los tiempos de traer un nuevo video lambda.

Estos tiempos están dados en decenas de milisegundos para evitar un poco el overhead, esto se puede modificar por medio de la variable "factor" al inicio del archivo Viewer.java

Si en caso se quisiera RR, el quantum se pedirá luego de escoger dicha política.

