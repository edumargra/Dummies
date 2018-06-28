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

    public void addThing(String name, String val1, String val2, String val3, String val4){
        Thing thing = new Thing(name, val1, val2, val3, val4);
        things.add(thing);
    }

    public Thing getThing(int id) {
        return things.get(id);
    }

    public void delete(int id) {
        things.remove(id);
    }
}
