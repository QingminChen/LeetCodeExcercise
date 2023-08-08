package com.qingmin.test;




//import com.sun.java.swing.plaf.windows.WindowsTextAreaUI;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.javatuples.Tuple;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.*;
import static java.util.Arrays.copyOf;

class ListNode {
    int value;
    ListNode next;

    //    int nodeLength=1;
    ListNode() {
    }    // here are three constructors

    ListNode(int value) {
        this.value = value;
    }

    ListNode(int value, ListNode next) {
        this.value = value;
        this.next = next;
    }

}

class Node {
    public int val;
    public List<Node> neighbors;
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}

class MyCalendar {  // my approahc is correct but exceed time limited
    LinkedList<Integer> allTimes = new LinkedList<>();

    public MyCalendar() {

    }

    public boolean book(int start, int end) {//10,20
        boolean insertStatus = false;
        if(this.allTimes.isEmpty()){
            this.allTimes.add(start);
            this.allTimes.add(end);
            insertStatus=true;
        }else{
            for(int i=this.allTimes.size()-1;i>=0;i--){
                if(this.allTimes.get(i)<=start&&i%2==1){
                    if(i+1<=this.allTimes.size()-1){
                        if(this.allTimes.get(i+1)>=end){
                            this.allTimes.add(i+1,end);
                            this.allTimes.add(i+1,start);
                            insertStatus=true;
                            break;
                        }else{
                            insertStatus=false;
                        }
                    }else{
                        this.allTimes.add(start);
                        this.allTimes.add(end);
                        insertStatus=true;
                        break;
                    }
                }else if(this.allTimes.get(i)>=end&&i%2==0){
                    if(i-1>=0){
                        if(this.allTimes.get(i-1)<=start){
                            this.allTimes.add(i,end);
                            this.allTimes.add(i,start);
                            insertStatus=true;
                            break;
                        }else{
                            insertStatus=false;
                        }
                    }else{
                        this.allTimes.addFirst(end);
                        this.allTimes.addFirst(start);
                        insertStatus=true;
                        break;
                    }

                }else{
                    continue;
                }
            }
        }
       System.out.println("insertStatus:"+ String.valueOf(insertStatus));
       return insertStatus;
    }
}

class MyCalendar2 {//others' solution
    List<int[]> calendar;
    public MyCalendar2() {
        calendar = new ArrayList();
    }

    public boolean book(int start, int end) {
        for (int[] iv: calendar) {
            if (iv[0] < end && start < iv[1]) {
                return false;
            }
        }

        calendar.add(new int[]{start, end});
        return true;
    }
}

class SnakeGame {

// |0|1|2|    this is the values of order in snake
// |3|4|5|
    int width;
    int height;
    int[][] food;
    int currentRow = 0;
    int currentColumn = 0;
    int foodIndex = 0;
    int score = 0;
    Queue<Integer> snake;    //insert into the end, and pull out the element from the other side of it

    boolean[][] board;

    public SnakeGame(int width, int height, int[][] food) {
        this.width=width;
        this.height=height;
        this.food=food;
        this.snake = new LinkedList<>();
        this.board = new boolean[height][width];  // all default value is false
        this.board[0][0] = true; // Used for recording which areas are occupied
        this.snake.offer(0);
    }

    public int move(String direction) {
        if(direction.equalsIgnoreCase("U")){
            this.currentRow--;
        }else if(direction.equalsIgnoreCase("D")){
            this.currentRow++;
        }else if(direction.equalsIgnoreCase("R")){
            this.currentColumn++;
        }else if(direction.equalsIgnoreCase("L")){
            this.currentColumn--;
        }

        if(this.currentRow<0||this.currentRow>this.height-1||this.currentColumn<0||this.currentColumn>this.width-1){
            return -1;
        }else{
            if(this.food[this.foodIndex][0]==this.currentRow&&this.food[this.foodIndex][1]==this.currentColumn){
                score++;
                foodIndex++;
            }else{
                int idx = snake.poll();//pop out one element
                board[idx / width][idx % width] = false;// reset the ever occupied area to false to pretend never stepping on it before
            }
        }
        if(board[currentRow][currentColumn]) { // bite itself
            score = -1;
            return score;
        } else {
            snake.offer(currentRow * width + currentColumn);// this is the one dimensional value which stands for the order in one panel
            board[currentRow][currentColumn] = true;
        }

        return score;
    }
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */
class RandomizedSet {

    Map<Integer,Integer> maintainMap;
    private Random rand;

    public RandomizedSet() {
        this.maintainMap = new HashMap<Integer,Integer>();
        this.rand = new Random();
    }

    public boolean insert(int val) {
        if(this.maintainMap.containsKey(val)){
            return false;
        }else{
            this.maintainMap.put(val,this.maintainMap.getOrDefault(val,0)+1);
            return true;
        }
    }

    public boolean remove(int val) {
        if(this.maintainMap.containsKey(val)){
            this.maintainMap.remove(val,this.maintainMap.get(val)-1);
            return true;
        }else{
            return false;
        }
    }

    public int getRandom() { //Skip it since don't understand the requirement,o
//            int RadomKey = (int) this.maintainMap.keySet().toArray()[0];
//            this.maintainMap.remove(RadomKey,this.maintainMap.get(RadomKey)-1);
//            return RadomKey;
        int sum = 0;

        for(int i: this.maintainMap.values()){
            sum=sum+i;
        }
        int index = this.rand.nextInt(sum);
        return this.maintainMap.get(index);
    }
}

class MinStack {//my solution is not correct
    Stack<Integer> stackResult;
    int min;
    public MinStack() {
        this.stackResult = new Stack<>();
        this.min = Integer.MAX_VALUE;
    }

    public void push(int val) {
        this.stackResult.push(val);
        this.min = Math.min(min,val);
    }

    public void pop() {
        this.stackResult.pop();
    }

    public int top() {
       return this.stackResult.pop();
    }

    public int getMin() {
       return this.min;
    }
}

class MinStack2 {//others solution is not correct
    Stack<Integer> stack = new Stack<>();
    Stack<Integer> min_vals = new Stack<>();
    int min;
    public MinStack2() {
//        this.stackResult = new Stack<>();
//        this.min = Integer.MAX_VALUE;
    }

    public void push(int val) {
        if(min_vals.empty()||val<=min_vals.peek()){//top
            min_vals.push(val);
        }
        this.stack.push(val);
    }

    public void pop() {
        if(stack.peek().equals(min_vals.peek())){
            min_vals.pop();
        }
        this.stack.pop();
    }

    public int top() {
        return this.stack.peek();
    }

    public int getMin() {
        return min_vals.peek();
    }
}


public class App2 {


    //classic traceback pattern
    private static  void traceback(int idx, StringBuilder str, String digits,List<String> res,String[] tel){
        if(idx == digits.length()){
            res.add(str.toString());
            return;
        }
        String button = tel[Integer.valueOf(String.valueOf(digits.charAt(idx))) - 2];//position is fixed
        for(char ch : button.toCharArray()){
            str.append(ch);
            traceback(idx+1, str,digits,res,tel);
            str.deleteCharAt(idx);
        }

    }


    private static void findCandidateTrackBack(int[] candidates, int remaining, int start, List<List<Integer>> result, List<Integer> curr){
        if(remaining < 0) return;
        if(remaining == 0){
            result.add(new ArrayList<>(curr));
        }else{
            for(int i = start; i< candidates.length; i ++){
                curr.add(candidates[i]);
                findCandidateTrackBack(candidates, remaining - candidates[i], i,result,curr);
                curr.remove(curr.size() -1);
            }
        }
    }

    private static  int findCandidateContinuousSubarray (int idx, List<Integer> intList, int[] nums, int limit, int maxCount,int finalMaxCount){  //Unused

        if(idx == nums.length){
            finalMaxCount = maxCount;
            return finalMaxCount;
        }

        intList.add(nums[idx]);
        int[] tmpArray = intList.stream().mapToInt(i->i).toArray();
        Arrays.sort(tmpArray);
        int diff = tmpArray[tmpArray.length-1]-tmpArray[0];
        if(diff<=limit){
            if(tmpArray.length>maxCount){
                maxCount = tmpArray.length;
            }
            findCandidateContinuousSubarray(idx+1,intList,nums,limit,maxCount,finalMaxCount);//1,3

        }else{
            intList.remove(0);
            int[] tmpArray2 = intList.stream().mapToInt(i->i).toArray();
            Arrays.sort(tmpArray2);
            for(int k=tmpArray2.length-1;k>0;k--){
                int diff2 = tmpArray2[k]-tmpArray2[0];
                if(diff2<=limit) {
                    if (tmpArray2.length > maxCount) {
                        maxCount = tmpArray2.length;
                        break;
                    }
                }
            }
            findCandidateContinuousSubarray(idx+1,intList,nums,limit,maxCount,finalMaxCount);//2,4
        }

        return finalMaxCount;

    }


    public static Map<Pair<Integer, Integer>, Double> getProbability(int current_row, int min_current_row_glass_no_need_loop, int query_glass, Map<Pair<Integer, Integer>, Double> result){
        if(result.containsKey(new Pair<>(current_row,query_glass))){
            return result;
        }
        for(int i=2;i<min_current_row_glass_no_need_loop;i++){
            if(i-1==1){
                result.put(new Pair<>(current_row-1,i-1),(current_row-1)/Math.pow(2,current_row-1));
            }
            if(i==current_row-2){
                result.put(new Pair<>(current_row-1,i),(current_row-1)/Math.pow(2,current_row-1));
            }
            if(result.containsKey(new Pair<>(current_row-1,i-1))&&result.containsKey(new Pair<>(current_row-1,i))){
                result.put(new Pair<>(current_row,i),(result.get(new Pair<>(current_row-1,i-1))+result.get(new Pair<>(current_row-1,i)))/2);
            }
        }
       return result;
    }

    public static Set<Integer> getMaxProfit(int i,int n,int[] prices,int maxProfit,Set<Integer> result){
        if(i>=n-1){
            result.add(maxProfit);
            return result;
        }
        int j=i+1;
        int profit =0;
        while(j<n) {
            profit = prices[j] - prices[i];
            if(profit+maxProfit>maxProfit){//prices[j] > prices[i]
                getMaxProfit(j+1,n,prices,profit+maxProfit,result);
            }
            j++;
        }
        getMaxProfit(i+1,n,prices,maxProfit,result);

        return result;
    }

    public static boolean[] tryPosition(int j,int i, int[] nums, boolean[] status){
        if(nums[i]==0&&i!=nums.length-1){
            return status;
        }

            if(i==nums.length-1){
                status[0]=true;
                return status;
            }else if(i+j<nums.length-1&&i<nums.length-1){
                    tryPosition(1, i + j, nums, status);
                    if(status[0]!=true) {
                        while (j + 1 <= nums[i]) {
                            if (status[0] != true) {
                                j = j + 1;
                                tryPosition(j, i, nums, status);
                            }
                        }
                    }
            }else if(i+j==nums.length-1&&i<nums.length-1){
                status[0]=true;
                return status;
            }else{
                return status;
            }

        return status;
    }

    public static List<Integer> loop(List<Integer> result,int[][] matrix, Queue<Integer> rowIndex, Queue<Integer> colIndex, int curRow, int curCol){
//        int[][] matrix = {
//                {1,2,3},
//                {4,5,6},
//                {7,8,9}
//        };


//        int[][] matrix = {
//                { 1, 2, 3, 4, 5, 6},
//                { 7, 8, 9,10,11,12},
//                {13,14,15,16,17,18},
//                {19,20,21,22,23,24}
//        };

//        int[][] matrix = {
//                { 1, 2, 3, 4},
//                { 5, 6, 7, 8},
//                { 9,10,11,12},
//                {13,14,15,16},
//                {17,18,19,20},
//                {21,22,23,24},
//                {25,26,27,28},
//        };

//        int[][] matrix ={
//                {1, 2, 3, 4},
//                {5, 6, 7, 8},
//                {9,10,11,12}
//        };

        if(result.size()==matrix.length*matrix[0].length){
            return result;
        }
        int newRowIndex = -1;
        int newColIndex = -1;
        if(rowIndex.isEmpty()){
            newRowIndex = curRow+1;
        }else{
            newRowIndex = rowIndex.poll();
        }
        if(colIndex.isEmpty()){
            newColIndex = curCol;
        }else{
            newColIndex = colIndex.poll();
        }
        while(curRow<newRowIndex){
            while(curCol<newColIndex){
                result.add(matrix[curRow][curCol]);
                curCol++;
            }
            result.add(matrix[curRow][newColIndex]);
            if(curCol==newColIndex){
                curRow++;
            }

        }

        if(result.size()==matrix.length*matrix[0].length){
            return result;
        }

        if(curRow==newRowIndex&&curCol==newColIndex){
            if(rowIndex.isEmpty()){
                newRowIndex = curRow-1;
            }else{
                newRowIndex = rowIndex.poll();
            }
            if(colIndex.isEmpty()){
                newColIndex = curCol-1;//?
            }else{
                newColIndex = colIndex.poll();
            }
        }

        while((curRow>newRowIndex)){
            while(curCol>newColIndex){
                result.add(matrix[curRow][curCol]);
                curCol--;
            }
            result.add(matrix[curRow][newColIndex]);
            if(curCol==newColIndex){
                curRow--;
            }
        }

        loop(result,matrix,rowIndex,colIndex,curRow,curCol);
        return result;
    }

