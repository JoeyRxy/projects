package mine.learn.javawebajax.util;

import java.util.HashSet;

/**
 * Surplus
 */
public class Surplus {

    private int surplus;
    private HashSet<SurplusListener> listenerSet;
    // private MyListener listener;

    public Surplus(int surplus) {
        this.listenerSet = new HashSet<>();
        this.surplus = surplus;
    }

    public int inc() {
        surplus++;
        notifyAllListener();
        return surplus;
    }

    public int dec() {
        surplus--;
        notifyAllListener();
        return surplus;
    }

    public void setSurplus(int surplus) {
        if (this.surplus != surplus) {
            this.surplus = surplus;
            notifyAllListener();
        }
    }

    public void register(SurplusListener listener) {
        listenerSet.add(listener);
        // this.listener = listener;
    }

    public void deleteListener(SurplusListener listener) {
        listenerSet.remove(listener);
        // this.listener = listener;
    }

    private void notifyAllListener() {
        for (SurplusListener listener : listenerSet) {
            listener.update(surplus);
        }
        // listener.update(surplus);
    }
}