////////////////////////////////////////////////////////////////////
// [GIOVANNI] [MORETTI] [1217655]
// [GIOVANNI] [COLANGELO] [2008070]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.model;

import it.unipd.mtss.business.exception.BillException;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unipd.mtss.business.Bill;

public class BillStd implements Bill {

    List<User> userList = new ArrayList<>();
    Clock clock = Clock.system(ZoneId.systemDefault());

    //checks if order can be gifted
    public boolean orderCanBeGifted(User user, LocalDateTime orderDateTime){
        LocalDate currentDate = LocalDate.now();
        LocalTime minTime = LocalTime.of(18, 00, 00, 00000);
        LocalTime maxTime = LocalTime.of(19, 00, 00, 00000);
        LocalDateTime min = LocalDateTime.of(currentDate, minTime); //today at 18.00
        LocalDateTime max = LocalDateTime.of(currentDate, maxTime); //today at 19.00
        if(orderDateTime.isAfter(min) && orderDateTime.isBefore(max)){ // order time is between 18.00 and 19.00 of today
            if(user.getAge() < 18){ // user is a minor
                if(!userList.contains(user)){ // user hasn't already received a gift
                    if(userList.size() < 10){ // less than 10 gift have been given
                        //user gets a chance to win a gift
                            return true;
                        }
                    }
                }
            }
        return false;
    }

    public double giftWin(User user, double totalOrderPrice){
        Random rnd = new Random();
        boolean check = rnd.nextBoolean();
        if(check){
            this.userList.add(user);
            return 0;
        }
        return totalOrderPrice;
    }

    LocalDate currentDate = LocalDate.now();
    LocalTime currentTime = LocalTime.now();
    LocalDateTime fromDateAndTime = LocalDateTime.of(currentDate, currentTime);

    //return number of items of a certain itemType in an item list
    public int getItemNumber(List<EItem> itemsOrdered, itemType article){
        int k = 0;
        int itemNumber = 0;
        while(k < itemsOrdered.size()){
            if(itemsOrdered.get(k).getItemType() == article) {
                itemNumber++;
            }
            k++;
        }
        return itemNumber;
    }

    //returns cheaper item of a certain itemType
    public EItem getCheaperItem(List<EItem> itemsOrdered, itemType article){
        int j = 0;
        EItem cheaperItem = new EItem(itemType.Keyboard, "", Double.MAX_VALUE);
        while(j < itemsOrdered.size()){
            if (itemsOrdered.get(j).getItemType() == article){
                if (itemsOrdered.get(j).getPrice() < cheaperItem.getPrice()){
                    cheaperItem = itemsOrdered.get(j);
                }
            }
            j++;
        }
        return cheaperItem;
    }

    //returns cheaper item in a list
    public EItem getCheaperItemOverall(List<EItem> itemsOrdered){
        int j = 0;
        EItem cheaperItem = new EItem(itemType.Keyboard, "", Double.MAX_VALUE);
        while(j < itemsOrdered.size()){
                if (itemsOrdered.get(j).getPrice() < cheaperItem.getPrice()){
                    cheaperItem = itemsOrdered.get(j);
            }
            j++;
        }
        return cheaperItem;
    }


    //puts items to be gifted in itemsGifted
    public void calcTotalGifts(List<EItem> itemsGifted, List<EItem> itemsOrdered){
        if(getItemNumber(itemsOrdered, itemType.Mouse) > 10){
            giftItem(itemsGifted, getCheaperItem(itemsOrdered, itemType.Mouse));
        }

        if(getItemNumber(itemsOrdered, itemType.Mouse) == getItemNumber(itemsOrdered, itemType.Keyboard)){
            giftItem(itemsGifted, getCheaperItemOverall(itemsOrdered));
        }

    }

    //calculates discount of an order
    public double calcTotalDiscount(List<EItem> itemsOrdered, List<EItem> itemsGifted) throws BillException{
        double discount = 0;
        if(getItemNumber(itemsOrdered, itemType.Processor) > 5){
            if(!itemsGifted.contains(getCheaperItem(itemsOrdered, itemType.Processor))){ //if item isn't already gifted
                discount += (getCheaperItem(itemsOrdered, itemType.Processor).getPrice() / 2);
            }
        }
        if(getListPrice(itemsOrdered) > 1000){
            discount += (getListPrice(itemsOrdered) / 10);
        }
        return discount;
    }

    public int calcCommission(List<EItem> itemsOrdered) throws BillException{
        if(getListPrice(itemsOrdered) < 10){
            return 2;
        }
        else{
            return 0;
        }

    }

    //calculates price of an order
    public double getOrderPrice(List<EItem> itemsOrdered, User user) throws BillException{
        List<EItem> itemsGifted = new ArrayList<>();
        double discount = 0;
        double total = 0;

        if(itemsOrdered.size() == 0){
            throw new BillException("invalid list size: 0 items");
        }
        else{
            total += getListPrice(itemsOrdered);
        }

        calcTotalGifts(itemsGifted, itemsOrdered);
        discount += calcTotalDiscount(itemsOrdered, itemsGifted);

        if(checkMoreThanThirtyElements(itemsOrdered)){
            throw new BillException("invalid order: you cannot order more than 30 items");
        }
        total = total + calcCommission(itemsOrdered) - discount - getListPrice(itemsGifted);

        if(orderCanBeGifted(user, LocalDateTime.now(clock))){
            return giftWin(user, total);
        }
        else{
            return total;
        }

    }

    //if order contains more tha 30 items, throw exception
    public boolean checkMoreThanThirtyElements(List<EItem> items){
        if(items.size() > 30){
            return true;
        }
        else{
            return false;
        }
    }

    //if item isn't already gifted, item is added to itemsGifted
    public void giftItem(List<EItem> itemsGifted, EItem gift){ 
        if(!itemsGifted.contains(gift)){
            itemsGifted.add(gift);
        }
    }

    //calculates tota price of a list of items
    public double getListPrice(List<EItem> items) throws BillException{
        double tot = 0;
        int i = 0;

        while(i < items.size()){
            tot += items.get(i).getPrice();
            i++;
        }

        return tot;
    }
}
