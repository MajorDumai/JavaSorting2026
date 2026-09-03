package com.company;

import java.util.ArrayList;
import java.util.Comparator;

public class SortingManager<T> {

    private Ignorer<T> ignorer;

    public SortingManager(Ignorer<T> ignorer) {
        this.ignorer = ignorer;
    }

    boolean isIgnorable(T x){
        if (ignorer!=null) return ignorer.isIgnorable(x);
        return false;
    }
    private T getPivot(ArrayList<Comparable <T>> arr, int start, int end)
    {
        int prev;

        for(prev=start; prev<=end;++prev)
            if(!isIgnorable((T)arr.get(prev))) break;

        if (prev>end) return null;

        int ascLeft=prev, ascLen=1, descLeft=prev, descLen=1;
        int bestStart=prev, bestEnd=prev, bestLen=1;

        for(int i=prev+1; i<=end;++i)
            if (!isIgnorable((T)arr.get(i))){
                int c=arr.get(prev).compareTo((T)arr.get(i));
                if (c>0){
                    ascLeft=i;
                    ascLen=1;
                    descLen++;
                    if(descLen>bestLen)
                    {
                        bestLen=descLen;
                        bestStart=descLeft;
                        bestEnd=i;
                    }
                }
                else if(c<0)
                {
                    descLeft=i;
                    descLen=1;
                    ascLen++;
                    if(ascLen>bestLen)
                    {
                        bestLen=ascLen;
                        bestStart=ascLeft;
                        bestEnd=i;
                    }
                }
                else
                {
                    ascLen++;
                    if(ascLen>bestLen)
                    {
                        bestLen=ascLen;
                        bestStart=ascLeft;
                        bestEnd=i;
                    }
                    descLen++;
                    if(descLen>bestLen)
                    {
                        bestLen=descLen;
                        bestStart=descLeft;
                        bestEnd=i;
                    }
                }
            }
        if(ignorer==null) {
            return (T) arr.get((bestStart + bestEnd) / 2);
        }
        else{
            int index=(bestStart + bestEnd) / 2, offset=0;
            while (true) {
                if(index-offset>=start && !isIgnorable((T)arr.get(index-offset)))
                    return (T)arr.get(index-offset);
                if(index+offset<=end && !isIgnorable((T)arr.get(index+offset)))
                    return (T)arr.get(index+offset);
                offset++;
            }
        }
    }

    private void myQSort(ArrayList<Comparable <T>> arr, int start, int end)
    {
        if(start<end)
        {
            T x=getPivot(arr,start,end);
            if(x==null) return;

            int left=start, right=end;
            while (left<right)
            {
                while(isIgnorable((T)arr.get(left)) || arr.get(left).compareTo(x)<0)++left;
                while(isIgnorable((T)arr.get(right)) || arr.get(right).compareTo(x)>0)--right;
                if (left<=right){
                    T t=(T)arr.get(left);
                    arr.set(left,arr.get(right));
                    arr.set(right,(Comparable <T>)t);
                    ++left;
                    --right;
                }
            }
            myQSort(arr, start, right);
            myQSort(arr, left, end);
        }
    }

    private T getPivotWithComparator(ArrayList<T> arr, int start, int end, Comparator<? super T> cmp)
    {
        int prev;

        for(prev=start; prev<=end;++prev)
            if(!isIgnorable((T)arr.get(prev))) break;

        if (prev>end) return null;

        int ascLeft=prev, ascLen=1, descLeft=prev, descLen=1;
        int bestStart=prev, bestEnd=prev, bestLen=1;

        for(int i=prev+1; i<=end;++i)
            if (!isIgnorable((T)arr.get(i))) {
                int c=cmp.compare(arr.get(i-1), arr.get(i));
                if (c>0){
                    ascLeft=i;
                    ascLen=1;
                    descLen++;
                    if(descLen>bestLen)
                    {
                        bestLen=descLen;
                        bestStart=descLeft;
                        bestEnd=i;
                    }
                }
                else if(c<0)
                {
                    descLeft=i;
                    descLen=1;
                    ascLen++;
                    if(ascLen>bestLen)
                    {
                        bestLen=ascLen;
                        bestStart=ascLeft;
                        bestEnd=i;
                    }
                }
                else
                {
                    ascLen++;
                    if(ascLen>bestLen)
                    {
                        bestLen=ascLen;
                        bestStart=ascLeft;
                        bestEnd=i;
                    }
                    descLen++;
                    if(descLen>bestLen)
                    {
                        bestLen=descLen;
                        bestStart=descLeft;
                        bestEnd=i;
                    }
                }
            }
        if(ignorer==null) {
            return (T) arr.get((bestStart + bestEnd) / 2);
        }
        else{
            int index=(bestStart + bestEnd) / 2, offset=0;
            while (true) {
                if(index-offset>=start && !isIgnorable(arr.get(index-offset)))
                    return arr.get(index-offset);
                if(index+offset<=end && !isIgnorable(arr.get(index+offset)))
                    return arr.get(index+offset);
                offset++;
            }
        }
    }

    private void myQSortWithComparator(ArrayList<T> arr, int start, int end, Comparator<? super T> cmp)
    {
        if(start<end)
        {
            T x=getPivotWithComparator(arr, start, end, cmp);
            if(x==null) return;

            int left=start, right=end;

            while (left<right)
            {
                while(isIgnorable(arr.get(left)) || cmp.compare(arr.get(left),x)<0)++left;
                while(isIgnorable(arr.get(right)) || cmp.compare(arr.get(right),x)>0)--right;
                if (left<=right){
                    T t=arr.get(left);
                    arr.set(left,arr.get(right));
                    arr.set(right,t);
                    ++left;
                    --right;
                }
            }
            myQSortWithComparator(arr, start, right,cmp);
            myQSortWithComparator(arr, left, end,cmp);
        }
    }

    public void mySort(ArrayList<T> arr, Comparator<? super T> cmp)
    {
        if (cmp==null)
            myQSort((ArrayList<Comparable<T>>) arr,0, arr.size()-1);
        else
            myQSortWithComparator(arr,0, arr.size()-1,cmp);
    }


}