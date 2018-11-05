package de.jonashackt.model;

public class User {

    public User(int age, int contribution, MethodOfPayment methodOfPayment) {
        this.age = age;
        this.contribution = contribution;
        this.methodOfPayment = methodOfPayment;
    }

    public User() {};

    private int age;
    private int contribution;
    private MethodOfPayment methodOfPayment;
    
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public int getContribution() {
        return contribution;
    }
    public void setContribution(int contribution) {
        this.contribution = contribution;
    }
    public MethodOfPayment getMethodOfPayment() {
        return methodOfPayment;
    }
    public void setMethodOfPayment(MethodOfPayment methodOfPayment) {
        this.methodOfPayment = methodOfPayment;
    }
}
