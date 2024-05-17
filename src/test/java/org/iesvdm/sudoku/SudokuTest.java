package org.iesvdm.sudoku;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

public class SudokuTest {

    @Test
    void failTest() {
        Sudoku sudoku = new Sudoku();
        sudoku.fillBoardBasedInCluesRandomlySolvable();
        //sudoku.fillBoardBasedInCluesRandomly();
        sudoku.printBoard();
    }

    @Test
    @DisplayName("rellenaTablero - rellena el tablero entero con números aleatorios")
    void rellenaTablero() {

        //When
        Sudoku sudoku = new Sudoku();

        //Do
        sudoku.fillBoardRandomly();

        //Then
        assertThat(sudoku.getBoard()).isNotEmpty();

    }

    @Test
    @DisplayName("rellenaPizarraBasadoEnPistas - rellena el tablero según el número de pistas")
    void rellenaPizarraBasadoEnPistas() {

        //When
        Sudoku sudoku = new Sudoku();

        //Do
        sudoku.fillBoardBasedInCluesRandomly();

        //Then
        assertThat(sudoku.getBoard()).isNotEmpty();
        assertEquals(sudoku.getNumClues(), 63);

    }

    @Test
    @DisplayName("rellenaPizarraBasadoEnPistasSolvable - rellena el tablero basado en pistas y lo resuelve")
    void rellenaPizarraBasadoEnPistasSolvable() {

        //When
        Sudoku sudoku = new Sudoku();

        //Then
        sudoku.fillBoardBasedInCluesRandomlySolvable();

        //Do
        assertThat(sudoku.getBoard()).isNotEmpty();
        assertEquals(sudoku.getNumClues(), 63);
        assertThat(sudoku.solveBoard()).isTrue();
        // En este assert se espera que pase y que resuelva el sudoku

    }

    @Test
    @DisplayName("rellenaPizarraSolvable - rellena el tablero y lo resuelve")
    void fillBoardSolvable() {

        //When
        Sudoku sudoku = new Sudoku();

        //Do
        sudoku.fillBoardSolvable();

        //Then
        assertThat(sudoku.getBoard()).isNotEmpty();
        assertTrue(sudoku.solveBoard());

    }

    @Test
    @DisplayName("rellenaPizarraUnsolvable - rellena el tablero y no lo resuelve")
    void fillBoardUnsolvable() {

        //When
        Sudoku sudoku = new Sudoku();

        //Do
        sudoku.fillBoardUnsolvable();

        //Then
        assertThat(sudoku.getBoard()).isNotEmpty();
        assertFalse(sudoku.solveBoard());

    }


    @Test
    @DisplayName("copiaPizarra - copia la pizarra con todos los valores")
    void copiaPizarra() {

        //When
        Sudoku sudoku = new Sudoku();
        Sudoku sudoku2 = new Sudoku();

        //Do
        sudoku.fillBoardRandomly();
        sudoku2.copyBoard(sudoku.getBoard());

        //Then
        assertThat(sudoku.getBoard()).isNotEmpty();
        assertEquals(sudoku2.getBoard(), sudoku.getBoard());

    }

    @Test
    @DisplayName("poneNumeroEnPizarra - pone un número en la pizarra")
    void poneNumeroEnPizarra() {

        //When
        Sudoku sudoku = new Sudoku();

        //Do
        sudoku.fillBoardRandomly();
        sudoku.putNumberInBoard(5, 2, 1);

        //Then
        assertEquals(sudoku.getBoard()[2][1], 5);
        assertThat(sudoku.getBoard()).isNotEmpty();

    }

    @Test
    @DisplayName("imprimePizarra - imprime la pizarra")
    void imprimePizarra() {

        //When
        Sudoku sudoku = new Sudoku();

        //Do
        sudoku.fillBoardRandomly();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));
        sudoku.printBoard();

        //Then
        assertThat(outputStream.toString()).isNotEmpty();

    }

    @Test
    @DisplayName("numeroInRow -  verifica si un número se encuentra en una fila")
    void numeroInRow() {

        //When
        Sudoku sudoku = new Sudoku();

        //Do
        sudoku.fillBoardRandomly();
        boolean isNumberInRow = sudoku.isNumberInRow(1, 0);

        //Then
        assertThat(sudoku.getBoard()).isNotEmpty();
        assertTrue(isNumberInRow);

    }

    @Test
    @DisplayName("numeroInColumn -  verifica si un número se encuentra en una columna")
    void numeroInColumn() {

        //When
        Sudoku sudoku = new Sudoku();

        //Do
        sudoku.fillBoardRandomly();
        boolean isNumberInColumn = sudoku.isNumberInColumn(1, 1);

        //Then
        assertThat(sudoku.getBoard()).isNotEmpty();
        assertTrue(isNumberInColumn);

    }

    @Test
    @DisplayName("numeroInBox -  verifica si un número se encuentra en una caja")
    void numeroInBox() {

        //When
        Sudoku sudoku = new Sudoku();

        //Do
        sudoku.fillBoardRandomly();
        boolean isNumberInBox = sudoku.isNumberInBox(1, 1, 1);

        //Then
        assertThat(sudoku.getBoard()).isNotEmpty();
        assertTrue(isNumberInBox);

    }

    @Test
    @DisplayName("sitioValido - comprueba si el hueco en el que se pone un número está ocupado o no")
    void sitioValido() {

        //When
        Sudoku sudoku = new Sudoku();

        //Do
        sudoku.fillBoardRandomly();
        sudoku.isValidPlacement(1, 2, 1);

        //Then
        assertThat(sudoku.getBoard()).isNotEmpty();
        assertThat(sudoku.isValidPlacement(1, 2, 1)).isTrue();
        // me ha funcionado bien, pero ahora me falla;

    }

    @Test
    @DisplayName("sitioNoValido - comprueba si el hueco en el que se pone un número está ocupado o no")
    void sitioNoValido() {

        //When
        Sudoku sudoku = new Sudoku();

        //Do
        sudoku.fillBoardRandomly();
        sudoku.isValidPlacement(1, 1, 1);

        //Then
        assertThat(sudoku.getBoard()).isNotEmpty();
        assertThat(sudoku.isValidPlacement(1, 1, 1)).isFalse();

    }

    @Test
    @DisplayName("solveBoard - resuelve el tablero")
    void solveBoard() {

        //When
        Sudoku sudoku = new Sudoku();

        //Do
        sudoku.fillBoardRandomly();
        sudoku.solveBoard();

        //Then
        assertThat(sudoku.getBoard()).isNotEmpty();
        assertThat(sudoku.solveBoard()).isTrue();
        // En este assert último, se espera que pase y que se resuelva el sudoku;

    }

}
