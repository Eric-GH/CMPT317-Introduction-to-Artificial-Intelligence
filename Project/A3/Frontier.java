import java.util.*;

public interface Frontier {
    boolean cutoff();

    void initial();

    int len();

    boolean is_empty();
    void add(SearchNode aNode);
    SearchNode remove();
}

class  FrontierFIFO implements Frontier{
    Queue<SearchNode> nodes;
    FrontierFIFO(){
        initial();
    }

    @Override
    public boolean cutoff() {
        return false;
    }

    @Override
    public void initial() {
        nodes = new LinkedList<>();
    }

    @Override
    public int len() {
        return nodes.size();
    }

    @Override
    public boolean is_empty() {
        return nodes.size()==0;
    }
    @Override
    public void add(SearchNode aNode){
        nodes.offer(aNode);
    }
    @Override
    public SearchNode remove(){
        return nodes.remove();
    }
}

class FrontierLIFO implements Frontier{
    Stack<SearchNode> nodes;
    public FrontierLIFO(){
        initial();
    }
    @Override
    public void add(SearchNode aNode){
        nodes.push(aNode);
    }
    @Override
    public SearchNode remove(){
        return nodes.pop();
    }

    @Override
    public void initial() {
        nodes = new Stack<>();
    }

    @Override
    public int len() {
        return nodes.size();
    }

    @Override
    public boolean is_empty() {
        return nodes.size() ==0;
    }
    @Override
    public boolean cutoff() {
        return false;
    }
}

class FrontierLIFO_DL extends FrontierLIFO implements Frontier{
    int dlimit;
    boolean cutoff;
    FrontierLIFO_DL(int dlimit){
        this.dlimit = dlimit;

        cutoff = false;
    }
    @Override
    public void  add(SearchNode aNode){
        if (aNode.depth <= this.dlimit){
            nodes.add(aNode);
        }
        else if (!cutoff){
            this.cutoff = true;
        }
    }

    @Override
    public boolean cutoff() {
        return cutoff;
    }

}

