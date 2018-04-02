package com.sweetdeveloper.instacoffee.broadcastrevievers;


import java.util.Observable;

public class NewsObservable extends Observable {
    private static NewsObservable instance = new NewsObservable();
    public static NewsObservable getInstance() {
        return instance;
    }

    private NewsObservable() {
    }

    public void updateValue(Object data) {
        synchronized (this) {
            setChanged();
            notifyObservers(data);
        }
    }
}
