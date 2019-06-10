package com.bk.tasks;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ResolverTest {
    @Test
    public void testEvenNumbersInList() {
        List<Integer> source = Arrays.asList(1, 2, 3, -4, 5);

        List<Integer> calculated = Resolver.filterListByCriteria(source, value -> value != null && value % 2 == 0);
        assertThat(calculated).containsOnly(2, -4);
    }

    @Test
    public void testPalindromeStringsInList() {
        List<String> source = Arrays.asList("abba", "Lorem", "Ipsum", "mil5lim", null);

        List<String> calculated = Resolver.filterListByCriteria(source, value -> value != null && value.equals(new StringBuilder(value).reverse().toString()));
        assertThat(calculated).containsOnly("abba", "mil5lim");
    }

    @Test
    public void testUndefinedList() {
        Predicate<Integer> evenNumberCriterion = value -> value != null && value % 2 == 0;
        Throwable ex = catchThrowable(() -> Resolver.filterListByCriteria(null, evenNumberCriterion));

        assertThat(ex)
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Input list of values has to be initialized");
    }

    @Test
    public void testUndefinedCriterion() {
        Throwable ex = catchThrowable(() -> Resolver.filterListByCriteria(new ArrayList<>(), null));

        assertThat(ex)
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Criterion how to filter a list has to be initialized");
    }

    @Test
    public void testEvenNumbersIterator() {
        List<Integer> source = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> calculatedList = new ArrayList<>();

        Iterator<Integer> calculatedIt = Resolver.filterSequenceIterator(source.iterator(), value -> value != null && value % 2 == 0);
        while (calculatedIt.hasNext()) {
            calculatedList.add(calculatedIt.next());
        }

        assertThat(calculatedList).containsOnly(2, 4);
    }

    @Test
    public void testEvenNumbersNextNext() {
        List<Integer> source = Arrays.asList(1, 2, 3, 4, 9, 7, 5);

        Iterator<Integer> calculatedIt = Resolver.filterSequenceIterator(source.iterator(), value -> value != null && value % 2 == 0);
        assertThat(calculatedIt.next()).isEqualTo(2);
        assertThat(calculatedIt.next()).isEqualTo(4);
    }

    @Test
    public void testMissingElementException() {
        List<Integer> source = Arrays.asList(1, -1, 3, -5, 9, 7, 5);
        Iterator<Integer> calculated = Resolver.filterSequenceIterator(source.iterator(), value -> value != null && value % 2 == 0);
        assertThat(calculated.hasNext()).isFalse();

        Throwable ex = catchThrowable(calculated::next);
        assertThat(ex)
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testIncorrectIteratorParameter() {
        Predicate<Integer> criterion = value -> value != null && value % 2 == 0;
        Throwable ex = catchThrowable(() -> Resolver.filterSequenceIterator(null, criterion));

        assertThat(ex)
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Input iterator has to be initialized");
    }

    @Test
    public void testIncorrectCriterion() {
        List<Integer> source = Arrays.asList(1, -1, 3, -5, 9, 7, 5);
        Throwable ex = catchThrowable(() -> Resolver.filterSequenceIterator(source.iterator(), null));

        assertThat(ex)
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Criterion how to filter a collection has to be initialized");
    }
}