    public static int[][] rotateImage(int[][] matrix,int rowBegin, int rowEnd, int colBegin, int colEnd){
        Queue<Integer> squareCornerPoint = new LinkedList<>();
        LinkedList<Integer> nonSquareCornerPoint = new LinkedList<>();
        for(int i = rowBegin; i <= rowEnd; i++){
            for(int j = colBegin; j <= colEnd; j++){
                if( i == rowBegin || i == rowEnd || j == colBegin || j==colEnd){
                     if((j-i == 0)||abs(j-i)==(rowEnd-rowBegin)){
                         if(j-i == 0){
                             if(i==rowBegin){
                                 squareCornerPoint.add(matrix[i][colEnd]);
                                 matrix[i][colEnd] = matrix[i][j];
                             }else{

                             }
                         }else{
                             if(i==rowBegin){
                                 squareCornerPoint.add(matrix[rowEnd][j]);
                                 matrix[rowEnd][j] = squareCornerPoint.poll();
                             }else{
                                 matrix[rowBegin][j] = matrix[i][j];
                                 matrix[i][j] = squareCornerPoint.poll();
                             }
                         }
                         System.out.println("square corner point:"+matrix[i][j]);
                     }else{
                         if(i == rowBegin){
                             nonSquareCornerPoint.add(matrix[j][colEnd]);
                             matrix[j][colEnd]=matrix[i][j];
                         }else if(j == colBegin){
                             matrix[rowBegin][rowEnd-(i-rowBegin)] = matrix[i][j];
                         }else if(j == colEnd){
                             nonSquareCornerPoint.add(matrix[j][rowEnd-(i-rowBegin)]);
                             matrix[j][rowEnd-(i-rowBegin)] = nonSquareCornerPoint.poll();
                         }else{
                             matrix[j][colBegin]=nonSquareCornerPoint.pollLast();
                         }
                     }

                }
            }

        }
        return matrix;
    }


