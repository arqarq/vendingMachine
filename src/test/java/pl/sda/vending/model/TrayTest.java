package pl.sda.vending.model;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TrayTest {
    @Test
    public void shouldBeAbleToBuyLastAvailableProduct() {
        // Given
        Product definedProduct = new Product("P1");
        Tray tray = Tray.builder("A1")
                .product(definedProduct)
                .build();
        // When
        Optional<Product> boughtProduct = tray.buyProduct();
        // Then
        assertTrue(boughtProduct.isPresent());
        assertEquals(definedProduct, boughtProduct.get());
    }
    // dalsze testy napisac


}
