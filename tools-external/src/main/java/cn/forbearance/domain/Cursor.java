package cn.forbearance.domain;

import java.util.Collection;

/**
 * @author cristina
 */
public class Cursor<T> {

    private final String cursor;

    private final Collection<T> elements;

    public Cursor(String cursor, Collection<T> elements) {
        this.cursor = cursor;
        this.elements = elements;
    }

    public String getCursor() {
        return cursor;
    }

    public Collection<T> getElements() {
        return elements;
    }

    @Override
    public String toString() {
        return "Cursor{" +
                "cursor='" + cursor + '\'' +
                ", elements=" + elements +
                '}';
    }


}
