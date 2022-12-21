

//First Fit Algorithm in Memory Management

import java.util.IllegalFormatCodePointException;
import java.util.Scanner;

public class OsProject3 {

    static void firstFit(int blockSize[], int m,
                         int processSize[], int n) {
        // Stores block id of the
        // block allocated to a process
        int allocation[] = new int[n];

        // Initially no block is assigned to any process
        for (int i = 0; i < allocation.length; i++)
            allocation[i] = -1;

        // pick each process and find suitable blocks
        // according to its size ad assign to it
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (blockSize[j] >= processSize[i]) {
                    // allocate block j to p[i] process
                    allocation[i] = j;

                    // Reduce available memory in this block.
                    blockSize[j] -= processSize[i];

                    break;
                }
            }
        }

        System.out.println("\nProcess No.\tProcess Size\tBlock no.");
        for (int i = 0; i < n; i++) {
            System.out.print(" " + (i + 1) + "\t\t" +
                    processSize[i] + "\t\t");
            if (allocation[i] != -1)
                System.out.print(allocation[i] + 1);
            else
                System.out.print("Not Allocated");
            System.out.println();
        }
    }

    // Driver Code
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int ramSize = scanner.nextInt();
        scanner.next();
        if (ramSize > 1024 || ramSize < 1) {
            System.out.println("Ram size should be a real number between 1 and 1024");
            ramSize = scanner.nextInt();
        }
        int blockArr[] = {-1, -1, -1, -1};

        int i = 0;
        while (scanner.hasNextLine()) {
            String[] h = scanner.nextLine().split(" ");
            String statement = h[0];
            String process = h[1];
            if (statement.equals("REQUEST")) {
                blockArr[i] = Integer.parseInt(h[2]);
            }
            if (statement.equals("RELEASE")) {

            }
        }
        System.out.println(blockArr[1]);

        int blockSize[] = {100, 500, 200, 300, 600};
        int processSize[] = {212, 417, 112, 426};
        int m = blockSize.length;
        int n = processSize.length;

        firstFit(blockSize, m, processSize, n);
    }
}









