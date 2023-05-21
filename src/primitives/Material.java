package primitives;

public class Material {
    public Double3 kD=Double3.ZERO,kS=Double3.ZERO;
    public int Shininess=0;

    /**
     * set the  defusive
     * @param kD
     * @return
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * set the specular
     * @param kS
     * @return
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;

    }

    /**
     * set the shininess
     * @param nShininess
     * @return
     */
    public Material setShininess(int nShininess) {
        this.Shininess = nShininess;
        return this;

    }
    /**
     * set the  defusive
     * @param kD
     * @return
     */
    public Material setKd(double kD) {
        this.kD =  new Double3(kD);
        return this;
    }
    /**
     * set the specular
     * @param kS
     * @return
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;

    }
    /**
     * set the shininess
     * @param nShininess
     * @return
     */
    public Material setShininess(double nShininess) {
        this.Shininess = ((int) nShininess);
        return this;

    }
}
