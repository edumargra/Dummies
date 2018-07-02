package com.example.eduard.mindummy;

import java.util.ArrayList;

/**
 * Created by eduard on 28/06/18.
 */

public class DataFacade {
    private ArrayList<Thing> things;

    private static volatile DataFacade instance = null;
    private static final DataFacade lock = new DataFacade();

    public static DataFacade getInstance() {
        DataFacade data = instance;
        if (data == null) {
            synchronized (lock) {   // While we were waiting for the lock, another
                data = instance;    // thread may have instantiated the object.
                if (data == null) {
                    data = new DataFacade();
                    instance = data;
                }
            }
        }

        return data;
    }

    public DataFacade() {
        things = new ArrayList<>();
    }

    public ArrayList<Thing> getThings() {
        return things;
    }

    public void setThings(ArrayList<Thing> things) {
        this.things = things;
    }

    public void addThing(String name, String val1, String val2, String val3, String val4,double lat, double lng){
        Thing thing = new Thing(name, val1, val2, val3, val4, lat, lng);
        things.add(thing);
    }

    public Thing getThing(int id) {
        return things.get(id);
    }

    public void delete(int id) {
        things.remove(id);
    }

    public ArrayList<Thing> getThingsVal3(String option) {
        ArrayList<Thing> things2 = new ArrayList<>();
        for(Thing thing : things){
            if(thing.getVal3().equals(option))
                things2.add(thing);
        }
        return things2;
    }

    public ArrayList<Thing> getThingsVal4(String option) {
        ArrayList<Thing> things2 = new ArrayList<>();
        for(Thing thing : things){
            if(thing.getVal4().equals(option))
                things2.add(thing);
        }
        return things2;
    }
}
