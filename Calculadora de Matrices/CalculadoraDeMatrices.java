//@author   Alanís Mercado Alexis Yael
//author    Frías Campos Ashly Daniela
//author    Leyva Morales Kevin Tairi
//author    Pineda Ruiz Carlos Alberto
//PROYECTO DE ALGEBRA LINEAL 2257
package org.example;

import java.util.Scanner;

public class CalculadoraDeMatrices{
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Este programa te permitirá ingresar el número de filas y columnas de las matrices, así como los elementos de cada una. A continuación, realizará las operaciones seleccionadas y mostrará los resultados por consola.");
        System.out.print("Ingrese el número de filas de las matrices: ");
        int filas = input.nextInt();
        System.out.print("Ingrese el número de columnas de las matrices: ");
        int columnas = input.nextInt();

        double[][] matriz1 = new double[filas][columnas];
        double[][] matriz2 = new double[filas][columnas];

        System.out.println("Ingrese los elementos de la matriz 1:");
        leerMatriz(matriz1, input);

        System.out.println("Ingrese los elementos de la matriz 2:");
        leerMatriz(matriz2, input);

        // Realizar operaciones
        double[][] suma = sumaMatrices(matriz1, matriz2);
        double[][] resta = restaMatrices(matriz1, matriz2);
        double[][] producto = productoMatrices(matriz1, matriz2);
        double determinante = determinanteMatriz(matriz1);
        double[][] sistema = resolverSistema(matriz1, matriz2);

        // Imprimir resultados
        System.out.println("Resultado de la suma de matrices:");
        imprimirMatriz(suma);

        System.out.println("Resultado de la resta de matrices:");
        imprimirMatriz(resta);

        System.out.println("Resultado del producto de matrices:");
        imprimirMatriz(producto);

        System.out.println("Determinante de la matriz 1:");
        System.out.println(determinante);

        System.out.println("Solución del sistema de ecuaciones:");
        imprimirMatriz(sistema);
    }

    public static void leerMatriz(double[][] matriz, Scanner input) {
        int filas = matriz.length;
        int columnas = matriz[0].length;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print("Elemento (" + (i+1) + "," + (j+1) + "): ");
                matriz[i][j] = input.nextDouble();
            }
        }
    }

    public static double[][] sumaMatrices(double[][] matriz1, double[][] matriz2) {
        int filas = matriz1.length;
        int columnas = matriz1[0].length;

        double[][] resultado = new double[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                resultado[i][j] = matriz1[i][j] + matriz2[i][j];
            }
        }

        return resultado;
    }

    public static double[][] restaMatrices(double[][] matriz1, double[][] matriz2) {
        int filas = matriz1.length;
        int columnas = matriz1[0].length;

        double[][] resultado = new double[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                resultado[i][j] = matriz1[i][j] - matriz2[i][j];
            }
        }

        return resultado;
    }

    public static double[][] productoMatrices(double[][] matriz1, double[][] matriz2) {
        int filas1 = matriz1.length;
        int columnas1 = matriz1[0].length;
        int columnas2 = matriz2[0].length;
        double[][] resultado = new double[filas1][columnas2];

        for (int i = 0; i < filas1; i++) {
            for (int j = 0; j < columnas2; j++) {
                for (int k = 0; k < columnas1; k++) {
                    resultado[i][j] += matriz1[i][k] * matriz2[k][j];
                }
            }
        }

        return resultado;
    }

    public static double determinanteMatriz(double[][] matriz) {
        int filas = matriz.length;
        int columnas = matriz[0].length;

        if (filas != columnas) {
            throw new IllegalArgumentException("La matriz debe ser cuadrada");
        }

        if (filas == 1) {
            return matriz[0][0];
        }

        if (filas == 2) {
            return matriz[0][0] * matriz[1][1] - matriz[0][1] * matriz[1][0];
        }

        double determinante = 0;

        for (int i = 0; i < filas; i++) {
            double[][] submatriz = new double[filas - 1][columnas - 1];

            for (int j = 1; j < filas; j++) {
                for (int k = 0, indiceColumna = 0; k < columnas; k++) {
                    if (k == i) {
                        continue;
                    }

                    submatriz[j - 1][indiceColumna] = matriz[j][k];
                    indiceColumna++;
                }
            }

            determinante += Math.pow(-1, i) * matriz[0][i] * determinanteMatriz(submatriz);
        }

        return determinante;
    }

    public static double[][] resolverSistema(double[][] matrizCoeficientes, double[][] matrizResultados) {
        int filas = matrizCoeficientes.length;
        int columnas = matrizCoeficientes[0].length;

        if (filas != columnas) {
            throw new IllegalArgumentException("La matriz de coeficientes debe ser cuadrada");
        }

        if (filas != matrizResultados.length) {
            throw new IllegalArgumentException("El número de filas de la matriz de coeficientes y la matriz de resultados debe ser el mismo");
        }

        double[][] ampliada = new double[filas][filas + 1];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < filas; j++) {
                ampliada[i][j] = matrizCoeficientes[i][j];
            }
            ampliada[i][filas] = matrizResultados[i][0];
        }

        for (int i = 0; i < filas - 1; i++) {
            if (ampliada[i][i] == 0) {
                for (int j = i + 1; j < filas; j++) {
                    if (ampliada[j][i] != 0) {
                        double[] temp = ampliada[i];
                        ampliada[i] = ampliada[j];
                        ampliada[j] = temp;
                        break;
                    }
                }
            }

            for (int j = i + 1; j < filas; j++) {
                double factor = ampliada[j][i] / ampliada[i][i];
                for (int k = i; k < filas + 1; k++) {
                    ampliada[j][k] -= factor * ampliada[i][k];
                }
            }
        }
        double[] soluciones = new double[filas];

        for (int i = filas - 1; i >= 0; i--) {
            double suma = 0;
            for (int j = i + 1; j < filas; j++) {
                suma += ampliada[i][j] * soluciones[j];
            }
            soluciones[i] = (ampliada[i][filas] - suma) / ampliada[i][i];
        }

        double[][] resultado = new double[filas][1];
        for (int i = 0; i < filas; i++) {
            resultado[i][0] = soluciones[i];
        }

        return resultado;
    }

    public static void imprimirMatriz(double[][] matriz) {
        int filas = matriz.length;
        int columnas = matriz[0].length;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }
}
