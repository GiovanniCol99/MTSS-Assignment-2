package it.unipd.mtss;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import it.unipd.mtss.model.itemType;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.business.exception.BillException;

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
    private final EItem cpu7 = new EItem(itemType.Processor, "cpu7", 5.50);
    private final EItem mb1 = new EItem(itemType.Motherboard, "mb1", 30.0);
    private final EItem mb2 = new EItem(itemType.Motherboard, "mb2", 20.5);
    private final EItem mouse1 = new EItem(itemType.Mouse, "mouse1", 10.0);
    private final EItem mouse2 = new EItem(itemType.Mouse, "mouse2", 7.5);
    private final EItem mouse3 = new EItem(itemType.Mouse, "mouse3", 15);
    private final EItem kb1 = new EItem(itemType.Keyboard, "kb1", 15.0);
    private final EItem kb2 = new EItem(itemType.Keyboard, "kb2", 12.0);


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
    public void testGetListPriseWithInvalidPriceValue() throws BillException {
        billItems.add(cpu1);
        billItems.add(mb2);
        billItems.add(new EItem(itemType.Processor, "invalidCpu", -1.0));

        bill.getListPrice(billItems);
    }

    @Test(expected = BillException.class, timeout = 500)
    public void testGetOrderPriceWithNoItem() throws BillException {
        bill.getOrderPrice(billItems);
    }
    
    @Test(timeout = 500)
    public void testGetItemNumber(){
        billItems.add(cpu1);
        billItems.add(mb1);
        billItems.add(kb1);

        assertEquals(1, bill.getItemNumber(billItems, itemType.Processor));
    }

    @Test(timeout = 500)
    public void testgetCheaperItemOnlyProcessors(){
        billItems.add(cpu1);
        billItems.add(cpu2);
        billItems.add(cpu3);

        assertEquals(32.5, bill.getCheaperItem(billItems, itemType.Processor).getPrice(), 0.0);
    }

    @Test(timeout = 500)
    public void testgetCheaperItemMixedOrder(){
        billItems.add(cpu1);
        billItems.add(cpu4);
        billItems.add(mb1);
        billItems.add(mouse1);
        billItems.add(kb1);

        assertEquals(45.5, bill.getCheaperItem(billItems, itemType.Processor).getPrice(), 0.0);
    }

    @Test(timeout = 500)
    public void testCalcTotalDiscountWithFiveProcessor() throws BillException{
        double discount = 0;
        List<EItem> gifts;
        gifts = new ArrayList<>();
        gifts.add(cpu1);
        billItems.add(mouse2);
        billItems.add(cpu1);
        billItems.add(cpu2);
        billItems.add(cpu3);
        billItems.add(cpu4);
        billItems.add(cpu5);
        billItems.add(mouse1);
        billItems.add(kb1);
        
        discount = bill.calcTotalDiscount(billItems, gifts);

        assertEquals(0, discount,0.0);
    }

    @Test(timeout = 500)
    public void testTotalDiscountWithSixProcessor() throws BillException{
        double discount = 0;
        List<EItem> gifts;
        gifts = new ArrayList<>();
        gifts.add(kb2);
        billItems.add(mouse2);
        billItems.add(cpu1);
        billItems.add(cpu2);
        billItems.add(cpu3);
        billItems.add(cpu4);
        billItems.add(cpu5);
        billItems.add(cpu6);
        billItems.add(mouse1);
        billItems.add(kb1);

        discount = bill.calcTotalDiscount(billItems, gifts);

        assertEquals(11.75, discount,0.0);
    }

    @Test(timeout = 500)
    public void testCalcTotalGiftsElevenMouses() throws BillException {
        List<EItem> gifts;
        gifts = new ArrayList<>();
        List<EItem> giftedItems;
        giftedItems = new ArrayList<>();
        gifts.add(mouse2);
        billItems.add(mouse1);
        billItems.add(mouse1);
        billItems.add(mouse1);
        billItems.add(kb1);
        billItems.add(mouse2);
        billItems.add(mouse2);
        billItems.add(mouse2);
        billItems.add(mouse3);
        billItems.add(mouse3);
        billItems.add(mouse3);
        billItems.add(mouse3);
        billItems.add(mouse3);
        
        bill.calcTotalGifts(giftedItems, billItems);

        assertEquals(gifts, giftedItems);
    }
    
    @Test(timeout = 500)
    public void testCalcTotalDiscountWithElevenMouseAndSixProcessors() throws BillException {
        double discount = 0;
        List<EItem> giftedItems;
        giftedItems = new ArrayList<>();
        billItems.add(mouse3);
        billItems.add(mouse3);
        billItems.add(mouse3);
        billItems.add(kb1);
        billItems.add(mouse1);
        billItems.add(mouse1);
        billItems.add(mouse1);
        billItems.add(mouse2);
        billItems.add(mouse2);
        billItems.add(mouse2);
        billItems.add(mouse2);
        billItems.add(mouse2);
        billItems.add(cpu1);
        billItems.add(cpu2);
        billItems.add(cpu3);
        billItems.add(cpu4);
        billItems.add(cpu5);
        billItems.add(cpu6);
        
        bill.calcTotalGifts(giftedItems, billItems);
        discount = bill.calcTotalDiscount(billItems, giftedItems);

        assertEquals(11.75, discount, 0.0);
    }

    @Test(timeout = 500)
    public void testgetCheaperItemOverall(){
        billItems.add(cpu1);
        billItems.add(mb1);
        billItems.add(mouse1);
        billItems.add(kb1);

        assertEquals(10.0, bill.getCheaperItemOverall(billItems).getPrice(), 0.0);
    }

    @Test(timeout = 500)
    public void testOrderPriceWithTwoMuosesTwoKeyboards() throws BillException{
        billItems.add(mouse1);
        billItems.add(mouse2);
        billItems.add(kb1);
        billItems.add(kb2);
        billItems.add(cpu1);

        assertEquals(82.5, bill.getOrderPrice(billItems), 0.0);
    }

    @Test(timeout = 500)
    public void testCalcGiftedItemsWithElevenMuosesElevenKeyboards() throws BillException{
        List<EItem> gifts;
        gifts = new ArrayList<>();
        List<EItem> giftedItems;
        giftedItems = new ArrayList<>();
        gifts.add(mouse2);
        gifts.add(cpu7);
        billItems.add(mouse1);
        billItems.add(mouse2);
        billItems.add(kb1);
        billItems.add(kb2);
        billItems.add(mouse1);
        billItems.add(mouse2);
        billItems.add(kb1);
        billItems.add(kb2);
        billItems.add(mouse1);
        billItems.add(mouse2);
        billItems.add(kb1);
        billItems.add(kb2);
        billItems.add(mouse1);
        billItems.add(mouse2);
        billItems.add(kb1);
        billItems.add(kb2);
        billItems.add(mouse1);
        billItems.add(mouse2);
        billItems.add(kb1);
        billItems.add(kb2);
        billItems.add(mouse1);
        billItems.add(kb1);
        billItems.add(cpu7);

        
        bill.calcTotalGifts(giftedItems, billItems);

        assertEquals(gifts, giftedItems);
    }    
    
    @Test(timeout = 500)
    public void testOrderPriceWithElevenMuosesElevenKeyboardsSixProcessors() throws BillException{
        billItems.add(mouse1);
        billItems.add(mouse2);
        billItems.add(kb1);
        billItems.add(kb2);
        billItems.add(mouse1);
        billItems.add(mouse2);
        billItems.add(kb1);
        billItems.add(kb2);
        billItems.add(mouse1);
        billItems.add(mouse2);
        billItems.add(kb1);
        billItems.add(kb2);
        billItems.add(mouse1);
        billItems.add(mouse2);
        billItems.add(kb1);
        billItems.add(kb2);
        billItems.add(mouse1);
        billItems.add(mouse2);
        billItems.add(kb1);
        billItems.add(kb2);
        billItems.add(mouse1);
        billItems.add(kb1);
        billItems.add(cpu2);
        billItems.add(cpu3);
        billItems.add(cpu4);
        billItems.add(cpu5);
        billItems.add(cpu6);
        billItems.add(cpu7);

        assertEquals(470.69, bill.getOrderPrice(billItems), 0.0);
    }  

    @Test(timeout = 500)
    public void testGetListPrice() throws BillException{
        billItems.add(cpu1);
        billItems.add(cpu2);

        assertEquals(115.5, bill.getListPrice(billItems), 0.0);
    }

    @Test(timeout = 500)
    public void testGiftItemNewGift(){
        billItems.add(cpu1);
        EItem gift = new EItem(itemType.Processor, "gift", 5.0);
        List<EItem> gifts;
        gifts = new ArrayList<>();
        gifts.add(cpu1);
        gifts.add(gift);
        bill.giftItem(billItems, gift);

        assertEquals(billItems, gifts);
    }

    @Test(timeout = 500)
    public void testGiftItemAlreadyGifted(){
        billItems.add(cpu1);
        EItem gift = cpu1;
        List<EItem> gifts;
        gifts = new ArrayList<>();
        gifts.add(cpu1);
        bill.giftItem(billItems, gift);

        assertEquals(billItems, gifts);
    }
}
