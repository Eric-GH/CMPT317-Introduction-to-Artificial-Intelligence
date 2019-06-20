
public class A1Search {
}
class SearchNode {

    State state;
    SearchNode parent;
    int path_cost = 0;
    int depth = 0;
    int step_cost;

    SearchNode(State state, SearchNode parent_node){
        this.step_cost = 1;
        this.state = state;
        this.parent = parent_node;
        if (parent_node == null){
            this.path_cost = 0;
            this.depth = 0;
        }
        else {
            this.path_cost = parent_node.path_cost+this.step_cost;
            this.depth = parent_node.depth + 1;
        }
    }

    @Override
    public String toString() {
        return "<"+ this.depth + "> " + this.state + " ("+this.path_cost+")";
    }

    void display_steps(){
        disp(this);
    }

    void disp(SearchNode node){
        if (node.parent!=null){
            disp(node.parent);
            System.out.print(node.state.toString());
        }
    }
}

class SearchResult{
    boolean success= false;
    SearchNode result= null;
    double time=0;
    int nodes;
    int space= 0;
    boolean cutoff=false;
    SearchResult(boolean success, SearchNode result, double time, int nodes, int space, boolean cutoff){
        this.success= success;
        this.result = result;
        this.time=time;
        this.nodes = nodes;
        this.space = space;
        this.cutoff=cutoff;
    }


    @Override
    public String toString() {
        String text = "";
        if (this.success){
            text+="Search successful";

        }
        else {
            text += "Search failed";
        }
        text += " (" + (this.time)/1000 + " sec, " + this.nodes + " nodes, "+ this.space + " queue)";
        return text;
    }
}
class Search {
    Problem problem;

    long timelimit;
    Frontier frontier;
    Search(Problem problem, long timelimit){
        this.problem = problem;
        this.timelimit = timelimit*1000;


    }

    SearchNode childNode(SearchNode parent_node, int action){
        State child = this.problem.result(parent_node.state,action);
        //child.Display();

        return new SearchNode(child,parent_node);
    }

    SearchResult tree_search(State initial_state){
        long start_time = System.currentTimeMillis();
        long now=start_time;
        this.frontier.add(new SearchNode(initial_state,null));
        int node_counter = 0;
        int max_space = 0;
        while (!this.frontier.is_empty() && ((now - start_time) < this.timelimit)){

            max_space = Math.max(max_space,frontier.len());
            SearchNode this_node = frontier.remove();
            node_counter +=1;
            now = System.currentTimeMillis();
            if (this.problem.is_goal(this_node.state)){
                this_node.state.Display();

                return new SearchResult(true,this_node,(now-start_time),node_counter,max_space,true);

            }
            else {
                for (int act:problem.actions(this_node.state)
                ) {

                    frontier.add(childNode(this_node,act));
                }

            }
        }
        System.gc();
        now = System.currentTimeMillis();
        return new SearchResult(false,null,(now-start_time),node_counter,max_space,true);
    }


    SearchResult DepthFirstSearch(State initial_state){

        frontier = new FrontierLIFO();
        return tree_search(initial_state);
    }

    SearchResult BreadthFirstSearch(State initial_state){
        frontier = new FrontierFIFO();
        return tree_search(initial_state);
    }


    SearchResult DepthLimitedSearch(State initial_state,int limit){
        frontier = new FrontierLIFO_DL(limit);
        SearchResult result = tree_search(initial_state);
        result.cutoff = frontier.cutoff();

        return result;
    }

    SearchResult IDS(State initial_state){
        int limit = 0;
        int nodes = 0;
        long time = 0;
        int space = 0;
        while (time< timelimit){
            SearchResult answer = DepthLimitedSearch(initial_state, limit);
            if (answer.success){
                answer.time += time;
                answer.nodes += nodes;
                answer.space += Math.max(answer.space,space);
                return answer;
            }
            else if (!answer.cutoff){
                return new SearchResult(false, null,time,nodes,space, true);
            }
            else {
                nodes += answer.nodes;
                time += answer.time;
                limit +=1;
                space = Math.max(answer.space, space);
            }
        }

        return new SearchResult(false,null,time,nodes,space,true);
    }


}

