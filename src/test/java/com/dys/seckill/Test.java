package com.dys.seckill;

import java.util.*;

public class Test {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        while(t-- > 0){
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            List<List<Integer>> list = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                List<Integer> lists = new ArrayList<>();
                lists.add(i);
                list.add(lists);
            }
            for(int i = 0; i < m; i++){
                int a = scanner.nextInt();
                int b = scanner.nextInt();
                list.get(a).add(b);
                list.get(b).add(a);
            }
            if(isReachable(list, n)){
                System.out.println("POSSIBLE");
            }
            else{
                System.out.println("IMPOSSIBLE");
            }
        }
    }
    public static boolean isReachable(List<List<Integer>> list, int n){
        List<Integer> temp = list.get(1);
        if(temp.contains(n)){
            return true;
        }
        for(int each: temp){
            if(list.get(each).contains(n)){
                return true;
            }
        }
        return false;
    }
}
