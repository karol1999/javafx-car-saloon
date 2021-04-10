package sample;

import java.util.Comparator;

public class ReverseSortByAmount implements Comparator<Vehicle>
{
    @Override
    public int compare(Vehicle firstVehicle, Vehicle secondVehicle)
    {
        return Integer.compare(firstVehicle.getAmount(),secondVehicle.getAmount());
    }
}
