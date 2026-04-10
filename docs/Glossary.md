# ✈️ Aviation System Glossary (Updated Domain Model)

| Concept | Description |
|----------|-------------|
| **Aircraft Model** | Defines technical specifications shared by a family of aircraft (range, capacity, speed). |
| **Aircraft** | A physical aircraft with a unique registration and operational state. |
| **Airport** | A location where aircraft operate, providing runways, facilities, and operational readiness state. |
| **Runway** | A physical strip within an airport used for takeoff and landing, with its own status and constraints. |
| **Route** | A planned connection between two airports defining origin, destination, and operational requirements. |
| **Route Requirements** | Constraints such as required aircraft range or capacity needed to operate a route. |
| **Flight** | A scheduled execution of a route using a specific aircraft at defined times. |
| **Flight Status** | Lifecycle state of a flight (e.g., SCHEDULED, DEPARTED, ARRIVED, CANCELED). |
| **Aircraft Specifications** | Technical capabilities of an aircraft model (fuel capacity, range, seating, speed). |
| **Installed Component** | A physical part installed on an aircraft, tracked by serial number and status. |
| **Maintenance Record** | Execution instance of maintenance performed on a specific aircraft. |
| **Maintenance Template** | Predefined procedure or checklist used for performing maintenance operations. |
| **Maintenance Operation** | Type of maintenance activity (INSPECTION, OVERHAUL, MODIFICATION, SCHEDULED). |
| **Used Part** | A part consumed or replaced during a maintenance operation. |
| **Inventory (Logistics)** | Stock tracking system for aircraft parts and fuel resources. |
| **Component Definition** | Definition of a spare part or component available in the logistics system. |
| **Fuel Inventory** | Tracking of available fuel types and quantities in storage. |
| **Airport Location** | Geographic and administrative data describing an airport’s position (city, region, timezone). |
| **Coordinates** | Latitude and longitude defining a precise geographic point. |
| **Facilities** | Infrastructure details of an airport such as terminals, gates, and services. |
| **Contact** | Communication channel for an airport (phone, email, fax). |
| **Airport Status** | Operational state of an airport (OPERATIONAL, CLOSED). |
| **Runway Status** | Operational state of a runway (OPEN, IN_USE, UNDER_MAINTENANCE). |
| **Aircraft Status** | Operational state of an aircraft (ACTIVE, INACTIVE, UNDER_MAINTENANCE, IN_FLIGHT). |
| **Contact Type** | Type of contact method (PHONE, EMAIL, FAX). |
| **Operational Constraint** | A time-bound condition affecting whether an airport can be used for operations (e.g., safety, security, maintenance validity). |
| **Flight Eligibility Logic** | Domain rule that determines whether a flight can operate based on aircraft, route, and airport constraints. |