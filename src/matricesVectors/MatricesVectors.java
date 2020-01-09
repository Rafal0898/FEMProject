package matricesVectors;

public class MatricesVectors {
    public static double[][] multiply1dMatrices(double[] matrix1, double[] matrix2, double[] detJ) {
        double[][] finalMatrix = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                finalMatrix[i][j] += (detJ[i] * matrix1[i] * matrix2[j]);
            }
        }
        return finalMatrix;
    }

    public static double[] sumVectors(double[] vector1, double[] vector2, int size) {
        double[] finalVector = new double[size];
        for (int i = 0; i < size; i++) {
            finalVector[i] += vector1[i] + vector2[i];
        }
        return finalVector;
    }

    public static double[] multiplyMatrixVector(double[][] matrix, double[] vector, int size) {
        double[] finalVector = new double[size];

        return finalVector;
    }

    public static double[][] sumMatrices(double[][] matrix1, double[][] matrix2, double alfa) {
        double[][] finalMatrix = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                finalMatrix[i][j] = alfa * (matrix1[i][j] + matrix2[i][j]);
            }
        }
        return finalMatrix;
    }

    public static void aggregateMatrix(double[][] globalMatrix, double[][] localMatrix){

    }

    public static double[][] calculateGlobalMatrixH(double[][] matrixH, double[][] matrixC, double dT, int size) {
        double[][] finalMatrixH = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                finalMatrixH[i][j] = matrixH[i][j] + matrixC[i][j] / dT;
            }
        }
        return finalMatrixH;
    }

    public static double[] calculateGlobalVectorP(double[] vectorP, double[][] matrixC, double dT, double[] T0, int size) {
        double[] tempVector = new double[size];
        double[][] matrixCdT = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrixCdT[i][j] = matrixC[i][j] * dT;
            }
        }
//TODO:        //{P}= {P}+{[C]/dT}*{T0}   CHECK IF WORKS
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tempVector[i] += matrixCdT[i][j] * T0[j];
            }
        }
        return sumVectors(vectorP, tempVector, size);

    }

    public static double[] calculateMatrixN(double ksi, double eta) {
        double[] N = new double[4];
        N[0] = 0.25 * (1 - ksi) * (1 - eta);
        N[1] = 0.25 * (1 + ksi) * (1 - eta);
        N[2] = 0.25 * (1 + ksi) * (1 + eta);
        N[3] = 0.25 * (1 - ksi) * (1 + eta);
        return N;
    }

    public double[][] transposeMatrix(double[][] matrix) {
        double[][] transposedMatrix = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                transposedMatrix[i][j] = matrix[j][i];
            }
        }
        return transposedMatrix;
    }

    public static void showMatrix(double[][] matrix, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] *= 10000;
                matrix[i][j] = Math.round(matrix[i][j]);
                matrix[i][j] /= 10000;
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.print("\n");
        }
    }

    public static void showVector(double[] vector, int size) {
        for (int i = 0; i < size; i++) {
            vector[i] *= 10000;
            vector[i] = Math.round(vector[i]);
            vector[i] /= 10000;
            System.out.print(vector[i] + "\t");
        }
    }
}
