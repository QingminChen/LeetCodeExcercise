package com.qingmin.test;

import java.util.*;

import javax.script.ScriptException;

class ListNode2 {
      int val;
      ListNode2 next;
      ListNode2() {}
      ListNode2(int val) { this.val = val; }
      ListNode2(int val, ListNode2 next) { this.val = val; this.next = next; }

 }


class ListNodeCache {
    Map<Integer,Integer> key_val =  new HashMap<>();
    ListNodeCache next;
    ListNodeCache() {}
    ListNodeCache(Map<Integer,Integer> key_val) {
        this.key_val = key_val;
    }
    ListNodeCache(Map<Integer,Integer> key_val, ListNodeCache next) { this.key_val = key_val; this.next = next; }

}

class ListNodeCacheOthers {// the smart way is he seperated the maintain map from ListNode
  int key;
  int val;
  ListNodeCacheOthers prev;
  ListNodeCacheOthers next;
  ListNodeCacheOthers() {}// it is defualt no argument contructor
  ListNodeCacheOthers(int key, int value) {
      this.key = key;
      this.val =  value;
  }

  //head->node->tail;  so the previous node of head is Null node, next node of tail node is Null node

}


 class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
 }

class NodeBinaryTree {
    public int val;
    public NodeBinaryTree left;
    public NodeBinaryTree right;
    public NodeBinaryTree next;

    public NodeBinaryTree() {}

    public NodeBinaryTree(int _val) {
        this.val = _val;
    }

    public NodeBinaryTree(int _val, NodeBinaryTree _left, NodeBinaryTree _right, NodeBinaryTree _next) {
        this.val = _val;
        this.left = _left;
        this.right = _right;
        this.next = _next;
    }
};


class LRUCache { // mine solution is time exceed
    ListNodeCache cache_all_head =  new ListNodeCache();
    ListNodeCache cache_all=cache_all_head;
    int capacity = 0;
    int numCount = 0;
    Map<Integer,Integer> lastest_used_map = new HashMap<>();

    public LRUCache(int capacity) {
      this.capacity = capacity;

    }

    public int get(int key) {
        int val = -1;
        ListNodeCache check_cache_all = this.cache_all_head;
        ListNodeCache prev =  null;
        int i =0;
        while(check_cache_all!=null){
            if(i==0){

            }else{
                if(check_cache_all.key_val.containsKey(key)){
                    val = check_cache_all.key_val.get(key);
                    if(check_cache_all.next!=null){
                        prev.next = check_cache_all.next;
                        i--;
                        Map<Integer, Integer> key_val = new HashMap<>();
                        key_val.put(key, val);
                        this.cache_all.next = new ListNodeCache(key_val);
                        check_cache_all.next = new ListNodeCache(key_val);
                        this.cache_all = this.cache_all.next;
                    }else{

                    }

                    break;
                }
            }
            prev = check_cache_all;
            check_cache_all = check_cache_all.next;
            i++;
        }

        return val;
    }

    public void put(int key, int value) {
        if(this.get(key)==-1){
            if(numCount<this.capacity) {

                Map<Integer, Integer> key_val = new HashMap<>();
                key_val.put(key, value);
                this.cache_all.next = new ListNodeCache(key_val);
                this.cache_all = this.cache_all.next;
                this.numCount++;
            }else{
                if(this.cache_all_head.next.next!=null){
                    cache_all_head.next = cache_all_head.next.next;
                    Map<Integer, Integer> key_val = new HashMap<>();
                    key_val.put(key, value);
                    this.cache_all.next = new ListNodeCache(key_val);
                    this.cache_all = this.cache_all.next;
                    this.numCount = capacity;
                }else{
                    cache_all_head.next = cache_all_head.next.next;
                    Map<Integer, Integer> key_val = new HashMap<>();
                    key_val.put(key, value);
                    this.cache_all.next = new ListNodeCache(key_val);
                    this.cache_all = this.cache_all.next;
                    this.numCount = capacity;
                    this.cache_all_head.next = this.cache_all;
                }
            }
        }else{
            this.cache_all.key_val.put(key,value);
        }
    }
}

class LRUCacheOthers {
    final ListNodeCacheOthers head = new ListNodeCacheOthers();
    final ListNodeCacheOthers tail = new ListNodeCacheOthers();
    Map<Integer,ListNodeCacheOthers> node_map;
    int capacity = 0;

    public LRUCacheOthers(int capacity){
        node_map = new HashMap<>(capacity);// To be honest no difference from the functionality perspective if initialize a hashmap with a capacity.
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
        System.out.println("123");
    }

    public int get(int key){
        int result =-1;
        if(this.node_map.containsKey(key)){
            this.remove(this.node_map.get(key));
            this.add(this.node_map.get(key));
            result = this.node_map.get(key).val;
        }
        return result;
    }

    public void put(int key, int value){
        ListNodeCacheOthers node = this.node_map.get(key);
        if(node !=null){
            this.remove(node);
            node.val = value;
            this.add(node);
        }else{
            if(this.node_map.size()==capacity){
                this.node_map.remove(this.tail.prev.key);
                this.remove(this.tail.prev);
            }
            ListNodeCacheOthers new_node = new ListNodeCacheOthers(key,value);
            this.add(new_node);
            this.node_map.put(key,new_node);
        }

    }

