////////////////////////////////////////////////////////////////////
// [GIOVANNI] [MORETTI] [1217655]
// [GIOVANNI] [COLANGELO] [2008070]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss;

import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.itemType;
import it.unipd.mtss.business.exception.BillException;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.model.License;

import it.unipd.mtss.business.Bill;

public class BillStd implements Bill {

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
    public double calcTotalDiscount(List<EItem> itemsOrdered, List<EItem> itemsGifted){
        double discount = 0;
        if(getItemNumber(itemsOrdered, itemType.Processor) > 5){
            if(!itemsGifted.contains(getCheaperItem(itemsOrdered, itemType.Processor))){ //if item isn't already gifted
                System.out.println(discount);
                discount += (getCheaperItem(itemsOrdered, itemType.Processor).getPrice() / 2);
                System.out.println(discount);
            }
        }
        return discount;
    }

    //calculates price of an order
    public double getOrderPrice(List<EItem> itemsOrdered) throws BillException{
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
        discount += calcTotalDiscount( itemsOrdered, itemsGifted);

        return total - discount - getListPrice(itemsGifted);
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
            if(items.get(i).getPrice() < 0) {
                throw new BillException("invalid price value: negative value");
            }
            else{
            tot += items.get(i).getPrice();
            i++;
            }
        }
        return tot;
    }
}
