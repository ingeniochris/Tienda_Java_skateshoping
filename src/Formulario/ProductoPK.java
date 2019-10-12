/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formulario;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author jesus
 */
@Embeddable
public class ProductoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "cod_pro")
    private String codPro;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;

    public ProductoPK() {
    }

    public ProductoPK(String codPro, String descripcion) {
        this.codPro = codPro;
        this.descripcion = descripcion;
    }

    public String getCodPro() {
        return codPro;
    }

    public void setCodPro(String codPro) {
        this.codPro = codPro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codPro != null ? codPro.hashCode() : 0);
        hash += (descripcion != null ? descripcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoPK)) {
            return false;
        }
        ProductoPK other = (ProductoPK) object;
        if ((this.codPro == null && other.codPro != null) || (this.codPro != null && !this.codPro.equals(other.codPro))) {
            return false;
        }
        if ((this.descripcion == null && other.descripcion != null) || (this.descripcion != null && !this.descripcion.equals(other.descripcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Formulario.ProductoPK[ codPro=" + codPro + ", descripcion=" + descripcion + " ]";
    }
    
}
