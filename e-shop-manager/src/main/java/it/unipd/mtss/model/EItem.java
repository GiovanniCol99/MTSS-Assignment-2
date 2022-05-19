////////////////////////////////////////////////////////////////////
// [GIOVANNI] [MORETTI] [1217655]
// [GIOVANNI] [COLANGELO] [2008070]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.model;


public class EItem {
    itemType article;
    String name;
    double price;

    public EItem(itemType article, String name, double price) {
        this.article = article;
        this.name = name;
        this.price = price;
    }

    public double getPrice(){
        return price;
    }

    public itemType getItemType(){
        return article;
    }
}
