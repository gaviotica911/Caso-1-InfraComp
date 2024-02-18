

def calcular_siguiente_turno(matriz):
    filas = len(matriz)
    columnas = len(matriz[0])
    
    def contar_vecinos_vivos(matriz, fila, columna):
        vecinos_vivos = 0
        for i in range(max(0, fila - 1), min(filas, fila + 2)):
            for j in range(max(0, columna - 1), min(columnas, columna + 2)):
                if (i != fila or j != columna) and matriz[i][j]:
                    vecinos_vivos += 1
        return vecinos_vivos
    
    siguiente_turno = [[False] * columnas for _ in range(filas)]
    
    for i in range(filas):
        for j in range(columnas):
            vecinos_vivos = contar_vecinos_vivos(matriz, i, j)
            if matriz[i][j]:  # Celda viva
                if vecinos_vivos < 2 or vecinos_vivos > 3:
                    siguiente_turno[i][j] = False  # Muere por sobrepoblación o aislamiento
                else:
                    siguiente_turno[i][j] = True  # Vive
            else:  # Celda muerta
                if vecinos_vivos == 3:
                    siguiente_turno[i][j] = True  # Nace
                else:
                    siguiente_turno[i][j] = False
    return siguiente_turno

# Función para imprimir la matriz
def imprimir_matriz(matriz):
    for fila in matriz:
        print(", ".join(map(str, fila)))

# Ejemplo de uso:
matriz_inicial = [
    [False, False, True, False],
    [True, True, False, False],
    [True, False, False, True],
    [False, True, True, True]
]

# Calculamos y mostramos los siguientes turnos
for _ in range(5):
    print("Turno:")
    imprimir_matriz(matriz_inicial)
    print()
    matriz_inicial = calcular_siguiente_turno(matriz_inicial)
