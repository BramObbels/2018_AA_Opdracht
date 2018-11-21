/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dylan
 */
@Entity
@Table(name = "tickets")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tickets.findAll", query = "SELECT t FROM Tickets t")
    , @NamedQuery(name = "Tickets.findById", query = "SELECT t FROM Tickets t WHERE t.id = :id")
    , @NamedQuery(name = "Tickets.findByValid", query = "SELECT t FROM Tickets t WHERE t.valid = :valid")})
public class Tickets implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int VALID = 1;
    public static final int INVALID = 0;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Column(name = "valid")
    private Integer valid;
    @JoinColumn(name = "accountId", referencedColumnName = "id")
    @ManyToOne
    private Accounts accountId;
    @JoinColumn(name = "playId", referencedColumnName = "id")
    @ManyToOne
    private Plays playId;
    @JoinColumn(name = "seatId", referencedColumnName = "id")
    @ManyToOne
    private Seats seatId;

    public Tickets() {
    }

    public Tickets(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Accounts getAccountId() {
        return accountId;
    }

    public void setAccountId(Accounts accountId) {
        this.accountId = accountId;
    }

    public Plays getPlayId() {
        return playId;
    }

    public void setPlayId(Plays playId) {
        this.playId = playId;
    }

    public Seats getSeatId() {
        return seatId;
    }

    public void setSeatId(Seats seatId) {
        this.seatId = seatId;
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
        if (!(object instanceof Tickets)) {
            return false;
        }
        Tickets other = (Tickets) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Tickets[ id=" + id + " ]";
    }
}
