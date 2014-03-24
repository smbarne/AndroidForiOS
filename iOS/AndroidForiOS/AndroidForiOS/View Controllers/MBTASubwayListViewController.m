//
//  MBTAViewController.m
//  AndroidForiOS
//
//  Created by Stephen Barnes on 3/21/14.
//  Copyright (c) 2014 Stephen Barnes. All rights reserved.
//

#import "MBTASubwayListViewController.h"

// View Controllers
#import "MBTATripListTableViewController.h"

// Managers
#import "MBTADataManager.h"

// Data Model
#import "MBTATripList.h"

// Utilities
#import "NSString+lineType.h"
#import "UIColor+MBTAColors.h"
#import "UIImage+QuickImage.h"

static NSString* const kMBTASubwayListViewControllerTitle = @"Subway Lines";
static NSString* const kMBTASubwayLineCell = @"kMBTASubwayLineCell";
NSString* const kMBTAApplicationDescription = @"This application demonstrates the correlation between iOS and Android development using Subway data provided by the MBTA.";

@interface MBTASubwayListViewController () < UITableViewDataSource,
                                             UITableViewDelegate,
                                             UIAlertViewDelegate >

@property (weak, nonatomic) IBOutlet UITableView *tableView;
@property (weak, nonatomic) IBOutlet UIButton *alertButton;

@property (strong, nonatomic) NSArray *data;

@end

@implementation MBTASubwayListViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    [self setTitle:kMBTASubwayListViewControllerTitle];
    [self.tableView registerClass:[UITableViewCell class] forCellReuseIdentifier:kMBTASubwayLineCell];
    
    NSMutableArray *subwayLines = [[NSMutableArray alloc] initWithCapacity:MBTALineTypeCount];
    for (NSInteger i = 0; i < MBTALineTypeCount; i++) {
        [subwayLines addObject:[NSNumber numberWithInteger:i]];
    }
    self.data = subwayLines;
    
    [self.alertButton setBackgroundImage:[UIImage imageWithColor:[UIColor redColor]] forState:UIControlStateNormal];
    [self.alertButton setBackgroundImage:[UIImage imageWithColor:[UIColor rzRedPressed]] forState:UIControlStateHighlighted];
    
    [[self navigationItem] setRightBarButtonItem:[[UIBarButtonItem alloc] initWithTitle:@"About" style:UIBarButtonItemStylePlain target:self action:@selector(didPressAboutButton:)]];
}

#pragma mark - UITableViewDataSource

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [self.data count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:kMBTASubwayLineCell forIndexPath:indexPath];
    
    MBTALineType subwayLine = [[self.data objectAtIndex:indexPath.row] integerValue];
    [[cell textLabel] setText:[NSString stringForLineType:subwayLine]];
    [[cell textLabel] setTextColor:[UIColor colorForLineType:subwayLine]];

    return cell;
}

#pragma mark - UITableViewDelegate

- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    return NO;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:NO];
    
    MBTALineType selectedLineType = [(NSNumber *)[self.data objectAtIndex:indexPath.row] integerValue];
    MBTATripListTableViewController *tripListVC = [[MBTATripListTableViewController alloc] initWithLineType:selectedLineType];
    [self.navigationController pushViewController:tripListVC animated:YES];
}

#pragma mark - Actions

- (IBAction)didPressAlertButton:(id)sender
{
    [[[UIAlertView alloc] initWithTitle:@"Hello World" message:kMBTAApplicationDescription delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil] show];
}

- (IBAction)didPressAboutButton:(id)sender
{
    [[[UIAlertView alloc] initWithTitle:@"About" message:kMBTAApplicationDescription delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil] show];
}

@end
