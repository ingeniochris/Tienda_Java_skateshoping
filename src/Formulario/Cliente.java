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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author jesus
 */
@Entity
@Table(name = "cliente", catalog = "huarache2", schema = "")
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c")
    , @NamedQuery(name = "Cliente.findByCodCli", query = "SELECT c FROM Cliente c WHERE c.codCli = :codCli")
    , @NamedQuery(name = "Cliente.findByNomCli", query = "SELECT c FROM Cliente c WHERE c.nomCli = :nomCli")
    , @NamedQuery(name = "Cliente.findByApeCli", query = "SELECT c FROM Cliente c WHERE c.apeCli = :apeCli")
    , @NamedQuery(name = "Cliente.findByApeMaCli", query = "SELECT c FROM Cliente c WHERE c.apeMaCli = :apeMaCli")
    , @NamedQuery(name = "Cliente.findBySexoCli", query = "SELECT c FROM Cliente c WHERE c.sexoCli = :sexoCli")
    , @NamedQuery(name = "Cliente.findByRfcCli", query = "SELECT c FROM Cliente c WHERE c.rfcCli = :rfcCli")
    , @NamedQuery(name = "Cliente.findByTelCli", query = "SELECT c FROM Cliente c WHERE c.telCli = :telCli")
    , @NamedQuery(name = "Cliente.findByEmailCli", query = "SELECT c FROM Cliente c WHERE c.emailCli = :emailCli")})
public class Cliente implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_cli")
    private String codCli;
    @Basic(optional = false)
    @Column(name = "nom_cli")
    private String nomCli;
    @Basic(optional = false)
    @Column(name = "ape_cli")
    private String apeCli;
    @Basic(optional = false)
    @Column(name = "ape_ma_cli")
    private String apeMaCli;
    @Basic(optional = false)
    @Column(name = "sexo_cli")
    private String sexoCli;
    @Basic(optional = false)
    @Column(name = "rfc_cli")
    private String rfcCli;
    @Basic(optional = false)
    @Column(name = "tel_cli")
    private String telCli;
    @Basic(optional = false)
    @Column(name = "email_cli")
    private String emailCli;

    public Cliente() {
    }

    public Cliente(String codCli) {
        this.codCli = codCli;
    }

    public Cliente(String codCli, String nomCli, String apeCli, String apeMaCli, String sexoCli, String rfcCli, String telCli, String emailCli) {
        this.codCli = codCli;
        this.nomCli = nomCli;
        this.apeCli = apeCli;
        this.apeMaCli = apeMaCli;
        this.sexoCli = sexoCli;
        this.rfcCli = rfcCli;
        this.telCli = telCli;
        this.emailCli = emailCli;
    }

    public String getCodCli() {
        return codCli;
    }

    public void setCodCli(String codCli) {
        String oldCodCli = this.codCli;
        this.codCli = codCli;
        changeSupport.firePropertyChange("codCli", oldCodCli, codCli);
    }

    public String getNomCli() {
        return nomCli;
    }

    public void setNomCli(String nomCli) {
        String oldNomCli = this.nomCli;
        this.nomCli = nomCli;
        changeSupport.firePropertyChange("nomCli", oldNomCli, nomCli);
    }

    public String getApeCli() {
        return apeCli;
    }

    public void setApeCli(String apeCli) {
        String oldApeCli = this.apeCli;
        this.apeCli = apeCli;
        changeSupport.firePropertyChange("apeCli", oldApeCli, apeCli);
    }

    public String getApeMaCli() {
        return apeMaCli;
    }

    public void setApeMaCli(String apeMaCli) {
        String oldApeMaCli = this.apeMaCli;
        this.apeMaCli = apeMaCli;
        changeSupport.firePropertyChange("apeMaCli", oldApeMaCli, apeMaCli);
    }

    public String getSexoCli() {
        return sexoCli;
    }

    public void setSexoCli(String sexoCli) {
        String oldSexoCli = this.sexoCli;
        this.sexoCli = sexoCli;
        changeSupport.firePropertyChange("sexoCli", oldSexoCli, sexoCli);
    }

    public String getRfcCli() {
        return rfcCli;
    }

    public void setRfcCli(String rfcCli) {
        String oldRfcCli = this.rfcCli;
        this.rfcCli = rfcCli;
        changeSupport.firePropertyChange("rfcCli", oldRfcCli, rfcCli);
    }

    public String getTelCli() {
        return telCli;
    }

    public void setTelCli(String telCli) {
        String oldTelCli = this.telCli;
        this.telCli = telCli;
        changeSupport.firePropertyChange("telCli", oldTelCli, telCli);
    }

    public String getEmailCli() {
        return emailCli;
    }

    public void setEmailCli(String emailCli) {
        String oldEmailCli = this.emailCli;
        this.emailCli = emailCli;
        changeSupport.firePropertyChange("emailCli", oldEmailCli, emailCli);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCli != null ? codCli.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.codCli == null && other.codCli != null) || (this.codCli != null && !this.codCli.equals(other.codCli))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Formulario.Cliente[ codCli=" + codCli + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
