import java.util.*;
public class SpaceSearch {
    ArrayList<State> closedSet = new ArrayList<State>();
    ArrayList<State> frontier = new ArrayList<State>();

    public State aStar(State initState){
        State currentState = null;
        frontier.add(initState);
        while(!frontier.isEmpty()){
            currentState = frontier.remove(0);
            if(currentState.isFinal()) {return currentState;}
            if(!closedSet.contains(currentState)) {

                closedSet.add(currentState);
                frontier.addAll(currentState.getChildren());
                Collections.sort(this.frontier);
            }
        }
        return null;
    }
}
