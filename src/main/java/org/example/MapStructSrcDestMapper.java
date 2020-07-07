package org.example;

import org.mapstruct.Mapper;

@Mapper
public interface MapStructSrcDestMapper {
    Destination sourceToDestination(Source src);
    Source destinationToSource(Destination dest);
}
