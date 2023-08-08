package com.qingmin.test;

import org.javatuples.Triplet;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

/**
 * Hello world!
 *
 */
public class App {
    static List<String> ans = new ArrayList<>();

    static void traceback(StringBuilder pt, int left, int right, int n) {
        if (left == n && right == n) {
            System.out.println("pt:"+new String(pt));
            ans.add(new String(pt));
            return;
        }
        if (left < n) {
            System.out.println("left:"+String.valueOf(left));
            pt.append('(');
            traceback(pt, left + 1, right, n);
            pt.deleteCharAt(pt.length() - 1);
        }
        if (right < left) {
            System.out.println("right:"+String.valueOf(right));
            pt.append(')');
            traceback(pt, left, right + 1, n);
            pt.deleteCharAt(pt.length() - 1);
        }

    }
//
//    public static long fibonacci(long number) {
//        if ((number == 0) || (number == 1))
//            return number;
//        else
//            return fibonacci(number - 1) + fibonacci(number - 2);
//    }

    public static boolean reachingPoints(int sx, int sy, int tx, int ty) {
        if (tx < sx || ty < sy) return false;
        if (tx == sx && (ty - sy) % sx == 0) return true;
        if (ty == sy && (tx - sx) % sy == 0) return true;
        return reachingPoints(sx, sy, tx % ty, ty % tx);
    }

    static void traceback2(boolean xFirstStatus,boolean status,ArrayList<Integer> sumList,ArrayList<Integer> xIndex,ArrayList<Integer> yIndex, int sx, int sy, int tx, int ty) {
        if (sx == tx && sy == ty) {
            status = true;
        }
        if (sx < tx && xFirstStatus) {
            sumList.add(sx+sy);
            xIndex.add(sumList.size()-1);
            traceback2(xFirstStatus,status,sumList,xIndex,yIndex,sumList.get(sumList.size()-1),sy,tx,ty);
//            System.out.println("left:"+String.valueOf(left));
//            pt.append('(');
//            x.add(sx + sy);
//            xIndex++;
//            y.add(x.get(xIndex)+sy);
//            xIndex
//            traceback2(x,y,sx + sy, sy, tx,ty);
//            pt.deleteCharAt(pt.length() - 1);
        }
        if (sx < tx && !xFirstStatus) {
            sumList.add(sx+sy);
            yIndex.add(sumList.size()-1);
            traceback2(xFirstStatus,status,sumList,xIndex,yIndex,sx,sumList.get(sumList.size()-1),tx,ty);
//            System.out.println("left:"+String.valueOf(left));
//            pt.append('(');
//            x.add(sx + sy);
//            xIndex++;
//            y.add(x.get(xIndex)+sy);
//            xIndex
//            traceback2(x,y,sx + sy, sy, tx,ty);
//            pt.deleteCharAt(pt.length() - 1);
        }
        if(sy==ty && sx < tx){
            sx=sx+sumList.get(yIndex.get(yIndex.size()-1));
            sumList.add(sx);
            xIndex.add(sumList.size()-1);
            status=false;
            traceback2(xFirstStatus,status,sumList,xIndex,yIndex,sx,sy,tx,ty);

        }
        if (sx == tx && sy < ty) {

                sy=sy+sumList.get(xIndex.get(xIndex.size()-1));
//                if(sy<ty){
                sumList.add(sy);
                yIndex.add(sumList.size()-1);
                status=false;
                traceback2(xFirstStatus,status,sumList,xIndex,yIndex,sx,sy,tx,ty);
//                }else if(sy==ty){
//                    status=true;
//                }else{
//
//                    status=false;
//                }

////            System.out.println("right:"+String.valueOf(right));
////            pt.append(')');
//            traceback2(x, y, sx,sx+sy,tx,ty);
//            pt.deleteCharAt(pt.length() - 1);
        }
        if(sx == tx && sy > ty){
            xFirstStatus=false;
            int maxXIndex = xIndex.get(xIndex.size()-1);
            xIndex.remove(xIndex.size()-1);
            List<Integer> yIndexTmp = new ArrayList<>();
            for(int i=0;i<yIndex.size(); i++){
                if(yIndex.get(i)<maxXIndex){
                    yIndexTmp.add(yIndex.get(i));
                }
            }
            yIndex = (ArrayList<Integer>) yIndexTmp;
            List<Integer> sumListTmp = new ArrayList<>();
            for(int i=0;i<sumList.size();i++){
                if(i<maxXIndex){
                    sumListTmp.add(sumList.get(i));
                }
            }
            sumList = (ArrayList<Integer>) sumListTmp;
            traceback2(xFirstStatus,status,sumList,xIndex,yIndex,sumList.get(xIndex.get(xIndex.size()-1)),sumList.get(yIndex.get(yIndex.size()-1)),tx,ty);

        }

    }

    public static int[] reverse(int data[], int left, int right)
    {

        // Reverse the sub-array
        while (left < right) {
            int temp = data[left];
            data[left++] = data[right];
            data[right--] = temp;
        }

        // Return the updated array
        return data;
    }

