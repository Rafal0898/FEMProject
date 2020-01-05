package grid;

public class GlobalData {
    private double h, w;
    private int nH, nW, nN, nE;

    public GlobalData(double h, double w, int nH, int nW) {
        this.h = h;
        this.w = w;
        this.nH = nH;
        this.nW = nW;
        this.nN = nH * nW;
        this.nE = (nH - 1) * (nW - 1);
    }
    public double getH() {
        return h;
    }

    public double getW() {
        return w;
    }

    public int getnH() {
        return nH;
    }

    public int getnW() {
        return nW;
    }

    public int getnN() {
        return nN;
    }

    public int getnE() {
        return nE;
    }

}
