#!/usr/bin/env python

import os

from suds.client import Client
estudiantes = Client('http://localhost:9000/Estudiantes?wsdl')
asignaturas = Client('http://localhost:9000/Asignaturas?wsdl')

seleccion = 0
while seleccion < 3:
    os.system('cls')

    print "Menu: "
    print "\t1: Estudiantes"
    print "\t2: Asignaturas"
    print "\t3: Terminar Programa"

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
            for i in range(0, len(lista)):
                os.system('cls')

                estudiante = lista[i]
                print "Estudiante #" + str(i + 1)
                print "\tMatricula: " + str(estudiante.matricula)
                print "\tNombre: " + estudiante.nombre
                print "\tCarrera: " + estudiante.carrera

                for i in range(0, len(estudiante.asignaturas)):
                    print "\tAsignatura #" + str(i + 1) + ": " + estudiante.asignaturas[i].codigo + " - " + estudiante.asignaturas[i].nombre

                seleccion = input("\nPresione 1 para terminar\nPresione 2 para ver el siguiente estudiante\nSeleccion: ")

                if(seleccion == 1):
                    break


        else:
            matricula = input("Escriba la Matricula del Estudiante: ")
            estudiante = estudiantes.service.selectByID(matricula)

            print "Matricula: " + str(estudiante.matricula)
            print "Nombre: " + estudiante.nombre
            print "Carrera: " + estudiante.carrera

            for i in range(0, len(estudiante.asignaturas)):
                print "Asignatura #" + str(i + 1) + ": " + estudiante.asignaturas[i].codigo + " - " + estudiante.asignaturas[i].nombre

            raw_input("\nPresione Enter para continuar")

        seleccion = 0


    elif (seleccion == 2):
        print "Menu: "
        print "\t1: Insertar"
        print "\t2: Actualizar"
        print "\t3: Borrar"
        seleccion = input("\nSeleccion: ")
        os.system('cls')

        if(seleccion == 1):
            matricula = input("Escriba la Matricula: ")
            codigo = raw_input("Escriba el codigo: ")
            nombre = raw_input("Escriba la nombre: ")

            asignaturas.service.insert(matricula, codigo, nombre)

        elif (seleccion == 2):
            matricula = input("Escriba la Matricula del Estudiante a cambiar: ")
            codigo = raw_input("Escriba el codigo: ")
            nombre = raw_input("Escriba el nuevo nombre: ")

            asignaturas.service.update(matricula, codigo, nombre)

        elif (seleccion == 3):
            matricula = input("Escriba la Matricula del Estudiante: ")
            codigo = raw_input("Escriba el codigo de la Asignatura que desea borrar: ")
            asignaturas.service.remove(matricula, codigo)

        seleccion = 0

os.system('cls')
print "Programa Terminado "




