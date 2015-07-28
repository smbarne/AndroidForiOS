//
//  SubwayListViewController.swift
//  AndroidForiOS
//
//  Created by Stephen Barnes on 6/14/15.
//  Copyright © 2015 Stephen Barnes. All rights reserved.
//

import UIKit

class SubwayListViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    // MARK - Properties and Static Values
    
    static let aboutMessage = NSLocalizedString("This application demonstrates the correlation between iOS and Android development using Subway data provided by the MBTA.",
        comment: "Description for hello world message")
    
    var data:[LineType] = [];
    
    // MARK - View Lifecycle
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.data = [LineType.Red, LineType.Blue, LineType.Orange] // TODO: figure out how to enumerate through the enum and set teha rray.
    }
    
    // MARK - UITableViewDataSource

	func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
		return self.data.count
	}

	func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell:UITableViewCell = tableView.dequeueReusableCellWithIdentifier("SubwayLineCell", forIndexPath: indexPath)

        var subwayLineType = self.data[indexPath.row]
        cell.textLabel?.text = subwayLineType.rawValue
        cell.textLabel?.textColor = subwayLineType.color()
        
        return cell;
	}
    
    // MARK - Navigation
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let tripVC = segue.destinationViewController as? TripListTableViewController {
            tripVC.prepareForSubwayLine(LineType.Red) // TODO: index path for selection? 
        }
    }
    
    // MARK - Actions

    @IBAction func didPressHelloWorldButton(sender: AnyObject) {
        self.showViewController(
            UIAlertController( title: NSLocalizedString("Hello World", comment: "Hello World Alert Title"),message:SubwayListViewController.aboutMessage, preferredStyle: UIAlertControllerStyle.Alert),             sender:nil)
    }
    
    @IBAction func didPressAboutButton(sender: AnyObject) {
        self.showViewController(
            UIAlertController( title: NSLocalizedString("About", comment: "About App Title"), message:SubwayListViewController.aboutMessage, preferredStyle: UIAlertControllerStyle.Alert),             sender:nil)
    }
}
