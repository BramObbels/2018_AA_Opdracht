/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dylan
 */
@Entity
@Table(name = "seats")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Seats.findAll", query = "SELECT s FROM Seats s")
    , @NamedQuery(name = "Seats.findAllSorted", query = "SELECT s FROM Seats s ORDER BY s.rowNumber, s.columnNumber")
    , @NamedQuery(name = "Seats.findById", query = "SELECT s FROM Seats s WHERE s.id = :id")
    , @NamedQuery(name = "Seats.findByRowNumber", query = "SELECT s FROM Seats s WHERE s.rowNumber = :rowNumber")
    , @NamedQuery(name = "Seats.findByColumnNumber", query = "SELECT s FROM Seats s WHERE s.columnNumber = :columnNumber")
    , @NamedQuery(name = "Seats.findByRank", query = "SELECT s FROM Seats s WHERE s.rank = :rank")
    , @NamedQuery(name = "Seats.findByStatus", query = "SELECT s FROM Seats s WHERE s.status = :status")
    , @NamedQuery(name = "Seats.findLastRowNumber", query = "SELECT MAX(s.rowNumber) FROM Seats s")
    , @NamedQuery(name = "Seats.findLastColumnNumber", query = "SELECT MAX(s.columnNumber) FROM Seats s")
    , @NamedQuery(name = "Seats.findLastId", query = "SELECT MAX(s.id) FROM Seats s")})
public class Seats implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int AVAILABLE = 0;
    public static final int RESERVED = 1;
    public static final int OCCUPIED = 2;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "rowNumber")
    private Integer rowNumber;
    @Column(name = "columnNumber")
    private Integer columnNumber;
    @Column(name = "rank")
    private Integer rank;
    @Column(name = "status")
    private Integer status;
    @OneToMany(mappedBy = "seatId")
    private Collection<Tickets> ticketsCollection;
    @JoinColumn(name = "playId", referencedColumnName = "id")
    @ManyToOne
    private Plays playId;

    public Seats() {
    }

    public Seats(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public Integer getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(Integer columnNumber) {
        this.columnNumber = columnNumber;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<Tickets> getTicketsCollection() {
        return ticketsCollection;
    }

    public void setTicketsCollection(Collection<Tickets> ticketsCollection) {
        this.ticketsCollection = ticketsCollection;
    }

    public Plays getPlayId() {
        return playId;
    }

    public void setPlayId(Plays playId) {
        this.playId = playId;
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
        if (!(object instanceof Seats)) {
            return false;
        }
        Seats other = (Seats) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Seats[ id=" + id + " ]";
    }
}
