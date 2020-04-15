package com.expo2020.models;

import javax.persistence.*;
import java.io.Serializable;

/***
 * @author christian foleide
 *  Klasse som definerer en stemme.
 *  TODO: Legge til kommentar
 */

@Entity
@Table(name = "stemme", schema = "expo")
public class Stemme implements Serializable {

    private static final long serialVersionUID = -2343243243242432341L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stemmeid")
    private Long id;

    /***
     * @personid id tilhørende en stemme
     */

    @Column(name = "personid")
    private String personid;

    /***
     * @standid hvilken stand denne stemmen tilhører
     */
    @Column(name = "standid")
    private Long standid;

    /***
     * @stemmeverdi hvor mye denne stemmen er vektet
     */
    @Column(name = "stemmeverdi")
    private int stemmeverdi;

    @Column(name = "kommentar")
    private String kommentar;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
                    name = "standid",
                    nullable = false,
                    insertable = false,
                    updatable = false)
    private Stand stand;

    public Stemme() {}

    public Stemme(String personid, Long standid, int stemmeverdi, String kommentar) {
        this.personid = personid;
        this.standid = standid;
        this.stemmeverdi = stemmeverdi;
        this.kommentar = kommentar;
    }

    /**
     * Laget for å unngå errors i testklassen :P
     * @param s
     * @param valueOf
     * @param i
     */
    public Stemme(String s, Long valueOf, int i) {
    }

    @Override
    public String toString() {
        return "Stemme{" +
                "id=" + id +
                ", personid='" + personid + '\'' +
                ", standid=" + standid +
                ", stemmeverdi=" + stemmeverdi +
                ", kommentar='" + kommentar +
                '}';
    }

    public void setPersonid(String personid) {
        this.personid = personid;
    }

    public void setStandid(Long standid) {
        this.standid = standid;
    }

    public void setStemmeverdi(int stemmeverdi) {
        this.stemmeverdi = stemmeverdi;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPersonid() {
        return personid;
    }

    public Long getStandid() {
        return standid;
    }

    public int getStemmeverdi() {
        return stemmeverdi;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }
}
