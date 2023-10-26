import java.util.*;
import java.math.*;

public class State implements Comparable<State>
{
	private int f, h, g;
	private int prevG;
	private State father;
	private int totalTime;
	private ArrayList<Integer> InitialSide = new ArrayList<>();
	private ArrayList<Integer> FinalSide = new ArrayList<>();
	private boolean lantern;	// if false then lantern is on the initial side.
	
	//constructor - fill with arguments if necessary
	public State() 
	{
		this.f = 0;
		this.h = 0;
		this.g = 0;
		this.father = null;
		this.totalTime = 0;
	}
	
	// copy constructor
	public State(State s)
	{
		// create a state similar with s...
		this.f = s.f;
		this.h = s.h;
		this.g = s.g;
		this.father = s.father;
		this.totalTime = s.totalTime;
		this.lantern = s.lantern;
		this.InitialSide = new ArrayList<>(s.InitialSide);
		this.FinalSide = new ArrayList<>(s.FinalSide);
	}


	//  -----------------------------------------
	public int getF() 
	{
		return this.f;
	}
	
	public int getG() 
	{
		return this.g;
	}
	
	public int getH() 
	{
		return this.h;
	}
	
	public State getFather()
	{
		return this.father;
	}
	
	public void setF(int f)
	{
		this.f = f;
	}
	
	public void setG(int g)
	{
		this.g = g;
	}
	
	public void setH(int h)
	{
		this.h = h;
	}
	
	public void setFather(State f)
	{
		this.father = f;
	}
	
	public int getTotalTime() 
	{
		return this.totalTime;
	}
	
	public void setTotalTime(int time)
	{
		this.totalTime = time;
	}
	public boolean getLantern(){return this.lantern;}
	public void setLantern(boolean lat){this.lantern = lat;}

	public ArrayList<Integer> getFinalSide() {
		return FinalSide;
	}

	public ArrayList<Integer> getInitialSide() {
		return InitialSide;
	}

	public void setFinalSide(ArrayList<Integer> finalSide) {
		this.FinalSide = finalSide;
	}

	public void setInitialSide(ArrayList<Integer> initialSide) {
		this.InitialSide = initialSide;
	}
	// ----------------------------------------
	
	public void evaluate()
	{
		//calculate f...
		heuristic();
		setF(getH()+getG());

	}



	// Heuristic function adding the time needed for pairs to pass the bridge
	// (without calculating that someone has to go back to the start with the lantern).
	public void heuristic(){
		Collections.sort(InitialSide);
		int len = InitialSide.size();
		h = 0;

		for (int i=0; i <= (len/2)-1; i+=1){
			// adds the time of the slowest person per pair. E.g: [12,6,3,1] -> h = 12 + 3. 12,6 and 3,1 are pairs here.
			h += InitialSide.get(len-1-(i*2));

		}
		if (len % 2 == 1 ){
			h += InitialSide.get(0);
		}
		if(this.lantern){
			h += Collections.min(FinalSide);
		}
	}

	
	public void print() {
		System.out.print("Initial Side: ");
		for (int i : this.InitialSide){
			System.out.print(i+",");
		}
		System.out.print("|"+InitialSide.size());
		System.out.println();
		System.out.print("Final Side: ");
		for (int i : this.FinalSide){
			System.out.print(i+",");
		}
		System.out.println();
		System.out.print("Lantern Side: ");
		if (this.lantern){
			System.out.println("Final");
		}
		else {System.out.println("Initial");}
		System.out.println("F(n) = "+getF()+"="+getG()+"+"+getH());
		System.out.println(this.getTotalTime());
		System.out.println("-----------------------------------");
		System.out.println();

	}
	
	public ArrayList<State> getChildren() {
		ArrayList<State> children = new ArrayList<>();
		Collections.sort(InitialSide);

		if (!lantern){

			for (int i = 0; i < InitialSide.size(); i++ ){
				for (int j = i+1; j < InitialSide.size(); j++){
					State child = new State(this);
					child.setFather(this);
					child.setG(this.getG() + Math.max(InitialSide.get(i), InitialSide.get(j)));
					child.setTotalTime(Math.max(InitialSide.get(i), InitialSide.get(j)) + this.getTotalTime());
					child.lantern = !lantern;

					int iPerson = child.InitialSide.get(i);
					int jPerson = child.InitialSide.get(j);
					child.FinalSide.add(iPerson);
					child.FinalSide.add(jPerson);
					child.InitialSide.remove(Integer.valueOf(iPerson));
					child.InitialSide.remove(Integer.valueOf(jPerson));
					child.evaluate();
					children.add(child);

				}
			}
		}
		else{

			for (int i = 0; i <= FinalSide.size()-1; i++){
				State child = new State(this);
				child.setFather(this);
				child.setTotalTime(this.totalTime + FinalSide.get(i));
				child.setG(this.getG() + FinalSide.get(i));
				child.lantern = !lantern;

				child.InitialSide.add(child.FinalSide.remove(i));
				child.evaluate();
				children.add(child);

			}
		}
		return children;
	}
	
	public boolean isFinal() {
		return InitialSide.isEmpty();
	}
	
	@Override
	public boolean equals(Object o) {
		State s = (State) o;
		return (s.InitialSide.equals(this.InitialSide) && s.getLantern() == this.getLantern());
	}
	
	@Override
    public int hashCode() {return 0;}
	
	@Override
    public int compareTo(State s)
    {
        return Double.compare(this.f, s.getF()); // compare based on the heuristic score.
    }
}