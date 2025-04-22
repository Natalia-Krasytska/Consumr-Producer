package ait.mediation;


import java.util.LinkedList;

public class BlkQueueImpl<X> implements BlkQueue<X> {
    private final LinkedList<X> queue = new LinkedList<>();
    private final int maxSize;

    public BlkQueueImpl(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public synchronized void push(X message) {
        while (queue.size() >= maxSize) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        queue.addLast(message);
        notifyAll();
    }

    @Override
    public synchronized X pop() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        X message = queue.removeFirst();
        notifyAll();
        return message;
    }
}
