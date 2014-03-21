//
//  SBViewController.m
//  AndroidForiOS
//
//  Created by Stephen Barnes on 3/21/14.
//  Copyright (c) 2014 Stephen Barnes. All rights reserved.
//

#import "SBSubwayListViewController.h"

static NSString* const kSBSubwayListViewControllerTitle = @"Subway Lines";

@interface SBSubwayListViewController () < UITableViewDataSource, UITableViewDelegate >

@property (weak, nonatomic) IBOutlet UITableView *tableView;

@end

@implementation SBSubwayListViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    [self setTitle:kSBSubwayListViewControllerTitle];
}

#pragma mark - UITableViewDataSource

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return 0;
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return nil;
}

#pragma mark - UITableViewDelegate

- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    return NO;
}

@end
