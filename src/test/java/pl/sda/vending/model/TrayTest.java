package pl.sda.vending.model;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

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
        assertEquals("P1", boughtProduct.get().getName());
    }

    @Test
    public void shouldNotBeAbleToBuyLastAvailableProducts2Times() {
        // Given
        Product definedProduct = new Product("P1");
        Tray tray = Tray.builder("A1")
                .product(definedProduct)
                .build();
        // When
        Optional<Product> boughtProduct = tray.buyProduct();
        Optional<Product> boughtProduct2 = tray.buyProduct();
        // Then
        assertTrue(boughtProduct.isPresent());
        assertEquals(definedProduct, boughtProduct.get());
        assertEquals("P1", boughtProduct.get().getName());
        assertFalse(boughtProduct2.isPresent());
    }

    @Test
    public void shouldBeAbleToBuy2LastAvailableProducts() {
        // Given
        Product definedProduct = new Product("P1");
        Product definedProduct2 = new Product("P2");
        Tray tray = Tray.builder("A1")
                .product(definedProduct)
                .product(definedProduct2)
                .build();
        // When
        Optional<Product> boughtProduct = tray.buyProduct();
        Optional<Product> boughtProduct2 = tray.buyProduct();
        // Then
        assertTrue(boughtProduct.isPresent());
        assertTrue(boughtProduct2.isPresent());
        assertEquals(definedProduct, boughtProduct.get());
        assertEquals(definedProduct2, boughtProduct2.get());
        assertEquals("P1", boughtProduct.get().getName());
        assertEquals("P2", boughtProduct2.get().getName());
    }

    @Test
    public void shouldGiveEmptyOptionalIfEmptyTrayBought() {
        // Given
        Tray tray = Tray.builder("A1")
                .build();
        // When
        Optional<Product> boughtProduct = tray.buyProduct();
        // Then
        assertFalse(boughtProduct.isPresent());
    }

    @Test
    public void shouldNotBeAbleToOverloadTray() {
        // Given
        Tray testedTray = Tray.builder("A1").build();
        Product product = new Product("produkt testowy");
        for (int productNumber = 0; productNumber < Tray.MAX_SIZE; productNumber++) {
            testedTray.addProduct(product);
        }
        Product surplusProduct = new Product("Mleko");
        // When
        boolean success = testedTray.addProduct(surplusProduct);
        // Then
        assertFalse(success);
    }
}