    public void add(ListNodeCacheOthers node){
        ListNodeCacheOthers head_next = this.head.next;
        node.next = head_next;
        node.prev = this.head;
        this.head.next = node;
        head_next.prev = node;

    }

    public void remove(ListNodeCacheOthers node){
        ListNodeCacheOthers next_node = node.next;
        ListNodeCacheOthers prev_node = node.prev;

        next_node.prev = prev_node;
        prev_node.next = next_node;
    }
}

class BSTIterator {
    TreeNode root;
    Queue<Integer> sortedNodeQueue = new LinkedList<>();
    public BSTIterator(TreeNode root) {//left nodes always less than root, right nodes always larger than root
       this.root = root;
       nextHelper(root);
    }

    public int next() {
        int next_node =-1;
        if(!this.sortedNodeQueue.isEmpty()) {
            next_node = this.sortedNodeQueue.peek();
            this.sortedNodeQueue.poll();
        }
       return next_node;
    }

    public void nextHelper(TreeNode current_node) {
        if(current_node.left==null&&current_node.right==null){
            this.sortedNodeQueue.add(current_node.val);
            return;
        }
        if(current_node!=null){
            if(current_node.left!=null){
                nextHelper(current_node.left);
            }
            this.sortedNodeQueue.add(current_node.val);
            System.out.println("123");
            if(current_node.right!=null){
                nextHelper(current_node.right);
            }
        }
        return;
    }


    public boolean hasNext() {
        boolean hasNextStatus = false;
        if(this.sortedNodeQueue.size()!=0){
            hasNextStatus = true;
        }
       return hasNextStatus;
    }
}


class BSTIteratorOthers {
    private TreeNode cur;
    private Stack<TreeNode> stack;
    public BSTIteratorOthers(TreeNode root){
        this.cur = root;
        this.stack = new Stack<>();

    }

    public boolean hasNext(){
        if(!this.stack.isEmpty()||this.cur!=null){
            return true;
        }else{
            return false;
        }
    }

    public int next(){
        while(this.cur!=null){
            this.stack.push(cur);
            this.cur = this.cur.left;
        }
        this.cur = this.stack.pop();
        int val = this.cur.val;
        this.cur = this.cur.right;
        return val;
    }
}

public class App3 {

    /**
     * 150. Evaluate Reverse Polish Notation
     * */
    public static int evalRPN(String[] tokens) throws ScriptException {
        if(tokens.length==1){
            return Integer.valueOf(tokens[0]);
        }
        Stack<Integer> stack =  new Stack<>();
        int resultTmp = 0;
        for(String token:tokens){
            if(!token.equalsIgnoreCase("+")&&!token.equalsIgnoreCase("-")&&!token.equalsIgnoreCase("*")&&!token.equalsIgnoreCase("/")){
                stack.push(Integer.valueOf(token));
            }else{
                int firstPop = stack.pop();
                int secondPop = stack.pop();
                if(token.equalsIgnoreCase("-")){
                    resultTmp = secondPop-firstPop;
                    stack.push(resultTmp);
                }else if(token.equalsIgnoreCase("*")){
                    resultTmp = secondPop*firstPop;
                    stack.push(resultTmp);
                }else if(token.equalsIgnoreCase("+")){
                    resultTmp = secondPop+firstPop;
                    stack.push(resultTmp);
                }else if(token.equalsIgnoreCase("/")){
                    resultTmp = secondPop/firstPop;
                    stack.push(resultTmp);
                }else{

                }
            }
        }
        return resultTmp;
    }

    public static ListNode2 addTwoNumbers(ListNode2 l1, ListNode2 l2) {// limited time ran out
        int countL1 = 0;
        int countL2 = 0;
        long tmpL1 = 0;
        long tmpL2 = 0;
        while(l1!=null){
            tmpL1 = tmpL1+(long) (l1.val*Math.pow(10,countL1));// this solution 32dight unit arithmetic overflow
            l1=l1.next;
            countL1++;
        }
        while(l2!=null){
            tmpL2 = tmpL2+(long) (l2.val*Math.pow(10,countL2));
            l2=l2.next;
            countL2++;
        }
        long resultTmp = tmpL1+tmpL2;
        String resultTmpStr = String.valueOf(resultTmp);
        int resultTmpStrLen = resultTmpStr.length();

        ListNode2 head = new ListNode2(Integer.valueOf(String.valueOf(resultTmpStr.charAt(resultTmpStrLen-1))));
        ListNode2 result = head;   // It's important for how to insert head node into ListNode dynamically, we need two pointers

        int j = 0;
        for(int i=resultTmpStrLen-2;i>=0;i--){
            String tmp = String.valueOf(resultTmpStr.charAt(i));
            ListNode2 tmpNode = new ListNode2(Integer.valueOf(tmp));
            result.next=tmpNode;
            result = result.next;
            j++;
        }

        return head;
    }

    public static ListNode2 addTwoNumbers_Others(ListNode2 l1, ListNode2 l2) {// limited time ran out
        ListNode2 head = new ListNode2(0);
        ListNode2 result = head;
        long tmpL1 = 0;
        long tmpL2 = 0;
        int carry = 0;
        while(l1!=null||l2!=null){
            int l1_val = (l1!=null)?l1.val:0;
            int l2_val = (l2!=null)?l2.val:0;
            int current_sum = l1_val+l2_val+carry;
            carry = current_sum/10;//进位
            int last_digit = current_sum%10;
            ListNode2 new_node = new ListNode2(last_digit);
            result.next = new_node;
            if(l1!=null){
                l1=l1.next;
            }
            if(l2!=null){
                l2=l2.next;
            }
            result = result.next;
        }
        if(carry>0){//溢出了
            ListNode2 new_node = new ListNode2(carry);
            result.next = new_node;
            result =  result.next;
        }
        head = head.next;  // Since we add an additional leading zero
        return head;
    }

