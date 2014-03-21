package com.example.androidforios.app.data.model;

import com.example.androidforios.app.R;

import java.util.Date;
import java.util.List;

/**
 * Created by stephen.barnes on 3/21/14.
 */
public class TripList {
    public enum LineType {
        RED { public String getLineName() {return "Red"; }
            public int getLineColorResourceId() {return R.color.LineType_Red;}},
        BLUE { public String getLineName() {return "Blue"; }
            public int getLineColorResourceId() {return R.color.LineType_Blue;}},
        ORANGE { public String getLineName() {return "Orange"; }
            public int getLineColorResourceId() {return R.color.LineType_Orange;}};

        public String getFileName() { return this.getLineName() + ".json"; }
        public abstract String getLineName();
        public abstract int getLineColorResourceId();
    }

    Date currentTime;
    LineType subwayLine;
    List<Trip> trips;
}
