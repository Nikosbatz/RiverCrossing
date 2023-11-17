import java.util.*;
public class SpaceSearch {
    ArrayList<State> closedSet = new ArrayList<State>();
    ArrayList<State> frontier = new ArrayList<State>();
    private final int  timeLimit;

    public SpaceSearch(int x){
        timeLimit = x;
    }

    public State aStar(State initState){
        State currentState = null;
        frontier.add(initState);
        while(!frontier.isEmpty()){
            currentState = frontier.remove(0);
            if(currentState.isFinal() & currentState.getG() < timeLimit) {return currentState;}

            if(!closedSet.contains(currentState) & currentState.getG() < timeLimit) {
                closedSet.add(currentState);
                frontier.addAll(currentState.getChildren());
                Collections.sort(this.frontier);
            }
        }
        return null;
    }
}
