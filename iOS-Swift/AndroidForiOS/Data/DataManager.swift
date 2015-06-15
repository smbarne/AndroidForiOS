//
//  DataManager.swift
//  AndroidForiOS
//
//  Created by Stephen Barnes on 6/14/15.
//  Copyright © 2015 Stephen Barnes. All rights reserved.
//

import Foundation

public class DataManager: NSObject {
	// MARK - Shared Instance
	public static let sharedInstance = DataManager()

	// MARK - Computed Properties
	public static var dataDateFormatter:NSDateFormatter {
		get {
			let dateFormatter = NSDateFormatter()
			dateFormatter.timeStyle = NSDateFormatterStyle.ShortStyle
			dateFormatter.dateStyle = NSDateFormatterStyle.ShortStyle
			dateFormatter.doesRelativeDateFormatting = true
			return dateFormatter
		}
	}

	private static var _backgroundQueue:dispatch_queue_t {
		get {
			return dispatch_queue_create("Hello World", DISPATCH_QUEUE_SERIAL)
		}
	}

	// MARK - Data Import

	public func importData(key:LineType, completion: ((tripList :TripList?, succeeded :Bool, error :NSError?) -> ())? ) {
		dispatch_async(DataManager._backgroundQueue) { () -> Void in
			let data = try! self.parseJSONToDictionary(key.filename())

			do {
				let tripList = try TripList.parseData(data)

				if let completion = completion {
					dispatch_async(dispatch_get_main_queue(), { () -> Void in
						completion(tripList: tripList, succeeded: true, error: nil)
					})
				}
			} catch {
				if let completion = completion {
					dispatch_async(dispatch_get_main_queue(), { () -> Void in
						completion(tripList: nil, succeeded: false, error: nil) // TODO: error
					})
				}
			}
		}
	}

	private func parseJSONToDictionary(fileName: String) throws -> NSDictionary {

		guard let filePath:String = NSBundle.mainBundle().pathForResource(fileName, ofType: fileName.pathExtension) else {
			// TODO: Throw error
			throw NSError.new()
		}
		let jsonData = NSData.dataWithContentsOfMappedFile(filePath) as! NSData
		let jsonDict = try NSJSONSerialization.JSONObjectWithData(jsonData, options: NSJSONReadingOptions.AllowFragments) as! NSDictionary

		return jsonDict;
	}

//	// From http://stackoverflow.com/questions/10866403/passing-data-from-local-file-using-json
//	+(NSDictionary*)dictionaryWithContentsOfJSONString:(NSString*)fileLocation
//	{
//	NSString *filePath = [[NSBundle mainBundle] pathForResource:[fileLocation stringByDeletingPathExtension] ofType:[fileLocation pathExtension]];
//	NSData* data = [NSData dataWithContentsOfFile:filePath];
//	__autoreleasing NSError* error = nil;
//	id result = [NSJSONSerialization JSONObjectWithData:data
//	options:kNilOptions error:&error];
//	if (error != nil) {
//	return nil;
//	}
//	else {
//	return result;
//	}
//	}


//	func processFile(filename: String) throws {
//		if exists(filename) {
//			let file = open(filename)
//			defer {
//				close(file)
//			}
//			while let line = try file.readline() {
//				/* Work with the file. */
//			}
//			// close(file) is called here, at the end of the scope.
//		}
//	}
}
