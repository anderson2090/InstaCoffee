package com.sweetdeveloper.instacoffee.models;


public class CustomOrder extends Order {

    private String acidity;
    private String weight;
    private String flavor;
    private String roast;


    public CustomOrder(String acidity, String weight, String flavor, String roast) {

        super("Custom Order", "25", "1");
        this.acidity = acidity;
        this.weight = weight;
        this.flavor = flavor;
        this.roast = roast;
    }

    public CustomOrder() {

    }

    public String getAcidity() {
        return acidity;
    }

    public void setAcidity(String acidity) {
        this.acidity = acidity;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getRoast() {
        return roast;
    }

    public void setRoast(String roast) {
        this.roast = roast;
    }

}