    public static int[] swap(int data[], int left, int right)
    {

        // Swap the data
        int temp = data[left];
        data[left] = data[right];
        data[right] = temp;

        // Return the updated array
        return data;
    }

    static boolean checkFullAStatus(char[] strCharArray) {
        boolean aStatus = false;
        for(Character elem:strCharArray){
            if(!(elem=='a')){
                aStatus=false;
                break;
            }else{
                aStatus=true;
            }
        }

        return aStatus;
    }

    public static void main(String[] args) {
//        int prices [] ={983,341,957,541,470,660,118,742,334,822,165,145,730,656,567,25,684,113,351,295,468,918,587,4,399,220,11,222,777,127,135,688,267,570,342,748,382,428,340,35,896,846,376,655,147,891,198,420,729,685,989,543,285,822,254,878,380,758,490,73,870,328,234,489,990,387,688,12,795,746,275,371,321,298,186,925,845,816,775,647,379,15,602,756,619,256,106,312,965,661,973,147,437,796,56,955,846,245,502,889,557,281,936,812,880,880,834,186,303,96,706,634,464,232,170,188,527,637,847,293,726,146,441,973,380,61,956,599,626,206,284,815,36,591,166,690,454,700,458,808,410,252,365,953,650,609,438,106,582,82,925,847,800,128,615,440,956,142,488,350,474,762,435,37,970,850,260,893,996,321,9,884,581,783,41,157,650,815,644,558,320,267,616,295,47,251,573,713,636,32,797,170,499,922,262,929,188,50,768,934,658,797,891,305,443};
//        int prices [] ={7,1,5,3,6,4};
//        int i, x,j;   //1s 786ms
//        int max_profit_value = 0;
//        int index=0;
//        int outdex=0;
//
//        for (i = 0; i < prices.length; i++) { // o(n^2)
//            for (j=i; j < prices.length; j++){
//                if(prices[j]-prices[i]>=max_profit_value){
//                    max_profit_value=prices[j]-prices[i];
//                    index=i;
//                    outdex=j;
//                }
//            }
//
//        }
//        System.out.println(index);
//        System.out.println(outdex);
//        System.out.println(max_profit_value);


//        int maxProfit = 0;  //1s805ms
//        int minPrice = Integer.MAX_VALUE;
//        int index=0;
//        int outdex = 0;
//
//        for (int i= 0; i < prices.length; i++) {   //o(n)
//            if (prices[i] < minPrice) {
//                minPrice = prices[i];
//                index = i;
//            } else if (prices[i] - minPrice > maxProfit) {
//                maxProfit =prices[i]-minPrice;
//                outdex=i;
//            }
//        }
//
////        return maxProfit;
//        System.out.println(index);
//        System.out.println(outdex);
//        System.out.println(maxProfit);


//        int i, x , j, k;
//        int max_profit_value = 0;
//        int index=0;
//        int outdex=0;

//        for (i = 0; i < prices.length; i++) { // o(n^2)
//            for (j=i; j < prices.length; j++){
//                if(prices[j]-prices[i]>=max_profit_value){//                    max_profit_value=prices[j]-prices[i];
//                    index=i;
//                    outdex=j;
//                }
//            }
//
//        }

//        int prices [] ={7,1,5,3,6,4};
//        int maxProfit = 0;  //1s805ms
//        int minPrice = Integer.MAX_VALUE;
//        int index=0;
//        int outdex = 0;
//        Triplet<Integer, Integer, Integer> triplet_benifit_tmp = new Triplet<Integer, Integer, Integer>(0,0,0);
//
//        ArrayList<Triplet> all_possible_benifit = new ArrayList<Triplet>();
//
//        for (int i= 0; i < prices.length; i++) {   //o(n)
//            for(int j=i;j<prices.length;j++){
//                Triplet<Integer, Integer, Integer> triplet_benifit = new Triplet<Integer, Integer, Integer>(i,j,prices[j]-prices[i]);
//                all_possible_benifit.add(triplet_benifit);
//
//                }
//        }
//        System.out.println("123");
//        for(int i = 0; i < all_possible_benifit.size(); i++){
//            triplet_benifit.g
//        }


//        int[] intArray=new int[]{25,2,3,57,38,41};
//        StringBuilder emptyStrBuilder = new StringBuilder("");
//        for(int intElem:intArray){
//            emptyStrBuilder.append(String.valueOf(intElem));
//        }
//
//        Map<Character, Integer> map1 = new HashMap<>();
//        LinkedHashMap<Character, Integer> sortedMap = new LinkedHashMap<>();
//
//        String emptyStr = emptyStrBuilder.toString();
//        char[] chArray = emptyStr.toCharArray();
//
//        for (int i = 0; i < chArray.length; i++) {
//            if(map1.containsKey(chArray[i])){
//                map1.put(chArray[i],map1.get(chArray[i])+1);
//            }else{
//                map1.put(chArray[i],1);
//            }
//            // Print current character
//            System.out.print(chArray[i] + " ");
//        }
//
//        ArrayList<Integer> list1 = new ArrayList<>();
//        List<Integer> sort_list_max = new ArrayList<>();
//        for (Map.Entry<Character, Integer> entry : map1.entrySet()) {
//            list1.add(entry.getValue());
//        }
//
//        Collections.sort(list1);
//        int max_value = list1.get(list1.size() - 1);
//        int max_index = list1.indexOf(max_value);
//        sort_list_max = list1.subList(max_index,list1.size());
//
//        for (int num : sort_list_max) {
//            for (Entry<Character, Integer> entry : map1.entrySet()) {
//                if (entry.getValue().equals(num)) {
//                    sortedMap.put(entry.getKey(), num);
//                }
//            }
//        }
//
//        System.out.println(emptyStr);


//        List<String> wordList = new ArrayList<>(); //["hot","dot","dog","lot","log","cog"]
//        wordList.add("hot");
//        wordList.add("dot");
//        wordList.add("dog");
//        wordList.add("hog");
////        wordList.add("log");
//        wordList.add("cog");
//        String beginWord = "hit";
//        String endWord = "cog";
//
//
//        Set<String> set = new HashSet<>(wordList);
//        Queue<String> queue = new LinkedList<>();
//        queue.offer(beginWord);
//        int step = 1;
//        int n = beginWord.length();
//        outerloop:
//        while(!queue.isEmpty()) {
//            int size = queue.size();
//            for(int i = 0; i < size; i++) {
//                System.out.println("i:"+String.valueOf(i));
//                String temp = queue.poll();
//                if(temp.equals(endWord)){
//                    break outerloop;
//                }
//                for(int j = 0; j < n; j++) {
//                    for(char c = 'a'; c <= 'z'; c++) {
//                        StringBuilder sb = new StringBuilder(temp);
//                        sb.setCharAt(j, c);
//                        String newStr = sb.toString();
//                        if(set.contains(newStr)) {
//                            if(newStr.equals(endWord)) {
//                                step=step + 1;
//                                System.out.println("step:"+String.valueOf(step));
//                                break outerloop;
//                            }
//                            set.remove(newStr);
//                            queue.offer(newStr);
//                            System.out.println("newStr:"+newStr);
//                        }
//                    }
//                    System.out.println("j:"+String.valueOf(j));
//                    System.out.println("++++++++++++++++++++++++++++++++++");
//                }
//            }
//            step++;
//            System.out.println("step:"+String.valueOf(step));
//        }


//        String str="({[]}[]())";
//        String[] strArrayStandard = {"()","[]","{}"};
//        char[] charArray = str.toCharArray();
//        List<Character> charList = new ArrayList<>();
//        for(char elem:charArray){
//            charList.add(elem);
//        }
//        Set<Character> charSet = new HashSet<Character>(charList);
//
//        boolean status = false;
//
//        int eleNum = charArray.length;
//        if (eleNum%2==0){
//            Stack<Character> stackCharLeft = new Stack<Character>();
//            for(int i=0; i<charArray.length/2;i++){
//                System.out.println("i:"+String.valueOf(i));
//                stackCharLeft.add(charArray[i]);
//            }
//            Stack<Character> stackCharRight = new Stack<Character>();
//            for(int j=charArray.length-1;j>=charArray.length/2;j--){
//                System.out.println("j:"+String.valueOf(j));
//                stackCharRight.add(charArray[j]);
//            }
//            while(!stackCharLeft.empty()){
//                char leftPopElem = stackCharLeft.pop();
//                char rightPopElem = stackCharRight.pop();
//                String concatPair = String.valueOf(leftPopElem).concat(String.valueOf(rightPopElem));
//                if(Arrays.asList(strArrayStandard).contains(concatPair)){
//                    status = true;
//                }else{
//                    char leftPopElemContinue = stackCharLeft.pop();
//                    String concatPairContinue = String.valueOf(leftPopElemContinue).concat(String.valueOf(leftPopElem));
//                    if(Arrays.asList(strArrayStandard).contains(concatPairContinue)){
//                        status = true;
//                        stackCharRight.push(rightPopElem);
//                    }else{
//                        status = false;
//                        break;
//                    }
//                }
//            }
//            while(!stackCharRight.empty()){
//                char rightPopElem = stackCharRight.pop();
//                char rightPopElemContinue = stackCharRight.pop();
//                String concatPairContinue = String.valueOf(rightPopElem).concat(String.valueOf(rightPopElemContinue));
//                if(Arrays.asList(strArrayStandard).contains(concatPairContinue)){
//                    status = true;
//                }else{
//                    status = false;
//                    break;
//                }
//            }
//        }else{
//            status = false;
//        }
//        System.out.println("Status:"+String.valueOf(status));
//    }//Wrong


//        HashMap<Character, Character> map =new HashMap<Character, Character> ();
//        map.put(')', '(');
//        map.put('}', '{');
//        map.put(']', '[');
//
//        boolean status = false;
//
//        String s = "({[]}[]())";
//        Stack<Character> stack = new Stack<Character>();
//        for (int i = 0; i < s.length(); i++) {
//                char c = s.charAt(i);
//                if (map.containsKey(c)) {
//                    char topEle = stack.empty()? '#':stack.pop();
//                    if (topEle != map.get(c)) {
//                        status =  false;
//                    }
//                }else {
//                    stack.push(c);
//                }
//        }
////            return stack.isEmpty();

//        int n=2;
//        char cL='{';
//        char cR='}';
//        List<String> sList = new ArrayList<>();
//        Stack<Character> stackLeft = new Stack<Character>();
//        Stack<Character> stackRight = new Stack<Character>();
//        for(int i=1;i<=n;i++){
//            List<Character> charListL = new ArrayList<>();
//            List<Character> charListR = new ArrayList<>();
//            for(int j=1;j<=i;j++){
//                charListL.add(cL);
//                charListR.add(cR);
//            }
//            stackLeft.addAll(charListL);
//            stackRight.addAll(charListR);
//            int countPopTimesL = 0;
//            int countPopTimesR = 0;
//            String concatStr = null;
//            while(countPopTimesL<i||countPopTimesR<i){
//                concatStr = String.valueOf(stackLeft.pop());
//                countPopTimesL++;
//                if(countPopTimesL>=countPopTimesR){
//                    concatStr = concatStr.concat(String.valueOf(stackRight.pop()));
//                    countPopTimesR++;
//                }
//
//            }
//            sList.add(concatStr);
//
//
//        }

//        int n = 3;
//        traceback(new StringBuilder(), 0, 0, n);
//        return ans;




//            int[] nums ={3,2,3,1};
//            int[] resultIndices = {};
//            int target = 6;
//            for(int i=0;i<nums.length;i++){
//                for(int j=i+1;j<nums.length;j++){
//                    if(nums[i]+nums[j]==target){
//                        resultIndices[0]=i;
//                        resultIndices[1]=j;
//                    }
//                }
//            }


//        //Approach #1  Done pretty much similiar, my approach much more straight forward
//        Integer[] nums ={4,3,5,4,1,3,2};//->3312//4325413
//        List<Tuple> gapList =  new ArrayList<>();
//        List<Tuple> gapList2 =  new ArrayList<>();
//        List<Integer> finalList = new ArrayList<>();
//        List<Integer> unsort = new ArrayList<>();
//        int PreIndex = 0;
//        int LatterIndex = 0;
//        int indexGapMin = 99999;
//        for(int i=0;i<nums.length;i++){
//            for(int j=i+1;j<nums.length;j++){
//                int gap = nums[j]-nums[i];
//                Triplet<Integer, Integer, Integer> triplet = Triplet.with(gap, i,j);
//                gapList.add(triplet);
//
//            }
//
//        }
//        int minGapLarger0 =9999;
//        for(int i=0;i<gapList.size();i++){
//            if(Integer.valueOf(gapList.get(i).getValue(0).toString())>0){
//                if(Integer.valueOf(gapList.get(i).getValue(2).toString())>=LatterIndex){
//                    LatterIndex = Integer.valueOf(gapList.get(i).getValue(2).toString());
//
//                }
//                gapList2.add(gapList.get(i));
////                minGapLarger0 = Integer.valueOf(gapList.get(i).getValue(0).toString());
////                minPreIndex = Integer.valueOf(gapList.get(i).getValue(1).toString());
////                minLatterIndex = Integer.valueOf(gapList.get(i).getValue(2).toString());
//            }
//        }
//
//        for(int i=0;i<gapList2.size();i++){
//            if(Integer.valueOf(gapList2.get(i).getValue(2).toString())==LatterIndex){
//                int preIndexTmp = Integer.valueOf(gapList2.get(i).getValue(1).toString());
//                int indexGapTmp = LatterIndex-preIndexTmp;
//                if(indexGapTmp<=indexGapMin){
//                    indexGapMin = indexGapTmp;
//                    PreIndex = preIndexTmp;
//                }
//            }
//        }
//        if (gapList2.size()==0){
//            List<Integer> numslist = Arrays.asList(nums);
//            Collections.sort(numslist);
//            finalList.addAll(numslist);
//        }else {
//            int tmp = nums[PreIndex];
//            nums[PreIndex] = nums[LatterIndex];
//            nums[LatterIndex] = tmp;
//
//            for (int i = 0; i <= PreIndex; i++) {
//                finalList.add(nums[i]);
//            }
//
//            for (int i = PreIndex + 1; i < nums.length; i++) { //remining elements is a sort with asc order
//                unsort.add(nums[i]);
//            }
//            Collections.sort(unsort);
//
//            finalList.addAll(unsort);
//        }
//
//        finalList.toString();
//
//        System.out.println("finalList:"+finalList.toString());




////Approach 2
//        Integer[] nums ={1,2,3};//->3312
//        List<Tuple> gapList =  new ArrayList<>();
//        List<Integer> finalList = new ArrayList<>();
//        List<Integer> unsort = new ArrayList<>();
//        int minPreIndex = 0;
//        int minLatterIndex = 0;
//        for(int i=0;i<nums.length;i++){
//            for(int j=i+1;j<nums.length;j++){
//                int gap = nums[j]-nums[i];
//                Triplet<Integer, Integer, Integer> triplet = Triplet.with(gap, i,j);
//                gapList.add(triplet);
//
//            }
//
//        }
//        int minGapLarger0 =9999;
//        for(int i=0;i<gapList.size();i++){
//            if(Integer.valueOf(gapList.get(i).getValue(0).toString())>0 && Integer.valueOf(gapList.get(i).getValue(0).toString())<=minGapLarger0){
//                minGapLarger0 = Integer.valueOf(gapList.get(i).getValue(0).toString());
//                minPreIndex = Integer.valueOf(gapList.get(i).getValue(1).toString());
//                minLatterIndex = Integer.valueOf(gapList.get(i).getValue(2).toString());
//            }
//        }
//        if (minGapLarger0==9999){
//            List<Integer> numslist = Arrays.asList(nums);
//            Collections.sort(numslist);
//            finalList.addAll(numslist);
//        }else {
//            int tmp = nums[minPreIndex];
//            nums[minPreIndex] = nums[minLatterIndex];
//            nums[minLatterIndex] = tmp;
//
//            for (int i = 0; i <= minPreIndex; i++) {
//                finalList.add(nums[i]);
//            }
//
//            for (int i = minPreIndex + 1; i < nums.length; i++) { //remining elements is a sort with asc order
//                unsort.add(nums[i]);
//            }
//            Collections.sort(unsort);
//
//            finalList.addAll(unsort);
//        }
//
//        finalList.toString();
//
//        System.out.println("finalList:"+finalList.toString());
//        boolean status =  false;
//        int[] data={4,3,5,4,1,3,2};
//        if (data.length <= 1)
//            status=false;
//
//        int last = data.length - 2;
//
//        // find the longest non-increasing suffix
//        // and find the pivot
//        while (last >= 0) {
//            System.out.println("last:"+Integer.valueOf(last));
//            if (data[last] < data[last + 1]) { // find the minimum index gap which latter index's value minus pre index's value>0  similiar to my second step
//                break;
//            }
//            last--;
//        }
//
//        // If there is no increasing pair
//        // there is no higher order permutation
//        if (last < 0)
//            status=false;
//
//        int nextGreater = data.length - 1;
//
//        // Find the rightmost successor to the pivot
//        for (int i = data.length - 1; i > last; i--) {//similiar
//            if (data[i] > data[last]) {
//                nextGreater = i;
//                break;
//            }
//        }
//
//        // Swap the successor and the pivot
//        data = swap(data, nextGreater, last);// I also implemeted this step
//
//        // Reverse the suffix
//        data = reverse(data, last + 1, data.length - 1);
//        System.out.println("data:"+ Arrays.asList(data).toString());//4354213
//        // Return true as the next_permutation is done
//        status=true;

//        int[] time = {30,20,150,100,40};
//        int pairCount =0;
//        for(int i=0;i<time.length;i++){
//            for(int j=i+1;j< time.length;j++){
//                if((time[i]+time[j])%60==0){
//                    pairCount=pairCount+1;
//                }
//            }
//        }
//        System.out.println("pairCount:"+ String.valueOf(pairCount));

//        int[] time = {30,20,150,100,40,80,};
//        Map<Integer, Integer> map = new HashMap<>();  // all the possible remainders' corresponding appear the times
//        int count = 0;
//        for (int t : time) {
//            float abc= (60 - t % 60) % 60;
////            float bce= t % 60;
////            float cef=map.getOrDefault(t % 60, 0);
//            float efg= map.getOrDefault((60 - t % 60) % 60, 0);
//            count += map.getOrDefault((60 - t % 60) % 60, 0);
//            map.put(t % 60, map.getOrDefault(t % 60, 0) + 1);
//        }
//        System.out.println("count:"+ String.valueOf(count));

//          String str = "bbb";
//          if(str.length()==1){
//            System.out.println("This string is not a palindrome");
//          }
//
//
//          char[] strCharArray = str.toCharArray();
//
//          if(strCharArray.length>=2){//fibo
//
//              if(!checkFullAStatus(strCharArray)){
//                  for (int i = 0; i < strCharArray.length / 2; i++) {
//                      if (strCharArray[i] != strCharArray[strCharArray.length - 1 - i]) {
//                          System.out.println("This string is not a palindrome");
//                      }
//                  }
//                  if(strCharArray.length%2==0) {
//                      Set<Integer> indexNotA = new HashSet<>();
//                      for (int i = 0; i < strCharArray.length; i++) {
//                          if (strCharArray[i] != 'a') {
//                              indexNotA.add(i);
//                          }
//                      }
//                      boolean status = true;
//                      for (int indexElem : indexNotA) {
//                          char[] tmpArray = new char[strCharArray.length];
//                          tmpArray=strCharArray;
//                          tmpArray[indexElem]='a';
//                          if(!checkFullAStatus(tmpArray)) {
//                              Map<Boolean,Integer> tmpMap = new HashMap();
//                              int tmpCount=0;
//                              for (int i = 0; i < strCharArray.length / 2; i++) {
//                                  if (strCharArray[i] == strCharArray[strCharArray.length - 1 - i]) {
//                                      tmpMap.put(true,tmpMap.getOrDefault(true,0)+1);
//                                  }
//                              }
//                              if (tmpMap.getOrDefault(true,0)==strCharArray.length/2) {
//                              } else {
//                                  System.out.println("result:" + String.valueOf(strCharArray));
//                              }
//
//                          }
//                      }
//                  }else{
//                      Set<Integer> indexNotA = new HashSet<>();
//                      for (int i = 0; i < strCharArray.length; i++) {
//                          if (strCharArray[i] != 'a') {
//                              indexNotA.add(i);
//                          }
//                      }
//                      boolean status = true;
//                      for (int indexElem : indexNotA) {
//                          char[] tmpArrayHead = new char[strCharArray.length];
//                          tmpArrayHead=strCharArray.clone();//clonecopy   if you wanna use deep copy import copy package
//                          tmpArrayHead[indexElem]='a';
//                          if(!checkFullAStatus(tmpArrayHead)) {
//                              Map<Boolean,Integer> tmpMap = new HashMap();
//                              int tmpCount=0;
//                              for (int i = 0; i < tmpArrayHead.length / 2; i++) {
//                                  if (tmpArrayHead[i] == tmpArrayHead[tmpArrayHead.length - 1 - i]) {
//                                      tmpMap.put(true,tmpMap.getOrDefault(true,0)+1);
//                                  }
//                              }
//                              if (tmpMap.getOrDefault(true,0)==strCharArray.length/2) {
//                              } else {
//                                  System.out.println("result" + String.valueOf(tmpArrayHead));
//                              }
//
//                          }else{
//                              strCharArray[strCharArray.length-1]='b';
//                              System.out.println("result"+String.valueOf(strCharArray));
//
//                          }
//                      }
//
//                  }
//              }else{//correct
//                  strCharArray[strCharArray.length-1]='b';
//                  System.out.println("result"+String.valueOf(strCharArray));
//              }
//          }

//        String palindrome="abb";  //Wrong,not a complete solution   the requirement is the smallest one, it list all possible combinations
//        if (palindrome.length() == 1) {
//        }
//        for (int i = 0; i < palindrome.length(); i++) {
//            if (palindrome.charAt(i) != 'a' &&
//                    (palindrome.length() % 2 == 0 || i != palindrome.length() / 2)) { // the middle one no need to change, in order to make sure the changed str is not a palindrome
//                System.out.println("result : "+palindrome.substring(0, i) + "a" + palindrome.substring(i + 1));
//            }
//        }
//        System.out.println("result : "+palindrome.substring(0, palindrome.length() - 1) + "b");


        /*** 1481. Least Number of Unique Integers after K Removals ***/
//        int[] orginalArray = {4,3,1,1,3,3,2};
//        int k = 3;
//        Map<Integer,Integer> countMap = new HashMap<>();
//        for(int i=0;i<orginalArray.length;i++){
//            countMap.put(orginalArray[i],countMap.getOrDefault(orginalArray[i],0)+1);
//        }
////        int uniqueNumsOriginal = countMap.size();
//        int uniqueNumsAfterDel = 0;
//        if(k>=orginalArray.length){
//            uniqueNumsAfterDel=0;
//        }else{
//            LinkedHashMap<Integer, Integer> sortedCountMap = countMap.entrySet()
//                    .stream()
//                    .sorted(Map.Entry.comparingByValue())
//                    .collect(Collectors.toMap(
//                            Map.Entry::getKey,
//                            Map.Entry::getValue,
//                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
////            for (Map.Entry<Integer, Integer> mapEntries : sortedCountMap.entrySet()) {
////
////                // fetching the values of the HashMap
////                // one at a time and comparing with
////                // the value to be checked
////                if (mapEntries.getValue() == valueToBeChecked)
////                    return true;
////            }
////            hashMap.containsValue(valueToBeChecked);
////            if(k<=sortedCountMap)
//            int accumulateCount = 0;
//            LinkedHashMap<Integer, Integer> sortedCountMapTmp = (LinkedHashMap<Integer, Integer>) sortedCountMap.clone();
//            for (Map.Entry<Integer, Integer> mapEntries : sortedCountMap.entrySet()) {
//                int currentKey = mapEntries.getKey();
//                int currentTimes = mapEntries.getValue();
//                accumulateCount=accumulateCount+currentTimes;
//                if(accumulateCount<=k){
//                    sortedCountMapTmp.remove(currentKey);
//                    uniqueNumsAfterDel = sortedCountMapTmp.keySet().size();
//                }else{
//                    uniqueNumsAfterDel = sortedCountMapTmp.keySet().size();
//                    break;
//                }
//            }
//
//        }


        // Wrong
//        int[][] intervals = {{-52,31},{-73,-26},{82,97},{-65,-11},{-62,-49},{95,99},{58,95},{-31,49},{66,98},{-63,2},{30,47},{-40,-26}};
////        int[][] intervals = [[-52,31],[-73,-26],[82,97],[-65,-11],[-62,-49],[95,99],[58,95],[-31,49],[66,98],[-63,2],[30,47],{-40,-26}};
//        java.util.Arrays.sort(intervals, new java.util.Comparator<int[]>() {
//            public int compare(int[] a, int[] b) {
////                int a0 = a[0];
////                int b0 = b[0];
////                int a1 = a[1];
////                int b1 = b[1];
////
////                // Integer.compare(a1, b1)
////                if (a0 < b0) {
////                    if(abs(a1-a0)<=abs(b1-b0)){
////                        return -1;// negative = less than
////                    }else{
////                        return 1;// positive = greater than
////                    }
////                } else if (a0 == b0) {
////                    if(abs(a1-a0)<abs(b1-b0)){
////                        return -1;// negative = less than
////                    }else if(abs(a1-a0)==abs(b1-b0)){
////                        return 0;
////                    }else{
////                        return 1;
////                    }
////                } else {
////                    return 1;
////                }
//                return Integer.compare(a[1],b[1]);
//            }
//        });
//
//        List<int[]> listIntervalsSort = new ArrayList<>();
//        List<int[]> listIntervalsTmpSort = new ArrayList<>();
//        for(int[] elem:intervals){
//            listIntervalsSort.add(elem);
//            listIntervalsTmpSort.add(elem);
//        }
//
//        int tmpPreMaxIndex=0;
//        int count = 0;
//        int[] tmpPreMaxElem=listIntervalsSort.get(0);
//        for(int i=1;i<listIntervalsSort.size();i++){
//            int[] tmpElem=listIntervalsSort.get(i);
//            if(tmpElem[0]>=tmpPreMaxElem[1]){
//                tmpPreMaxIndex = i;
//                tmpPreMaxElem = tmpElem;
//            }else{
//                if(abs(tmpElem[1]-tmpElem[0])>=abs(tmpPreMaxElem[1]-tmpPreMaxElem[0])){
//                    listIntervalsTmpSort.remove(tmpElem);
//                }else{
//                    listIntervalsTmpSort.remove(tmpPreMaxElem);
//                    tmpPreMaxIndex = i;
//                    tmpPreMaxElem = tmpElem;
//                }
//                count++;
//            }
//
//        }
//
//        System.out.println("result count:"+String.valueOf(count));


//        int[][] intervals = {{-52,31},{-73,-26},{82,97},{-65,-11},{-62,-49},{95,99},{58,95},{-31,49},{66,98},{-63,2},{30,47},{-40,-26}};
//        int count =0;
//        if(intervals.length == 0){
//            count=0;
//        }
//        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
//        int end = intervals[0][1];
//        int counttmp = 1;
//        for(int i = 1; i < intervals.length; i++) {
//            if(intervals[i][0] >= end) {
//                counttmp++;
//                end = intervals[i][1];
//            }
//        }
//        count = intervals.length - counttmp;
//        System.out.println("result count:"+String.valueOf(count));

//          int sx=1,sy=1,tx=3,ty=5;
//          ArrayList<Integer> xIndex =new ArrayList<>();
//          ArrayList<Integer> yIndex =new ArrayList<>();
//          ArrayList<Integer> sumList =new ArrayList<>();
////          int xIndex = 0;
////          int yIndex = 1;
//          int xSumTmp=0;
//          int ySumTmp=0;
//        xIndex.add(0);
//        yIndex.add(1);
//        sumList.add(sx);
//        sumList.add(sy);
//        boolean status =false;
//        boolean xFirstStatus = true;

//          while(xSumTmp!=tx&&ySumTmp!=ty){
//               sx=sx+sy;
//               sy=sx+sy;
//          }
//
//
//          for (int counter = 0; counter <= 10; counter++){
//                System.out.printf("Fibonacci of %d is: %d\n", counter, fibonacci(counter));
//          }
//          StringBuilder sb=new StringBuilder();
//          traceback2(xFirstStatus,status,sumList,xIndex,yIndex,sx,sy,tx,ty);

//         reachingPoints(sx,sy,tx,ty);

        /***2038. Remove Colored Pieces if Both Neighbors are the Same Color
         * Mine Start
         String colors = "ABBB";
        int turnPerson=1;//1:alice, 0 Bob;
        int alicePosition = 0;
        int bobPosition = 0;
        String winPerson="";
        outloop:
        while(true) {
            if(colors.length()<=2){
                if (turnPerson == 1) {
                    winPerson="Bob";
                }else{
                    winPerson="Alice";
                }
            }
            for (int i = 0; i < colors.length(); i++) {
                if (!((i == 0) || (i == colors.length() - 1))) {
                    if (turnPerson == 1) {
                        if (colors.charAt(i - 1) == 'A' && colors.charAt(i + 1) == 'A'&& colors.charAt(i)=='A') {
                            StringBuilder sb = new StringBuilder(colors);
                            sb.deleteCharAt(i);
                            colors = sb.toString();
                            turnPerson = 0;
                            winPerson = "";
                            break;
                        } else {
                            turnPerson = 1;
                            winPerson = "Bob";
                        }
                    } else {
                        if (colors.charAt(i - 1) == 'B' && colors.charAt(i + 1) == 'B'&& colors.charAt(i)=='B') {
                            StringBuilder sb = new StringBuilder(colors);
                            sb.deleteCharAt(i);
                            colors = sb.toString();
                            turnPerson = 1;
                            winPerson = "";
                            break;
                        } else {
                            turnPerson = 0;
                            winPerson = "Alice";
                        }
                    }
                }
                if(i==colors.length()-1){
                    break outloop;
                }
            }

        }
        System.out.println("winPerson:"+String.valueOf(winPerson));
         * Mine End

         * Correct Solution Start
        int countAAA = 0;
        int countBBB = 0;
        String colors = "BB";
        boolean winPerson = true;
        if(colors.length()<=2){
            countAAA++;
        }

        for (int i = 1; i + 1 < colors.length(); ++i)
            if (colors.charAt(i - 1) == colors.charAt(i) && colors.charAt(i) == colors.charAt(i + 1))
                if (colors.charAt(i) == 'A')
                    ++countAAA;
                else
                    ++countBBB;
        winPerson = countAAA>countBBB?true:false;
         * Correct Solution Start
         ***/


        /*** 1529. Minimum Suffix Flips
        String target="000000";
        int notZeroIndex = target.indexOf("1");
        int count = 0;
        if(notZeroIndex==-1){
            System.out.println("count:"+String.valueOf(count));
        }else{
            String subNeedCalculateStr = target.substring(notZeroIndex);
            count=count+1;
            int preChar = subNeedCalculateStr.charAt(0);
            for(int i=1;i<subNeedCalculateStr.length();i++){
                if(subNeedCalculateStr.charAt(i)!=preChar){
                    count=count+1;
                    preChar = subNeedCalculateStr.charAt(i);
                }
            }
            System.out.println("count:"+String.valueOf(count));
        }
         ***/


        /************************* ebay ******************************/
        /*** 3. Longest Substring Without Repeating Characters
        //Mine Start --> timeout     From long substring to short substring gradually
        String s = "imuwmpvyhnukmauohooedhtfhxfvsaldmfbauwzdjvpwvlkccyjfhckrcpouszzeeahbwgeibwezhqdjrmoyrzrvqgpkfrpmry";//length=8
        int longestLength = 0;
        outloop:
        for(int i=s.length();i<=s.length();i--){ //i=7 0-6 1-7; i=6 0-5 1-6 2-7;
            for(int j=0;j+i<=s.length();j++){
                String subStrTmp = s.substring(j,i+j);
                Map<Character,Integer> mapTmp = new HashMap<Character,Integer>();
                for(int k=0;k<subStrTmp.length();k++){
                    mapTmp.put(subStrTmp.charAt(k),mapTmp.getOrDefault(subStrTmp.indexOf(i),0)+1);
                }
                if(mapTmp.keySet().size()==i){
                    longestLength = i;
                    break outloop;
                }

            }
        }
        System.out.println("longestLength:"+String.valueOf(longestLength));
        // Mine End --> timeout


        String s = "imuwmpvyhnukmauohooedhtfhxfvsaldmfbauwzdjvpwvlkccyjfhckrcpouszzeeahbwgeibwezhqdjrmoyrzrvqgpkfrpmry";
        int maxLen = 0, start = 0, sLen = s.length(); //From short substring to long substring gradually
        HashMap<Character, Integer> hitMap = new HashMap<>();   //Char -> First time appear index
        for (int i = 0; i < sLen; i++) {
            char ch = s.charAt(i);
            if (hitMap.containsKey(ch) && hitMap.get(ch) >= start) {
                start = hitMap.get(ch) + 1;
            }
            hitMap.put(ch, i);
            maxLen = Math.max(maxLen, i - start + 1);
            if (start + maxLen >= sLen) {
                break;
            }
        }
        System.out.println("maxLen:"+String.valueOf(maxLen));
        ***/

        /*** 17. Letter Combinations of a Phone Number
         *
         */
         //Mine Start  it's not a good way and time-comsuming
//        Map<Integer,String> initMap = new HashMap<>();
//        StringBuilder resultTmp = new StringBuilder();
//        initMap.put(2,"abc");
//        initMap.put(3,"def");
//        initMap.put(4,"ghi");
//        initMap.put(5,"jkl");
//        initMap.put(6,"mno");
//        initMap.put(7,"pqrs");
//        initMap.put(8,"tuv");
//        initMap.put(9,"wxyz");
//        String digits = "23";
//        Map<Integer,Integer> indexMap = new HashMap<>();
//        for(int i=0;i<digits.length();i++){
//            String a= initMap.getOrDefault(digits.charAt(i),"");
//            resultTmp.append(a);
//            indexMap.put(i,indexMap.getOrDefault(i-1,-a.length())+a.length());
////            //indexMap.put(i,indexMap.getOrDefault(i-1,new Array(0,0)));
//        }
//
//        for (Map.Entry<Integer,Integer> entry : indexMap.entrySet()){
//            resultTmp
//        }
////                    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());

}


}







