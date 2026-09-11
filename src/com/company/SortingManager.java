package com.company;

import java.util.List;
import java.util.Comparator;

public class SortingManager<T> {

    private final Ignorer<T> ignorer;

    public SortingManager(Ignorer<T> ignorer) {
        this.ignorer = ignorer;
    }

    private boolean isIgnorable(T value) {
        if (ignorer != null) {
            return ignorer.isIgnorable(value);
        }
        return false;
    }

    private int flexibleCompare(T a, T b, Comparator<? super T> comparator) {
        if (comparator != null) {
            return comparator.compare(a, b);
        } else {
            return ((Comparable<T>) a).compareTo(b);
        }
    }

    private T getPivot(List<T> list, int start, int end, Comparator<? super T> comparator) {
        int prev;

        for (prev = start; prev <= end; ++prev) {
            if (!isIgnorable(list.get(prev))) {
                break;
            }
        }

        if (prev > end) {
            return null;
        }

        int ascLeft = prev, ascLen = 1, descLeft = prev, descLen = 1;
        int bestStart = prev, bestEnd = prev, bestLen = 1;

        for (int i = prev + 1; i <= end; ++i) {
            if (!isIgnorable(list.get(i))) {
                int c = flexibleCompare(list.get(i - 1), list.get(i), comparator);
                if (c > 0) {
                    ascLeft = i;
                    ascLen = 1;
                    descLen++;
                    if (descLen > bestLen) {
                        bestLen = descLen;
                        bestStart = descLeft;
                        bestEnd = i;
                    }
                } else if (c < 0) {
                    descLeft = i;
                    descLen = 1;
                    ascLen++;
                    if (ascLen > bestLen) {
                        bestLen = ascLen;
                        bestStart = ascLeft;
                        bestEnd = i;
                    }
                } else {
                    ascLen++;
                    if (ascLen > bestLen) {
                        bestLen = ascLen;
                        bestStart = ascLeft;
                        bestEnd = i;
                    }
                    descLen++;
                    if (descLen > bestLen) {
                        bestLen = descLen;
                        bestStart = descLeft;
                        bestEnd = i;
                    }
                }
            }
        }
        if (ignorer == null) {
            return list.get((bestStart + bestEnd) / 2);
        } else {
            int index = (bestStart + bestEnd) / 2, offset = 0;
            while (true) {
                if (index - offset >= start && !isIgnorable(list.get(index - offset))) {
                    return list.get(index - offset);
                }
                if (index + offset <= end && !isIgnorable(list.get(index + offset))) {
                    return list.get(index + offset);
                }
                offset++;
            }
        }
    }

    private void sort(List<T> list, int start, int end, Comparator<? super T> comparator) {
        if (start < end) {
            T pivot = getPivot(list, start, end, comparator);
            if (pivot == null) return;

            int left = start, right = end;

            while (left < right) {
                while (isIgnorable(list.get(left)) || flexibleCompare(list.get(left), pivot, comparator) < 0) {
                    ++left;
                }
                while (isIgnorable(list.get(right)) || flexibleCompare(list.get(right), pivot, comparator) > 0) {
                    --right;
                }
                if (left <= right) {
                    T temp = list.get(left);
                    list.set(left, list.get(right));
                    list.set(right, temp);
                    ++left;
                    --right;
                }
            }
            sort(list, start, right, comparator);
            sort(list, left, end, comparator);
        }
    }

    public void sort(List<T> list, Comparator<? super T> comparator) {
        if (list == null) {
            throw new NullPointerException("Sorting manager can't sort a non-existing (null) list.");
        }
        sort(list, 0, list.size() - 1, comparator);
    }
}