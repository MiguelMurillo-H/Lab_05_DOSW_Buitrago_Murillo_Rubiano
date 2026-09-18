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

(<img width="777" height="148" alt="image" src="https://github.com/user-attachments/assets/33f22b59-264a-470b-a18f-4e1421f11689" />)

## Pull Requests

- PR JUnit: [#1](https://github.com/MiguelMurillo-H/Lab_05_DOSW_Buitrago_Murillo_Rubiano/pull/1)
- PR clases base: [#2](https://github.com/MiguelMurillo-H/Lab_05_DOSW_Buitrago_Murillo_Rubiano/pull/2)
- PR TDD addDrone: [#3](https://github.com/MiguelMurillo-H/Lab_05_DOSW_Buitrago_Murillo_Rubiano/pull/3) · cobertura completa: [#13](https://github.com/MiguelMurillo-H/Lab_05_DOSW_Buitrago_Murillo_Rubiano/pull/13)
- PR TDD assignMission: [#4](https://github.com/MiguelMurillo-H/Lab_05_DOSW_Buitrago_Murillo_Rubiano/pull/4) · dron ocupado: [#8](https://github.com/MiguelMurillo-H/Lab_05_DOSW_Buitrago_Murillo_Rubiano/pull/8) · distancia/autonomía: [#12](https://github.com/MiguelMurillo-H/Lab_05_DOSW_Buitrago_Murillo_Rubiano/pull/12) · operador inexistente/misión activa: [#14](https://github.com/MiguelMurillo-H/Lab_05_DOSW_Buitrago_Murillo_Rubiano/pull/14)
- PR TDD completeMission: [#6](https://github.com/MiguelMurillo-H/Lab_05_DOSW_Buitrago_Murillo_Rubiano/pull/6) · misión inexistente: [#7](https://github.com/MiguelMurillo-H/Lab_05_DOSW_Buitrago_Murillo_Rubiano/pull/7) · doble cierre: [#11](https://github.com/MiguelMurillo-H/Lab_05_DOSW_Buitrago_Murillo_Rubiano/pull/11)
- PR JaCoCo: [#15](https://github.com/MiguelMurillo-H/Lab_05_DOSW_Buitrago_Murillo_Rubiano/pull/15) · cobertura Drone: [#16](https://github.com/MiguelMurillo-H/Lab_05_DOSW_Buitrago_Murillo_Rubiano/pull/16)
- PR SonarQube: [#17](https://github.com/MiguelMurillo-H/Lab_05_DOSW_Buitrago_Murillo_Rubiano/pull/17)

## Reflexión técnica

**1. ¿Qué error o comportamiento inesperado fue detectado primero gracias a una prueba?**
El primer test de `addDrone` (`shouldRegisterDroneWhenDataIsValid`) evidenció que el método retornaba `false` incluso con datos válidos, ya que la lógica todavía no estaba implementada (`// TODO`). Esto confirmó el ciclo RED esperado antes de escribir la implementación mínima.

**2. ¿Qué parte del código cambió durante REFACTOR sin modificar el comportamiento?**
En `addDrone`, la cadena de validaciones (`drone == null`, `id == null`, `id.isBlank()`, `id` duplicado) se reorganizó usando `if / else if` encadenados en lugar de validaciones anidadas, mejorando la legibilidad sin alterar el resultado de ningún caso de prueba existente.

**3. ¿Qué casos adicionales aparecieron al revisar la cobertura?**
Al revisar el reporte de JaCoCo se detectó que la clase `Drone` no alcanzaba el umbral mínimo de cobertura (85%), por lo que se agregaron pruebas unitarias adicionales específicas para esa clase (PR #16), cubriendo comportamientos que no estaban siendo ejercitados por los tests de `RescueCenter`.

**4. ¿Qué hallazgo de SonarQube produjo un cambio real en el código?**
SonarQube reportó un issue de confiabilidad (Reliability) indicando que podía lanzarse un `NullPointerException` porque el campo `missions` era nullable en cierto punto del código. Este hallazgo llevó a corregir la validación correspondiente (PR #17).
