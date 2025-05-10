package org.example.models;

public class Client extends Utilisateur{
    private int id_client;
    

    public Client(int id, String nom, String prenom, String nom_utilisateur, String email, String mot_de_passe, Role role, int id_client) {
        super(id, nom, prenom, nom_utilisateur, email, mot_de_passe, role);
        this.id_client = id_client;
    }

    public Client(String nom, String prenom, String nom_utilisateur, String email, String mot_de_passe, Role role, int id_client) {
        super(nom, prenom, nom_utilisateur, email, mot_de_passe, role);
        this.id_client = id_client;
    }

    public Client(String nom, String prenom, String nom_utilisateur, String email, String mot_de_passe, Role role) {
        super(nom, prenom, nom_utilisateur, email, mot_de_passe, role);
    }

    public Client(int id, String nom, String prenom, String nom_utilisateur, String email, String mot_de_passe, Role role) {
        super(id,nom, prenom, nom_utilisateur, email, mot_de_passe, role);
    }

    public Client(int id, String nom, String prenom, String nom_utilisateur, String email, String mot_de_passe, Role role, int ban, int id_client) {
        super(id, nom, prenom, nom_utilisateur, email, mot_de_passe, role, ban);
        this.id_client = id_client;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    @Override
    public String toString() {
        return "Client{" +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", nom_utilisateur='" + getNom_utilisateur() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", mot_de_passe='" + getMot_de_passe() + '\'' +
                ", role=" + getRole() + '\'' +
                "id_client=" + id_client +
                '}';
    }
}
