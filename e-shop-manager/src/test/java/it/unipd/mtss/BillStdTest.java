////////////////////////////////////////////////////////////////////
// [GIOVANNI] [MORETTI] [1217655]
// [GIOVANNI] [COLANGELO] [2008070]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

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
    private final EItem cpu3 = new EItem(itemType.Processor, "cpu3", 32.5);
    private final EItem cpu4 = new EItem(itemType.Processor, "cpu4", 56.99);
    private final EItem cpu5 = new EItem(itemType.Processor, "cpu5", 23.5);
    private final EItem cpu6 = new EItem(itemType.Processor, "cpu6", 47.70);
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

    @Test(timeout = 500)
    public void testGetOrderPriceWithValidValues() throws BillException {
        billItems.add(cpu1);
        billItems.add(mb2);
        billItems.add(kb1);

        assertEquals(81.0, bill.getOrderPrice(billItems), 0.0);
    }

    @Test(expected = BillException.class, timeout = 500)
    public void testGetOrderPriceWithInvalidPriceValue() throws BillException {
        billItems.add(cpu1);
        billItems.add(mb2);
        billItems.add(new EItem(itemType.Processor, "invalidCpu", -1.0));

        double tot = bill.getOrderPrice(billItems);
    }

    @Test(timeout = 500)
    public void testGetProcesorNumber(){
        billItems.add(cpu1);
        billItems.add(mb1);
        billItems.add(kb1);

        assertEquals(1, bill.getProcessorNumber(billItems));
    }

    @Test(timeout = 500)
    public void testGetCheaperProcessorOnlyProcessors(){
        billItems.add(cpu1);
        billItems.add(cpu2);
        billItems.add(cpu3);

        assertEquals(32.5, bill.getCheaperProcessor(billItems), 0.0);
    }

    @Test(timeout = 500)
    public void testGetCheaperProcessorMixedOrder(){
        billItems.add(cpu1);
        billItems.add(cpu4);
        billItems.add(mb1);
        billItems.add(mouse1);
        billItems.add(kb1);

        assertEquals(45.5, bill.getCheaperProcessor(billItems), 0.0);
    }

    @Test(timeout = 500)
    public void testOrderPriceWithProcessorDiscountFiveProcessor() throws BillException{
        billItems.add(mouse2);
        billItems.add(cpu1);
        billItems.add(cpu2);
        billItems.add(cpu3);
        billItems.add(cpu4);
        billItems.add(cpu5);
        billItems.add(mouse1);
        billItems.add(kb1);

        assertEquals(260.99, bill.getOrderPrice(billItems),0.0);
    }

    @Test(timeout = 500)
    public void testOrderPriceWithProcessorDiscountSixProcessor() throws BillException{
        billItems.add(mouse2);
        billItems.add(cpu1);
        billItems.add(cpu2);
        billItems.add(cpu3);
        billItems.add(cpu4);
        billItems.add(cpu5);
        billItems.add(cpu6);
        billItems.add(mouse1);
        billItems.add(kb1);

        assertEquals(296.94, bill.getOrderPrice(billItems),0.0);
    }
}
