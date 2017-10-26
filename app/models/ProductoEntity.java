package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.ebean.*;
import play.data.validation.Constraints.*;

import javax.persistence.*;

@Entity
@Table(name = "producto")
public class ProductoEntity extends Model implements Validatable<String> {

    @Id
    private int cId;
    private String dNombre;
    private String dUrlFoto;
    private String fLimite;
    private double precio;
    private String ingredientes;
    @JsonBackReference
    private SucursalEntity sucursal;
    @JsonBackReference
    private CategoriaEntity categoria;
    public static final Finder<Integer, ProductoEntity> find = new Finder<>(ProductoEntity.class);

    @Column(name = "c_id")
    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    @Column(name = "d_nombre")
    public String getdNombre() {
        return dNombre;
    }

    public void setdNombre(String dNombre) {
        this.dNombre = dNombre;
    }

    @Column(name = "d_url_foto")
    public String getdUrlFoto() {
        return dUrlFoto;
    }

    public void setdUrlFoto(String dUrlFoto) {
        this.dUrlFoto = dUrlFoto;
    }

    @Column(name = "f_limite")
    public String getfLimite() {
        return fLimite;
    }

    public void setfLimite(String fLimite) {
        this.fLimite = fLimite;
    }

    @Column(name = "n_precio")
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Column(name = "a_ingredientes")
    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    @ManyToOne(optional = false) @JoinColumn(name = "c_id_sucursal")
    public SucursalEntity getSucursal() {
        return sucursal;
    }

    public void setSucursal(SucursalEntity sucursal) {
        this.sucursal = sucursal;
    }

    @ManyToOne(optional = false) @JoinColumn(name = "c_id_categoria")
    public CategoriaEntity getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaEntity categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "ProductoEntity{" +
                "cId=" + cId +
                ", dNombre='" + dNombre + '\'' +
                ", dUrlFoto='" + dUrlFoto + '\'' +
                ", fLimite='" + fLimite + '\'' +
                ", sucursal=" + sucursal +
                ", categoria=" + categoria +
                '}';
    }

    @Override
    public String validate() {
        if(dNombre == null || dNombre.trim().equalsIgnoreCase("")){
            return "Nombre no puede estar vacio";
        }else if(fLimite == null || fLimite.trim().equalsIgnoreCase("")){
            return "Fecha no puede estar vacia";
        }else if(precio == 0 || precio < 0){
            return "Precio incorrecto";
        }else if(ingredientes == null || ingredientes.trim().equalsIgnoreCase("")){
            return "Debe haber al menos 1 ingrediente";
        }
        return null;
    }
}