    public static ListNode2 reverseBetween(ListNode2 head, int left, int right) {//tricky solution, not familiar with listnode
        if(head==null){
            return null;
        }
        ListNode2 prev = null;
        ListNode2 current_node = head;//in order to not lose the reference

        while(left>1){ // the purpose of this part is always find the previous node of starting position which needs to be reversed
            prev = current_node;
            current_node=current_node.next;
            left--;
            right--;
        }
        ListNode2 connection = prev;//split the listnode into two parts, former part is all the nodes before the starting postion , the latter part includes the remaining nodes
        ListNode2 tail = current_node;
        while(right>0){
          ListNode2 next_node = current_node.next;
          current_node.next =  prev;
          prev = current_node;
          current_node = next_node;
          right--;
        }

        if(connection!=null){
            connection.next = prev;
        }else{
            head=prev;
        }
        tail.next =current_node;
        return head;
    }

    public static ListNode2 removeNthFromEnd(ListNode2 head, int n) {

        ListNode2 current_node = head;
        ListNode2 count_node = current_node;
        int count = 0;
        while(count_node!=null){
          count++;
          count_node = count_node.next;
        }
        if(count==1){
            return null;
        }

        int left = count+1-n;
        int i =2;
        ListNode2 prev = null;
        if(i>left){
            return head.next;
        }
        while(i<=left&&i>1){// the reason why needs to larger than 1 is that we need make sure current node has previous node, this time efficiency solution
            prev = current_node;
            current_node = current_node.next;
            i++;
        }

        prev.next = current_node.next;

        return head;
    }



    public static ListNode2 deleteDuplicates(ListNode2 head) {
        ListNode2 current_node = head;
        int i = 1;
        int prev_i=0;
        int pre_val = Integer.MAX_VALUE;
        ListNode2 prev = null;
        ListNode2 prev_prev = null;
        while(current_node!=null){
            if(i==1){
                pre_val = current_node.val;
                prev = current_node;
                current_node = current_node.next;
                prev_i = i;
                i++;
            }else if(i==2){// the first node is the current node
                if(pre_val!= current_node.val){
                    prev_prev = prev;
                    pre_val = current_node.val;
                    prev = current_node;
                    current_node = current_node.next;
                    prev_i = i;
                    i++;
                }else{
                    current_node = current_node.next;
                    i++;
                }

            }else{
                if(prev_i>1){
                    if(pre_val!= current_node.val){
                        if(prev_i+1==i){
                            prev_prev = prev;
                            prev = current_node;
                            pre_val = current_node.val;
                            prev_i = i;
                            current_node = current_node.next;
                            i++;
                        }else{
                            prev_prev.next=current_node;
                            prev = current_node;
                            pre_val = current_node.val;
                            current_node = current_node.next;
                            i =prev_i+1;

                        }
                    }else{
                        current_node = current_node.next;
                        i++;
                    }
                }else{
                    if(pre_val!= current_node.val){
                        i=prev_i;
                        head = current_node;
                    }else{
                        current_node = current_node.next;
                        i++;
                    }
                }
            }
        }
        if(prev_i!=i-1){
            if(prev_prev!=null){
                prev_prev.next=current_node;
            }else{
                head = null;
            }
        }

        return head;
    }

    public static ListNode2 deleteDuplicatesOthers(ListNode2 head) {//I didn't review it
//        ListNode2 current_node = head;
//        ListNode2 prev = null;
//        int i = 1;
//        int start_duplicate_i = -1;
//        while(current_node!=null&&current_node.next!=null){
//            if(i==1){
//                if(current_node.val==current_node.next.val){
//                    start_duplicate_i = i;
//                }else{
//                    prev = current_node;
//                    start_duplicate_i = -1;
//                }
//                current_node = current_node.next;
//                i++;
//            }else{
//                if(start_duplicate_i==1){
//                    if(current_node.val==current_node.next.val){
//                        current_node = current_node.next;
//                        i++;
//                    }else{
//                        start_duplicate_i = -1;
//                        current_node = current_node.next;
//                        i=1;
//                    }
//                }else{
//                    start_duplicate_i = -1;
//                    if(current_node.val==current_node.next.val){
//                        prev.next = current_node.next.next;
//                        current_node = current_node.next.next;
//                    }else{
//                        prev = current_node;
//                        current_node = current_node.next;
//                        i++;
//                    }
//                }
//            }
//
//        }
//        if(start_duplicate_i==1){
//            head = null;
//        }else{
//            head = prev;
//        }

        if(head ==null||head.next==null){
            return head;
        }

        ListNode2 dummy = new ListNode2();
        dummy.next = head;

        ListNode2 pre = dummy;
        ListNode2 cur = head;
        while(cur !=null) {
            if ((cur.next != null) && (cur.val == cur.next.val)) {
                while(cur.next!=null&&cur.val==cur.next.val){
                    cur =  cur.next;
                }
                pre.next = cur.next;
            }else {
                pre = cur;
            }
            cur = cur.next;
        }

        return dummy.next;
    }


