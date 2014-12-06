/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * @author URRUTIA
 */
@Entity
@Table(name = "detallev")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Detallev.findAll", query = "SELECT d FROM Detallev d"),
    @NamedQuery(name = "Detallev.buscarventa", query = "SELECT d FROM Detallev d WHERE d.ventanVenta = :ventanVenta"),
    @NamedQuery(name = "Detallev.findByNdetalle", query = "SELECT d FROM Detallev d WHERE d.ndetalle = :ndetalle"),
    @NamedQuery(name = "Detallev.findByPrecioP", query = "SELECT d FROM Detallev d WHERE d.precioP = :precioP"),
    @NamedQuery(name = "Detallev.findByCantidadP", query = "SELECT d FROM Detallev d WHERE d.cantidadP = :cantidadP")})
public class Detallev implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ndetalle")
    private Integer ndetalle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "precioP")
    private double precioP;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidadP")
    private int cantidadP;
    @JoinColumn(name = "Venta_nVenta", referencedColumnName = "nVenta")
    @ManyToOne(optional = false)
    private Venta ventanVenta;
    @JoinColumn(name = "Producto_codigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Producto productocodigo;

    public Detallev() {
    }

    public Detallev(Integer ndetalle) {
        this.ndetalle = ndetalle;
    }

    public Detallev(Integer ndetalle, double precioP, int cantidadP) {
        this.ndetalle = ndetalle;
        this.precioP = precioP;
        this.cantidadP = cantidadP;
    }

    public Integer getNdetalle() {
        return ndetalle;
    }

    public void setNdetalle(Integer ndetalle) {
        this.ndetalle = ndetalle;
    }

    public double getPrecioP() {
        return precioP;
    }

    public void setPrecioP(double precioP) {
        this.precioP = precioP;
    }

    public int getCantidadP() {
        return cantidadP;
    }

    public void setCantidadP(int cantidadP) {
        this.cantidadP = cantidadP;
    }

    public Venta getVentanVenta() {
        return ventanVenta;
    }

    public void setVentanVenta(Venta ventanVenta) {
        this.ventanVenta = ventanVenta;
    }

    public Producto getProductocodigo() {
        return productocodigo;
    }

    public void setProductocodigo(Producto productocodigo) {
        this.productocodigo = productocodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ndetalle != null ? ndetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Detallev)) {
            return false;
        }
        Detallev other = (Detallev) object;
        if ((this.ndetalle == null && other.ndetalle != null) || (this.ndetalle != null && !this.ndetalle.equals(other.ndetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Detallev[ ndetalle=" + ndetalle + " ]";
    }
    
}
