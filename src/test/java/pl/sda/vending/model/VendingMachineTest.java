package pl.sda.vending.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.sda.vending.util.Configuration;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
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
}
