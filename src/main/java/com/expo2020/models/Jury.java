package com.expo2020.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(schema = "expo", name = "jury")
public class Jury implements Serializable {

    private static final long serialVersionUID = -2343243243242432341L;

    @Id
    @Column
    private Long cookievalue;

    public Jury() {}

    public Long getCookievalue() {
        return cookievalue;
    }

    public void setCookievalue(Long cookievalue) {
        this.cookievalue = cookievalue;
    }

}
