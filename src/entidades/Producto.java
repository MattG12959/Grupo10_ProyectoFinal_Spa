package entidades;

/**
 *
 * @author matia
 */
public class Producto {
    private int idProducto = -1;
    private String nombre;
    private String fabricante;
    private String detalle;
    private double precio;
    private int stock;
    private boolean vegano;
    private boolean sinTacc;

    public Producto() {
    }

    public Producto(String nombre, String fabricante, String detalle, double precio, int stock, boolean vegano, boolean sinTacc) {
        this.nombre = nombre;
        this.fabricante = fabricante;
        this.detalle = detalle;
        this.precio = precio;
        this.stock = stock;
        this.vegano = vegano;
        this.sinTacc = sinTacc;
    }

    public Producto(int idProducto, String nombre, String fabricante, String detalle, double precio, int stock, boolean vegano, boolean sinTacc) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.fabricante = fabricante;
        this.detalle = detalle;
        this.precio = precio;
        this.stock = stock;
        this.vegano = vegano;
        this.sinTacc = sinTacc;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isVegano() {
        return vegano;
    }

    public void setVegano(boolean vegano) {
        this.vegano = vegano;
    }

    public boolean isSinTacc() {
        return sinTacc;
    }

    public void setSinTacc(boolean sinTacc) {
        this.sinTacc = sinTacc;
    }

    @Override
    public String toString() {
        return "Producto{" + "idProducto=" + idProducto + ", nombre=" + nombre + ", fabricante=" + fabricante + ", detalle=" + detalle + ", precio=" + precio + ", stock=" + stock + ", vegano=" + vegano + ", sinTacc=" + sinTacc + '}';
    }    
}
