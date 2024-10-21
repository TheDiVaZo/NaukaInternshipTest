package me.thedivazo.nauka.internship.util;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.StreamSupport;

@AllArgsConstructor
public final class ListUtils {
    public static <E> List<E> getListView(Iterable<E> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false).toList();
    }
}
