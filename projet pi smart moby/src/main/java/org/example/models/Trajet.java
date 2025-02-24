package org.example.models;
import java.sql.Timestamp;

public class Trajet {
    private int id;
    private String pointD;
    private String pointA;
    private Timestamp dateD;
    private Timestamp dateA;
    private double distance;
    private double prix;
    private String vehicule;
    private int id_veh;

    // Constructeur par défaut
    public Trajet() {}

    // Constructeur sans ID (ID auto-incrémenté par la base de données)
    public Trajet(String pointD, String pointA, Timestamp dateD, Timestamp dateA, double distance, double prix, int id_veh) {
        this.pointD = pointD;
        this.pointA = pointA;
        this.dateD = dateD;
        this.dateA = dateA;
        this.distance = distance;
        this.prix = prix;

        this.id_veh = id_veh;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPointD() { return pointD; }
    public void setPointD(String pointD) { this.pointD = pointD; }

    public String getPointA() { return pointA; }
    public void setPointA(String pointA) { this.pointA = pointA; }

    public Timestamp getDateD() { return dateD; }
    public void setDateD(Timestamp dateD) { this.dateD = dateD; }

    public Timestamp getDateA() { return dateA; }
    public void setDateA(Timestamp dateA) { this.dateA = dateA; }

    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    public String getVehicule() { return vehicule; }
    public void setVehicule(String vehicule) { this.vehicule = vehicule; }
    public int getId_veh() { return id_veh; }
    public void setId_veh(int id_veh) { this.id_veh = id_veh; }

    // Méthode toString()
    @Override
    public String toString() {
        return "Trajet{" +
                "id=" + id +
                ", pointD='" + pointD + '\'' +
                ", pointA='" + pointA + '\'' +
                ", dateD=" + dateD +
                ", dateA=" + dateA +
                ", distance=" + distance +
                ", prix=" + prix +
                '}';
    }
}
