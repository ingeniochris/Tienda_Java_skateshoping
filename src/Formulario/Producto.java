/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formulario;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author jesus
 */
@Entity
@Table(name = "producto", catalog = "huarache2", schema = "")
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p")
    , @NamedQuery(name = "Producto.findByCodPro", query = "SELECT p FROM Producto p WHERE p.productoPK.codPro = :codPro")
    , @NamedQuery(name = "Producto.findByDescripcion", query = "SELECT p FROM Producto p WHERE p.productoPK.descripcion = :descripcion")
    , @NamedQuery(name = "Producto.findByPrecio", query = "SELECT p FROM Producto p WHERE p.precio = :precio")
    , @NamedQuery(name = "Producto.findByStock", query = "SELECT p FROM Producto p WHERE p.stock = :stock")
    , @NamedQuery(name = "Producto.findById_tipo", query = "SELECT p FROM Producto p WHERE p.id_tipo = :id_tipo")})
public class Producto implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProductoPK productoPK;
    @Basic(optional = false)
    @Column(name = "precio")
    private String precio;
    @Basic(optional = false)
    @Column(name = "stock")
    private String stock;
    @Basic(optional = false)
    @Column(name = "id_tipo")
    private int id_tipo;

    public Producto() {
    }

    public Producto(ProductoPK productoPK) {
        this.productoPK = productoPK;
    }

    public Producto(ProductoPK productoPK, String precio, String stock, int id_tipo) {
        this.productoPK = productoPK;
        this.precio = precio;
        this.stock = stock;
        this.id_tipo = id_tipo;
    }

    public Producto(String codPro, String descripcion) {
        this.productoPK = new ProductoPK(codPro, descripcion);
    }

    public ProductoPK getProductoPK() {
        return productoPK;
    }

    public void setProductoPK(ProductoPK productoPK) {
        this.productoPK = productoPK;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        String oldPrecio = this.precio;
        this.precio = precio;
        changeSupport.firePropertyChange("precio", oldPrecio, precio);
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        String oldStock = this.stock;
        this.stock = stock;
        changeSupport.firePropertyChange("stock", oldStock, stock);
    }

    public int getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(int id_tipo) {
        int oldId_tipo = this.id_tipo;
        this.id_tipo = id_tipo;
        changeSupport.firePropertyChange("id_tipo", oldId_tipo, id_tipo);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productoPK != null ? productoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.productoPK == null && other.productoPK != null) || (this.productoPK != null && !this.productoPK.equals(other.productoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Formulario.Producto[ productoPK=" + productoPK + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
