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

    public int getProcessorNumber(List<EItem> itemsOrdered){

        int k = 0;
        int processorNumber = 0;

        while(k < itemsOrdered.size()){
            if(itemsOrdered.get(k).getItemType() == itemType.Processor) {
                processorNumber++;
            }
            k++;
        }
        return processorNumber;
    }

    public double getCheaperProcessor(List<EItem> itemsOrdered){
        int j = 0;
        double priceOfCheaper  = Double.MAX_VALUE;
        while(j < itemsOrdered.size()){
            if (itemsOrdered.get(j).getItemType() == itemType.Processor){
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

        while(i < itemsOrdered.size()){
            if(itemsOrdered.get(i).getPrice() < 0) {
                throw new BillException("invalid price value: negative value");
            }
            else{
            tot += itemsOrdered.get(i).getPrice();
            i++;
            }
        }

        if(getProcessorNumber(itemsOrdered) > 5){
            tot = tot - getCheaperProcessor(itemsOrdered) / 2;
        }

        return tot;
    }



}
