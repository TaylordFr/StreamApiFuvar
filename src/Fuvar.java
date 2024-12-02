public class Fuvar {

    private int taxiId;
    private String indulasIdopontja;
    private int idotartam;
    private double megtettTavolsag;
    private double viteldij;
    private double borravalo;
    private String fizetesModja;

    public Fuvar(int taxiId, String indulasIdopontja, int idotartam, double megtettTavolsag, double viteldij, double borravalo, String fizetesModja) {
        this.taxiId = taxiId;
        this.indulasIdopontja = indulasIdopontja;
        this.idotartam = idotartam;
        this.megtettTavolsag = megtettTavolsag;
        this.viteldij = viteldij;
        this.borravalo = borravalo;
        this.fizetesModja = fizetesModja;
    }

    public int getTaxiId() {
        return taxiId;
    }

    public void setTaxiId(int taxiId) {
        this.taxiId = taxiId;
    }

    public String getIndulasIdopontja() {
        return indulasIdopontja;
    }

    public void setIndulasIdopontja(String indulasIdopontja) {
        this.indulasIdopontja = indulasIdopontja;
    }

    public int getIdotartam() {
        return idotartam;
    }

    public void setIdotartam(int idotartam) {
        this.idotartam = idotartam;
    }

    public double getMegtettTavolsag() {
        return megtettTavolsag;
    }

    public void setMegtettTavolsag(double megtettTavolsag) {
        this.megtettTavolsag = megtettTavolsag;
    }

    public double getViteldij() {
        return viteldij;
    }

    public void setViteldij(double viteldij) {
        this.viteldij = viteldij;
    }

    public double getBorravalo() {
        return borravalo;
    }

    public void setBorravalo(double borravalo) {
        this.borravalo = borravalo;
    }

    public String getFizetesModja() {
        return fizetesModja;
    }

    public void setFizetesModja(String fizetesModja) {
        this.fizetesModja = fizetesModja;
    }

    @Override
    public String toString() {
        return String.format("\nFuvar[taxiId=%d\n indulasIdopontja=%s\n idotartam=%d\n megtettTavolsag=%.2f\n viteldij=%.2f\n borravalo=%.2f\n fizetesModja=%s]",
                                taxiId, indulasIdopontja, idotartam, megtettTavolsag, viteldij, borravalo, fizetesModja);
    }
}
