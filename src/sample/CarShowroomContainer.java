package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

public class CarShowroomContainer
{
    private Map<String,CarShowroom> salony;

    public CarShowroomContainer() {
        this.salony = new HashMap<>();
    }

    public void addCenter(String name, CarShowroom carShowroom){
        salony.put(name,carShowroom);
    }

    public List<CarShowroom> findEmpty()
    {
        List<CarShowroom> salons= new ArrayList<>();
        for (Map.Entry<String, CarShowroom> entry : salony.entrySet()) {
            if (entry.getValue().isEmpty()){
                salons.add(entry.getValue());
            }
        }
        return salons;
    }

    public void summary(){
        for (Map.Entry<String, CarShowroom> entry : salony.entrySet()) {
            System.out.println("Nazwa salonu: "+entry.getKey()+"Procentowe zapełninie: "+
                    entry.getValue().getPercentageFill()+"%");
        }
    }

    public ObservableList <String> listOfCarSaloons() // lista salonów samochodowych
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        for (Map.Entry<String,CarShowroom> entry : salony.entrySet())
        {
            list.add(entry.getKey());
        }
        return list;
    }

    public String [] arrayOfSortedShowrooms(){
        int i=0;
        String [] array=new String[salony.size()];
        ArrayList<CarShowroom> valueList = new ArrayList<>(salony.values());
        Collections.sort(valueList, new Comparator<CarShowroom>() {
            @Override
            public int compare(CarShowroom o1, CarShowroom o2) {
                return Double.compare(o2.getPercentageFill(),o1.getPercentageFill());
            }
        });
        for (CarShowroom showroom : valueList) {
            array[i]=showroom.getName();
            i++;
        }
        return array;
    }


    public CarShowroom selectedParticularShowroom(String name) // po wybraniu konkretnego salonu
    {
        for (Map.Entry<String,CarShowroom> entry : salony.entrySet())
        {
            if (entry.getKey().equals(name))
            {
                return entry.getValue();
            }
        }
        return null;
    }

    public ObservableList <String> listOfCity() // lista miast w których znajdują się poszczególne salony
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        for (Map.Entry<String, CarShowroom> entry : salony.entrySet())
        {
            List<Vehicle> temp = new ArrayList<>(entry.getValue().getCarList());
            for (Vehicle vehicle : temp)
            {
                if(!contains(list,vehicle.getCity()))
                {
                    list.add(vehicle.getCity());
                }
            }
        }
        return list;
    }

    public boolean contains(ObservableList<String>list,String city)
    {
        for (String s : list)
        {
            if (s.equals(city))return true;
        }
        return false;
    }

    public String findParticularSaloon(Vehicle vehicle2) // znajdź poszczególny salon samochodowy
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        for (Map.Entry<String, CarShowroom> entry : salony.entrySet())
        {
            List<Vehicle> temp = new ArrayList<>(entry.getValue().getCarList());
            for (Vehicle vehicle : temp)
            {
                if((vehicle.equals(vehicle2)))
                {
                    return entry.getValue().getName();
                }
            }
        }
        return null;
    }
}
