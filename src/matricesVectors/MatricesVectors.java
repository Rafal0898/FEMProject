package matricesVectors;

import java.util.List;

import grid.Node;

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

    public static double[][] sumMatrices(double[][] matrix1, double[][] matrix2, double alfa) {
        double[][] finalMatrix = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                finalMatrix[i][j] = alfa * (matrix1[i][j] + matrix2[i][j]);
            }
        }
        return finalMatrix;
    }

    public static void aggregateVector(double[] globalVector, double[] localVector, List<Node> elementsNodeList){
        int[] id = new int[4];
        for (int i = 0; i < 4; i++) {
            id[i] = elementsNodeList.get(i).getId() - 1;
        }
        for (int j = 0; j < 4; j++) {
                globalVector[id[j]] += localVector[j];
        }
    }

    public static void aggregateMatrix(double[][] globalMatrix, double[][] localMatrix, List<Node> elementsNodeList) {
        int[] id = new int[4];
        for (int i = 0; i < 4; i++) {
            id[i] = elementsNodeList.get(i).getId() - 1;
        }
        for (int j = 0; j < 4; j++) {
            for (int k = 0; k < 4; k++) {
                globalMatrix[id[j]][id[k]] += localMatrix[j][k];
            }
        }
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

    public static double[] calculateGlobalVectorPCdT(double[] vectorP, double[][] matrixC, double dT, double[] T0, int size) {
        double[] tempVector = new double[size];
        double[] copyVectorP = copyVector(vectorP, size);
        double[][] matrixCdT = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrixCdT[i][j] = matrixC[i][j] / dT;
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tempVector[i] += matrixCdT[i][j] * T0[j];
            }
        }
        return sumVectors(copyVectorP, tempVector, size);
    }

    public static double[] calculateMatrixN(double ksi, double eta) {
        double[] N = new double[4];
        N[0] = 0.25 * (1 - ksi) * (1 - eta);
        N[1] = 0.25 * (1 + ksi) * (1 - eta);
        N[2] = 0.25 * (1 + ksi) * (1 + eta);
        N[3] = 0.25 * (1 - ksi) * (1 + eta);
        return N;
    }

    public static double[][] copyMatrix(double[][] matrix, int size) {
        double[][] finalMatrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                finalMatrix[i][j] = matrix[i][j];
            }
        }
        return finalMatrix;
    }

    public static double[] copyVector(double[] vector, int size) {
        double[] finalVector = new double[size];
        for (int i = 0; i < size; i++) {
            finalVector[i] = vector[i];
        }
        return finalVector;
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

    public static void roundVector(double[] vector, int size) {
        for (int i = 0; i < size; i++) {
            vector[i] *= 100;
            vector[i] = Math.round(vector[i]);
            vector[i] /= 100;
        }
    }

    public static void showVector(double[] vector, int size) {
        for (int i = 0; i < size; i++) {
            roundVector(vector, size);
            System.out.print(vector[i] + "  ");
        }
        System.out.println();
    }
}
