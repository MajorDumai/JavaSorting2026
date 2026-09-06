package com.company;

import java.util.AbstractList;

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

        public Page(int size) {
            array = new Object[size];
            this.size = size;
        }

        public boolean add(E element) {
            if (nextPage != null) {
                return nextPage.add(element);
            } else {
                try {
                    if (count == size) {
                        nextPage = new Page(size);
                        lastPage = nextPage;
                        return nextPage.add(element);
                    } else {
                        array[count++] = element;
                        return true;
                    }
                } catch (RuntimeException e) {
                    checkEmptyPage();
                    return false;
                }
            }
        }

        public boolean remove(Object o) {
            for (int i = 0; i < count; i++) {
                if (o.equals(array[i])) {
                    while (i < count) {
                        array[i] = array[i + 1];
                        i++;
                        if (i == size - 1) {
                            final E next;
                            if (nextPage != null) {
                                next = nextPage.get(0);
                                nextPage.remove(next);
                                checkEmptyPage();
                                if (nextPage == null) {
                                    count++;
                                }
                            } else {
                                next = null;
                            }
                            array[i] = next;
                            break;
                        }
                    }
                    if (nextPage == null) {
                        count--;
                    }
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

        public E set(int index, E e) {
            if (index >= size) {
                return nextPage.get(index - size);
            } else {
                final E old = (E) array[index];
                array[index] = e;
                return old;
            }
        }

        public E get(int index) {
            if (index >= size) {
                return nextPage.get(index - size);
            } else {
                return (E) array[index];
            }
        }

        private void checkEmptyPage() {
            if (nextPage.isEmpty()) {
                nextPage = null;
                lastPage = this;
            }
        }

        public boolean isEmpty() {
            return count == 0;
        }

        public Page getNextPage() {
            return nextPage;
        }
    }

    @Override
    public boolean add(E element) {
        final boolean added = lastPage.add(element);
        if (added) {
            size++;
        }
        return added;
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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        Page currentPage = firstPage;
        while (index >= pageSize) {
            currentPage = currentPage.getNextPage();
            index -= pageSize;
        }
        return currentPage.set(index, element);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        Page currentPage = firstPage;
        while (index >= pageSize) {
            currentPage = currentPage.getNextPage();
            index -= pageSize;
        }
        return currentPage.get(index);
    }
}
