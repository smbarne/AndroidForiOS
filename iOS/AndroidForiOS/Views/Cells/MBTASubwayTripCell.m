//
//  MBTASubwayTripCellTableViewCell.m
//  AndroidForiOS
//
//  Created by Stephen Barnes on 3/21/14.
//  Copyright (c) 2014 Stephen Barnes. All rights reserved.
//

#import "MBTASubwayTripCell.h"

// Managers
#import "MBTADataManager.h"

// Data Model
#import "MBTATrip.h"

NSString* const kMBTASubwayTripCellReuseId = @"kMBTASubwayTripCellReuseId";

@interface MBTASubwayTripCell ()
@property (weak, nonatomic) IBOutlet UILabel *destinationLabel;
@property (weak, nonatomic) IBOutlet UILabel *timeStampLabel;
@property (weak, nonatomic) IBOutlet UILabel *trainNameLabel;

@end

@implementation MBTASubwayTripCell

#pragma mark - Cell Reuse Id

+ (NSString *)reuseId
{
    return kMBTASubwayTripCellReuseId;
}

#pragma mark - Cell Lifecycle

- (void)awakeFromNib
{
    [self commonInit];
}

- (void)prepareForReuse
{
    [self commonInit];
}

- (void)commonInit
{
    [self.destinationLabel setText:nil];
    [self.trainNameLabel setText:nil];
    [self.timeStampLabel setText:nil];
}

#pragma mark - Setters

- (void)setFromTrip:(MBTATrip *)trip
{
    [self.destinationLabel setText:trip.destination];
    [self.trainNameLabel setText:trip.trainName];
    
    NSDateFormatter *dateFormatter = [MBTADataManager dataDateFormatter];
    [self.timeStampLabel setText:[dateFormatter stringFromDate:trip.positionTimeStamp]];
}

@end
