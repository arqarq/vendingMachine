package pl.sda.vending.model;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

public class Tray implements Serializable {
    public static final long serialVersionUID = 1L;
    static final int MAX_SIZE = 10;
    private final String symbol;
    private final Queue<Product> products;
    private Long price;

    private Tray(Builder builder) {
        symbol = builder.symbol;
        price = builder.price;
        products = builder.products;
    }

    public static Builder builder(String symbol) {
        return new Builder(symbol);
    }

    String getSymbol() {
        return symbol;
    }

    public Long getPrice() {
        return price;
    }

    void setPrice(Long price) {
        this.price = price;
    }

    Optional<String> firstProductName() {
//        if (products.peek() != null) {
//              Product firstProduct = products.peek();
//              String name = firstProduct.getName();
//              return Optional.ofNullable(name);
//        } else {
//          return Optional.empty();
//        }
        return Optional.ofNullable(products.peek()).map(Product::getName);
    }

    Optional<Product> buyProduct() {
        return Optional.ofNullable(products.poll());
    }

    public boolean addProduct(Product product) {
        if (products.size() < MAX_SIZE) {
            return products.add(product);
        } else {
            return false;
        }
    }

    Integer removeProductsFromThisTray(Integer howManyToRemove) {
        int quantityOfProductsInTray;

        if ((quantityOfProductsInTray = products.size()) == 0) {
            return 0;
        }
        if (quantityOfProductsInTray >= howManyToRemove) {
            for (int i = 0; i < howManyToRemove; i++) {
                products.poll();
            }
            return howManyToRemove;
        }
        for (int i = 0; i < quantityOfProductsInTray; i++) {
            products.poll();
        }
        return howManyToRemove - quantityOfProductsInTray;
    }

    TraySnapshot snapshot() {
        return new TraySnapshot(symbol, price, firstProductName().orElse("--"));
    }

    public static class Builder {
        private final String symbol;
        private final Queue<Product> products;
        private Long price;

        private Builder(String symbol) {
            this.symbol = symbol;
            products = new ArrayDeque<>();
        }

        public Builder price(Long price) {
            this.price = price;
            return this;
        }

        public Builder product(Product product) {
            products.add(product);
            return this;
        }

        public Tray build() {
            if (price == null || price < 0) {
                price = 0L;
            }
            return new Tray(this);
        }
    }
}
