package pl.sda.vending.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConfigurationTest {
    private Configuration testedConfig;

    @Before
    public void init() {
        testedConfig = new Configuration();
    }

    @Test
    public void shouldReturnDefaultStringValueWhenPropertyIsUnknown() {
        // Given
        String unknownPropertyName = "sdfdsfsdfsfsfsf";
        String expectedDefault = "javaIsAwesome";
        // When
        String propertyValue = testedConfig.getStringProperty(unknownPropertyName, expectedDefault);
        // Then
        assertEquals(expectedDefault, propertyValue);
    }

    @Test
    public void shouldReturnDefaultNumericalValueWhenPropertyIsUnknown() {
        // Given
        String unknownPropertyValue = "sdfdsfsdfsfsfsf";
        Long expectedLongDefault = 6L;
        // When
        Long propertyValue = testedConfig.getLongProperty(unknownPropertyValue, expectedLongDefault);
        // Then
        assertEquals(expectedLongDefault, propertyValue);
    }

    @Test
    public void shouldReturnExistingStringValue() {
        // Given
        String propertyName = "test.property.string";
        String expectedStringValue = "qwerty";
        // When
        String propertyValue = testedConfig.getStringProperty(propertyName, "N/A");
        // Then
        assertEquals(expectedStringValue, propertyValue);
    }

    @Test
    public void shouldReturnKnownLongValue() {
        // Given
        String propertyName = "test.property.long";
        Long expectedLongValue = 7L;
        // When
        Long propertyValue = testedConfig.getLongProperty(propertyName, 12L);
        // Then
        assertEquals(expectedLongValue, propertyValue);
    }

    @Test(expected = Exception.class)
    public void shouldGetException() throws Exception {
        // Given
        throw new Exception("wyj");
        // When
        // Then
    }
}
