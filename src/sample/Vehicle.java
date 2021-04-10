package sample;

import java.io.Serializable;

public class Vehicle implements Comparable<Vehicle>, Serializable
{
    @CSVAnnotation("brand")
    final private String brand;

    @CSVAnnotation("model")
    private String model;

    private ItemCondition condition;

    @CSVAnnotation("price")
    private double price;

    @CSVAnnotation("yearOfProduction")
    private int yearOfProduction;

    @CSVAnnotation("mileage")
    private double mileage;

    @CSVAnnotation("engineCapacity")
    private double engineCapacity;
    private int amount;

    @CSVAnnotation("city")
    private String city;
    private String status;

    public Vehicle(String brand, String model, ItemCondition condition, double price, int yearOfProduction, double mileage, double engineCapacity, int amount,String city)
    {
        this.brand = brand;
        this.model = model;
        this.condition = condition;
        this.price = price;
        this.yearOfProduction = yearOfProduction;
        this.mileage = mileage;
        this.engineCapacity = engineCapacity;
        this.amount = amount;
        this.city=city;
        status = "Nierez";
    }

    public void print(){
        System.out.println("Brand: "+brand+" Model: "+model+" Condition: "+condition+" Price: "+price+" Year of production: "+
                yearOfProduction+" Mileage: "+mileage+" Engine capacity: "+engineCapacity+" Amount: "+amount);
    }

    @Override
    public int compareTo(Vehicle o) {
        if (this.brand.equals(o.brand)){
            return this.model.compareTo(o.model);
        }
        return this.brand.compareTo(o.brand);
    }

    public int adjustAmountOfCars(int amount){
        return this.amount+=amount;
    }

    public int getAmount() {
        return amount;
    }

    public String getBrand() {
        return brand;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", condition=" + condition +
                ", price=" + price +
                ", yearOfProduction=" + yearOfProduction +
                ", mileage=" + mileage +
                ", engineCapacity=" + engineCapacity +
                ", amount=" + amount +
                '}';
    }

    public ItemCondition getCondition() {
        return condition;
    }

    @Override
    public boolean equals(Object object) {
        boolean result = false;
        if (object == null || object.getClass() != getClass()) {
            result = false;
        } else {
            Vehicle vehicle = (Vehicle) object;
            if (this.brand.equals(vehicle.getBrand()) && this.model.equals(vehicle.getModel())   && this.engineCapacity == vehicle.getEngineCapacity()
                    && this.mileage==vehicle.getMileage() && this.yearOfProduction== vehicle.getYearOfProduction() && this.price==vehicle.getPrice()
                    && this.condition.equals(vehicle.getCondition()) &&this.city.equals(vehicle.getCity())) {
                result = true;
            }
        }
        return result;
    }

    public String getModel() {
        return model;
    }

    public double getPrice() {
        return price;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public double getMileage() {
        return mileage;
    }

    public double getEngineCapacity() {
        return engineCapacity;
    }

    public String getCity() {
        return city;
    }

    public void setStatus(String newStatus) { status = newStatus; }

    public String getStatus() {return status;}

}
