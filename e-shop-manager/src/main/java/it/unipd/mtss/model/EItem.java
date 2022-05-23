////////////////////////////////////////////////////////////////////
// [GIOVANNI] [MORETTI] [1217655]
// [GIOVANNI] [COLANGELO] [2008070]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.model;

import java.lang.IllegalArgumentException;

public class EItem {
    itemType article;
    String name;
    double price;

    public EItem(itemType article, String name, double price) {
        if(article != null){
            this.article = article;}
            else{
                throw new IllegalArgumentException("article type of an item cannot be null");
            }
        if(name != null){
            this.name = name;
        }
        else{
            throw new IllegalArgumentException("name of an item cannot be null");
        }
        if(price >= 0){
            this.price = price;
        }
        else{
            throw new IllegalArgumentException("price of an item must be a positive number");
        }
    }

    public double getPrice(){
        return price;
    }

    public itemType getItemType(){
        return article;
    }

    public String getName(){
        return name;
    }
}