    public static ListNode2 rotateRightOthers(ListNode2 head, int k) {// I am not familar with reverse with ListNode
        if(head==null||head.next==null){
            return head;
        }
        ListNode2 current_node = head;
        int  count =1;// no need to loop to the last elment, so we start from 1 as default
        while(current_node.next!=null){// until the last second element
            current_node = current_node.next;
            count++;
        }
        current_node.next=head;//it is the circle

        System.out.println("123");
        int real_k = k%count;
        for(int i=1;i<count-real_k;i++){//anti-clockwise direction, the reason why of less than not less than and equale is that we have the next step to get the expected order we wanted
           head = head.next;
        }
        ListNode2 res = head.next;
        head.next =  null;// here is used for cutting the circle

        return res;
    }


    public static ListNode2 partitionOthers(ListNode2 head, int x) {
        ListNode2 before_head =  new ListNode2(0);
        ListNode2 before = before_head;
        ListNode2 after_head =  new ListNode2(0);
        ListNode2 after = after_head;

        while(head !=null){
            if(head.val<x){
                before.next = head;
                before = before.next;
            }else{
                after.next =  head;
                after = after.next;
            }
            head = head.next;
        }
        after.next = null;
        before.next= after_head.next;//exclude the leading zero
        return before_head.next;
    }

