//src\database\OrderQueue.java
package database;

import java.util.LinkedList;
import java.util.Queue;

public class OrderQueue {
    private static OrderQueue instance;
    private LinkedList<String> completedOrds = new LinkedList<>();
    private Queue<Order> orderQueue;

    private OrderQueue() {
        orderQueue = new LinkedList<>();
    }

    public static synchronized OrderQueue getInstance() {
        if (instance == null) {
            instance = new OrderQueue();
        }
        return instance;
    }

    public class Order {
        private String flavor;
        private String size;
        private String temp;
        private String addons;
        private int quantity;
        private int price;
        private int ordnumb;

        public Order(String flavor, String size, String temp, String addons, int quantity, int price, int ordnumb) {
            this.flavor = flavor;
            this.size = size;
            this.temp = temp;
            this.addons = addons;
            this.quantity = quantity;
            this.price = price;
            this.ordnumb = ordnumb;
        }

        public String getFlavor() {
            return flavor;
        }

        public String getSize() {
            return size;
        }

        public String getTemp() {
            return temp;
        }

        public String getAddons() {
            return addons;
        }

        public int getQuantity() {
            return quantity;
        }

        public int getPrice() {
            return price;
        }

        public int getNum() {
            return ordnumb;
        }
    }

    public void addOrderToQueue(String flavor, String size, String temp, String addons, int quantity, int price, int ordnumb) {
        Order newOrder = new Order(flavor, size, temp, addons, quantity, price, ordnumb);
        orderQueue.add(newOrder);
        System.out.println("Added to queue: " + newOrder.getFlavor());
    }

    public Order processNextOrder() {
        Order nextOrder = orderQueue.poll();
        if (nextOrder != null) {
            System.out.println("Processing order: " + nextOrder.toString());
        } else {
            System.out.println("No orders in queue.");
        }
        return nextOrder;
    }

    public boolean isQueueEmpty() {
        return orderQueue.isEmpty();
    }

    public int getQueueSize() {
        return orderQueue.size();
    }

    public void printQueue() {
        if (orderQueue.isEmpty()) {
            System.out.println("The queue is empty.");
        } else {
            System.out.println("Current orders in the queue:");
            for (Order order : orderQueue) {
                System.out.println(order.toString());
            }
        }
    }

    public Order peekNextOrder() {
        Order nextOrder = orderQueue.peek();
        if (nextOrder == null) {
            System.out.println("No orders in queue.");
        }
        return nextOrder;
    }

    public Queue<Order> pollOrdersWithSameNum(int ordnum) {
        Queue<Order> matchingOrders = new LinkedList<>();
        Queue<Order> tempQueue = new LinkedList<>();

        while (!orderQueue.isEmpty()) {
            Order currentOrder = orderQueue.poll();
            if (currentOrder.getNum() == ordnum) {
                matchingOrders.add(currentOrder);
            } else {
                tempQueue.add(currentOrder);
            }
        }

        orderQueue = tempQueue;

        if (matchingOrders.isEmpty()) {
            System.out.println("No orders found with order number: " + ordnum);
        } else {
            System.out.println("Removed " + matchingOrders.size() + " orders with order number: " + ordnum);
        }
        return matchingOrders;
    }

    public Queue<Order> peekOrdersWithSameNum(int ordnum) {
        Queue<Order> matchingOrders = new LinkedList<>();

        for (Order order : orderQueue) {
            if (order.getNum() == ordnum) {
                matchingOrders.add(order);
            }
        }

        if (matchingOrders.isEmpty()) {
            System.out.println("No orders found with order number: " + ordnum);
        } else {
            System.out.println("Found " + matchingOrders.size() + " orders with order number: " + ordnum);
        }

        return matchingOrders;
    }

    public void setCompletedOrds(LinkedList<String> completedOrds) {
        this.completedOrds = completedOrds;
    }

    public LinkedList<String> getCompletedOrds() {
        return completedOrds;
    }

    public String getFirstCompletedOrd() {
        String firstCompletedOrder = completedOrds.peekFirst();
        if (firstCompletedOrder != null) {
            return firstCompletedOrder;
        } else {
            return "No completed orders yet.";
        }
    }

    public void clearOrderQueue() {
        completedOrds.clear();
        orderQueue.clear();
    }
}
