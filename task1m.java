package HM4Bolshova;

/* Крестики-нолики в процедурном стиле
1. Полностью разобраться с кодом, попробовать переписать с нуля, стараясь не подглядывать в методичку;
2.2. Переделать проверку победы, чтобы она не была реализована просто набором условий, например, с использованием циклов.
*/
import java.util.Random;
import java.util.Scanner;

public class task1m {
    //1. параметры игрового поля
    static final int SIZE_Y = 3; //размер поля по вертикале
    static final int SIZE_X = 3; //расчет поля по горизонтале
    static final int SIZE_WIN = 3; //кол-во заполненных подряд полей для победы
    static final char[][] fieldg =  new char [SIZE_Y][SIZE_X];
    // игровые элемент
    static final char player_DOT = 'X';
    static final char AI_DOT = 'O';
    static final char EMPTY_DOT = '.';

    // обявляется классов ввода и случайного числа для игры
    static Scanner scr = new Scanner(System.in);
    static Random rnd = new Random();

    //поле в начале игры
    private static void emtpyField () {
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                fieldg [i][j] = EMPTY_DOT;
            }
        }
    }

    //печать поля на экран
    private static void printField ()     {
        printFieldLine ();
        for (int i = 0; i < SIZE_Y; i++) {
            System.out.print("|");
            for (int j = 0; j < SIZE_X; j++) {
                System.out.print(fieldg[i][j]+"|");
            }
            System.out.println("");
        }
        printFieldLine ();
    }
    //чертим линию для поля
    private static void printFieldLine () {
        for (int i = 0; i < fieldg.length * 2 + 1; i++) {
            System.out.print("-");
        }
        System.out.println("");
    }
    //запись хода игрока на поле
    private static void dotField (int y, int x, char dot) {
        fieldg [y][x] = dot;
    }
    //Ход человева
    private static void playerMove () {
        int x, y;
        do {
            System.out.printf("Введите координаты хода через пробел в формате X(= 1..%d) Y(= 1..%d):\n", SIZE_X, SIZE_Y);
            x = Integer.valueOf(scr.next()) - 1;
            y = Integer.valueOf(scr.next()) - 1;
        } while (!checkMove(y, x));
        dotField(y, x, player_DOT);
    }
    //Ход компьютера
    private static void AiMove () {
        int x, y;
        //блокировка ходов человека
        for (int v = 0; v < SIZE_Y; v++) {
            for (int h = 0; h < SIZE_X; h++) {
                //анализ наличие поля для проверки
                if (h + SIZE_WIN <= SIZE_X) {                           //по горизонтале
                    if (checkLineHorisont(v, h, player_DOT) == SIZE_WIN - 1) {
                        if (MoveAiLineHorisont(v, h, AI_DOT)) return;
                    }

                    if (v - SIZE_WIN > -2) {                            //вверх по диагонале
                        if (checkDiaUp(v, h, player_DOT) == SIZE_WIN - 1) {
                            if (MoveAiDiaUp(v, h, AI_DOT)) return;
                        }
                    }
                    if (v + SIZE_WIN <= SIZE_Y) {                       //вниз по диагонале
                        if (checkDiaDown(v, h, player_DOT) == SIZE_WIN - 1) {
                            if (MoveAiDiaDown(v, h, AI_DOT)) return;
                        }
                    }
                }
                if (v + SIZE_WIN <= SIZE_Y) {                       //по вертикале
                    if (checkLineVertical(v, h, player_DOT) == SIZE_WIN - 1) {
                        if(MoveAiLineVertical(v, h, AI_DOT)) return;
                    }
                }
            }
        }
        //игра на победу
        for (int v = 0; v < SIZE_Y; v++) {
            for (int h = 0; h < SIZE_X; h++) {
                //анализ наличие поля для проверки
                if (h + SIZE_WIN <= SIZE_X) {                           //по горизонтале
                    if (checkLineHorisont(v, h, AI_DOT) == SIZE_WIN - 1) {
                        if (MoveAiLineHorisont(v, h, AI_DOT)) return;
                    }

                    if (v - SIZE_WIN > -2) {                            //вверх по диагонале
                        if (checkDiaUp(v, h, AI_DOT) == SIZE_WIN - 1) {
                            if (MoveAiDiaUp(v, h, AI_DOT)) return;
                        }
                    }
                    if (v + SIZE_WIN <= SIZE_Y) {                       //вниз по диагонале
                        if (checkDiaDown(v, h, AI_DOT) == SIZE_WIN - 1) {
                            if (MoveAiDiaDown(v, h, AI_DOT)) return;
                        }
                    }

                }
                if (v + SIZE_WIN <= SIZE_Y) {                       //по вертикале
                    if (checkLineVertical(v, h, AI_DOT) == SIZE_WIN - 1) {
                        if(MoveAiLineVertical(v, h, AI_DOT)) return;
                    }
                }
            }
        }

        //случайный ход
        do {
            y = rnd.nextInt(SIZE_Y);
            x = rnd.nextInt(SIZE_X);
        } while (!checkMove(y, x));
        dotField(y, x, AI_DOT);
    }

    //ход компьютера по горизонтале
    private static boolean MoveAiLineHorisont(int v, int h, char dot) {
        for (int j = h; j < SIZE_WIN; j++) {
            if ((fieldg[v][j] == EMPTY_DOT)) {
                fieldg[v][j] = dot;
                return true;
            }
        }
        return false;
    }
    //ход компьютера по вертикале
    private static boolean MoveAiLineVertical(int v, int h, char dot) {
        for (int i = v; i < SIZE_WIN; i++) {
            if ((fieldg[i][h] == EMPTY_DOT)) {
                fieldg[i][h] = dot;
                return true;
            }
        }
        return false;
    }
    //проверка заполнения всей линии по диагонале вверх

    private static boolean MoveAiDiaUp(int v, int h, char dot) {
        for (int i = 0, j = 0; j < SIZE_WIN; i--, j++) {
            if ((fieldg[v + i][h + j] == EMPTY_DOT)) {
                fieldg[v + i][h + j] = dot;
                return true;
            }
        }
        return false;
    }
    //проверка заполнения всей линии по диагонале вниз

    private static boolean MoveAiDiaDown(int v, int h, char dot) {

        for (int i = 0; i < SIZE_WIN; i++) {
            if ((fieldg[i + v][i + h] == EMPTY_DOT)) {
                fieldg[i + v][i + h] = dot;
                return true;
            }
        }
        return false;
    }
    //проверка заполнения выбранного для хода игроком
    private static boolean checkMove(int y, int x) {
        if (x < 0 || x >= SIZE_X || y < 0 || y >= SIZE_Y) return false;
        else return fieldg[y][x] == EMPTY_DOT;

    }
    //проверка на ничью (все  ячейки поля заполнены ходами)
    private  static boolean fullField() {
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                if (fieldg[i][j] == EMPTY_DOT) return false;
            }
        }
        System.out.println("Игра закончилась в ничью");
        return true;
    }

    //проверка победы
    private static boolean checkWin(char dot) {
        for (int v = 0; v < SIZE_Y; v++){
            for (int h = 0; h < SIZE_X; h++) {
                //анализ наличие поля для проверки
                if (h + SIZE_WIN <= SIZE_X) {                           //по горизонтали
                    if (checkLineHorisont(v, h, dot) >= SIZE_WIN) return true;

                    if (v - SIZE_WIN > -2) {                            //вверх по диагонали
                        if (checkDiaUp(v, h, dot) >= SIZE_WIN) return true;
                    }
                    if (v + SIZE_WIN <= SIZE_Y) {                       //вниз по диагонали
                        if (checkDiaDown(v, h, dot) >= SIZE_WIN) return true;
                    }
                }
                if (v + SIZE_WIN <= SIZE_Y) {                       //по вертикали
                    if (checkLineVertical(v, h, dot) >= SIZE_WIN) return true;
                }
            }
        }
        return false;
    }

    //проверка заполнения всей линии по диагонали вверх

    private static int checkDiaUp(int v, int h, char dot) {
        int count=0;
        for (int i = 0, j = 0; j < SIZE_WIN; i--, j++) {
            if ((fieldg[v+i][h+j] == dot)) count++;
        }
        return count;
    }
    //проверка заполнения всей линии по диагонали вниз

    private static int checkDiaDown(int v, int h, char dot) {
        int count=0;
        for (int i = 0; i < SIZE_WIN; i++) {
            if ((fieldg[i+v][i+h] == dot)) count++;
        }
        return count;
    }

    private static int checkLineHorisont(int v, int h, char dot) {
        int count=0;
        for (int j = h; j < SIZE_WIN + h; j++) {
            if ((fieldg[v][j] == dot)) count++;
        }
        return count;
    }
    //проверка заполнения всей линии по вертикали
    private static int checkLineVertical(int v, int h, char dot) {
        int count=0;
        for (int i = v; i< SIZE_WIN + v; i++) {
            if ((fieldg[i][h] == dot)) count++;
        }
        return count;
    }

    public static void main(String[] args) {
        emtpyField();
        printField();
        do {
            playerMove();
            System.out.println("Ваш ход на поле");
            printField();
            if (checkWin(player_DOT)) {
                System.out.println("Вы выиграли");
                break;
            } else if (fullField()) break;
            AiMove();
            System.out.println("Ход ИИ на поле");
            printField();
            if (checkWin(AI_DOT)) {
                System.out.println("Выиграл ИИ");
                break;
            } else if (fullField()) break;
        } while (true);
        System.out.println("!Конец игры!");
    }
}