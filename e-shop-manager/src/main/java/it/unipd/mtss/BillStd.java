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
            tot += itemsOrdered.get(i).getPrice();
            i++;
        }
    return tot;
    }

}
