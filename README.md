# Lab_05_DOSW_Buitrago_Murillo_Rubiano
Laboratorio 5 de DOSW - TDD, Cubrimiento y Análisis estático

### Integrantes del equipo
- Juanita Rubiano
- Miguel Murillo
- Sergio Buitrago

### Descripción de SkyRescue
SkyRescue es la lógica de dominio de una plataforma que coordina drones de emergencia para transportar kits médicos, cámaras térmicas o radios hacia zonas de difícil acceso.
El sistema resuelve el problema de asignar drones y operadores a misiones de rescate evitando conflictos operativos: que un dron atienda dos emergencias a la vez, que se le exija una distancia mayor a su autonomía, que se asignen misiones a operadores inexistentes, que un operador controle varias misiones activas simultáneamente, o que una misión se cierre más de una vez. 
Las reglas principales del dominio giran en torno a la disponibilidad de los drones, su rango máximo (maxRangeKm), la existencia y disponibilidad de los operadores, y el estado (ACTIVE/COMPLETED) de cada misión. 
Las tres operaciones desarrolladas con TDD son: addDrone (registro de drones validando id y duplicados), assignMission (asignación de misiones validando dron, operador, distancia y disponibilidad) y completeMission (cierre de misiones liberando el dron y evitando doble cierre).

### Ciclo TDD - registro de dron válido

**RED:** prueba que demuestra que `addDrone` no registraba drones correctamente antes de la implementación.

![Prueba fallando](<img width="1120" height="605" alt="image" src="https://github.com/user-attachments/assets/bdc8327d-ddf9-4106-a874-c6d18b01cb87" />
)

![Prueba fallando](<img width="1126" height="613" alt="image" src="https://github.com/user-attachments/assets/be9af1d0-ac56-4979-a98e-924224d7260e" />
)

**GREEN:** implementación mínima que hace pasar la prueba.
![Prueba pasando](<img width="1132" height="582" alt="image" src="https://github.com/user-attachments/assets/0db520bf-3943-4744-ac21-04887df2efee" />
)

![Prueba pasando](<img width="578" height="167" alt="image" src="https://github.com/user-attachments/assets/2583d318-1998-47e5-b976-6e1077bd98f5" />
)

###Cobertura 
![Evidencias](<img width="1320" height="241" alt="image" src="https://github.com/user-attachments/assets/c5917031-5dce-4a0c-b607-a20137ad08bf" />
)
(<img width="1009" height="467" alt="image" src="https://github.com/user-attachments/assets/4ab6e566-a760-4f42-91cd-26e3a1ea35f9" />
)
(<img width="612" height="124" alt="image" src="https://github.com/user-attachments/assets/5fb2dbc5-a03f-40af-a129-868343d5d7c3" />
)

### SonarQube

