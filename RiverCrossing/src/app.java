import java.io.*;
import java.util.*;
import java.util.Scanner;

public class app {


    public static void main(String []args){
        Scanner input = new Scanner(System.in);
        //System.out.print("Number of persons: ");
        //int persons = input.nextInt();

        int persons = 5;
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(12);
        //arr.add(8);
        arr.add(6);
        arr.add(3);
        arr.add(1);
        /*for (int i = 0; i<persons; i++){
            System.out.print("Enter person "+ (i+1) +" time:");
            arr.add(input.nextInt());
        }*/
        State init = new State();
        init.setInitialSide(arr);
        SpaceSearch spaceSearch = new SpaceSearch();
        State finalState = spaceSearch.aStar(init);
        System.out.println(finalState);

        while (finalState.getFather() != null){
            finalState.print();
            finalState = finalState.getFather();
        }






    }
}
