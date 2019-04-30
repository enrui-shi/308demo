package com.example.demo.Type;

import java.util.Map;

public class phaseOneReturn {

    /* map precinct ID to district ID */
    private Map<Long,Long> geoUpdate;

    public void setGeoUpdate(Map<Long, Long> geoUpdate) {
        this.geoUpdate = geoUpdate;
    }

    public Map<Long, Long> getGeoUpdate() {
        return geoUpdate;
    }

    public phaseOneReturn(Map<Long, Long> geoUpdate) {
        this.geoUpdate = geoUpdate;
    }
}
