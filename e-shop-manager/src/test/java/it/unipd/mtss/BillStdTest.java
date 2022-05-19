package it.unipd.mtss;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runners.parameterized.BlockJUnit4ClassRunnerWithParameters;
import org.junit.Assert;
import it.unipd.mtss.model.itemType;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.business.Bill;
import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.BillStd;

public class BillStdTest {

    BillStd bill = new BillStd();
    List<EItem> billItems;
    double billPrice;

    private final EItem cpu1 = new EItem(itemType.Processor, "cpu1", 45.5);
    private final EItem cpu2 = new EItem(itemType.Processor, "cpu2", 70.0);
    private final EItem mb1 = new EItem(itemType.Motherboard, "mb1", 30.0);
    private final EItem mb2 = new EItem(itemType.Motherboard, "mb2", 20.5);
    private final EItem mouse1 = new EItem(itemType.Mouse, "mouse1", 10.0);
    private final EItem mouse2 = new EItem(itemType.Mouse, "mouse2", 7.5);
    private final EItem kb1 = new EItem(itemType.Keyboard, "kb1", 15.0);
    private final EItem kb2 = new EItem(itemType.Keyboard, "kb2", 20.0);


    @Before
    public void Init() {
        billItems = new ArrayList<>();
        billPrice = 0.0;
    }

    @Test
    public void testGetOrderPrice() throws BillException {
        billItems.add(cpu1);
        billItems.add(mb2);
        billItems.add(kb1);

        assertEquals(81.0, bill.getOrderPrice(billItems), 0.0);
    }

    @Test(expected = BillException.class)
    public void testGetOrderPriceWithInvalidPriceValue() throws BillException {
        billItems.add(cpu1);
        billItems.add(mb2);
        billItems.add(new EItem(itemType.Processor, "invalidCpu", -1.0));

        double tot = bill.getOrderPrice(billItems);
    }

    


}
