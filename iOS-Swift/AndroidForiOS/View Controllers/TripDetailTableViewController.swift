//
//  TripDetailViewController.swift
//  AndroidForiOS
//
//  Created by Stephen Barnes on 7/27/15.
//  Copyright © 2015 Stephen Barnes. All rights reserved.
//

import UIKit

public class TripDetailViewController : UITableViewController {
    
    var trip:Trip?
    
    public func prepareWithTrip(trip:Trip) {
        self.trip = trip
    }
    
    public override func viewDidLoad() {
        self.title = trip?.destination
        // TODO: setup header view
    }
    
    public override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        guard let dataCount = self.trip?.predictions?.count else {
            return 0
        }
        return dataCount
    }
    
    public override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell:UITableViewCell = tableView.dequeueReusableCellWithIdentifier("TripPredictionTableViewCell", forIndexPath: indexPath)
        
        if let prediction = self.trip?.predictions?[indexPath.row] {
            cell.textLabel?.text = prediction.stopName
            cell.detailTextLabel?.text = String(prediction.stopSeconds / 60) + " m"
        }
        
        return cell;
    }
}