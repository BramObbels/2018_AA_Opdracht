/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dylan
 */
@Entity
@Table(name = "plays")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Plays.findAll", query = "SELECT p FROM Plays p")
    , @NamedQuery(name = "Plays.findById", query = "SELECT p FROM Plays p WHERE p.id = :id")
    , @NamedQuery(name = "Plays.findByName", query = "SELECT p FROM Plays p WHERE p.name = :name")
    , @NamedQuery(name = "Plays.findByDate", query = "SELECT p FROM Plays p WHERE p.date = :date")
    , @NamedQuery(name = "Plays.findByBasicPrice", query = "SELECT p FROM Plays p WHERE p.basicPrice = :basicPrice")
    , @NamedQuery(name = "Plays.findByRankFee", query = "SELECT p FROM Plays p WHERE p.rankFee = :rankFee")})
public class Plays implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "name")
    private String name;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "basicPrice")
    private Float basicPrice;
    @Column(name = "rankFee")
    private Float rankFee;
    @OneToMany(mappedBy = "playId")
    private Collection<Tickets> ticketsCollection;
    @OneToMany(mappedBy = "playId")
    private Collection<Seats> seatsCollection;

    public Plays() {
    }

    public Plays(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice(Float basicPrice) {
        this.basicPrice = basicPrice;
    }

    public Float getRankFee() {
        return rankFee;
    }

    public void setRankFee(Float rankFee) {
        this.rankFee = rankFee;
    }

    @XmlTransient
    public Collection<Tickets> getTicketsCollection() {
        return ticketsCollection;
    }

    public void setTicketsCollection(Collection<Tickets> ticketsCollection) {
        this.ticketsCollection = ticketsCollection;
    }

    @XmlTransient
    public Collection<Seats> getSeatsCollection() {
        return seatsCollection;
    }

    public void setSeatsCollection(Collection<Seats> seatsCollection) {
        this.seatsCollection = seatsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Plays)) {
            return false;
        }
        Plays other = (Plays) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Plays[ id=" + id + " ]";
    }
    
}
