//
//  TripListTableViewController.swift
//  AndroidForiOS
//
//  Created by Stephen Barnes on 7/27/15.
//  Copyright © 2015 Stephen Barnes. All rights reserved.
//

import UIKit

public class TripListTableViewController: UITableViewController {
    
    public var subwayLineType:LineType?
    var data:[Trip]?
    
    public func prepareForSubwayLine(subwayLineType:LineType) {
        self.subwayLineType = subwayLineType
        weak var weakSelf = self
        DataManager.sharedInstance.importData(subwayLineType) { (tripList, succeeded, error) -> () in
            if (succeeded) {
                weakSelf?.data = tripList?.trips
                weakSelf?.tableView.reloadData()
            } else {
                weakSelf?.showViewController(
                    UIAlertController(
                        title: NSLocalizedString("Uh Oh", comment: "Unknown Error Title"),
                        message: error?.localizedDescription,
                        preferredStyle: UIAlertControllerStyle.Alert),
                    sender: weakSelf)
            }
        }
    }
    
    public override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        guard let dataCount = self.data?.count else {
            return 0
        }
        return dataCount
    }
    
    public override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {

        if let subwayCell = tableView.dequeueReusableCellWithIdentifier("SubwayTripTableViewCell", forIndexPath: indexPath) as? SubwayTripTableViewCell {
            if let tripData = self.data?[indexPath.row] {
                subwayCell.updateCell(tripData)
            }
            return subwayCell
        } else {
            return UITableViewCell.new()
        }
    }
}

