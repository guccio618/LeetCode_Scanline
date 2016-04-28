import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Comparator;


/******************************************************************
 * (1). 使用扫描线方法按position先后顺序记录下对应node（heap）
 * (2). 使用currentMaxHeight (queue) 存放当前位置最大的高度
 * (3). 注意实时处理同一position上的高度值在currentMaxHeight里的维护状况
 *
 ******************************************************************/
//test case: [[0,2,3],[2,5,3]]两段高度相同
//  正确结果：[[0,3],[2,3],[5,0]]
//  错误结果：[[0,3],[5,0]]
public class Le_218_The_Skyline_Problem {  
	public List<int[]> getSkyline(int[][] buildings) {
        List<int[]> ans = new ArrayList<int[]>();
		if(buildings == null || buildings.length == 0){
			return ans;
		}
		
		int n = buildings.length;
		Queue<Node> heap = new PriorityQueue<Node>(1, new Comparator<Node>(){
		    public int compare(Node left, Node right){
		        if(left.position != right.position){
		            return left.position - right.position;
		        } else {
		            if(left.isStart == true && right.isStart == false){
		                return 1;
		            } else if(left.isStart == false && right.isStart == true){
		                return -1;
		            } else {
		                return 0;
		            }
		        }
		    }
		});
		
		Queue<Integer> currentMaxHeight = new PriorityQueue<Integer>(1, new Comparator<Integer>(){
		    public int compare(Integer left, Integer right){
		        return right - left;
		    }
		});
		
		for(int i = 0; i < n; i++){
		    heap.offer(new Node(buildings[i][0], buildings[i][2], true));
		    heap.offer(new Node(buildings[i][1], buildings[i][2], false));
		}
		
		while(!heap.isEmpty()){
		    Node temp = heap.poll();
		    
		    if(temp.isStart == true){
		        currentMaxHeight.offer(temp.height);
		    } else {
		        currentMaxHeight.remove(temp.height);
		    }
		    
		    while(!heap.isEmpty() && heap.peek().position == temp.position){
		        Node temp2 = heap.poll();
		        if(temp2.isStart == true){
		            currentMaxHeight.offer(temp2.height);
		        } else {
		            currentMaxHeight.remove(temp2.height);
		        }
		    }
		    
		    int[] result = new int[2];
		    result[0] = temp.position;
		    if(currentMaxHeight.isEmpty()){
		        result[1] = 0;
		    } else {
		        result[1] = currentMaxHeight.peek();
		    }
		    
		    if(ans.size() > 0 && result[1] == ans.get(ans.size() - 1)[1]){
				continue;
			}
		    
		    ans.add(result);
		}
		
		return ans;
    }
    
    class Node{
        int position, height;
        boolean isStart;
        
        public Node(int p, int h, boolean s){
            position = p;
            height = h;
            isStart = s;
        }
    }
}
