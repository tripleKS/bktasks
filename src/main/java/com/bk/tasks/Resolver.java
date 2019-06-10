package com.bk.tasks;

import com.sun.istack.internal.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

class Resolver {

    static <T> List<T> filterListByCriteria(@NotNull List<T> values, Predicate<T> criterion) {
        checkNotNull(values, "Input list of values has to be initialized");
        checkNotNull(criterion, "Criterion how to filter a list has to be initialized");

        return values.stream().filter(criterion).collect(Collectors.toList());
    }

    static <T> Iterator<T> filterSequenceIterator(Iterator<T> numbersIt, Predicate<T> criterion) {
        checkNotNull(numbersIt, "Input iterator has to be initialized");
        checkNotNull(criterion, "Criterion how to filter a collection has to be initialized");

        return new Iterator<T>() {
            private T value;
            private boolean isFound;

            @Override
            public boolean hasNext() {
                while (numbersIt.hasNext()) {
                    T el = numbersIt.next();
                    if (criterion.test(el)) {
                        value = el;
                        isFound = true;
                        return true;
                    }
                }

                return false;
            }

            @Override
            public T next() {
                if (isFound) {
                    isFound = false;
                    return value;
                }

                while (numbersIt.hasNext()) {
                    T el = numbersIt.next();
                    if (criterion.test(el)) {
                        value = el;
                        return value;
                    }
                }

                throw new NoSuchElementException();
            }
        };
    }
}
