#!/usr/bin/env python

from suds.client import Client
url = 'http://localhost:9000/HelloWorld?WSDL'
client = Client(url)

#ejecutando un metodo
salida = client.service.sayHelloWorldFrom('Python')
print salida