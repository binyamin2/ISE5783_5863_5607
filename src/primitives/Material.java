package primitives;

public class Material {
    public Double3 kD=Double3.ZERO,kS=Double3.ZERO;
    /**
     * refraction
     */
    public Double3 kT=Double3.ZERO;
    /**
     * reflection
     */
    public Double3 kR=Double3.ZERO;
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
     * set the refraction
     * @param kT
     * @return
     */
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * set the refraction
     * @param kT
     * @return
     */
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * set the reflection
     * @param kR
     * @return
     */
    public Material setKr(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * set the reflection
     * @param kR
     * @return
     */
    public Material setKr(double kR) {
        this.kR=new Double3(kR);
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
