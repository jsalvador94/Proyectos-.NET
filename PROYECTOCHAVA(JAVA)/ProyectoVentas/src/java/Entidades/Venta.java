/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author URRUTIA
 */
@Entity
@Table(name = "venta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Venta.findAll", query = "SELECT v FROM Venta v"),
    @NamedQuery(name = "Venta.buscarventa", query = "SELECT v FROM Venta v where v.nVenta LIKE :nVenta"),
    @NamedQuery(name = "Venta.findByNVenta", query = "SELECT v FROM Venta v WHERE v.nVenta = :nVenta"),
    @NamedQuery(name = "Venta.findByFechaVenta", query = "SELECT v FROM Venta v WHERE v.fechaVenta = :fechaVenta"),
    @NamedQuery(name = "Venta.findByNombreCliente", query = "SELECT v FROM Venta v WHERE v.nombreCliente = :nombreCliente")})
public class Venta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "nVenta")
    private Integer nVenta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaVenta")
    @Temporal(TemporalType.DATE)
    private Date fechaVenta;
    @Size(max = 45)
    @Column(name = "nombreCliente")
    private String nombreCliente;
    @JoinColumn(name = "Usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ventanVenta")
    private List<Detallev> detallevList;

    public Venta() {
    }

    public Venta(Integer nVenta) {
        this.nVenta = nVenta;
    }

    public Venta(Integer nVenta, Date fechaVenta) {
        this.nVenta = nVenta;
        this.fechaVenta = fechaVenta;
    }

    public Integer getNVenta() {
        return nVenta;
    }

    public void setNVenta(Integer nVenta) {
        this.nVenta = nVenta;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Usuario getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(Usuario usuarioid) {
        this.usuarioid = usuarioid;
    }

    @XmlTransient
    public List<Detallev> getDetallevList() {
        return detallevList;
    }

    public void setDetallevList(List<Detallev> detallevList) {
        this.detallevList = detallevList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nVenta != null ? nVenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Venta)) {
            return false;
        }
        Venta other = (Venta) object;
        if ((this.nVenta == null && other.nVenta != null) || (this.nVenta != null && !this.nVenta.equals(other.nVenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + nVenta + "";
    }
    
}
