////////////////////////////////////////////////////////////////////
// [GIOVANNI] [MORETTI] [1217655]
// [GIOVANNI] [COLANGELO] [2008070]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss;

import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.itemType;
import it.unipd.mtss.business.exception.BillException;
import java.util.List;

import it.unipd.mtss.business.Bill;

public class BillStd implements Bill {

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

    public double getCheaperItem(List<EItem> itemsOrdered, itemType article){
        int j = 0;
        double priceOfCheaper  = Double.MAX_VALUE;
        while(j < itemsOrdered.size()){
            if (itemsOrdered.get(j).getItemType() == article){
                if (itemsOrdered.get(j).getPrice() < priceOfCheaper){
                    priceOfCheaper = itemsOrdered.get(j).getPrice();
                }
            }
            j++;
        }
        return priceOfCheaper;
    }

    public double getOrderPrice(List<EItem> itemsOrdered) throws BillException{
        int i = 0;
        double tot = 0;
        if(itemsOrdered.size() == 0){
            throw new BillException("invalid list size: 0 items");
        }

        while(i < itemsOrdered.size()){
            if(itemsOrdered.get(i).getPrice() < 0) {
                throw new BillException("invalid price value: negative value");
            }
            else{
            tot += itemsOrdered.get(i).getPrice();
            i++;
            }
        }

        if(getItemNumber(itemsOrdered, itemType.Processor) > 5){
            tot = tot - getCheaperItem(itemsOrdered, itemType.Processor) / 2;
        }

        if(getItemNumber(itemsOrdered, itemType.Mouse) > 10){
            tot = tot - getCheaperItem(itemsOrdered, itemType.Mouse);
        }

        return tot;
    }



}
