package it.unipd.mtss.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class EItemTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test(timeout = 500)
    public void testConstructorWithValidValues(){
        EItem item = new EItem(itemType.Mouse, "itemName", 5.00);

        assertEquals(item.getPrice(), 5.00, 0.00);
        assertEquals(item.getItemType(), itemType.Mouse);
        assertEquals(item.getName(), "itemName");
    }

    @Test(timeout = 500)
    public void testContructorExceptionNegativePrice() throws IllegalArgumentException{
        
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("price of an item must be a positive number");

        new EItem(itemType.Keyboard,"name", -1);
    }

    @Test(timeout = 500)
    public void testContructorExceptionNullName() throws IllegalArgumentException{
        
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("name of an item cannot be null");

        new EItem(itemType.Keyboard, null, 1.00);
    }

    @Test(timeout = 500)
    public void testContructorExceptionNullArticle() throws IllegalArgumentException{
        
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("article type of an item cannot be null");

        new EItem(null, "name", 1.00);
    }

    @Test(timeout = 500)
    public void testGetItemType() {
        EItem item = new EItem(itemType.Mouse, "itemName", 5.00);

        assertEquals(item.getItemType(), itemType.Mouse);
    }

    @Test(timeout = 500)
    public void testGetName() {
        EItem item = new EItem(itemType.Mouse, "itemName", 5.00);

        assertEquals(item.getName(), "itemName");
    }

    @Test(timeout = 500)
    public void testGetPrice() {
        EItem item = new EItem(itemType.Keyboard, "itemName", 10.00);

        assertEquals(10.00, item.getPrice(), 0.00);
    }

}
