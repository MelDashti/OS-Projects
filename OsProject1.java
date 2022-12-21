import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class OsProject1 {

    static Customer[] customers;

    static class Chef extends Thread {
        public Chef(int chefName) {
            this.chefName = chefName;
        }

        Customer[] customer;
        int chefName;

        public void run() {
            //Total time taken to make kebabs -> Number Of kebab seekh multiplied by time taken to prepare one kebab which is 200 milliseconds


            for (int i = 0; i < customer.length; i++) {
                int timeTaken = customer[i].kebabSeekh * 200;
                try {
                    sleep(timeTaken); //2 seconds for every kebab
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Chef " + chefName + "prepared order of " + customer[i].customerName);
                customer[i].start();
                if (i + 1 == customer.length) System.out.println("Chef" + chefName + "completed all his orders");
            }


        }
    }

    static class Customer extends Thread {


        String customerName;
        int kebabSeekh;

        public Customer(String customerName, Integer kebabSeekh) {
            this.customerName = customerName;
            this.kebabSeekh = kebabSeekh;
        }

        public void run() {

            boolean visitsWC = false;
            int timeTaken = kebabSeekh * 150;
            //total time taken by the customer to eat all the kebabs.
            try {
                sleep(timeTaken);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(customerName + " finished eating food");

            // if number of kebabs is more than 10, the customers visits WC

            if (kebabSeekh > 10) {
                visitsWC = true;
                try {
                    sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(customerName + " visits WC");

            int costOneSeekh = 8000;
            //calculation for bill amount
            if (kebabSeekh > 12) {
                costOneSeekh = 6000;
            }
            int totalBillAmount = kebabSeekh * costOneSeekh;
            if (visitsWC) totalBillAmount += 5000;
            System.out.println(customerName + " " + totalBillAmount + " puts on the table");


        }
    }

    static class Cashier extends Thread {

        public void run() {

            int noOfChefs;
            System.out.println("Number of Chefs:");
            Scanner scanner = new Scanner(System.in);
            noOfChefs = scanner.nextInt();

            // Read txt file as list.

            List<String> content = null;

            int numberOfOrders;
            try {
                content = Files.readAllLines(Paths.get("C:\\Users\\Meelad\\Desktop\\Orders.txt"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            numberOfOrders = Integer.parseInt(content.get(0));
            content.remove(0);
            customers = new Customer[numberOfOrders];
            // First line -> No. of Orders
            //getting the orders from txt file

            for (int i = 0; i < content.size() - 1; i++) {
                String[] temp = content.get(i).split(" ");
                String customerName = temp[0];
                Integer noOfSeekhs = Integer.parseInt(temp[1]);
                customers[i] = new Customer(customerName, noOfSeekhs);
            }

            //initialize no. of cooks entered
            Chef[] chefs = new Chef[noOfChefs];

            // every chef gets equal number of orders
            int noOfOrdersForEach = numberOfOrders / noOfChefs;


            int j = 0;// temp variable for number of orders
            int k = 0;// index of customer array (number of orders)
            for (int i = 0; i < noOfChefs; i++) {
                //array for no. of customers sent to one chef
                Customer[] subCustomerArr = new Customer[noOfOrdersForEach];
                chefs[i] = new Chef(i);
                while (j < noOfOrdersForEach && k < numberOfOrders) {
                    subCustomerArr[j] = customers[k++];
//                    chefs[i].customer = customers[k++];
                    j++;
                }
                chefs[i].customer = subCustomerArr;
                chefs[i].start();
                j = 0;
                System.out.println("Chef " + i + " gets orders");
            }

            System.out.println("Cashier completes his task");
        }

    }

    public static void main(String[] args) throws IOException {

        Cashier cashier = new Cashier();
        cashier.start();

    }


}



