package com.axoulotl.alextheque.model.entity.enums;

import java.util.*;
import java.util.stream.Collectors;

public enum Zone {
    EUR(0),
    JAP(1),
    USA(2);

    private final Integer numZone;
    private static final Map<Integer, Zone> mapOfZoneById = new HashMap<>();

    Zone(Integer numZone) {
        this.numZone = numZone;
    }

    public Integer getZoneValue(){
        return numZone;
    }

    static {
        Arrays.stream(Zone.values()).forEach(zone -> mapOfZoneById.put(zone.getZoneValue(), zone));
    }

    public static Zone getZoneFromInt(Integer zoneId){
        return mapOfZoneById.get(zoneId);
    }
}
