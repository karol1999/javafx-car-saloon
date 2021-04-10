package sample;

import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;

public class CarShowroom
{
    private String nameOfTheCarShowroom;
    private List<Vehicle> carList;
    private int maxCarShowroomCapacity;

    public CarShowroom(String newShowroomName, int newMaxShowroomCapacity)
    {
        this.nameOfTheCarShowroom = newShowroomName;
        this.maxCarShowroomCapacity = newMaxShowroomCapacity;
        this.carList = new ArrayList<>(); // na razie tworzymy pustą Arraylistę
    }

    public boolean addProduct(Vehicle vehicle)
    {
        if (vehicle!=null) // jeżeli obiekt nie jest nullem
        {
            if (carList.size() == maxCarShowroomCapacity) // jeżeli już nie ma miejsca w ArrayLiscie
            {
                System.err.println("Pojemność magazynu została przekroczona."); // informacja zwrotna, że nie ma już miejsca
            }
            else if (carList.contains(vehicle)) // jeżeli już obiekt jest w liście to go dodajemy (zwiększamy licznik dla podanego pojazdu)
            {
                carList.get(carList.indexOf(vehicle)).adjustAmountOfCars(vehicle.getAmount());
                return true;
            }

            return carList.add(vehicle); // jeżeli go nie ma w liście to go dodajemy dopiero
        }

        return false;
    }

    public void getProduct(Vehicle vehicle)
    {
        vehicle.adjustAmountOfCars(-1); // zliczamy ilość pojazdów w garażu

        if (vehicle.getAmount()==0) // jeżeli ilość samochodów jest równa 0 to go całkowicie usuwamy
        {
            carList.remove(vehicle);
        }
    }

    public boolean removeProduct(Vehicle vehicle)
    {
        if (vehicle!=null)
        {
            return carList.remove(vehicle);
        }
        return false;
    }

    public void search(String newModel)
    {
        for(Vehicle vehicle : carList)
        {
            if(vehicle.getModel().equalsIgnoreCase(newModel))
            {
                vehicle.print();
                return;
            }
        }

        System.out.println("Nie znaleziono danego przedmiotu: " + newModel);
    }

    public void searchPartial(String newModelPartial)
    {
        for(Vehicle vehicle : carList)
        {
            if(Pattern.compile(Pattern.quote(newModelPartial), Pattern.CASE_INSENSITIVE).matcher(vehicle.getModel()).find())
            {
                vehicle.print();
                return;
            }
        }

        System.out.println("Nie znaleziono danego przedmiotu");
    }

    public int countByCondition(ItemCondition newItemCondition)
    {
        int amoutOfCarsOfParticularCondition = 0;

        for (Vehicle vehicle : carList)
        {
            if(vehicle.getCondition().equals(newItemCondition))
            {
                amoutOfCarsOfParticularCondition += vehicle.getAmount();
            }
        }

        if(amoutOfCarsOfParticularCondition == 0)
        {
            System.out.println("Nie znaleziono danego przedmiotu");
            return 0;
        }
        else
        {
            return amoutOfCarsOfParticularCondition;
        }
    }

    public void summary()
    {
        for (Vehicle vehicle : carList)
        {
            vehicle.print();
        }
    }

    public  void sortByName()
    {
        Collections.sort(carList);
        for (Vehicle vehicle : carList)
        {
            System.out.println(vehicle);
        }
    }

    public void sortByAmount()
    {
        Collections.sort(carList, new ReverseSortByAmount());

        for (Vehicle vehicle: carList)
        {
            vehicle.print();
        }
    }


    public Vehicle maxElement() // mogliśmy stworzyć klasę z implementacją Comparatora, albo w maine wywołać z przypisaniem albo anonymous class tak jak tutaj
    {
        return Collections.max(carList, new Comparator<Vehicle>()
        {
            @Override
            public int compare(Vehicle firstVehicle, Vehicle secondVehicle)
            {
                return Integer.compare(firstVehicle.getAmount(), secondVehicle.getAmount());
            }
        });
    }

    public boolean isEmpty()
    {
        if (carList.isEmpty())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public double getPercentageFill()
    {
        double result = (double) carList.size()/getMaxCarShowroomCapacity();
        return result*100;
    }

    public String getName(){
        return nameOfTheCarShowroom;
    }

    public List<Vehicle> getCarList() {
        return carList;
    }

    public int getMaxCarShowroomCapacity() {
        return maxCarShowroomCapacity;
    }
}
