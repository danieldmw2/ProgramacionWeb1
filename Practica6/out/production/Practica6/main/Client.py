#!/usr/bin/env python

from suds.client import Client
url = 'http://localhost:9000/Estudiantes?WSDL'
client = Client(url)

#ejecutando un metodo
salida = client.service.guevo()
print salida