package pl.sda.vending.model;

import pl.sda.vending.util.Configuration;

import java.util.Optional;
import java.util.Random;

public class VendingMachine {
    private final Configuration configuration;
    private final Long rowsCount;
    private final Long colsCount;
    private final Integer trayWidth;
    private final Tray[][] trays;

    public VendingMachine(Configuration configuration) {
        this.configuration = configuration;
        rowsCount = configuration.getLongProperty("machine.size.rows", 6L);
        colsCount = configuration.getLongProperty("machine.size.cols", 4L);
        trayWidth = configuration.getIntProperty("machine.display.trayWidth", 12);
        if (rowsCount <= 0 || rowsCount > 26) {
            throw new IllegalArgumentException("Row count " + rowsCount + " is invalid");
        }
        if (colsCount <= 0 || colsCount > 9) {
            throw new IllegalArgumentException("Col count " + colsCount + " is invalid");
        }
        // stworz tablice 2-wym.
        // do kazdeo pola tablicy wpisac nowy obiekt tacki
        // obiekt tacki musi miec ustawiony poprawny symbol

        trays = new Tray[rowsCount.intValue()][colsCount.intValue()];

        /*        char letter = 'A';
        for (Tray[] tray : trays) {
            for (int i = 0; i < tray.length; i++) {
//                tray[i] = new Tray(letter + String.valueOf(i + 1));
                String symbol = letter + String.valueOf(i);
                tray[i] = Tray.builder(symbol).build();
            }
            letter++;
        }*/

        Random random = new Random();

        for (int rowNo = 0; rowNo < rowsCount; rowNo++) {
            for (int colNo = 0; colNo < colsCount; colNo++) {
                char letter = (char) ('A' + rowNo);
                int number = colNo + 1;
                String symbol = "" + letter + number;

                Long priceRandom = (long) (random.nextInt(901) + 100);

                Product product;
                Product product1;
                Tray tray;

                if (random.nextInt(10) < 8) {
                    int temp = random.nextInt(10);
                    product = new Product("Product " + symbol);
                    if (temp < 1) {
                        product1 = new Product("Product " + symbol);
                        tray = Tray.builder(symbol).price(priceRandom).product(product).product(product1).build();
                    } else if (temp < 5) {
                        tray = Tray.builder(symbol).price(priceRandom).product(product).build();
                    } else {
                        tray = Tray.builder(symbol).price(priceRandom).build();
                    }
                    trays[rowNo][colNo] = tray;
                }
            }
        }
    }

    /*
    Wylosuj dla każdej tacki cenę z zakresu od 1 do 10 zł (100 do 1000 groszy)
    Dla każdej pozycji automatu prawdopodobieństwo posiadania tacki wynosi 0,8
    Prawdopodobieństwo posiadania przez tackę produktu wynosi 0,5.
    Prawdopodobieństwo, że tacka posiada 2 produkty wynosi 0,1.
    Niech nazwą produktu będzie "Product <Symbol Tacki>"
    */
    private void generateTrayAtPosition(int rowNo, int colNo) {
        Random random = new Random();
        long price = random.nextInt(901) + 100;
        char letter = (char) ('A' + rowNo);
        int number = colNo + 1;
        String symbol = "" + letter + number;

        int productProbability = random.nextInt(10);
        if (productProbability < 1) {
            Tray tray = Tray
                    .builder(symbol)
                    .price(price)
                    .product(new Product("Product " + symbol))
                    .product(new Product("Product " + symbol))
                    .build();
            trays[rowNo][colNo] = tray;
        } else if (productProbability < 5) {
            Tray tray = Tray
                    .builder(symbol)
                    .price(price)
                    .product(new Product("Product " + symbol))
                    .build();
            trays[rowNo][colNo] = tray;
        } else {
            Tray tray = Tray
                    .builder(symbol)
                    .price(price)
                    .build();
            trays[rowNo][colNo] = tray;
        }
    }

    /*inny sposób*/
    private void generateTrayAtPosition_(int rowNo, int colNo) {
        Random random = new Random();
        long price = random.nextInt(901) + 100;
        char letter = (char) ('A' + rowNo);
        int number = colNo + 1;
        String symbol = "" + letter + number;

        Tray.Builder trayBuilder = Tray.builder(symbol).price(price);
        int productProbability = random.nextInt(10);
        if (productProbability < 5) {
            trayBuilder =
                    trayBuilder.product(new Product("Product " + symbol));
        }
        if (productProbability < 1) {
            trayBuilder =
                    trayBuilder.product(new Product("Product " + symbol));
        }
        trays[rowNo][colNo] = trayBuilder.build();
    }

    public Optional<Tray> getTrayAtPosition(int rowNo, int colNo) {
        // zwroc tacke opakowana w Optional
        // a jezel nie istnieje, to pusty optional

        Tray tray;
        try {
            tray = trays[rowNo][colNo];
        } catch (ArrayIndexOutOfBoundsException e) { // tablice to wyrzucaja; kolekcje wyrzucacj index out of bound
//            throw new IndexOutOfBoundsException("nie pasuja indeksy"); // ale to przerwie
            return Optional.empty();
        }
        return tray == null ? Optional.empty() : Optional.of(tray);

/*        try {
            Tray tray = trays[rowNo][colNo];
            return Optional.ofNullable(tray);
        } catch (ArrayIndexOutOfBoundsException e) {
            return Optional.empty();
        }*/
    }

    public Long rowsCount() {
//        return configuration.getLongProperty("machine.size.rows", 6L);
        return rowsCount;
    }

    public Long colsCount() {
//        return configuration.getLongProperty("machine.size.cols", 4L);
        return colsCount;
    }

    public Integer trayWidth() {
        return trayWidth;
    }

/*    public Optional<String> productNameAtPosition(Integer rowNo, Integer colNumber){
        // pobrac z tablicy dwuwymiarowej odpowiednia tacke
        // nazwa pierwszego produktu
        // zwroc optionala
        Tray tray = trays[rowNo][colNumber];
        if (tray != null){
            return tray.firstProductName();
        } else {
            return Optional.empty();
        }
    }*/

    public Optional<String> productNameAtPosition(Integer rowNo, Integer colNo) {
        Optional<Tray> tray = getTrayAtPosition(rowNo, colNo);
        if (tray.isPresent()) {
            return tray.get().firstProductName();
        } else {
            return Optional.empty();
        }
    }

    public Optional<Product> buyProductWithSymbol(String traySymbol) {
        if (traySymbol.length() == 2) {
            return Optional.empty();
        }
        char first = traySymbol.toUpperCase().charAt(0);
        char second = traySymbol.charAt(1);
        int rowNo = first - 'A';
        int colNo = second - '1';

        if (rowNo < 0 || rowNo >= rowsCount || colNo < 0 || colNo >= colsCount) {
            return Optional.empty();
        }
        Tray tray = trays[rowNo][colNo];
        if (tray == null) {
            return Optional.empty();
        } else {
            return tray.buyProduct();
        }
    }
}
