package pl.sda.vending.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.sda.vending.util.Configuration;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class VendingMachineTest {
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenZeroRowsConfigured() {
        // Given
        Configuration config = mock(Configuration.class);
        doReturn(0L).when(config).getLongProperty(eq("machine.size.rows"), anyLong());
        doReturn(4L).when(config).getLongProperty(eq("machine.size.cols"), anyLong());
        // When
        new VendingMachine(config);
        // Then
        fail("Exception should be raised");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenZeroColsConfigured() {
        // Given
        Configuration config = mock(Configuration.class);
        doReturn(6L).when(config).getLongProperty(eq("machine.size.rows"), anyLong());
        doReturn(0L).when(config).getLongProperty(eq("machine.size.cols"), anyLong());
        // When
        new VendingMachine(config);
        // Then
        fail("Exception should be raised");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenAbove26RowsConfigured() {
        // Given
        Configuration config = mock(Configuration.class);
        doReturn(27L).when(config).getLongProperty(eq("machine.size.rows"), anyLong());
        doReturn(4L).when(config).getLongProperty(eq("machine.size.cols"), anyLong());
        // When
        new VendingMachine(config);
        // Then
        fail("Exception should be raised");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenAbove9ColsConfigured() {
        // Given
        Configuration config = mock(Configuration.class);
        doReturn(6L).when(config).getLongProperty(eq("machine.size.rows"), anyLong());
        doReturn(10L).when(config).getLongProperty(eq("machine.size.cols"), anyLong());
        // When
        new VendingMachine(config);
        // Then
        fail("Exception should be raised");
    }

    @Test
    @Parameters({"6, 4", "26, 9"})
    public void shouldGiveNoException(Long rows, Long cols) {
        // Given
        Configuration config = mock(Configuration.class);
//        doReturn(6L).when(config).getLongProperty(eq("machine.size.rows"), anyLong());
        when(config.getLongProperty(eq("machine.size.rows"), anyLong())).thenReturn(rows);
        doReturn(cols).when(config).getLongProperty(eq("machine.size.cols"), anyLong());
        // When
        new VendingMachine(config);
        // Then
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({"0, 4", "6,0", "27|4", "6| 10"})
    public void shouldDoAllTestsWithParameters(Long rows, Long cols) {
        // Given
        Configuration config = mock(Configuration.class);
        doReturn(rows).when(config).getLongProperty(eq("machine.size.rows"), anyLong());
        doReturn(cols).when(config).getLongProperty(eq("machine.size.cols"), anyLong());
        // When
        new VendingMachine(config);
        // Then
        fail("Exception should be raised");
    }

    @Test
    public void shouldBeAbleToAddTrayToEmptySpot() {
        // Given
        Tray tray = Tray.builder("A2").build();
        Configuration config = mock(Configuration.class);
        doReturn(6L).when(config).getLongProperty(eq("machine.size.rows"), anyLong());
        doReturn(4L).when(config).getLongProperty(eq("machine.size.cols"), anyLong());
        VendingMachine testedMachine = new VendingMachine(config);
        // When
        boolean placed = testedMachine.placeTray(tray);
        // Then
        assertTrue(placed);
        assertEquals(tray, testedMachine.getTrayAtPosition(0, 1).get());
    }

    @Test
    public void shouldNotBeAbleToAddTrayToTakenSpot() {
        // Given
        Tray tray = Tray.builder("A2").build();
        Tray secondTray = Tray.builder("A2").build();
        Configuration config = mock(Configuration.class);
        doReturn(6L).when(config).getLongProperty(eq("machine.size.rows"), anyLong());
        doReturn(4L).when(config).getLongProperty(eq("machine.size.cols"), anyLong());
        VendingMachine testedMachine = new VendingMachine(config);
        // When
        boolean firstTrayPlacementResult = testedMachine.placeTray(tray);
        boolean secondTrayPlacementResult = testedMachine.placeTray(secondTray);
        // Then
        assertTrue(firstTrayPlacementResult);
        assertFalse(secondTrayPlacementResult);
        assertEquals(tray, testedMachine.getTrayAtPosition(0, 1).get());
    }

    @Test
    public void shouldNotBeAbleToAddTrayToNoExistingPosition() {
        // Given
        Tray tray = Tray.builder("$$").build();
        Configuration config = mock(Configuration.class);
        doReturn(6L).when(config).getLongProperty(eq("machine.size.rows"), anyLong());
        doReturn(4L).when(config).getLongProperty(eq("machine.size.cols"), anyLong());
        VendingMachine testedMachine = new VendingMachine(config);
        // When
        boolean placed = testedMachine.placeTray(tray);
        // Then
        assertFalse(placed);
    }

    @Test
    public void shouldReturnEmptyOptionalIfTrayCouldNotBeRemoved() {
        // Given
        String traySymbol = "A1";
        Configuration mockedConfig = getMockedConfiguration();
        VendingMachine testedMachine = new VendingMachine(mockedConfig);
        // When
        Optional<Tray> removedTray = testedMachine.removeTrayWithSymbol(traySymbol);
        // Then
        assertFalse(removedTray.isPresent());
    }

    @Test
    public void shouldBeAbleToRemoveTray() {
        // Given
        String traySymbol = "B1";
        Configuration mockedConfig = getMockedConfiguration();
        VendingMachine machine = new VendingMachine(mockedConfig);
        Tray tray = Tray.builder(traySymbol).build();
        machine.placeTray(tray);
        // When
        Optional<Tray> removedTray = machine.removeTrayWithSymbol(traySymbol);
        // Then
        assertTrue(removedTray.isPresent());
        assertEquals(tray, removedTray.get());
    }

    @Test
    public void removedTrayShouldNotBeAvailable() {
        // Given
        String traySymbol = "C4";
        VendingMachine machine = new VendingMachine(getMockedConfiguration());
        Tray tray = Tray.builder(traySymbol).build();
        machine.placeTray(tray);
        // When
        machine.removeTrayWithSymbol(traySymbol);
        Optional<Tray> obtainedTray = machine.getTrayAtPosition(2, 4);
        // Then
        assertFalse(obtainedTray.isPresent());
        /*
        strworzyc automat z jedna tacka
        usunac tacke
        SPRAWDZAMY: czy po usunieciu tacki, metoda
        getTrayAtPosition zwraca pusta wartosc
         */
    }

    @Test
    public void shouldBeAbleToRemovedOnlyAvailablaProductsFromTray() {
        // Given
        String traySymbol = "C4";
        Product product = new Product("Mars");
        VendingMachine machine = new VendingMachine(getMockedConfiguration());
        Tray tray = Tray.builder(traySymbol).product(product).build();
        machine.placeTray(tray);
        // When
        Integer howManyRemoved = machine.removeProductFromTray("C4", 2);
        // Then
        assertEquals((Integer) 1, howManyRemoved);
    }

    private Configuration getMockedConfiguration() {
        Configuration config = mock(Configuration.class);
        doReturn(6L).when(config).getLongProperty(eq("machine.size.rows"), anyLong());
        doReturn(4L).when(config).getLongProperty(eq("machine.size.cols"), anyLong());
        return config;
    }
}
