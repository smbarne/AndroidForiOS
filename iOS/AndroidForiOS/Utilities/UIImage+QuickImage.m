//
//  UIImage+QuickImage.m
//  AndroidForiOS
//
//  Created by Stephen Barnes on 3/21/14.
//  Copyright (c) 2014 Stephen Barnes. All rights reserved.
//

#import "UIImage+QuickImage.h"

@implementation UIImage (QuickImage)

+ (UIImage *)imageWithColor:(UIColor *)color
{
    CGRect rect = CGRectMake(0, 0, 1, 1);
    UIGraphicsBeginImageContextWithOptions(rect.size, NO, 0);
    
    [color setFill];
    UIRectFill(rect);
    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    
    UIGraphicsEndImageContext();
    
    return image;
}

@end
