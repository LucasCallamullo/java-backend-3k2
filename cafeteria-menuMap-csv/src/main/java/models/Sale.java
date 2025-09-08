package models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Sale {
    // Codigo,NombreProducto,TipoProducto,CantidadVendida,PrecioVenta,Descuento
    private String code;
    private String name;
    private Category category;
    private int quantitySold;
    private double price;
    private double discount;

    public double calcularPrecioVenta() {
        return this.price - (this.price * this.discount);
    }
}
