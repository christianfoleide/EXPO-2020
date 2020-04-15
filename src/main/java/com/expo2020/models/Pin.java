package com.expo2020.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(schema = "expo", name = "pin")
public class Pin implements Serializable {

    private static final long serialVersionUID = -2343243243242432341L;

    @Id
    @Column
    private Long kode;

    public Long getKode() {
        return kode;
    }

    public void setKode(Long kode) {
        this.kode = kode;
    }
}
