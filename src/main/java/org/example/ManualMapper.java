package org.example;

public class ManualMapper {

    public void mapSrcToDest(Source src, Destination dest) {
        if (src == null || dest == null) {
            return;
        }
        dest.setName(src.getName());
        dest.setAge(src.getAge());
    }

    public Destination mapSrcToDest(Source src) {
        if (src == null) {
            return null;
        }
        Destination destination = new Destination();
        destination.setName(src.getName());
        destination.setAge(src.getAge());
        return destination;
    }
}
