package sample;


public class CarShowroomData
{
    CarShowroomContainer carShowroomContainer =new CarShowroomContainer();
    CarShowroom carShowroomAudi =new CarShowroom("Audi",10);
    CarShowroom carShowroomBMW =new CarShowroom("BMW",10);
    CarShowroom carShowroomOpel =new CarShowroom("Opel",10);

    public CarShowroomData()
    {
        carShowroomContainer.addCenter(carShowroomAudi.getName(), carShowroomAudi);
        carShowroomContainer.addCenter(carShowroomBMW.getName(), carShowroomBMW);
        carShowroomContainer.addCenter(carShowroomOpel.getName(), carShowroomOpel);
//        Vehicle vehicle= new Vehicle("Audi","A5",ItemCondition.NEW,150000,2020,0,2000,1,"Warszawa");
//        carShowroomAudi.addProduct(vehicle);
//        vehicle= new Vehicle("Audi","A7",ItemCondition.DAMAGED,320000,2020,0,3000,1,"Kraków");
//        carShowroomAudi.addProduct(vehicle);
//        vehicle= new Vehicle("Audi","Q5",ItemCondition.USED,230000,2020,0,2500,1,"Warszawa");
//        carShowroomAudi.addProduct(vehicle);
//        vehicle= new Vehicle("BMW","seria 3",ItemCondition.USED,160000,2019,10,1800,1,"Kraków");
//        carShowroomBMW.addProduct(vehicle);
//        vehicle= new Vehicle("BMW","seria 5",ItemCondition.NEW,250000,2020,0,1800,1,"Poznań");
//        carShowroomBMW.addProduct(vehicle);
//        vehicle= new Vehicle("Opel","Astra",ItemCondition.USED,80000,2020,0,1800,1,"Warszawa");
//        carShowroomOpel.addProduct(vehicle);

    }

}
