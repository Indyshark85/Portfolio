import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {

  @Test
  void testMatrix() {
    int[][] data = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    };
    Matrix matrix = new Matrix(3, 3, data);

    assertAll(
            () -> assertEquals(3, matrix.getRows()),
            () -> assertEquals(3, matrix.getCols()),
            () -> assertArrayEquals(data, matrix.getMatrix())
    );

    int[][] data2 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    };
    Matrix matrix2 = new Matrix(3, 3, data2);
    matrix2.set(1, 1, 10);

    assertAll(
            () -> assertEquals(10, matrix2.getMatrix()[1][1]),
            () -> assertEquals(6, matrix2.getMatrix()[1][2])
    );
    int[][] data3 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    };
    int[][] data4 = {
            {9, 8, 7},
            {6, 5, 4},
            {3, 2, 1}
    };
    int[][] expectedSum = {
            {10, 10, 10},
            {10, 10, 10},
            {10, 10, 10}
    };

    Matrix matrix3 = new Matrix(3, 3, data3);
    Matrix matrix4 = new Matrix(3, 3, data4);

    assertAll(
            () -> assertTrue(matrix3.add(matrix4)),
            () -> assertArrayEquals(expectedSum, matrix3.getMatrix())
    );
  }
  void testRotateMethod() {
    int[][] data = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    };
    int[][] expectedRotation = {
            {7, 4, 1},
            {8, 5, 2},
            {9, 6, 3}
    };

    Matrix matrix = new Matrix(3, 3, data);
    matrix.rotate();

    assertAll(
            () -> assertEquals(3, matrix.getRows()),
            () -> assertEquals(3, matrix.getCols()),
            () -> assertArrayEquals(expectedRotation, matrix.getMatrix())
    );
  }
  void testToStringMethod() {
    int[][] data = {
            {1, 2, 3},
            {4, 5, 6}
    };
    String expectedString = "[[1, 2, 3], [4, 5, 6]]";

    Matrix matrix = new Matrix(2, 3, data);

    assertAll(
            () -> assertEquals(expectedString, matrix.toString())
    );
  }





}