package de.jonashackt.model;

public enum MethodOfPayment {

    Paypal("Paypal"), Bitcoin("Bitcoin"), Unknown("Unknown");
    
    private String name;
    
    private MethodOfPayment(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
