package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Scanner scanner1 = new Scanner(System.in);

/*
        int countOfNodes;
        do {
            System.out.println("Введите количество узлов: ");
            countOfNodes = scanner.nextInt();
        } while (countOfNodes < 0);

        int [][] valueMatrix = new int [countOfNodes][countOfNodes];        //матрица весов

        System.out.println("\nЗаполнение матрицы весов: ");

        System.out.println(" *88 - бесконечность \n **все элементы диагонали будут заменены на 0");
        for (int i = 0; i < countOfNodes; i++) {
            System.out.println("Введите элементы " + i + "-ой строки через пробел: ");
            String [] strRow = scanner1.nextLine().split(" ");           //запихиваем ввод в строку, сплитим ее по пробелу
            for (int j = 0; j < countOfNodes; j++)
            {
                valueMatrix[i][j] = Integer.parseInt(strRow[j]);          //заполняем матрицу значениями с той строки
            }
        }

*/

        int countOfNodes = 8;
        int [][] valueMatrix = {
                {0,1,6,1,5,88,88,88},
                {1,0,9,88,88,1,88,88},
                {6,9,0,88,88,4,88,1},
                {1,88,88,0,9,88,2,88},
                {5,88,88,9,0,88,8,8},
                {88,1,4,88,88,0,88,1},
                {88,88,88,2,8,88,0,8},
                {88,88,1,88,8,1,8,0}};

/*

        int countOfNodes = 5;
        int [][] valueMatrix = {
                {0,5,88,88,88},
                {88, 0,88,88,4},
                {88,1,0,3,88},
                {88,88,88,0,88},
                {88,6,3,4,0}};

*/

        for (int i = 0; i < countOfNodes; i++)
        {
            valueMatrix[i][i] = 0;              //заполняю диагональ нулями
        }

        int [][] waysMatrix = new int [countOfNodes][countOfNodes];

        for (int i = 0; i < countOfNodes; i++)
        {
            for (int j = 0; j < countOfNodes; j++)
            {
                waysMatrix[i][j] = j;
            }
        }

        printMatrix(valueMatrix, "Dstart = ");
        printMatrix(waysMatrix, "Sstart = ");

        //основной цикл!
        for (int counter = 0; counter < countOfNodes; counter++)
        {
            System.out.println("Вычеркнуты строка и столбец под номером: " + counter);
            int[] crossedRow = valueMatrix[counter];        //значения зачеркнутой строки

            int[] crossedColum = new int [countOfNodes];    //значения зачеркнутого столбца
            for (int i = 0; i < countOfNodes; i++)
            {
                crossedColum[i] = valueMatrix[i][counter];
            }

            for (int i = 0; i < countOfNodes; i++)
            {
                for (int j = 0; j < countOfNodes; j++)
                {
                    if (i != counter && j != counter && i != j && !(crossedRow[j] == 88 || crossedColum[i] == 88))     //если эта строка не вычеркнута и это не диагональ
                    {
                        if (crossedRow[j] + crossedColum[i] < valueMatrix[i][j])        //если есть альтернатива с меньшим весом, то
                        {
                            valueMatrix[i][j] = crossedRow[j] + crossedColum[i];        //присваиваем
                            waysMatrix[i][j] = counter;                                 //меняем матрицу путей
                        }
                    }
                }
            }

            printMatrix(valueMatrix, "D" + counter + " = ");
            printMatrix(waysMatrix, "S" + counter + " = ");
        }

        int selectedNode;
        do
        {
            System.out.println("Введите вершину, кратчайшие пути из которой, надо вывести: ");
            selectedNode = scanner.nextInt();
        } while (selectedNode < 0 || selectedNode > countOfNodes);

        System.out.println("Пути из вершины " + selectedNode + ":");
        System.out.println("В вершину\tДлина\tПуть");
        for (int node = 0; node < countOfNodes; node++)
        {
            if (valueMatrix[selectedNode][node] != 88)
            System.out.print(node + "\t\t\t" + valueMatrix[selectedNode][node] + "\t\t" + stringWay(waysMatrix, selectedNode, node) + "\n");
            else
                System.out.print(node + "\t\t\t" + valueMatrix[selectedNode][node] + "\t\t" + selectedNode + "--> Нет пути\n");
        }

    }

    public static void printMatrix (int[][] matrix, String msg)
    {
        System.out.println(msg);
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[0].length; j++)
            {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static String stringWay (int[][] waysMatrix, int start, int end)
    {
        StringBuilder result = new StringBuilder();
        ArrayList<Integer> plan = new ArrayList<Integer>();     //список с вершинами, которые нужно посетить
        plan.add(end);

        ArrayList<Integer> path = new ArrayList<>();            //список с путем
        path.add(start);

        int currentNode = start;
        int whereIneed = end;
        while (!plan.isEmpty())
        {
            whereIneed = plan.get(plan.size()-1);
            if (waysMatrix[currentNode][whereIneed] == whereIneed)
            {
                path.add(whereIneed);
                currentNode = whereIneed;
                plan.remove(plan.size()-1);
            } else
            {
                plan.add(waysMatrix[currentNode][whereIneed]);
            }
        }

        for (int i = 0; i < path.size(); i++)
        {
            result.append(path.get(i)).append(" ");
            if (i != path.size() - 1)
                result.append("--> ");

        }



        return result.toString();
    }
}
