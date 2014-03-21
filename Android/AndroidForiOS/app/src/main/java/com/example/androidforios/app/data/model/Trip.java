package com.example.androidforios.app.data.model;

import java.util.Date;
import java.util.List;

/**
 * Created by stephen.barnes on 3/21/14.
 */
public class Trip {

    String tripId;
    String destination;
    Date positionTimeStamp;
    String trainName;
    Float positionLat;
    Float positionLong;
    Float positionHeading;
    List<Prediction> predictions;
}
