////////////////////////////////////////////////////////////////////
// [GIOVANNI] [MORETTI] [1217655]
// [GIOVANNI] [COLANGELO] [2008070]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.business;

import java.util.List;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.itemType;

public interface Bill {
    int getItemNumber(List<EItem> itemsOrdered, itemType article);
}