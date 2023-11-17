import java.io.*;
import java.util.*;
import java.util.Scanner;

public class app {

    public static void main(String []args){
        Scanner input = new Scanner(System.in);
        System.out.print("Number of persons: ");
        int persons = input.nextInt();

        ArrayList<Integer> arr = new ArrayList<>();

        for (int i = 0; i<persons; i++){
            System.out.print("Enter person "+ (i+1) +" time:");
            arr.add(input.nextInt());
        }
        System.out.print("Enter solution time limit:");
        int timeLimit = input.nextInt();
        System.out.println();
        State init = new State();
        init.setInitialSide(arr);
        // A* algorithm starts from here
        SpaceSearch spaceSearch = new SpaceSearch(timeLimit);
        State finalState = spaceSearch.aStar(init);
        //prints the solution path
        if(finalState == null){
            System.out.println("Could not reach a final state. Please try a different combination of numbers!");
        }
        else{
            while (finalState.getFather() != null){
                finalState.print();
                finalState = finalState.getFather();
            }
        }




    }
}
