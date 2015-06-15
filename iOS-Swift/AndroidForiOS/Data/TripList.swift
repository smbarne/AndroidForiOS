//
//  TripList.swift
//  AndroidForiOS
//
//  Created by Stephen Barnes on 6/14/15.
//  Copyright © 2015 Stephen Barnes. All rights reserved.
//

import Foundation

public enum LineType: Int {
	case Red, Blue, Orange

	public init?(lineTypeValue:String) throws {
		switch lineTypeValue {
			case "Red":
				self = .Red
			case "Blue":
				self = .Blue
			case "Orange":
				self = .Orange
			default:
				return nil;
		}
	}

	public var name: String {
		switch self {
		case .Red:		return "Red"
		case .Blue:     return "Blue"
		case .Orange:   return "Orange"
		}
	}

	public func filename() -> String {
		return self.name + ".json"
	}

	public func description() -> String {
		return name
	}
}

public class TripList: NSObject, NSCoding, NSCopying {
	/**
	String constants that are used to archive the stored properties of a `List`. These constants
	are used to help implement `NSCoding`.
	*/
	private struct SerializationKeys {
		static let time = "time"
		static let line = "line"
		static let trips = "trips"
	}

	public var currentTime: NSDate

	public var subwayLine: LineType

	public var trips: [Trip]

	// MARK: Initialization

	public init(currentTime: NSDate, subwayLine: LineType, trips: [Trip]) {
		self.currentTime = currentTime
		self.subwayLine = subwayLine
		self.trips = trips
	}

	public required init(coder aDecoder: NSCoder) {
		currentTime = aDecoder.decodeObjectForKey(SerializationKeys.time) as! NSDate
		subwayLine = aDecoder.decodeObjectForKey(SerializationKeys.line) as! LineType
		trips = aDecoder.decodeObjectForKey(SerializationKeys.trips) as! [Trip]
	}

	// MARK: NSCopying

	public func copyWithZone(zone: NSZone) -> AnyObject  {
		return TripList(currentTime: self.currentTime, subwayLine: self.subwayLine, trips: self.trips)
	}

	public func encodeWithCoder(aCoder: NSCoder) {
		aCoder.encodeObject(currentTime, forKey: SerializationKeys.time)
		aCoder.encodeObject(subwayLine.rawValue, forKey: SerializationKeys.line)
		aCoder.encodeObject(trips, forKey: SerializationKeys.trips)
	}

	// MARK: Helper Methods

	public static func parseData(data: NSDictionary) throws -> TripList {
		var currentTime: NSDate, subwayLine: LineType, trips: [Trip];

		// Import Current Time
		if let timeInterval:NSTimeInterval = data.objectForKey("CurrentTime") as? NSTimeInterval {
			currentTime = NSDate(timeIntervalSince1970: timeInterval)
		} else {
			throw parseError("Data had invalid time interval formatting")
		}

		// Import Subway Line Type
		if let subwayLineValue:String = data.objectForKey("Line") as? String {
			do {
				try subwayLine = LineType(lineTypeValue: subwayLineValue)!
			} catch {
				throw parseError("Subway line type not recognized")
			}
		} else {
			throw parseError("Subway line type data malformed")
		}

		// Import Trips List
//		if let tripData:NSArray = data.objectForKey("Trips") as? [NSDictionary] {
//
//		} else {
//			throw parseError("Subway line type data malformed")
//		}
		trips = [Trip.new()] // TODO

		return TripList(currentTime: currentTime, subwayLine: subwayLine, trips: trips)
	}

	static func parseError(failureReason: String) -> NSError {
		return NSError(domain: NSCocoaErrorDomain, code: NSFileReadCorruptFileError, userInfo: [
			NSLocalizedDescriptionKey: NSLocalizedString("Could not read trip list data.", comment: "Read error description"),
			NSLocalizedFailureReasonErrorKey: NSLocalizedString(failureReason, comment: "Read failure reason")
			])
	}
}