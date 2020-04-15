package com.expo2020.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "stand", schema = "expo")
public class Stand implements Serializable {

    private static final long serialVersionUID = -2343243243242432341L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "standid")
    private Long id;

    @Column(name = "navn")
    private String navn;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "bransje")
    private String bransje;

    @Column(name = "beskrivelse")
    private String beskrivelse;

    @Column(name = "qr")
    private byte[] qrkode;

    @Transient
    private double gjennomsnitt;

    @OneToMany(mappedBy = "stand", fetch = FetchType.LAZY)
    private List<Stemme> stemmer;

    public Stand() {}

    public Stand(String navn, String adresse, String bransje, String beskrivelse) {
        this.navn = navn;
        this.adresse = adresse;
        this.bransje = bransje;
        this.beskrivelse = beskrivelse;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setBransje(String bransje) {
        this.bransje = bransje;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public String getNavn() {
        return navn;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getBransje() { return bransje;}

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public byte[] getQrkode() {
        return qrkode;
    }

    public void setQrkode(byte[] qrkode) {
        this.qrkode = qrkode;
    }

    public double getGjennomsnitt() {
        return gjennomsnitt;
    }

    public void setGjennomsnitt(double gjennomsnitt) {
        this.gjennomsnitt = gjennomsnitt;
    }

    @Override
    public String toString() {
        return "Stand{" +
                "id=" + id +
                ", navn='" + navn + '\'' +
                ", adresse='" + adresse + '\'' +
                ", bransje='" + bransje + '\'' +
                ", beskrivelse='" + beskrivelse;
    }
}
