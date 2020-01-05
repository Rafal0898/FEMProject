package matrixes_vectors;

public class MatrixVectorOperations {
    public static double[][] Matrix1DMultiplication(double[] matrix1, double[] matrix2, double[] detJ) {
        double[][] finalMatrix = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                finalMatrix[i][j] += (detJ[i] * matrix1[i] * matrix2[j]);
            }
        }
        return finalMatrix;
    }

    public static double[][] MatrixSum(double[][] matrix1, double[][]matrix2, double alfa){
        double[][] finalMatrix = new double[4][4];
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                finalMatrix[i][j] = alfa *(matrix1[i][j] + matrix2[i][j]);
            }
        }
        return   finalMatrix;
    }

    public static double[][] CalculateGlobalMatrixH(double[][] matrixH, double[][] matrixC, double dT) {
        double[][] finalMatrixH = new double[16][16];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                finalMatrixH[i][j] = matrixH[i][j] + matrixC[i][j] / dT;
            }
        }
        return finalMatrixH;
    }

    public static double[] CalculateVectorP(double[] vectorP, double[][] matrixC, double dT, double[] T0) {
        double[] finalVectorP = new double[16];
        //TODO
        //{P}= {P}+{[C]/dT}*{T0}
        return finalVectorP;
    }

    public static double[] CalculateMatrixN(double ksi, double eta){
        double[] N = new double[4];
        N[0] = 0.25 * (1 - ksi) * (1 - eta);
        N[1] = 0.25 * (1 + ksi) * (1 - eta);
        N[2] = 0.25 * (1 + ksi) * (1 + eta);
        N[3] = 0.25 * (1 - ksi) * (1 + eta);
        return N;
    }
    public double[][] MatrixTranspose(double[][] matrix) {
        double[][] transposedMatrix = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                transposedMatrix[i][j] = matrix[j][i];
            }
        }
        return transposedMatrix;
    }
}
