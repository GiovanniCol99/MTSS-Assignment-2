package it.unipd.mtss;

import it.unipd.mtss.model.EItem;
import it.unipd.mtss.business.exception.BillException;
import java.util.List;

import it.unipd.mtss.business.Bill;

public class BillStd implements Bill {
    public double getOrderPrice(List<EItem> itemsOrdered) throws BillException{
        int i = 0;
        double tot = 0;
        while(i < itemsOrdered.size()){
            if(itemsOrdered.get(i).getPrice() < 0) throw new BillException("invalid price value: negative value");
            else{
            tot += itemsOrdered.get(i).getPrice();
            i++;
            }
        }
    return tot;
    }

}
