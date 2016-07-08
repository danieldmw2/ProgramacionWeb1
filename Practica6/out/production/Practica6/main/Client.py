#!/usr/bin/env python

import os

from suds.client import Client
estudiantes = Client('http://localhost:9000/Estudiantes?wsdl')
asignaturas = Client('http://localhost:9000/Asignaturas?wsdl')

#ejecutando un metodo
print "Menu: "
print "\t1: Estudiantes"
print "\t2: Asignaturas"

seleccion = input("\nSeleccion: ")
os.system('cls')

if(seleccion == 1):
    print "Menu: "
    print "\t1: Insertar"
    print "\t2: Actualizar"
    print "\t3: Borrar"
    print "\t4: Mostrar todos los estudiantes"
    print "\t5: Buscar un estudiante especifico"
    seleccion = input("\nSeleccion: ")
    os.system('cls')

    if(seleccion == 1):
        matricula = input("Escriba la Matricula: ")
        nombre = raw_input("Escriba el nombre: ")
        carrera = raw_input("Escriba la carrera: ")

        estudiantes.service.insert(matricula, nombre, carrera)

    elif (seleccion == 2):
        matricula = input("Escriba la Matricula del Estudiante a cambiar: ")
        nombre = raw_input("Escriba el nuevo nombre: ")
        carrera = raw_input("Escriba la nueva carrera: ")

        estudiantes.service.update(matricula, nombre, carrera)

    elif (seleccion == 3):
        matricula = input("Escriba la Matricula del Estudiante a borrar: ")
        estudiantes.service.delete(matricula)

    elif (seleccion == 4):
        lista = estudiantes.service.select()
        #for i in range(0, lista):

    else:
        matricula = input("Escriba la Matricula del Estudiante: ")
        estudiante = estudiantes.service.selectByID(matricula)
        print estudiante


else:
    print "Menu: "
    print "\t1: Insertar"
    print "\t2: Actualizar"
    print "\t3: Borrar"
    print "\t4: Mostrar todos las asignaturas de un estudiante"
    print "\t5: Buscar una asignatura especifica"
    seleccion = input("\nSeleccion: ")
    os.system('cls')

os.system('cls')
print "Programa Terminado "




