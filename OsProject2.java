
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class OsProject2 {

    static Patient[] patients;

    static Doctor[] doctors = new Doctor[3];

    static class Shared {
        static int parkingCapacity = 8;
        // creating a Semaphore object
        // with number of permits 8 for parking
        static Semaphore semParking = new Semaphore(8);
//        static ArrayList<Patient> waitingList = new ArrayList<Patient>();
    }

    static class Patient extends Thread {

        int threadName;

        public Patient(int threadName) {
            this.threadName = threadName;
        }

        @Override
        public void run() {

            System.out.println("Patient No:" + threadName + " wants to enter parking");

            try {
                Shared.semParking.acquire();
                Shared.parkingCapacity--;
                System.out.println("Patient No:" + threadName + " Entered Parking");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 3; i++) {
                if (doctors[i].patientsWaiting < 2) {
                    try {
                        doctors[i].semWaiting.acquire();
                        doctors[i].patientsWaiting += 1;
                        System.out.println("Patient No:" + threadName + " Entered Dr" + doctors[i].name + "'s office");
                        doctors[i].semVisitation.acquire();
                        System.out.println(" Dr" + doctors[i].name + "visits Patient No:" + threadName);
                        //visitation takes 0.1 seconds
                        sleep(100);

                        doctors[i].semVisitation.release();

                        System.out.println("Patient No:" + threadName + " Left Dr" + doctors[i].name + "'s office");
                        doctors[i].patientsWaiting -= 1;
                        doctors[i].semWaiting.release();
                        System.out.println("Patient No:" + threadName + " Left Parking");
                        Shared.semParking.release();
                        break;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                } else if (i == 2) {
//                    Shared.waitingList.add(this);
//                }
                }


            }
        }}


        static class Doctor {
            //creating a Semaphore object with number of permits 2 for each clinic -> waiting list is 2
            Semaphore semWaiting = new Semaphore(2);
            //creating a Semaphore object with number of permits 1 for each clinic-> visitation is for 1
            Semaphore semVisitation = new Semaphore(1);
            int patientsWaiting = 0;
            String name;

            public Doctor(String name) {
                this.name = name;
            }

        }


        // Driver class
        public static void main(String args[]) throws InterruptedException {

            // 3 story building with one clinic on each floor. Total no. of doctors is 3
            // with capacity of two patients in each clinic. 3*2 = 6 patients total
            doctors[0] = new Doctor("Dr Ahmad");
            doctors[1] = new Doctor("Dr Ranjbar");
            doctors[2] = new Doctor("Dr Ali");

            //get the number of patients from user
            int noOfPatients;
            System.out.println("Number of patients: ");
            Scanner scanner = new Scanner(System.in);
            noOfPatients = scanner.nextInt();
            patients = new Patient[noOfPatients];
            for (int i = 0; i < noOfPatients; i++) {
                patients[i] = new Patient(i);
                patients[i].start();
            }


        }
    }