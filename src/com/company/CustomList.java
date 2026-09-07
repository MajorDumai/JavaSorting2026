package com.company;

import java.util.AbstractList;
import java.util.function.Function;

public class CustomList<E> extends AbstractList<E> {
    private final int pageSize;
    private final Page firstPage;
    private Page lastPage;
    private int size = 0;

    public CustomList() {
        lastPage = firstPage = new Page(16);
        pageSize = 16;
    }

    public CustomList(int pageSize) {
        lastPage = firstPage = new Page(pageSize);
        this.pageSize = pageSize;
    }

    private class Page {
        private final Object[] array;
        private final int size;
        private int count = 0;
        private Page nextPage = null;

        private Page(int size) {
            array = new Object[size];
            this.size = size;
        }

        private void add(int index, E element) {
            if (index == size) {
                newPage();
                nextPage.add(0, element);
            } else {
                E old = (E) array[index];
                while (index <= count) {
                    array[index++] = element;
                    if (index < size) {
                        element = old;
                        old = (E) array[index];
                    }
                }
                if (count == size) {
                    if (nextPage == null) {
                        newPage();
                    }
                    nextPage.add(0, old);
                } else {
                    count++;
                }
            }
        }

        private boolean remove(Object o) {
            for (int i = 0; i < count; i++) {
                if (o.equals(array[i])) {
                    remove(i);
                    return true;
                }
            }
            if (nextPage != null) {
                final boolean removed = nextPage.remove(o);
                checkEmptyPage();
                return removed;
            } else {
                return false;
            }
        }

        private E remove(int index) {
            E old = (E) array[index];
            while (index < count && index < size - 1) {
                array[index] = array[index + 1];
                index++;
            }
            if (index == size - 1) {
                final E next;
                if (nextPage != null) {
                    next = nextPage.get(0);
                    nextPage.remove(next);
                    checkEmptyPage();
                    count++;
                } else {
                    next = null;
                }
                array[index] = next;
            }
            count--;
            return old;
        }

        private E set(int index, E element) {
            final E old = (E) array[index];
            array[index] = element;
            return old;
        }

        private E get(int index) {
            return (E) array[index];
        }

        private void checkEmptyPage() {
            if (nextPage.count == 0) {
                nextPage = null;
                lastPage = this;
            }
        }

        private void newPage() {
            nextPage = new Page(size);
            lastPage = nextPage;
        }
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        final Page page = getPage(index);
        while (index > pageSize) {
            index -= pageSize;
        }
        page.add(index, element);
        size++;
    }

    @Override
    public E remove(int index) {
        final int indexInPage = index % pageSize;
        E old = doOnPage(index, page -> page.remove(indexInPage));
        size--;
        return old;
    }

    @Override
    public boolean remove(Object o) {
        final boolean removed = firstPage.remove(o);
        if (removed) {
            size--;
        }
        return removed;
    }

    @Override
    public E set(int index, E element) {
        final int indexInPage = index % pageSize;
        return doOnPage(index, page -> page.set(indexInPage, element));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E get(int index) {
        final int indexInPage = index % pageSize;
        return doOnPage(index, page -> page.get(indexInPage));
    }

    private <T> T doOnPage(int index, Function<Page, T> action) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Page page = getPage(index);
        return action.apply(page);
    }

    private Page getPage(int index) {
        Page page;
        final int pagesForward = index / pageSize;
        final int pagesTotal = size / pageSize;
        if (pagesForward == pagesTotal) {
            page = lastPage;
        } else {
            page = firstPage;
            for (int i = 0; i < pagesForward; i++) {
                page = page.nextPage;
            }
        }
        return page;
    }
}
