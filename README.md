# Taller 02 - La fonda del Papu

## Descripcion del proyecto

Programa desarrollado para el Taller 02 de Programacion Orientada a Objetos.

El sistema administra los consumos de choripanes y terremotos realizados por
los participantes de una fonda. Permite consultar los registros, agregar,
editar y eliminar consumos, calcular metricas y guardar los cambios en el
archivo `Consumido.txt`.

## Integrantes

- Alvaro Elgueta - Rut: 21.806.097-8 - Usuario GitHub: [AlvaroElgueta](https://github.com/AlvaroElgueta)
- Catalina Fica    - Rut: 21.779.663-6 - Usuario GitHub: [panconmayo-pixel](https://github.com/panconmayo-pixel)

## Estructura del proyecto

El codigo se encuentra en el paquete `Taller02`.

```text
Taller02/
|-- src/
|   |-- module-info.java
|   `-- Taller02/
|       |-- Main.java
|       |-- Participante.java
|       |-- Consumo.java
|       |-- Choripan.java
|       `-- Terremoto.java
|-- Participantes.txt
|-- Consumido.txt
`-- README.md
```

## Instrucciones de ejecucion

1. Clonar el repositorio desde una terminal:

```bash
git clone https://github.com/AlvaroElgueta/Taller02.git
```

2. Abrir Eclipse y seleccionar `File > Import`.
3. Seleccionar `General > Existing Projects into Workspace`.
4. Elegir la carpeta clonada `Taller02`.
5. Ejecutar `src/Taller02/Main.java` como `Java Application`.
6. Probar las opciones del menu y volver a ejecutar el programa para comprobar
   que los cambios realizados en `Consumido.txt` se mantengan.
