#Esteban Elias Pacheco Vega

"""
Crea un programa en Python que gestione una 
lista de libros en una biblioteca
"""
from datetime import datetime
from tabulate import tabulate
import os
import msvcrt
import time
import csv

#Colecciones para almacenar libros
LIBROS = []

def limpiar():
    print('<<Press any key to continue>>')
    msvcrt.getch()
    os.system('cls')

def espera():
    time.sleep(1) #Añade un retraso de 1 segundo

def printR(texto):  #Color Rojo
    print(f'\033[31m{texto}\033[0m')

def printA(texto):  #Color Verde
    print(f'\033[32m{texto}\033[0m')

def printV(texto):  #Color Amarillo
    print(f'\033[33m{texto}\033[0m')

#Interfaz inicio
def menu():
    printA('Sistema gestión de Biblioteca 📚')
    printA('-------------------------------------------')
    print("""1) Agregar Libro.
2) Mostrar Libros.
3) Buscar Libro por título.
4) Actualizar información del libro.
5) Guardar libros en un archivo CSV.
6) Salir del programa.""")
    printA('-------------------------------------------')    

#Validar fechas (que tenga formato de fecha)
def validarFecha(fecha):
    try:
        datetime.strptime(fecha, '%Y-%m-%d')
        return True
    except ValueError:
        return False

#Validar titulo del libro
def validarTitulo(titulo):
    for i in range(len(LIBROS)):
        if LIBROS[i][0]==titulo:
            return i 
    return -1

#Registramos Libro
def agregarLibro():
    #INGRESAMOS TITULO DEL LIBRO
    titulo = input('Ingrese el título del libro: ').strip().upper()
    if not titulo:
        printR('El título no puede estar vacío.')
        return
    #LLAMAMOS A LA VALIDACION DEL TITULO
    if validarTitulo(titulo) != -1:
        printR('Título repetido.')
        return 
       
    #INGRESAMOS AUTOR DEL LIBRO
    autor = input('Ingrese el autor del libro: ').strip().upper()
    if not autor:
        printR('Autor del libro no puede estar vacío.')
        return

    #INGRESAMOS AÑO DE PUBLICACIÓN
    anio_public = input('Ingrese el año de publicación del libro (EJ: YYYY-MM-DD): ').strip()
    #LLAMAMOS A LA VALIDACION Y GUARDAMOS TIPO FECHA
    if not validarFecha(anio_public):
        printR('Fecha de publicación no válida.')
        return

    #INGRESAMOS GENERO DEL LIBRO
    genero_libro = input('Ingrese el género del libro: ').strip().capitalize()
    if not genero_libro:
        printR('El género del libro no puede estar vacío.')
        return
    
    #Agregar libro a la colección
    LIBROS.append([titulo, autor, anio_public, genero_libro])
    printV(f'El libro "{titulo}" ha sido agregado a la biblioteca.')  

#Mostrar los libros que hay en el sistema    
def mostrarLibros():
    if len(LIBROS) > 0:
        headers = ['Título', 'Autor', 'Año de publicación', 'Género']
        print(tabulate(LIBROS, headers=headers, tablefmt='grid'))
    else:
        printR('No hay libros registrados.')
    
#Filtramos por titulo
def filtroTitulo():
    titulo = input('Ingrese el título del libro que desea buscar: ').strip().upper()
    if titulo:
        for libro in LIBROS:
            if libro[0].lower() == titulo.lower(): # Filtrar por Título
                headers = ['Título', 'Autor', 'Año de publicación', 'Género']
                print(tabulate([libro], headers=headers, tablefmt='grid'))
                return
        printR(f'No se encontró ningún libro con el título "{titulo}".')
    else:
        printR('El título no puede estar vacío.')

#MODIFICAR LIBROS
def modLibro():
    titulo = input('Ingrese título del libro a modificar: ').strip().upper()
    pos = validarTitulo(titulo)
    if pos>=0:
        libro = LIBROS[pos]
        nuevo_autor = input(f'Ingrese nuevo autor (actual: {libro[1]}): ').strip().upper()
        nuevo_anio_public = input(f'Ingrese nuevo año de publicación (actual: {libro[2]}): ').strip()
        if not validarFecha(nuevo_anio_public):
            printR('Fecha de publicación no válida')
            return  
        nuevo_genero_libro = input(f'Ingrese nuevo género del libro (actual: {libro[3]}): ').strip().capitalize()
        
        LIBROS[pos] = [titulo, nuevo_autor, nuevo_anio_public, nuevo_genero_libro]
        printV(f'El libro "{titulo}" actualizado con éxito.')      
    else:
        printR('Título no existe.')

def ImprimirHojaLibros():
    nombre_archivo = input("Ingrese el nombre del archivo CSV donde desea guardar la lista de libros: ")
    if len(LIBROS)>0:
        with open(f'{nombre_archivo}.csv', mode='w', newline='', encoding='utf-8') as archivo_csv:
            writer = csv.writer(archivo_csv, delimiter=',')
            LIBROS.insert(0,['Título','Autor','Año de publicación', 'Género'])
            writer.writerows(LIBROS)
            LIBROS.pop(0)
        printV('Reporte Generado.')
    else:
        printR('No hay Libros registrados.')

def main():
    while True:
        limpiar()
        menu()
        opcion = input('Seleccione: ')

        if opcion == '6':
            printV('Saliendo del programa...')
            espera()
            break
        elif opcion == '1':
            printV('Registrar Libro')
            agregarLibro()
        elif opcion == '2':
            printV('Lista de Libros')
            mostrarLibros()
        elif opcion == '3':
            printV('Buscar libro por título')
            filtroTitulo()
        elif opcion == '4':
            printV('Actualizar información del libro')
            modLibro()
        elif opcion == '5':
            printA('Guardar libros en un archivo CSV')
            ImprimirHojaLibros()
        else:
            printR('Opción no válida')