    public static TreeNode buildTreeOthers(int[] preorder, int[] inorder) {//iteration

//        int[] preorder = {3,9,20,15,7};//middle->left->right
//        int[] inorder = {9,3,15,20,7};//left->middle->right
        Map<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<inorder.length;i++){
            map.put(inorder[i],i);
        }
        return helper(0, 0, inorder.length-1, preorder, inorder,map);
    }

    public static TreeNode helper(int preIndexStartSubTree, int inStart, int inEnd, int[] preorder, int[] inorder, Map<Integer,Integer> map) {//it includes of generating left tree and right tree
        //start node's position in preorder of every sub-tree group(includding left tree and right tree) is sub-root node
        if(preIndexStartSubTree>preorder.length-1||inStart>inEnd){
            return null;
        }
        TreeNode root = new TreeNode(preorder[preIndexStartSubTree]);//preIndexStartSubTree is also the root node of sub tree
        if(inStart==inEnd){
            return root;
        }
        int inRootIndex = map.get(root.val);
        root.left = helper(preIndexStartSubTree+1,inStart,inRootIndex-1,preorder,inorder,map);
        root.right = helper(preIndexStartSubTree+(inRootIndex-1-inStart+1)+1,inRootIndex+1,inEnd,preorder,inorder,map);

        return root;
    }

    public static TreeNode buildTreePost(int[] postorder, int[] inorder) {//iteration
//        int[] postorder = {9,15,7,20,3};//left->right->middle
//        int[] inorder = {9,3,15,20,7};//left->middle->right
        Map<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<inorder.length;i++){
            map.put(inorder[i],i);
        }
        return helperPost(postorder.length-1, 0, inorder.length-1, postorder, inorder,map);
    }

    public static TreeNode helperPost(int preIndexEndSubTree, int inStart, int inEnd, int[] postorder, int[] inorder, Map<Integer,Integer> map) {//it includes of generating left tree and right tree
        //End node's position in postorder of every sub-tree group(includding left tree and right tree) is sub-root node
        if(preIndexEndSubTree>postorder.length-1||inStart>inEnd){
            return null;
        }
        TreeNode root = new TreeNode(postorder[preIndexEndSubTree]);//preIndexEndSubTree is also the root node of sub tree
        if(inStart==inEnd){
            return root;
        }
        int inRootIndex = map.get(root.val);
        root.right = helperPost(preIndexEndSubTree-1,inRootIndex+1,inEnd,postorder,inorder,map);
        root.left = helperPost(preIndexEndSubTree-(inEnd-(inRootIndex+1)+1)-1,inStart,inRootIndex-1,postorder,inorder,map);

        return root;
    }

    public static NodeBinaryTree connectOthers(NodeBinaryTree root) {//dummy stands for linkedlist of every layer
        NodeBinaryTree head = root;//head is used for changing root
        while(head!=null){
            NodeBinaryTree dummy = new NodeBinaryTree();//dummy is used for maintaining linkedlist, dummy start to connect from second layer
            NodeBinaryTree temp = dummy;//temp is used for changing linkedlist
            while(head!=null){
                if(head.left!=null){
                    temp.next = head.left;// once this assignment finish, any changes happened in temp will change dummy and head left synchronically
                    temp = temp.next;
                }
                if(head.right!=null){
                    temp.next = head.right;
                    temp = temp.next;
                }
                head =  head.next;// here is to check if any brother or sister nodes
            }
            head = dummy.next; //overwrite previous layer had-done connection info as new head
        }

        return root;
    }

    public static void flatten(TreeNode root) {// do it in-place
        TreeNode head =  root;
        TreeNode dummy = new TreeNode();
        TreeNode temp = dummy;

        dummy = helperFlatten(head,dummy,temp);
        root = dummy;
        System.out.println("789");
    }
    public static TreeNode helperFlatten(TreeNode subRoot, TreeNode dummy, TreeNode temp) {

        if(subRoot!=null){
            temp.val= subRoot.val;
            if(subRoot.left==null&&subRoot.right==null){
                return dummy;
            }
            if(subRoot.left!=null||subRoot.right!=null){
                if(subRoot.left!=null){
                    temp.right = new TreeNode();
                    temp = temp.right;
                    helperFlatten(subRoot.left,dummy,temp);
                    subRoot.left = null;
                }
                while(temp.right!=null){
                    temp = temp.right;
                }
                temp.right = new TreeNode();
                temp = temp.right;
                helperFlatten(subRoot.right,dummy,temp);

            }
        }
       System.out.println("456");
       return dummy;
    }

    public static void flatten2(TreeNode root) {// do it in-place， this splution is wrong,ignore this
        TreeNode current_root =  root;
        TreeNode previous_ready =  new TreeNode();
//        TreeNode head_right = head.right;
        Map<TreeNode,TreeNode> right_map = new HashMap<>();
        boolean leaf_node_status = false;
        helperFlatten2(current_root,right_map,previous_ready,root);
        System.out.println("789");

    }

    public static TreeNode helperFlatten2(TreeNode current_root, Map<TreeNode,TreeNode> right_map, TreeNode previous_ready,TreeNode root) {// do it in-place
       // TreeNode current_root_right = current_root.right;
        if(current_root.left==null&&current_root.right==null){
            previous_ready = current_root;
            return current_root;
        }
        if(current_root.left!=null){// I didn't put the root node into recursive
            right_map.put(current_root,current_root.right);
            current_root.right = current_root.left;
            current_root.left = null;
            current_root = current_root.right;
            previous_ready = current_root;
            helperFlatten2(current_root,right_map,previous_ready,root);
            System.out.println("123");
            while(previous_ready.right!=null){
                previous_ready = previous_ready.right;
            }
        }

//        if(current_root.right!=null){
//            TreeNode new_right = current_root.right;
//            if(current_root.right!=null){
//                current_root = current_root.right;
//                //helperFlatten2();
//                current_root = current_root.right;
//            }
//            current_root.right = new_right;
//        }

        if(right_map.size()!=0){
            if(right_map.size()==1){
                TreeNode original_root = (TreeNode) right_map.keySet().toArray()[0];
                TreeNode tempCurrent_root= right_map.get(original_root);
                right_map.remove(original_root);
                current_root = tempCurrent_root;
                helperFlatten2(current_root,right_map,previous_ready,root);
                previous_ready.right = current_root;
                previous_ready = previous_ready.right;
                System.out.println("1234");
            }else{
                if(right_map.get(current_root)!=null){
                    TreeNode tempCurrent_root = right_map.get(current_root);
                    right_map.remove(current_root);
                    current_root = tempCurrent_root;
                    helperFlatten2(current_root,right_map,previous_ready,root);
                    previous_ready.right = current_root;
                    previous_ready = previous_ready.right;
                    System.out.println("1234");
                }
            }

        }

        System.out.println("456");
        return current_root;
    }

    public static void flattenOthers(TreeNode root) {// do it in-place
        if(root == null){
            return;
        }
        Stack<TreeNode> stack = new Stack<>(); //stack is used for next layer as root
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode current_node = stack.pop();
            if(current_node.right!=null) {
                stack.push(current_node.right);
            }
            if(current_node.left!=null){
                stack.push(current_node.left);
            }

            if(!stack.isEmpty()){
                current_node.right = stack.peek();
            }
            current_node.left = null;
        }
    }

    public static int sumNumbers(TreeNode root) {

        TreeNode current_node = root;
        if(current_node==null){
           return 0;
        }
        int sum = 0;
        StringBuilder tempStr = new StringBuilder();
        List<String> all_possible_values = new ArrayList<>();
        all_possible_values = calNums(current_node,tempStr,all_possible_values);
        for(String possible_value : all_possible_values){
            sum = sum+Integer.valueOf(possible_value);
        }
        return sum;
    }

    public static List<String> calNums(TreeNode current_node,StringBuilder tempStr,List<String> all_possible_values) {
        tempStr.append(current_node.val);
        if(current_node.left==null&current_node.right==null){
            all_possible_values.add(tempStr.toString());
            tempStr.deleteCharAt(tempStr.length()-1);
            return all_possible_values;
        }
        if(current_node.left!=null){
            calNums(current_node.left,tempStr,all_possible_values);
        }
        if(current_node.right!=null){
            calNums(current_node.right,tempStr,all_possible_values);
        }

        tempStr.deleteCharAt(tempStr.length()-1);
        return all_possible_values;
    }

    public static int sumNumbersOthers(TreeNode root) {
        int sum = 0;
        List<Integer> sumList = new ArrayList<>();
        if(root == null){
            return sumList.get(0);
        }
        helperSumNumbersOthers(root ,0, sumList);
        for(int indivitual_sum: sumList){
            sum = sum+indivitual_sum;
        }
        return sum;
    }

    public static List<Integer> helperSumNumbersOthers(TreeNode node, int val, List<Integer> sumList) {
        if(node == null){
            return sumList;
        }
        if(node.left==null&node.right==null){
            sumList.add(val*10+node.val);
            return sumList;
        }
        helperSumNumbersOthers(node.left,val*10+node.val,sumList);
        helperSumNumbersOthers(node.right,val*10+node.val,sumList);
        return sumList;
    }

    public static int countNodes(TreeNode root) {
        List<Integer> nodeValueList = new ArrayList<>();
        if(root==null){
            return 0;
        }
        nodeValueList = countNodesHelper(root, nodeValueList);
        return nodeValueList.size();
    }
    public static List<Integer> countNodesHelper(TreeNode cur, List<Integer> nodeValueList) {
        if(cur.left==null&&cur.right==null){
            nodeValueList.add(cur.val);
            return nodeValueList;
        }

        if(cur!=null){
            if(cur.left!=null){
                countNodesHelper(cur.left,nodeValueList);
            }
            nodeValueList.add(cur.val);
            if(cur.right!=null){
                countNodesHelper(cur.right,nodeValueList);
            }
        }

        return nodeValueList;
    }

    public static int countNodesOthers(TreeNode root){
        Queue<TreeNode> nodeValueQueue = new LinkedList<>();
        if(root==null){
            return 0;
        }
        nodeValueQueue.offer(root);
        int count = 1;
        while(!nodeValueQueue.isEmpty()){
            if(nodeValueQueue.peek().left!=null){
                nodeValueQueue.offer(nodeValueQueue.peek().left);
                count++;
            }
            if(nodeValueQueue.peek().right!=null){
                nodeValueQueue.offer(nodeValueQueue.peek().right);
                count++;
            }
            nodeValueQueue.poll();
        }
        return count;
    }

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {//correct
         Map<Integer, Stack<Integer>> nodeAncestorStackMap = new HashMap<>();
         Queue<TreeNode> nodeValueQueue = new LinkedList<>();
         nodeValueQueue.add(root);
         Stack<Integer> rootAncestorStack = new Stack<>();
         rootAncestorStack.add(root.val);
         nodeAncestorStackMap.put(root.val,rootAncestorStack);
         boolean checkPStatus = false;
         boolean checkQStatus = false;
         Stack<Integer> pAncestorStack = new Stack<>();

         Stack<Integer> qAncestorStack = new Stack<>();

         while(!nodeValueQueue.isEmpty()) {
             if (nodeValueQueue.peek().val == p.val) {
                 checkPStatus=true;
                 pAncestorStack = nodeAncestorStackMap.get(nodeValueQueue.peek().val);
             }
             if (nodeValueQueue.peek().val == q.val) {
                 checkQStatus=true;
                 qAncestorStack = nodeAncestorStackMap.get(nodeValueQueue.peek().val);
             }
             if(checkPStatus&&checkQStatus){
                 break;
             }

             if (nodeValueQueue.peek().left != null) {
                 nodeValueQueue.offer(nodeValueQueue.peek().left);
                 Stack<Integer> tmpAncestors = nodeAncestorStackMap.getOrDefault(nodeValueQueue.peek().val,new Stack<>());
                 Stack<Integer> tmp = new Stack<>();
                 tmp.addAll(tmpAncestors);
                 tmp.add(nodeValueQueue.peek().left.val);
                 nodeAncestorStackMap.put(nodeValueQueue.peek().left.val,tmp);
             }
             if (nodeValueQueue.peek().right != null) {
                 nodeValueQueue.offer(nodeValueQueue.peek().right);
                 Stack<Integer> tmpAncestors = nodeAncestorStackMap.getOrDefault(nodeValueQueue.peek().val,new Stack<>());
                 Stack<Integer> tmp = new Stack<>();
                 tmp.addAll(tmpAncestors);
                 tmp.push(nodeValueQueue.peek().right.val);
                 nodeAncestorStackMap.put(nodeValueQueue.peek().right.val,tmp);
             }
             nodeValueQueue.poll();
         }
        int commonLayer = 0;
        int i = 0;
        TreeNode result = new TreeNode();
        Stack<Integer> longerStack = new Stack<>();
        Stack<Integer> shorterStack = new Stack<>();
         if(qAncestorStack.size()>pAncestorStack.size()){
             commonLayer =pAncestorStack.size();
             i = qAncestorStack.size();
             longerStack=qAncestorStack;
             shorterStack = pAncestorStack;
         }else if(qAncestorStack.size()==pAncestorStack.size()){
             commonLayer =pAncestorStack.size();
             i = qAncestorStack.size();
             longerStack=qAncestorStack;
             shorterStack=pAncestorStack;
        }else{
             commonLayer =qAncestorStack.size();
             i = pAncestorStack.size();
             longerStack=pAncestorStack;
             shorterStack =qAncestorStack;
        }
         while(i>commonLayer){
             if(longerStack.peek()==shorterStack.peek()){
                 result=new TreeNode(longerStack.peek());
             }
             longerStack.pop();
             i--;
         }
        while(i==commonLayer){
            if(longerStack.peek()==shorterStack.peek()){
                result=new TreeNode(longerStack.peek());
                break;
            }
            longerStack.pop();
            shorterStack.pop();
            i--;
            commonLayer--;
        }

         return result;
    }

    public static TreeNode lowestCommonAncestorOthers(TreeNode root, TreeNode p, TreeNode q){
        if(root==null){
            return null;
        }
        if(root.val==q.val||root.val==p.val){
            return root;
        }
        TreeNode left = lowestCommonAncestorOthers(root.left,p,q);
        TreeNode right = lowestCommonAncestorOthers(root.right,p,q);

        if(left!=null&& right!=null){
            return root;
        }

        if(left !=null){
            return left;
        }

        return right;
    }


   public static void main(String[] args) throws ScriptException {
       /**
        * 150. Evaluate Reverse Polish Notation
        *
//     String[] tokens = {"2", "1", "+", "3", "*"};
//     String[] tokens = {"4","13","5","/","+"};
//     String[] tokens = {"10","6","9","3","+","-11","*","/","*","17","+","5","+"};
       String[] tokens = {"3","11","+","5","-"};
       int result = evalRPN(tokens); // others' solution is similar with mine
       */

       /**
        * 2. Add Two Numbers
        *
//       ListNode2 l1 = new ListNode2(1);
//       ListNode2 l1Tmp = l1;
//       int[] l1Array = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1};
//       for(int i=0;i<l1Array.length;i++){
//           ListNode2 tmpNode = new ListNode2(l1Array[i]);
//           l1Tmp.next = tmpNode;
//           l1Tmp = l1Tmp.next;
//       }
       ListNode2 l1 = new ListNode2(2);
       l1.next = new ListNode2(4);
       l1.next.next = new ListNode2(3);

       ListNode2 l2 = new ListNode2(5);
       l2.next = new ListNode2(6);
       l2.next.next = new ListNode2(4);

       addTwoNumbers(l1,l2);
       addTwoNumbers_Others(l1,l2);//others' solution resolve the super long integer
        */

       /**
        * 138. Copy List with Random Pointer
        * *

       int[][] head = {{7,null},{13,0},{11,4},{10,2},{1,0}};//skip it, just deep copy
        */


       /**
        * 92. Reverse Linked List II
        *
       int[] head = {1,2,3,4,5,6,7,8}; //I didn't implement it , hard
       int left = 3;
       int right = 5;
       ListNode2 tmp = new ListNode2(1);
       tmp.next = new ListNode2(2);
       tmp.next.next = new ListNode2(3);
       tmp.next.next.next = new ListNode2(4);
       tmp.next.next.next.next = new ListNode2(5);
       tmp.next.next.next.next.next = new ListNode2(6);
       tmp.next.next.next.next.next.next = new ListNode2(7);
       tmp.next.next.next.next.next.next.next = new ListNode2(8);
       reverseBetween(tmp,left,right);
       */

       /**
        * 【Again】19. Remove Nth Node From End of List
        *
       int[] headArray = {2};// I implemented it twice, this time is efficiency
       int n = 2;
       ListNode2 head = new ListNode2(1);
       ListNode2 headTmp = head;
       for(int i=0;i<headArray.length;i++){
           ListNode2 tmpNode = new ListNode2(headArray[i]);
           headTmp.next = tmpNode;
           headTmp = headTmp.next;
       }
       removeNthFromEnd(head,n);
       */

       /**
        * 82. Remove Duplicates from Sorted List II
        *
       int[] headArray = {2,3,3,4,4,5};
//       int[] headArray = {1,1,2,3};
//       int[] headArray = {2,2};
       ListNode2 head = new ListNode2(1);
       ListNode2 headTmp = head;
       for(int i=0;i<headArray.length;i++){
           ListNode2 tmpNode = new ListNode2(headArray[i]);
           headTmp.next = tmpNode;
           headTmp = headTmp.next;
       }

       //deleteDuplicates(head);//my solution is same efficiency with others, but others looks much more straight forward
       deleteDuplicatesOthers(head);
       */

       /**
        * 61. Rotate List, hard, others' solution is smart
        *
       int[] headArray = {2,3,4,5};
       ListNode2 head = new ListNode2(1);
       ListNode2 headTmp = head;
       for(int i=0;i<headArray.length;i++){
           ListNode2 tmpNode = new ListNode2(headArray[i]);
           headTmp.next = tmpNode;
           headTmp = headTmp.next;
       }
       int k = 2;
       rotateRight(head,k);
       */

       /***
        * 86. Partition List
       int[] headArray = {4,3,2,5,2};// the requirements is misleading ,just check out others' solution
       ListNode2 head = new ListNode2(1);
       ListNode2 headTmp = head;
       for(int i=0;i<headArray.length;i++){
           ListNode2 tmpNode = new ListNode2(headArray[i]);
           headTmp.next = tmpNode;
           headTmp = headTmp.next;
       }
       int x = 3;
       partitionOthers(head,x);
        */

       /**
        * 146. LRU Cache
        *

//       String[] commands = {"LRUCache","put","put","get","put","get","put","get","get"};
//       LRUCache lRUCache = new LRUCache(2);
//       lRUCache.put(1,1);
//       lRUCache.put(2,2);
//       lRUCache.get(1);
//       lRUCache.put(3,3);
//       lRUCache.get(2);
//       lRUCache.put(4,4);
//       lRUCache.get(1);
//       lRUCache.get(3);
//       lRUCache.get(4);

//       String[] commands = {"LRUCache","put","get"};
//       LRUCache lRUCache = new LRUCache(1);
//       lRUCache.put(2,1);
//       lRUCache.get(2);

//       String[] commands = {"LRUCache","put","get","put","get","get"};
//       LRUCache lRUCache = new LRUCache(1);
//       lRUCache.put(2,1);
//       lRUCache.get(2);
//       lRUCache.put(3,2);
//       lRUCache.get(2);
//       lRUCache.get(3);


       LRUCache lRUCache = new LRUCache(2);
       lRUCache.put(2,1);
       lRUCache.put(2,2);
       lRUCache.get(2);
       lRUCache.put(1,1);
       lRUCache.put(4,1);
       lRUCache.get(2);

       // Pay attention:
       // Here we always copy the linkNode twice in order to keep the reference(head pointer) and current pointer,
       // but if any case set the reference(head pointer) to Null, the reference will be lost ,any current pointer change won't reflect back to the head pointer any more

      //others' solution
      //Add from head, remove from tail
       String[] commands = {"LRUCache","put","put","get","put","get","put","get","get","get"};
       LRUCacheOthers lRUCacheOthers = new LRUCacheOthers(2);
       lRUCacheOthers.put(1,1);
       lRUCacheOthers.put(2,2);
       lRUCacheOthers.get(1);
       lRUCacheOthers.put(3,3);
       lRUCacheOthers.get(2); //wrong
       lRUCacheOthers.put(4,4);
       lRUCacheOthers.get(1);
       lRUCacheOthers.get(3);
       lRUCacheOthers.get(4);
        */

       /**
        * 105. Construct Binary Tree from Preorder and Inorder Traversal
        *  I followed others' solution to implement it,but some bugs, I fixed it
        *
        *
//        int[] preorder = {3,9,20,15,7};//middle->left->right
//        int[] inorder = {9,3,15,20,7};//left->middle->right

        int[] preorder = {1,2};
        int[] inorder = {1,2};
        TreeNode result= buildTreeOthers(preorder,inorder);//
        System.out.println("123");
        */

        /**
         * 106. Construct Binary Tree from Inorder and Postorder Traversal
         *
        int[] postorder = {9,15,7,20,3};//left->right->middle
        int[] inorder = {9,3,15,20,7};//left->middle->right
        TreeNode result= buildTreePost(postorder,inorder);//
        System.out.println("123");
        */


        /**
         * 117. Populating Next Right Pointers in Each Node II
         *
        NodeBinaryTree root = new NodeBinaryTree();
        root.val = 1;
        root.left = new NodeBinaryTree(2);
        root.left.left = new NodeBinaryTree(4);
        root.left.right = new NodeBinaryTree(5);
        root.right = new NodeBinaryTree(3);
        root.right.right = new NodeBinaryTree(7);
        NodeBinaryTree result= connectOthers(root);//
        System.out.println("123");
        */

        // most difficult part of tree related problems is how to maintain the head pointer reference

       /**
        * 114. Flatten Binary Tree to Linked List

       TreeNode root = new TreeNode(1);
       root.left = new TreeNode(2);
       root.right = new TreeNode(5);
       root.left.left = new TreeNode(3);
       root.left.right = new TreeNode(4);
       root.right.right = new TreeNode(6);
//       flatten(root);
//       flatten2(root);
       flattenOthers(root);
       */

       /**
        * 129. Sum Root to Leaf Numbers
        *
       TreeNode root = new TreeNode(4);
       root.left = new TreeNode(9);
       root.right = new TreeNode(0);
       root.left.left = new TreeNode(5);
       root.left.right = new TreeNode(1);
       sumNumbers(root);
       sumNumbersOthers(root);
       */


       /**
        * 173. Binary Search Tree Iterator
        * binary search tree BST
        *
        String[] commandsStrArray = {"BSTIterator", "next", "next", "hasNext", "next", "hasNext", "next", "hasNext", "next", "hasNext"};
        TreeNode root =  new TreeNode(7);
        root.left = new TreeNode(3);
        root.right = new TreeNode(15);
//        root.right.left = new TreeNode(9);
        root.right.right = new TreeNode(20);
//        BSTIterator bSTIterator = new BSTIterator(root);
        BSTIteratorOthers bstIteratorOthers = new BSTIteratorOthers(root);// pretty much similar with mine, but much efficiency, I iterated all the nodes at the initialization stage no matter I really loop to that node or not , others solution only iterate the node when the object invoke
        bstIteratorOthers.next();
        bstIteratorOthers.next();
        bstIteratorOthers.hasNext();
        bstIteratorOthers.next();
        bstIteratorOthers.hasNext();
        */


        /**
         * 222. Count Complete Tree Nodes
         *

       TreeNode root =  new TreeNode(1);
       root.left = new TreeNode(2);
       root.right = new TreeNode(3);
       root.left.left = new TreeNode(4);
       root.left.right = new TreeNode(5);
       root.right.left = new TreeNode(6);
//       countNodes(root);//my solution is inorder left->middle->right  //my solution is better than others
       countNodesOthers(root);//Others' solution is iterate the all the nodes by looping nodes one line by one line
         */

       /**
        * 236. Lowest Common Ancestor of a Binary Tree
        * */
       TreeNode root =  new TreeNode(3);
       root.left = new TreeNode(5);
       root.right = new TreeNode(1);
       root.left.left = new TreeNode(6);
       root.left.right = new TreeNode(2);
       root.left.right.left = new TreeNode(7);
       root.left.right.right = new TreeNode(4);
       root.right.left = new TreeNode(0);
       root.right.right = new TreeNode(8);
       TreeNode p = new TreeNode(7);
       TreeNode q = new TreeNode(8);
//       lowestCommonAncestor(root,p,q);//correct ,but limit memory exceeded
       lowestCommonAncestorOthers(root,p,q);//difficult,my solution is much more straight forward

   }
}
