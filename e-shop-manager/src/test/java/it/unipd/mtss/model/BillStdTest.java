package it.unipd.mtss.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
    private final EItem cpu8 = new EItem(itemType.Processor, "cpu8", 500.0);
    private final User utente1 = new User(1, "prova", "prova", LocalDate.of(1999, 6, 27));
    private final User utente2 = new User(2, "prova", "prova", LocalDate.of(1999, 6, 27));
    private final User utente3 = new User(3, "prova", "prova", LocalDate.of(1999, 6, 27));
    private final User utente4 = new User(4, "prova", "prova", LocalDate.of(1999, 6, 27));
    private final User utente5 = new User(5, "prova", "prova", LocalDate.of(1999, 6, 27));
    private final User utente6 = new User(6, "prova", "prova", LocalDate.of(1999, 6, 27));
    private final User utente7 = new User(7, "prova", "prova", LocalDate.of(1999, 6, 27));
    private final User utente8 = new User(8, "prova", "prova", LocalDate.of(1999, 6, 27));
    private final User utente9 = new User(9, "prova", "prova", LocalDate.of(1999, 6, 27));
    private final User utente10 = new User(10, "prova", "prova", LocalDate.of(1999, 6, 27));
    private final User underAgeUser = new User(11, "nome", "cognome", LocalDate.of(2010, 1, 1));

    @Before
    public void Init() {
        billItems = new ArrayList<>();
        billPrice = 0.0;
        bill.userList.clear();
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test(timeout = 500)
    public void testGetOrderPriceWithValidValues() throws BillException{
        billItems.add(cpu1);
        billItems.add(mb2);
        billItems.add(kb1);

        assertEquals(81.0, bill.getOrderPrice(billItems, utente1), 0.0);
    }

    @Test(timeout = 500)
    public void testGetOrderPriceWithNoItem() throws BillException {
        exceptionRule.expect(BillException.class);
        exceptionRule.expectMessage("invalid list size: 0 items");

        bill.getOrderPrice(billItems, utente1);
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
    public void testCalcToalGiftsWithTwoMuosesTwoKeyboards() throws BillException{
        List<EItem> gifts;
        gifts = new ArrayList<>();
        List<EItem> giftedItems;
        giftedItems = new ArrayList<>();
        gifts.add(mouse2);
        billItems.add(mouse1);
        billItems.add(mouse2);
        billItems.add(kb1);
        billItems.add(kb2);
        billItems.add(cpu1);
        
        bill.calcTotalGifts(giftedItems, billItems);

        assertEquals(gifts, giftedItems);
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
    public void testOrderPriceWithGiftsAndDIscount() throws BillException{
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

        assertEquals(470.69, bill.getOrderPrice(billItems, utente1), 0.0);
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
      
    @Test(timeout = 500)
    public void testOrderMoreThanOneThousandPrice() throws BillException {
        double discount = 0;
        List<EItem> gifts;
        gifts = new ArrayList<>();
        billItems.add(cpu8);
        billItems.add(cpu8);
        billItems.add(cpu8);
        billItems.add(cpu8);
        discount = bill.calcTotalDiscount(billItems, gifts);
        assertEquals(200.0, discount, 0.0);
    }

    @Test(timeout = 500)
    public void testCheckMoreThanThirtyElementsTrue(){
        for(int i = 0; i < 31; i++){
            billItems.add(cpu1);
        }
        assertEquals(true, bill.checkMoreThanThirtyElements(billItems));
    
    }

    @Test(timeout = 500)
    public void testCheckMoreThanThirtyElementsFalse(){
        for(int i = 0; i < 30; i++){
            billItems.add(cpu1);
        }
        assertEquals(false, bill.checkMoreThanThirtyElements(billItems));
    
    }

    @Test(timeout = 500)
    public void testOrderPriceOrderTooBig() throws BillException{
        for(int i = 0; i < 31; i++){
            billItems.add(cpu1);
        }
        exceptionRule.expect(BillException.class);
        exceptionRule.expectMessage("invalid order: you cannot order more than 30 items");

        bill.getOrderPrice(billItems, utente1);
    }

    @Test(timeout = 500)
    public void testOrderWithCommission() throws BillException{
        billItems.add(mouse2);
        assertEquals(2, bill.calcCommission(billItems));
    }

    @Test(timeout = 500)
    public void testWinGift(){

        int countGift = 0;
        for(int i = 0; i < 10; i++){
            double gifted = bill.giftWin(utente1, 1000);
            if(gifted == 0){
                countGift++;
            }
        }

        boolean atLeastOneGift = false;
        if(countGift > 1){
            atLeastOneGift = true;
        }

        assertTrue(atLeastOneGift);
        assertTrue(bill.userList.contains(utente1));
    }

    @Test(timeout = 500)
    public void testOrderCanBeGiftedTrue(){

        assertTrue(bill.orderCanBeGifted(underAgeUser, LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 30))));
    }

    @Test(timeout = 500)
    public void testOrderCanBeGiftedAndIsGifted() throws BillException{
        bill.clock = Clock.fixed(Instant.parse("2022-05-23T16:30:00.000Z"), ZoneId.systemDefault());
        System.out.println(LocalDateTime.now(bill.clock));
        billItems.add(cpu1);
        billItems.add(mouse1);
        double tot = 99;
        while(tot != 0.00){ //force giftOrder
             tot = bill.getOrderPrice(billItems, underAgeUser);
        }

         assertEquals(tot, 0.00, 0.00);
    }

    //test fail already 10 gifts
    @Test(timeout = 500)
    public void testOrderCanBeGiftedfalseAlreadyTenGifts(){
        bill.userList.add(utente1);
        bill.userList.add(utente2);
        bill.userList.add(utente3);
        bill.userList.add(utente4);
        bill.userList.add(utente5);
        bill.userList.add(utente6);
        bill.userList.add(utente7);
        bill.userList.add(utente8);
        bill.userList.add(utente9);
        bill.userList.add(utente10);

        assertTrue(!bill.orderCanBeGifted(underAgeUser, LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 30))));
    }
    
    //test fail already gifted to user
    @Test(timeout = 500)
    public void testOrderCanBeGiftedfalseAlreadyGiftedToUser(){
        bill.userList.add(underAgeUser);

        assertTrue(!bill.orderCanBeGifted(underAgeUser, LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 30))));
    }

    //test fail user not underage
    @Test(timeout = 500)
    public void testOrderCanBeGiftedfalseUserNotUnderage(){

        assertTrue(!bill.orderCanBeGifted(utente1, LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 30))));
    }

    //test fail not in correct time
    @Test(timeout = 500)
    public void testOrderCanBeGiftedfalseOrderNotIntimeWindow(){

        assertTrue(!bill.orderCanBeGifted(underAgeUser, LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 30))));
    }

    @Test(timeout = 500)
    public void testOrderPriceOrderGifted() throws BillException{
        billItems.add(cpu1);

        assertEquals(0, bill.getOrderPrice(billItems, underAgeUser), 0.0);
    }
}