    public static void main(String[] args) {

        /*** 17. Letter Combinations of a Phone Number
         *

        String digits = "23";
        String[] tel = {"abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

        char test1='a';
        int test2 = test1-'0'-2;
        List<String> res = new ArrayList<>();
        if(digits == null || digits.length() == 0) {

        }

        traceback(0, new StringBuilder(),digits,res,tel);
        */



        /*** 19. Remove Nth Node From End of List
         *
         */
        // my version doesn't satisfy the requirement
//        int[] head = {1,2,3,4,5};//lenght =5
//        int n =2;
//        StringBuilder resultTmp = new StringBuilder();
//        int count=0;
//        for(int i=head.length-1;i>=0;i--){
//            count++;
//            if(count==n){
//
//            }else{
//                resultTmp.append(head[i]);
//            }
//        }
//        StringBuilder resultTmp2 = resultTmp.reverse();

        // The following solution is mine, and it works
        /**
         int n = 2;
         ListNode head =  new ListNode(1);
         head.next = new ListNode(2);
         head.next.next = new ListNode(3);
         head.next.next.next = new ListNode(4);
         head.next.next.next.next = new ListNode(5);
         ListNode cur=head;
         ListNode nullNode=null;
         int nodeLength = 0;
         while(cur!=null){
            nodeLength++;
            cur = cur.next;
         }
         if(nodeLength==1){
            head=nullNode;
         }
         ListNode cur2=head;
         for(int i=0;i<nodeLength;i++){//
             if(i==nodeLength-n-1){
                  for(int j=0;j<i;j++){
                      cur2 = cur2.next;  // at the them time, the head references is changed with this cur2 changing
                  }
                 cur2.next=cur2.next.next;
                 break;
             }else if(nodeLength-n-1==-1){
                 cur2=cur2.next;
                 head = cur2;
             }else if(nodeLength-n-1<-1){
                 head = nullNode;
             }
         }

         System.out.println("head");
         */

        /**
         * 39. Combination Sum  mine solution is correct but performance is good or not is unknown, but I don't like this solution,I wanna implement it in trackback solution
         *
        int[] candidates = {7,3,2};
        Map<Integer,Integer> candidatesMap = new HashMap<Integer,Integer>();
        for(int i=0;i<candidates.length;i++){
            candidatesMap.put(candidates[i],candidatesMap.getOrDefault(candidates[i],0)+1);
        }
        candidatesMap.put(0,0);
        int target = 18;
        Set<List<Integer>> res = new HashSet<>();

        int maxTimes = 0;
        for(int i=0;i<candidates.length;i++){

            maxTimes = target/candidates[i];

            int count=0;
            int sum = 0;
            while(sum<target||count<maxTimes){
                count++;
                if(candidatesMap.containsKey(target-candidates[i]*count)){
                    if(target-candidates[i]*count==0){
                        int[] candidate = new int[count];
                        Arrays.fill(candidate, candidates[i]);
                        Arrays.sort(candidate);
                        res.add(Arrays.stream(candidate).boxed().collect(Collectors.toList()));
                        sum=target;
                    }else{
                        int[] candidate = new int[count+1];
                        Arrays.fill(candidate, candidates[i]);
                        candidate[candidate.length-1]=target-candidates[i]*count;
                        Arrays.sort(candidate);
                        res.add(Arrays.stream(candidate).boxed().collect(Collectors.toList()));
                        sum=target;
                    }
                }else{
                    sum=candidates[i]*count;
                }

            }

        }

        List<List<Integer>> result = new ArrayList<>(res);
        System.out.println("result:"+result.toString());

        // Others' solution
        int[] candidates = {7,3,2};
        int target = 18;
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> curr = new ArrayList<>();
        Arrays.sort(candidates);
        findCandidateTrackBack(candidates, target, 0,result,curr);
        System.out.println("result:"+result.toString());
         * */

        /**
         * 49. Group Anagrams
         *
         *
        String strs[] = {"eat","tea","tan","ate","nat","bat"};
        Map<String,List<String>> result =new HashMap<>();
        for(int i=0;i< strs.length;i++){
            char[] tmpCharArray = strs[i].toCharArray();
            Arrays.sort(tmpCharArray);  // this is the hardcore part
            if(!result.containsKey(String.valueOf(tmpCharArray))){
                result.put(String.valueOf(tmpCharArray),result.getOrDefault(String.valueOf(tmpCharArray),new ArrayList<>()));
                result.get(String.valueOf(tmpCharArray)).add(strs[i]);
            }else{
                result.get(String.valueOf(tmpCharArray)).add(strs[i]);
            }
        }

        List resultList  = new ArrayList<>();
        if (result.values() instanceof List)
            resultList = (List)result.values();
        else
            resultList = new ArrayList(result.values());
        System.out.println("resultList:"+resultList);
        */

        /***
         * 56. Merge Intervals

//        int[][] nums = {{1000,2000},{990,2010},{980,2020},{970,2030},{960,2040},{950,2050},{940,2060},{930,2070},{920,2080},{910,2090},{900,2100},{890,2110},{880,2120},{870,2130},{860,2140},{850,2150},{840,2160},{830,2170},{820,2180},{810,2190},{800,2200},{790,2210},{780,2220},{770,2230},{760,2240},{750,2250},{740,2260},{730,2270},{720,2280},{710,2290},{700,2300},{690,2310},{680,2320},{670,2330},{660,2340},{650,2350},{640,2360},{630,2370},{620,2380},{610,2390},{600,2400},{590,2410},{580,2420},{570,2430},{560,2440},{550,2450},{540,2460},{530,2470},{520,2480},{510,2490},{500,2500},{490,2510},{480,2520},{470,2530},{460,2540},{450,2550},{440,2560},{430,2570},{420,2580},{410,2590},{400,2600},{390,2610},{380,2620},{370,2630},{360,2640},{350,2650},{340,2660},{330,2670},{320,2680},{310,2690},{300,2700},{290,2710},{280,2720},{270,2730},{260,2740},{250,2750},{240,2760},{230,2770},{220,2780},{210,2790},{200,2800},{190,2810},{180,2820},{170,2830},{160,2840},{150,2850},{140,2860},{130,2870},{120,2880},{110,2890},{100,2900},{90,2910},{80,2920},{70,2930},{60,2940},{50,2950},{40,2960},{30,2970},{20,2980},{10,2990}};
////        int[][] intervals = {{1,3}};
////        int[][] intervals = {{1,4},{2,4}};
//        int preEnd = nums[0][1];
////        java.util.Arrays.sort(intervals, new java.util.Comparator<int[]>() {// int[] sort in a array, two dimensions array
////            public int compare(int[] a, int[] b) {
////                return Integer.compare(a[0], b[0]);
////            }
////        });
//
//        Arrays.sort(nums, Comparator.comparingDouble(o -> o[0]));
//        List<int[]> result = new ArrayList<>();
//        int addIndex =-1;
//        for(int i=0;i<nums.length;i++){
//            if(nums[i][1]<=preEnd){
//                if(addIndex==-1){
//                    result.add(new int[]{nums[0][0], nums[i][1]});
//                    addIndex++;
//                    preEnd=result.get(addIndex)[1];
//                }else{
//                    preEnd=result.get(addIndex)[1];
//                }
//            }else if(nums[i][0]<=preEnd){
//                if(addIndex==-1){
//                    result.add(new int[]{nums[0][0], nums[i][1]});
//                    addIndex++;
//                    preEnd=result.get(addIndex)[1];
//                }else{
//                    int oldPreStarValue = result.get(addIndex)[0];
//                    result.remove(addIndex);
//                    result.add(new int[]{oldPreStarValue, nums[i][1]});
//                    preEnd=result.get(addIndex)[1];
//                }
//            }else{
//                result.add(nums[i]);
//                addIndex++;
//                preEnd=result.get(addIndex)[1];
//            }
//
//        }
//        int[][] resultArray = new int[result.size()][];
//
//        for(int i=0;i<result.size();i++){
//            resultArray[i]=result.get(i);
//        }
//        System.out.println("resultArray:"+resultArray.toString());

////       others' solution
//         int[][] nums = {{1,3}};
         int[][] nums = {{1,3},{2,6},{6,10},{11,15}};
         Arrays.sort(nums, (a, b) -> a[0] - b[0]);
         List<int[]> list = new ArrayList<>();
         int min = nums[0][0];
         int max = nums[0][1];
         for (int i = 1; i < nums.length; ++i) {
             if (nums[i][0] <= max) {
                 max = Math.max(nums[i][1], max);
             } else {
                 list.add(new int[]{min, max});
                 min = nums[i][0];
                 max = nums[i][1];
             }
         }
         list.add(new int[]{min, max});
         int[][] res = new int[list.size()][2];
         for (int i = 0; i < list.size(); ++i){
             res[i] =list.get(i);
         }
        System.out.println("res:"+res.toString());
         */

        /**
         * 133. Clone Graph  I didn't get the meaning  of this problem, skip it
         *
        //[[2,4],[1,3],[2,4],[1,3]]
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        node1.neighbors.add(node2);
        node1.neighbors.add(node4);
        node2.neighbors.add(node1);
        node3.neighbors.add(node2);
        node3.neighbors.add(node4);
        node4.neighbors.add(node1);
        node4.neighbors.add(node3);
        Node result = node1;
        if(node1==null){

        }
        if(node1.neighbors==null||node1.neighbors.size()==0){

        }

        Map<Integer,Node> nodeMap = new HashMap<Integer,Node>();
        List<Integer> visited = new ArrayList();
        Node resultPtr = result;
        int index = 0;
        System.out.println(node1.val);

        while(true){
            visited.add(node1.val);
            List<Node> neighbors = node1.neighbors;
            System.out.println(neighbors.get(0).val);
            System.out.println(neighbors.get(1).val);
            neighbors = neighbors.get(0).neighbors;
            if(neighbors == null || neighbors.size() == 0){
                break;
            }
        }
        * */

        /**
         * 253. Meeting Rooms II
         * */
        //my solution is wrong
//        int[][] intervals = {{0,30},{5,10},{15,20}};
//          int[][] intervals = {{7,10},{2,4}};
//        int[][] intervals = {{13,15},{1,13}};
//        int[][] nums = {{2,15},{36,45},{9,29},{16,23},{4,9}};
//        int numsOriginalLength = nums.length;
//
//        List< List<Integer> > numsRemoveDuplicatedList=Arrays.stream(nums).map(
//                internalArray -> Arrays.stream(internalArray).boxed().collect(Collectors.toList()
//                )).distinct().collect(Collectors.toList());
//
//        int[][] numsRemoveDuplicate = new int[numsRemoveDuplicatedList.size()][];
//        for(int i=0;i<numsRemoveDuplicatedList.size();i++){
//            numsRemoveDuplicate[i]=numsRemoveDuplicatedList.get(i).stream().mapToInt(value->value).toArray();
//        }
//
//        Arrays.sort(numsRemoveDuplicate, (a, b) -> a[1] - b[1]);
//        int preStart = numsRemoveDuplicate[0][0];
//        int preEnd = numsRemoveDuplicate[0][1];
//        int preIndex = 0;
//        int count = 1+(numsOriginalLength-numsRemoveDuplicatedList.size());
//        for(int i=1;i<numsRemoveDuplicate.length;i++){
//            if(numsRemoveDuplicate[i][0]<preEnd){
//                if(numsRemoveDuplicate[i][1]<=preEnd){
//                    preEnd = numsRemoveDuplicate[i][1];
//                    preStart = numsRemoveDuplicate[i][0];
//                    preIndex = i;
//                    count++;
//                }else{
//                    count++;
//                }
//            }else if(numsRemoveDuplicate[i][0]<preEnd && (numsRemoveDuplicate[i][1]-numsRemoveDuplicate[i][0])>(preEnd-preStart)){
//                count++;
//            }else{
//                preEnd = numsRemoveDuplicate[preIndex+1][1];
//                preStart = numsRemoveDuplicate[preIndex+1][0];
//                preIndex = preIndex+1;
//            }
//        }
        /**
        int[][] nums = {{2,15},{36,45},{9,29},{16,23},{4,9}};
        int[] start = new int[nums.length];
        int[] end = new int[nums.length];
        for (int i = 0; i < nums.length; ++i) {
            start[i] = nums[i][0];
            end[i] = nums[i][1];
        }
        Arrays.sort(start);
        Arrays.sort(end);
        int max = 0, count = 0;//count is the number of meeting is in used parallelly  max is num of maximun num has ever happened before
        int left = 0, right = 0;
        while (left < nums.length) {
            if (start[left] < end[right]) {
                left++;
                count++;
                max = Math.max(max, count);
            } else {
                right++;
                count--;
            }
        }
        System.out.println(max);
        */

        /**
         *729. My Calendar I
         *
        MyCalendar2 myCalendar2 = new MyCalendar2();//last 6,7,9
        myCalendar2.book(1, 19);
        myCalendar2.book(20, 32);// return False, It can not be booked because time 15 is already booked by another event.
        myCalendar2.book(19,21);
        */

        /**
         * 1060. Missing Element in Sorted Array

         int[] nums ={1,2,4};
         int k = 3;
         int count = 0;
         outerloop:
         for(int i=0;i< nums.length;i++){
             int tmp = nums[i];
             while(i+1<=nums.length-1){
                 if(tmp+1!=nums[i+1]){
                     count++;
                     tmp++;
                     if(count==k){
                         break outerloop;
                     }
                 }else{
                     break;
                 }
             }
             while(i+1>nums.length-1){
                 count++;
                 tmp++;
                 if(count==k){
                     break;
                 }
             }
         }
         //others‘ version
        int[] nums ={4,7,9,10};
        int k = 3;
        int left =0;
        int right = nums.length-1;
        int tm = nums[right] - nums[0] - nums.length+1; //numbers of missing element
        int result =0;
        if(tm<k){
            result = nums[right]+k - tm;//quit
        }
        while(left<right){   // not easy to understand
            int mid = left+(right-left)/2;
            if(nums[mid] - nums[0] - mid >=k){
                right = mid;
            }else {
                left = mid+1;
            }
        }
        result =nums[left-1] + k - (nums[left-1] - nums[0] - left+1);
        System.out.println("result:"+String.valueOf(result));
        */


        /**
         * 353.Design-Snake-Game
        int width = 3;
        int height = 2;
        int[][] food = {{1,2},{0,1}};
        String[] direction = {"R", "D", "R", "U", "L", "U"};

        SnakeGame snakeGame = new SnakeGame(width, height, food);
        for(int i=0; i<direction.length;i++){
            snakeGame.move(direction[i]);
        }
        */

        /**
         * 1438. Longest Continuous Subarray With Absolute Diff Less Than or Equal to Limit
         *

        int[] nums={10,1,2,4,7,2};   //time exceeded
        int limit = 5;
        List<Integer> intList = new ArrayList<>();
        int maxCount = 0;

        for(int i=0;i<nums.length;i++){
            intList.add(nums[i]);
            int[] tmpArray = intList.stream().mapToInt(a->a).toArray();
            Arrays.sort(tmpArray);
            int diff = tmpArray[tmpArray.length-1]-tmpArray[0];
            if(diff<=limit){
                if(tmpArray.length>maxCount){
                    maxCount = tmpArray.length;
                }
            }else{
                intList.remove(0);
                int[] tmpArray2 = intList.stream().mapToInt(a->a).toArray();
                Arrays.sort(tmpArray2);
                for(int k=tmpArray2.length-1;k>0;k--){
                    int diff2 = tmpArray2[k]-tmpArray2[0];
                    if(diff2<=limit) {
                        if (tmpArray2.length > maxCount) {
                            maxCount = tmpArray2.length;
                            break;
                        }
                    }
                }
            }

        }

        int[] nums={10,1,2,4,7,2};   //Others' solution
        int limit = 5;
        int maxCount = 1;
        LinkedList<Integer> min = new LinkedList<>();//stack minimun value first in last out
        LinkedList<Integer> max = new LinkedList<>();//stack maximun value first in last out
        int n = nums.length;
        int windowStartIndex = 0;// stands for the the start index of windows in nums
        for(int j = 0; j < n; j++){
            while(!min.isEmpty() && min.peekLast() > nums[j]){
                min.pollLast();
            }
            min.addLast(nums[j]);
            while(Math.abs(nums[j]- min.peekFirst()) > limit){
                min.pollFirst();
                windowStartIndex++;// index in nums of the first element appeared in min
            }

            while(!max.isEmpty() && max.peekLast() < nums[j]){
                max.pollLast(); //clean up all the elements which less than next element
            }
            max.addLast(nums[j]);
            while(Math.abs(nums[j] - max.peekFirst()) > limit){
                max.pollFirst();
                windowStartIndex++;
            }
            maxCount = Math.max(maxCount, j - windowStartIndex + 1);  // j(end index of windows in nums)-start index of windows in nums +1 =length
        }
        */

        /**
         * 723. Candy Crush
         * */

//        int[][] board={{110,5,112,113,114},{210,211,5,213,214},{310,311,3,313,314},{410,411,412,5,414},{5,1,512,3,3},{610,4,1,613,614},{710,1,2,713,714},{810,1,2,1,1},{1,1,2,2,2},{4,1,4,4,1014}};
//        int[] boardFlat =new int[board.length* board[0].length];
//        for(int i=0;i<board.length;i++){
//            for(int j=0;j<board[0].length;j++){
//                boardFlat[i*board[0].length+j]=board[i][j];
//            }
//        }
//        //j=0->(i*5+0 -> 0,5,10,15,20,25,30,35,40,45 )
//        //j=1->(i*5+1 -> 1,6,11,16,21,26,31,36,41,46 )
//        //j=2->(i*5+2 -> 2,7,12,17,22,27,32,37,42,47 )
//        //j=3->(i*5+3 -> 3,8,13,18,23,28,33,38,43,48 )
//        //j=4->(i*5+4 -> 4,9,14,19,24,29,34,39,44,49 )
//
//        List<LinkedList> columnLinkedlistList = new ArrayList<>();
//        for(int j=0;j<board[0].length;j++){
//            LinkedList<Integer> columnLinkedlist = new LinkedList<>();
//            for(int i=0;i<board.length;i++){
//                columnLinkedlist.add(i*board[0].length+j);
//            }
//            columnLinkedlistList.add(columnLinkedlist);
//        }
//
//        List<LinkedList> rowLinkedlistList = new ArrayList<>();
//        for(int i=0;i<board.length;i++){
//            LinkedList<Integer> rowLinkedlist = new LinkedList<>();
//            for(int j=0;j<board[0].length;j++){
//                rowLinkedlist.add(i*board[0].length+j);
//            }
//            rowLinkedlistList.add(rowLinkedlist);
//        }
//
//        List<LinkedList> columnIndexDuplicateList = new ArrayList<>();
//        for(int jth=0;jth<columnLinkedlistList.size();jth++){
//            LinkedList<Integer> columnIndexDuplicate = new LinkedList<>();
//            for(int ith=0;ith<columnLinkedlistList.get(0).size();ith++){
//                if(!columnIndexDuplicate.isEmpty() && boardFlat[columnIndexDuplicate.peekLast()] != boardFlat[Integer.valueOf(columnLinkedlistList.get(jth).get(ith).toString())]){
//                    if(columnIndexDuplicate.size()<3){
//                        while(!columnIndexDuplicate.isEmpty()&&columnIndexDuplicate.size()<3){
//                            columnIndexDuplicate.pollFirst();
//                        }
//                    }else{
//                        columnIndexDuplicateList.add(new LinkedList<>(columnIndexDuplicate));
//                        while(!columnIndexDuplicate.isEmpty()){
//                            columnIndexDuplicate.pollFirst();
//                        }
//                    }
//                    columnIndexDuplicate.addLast((int) columnLinkedlistList.get(jth).get(ith));
//                }else{
//                    columnIndexDuplicate.addLast((int) columnLinkedlistList.get(jth).get(ith));
//                }
//
//            }
//            if(columnIndexDuplicate.size()>=3) {
//                columnIndexDuplicateList.add(columnIndexDuplicate);
//            }
//
//        }
//
//        List<LinkedList> rowIndexDuplicateList = new ArrayList<>();
//        for(int ith=0;ith<rowLinkedlistList.size();ith++){
//            LinkedList<Integer> rowIndexDuplicate = new LinkedList<>();
//            for(int jth=0;jth<rowLinkedlistList.get(0).size();jth++){
//                if(!rowIndexDuplicate.isEmpty() && boardFlat[rowIndexDuplicate.peekLast()] != boardFlat[Integer.valueOf(rowLinkedlistList.get(ith).get(jth).toString())]){
//                    if(rowIndexDuplicate.size()<3){
//                        while(!rowIndexDuplicate.isEmpty()){
//                            rowIndexDuplicate.pollFirst();
//                        }
//                    }else{
//                        columnIndexDuplicateList.add(new LinkedList<>(rowIndexDuplicate));
//                        while(!rowIndexDuplicate.isEmpty()){
//                            rowIndexDuplicate.pollFirst();
//                        }
//                    }
//                    rowIndexDuplicate.addLast((int) rowLinkedlistList.get(ith).get(jth));
//                }else{
//                    rowIndexDuplicate.addLast((int) rowLinkedlistList.get(ith).get(jth));
//                }
//
//            }
//            if(rowIndexDuplicate.size()>=3) {
//              rowIndexDuplicateList.add(rowIndexDuplicate);
//            }
//
//        }
//
//        for(int jth=0;jth<columnIndexDuplicateList.size();jth++){
//            if(int ith=0;i<columnIndexDuplicateList.get(0).size();ith++){
//                columnLinkedlistList.get(jth).remove()
//            }
//
//        }
//
//        //i=0->(0,1,2,3,4)
//        //i=1->(5,6,7,8,9)
//        //i=2->(5,6,7,8,9)
//        //i=3->(5,6,7,8,9)
//        //i=4->(5,6,7,8,9)
//        //i=5->(5,6,7,8,9)
//        //i=6->(5,6,7,8,9)
//        //i=7->(5,6,7,8,9)
//        //i=8->(5,6,7,8,9)
//        //i=9->(5,6,7,8,9)

//        int[][] board={{110,5,112,113,114},{210,211,5,213,214},{310,311,3,313,314},{410,411,412,5,414},{5,1,512,3,3},{610,4,1,613,614},{710,1,2,713,714},{810,1,2,1,1},{1,1,2,2,2},{4,1,4,4,1014}};// really smart solution， I was thinking about  checking the nearest two element to see if it is same ,but I thought it suppose to be not efficient, so I gave up
//        final int m = board.length;
//        final int n = board[0].length;
//        boolean haveCrushes = true;
//
//        while (haveCrushes) {
//            haveCrushes = false;
//
//            for (int i = 0; i < m; ++i)
//                for (int j = 0; j < n; ++j) {
//                    final int val = Math.abs(board[i][j]);
//                    if (val == 0)
//                        continue;
//                    // Crush vertical candies
//                    if (j + 2 < n && Math.abs(board[i][j + 1]) == val && Math.abs(board[i][j + 2]) == val) {
//                        haveCrushes = true;
//                        for (int k = j; k < j + 3; ++k)
//                            board[i][k] = -val;
//                    }
//                    // Crush horizontal candies
//                    if (i + 2 < m && Math.abs(board[i + 1][j]) == val && Math.abs(board[i + 2][j]) == val) {
//                        haveCrushes = true;
//                        for (int k = i; k < i + 3; ++k)
//                            board[k][j] = -val;
//                    }
//                }
//
//            if (haveCrushes) {
//                // For each column, drop candies
//                for (int j = 0; j < n; ++j) {
//                    int nextIndex = m - 1;
//                    for (int i = m - 1; i >= 0; --i)
//                        if (board[i][j] > 0){
//                            board[nextIndex][j] = board[i][j];
//                            nextIndex--;
//                        }
//                    // Set board[0..nextIndex][j] to 0s
//                    for (int i = nextIndex; i >= 0; --i)
//                        board[i][j] = 0;
//                }
//            }
//        }
//
//
//        System.out.println("123");


        /**
         * TikTok
         * 799. Champagne(香槟酒) Tower


        int poured = 25;//my solution is wrong, since the flow speed is not always same to reach each node
        int query_row = 6;//started from 0
        int query_glass = 1;

        int max_reach_row_number=1;//started from 1
        int real_1_row_num =0;//started from 1
        while(max_reach_row_number*(max_reach_row_number+1)<poured*2){
            max_reach_row_number++;
        }
        if(max_reach_row_number*(max_reach_row_number+1)==poured*2){
            real_1_row_num=max_reach_row_number;
        }else{
            real_1_row_num=max_reach_row_number-1;
        }
        int num_node_is_1 = real_1_row_num*(real_1_row_num+1)/2;
        double probability = 0.0;
        if(query_row<=real_1_row_num-1){
            probability = 1.0;
        }else if(query_row>(real_1_row_num-1) && query_row==real_1_row_num){
            int overExceedPoured=poured-num_node_is_1;
            System.out.println("456");
            double head_tail_node_probability=1/Math.pow(2, max_reach_row_number-1);
            double head_tail_near_node_probability=(max_reach_row_number-1)/Math.pow(2, max_reach_row_number-1);
            //double middle_node_probability =(max_reach_row_number-1)/Math.pow(2, max_reach_row_number-1);
            int not_full_row_node_num = max_reach_row_number;
            int not_full_row_head_node_index=0;
            int not_full_row_head_near_node_index=1;
            int not_full_row_tail_node_index=max_reach_row_number-1;
            int not_full_row_tail_near_node_index=max_reach_row_number-2;
            Map<Pair<Integer,Integer>,Double> result= new HashMap<>();
            if(query_glass==not_full_row_head_node_index||query_glass==not_full_row_tail_node_index){
                probability= overExceedPoured*head_tail_node_probability;
                Pair<Integer,Integer> resultKey = new Pair<>(query_row,query_glass);
                result.put(resultKey,head_tail_node_probability);
            }else if(query_glass==not_full_row_head_near_node_index||query_glass==not_full_row_tail_near_node_index){
                probability= overExceedPoured*head_tail_near_node_probability;
                Pair<Integer,Integer> resultKey = new Pair<>(query_row,query_glass);
                result.put(resultKey,head_tail_near_node_probability);
            }else{
                if(query_row>=4){
                    for(int i=4;i<=query_row;i++){
                        result=getProbability(i,i-1,query_glass,result);
                    }
                }
                probability=overExceedPoured*result.get(new Pair<>(query_row,query_glass));
            }
        }else{
            probability=0.0;
        }
        System.out.println("probability:"+String.valueOf(probability));


        int poured = 25;
        int query_row = 6;//started from 0
        int query_glass = 1;
        double probability = 0;

        double[] row = {poured};// row_index=0
        for (int i = 1; i <= query_row; i++) {
            double[] nextRow = new double[i + 1];// current i how many nodes
            for (int j = 0; j < i; j++) {// loop the element for previous row, i=current_row_index=previous_row_element_num=previous_row_index_range[0,i-1], so j<i->j=i-1
                double volume = row[j];
                if (volume > 1) {//calculate each node's left and right child
                    nextRow[j] = nextRow[j]+(volume - 1) / 2;
                    nextRow[j + 1] = nextRow[j + 1]+(volume - 1) / 2;
                }
            }
            row = nextRow;
        }

        probability=Math.min(1, row[query_glass]);
        System.out.println("probability:"+String.valueOf(probability));
        */

        /**
         *137. Single Number II
         *

//        int[] nums1 = {0,1,0,1,0,1,99};
//        Map<Integer,Integer> countMap = new HashMap<>();
//        for(int i=0;i<nums1.length;i++){
//            if(countMap.containsKey(nums1[i])&&countMap.get(nums1[i])==2){
//                countMap.remove(nums1[i]);
//            }else{
//                countMap.put(nums1[i],countMap.getOrDefault(nums1[i],0)+1);
//            }
//        }
//
//        List<Integer> values = new ArrayList<Integer>(countMap.keySet());//no used
//        List<Integer> keys = new ArrayList<>(countMap.keySet());
//        System.out.println("exect one appear element:"+String.valueOf(keys.get(0)));

        //others' solution
        int[] nums1 = {0,1,0,1,0,1,99};
        int abc=100;

        int res = 0;
        for(int i = 0; i < 32; i++){//We iterate over all integers in nums once, and for each integer, we iterate over all 32 bits
            int sum = 0;
            for(int n: nums1){
                if((n >> i & 1) == 1) //>> i -> move i to right how many bits   to check each of bit is 1 or not
                    sum++;

            }
            sum %= 3;
            res += sum<<i;
        }
        System.out.println("exect one appear element:"+String.valueOf(res));
        */


        /**
         * 136. Single Number
         *

//        int[] nums2 = {2,2,1};//my solution
//        Map<Integer,Integer> countMap = new HashMap<>();
//        for(int i=0;i<nums2.length;i++){
//            if(countMap.containsKey(nums2[i])){
//                countMap.remove(nums2[i]);
//            }else{
//                countMap.put(nums2[i],countMap.getOrDefault(nums2[i],0)+1);
//            }
//        }
//
//        List<Integer> values = new ArrayList<Integer>(countMap.keySet());//no used
//        List<Integer> keys = new ArrayList<>(countMap.keySet());
//        System.out.println("exect one appear element:"+String.valueOf(keys.get(0)));

        int[] nums2 = {2,1,2};//good other solution
        int a = 0;
        for (int i : nums2) {
            a ^= i;//a=0010=2 i=0001=1
        }
        System.out.println("exect one appear element:"+String.valueOf(a));
        */

        /**
         * 80. Remove Duplicates from Sorted Array II
         *

        //my solution
//         int[] nums = {-50,-50,-49,-48,-47,-47,-47,-46,-45,-43,-42,-41,-40,-40,-40,-40,-40,-40,-39,-38,-38,-38,-38,-37,-36,-35,-34,-34,-34,-33,-32,-31,-30,-28,-27,-26,-26,-26,-25,-25,-24,-24,-24,-22,-22,-21,-21,-21,-21,-21,-20,-19,-18,-18,-18,-17,-17,-17,-17,-17,-16,-16,-15,-14,-14,-14,-13,-13,-12,-12,-10,-10,-9,-8,-8,-7,-7,-6,-5,-4,-4,-4,-3,-1,1,2,2,3,4,5,6,6,7,8,8,9,9,10,10,10,11,11,12,12,13,13,13,14,14,14,15,16,17,17,18,20,21,22,22,22,23,23,25,26,28,29,29,29,30,31,31,32,33,34,34,34,36,36,37,37,38,38,38,39,40,40,40,41,42,42,43,43,44,44,45,45,45,46,47,47,47,47,48,49,49,49,50};
//         int prev = nums[0];
//         int sum=0;
//         int minus1Count=0;
//         for(int i=1;i<nums.length;i++){
//             if((prev^nums[i])==0&&sum<1){
//                 sum=sum+1;
//             }else if((prev^nums[i])==0&&sum>=1){
//                 nums[i]=10001;
//                 sum=sum+1;
//                 minus1Count=minus1Count+1;
//             }else{
//                 sum=0;
//                 prev=nums[i];
//             }
//         }
//         int k = nums.length-minus1Count;
//         int startMinusIndex=30000;
//         for(int i=0;i< nums.length;i++){
//             if(nums[i]==10001){
//                 if(i<startMinusIndex){
//                     startMinusIndex=i;
//                 }
//             }else{
//                 if(startMinusIndex<nums.length){
//                     nums[startMinusIndex]=nums[i];
//                     nums[i]=10001;
//                     startMinusIndex++;
//                 }
//             }
//         }
//        System.out.println("k:"+String.valueOf(k));


        // others' solution
        int[] nums = {-50,-50,-49,-48,-47,-47,-47,-46,-45,-43,-42,-41,-40,-40,-40,-40,-40,-40,-39,-38,-38,-38,-38,-37,-36,-35,-34,-34,-34,-33,-32,-31,-30,-28,-27,-26,-26,-26,-25,-25,-24,-24,-24,-22,-22,-21,-21,-21,-21,-21,-20,-19,-18,-18,-18,-17,-17,-17,-17,-17,-16,-16,-15,-14,-14,-14,-13,-13,-12,-12,-10,-10,-9,-8,-8,-7,-7,-6,-5,-4,-4,-4,-3,-1,1,2,2,3,4,5,6,6,7,8,8,9,9,10,10,10,11,11,12,12,13,13,13,14,14,14,15,16,17,17,18,20,21,22,22,22,23,23,25,26,28,29,29,29,30,31,31,32,33,34,34,34,36,36,37,37,38,38,38,39,40,40,40,41,42,42,43,43,44,44,45,45,45,46,47,47,47,47,48,49,49,49,50};
        int n = nums.length;
        int l = 0;
        int r = 1;
        int count = 0;
        if (n<3){
            System.out.println("k:"+String.valueOf(n));
        }
        while(r<n){
            if (nums[l]!= nums[r]){
                count = 0;
                nums[++l] = nums[r];

            }else if (nums[l]== nums[r] && count < 1){
                count ++;
                nums[++l] = nums[r];

            }
            r++;
        }
        System.out.println("k=l+1:"+String.valueOf(l+1)); //Difficult to think out at the moment coding this algorithm
         */


        /**
         *189. Rotate Array
         *

        //my solution time limit exceed , correct , performance is not good.
//        int[] nums = {-1,-100};
//        int k=3;
//        int k_remaining=k% nums.length;
//        int maxIndex = nums.length-1;
//        int times=1;
//        LinkedList<Integer> numsLinkedList = new LinkedList<>();
//        if(nums.length!=1){
//            for(int i=0;i<nums.length;i++){
//                numsLinkedList.addLast(nums[i]);
//            }
//            LinkedList<Integer> numsLinkedListTmp = (LinkedList<Integer>) numsLinkedList.clone();
//
//            while (times<=k_remaining){
//                    numsLinkedListTmp.removeLast();
//                    times++;
//            }
//            for(int i=maxIndex; i>numsLinkedList.size()-k_remaining-1; i--){
//                    numsLinkedListTmp.addFirst(numsLinkedList.get(i));
//            }
//
//            for(int i=0;i<nums.length;i++){
//                nums[i]=numsLinkedListTmp.get(i);
//            }
//        }
//        System.out.println("nums"+String.valueOf(nums));


//        int[] nums = {1,2,3,4,5,6,7};//passed my solution
//        int k=3;
//        int k_remaining=k% nums.length;
//        int maxIndex = nums.length-1;
//        Stack<Integer> stack = new Stack<>();
//        int times=0;
//        if(nums.length!=1){
//            while(times<k_remaining){
//                stack.add(nums[nums.length-times-1]);
//                times++;
//            }
//            times=0;
//            for(int i= nums.length-k_remaining-1;i>=0;i--){
//                nums[i+k_remaining]=nums[i];
//            }
//            while(!stack.isEmpty()){
//                nums[times]=stack.pop();
//                times++;
//            }
//        }

        //others' solution
        int[] nums = {1,2,3,4,5,6,7};//passed my solution
        int k=3;
        k = k % nums.length;
        int l = 0;
        int r = nums.length-1;
        while(r > l) {//whole array reverse first
            int tmp = nums[l];
            nums[l] = nums[r];
            nums[r] = tmp;
            l = l + 1;
            r = r - 1;
        }

        l = 0;
        r = k-1;
        while (r > l){//first part reverse
            int tmp = nums[l];
            nums[l] = nums[r];
            nums[r] = tmp;
            l = l + 1;
            r = r - 1;
        }

        l = k;
        r = nums.length - 1;
        while (r > l) {//second part reverse
            int tmp = nums[l];
            nums[l] = nums[r];
            nums[r] = tmp;
            l = l + 1;
            r = r - 1;
        }
         */

        /**
         *122. Best Time to Buy and Sell Stock II
         * */

//        int[] prices = {7,6,4,3,1};
//        int i = 0;
//        int maxProfit = 0;
//        Set<Integer> result = new HashSet<>();
//        result = getMaxProfit(i,prices.length,prices,maxProfit,result);// I implement it in traceback solution but timelimit exceed
//        Collections.max(result);
//        System.out.println("maxProfict"+String.valueOf(Collections.max(result)));

        //others' solution profit = prices[i]-min; 只要后面的值比前面的值大，在卖掉之前那么久以前买的，这样总的值一直在增大，最后一定是最大的收益
//        int[] prices = {1,2,3,4,5};//greedy algorithm,partial optimized choosing
//        int profit=0;
//        for(int i=0; i<prices.length-1; i++){
//           int j=i+1;
//           if (prices[j]>prices[i]){
//              profit=profit+prices[j]-prices[i];
//           }
//        }
//        System.out.println("maxProfict"+String.valueOf(profit));


        /**
         *121. Best Time to Buy and Sell Stock
         *
        int[] prices = {7,1,5,3,6,4};
        int profit=0;
        int min = prices[0];
        for(int i=0; i<prices.length; i++){
            if (prices[i]<min){
                min=prices[i];
            }else if(prices[i]-min>profit){
                profit = prices[i]-min;
            }
        }
        System.out.println("profit"+String.valueOf(profit));
        */

        /**
         * 55. Jump Game
         *
//        int[] nums = {3,2,1,0,4};//my solution time limit exceeed
//        int i = 0;
//        int j=1;
//        boolean[] status =new boolean[1];
//        status = tryPosition(j,i,nums,status);
//        System.out.println("status"+String.valueOf(status));

        int[] nums = {5,3,1,0,4};//others' solution
        int goal_post = nums.length - 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            int jump_distance = i + nums[i];
            if (jump_distance >= goal_post) {
                goal_post = i;
            }
        }
        boolean status = (goal_post == 0) ? true : false;
        System.out.println("status"+String.valueOf(status));
        */


        /**
         * 45. Jump Game II
         * Given that n-1 index of array must be reachable
         *
        int[] nums = {1,2};//others' solution
        int goal_post = nums.length - 1;
        int jump_steps=0;
        for (int i = 0; i < nums.length-1; i++) {
            if(i<goal_post&&0<=i) {
                int jump_distance = goal_post - i;
                if (jump_distance <= nums[i]) {
                    goal_post = i;
                    jump_steps++;
                    i = -1;
                }
            }else{
                break;
            }
        }
        //boolean status = (goal_post == 0) ? true : false;  //no need any more
        System.out.println("jump_steps:"+String.valueOf(jump_steps));
        */


        /**
         * 274. H-Index

        //int[] citations = {3,0,6,1,5}; //{6,5,3,1,0}
        //int[] citations = {1,3,1};
        int[] citations = {1,7,9,4};
        Arrays.sort(citations);
        int h_index = 0;
        if(citations.length==1&&citations[0]!=0){
            h_index = 1;
        }else if(citations.length==1&&citations[0]==0){
            h_index = 0;
        }
        for(int i=0;i<citations.length;i++){
            if(i<citations.length-1-i) {
                int tmp = citations[i];
                citations[i] = citations[citations.length - 1 - i];
                citations[citations.length - 1 - i]=tmp;
            }
        }

        if(citations.length<=citations[citations.length-1]){
            h_index = citations.length;
        }else{
            for(int i=0;i<citations.length;i++){
                if(citations[i]!=0) {
                    if (i + 1 == citations[i]) {
                        h_index = citations[i];
                        break;
                    }
                    if (i + 1 > citations[i]) {
                        h_index = i;
                        break;
                    }
                }else if(citations[i]==0&&i==1){
                    h_index = 1;
                    break;
                }else if(citations[i]==0&&i==0){
                    h_index = 0;
                    break;
                }else{
                    if(i<=citations[i-1]){
                        h_index=i;
                        break;
                    }else{
                        h_index=citations[i-1];
                        break;
                    }
                }
            }
        }
        System.out.println("h_index:"+String.valueOf(h_index));
        */


        /**
         * 275. H-Index II
         *
        int[] citations = {0,1,3,5,6};
        int h_index = 0;
        if(citations.length==1&&citations[0]!=0){
            h_index = 1;
            System.out.println("h_index:"+String.valueOf(h_index));
        }else if(citations.length==1&&citations[0]==0){
            h_index = 0;
            System.out.println("h_index:"+String.valueOf(h_index));
        }

        if(citations.length<=citations[0]){
            h_index = citations.length;
        }else{
            for(int i=citations.length-1;i>=0;i--){
                if(citations[i]!=0) {
                    if (citations.length-i == citations[i]) {
                        h_index = citations[i];
                        break;
                    }
                    if (citations.length-i > citations[i]) {
                        h_index = citations.length-i-1;
                        break;
                    }
                }else if(i==citations.length-1&&citations[i]==0){
                    h_index = 0;
                    break;
                }else if(i==citations.length-2&&citations[i]==0){
                    h_index = 1;
                    break;
                }else{
                    if(citations.length-i-1<=citations[i+1]){
                        h_index=citations.length-i-1;
                        break;
                    }else{
                        h_index=citations[i+1];
                        break;
                    }
                }
            }
        }
        System.out.println("h_index:"+String.valueOf(h_index));
        */



        /**
         * 380. Insert Delete GetRandom O(1)
         *
        RandomizedSet randomizedSet=new RandomizedSet();
        */


        /**
         *238. Product of Array Except Self
         *
         int[] nums = {1,2,3,4};
         int[] prefix = new int[nums.length];
         int[] suffix = new int[nums.length];
         int[] result = new int[nums.length];
         for(int i=1;i< nums.length;i++){  //prefix[1,2,6,0]
             if(i==1){
                 prefix[i-1]=nums[i-1];
             }else{
                 prefix[i-1]=prefix[i-2]*nums[i-1];
             }
         }
         for(int i= nums.length-2;i>=0;i--){
            if(i==nums.length-2){
                suffix[i+1]=nums[i+1];
            }else{
                suffix[i+1]=suffix[i+2]*nums[i+1]; //suffix[0,24,12,4]
            }
         }
        for(int i=0;i<nums.length;i++){
            if(i==0){
                result[i]=suffix[i+1];
            }else if(i==nums.length-1){
                result[i]=prefix[i-1];
            }else{
                result[i]=prefix[i-1]*suffix[i+1];
            }
        }

        System.out.println("result:"+result);//result[24,12,8,6]



        //Others' solution, similiar with my solution implementation
         int[] nums = {1,2,3,4};
         int[] res = new int[nums.length];
         Arrays.fill(res, 1);
         int product = 1;
         for (int i = 1; i < nums.length; ++i) {
             product *= nums[i - 1];
             res[i] = product;
         }

         product = 1;
         for (int i = nums.length - 2; i >= 0; --i) {
             product *= nums[i + 1];
             res[i] *= product;
         }
        System.out.println("res:"+res);
          */

        /**
         * 134. Gas Station
         *
        int[] gas = {1,2,3,4,5};// my solution time limitation exceed
        int[] cost = {3,4,5,1,2};

        ArrayDeque<Integer> gasQueue = new ArrayDeque();
        ArrayDeque<Integer> costQueue = new ArrayDeque();
        int tank = 0;
        boolean status =  false;
        int startIndex = 0;
        for(int i=0;i< gas.length;i++){
            gasQueue.addLast(gas[i]);
            costQueue.addLast(cost[i]);
        }

        ArrayDeque gasQueueBK = gasQueue.clone();
        ArrayDeque costQueueBK = costQueue.clone();
        for(int i=0;i<gas.length;i++){
            if(status){
                tank = 0;
                startIndex =i;
                int count=1;
                gasQueueBK = gasQueue.clone();
                costQueueBK = costQueue.clone();
                while(count<=i){
                    int outGasFirst = (int) gasQueueBK.peekFirst();
                    gasQueueBK.pollFirst();
                    gasQueueBK.addLast(outGasFirst);

                    int outCostFirst = (int) costQueueBK.peekFirst();
                    costQueueBK.pollFirst();
                    costQueueBK.addLast(outCostFirst);
                    count++;
                }
            }
            while(!gasQueueBK.isEmpty()){
                tank = tank+(int) gasQueueBK.peekFirst();
                if(tank>=(int) costQueueBK.peekFirst()){
                    tank = tank-(int) costQueueBK.peekFirst();
                    costQueueBK.pollFirst();
                    gasQueueBK.pollFirst();
                    status=false;
                }else{
                    status=true;
                    startIndex=-1;
                    break;
                }
            }
            if(gasQueueBK.isEmpty()){
                if(tank>=0&&!status){
                    break;
                }
            }
        }
        System.out.println("startIndex:"+startIndex);



        //others' smart solution
        int[] gas = {3,2,3,4,5};// my solution time limitation exceed   //1-3+2-4+3-5+4-1+5-2
        int[] cost = {1,4,5,1,2};     //1-3+2-4+3-5+4-1+5-2
        int sum = 0;
        int[] dp = new int[gas.length];
        for (int i = 0; i < dp.length; ++i){
            dp[i] = gas[i] - cost[i];
        }
        for (int i : dp){
            sum += i;
        }
        if (sum < 0) { // no matter where you start ,if the sum is 0,you can make it
            System.out.println("res=-1");
        }

        int res = 0;
        int count = 0;
        for (int i = 0; i < dp.length; ++i) {
             count += dp[i];
             if (count < 0) {// at least until index i, the sum is descreasing,so we need to re-find a index start from above 0
                 count= 0;
                 res = i + 1;
             }
        }
        System.out.println("res:"+res);
         */

        /**
         * 12. Integer to Roman
         *

        //others‘ solution, I implemented it in Java
        int num = 1994;
        String[] romanSymbol = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
        int[] nums = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        StringBuilder result =  new StringBuilder();
        int remaining = 0;
        for(int i=0;i<nums.length;i++){
            int quotient = num/nums[i];
            remaining = num%nums[i];
            num = remaining;
            result.append(romanSymbol[i].repeat(quotient));
        }
        System.out.println("result.toString():"+result.toString());
        */

        /**
         * 151. Reverse Words in a String
         *
        String s = "a good   example";
        s= s.trim();
        String[] sArray = s.split(" ");
        StringBuilder result = new StringBuilder();
        int resultHaveIndex = -1;
        for(int i=0;i<=sArray.length/2;i++){
            if(i<=sArray.length-1-i){
                String tmp = sArray[i];
                sArray[i] = sArray[sArray.length-1-i];
                sArray[sArray.length-1-i] = tmp;
                if (sArray[i].length() != 0) {
                    result.append(sArray[i]+" ");
                }
                resultHaveIndex=i;
            }
        }
        for(int j=resultHaveIndex+1;j<sArray.length;j++){
            if (sArray[j].length() != 0) {
                result.append(sArray[j]+" ");
            }
        }
        System.out.println("result:"+result.toString().trim());


        //Others' solution  not a good understandable solution
        String s = "a good   example";
        StringBuilder reversed = new StringBuilder();
        int j = 0;
        for(int i = s.length()-1; i >=0; i--){
            if(i == 0 && s.charAt(i) != ' '){
                reversed.append(s.substring(i,i+j+1));
                j=0;
            }
            else if(s.charAt(i) == ' ') {
                if(j > 0){
                    reversed.append(s.substring(i+1,i+j+1));
                    reversed.append(" ");
                    j=0;
                }
                continue;
            }

            j++;
        }
        System.out.println("reversed:"+reversed.toString().trim());
        */

        /**
         * 6. Zigzag Conversion
         *

        //String s = "PAYPALISHIRING";   //my solution is resolving this issue in vertical direction
        String s = "P";
        int numRows = 1;
        int groupNum = 0;
        if(numRows==1){
            System.out.println("result:"+s);
        }
        int twoColumnEleNum = 2*numRows-2;
        int groupIndex = 0;
        int remaining = s.length()%twoColumnEleNum;
        if(remaining==0){
            groupNum=s.length()/twoColumnEleNum;
        }else{
            groupNum=s.length()/twoColumnEleNum+1;
        }
        Map<Integer,LinkedList<Character>> rowIndexMap = new HashMap<>();
        while(groupIndex<groupNum){
            int loopIndex = 0;
            int bottomRowIndex = (groupIndex*twoColumnEleNum+(groupIndex+1)*twoColumnEleNum)/2;
            for(int i=groupIndex*twoColumnEleNum;i<min((groupIndex+1)*twoColumnEleNum,s.length());i++){
                if(i<bottomRowIndex){
                        LinkedList<Character> tmp = rowIndexMap.getOrDefault(loopIndex,new LinkedList<>());
                        tmp.add(s.charAt(i));
                        rowIndexMap.put(loopIndex,tmp);
                        loopIndex++;
                }else if(i==bottomRowIndex){
                    LinkedList<Character> tmp = rowIndexMap.getOrDefault(loopIndex,new LinkedList<>());
                    tmp.add(s.charAt(i));
                    rowIndexMap.put(loopIndex,tmp);
                }else{
                    loopIndex=loopIndex-1;
                    LinkedList<Character> tmp = rowIndexMap.getOrDefault(loopIndex,new LinkedList<>());
                    tmp.add(s.charAt(i));
                    rowIndexMap.put(loopIndex,tmp);
                }
            }
            groupIndex++;
        }
        LinkedList<Character> resultList = new LinkedList<>();
        rowIndexMap.keySet().stream().sorted();
        for(int rowIndex:rowIndexMap.keySet()){
            resultList.addAll(rowIndexMap.get(rowIndex));
        }
        String result = resultList.stream().map(Object::toString).collect(Collectors.joining(""));
        System.out.println("result:"+result);

        //others' solution
        String s = "PAYPALISHIRING"; //others' solution is resolving this issue in horizontal direction,So smart
        int numRows = 4;
        if (numRows == 1) {
            System.out.println("result:"+s);
        }

        StringBuilder answer = new StringBuilder();
        int n = s.length();
        int charsInSection = 2 * (numRows - 1);//same with mine

        for (int currRow = 0; currRow < numRows; ++currRow) {
            int index = currRow;

            while (index < n) {
                answer.append(s.charAt(index));

                // If currRow is not the first or last row
                // then we have to add one more character of current section.
                if (currRow != 0 && currRow != numRows - 1) {
                    int charsInBetween = charsInSection - 2 * currRow;//find current lag between two elements in the current row
                    int secondIndex = index + charsInBetween;

                    if (secondIndex < n) {
                        answer.append(s.charAt(secondIndex));
                    }
                }
                // Jump to same row's first character of next section.
                index += charsInSection;
            }
        }

        System.out.println("result:"+answer.toString());
        */

        /**
         * 167. Two Sum II - Input Array Is Sorted (it's a 1-indexed array)
         *
//        int[] numbers = {-250,-200,-100,-25,7,11,15,34,40};
//        int[] numbers = {2,7,11,15};
//        int[] numbers = {2,3,3,5};
        int[] numbers = {-1,0}; // Others' solution is same with mine
        int target = -1;
        int[] result =  new int[2];
        int right  = numbers.length-1;
        int left = 0;
        while(left<right){
                if(numbers[left]+numbers[right]<target){
                    left++;
                    continue;
                }else if(numbers[left]+numbers[right]==target){
                    result[0] = left+1;
                    result[1] = right+1;
                    System.out.println("result:"+result);
                    break;
                }else{
                    right--;
                    continue;
                }

        }
        result = new int[]{-1,-1};
        System.out.println("result:"+result);
         */

        /**
         * 11. Container With Most Water
         *
        int[] height = {1,8,6,2,5,4,8,3,7};
//        int[] height = {1,1};//time limit exceed
        int xMax = height.length-1;
        int maxArea = 0;
        for(int i=xMax;i>0;i--){
            int j = 0;
            while(j+i<height.length){
                int heightTmp = min(height[j],height[j+i]);
                maxArea = max(maxArea,i*heightTmp);
                j++;
            }
        }
        System.out.println("maxArea:"+maxArea);


        int[] height = {1,8,6,2,60,59,8,3,7};// this solution is same with others and efficiency
//        int[] height = {1,1};
        int l = 0;
        int r = height.length-1;
        int maxArea = 0;
        while(l<r){
            int x = r-l;
            int y = min(height[l],height[r]);
            maxArea = max(maxArea,x*y);
            if(height[l]<height[r]){
                l++;
            }else if(height[l]>height[r]){
                r--;
            }else{
                r--;// whichever is okay  r-- or l++
            }
        }
        System.out.println("maxArea:"+maxArea);
        */

        /**
         * 15. 3Sum
         *
         int[] nums = {0,0,0};//my solution time limited exceed
         Arrays.sort(nums);
         int minAboveZeroIndex = -1;
         int maxBelowZeroIndex = -1;
         int maxNegative = -100000;
         LinkedList<Integer> zeroIndex = new LinkedList<>();
         Set<List<Integer>> resultSet = new HashSet<>();
         List<List<Integer>> result = new ArrayList<>();
         for(int i=0;i<nums.length;i++){
             if(nums[i]>0){
                 minAboveZeroIndex=0;
                 break;
             }else if(nums[i]<0){
                 if(nums[i]>=maxNegative){
                     maxNegative = nums[i];
                     maxBelowZeroIndex =i;
                 }
             }else{
                 zeroIndex.addLast(i);
             }
         }
         if((minAboveZeroIndex==-1||maxBelowZeroIndex==-1)&&zeroIndex.size()!= nums.length){
             System.out.println("0");
         }
         int l = 0;
         int r = nums.length-1;
         while(l<r-1){
             int gap = nums[r]+nums[l];
             if(gap<0){
                int i =  minAboveZeroIndex;
                while(gap+nums[i]<0&&i+1<r){
                    i++;
                }
                if(gap+nums[i]==0){
                    List<Integer> tmp = new ArrayList<>();
                    tmp.add(nums[r]);
                    tmp.add(nums[l]);
                    tmp.add(nums[i]);
                    resultSet.add(tmp);
                    l++;
                    continue;
                }else if(gap+nums[i]>0){
                    //???
                    l++;
                    continue;
                }else{
                    l++;
                    continue;
                }
             }else if(gap==0){
                 if(zeroIndex.size()!=0){
                     List<Integer> tmp = new ArrayList<>();
                     tmp.add(nums[l]);
                     tmp.add(0);
                     tmp.add(nums[r]);
                     resultSet.add(tmp);
                     l++;
                 }
             }else{
                 int i =  maxBelowZeroIndex;
                 while(gap+nums[i]>0&&i-1>l){
                     i++;
                 }
                 if(gap+nums[i]==0){List<Integer> tmp = new ArrayList<>();
                     tmp.add(nums[l]);
                     tmp.add(nums[i]);
                     tmp.add(nums[r]);
                     resultSet.add(tmp);
                     r--;
                     continue;
                 }else if(gap+nums[i]>0){
                     //???
                     r--;
                     continue;
                 }else{
                     r--;
                     continue;
                 }
             }
         }

         for(List eList : resultSet){
             result.add(eList);
         }
         System.out.println("result:"+result);


        //int[] nums = {-1,0,1,2,-1,-4};//{-4,-1,-1,0,1,2} //time limited exceed
        int[] nums = {0,0,0};//others' solution
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for(int i=0;i<nums.length;i++){
            if((i!=0)&&(nums[i]==nums[i-1])){
                continue;
            }else{
                int l = i+1;
                int r = nums.length-1;
                while(l<r){
                    if(nums[i]+nums[l]+nums[r]==0){
                        List<Integer> groupEle = new ArrayList<>();
                        groupEle.add(nums[i]);
                        groupEle.add(nums[l]);
                        groupEle.add(nums[r]);
                        result.add(groupEle);
                        l++;
                        while(l<r&&nums[l]==nums[l-1]){
                            l++;
                        }
                    }else if(nums[i]+nums[l]+nums[r]<0){
                        l++;
                    }else{
                        r--;
                    }
                }
            }
        }
        System.out.println("result:"+result);
         */

        /**
         * 209. Minimum Size Subarray Sum
         *
        int target = 7;//my solution is Time Limit Exceeded
        //int[] nums = {1,1,1,1,1,1,1,1};
        int[] nums = {2,3,1,2,4,3};
        int[] preSum = new int[nums.length];
        int[] sufSum = new int[nums.length];
        int totalSum = Arrays.stream(nums).sum();
        int minLength = nums.length;
        if(totalSum<target){
            minLength = 0;
            System.out.println("minLength:"+minLength);
        }
        for(int i=0;i< nums.length;i++){
            if(i==0){
                preSum[i]=0;
                sufSum[i]=totalSum-nums[i];
            }else if(i== nums.length-1){
                preSum[i]=totalSum-nums[i];
                sufSum[i]=0;
            }else{
                preSum[i]=preSum[i-1]+nums[i-1];
                sufSum[i]=sufSum[i-1]-nums[i];
            }
        }
        for(int i=nums.length-1;i>=0;i--){
             int j = 0;
             while(j+i<nums.length){//0,5; 0,4/1,5
                 int tmpSum = totalSum-preSum[j]-sufSum[j+i];
                 if(tmpSum>=target){
                     minLength = min(minLength,i+1);
                 }
                 j++;
             }
        }
        System.out.println("minLength:"+minLength);


        int target = 7;//others' thoughts ,I implemented this solution is 6ms not efficiency
        //int[] nums = {1,1,1,1,1,1,1,1};
        int[] nums = {2,3,1,2,4,3};
        int tmpTotal = 0;
        int l = 0;
        int r = 0;
        int preR = -1;
        int totalSum = Arrays.stream(nums).sum();
        int minLength = Integer.MAX_VALUE;
        if(totalSum<target){
            minLength = 0;
            System.out.println("minLength:"+minLength);
        }
        while(l<=r&&r<nums.length){
            if(tmpTotal>=target){
                minLength = min(minLength,r-l+1);
                tmpTotal = tmpTotal-nums[l];
                l++;
                preR = r;
            }else{
                if(r==preR){
                    r++;
                    if(r==nums.length){
                        break;
                    }
                }
                tmpTotal = tmpTotal+nums[r];
                if(tmpTotal>=target){
                    minLength = min(minLength,r-l+1);
                    tmpTotal = tmpTotal-nums[l];
                    l++;
                    preR = r;
                    continue;
                }
                preR = r;
                r++;
            }
        }
        System.out.println("minLength:");


        int target = 7;//others' solution,this solution is 9ms not efficiency,either
        int[] nums = {2,3,1,2,4,3};
        int tmpTotal = 0;
        int l = 0;
        int r = 0;
        int totalSum = Arrays.stream(nums).sum();
        int minLength = Integer.MAX_VALUE;
        if(totalSum<target){
            minLength = 0;
            System.out.println("minLength:"+minLength);
        }
        while(r< nums.length){
            tmpTotal = tmpTotal+nums[r];
            while(target<=tmpTotal){
                minLength = min(minLength,r-l+1);
                tmpTotal=tmpTotal-nums[l];
                l++;
            }
            r++;
        }
        System.out.println("minLength:");
        */

        /**
         *【Again】3. Longest Substring Without Repeating Characters
         *
       String s = "abcabcbb";
//        String s = "abba";
        int result = 0;
        int l = 0;
        int r = 0;
        Map<Character,Integer> cMapIndex = new HashMap<>();
        while(r<s.length()){
            if(!cMapIndex.containsKey(s.charAt(r))||(cMapIndex.containsKey(s.charAt(r))&&cMapIndex.get(s.charAt(r))<l)){
                cMapIndex.put(s.charAt(r),r);
            }
            if(cMapIndex.containsKey(s.charAt(r))&&cMapIndex.get(s.charAt(r))<r){
                l = cMapIndex.get(s.charAt(r))+1;
                cMapIndex.put(s.charAt(r),r);
            }
            result = max(result,r-l+1);
            r++;
        }


        //others' solution
        String s = "abcabcbb";
//        String s = "abba";
        int l = 0;
        int r = 0;
        int result = 0;
        HashMap<Character, Integer> hitMap = new HashMap<>();   //Char -> First time appear index
        while(r<s.length()){
            char ch = s.charAt(r);
            if (hitMap.containsKey(ch) && hitMap.get(ch) >= l) {
                l = hitMap.get(ch) + 1;
            }
            hitMap.put(ch, r);
            result = Math.max(result, r - l + 1);
            if (l + result >= s.length()) {//no need to loop since there is no enough chars to make up a subsrting whose length is larger than current maxLength
                break;
            }
            r++;
        }
        System.out.println("result:"+String.valueOf(result));
        */


        /**
         *
         * 36. Valid Sudoku


        Set<String> test = new HashSet<>();// I implemented in what I thought is correct,but the standard answer seems not correct ,anyway skip it
        test.add("123");
        test.add("456");
        test.remove("123");
        test.remove("789");
        System.out.println(test.add("123"));


//        char[][] board = {// i think my solution is correct ,but the standard answer of example is wrong
//                {'5','3','.','.','7','.','.','.','.'},//0
//                {'6','.','.','1','9','5','.','.','.'},//1
//                {'.','9','8','.','.','.','.','6','.'},//2
//                {'8','.','.','.','6','.','.','.','3'},//3
//                {'4','.','.','8','.','3','.','.','1'},//4
//                {'7','.','.','.','2','.','.','.','6'},//5
//                {'.','6','.','.','.','.','2','8','.'},//6
//                {'.','.','.','4','1','9','.','.','5'},//7
//                {'.','.','.','.','8','.','.','7','9'} //8
//                //0 , 1 , 2 , 3 , 4 , 5 , 6 , 7 , 8
//        };

//        char[][] board = {
//                {'8','3','.','.','7','.','.','.','.'},
//                {'6','.','.','1','9','5','.','.','.'},
//                {'.','9','8','.','.','.','.','6','.'},
//                {'8','.','.','.','6','.','.','.','3'},
//                {'4','.','.','8','.','3','.','.','1'},
//                {'7','.','.','.','2','.','.','.','6'},
//                {'.','6','.','.','.','.','2','8','.'},
//                {'.','.','.','4','1','9','.','.','5'},
//                {'.','.','.','.','8','.','.','7','9'}
//        };


//        char[][] board = {
//                {'7','.','.','.','4','.','.','.','.'},
//                {'.','.','.','8','6','5','.','.','.'},
//                {'.','1','.','2','.','.','.','.','.'},
//                {'.','.','.','.','.','9','.','.','.'},
//                {'.','.','.','.','5','.','5','.','.'},
//                {'.','.','.','.','.','.','.','.','.'},
//                {'.','.','.','.','.','.','2','.','.'},
//                {'.','.','.','.','.','.','.','.','.'},
//                {'.','.','.','.','.','.','.','.','.'}
//        };

//        char[][] board = {
//                {'.', '.', '.', '.', '5', '.', '.', '1', '.'},
//                {'.', '4', '.', '3', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '3', '.', '.', '1'},
//                {'8', '.', '.', '.', '.', '.', '.', '2', '.'},
//                {'.', '.', '2', '.', '7', '.', '.', '.', '.'},
//                {'.', '1', '5', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '2', '.', '.', '.'},
//                {'.', '2', '.', '9', '.', '.', '.', '.', '.'},
//                {'.', '.', '4', '.', '.', '.', '.', '.', '.'}
//        };

        char[][] board = {
                {'5','3','.','.','7','.','.','.','.'},
                {'6','.','.','1','9','5','.','.','.'},
                {'.','9','8','.','.','.','.','6','.'},
                {'8','.','.','.','6','.','.','.','3'},
                {'4','.','.','8','.','3','.','.','1'},
                {'7','.','.','.','2','.','.','.','6'},
                {'.','6','.','.','.','.','2','8','.'},
                {'.','.','.','4','1','9','.','.','5'},
                {'.','.','.','.','8','.','.','7','9'}
        };

        int rowNum = board.length;
        int columnNum = board[0].length;
        Map<Integer,Set<String>> colMap = new HashMap<>();
        Set<String> rowSet = new HashSet<>();
        Set<String> subBox33Set = new HashSet<>();
        Map<Integer,Set<String>> subBox33Group = new HashMap<>();
        int groupId = 1;
        int r = 0;
        int loopTimes = 3;
        List<String> specifiedCol_rowStr_list = new ArrayList<>();
        outerLoop:
        for(int i=0;i<rowNum;i++){
            System.out.println("i:"+i);
            rowSet = new HashSet<>();
            for(int j=0;j<columnNum;j++){
                System.out.println("j:"+j);
                subBox33Set = subBox33Group.getOrDefault(groupId,new HashSet<>());
                String cStr = String.valueOf(board[i][j]);
                if(!cStr.equalsIgnoreCase(".")){
                    Set<String> colSet =  colMap.getOrDefault(j,new HashSet<>());
                    if(!colSet.add(cStr)){
                        break outerLoop;
                    }
                    colMap.put(j,colSet);
                    if(!rowSet.add(cStr)){
                        break outerLoop;
                    }
                    if(i%3==0&&j%3==0){
                        if(!String.valueOf(board[i][j+1]).equalsIgnoreCase(".")){
                            if(!subBox33Set.add(String.valueOf(board[i][j+1]))){
                                break outerLoop;
                            }
                        }
                        if(!String.valueOf(board[i][j+2]).equalsIgnoreCase(".")){
                            if(!subBox33Set.add(String.valueOf(board[i][j+2]))){
                                break outerLoop;
                            }
                        }
                        if(!String.valueOf(board[i+1][j]).equalsIgnoreCase(".")){
                            if(!subBox33Set.add(String.valueOf(board[i+1][j]))){
                                break outerLoop;
                            }
                        }
                        if(!String.valueOf(board[i+1][j+1]).equalsIgnoreCase(".")){
                            if(!subBox33Set.add(String.valueOf(board[i+1][j+1]))){
                                break outerLoop;
                            }
                        }
                        if(!String.valueOf(board[i+1][j+2]).equalsIgnoreCase(".")){
                            if(!subBox33Set.add(String.valueOf(board[i+1][j+2]))){
                                break outerLoop;
                            }
                        }
                        if(!String.valueOf(board[i+2][j]).equalsIgnoreCase(".")){
                            if(!subBox33Set.add(String.valueOf(board[i+2][j]))){
                                break outerLoop;
                            }
                        }
                        if(!String.valueOf(board[i+2][j+1]).equalsIgnoreCase(".")){
                            if(!subBox33Set.add(String.valueOf(board[i+2][j+1]))){
                                break outerLoop;
                            }
                        }
                        if(!String.valueOf(board[i+2][j+2]).equalsIgnoreCase(".")){
                            if(!subBox33Set.add(String.valueOf(board[i+2][j+2]))){
                                break outerLoop;
                            }
                        }

                    }

                    if(!subBox33Set.add(cStr)){
                            break outerLoop;
                    }

                    if((j%3==0)||(j%3==1)){
                        subBox33Set.remove(String.valueOf(board[i][j + 1]));
                        subBox33Group.put(groupId,subBox33Set);
                    }else{
                        if((i%3==0)||(i%3==1)){
                            subBox33Set.remove(String.valueOf(board[i+1][j-2]));
                        }
                        subBox33Group.put(groupId,subBox33Set);
                        if(j%9==8&&(i%3==0||i%3==1)){
                            groupId=groupId-2;
                        }else{
                            groupId++;
                        }
                    }

                }else{
                    if(i%3==0&&j%3==0){
                        if(!String.valueOf(board[i][j+1]).equalsIgnoreCase(".")){
                            if(!subBox33Set.add(String.valueOf(board[i][j+1]))){
                                break outerLoop;
                            }
                        }
                        if(!String.valueOf(board[i][j+2]).equalsIgnoreCase(".")){
                            if(!subBox33Set.add(String.valueOf(board[i][j+2]))){
                                break outerLoop;
                            }
                        }
                        if(!String.valueOf(board[i+1][j]).equalsIgnoreCase(".")){
                            if(!subBox33Set.add(String.valueOf(board[i+1][j]))){
                                break outerLoop;
                            }
                        }
                        if(!String.valueOf(board[i+1][j+1]).equalsIgnoreCase(".")){
                            if(!subBox33Set.add(String.valueOf(board[i+1][j+1]))){
                                break outerLoop;
                            }
                        }
                        if(!String.valueOf(board[i+1][j+2]).equalsIgnoreCase(".")){
                            if(!subBox33Set.add(String.valueOf(board[i+1][j+2]))){
                                break outerLoop;
                            }
                        }
                        if(!String.valueOf(board[i+2][j]).equalsIgnoreCase(".")){
                            if(!subBox33Set.add(String.valueOf(board[i+2][j]))){
                                break outerLoop;
                            }
                        }
                        if(!String.valueOf(board[i+2][j+1]).equalsIgnoreCase(".")){
                            if(!subBox33Set.add(String.valueOf(board[i+2][j+1]))){
                                break outerLoop;
                            }
                        }
                        if(!String.valueOf(board[i+2][j+2]).equalsIgnoreCase(".")){
                            if(!subBox33Set.add(String.valueOf(board[i+2][j+2]))){
                                break outerLoop;
                            }
                        }
                    }
                    if((j%3==0)||(j%3==1)){
                        subBox33Set.remove(String.valueOf(board[i][j + 1]));
                        subBox33Group.put(groupId,subBox33Set);
                    }else{
                        if((i%3==0)||(i%3==1)){
                            subBox33Set.remove(String.valueOf(board[i+1][j-2]));
                        }
                        subBox33Group.put(groupId,subBox33Set);
                        if(j%9==8&&(i%3==0||i%3==1)){
                            groupId=groupId-2;
                        }else{
                            groupId++;
                        }
                    }
                }

            }
        }


        //Holly shit solution, bloody smart solution
        char[][] board = {
                {'5','3','.','.','7','.','.','.','.'},
                {'6','.','.','1','9','5','.','.','.'},
                {'.','9','8','.','.','.','.','6','.'},
                {'8','.','.','.','6','.','.','.','3'},
                {'4','.','.','8','.','3','.','.','1'},
                {'7','.','.','.','2','.','.','.','6'},
                {'.','6','.','.','.','.','2','8','.'},
                {'.','.','.','4','1','9','.','.','5'},
                {'.','.','.','.','8','.','.','7','9'}
        };

        int rowNum = board.length;// others' solution, the execution time is similar ,there is no too much improvement
        int columnNum = board[0].length;
        Set<String> tmpSet = new HashSet<>();
        int groupId = 1;
        outerLoop:
        for(int i = 0; i < rowNum; i++){
            System.out.println("i:"+i);
            for(int j = 0; j < columnNum; j++){
                System.out.println("j:"+j);
                char cTmp =  board[i][j];
                if(cTmp!='.') {
                    if (!tmpSet.add("R" + i + cTmp) || !tmpSet.add("C" + j + cTmp) || !tmpSet.add("BOX" + groupId + cTmp)) {
                        break outerLoop;
                    }
                }
                if(j%3==2){
                    if((j%9==8)&&(i%3!=2)){
                        groupId=groupId-2;
                    }else{
                        groupId++;
                    }
                }
            }
        }
         */

        /**
         * 54. Spiral Matrix
         * *
//         int[][] matrix = {
//                 {1,2,3},
//                 {4,5,6},
//                 {7,8,9}
//         };
//        int[][] matrix = {
//                 { 1, 2, 3, 4, 5, 6},
//                 { 7, 8, 9,10,11,12},
//                 {13,14,15,16,17,18},
//                 {19,20,21,22,23,24}
//         };

//                int[][] matrix = {
//                 { 1, 2, 3, 4},
//                 { 5, 6, 7, 8},
//                 { 9,10,11,12},
//                 {13,14,15,16},
//                 {17,18,19,20},
//                 {21,22,23,24},
//                 {25,26,27,28},
//         };


        int[][] matrix ={
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9,10,11,12}
        };


         int rowNum = matrix.length;
         int colNum = matrix[0].length;
         Queue<Integer> rowIndex = new LinkedList<>();//0,8,1,7,2,6,3,5,4
         Queue<Integer> colIndex = new LinkedList<>();//0,8,1,7,2,6,3,5,4

//         rowIndex.add(2);  //3*3
//         rowIndex.add(1);
//         colIndex.add(2);
//         colIndex.add(0);
//         colIndex.add(1);



//         rowIndex.add(3); //4*6
//         rowIndex.add(1);
//         rowIndex.add(2);
//         colIndex.add(5);
//         colIndex.add(0);
//         colIndex.add(4);
//         colIndex.add(1);

//        rowIndex.add(6);// 7*4 core, how to generate the index for both row and column queue
//        rowIndex.add(1);
//        rowIndex.add(5);
//        rowIndex.add(2);
//        colIndex.add(3);
//        colIndex.add(0);
//        colIndex.add(2);
//        colIndex.add(1);

         rowIndex.add(2);//core
         rowIndex.add(1);
         colIndex.add(3);
         colIndex.add(0);
         colIndex.add(2);

         int curRow = 0;
         int curCol = 0;
         List<Integer> result = new ArrayList<>();

         loop(result,matrix,rowIndex,colIndex,curRow,curCol);
         */


         /**
          * 48. Rotate Image
          *
//         int[][] matrix = {
//                 {1,2,3},
//                 {4,5,6},
//                 {7,8,9}
//         };

        int[][] matrix = {
                { 5, 1, 9,11},
                { 2, 4, 8,10},
                {13, 3, 6, 7},
                {15,14,12,16}
        };


         int layerNum = matrix.length/2;
         int i = 0;
         int rowBegin = 0;
         int rowEnd = matrix.length-1;
         int colBegin = 0;
         int colEnd = matrix[0].length-1;
         while(i<layerNum){
             rotateImage(matrix, rowBegin+i, rowEnd-i, colBegin+i, colEnd-i);
             i++;
         }
         System.out.println("matrix:"+String.valueOf(matrix));


         // others‘ solution
        int[][] matrix = {
                { 5, 1, 9,11},
                { 2, 4, 8,10},
                {13, 3, 6, 7},
                {15,14,12,16}
        };
        for(int i=0;i<matrix.length;i++){
            for(int j=i;j<matrix[0].length;j++){
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length/2;j++){
                 int tmp = matrix[i][j];
                 matrix[i][j] = matrix[i][matrix.length-j-1];
                matrix[i][matrix.length-j-1] = tmp;
            }
        }
        System.out.println("matrix:"+String.valueOf(matrix));
        */


        /**
         * 73. Set Matrix Zeroes
         *

        int[][] matrix = {
                {1,1,1},
                {1,0,1},
                {1,1,1}
        };

//        int[][] matrix = {
//                {0,1,2,0},
//                {3,4,5,2},
//                {1,3,1,5}
//        };
        // I did think of using copy of matrix, but is it increase the space complexity which not satisfied the requirements
        Set<Integer> zero_row_index_set = new HashSet<>();//my solution is same with others
        Set<Integer> zero_col_index_set = new HashSet<>();
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
                if(matrix[i][j]==0){
                    zero_row_index_set.add(i);
                    zero_col_index_set.add(j);
                }
            }
        }
        for(int zero_row_index : zero_row_index_set){
            for(int j=0; j<matrix[0].length;j++){
                matrix[zero_row_index][j] = 0;
            }
        }
        for(int zero_col_index : zero_col_index_set){
            for(int i=0; i<matrix.length;i++){
                matrix[i][zero_col_index] = 0;
            }
        }

        //others' solution
//        int[][] matrix = {
//                {1,1,1},
//                {1,0,1},
//                {1,1,1}
//        };
                int[][] matrix = {
                {0,1,2,0},
                {3,4,5,2},
                {1,3,1,5}};
        int[][] matrix_copy = new int[matrix.length][matrix[0].length];
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
                matrix_copy[i][j]=matrix[i][j];
            }
        }
        boolean row_set_status = false;
        for(int i=0;i<matrix_copy.length;i++){
            row_set_status = false;
            for(int j=0;j<matrix_copy[0].length;j++){
                if(matrix_copy[i][j]==0){
                    if(!row_set_status){
                        for (int m = 0; m < matrix[0].length; m++) {
                            matrix[i][m] = 0;
                            row_set_status = true;
                        }
                    }
                    for(int n=0; n<matrix.length;n++){
                        matrix[n][j] = 0;
                    }
                }
            }
        }*/

        /**
         * 289. Game of Life
         *
//        int[][] board = {
//                {0,1,0},
//                {0,0,1},
//                {1,1,1},
//                {0,0,0}
//        };

        int[][] board ={{1,0,1,0,1,1,0,0,0,0,1,1,1,0,1,1,0,0,1,0,0},//0
                {1,1,0,1,1,1,0,0,1,1,1,1,1,0,0,1,1,0,1,0,1},//1
                {0,1,1,0,0,1,0,1,0,1,0,1,0,1,1,1,0,0,0,0,1},//2
                {1,0,1,1,0,0,0,1,1,1,0,1,1,0,0,1,0,1,1,0,0},//3
                {1,1,0,1,1,0,1,1,0,1,1,1,1,0,0,0,0,0,0,1,1},//4
                {1,1,1,1,1,1,1,0,0,1,0,1,0,0,1,0,1,1,1,1,0},//5
                {0,1,0,1,1,1,1,1,0,0,1,1,0,0,0,1,0,1,1,1,0},//6
                {0,0,1,0,0,1,1,1,0,1,0,1,0,0,1,1,1,0,1,0,0},//7
                {0,0,1,0,1,1,1,1,1,0,0,1,1,1,0,0,0,0,1,1,1},//8
                {0,0,0,0,0,0,0,1,0,1,1,1,1,0,0,0,1,0,1,0,1},//9
                {0,0,0,0,1,1,1,0,0,0,1,1,1,0,0,1,1,0,1,1,1},//10
                {0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},//11
                {0,0,0,0,1,1,0,0,0,1,1,1,1,0,0,0,1,0,1,0,0},//12
                {1,1,1,0,1,1,1,0,1,1,0,1,1,1,1,1,0,1,0,1,1},//13
                {1,1,0,0,0,1,0,1,0,1,0,1,1,1,1,0,1,1,0,1,1},//14
                {0,0,1,1,1,1,0,0,1,1,1,1,0,1,0,1,0,1,0,1,0},//15
                {0,0,0,0,0,1,0,0,0,1,1,1,0,1,0,0,1,0,0,0,1},//16
                {0,0,1,0,1,0,1,0,1,1,0,1,1,0,1,0,0,1,1,0,1},//17
                {1,0,1,1,0,1,1,0,0,1,0,1,1,0,1,1,0,0,1,0,1},//18
                {1,0,1,1,0,1,0,0,1,0,0,1,1,0,1,1,1,0,1,1,0},//19
                {1,1,1,1,0,1,1,1,0,0,1,1,1,1,1,1,1,0,0,1,1}};//20
        Map<String,Integer> gridMap = new HashMap<>();
        Map<int[],Integer> changeResult = new HashMap<>();
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                if(j+1<board[0].length) {
                    gridMap.put(String.valueOf(i) + "_" + String.valueOf(j) + "_" + board[i][j + 1], gridMap.getOrDefault(String.valueOf(i) + "_" + String.valueOf(j) + "_" + board[i][j + 1], 0) + 1);
                }
                if(j-1>=0) {
                    gridMap.put(String.valueOf(i) + "_" + String.valueOf(j) + "_" + board[i][j - 1], gridMap.getOrDefault(String.valueOf(i) + "_" + String.valueOf(j) + "_" + board[i][j - 1], 0) + 1);
                }
                if(i+1<board.length) {
                    gridMap.put(String.valueOf(i) + "_" + String.valueOf(j) + "_" + board[i + 1][j], gridMap.getOrDefault(String.valueOf(i) + "_" + String.valueOf(j) + "_" + board[i + 1][j], 0) + 1);
                }
                if(i-1>=0) {
                    gridMap.put(String.valueOf(i) + "_" + String.valueOf(j) + "_" + board[i - 1][j], gridMap.getOrDefault(String.valueOf(i) + "_" + String.valueOf(j) + "_" + board[i - 1][j], 0) + 1);
                }
                if((i-1>=0)&&(j-1>=0)) {
                    gridMap.put(String.valueOf(i) + "_" + String.valueOf(j) + "_" + board[i - 1][j - 1], gridMap.getOrDefault(String.valueOf(i) + "_" + String.valueOf(j) + "_" + board[i - 1][j - 1], 0) + 1);
                }
                if((i+1<board.length)&&(j+1)<board[0].length){
                    gridMap.put(String.valueOf(i)+ "_" + String.valueOf(j)+ "_" + board[i+1][j+1],gridMap.getOrDefault(String.valueOf(i) + "_" + String.valueOf(j) + "_" + board[i+1][j+1],0)+1);
                }
                if((i-1>=0)&&(j+1<board[0].length)) {
                    gridMap.put(String.valueOf(i) + "_" + String.valueOf(j) + "_" + board[i - 1][j + 1], gridMap.getOrDefault(String.valueOf(i) + "_" + String.valueOf(j) + "_" + board[i - 1][j + 1], 0) + 1);
                }
                if((i+1<board.length)&&(j-1>=0)) {
                    gridMap.put(String.valueOf(i) + "_" + String.valueOf(j) + "_" + board[i + 1][j - 1], gridMap.getOrDefault(String.valueOf(i) + "_" + String.valueOf(j) + "_" + board[i + 1][j - 1], 0) + 1);
                }
                int curOneNum = gridMap.getOrDefault(String.valueOf(i) + "_" + String.valueOf(j)+ "_" + "1",0);
                int[] keyIndexArray = new int[2];
                if(board[i][j]==1){
                    if(curOneNum<2){
                        keyIndexArray[0] = i;
                        keyIndexArray[1] = j;
                        changeResult.put(keyIndexArray,0);
                    }else if((curOneNum==2)||(curOneNum==3)){

                    }else{
                        keyIndexArray[0] = i;
                        keyIndexArray[1] = j;
                        changeResult.put(keyIndexArray,0);
                    }
                }else{
                    if(curOneNum==3){
                        keyIndexArray[0] = i;
                        keyIndexArray[1] = j;
                        changeResult.put(keyIndexArray,1);
                    }
                }

            }
        }
        for(int[] keyIndexArray:changeResult.keySet()){
            board[keyIndexArray[0]][keyIndexArray[1]] = changeResult.get(keyIndexArray);
        }
        System.out.println("board:"+String.valueOf(board));
         */


        /**
         * 【Again】 49. Group Anagrams
         *
        String[] strs = {"eat","tea","tan","ate","nat","bat"};
        Map<String,List<String>> eleListMapTmp = new HashMap<>();
        String[] strsCopy = new String[strs.length];
        for(int i=0;i<strs.length;i++){
            char[] tmpChar = strs[i].toCharArray();
            Arrays.sort(tmpChar);
            strsCopy[i] = String.valueOf(tmpChar);
            System.out.println("strs:"+String.valueOf(strs));
        }

        for(int i=0;i<strsCopy.length;i++){
            List<String> eleListTmp = eleListMapTmp.getOrDefault(strsCopy[i],new ArrayList<>());
            eleListTmp.add(strs[i]);
            eleListMapTmp.put(strsCopy[i],eleListTmp);
        }

        List<List<String>> result = eleListMapTmp.values().stream().toList();
        System.out.println("strs:"+String.valueOf(strs));




//        int[] test1 = {1,2,3};// same reference
//        int[] test2 = test1;
//        Map<int[],Integer> test = new HashMap<>();
//        test.put(test1,0);
//        test.put(test2,2);
//
//        int[] test1 = {1,2,3};// even with same values but different reference, map will identify test1 and test2 as different variables
//        int[] test2 = {1,2,3};
//        Map<int[],Integer> test = new HashMap<>();
//        test.put(test1,0);
//        test.put(test2,2);


        //Others' solution, similar with mine,but since the same reference changed synchronized,doesn't make sense , skip this solution
        String[] strs = {"eat","tea","tan","ate","nat","bat"};
        Map<int[],List<String>> res = new HashMap<>();
        int[] count = new int[26];
        for(int i=0;i<strs.length;i++){
            Arrays.fill(count, 0);
            for(int j=0;j<strs[i].length();j++){
                count[(int)strs[i].charAt(j)-(int)'a']=count[(int)strs[i].charAt(j)-(int)'a']+1;
            }
            List<String> eleListTmp = res.getOrDefault(count,new ArrayList<>());
            eleListTmp.add(strs[i]);
            res.put(count,eleListTmp);
        }
        List<List<String>> result = res.values().stream().toList();
        System.out.println("strs:"+String.valueOf(strs));
        */

        /**
         * 128. Longest Consecutive Sequence
         *
//        int[] nums = {100,4,200,1,3,2};
//        int[] nums = {0,3,7,2,5,8,4,6,0,1};
        int[] nums = {1,3,0,1};
        Arrays.sort(nums);
        int count = 1;
        int maxCount = 0;
        if(nums.length==0){
            System.out.println("maxCount:"+String.valueOf(maxCount));
        }
        if(nums.length==1){
            System.out.println("count:"+String.valueOf(count));
        }
        for(int i=1;i<nums.length;i++){
            if(nums[i]-1==nums[i-1]){
                count++;
                maxCount = max(maxCount,count);
                continue;
            }else if(nums[i]==nums[i-1]){
                maxCount = max(maxCount,count);
                continue;
            }else{
                maxCount = max(maxCount,count);
                count=1;
            }
        }
        System.out.println("maxCount:"+String.valueOf(maxCount));
         */


        /**
         *
         *【Again】 56. Merge Intervals
         *
//        int[][] intervals = {{1,3},{8,10},{2,6},{15,18}};
        int[][] intervals = {{1,4},{4,5}};
        List<int[]> resultList = new ArrayList<>();
        Arrays.sort(intervals, new java.util.Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return Integer.compare(a[0], b[0]);
            }
        });
        int[] begin = intervals[0];
        for(int i=1;i<intervals.length;i++){
            if(intervals[i][0]<=begin[1]){
                if(intervals[i][1]<=begin[1]){

                }else{
                    begin[1]=intervals[i][1];
                }
            }else{
                int[] tmpArray = new int[2];
                tmpArray[0]=begin[0];
                tmpArray[1]=begin[1];
                resultList.add(tmpArray);
                begin[0] = intervals[i][0];
                begin[1] = intervals[i][1];
            }
        }
        int[] tmpArray = new int[2];
        tmpArray[0]=begin[0];
        tmpArray[1]=begin[1];
        resultList.add(tmpArray);

        int[][] result = new int[resultList.size()][2];
        for(int i=0;i<resultList.size();i++){
            result[i] = resultList.get(i);
        }
        System.out.println("result:"+String.valueOf(result));
         */


        /**
         * 57. Insert Interval
         * *
        int[][] intervals = {{1,3},{6,9}};
        int[] newInterval = {2,5};
        int[][] intervalsNew = new int[intervals.length+1][2];
        for(int i=0;i<intervals.length+1;i++){
            if(i==intervals.length){
                intervalsNew[i]=newInterval;
            }else{
                intervalsNew[i]=intervals[i];
            }
        }
        List<int[]> resultList = new ArrayList<>();
        Arrays.sort(intervalsNew, new java.util.Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return Integer.compare(a[0], b[0]);
            }
        });
        int[] begin = intervalsNew[0];
        for(int i=1;i<intervalsNew.length;i++){
            if(intervalsNew[i][0]<=begin[1]){
                if(intervalsNew[i][1]<=begin[1]){

                }else{
                    begin[1]=intervalsNew[i][1];
                }
            }else{
                int[] tmpArray = new int[2];
                tmpArray[0]=begin[0];
                tmpArray[1]=begin[1];
                resultList.add(tmpArray);
                begin[0] = intervalsNew[i][0];
                begin[1] = intervalsNew[i][1];
            }
        }
        int[] tmpArray = new int[2];
        tmpArray[0]=begin[0];
        tmpArray[1]=begin[1];
        resultList.add(tmpArray);

        int[][] result = new int[resultList.size()][2];
        for(int i=0;i<resultList.size();i++){
            result[i] = resultList.get(i);
        }
        System.out.println("result:"+String.valueOf(result));


        //others‘ solution
        int[][] intervals = {{1,3},{6,9}};
        int[] newInterval = {2,5};
        List<int[]> list = new ArrayList<>();
        int i = 0;
        int n = intervals.length;
        while(i<n && intervals[i][1]<newInterval[0]){// it means the first element of newInterval falls into the range of first of element of interval
            list.add(intervals[i]);
            i++;
        }
        while(i<n && intervals[i][0]<=newInterval[1]){
            newInterval = new int[]{
               Math.min(intervals[i][0],newInterval[0]),
               Math.max(intervals[i][1],newInterval[1])
            };
            i++;
        }
        list.add(newInterval);
        while(i<n){
            list.add(intervals[i++]);
        }

        System.out.println("list:"+String.valueOf(list));
         */

        /**
         * 452. Minimum Number of Arrows to Burst Balloons
         * Others' solution is similar with mine, no need to re-implement it again
         * *
        //int[][] points = {{10,16},{2,8},{1,6},{7,12}};
        //int[][] points = {{1,2},{3,4},{5,6},{7,8}};
        //int[][] points = {{1,2},{2,3},{3,4},{4,5}};
        int[][] points = {{3,9},{7,12},{3,8},{6,8},{9,10},{2,9},{0,9},{3,9},{0,6},{2,8}};
        Arrays.sort(points, new java.util.Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return Integer.compare(a[0], b[0]);
            }
        });

        int i = 1;
        int[] begin = new int[2];
        begin[0] = points[0][0];
        begin[1] = points[0][1];
        int[] tmpArrayBegin = new int[2];
        tmpArrayBegin[0] = points[0][0];
        tmpArrayBegin[1] = points[0][1];
        LinkedList<int[]> crossRange = new LinkedList<>();
        crossRange.add(tmpArrayBegin);
        int n = points.length;
        while(i < n){
           if(points[i][0]<=begin[1]){
               begin[0]=max(begin[0],points[i][0]);
               begin[1]=min(begin[1],points[i][1]);
               i++;
           }else{
               crossRange.removeLast();
               int[] tmpArray1 = new int[2];
               tmpArray1[0] = begin[0];
               tmpArray1[1] = begin[1];
               crossRange.add(tmpArray1);
               begin[0] = points[i][0];
               begin[1] = points[i][1];
               int[] tmpArray2 = new int[2];
               tmpArray2[0] = points[i][0];
               tmpArray2[1] = points[i][1];
               crossRange.add(tmpArray2);
               i++;
           }
        }
         System.out.println("crossRange.size():"+crossRange.size());
         */

        /**
         * 71. Simplify Path
         *

//        String path = "/home////foo//////";
        String path = "/Library/Java/JavaVirtualMachines/jdk-20_0_1_jdk/../Contents";
        String path = "/../";
        String path ="/home/";
        boolean tailSlashStatus = true;//my solution is not correct, can't cover all the cases
        int slashCount = 0;
        int docCount = 0;
//        int firstSlashIndex = -1;
//        int endSlashIndex = -1;
//        int firstDotIndex = -1;
//        int endDotIndex = -1;
        Stack<Character> pathStack = new Stack<>();

        for(int i=path.length()-1;i>=0;i--){
                if(path.charAt(i)=='/'){
                    slashCount++;
                    if(tailSlashStatus){
                        slashCount--;
                        continue;
                    }
                    if(slashCount==1){
                        pathStack.add('/');
                    }
                    if(docCount==1){
                        pathStack.pop();
                        docCount=0;
                    }else if(docCount==2 && slashCount<2&& pathStack.size()==1){

                    }else if(docCount==2 && slashCount<2&& pathStack.size()>1){
                        pathStack.pop();
                        pathStack.pop();
                    }else if(docCount==2 && slashCount==2){
                        pathStack.add('/');
                        docCount = 0;
                        slashCount = 0;
                    }
                }else{
                    if(slashCount>1){
                        slashCount = 0;
                    }
                    if(!(docCount==2)){
                        slashCount = 0;
                    }
                    tailSlashStatus = false;
                    if(path.charAt(i)=='.'){
                        docCount++;
                        continue;
                    }else{
                        if(!(docCount==2)){
                           pathStack.add(path.charAt(i));
                        }
                    }


                }
        }
        StringBuilder result = new StringBuilder();
        while(!pathStack.empty()){
            result.append(pathStack.pop());
        }
        System.out.println("result:"+result.toString());
         */

        //others' solution, smart solution, split, I didn't think of it.
        String path = "/home////foo//...////";
        String[] pathSplit = path.split("/");  // here three dots will be kept as it is.
        Stack<String> pathStack = new Stack<>();
        for(int i=0;i<pathSplit.length;i++){
           if(pathSplit[i].equalsIgnoreCase("..")&&!pathStack.empty()){
               pathStack.pop();
           }else if(!pathSplit[i].equalsIgnoreCase("")&&!pathSplit[i].equalsIgnoreCase(".")&&!pathSplit[i].equalsIgnoreCase("..")){
               pathStack.add(pathSplit[i]);
           }else{// this case is "." or ""

           }
        }
        System.out.println("/"+String.join("/",pathStack));


        /**
         * 155. Min Stack
         * */

         String[] commands = {"MinStack","push","push","push","getMin","pop","top","getMin"};// I have no idea the purpose of this problem is aiming at testing what, so I prefer to skip it, my solution isn't correct
         //                   []          [-2]   [0]    [-3]   []       []    []    []
         MinStack obj = new MinStack();
         obj.push(-2);
         obj.pop();
         int param_3 = obj.top();
         int param_4 = obj.getMin();

    }
}
