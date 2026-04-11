# Glossary

| Concept                     |Description                                                                                                                            |
|-----------------------------|---------------------------------------------------------------------------------------------------------------------------------------|
| **Defines technical**       | specifications shared by a family of aircraft, such as manufacturer, model name, and cruising speed.                                  |
| **Aircraft**                | A physical aircraft instance with a unique registration, its own seating capacity, operational state, and specific features.          |
| **Airport**                 | A location where aircraft operate, identified by a unique IATA code, providing runways, facilities, and operational readiness state.  |
| **Runway**                  | A physical strip within an airport used for takeoff and landing, with its own status and constraints.                                 |
| **Route**                   | A planned, atomic connection between two airports (point-to-point) with a fixed distance and specific operational requirements.       |
| **Route**                   | Requirements,Constraints such as required aircraft range or capacity needed to operate a route.                                       |
| **Flight**                  | A scheduled execution of a route using a specific aircraft at defined times.                                                          |
| **Flight Status**           | Lifecycle state of a flight (e.g., SCHEDULED, DEPARTED, ARRIVED, CANCELED, DELAYED).                                                  |
| **Aircraft Specifications** | Technical capabilities of an aircraft model (fuel capacity, range, seating, speed).                                                   |
| **Installed Component**     | A physical part installed on an aircraft, tracked by serial number and status.                                                        |
| **Maintenance Record**      | Execution instance of maintenance performed on a specific aircraft.                                                                   |
| **Maintenance Template**    | Predefined procedure or checklist used for performing maintenance operations, including alert intervals.                              |
| **Maintenance Operation**   | Type of maintenance activity (INSPECTION, OVERHAUL, MODIFICATION, SCHEDULED).                                                         |
| **Used Part**               | A part consumed or replaced during a maintenance operation.                                                                           |
| **Maintenance Part**        | Definition of a spare part or component available in the logistics system with manual stock management.                               |
| **Inventory (Logistics)**   | Stock tracking system for aircraft parts and fuel resources.                                                                          |
| **Fuel Inventory**          | Tracking of available fuel types and quantities in storage.                                                                           |
| **Airport Location**        | Geographic and administrative data describing an airport's position (city, region, timezone).                                         |
| **Coordinates**             | Latitude and longitude defining a precise geographic point.                                                                           |
| **Facilities**              | Infrastructure details of an airport such as terminals, gates, and services, modeled as structured data.                              |
| **Contact**                 | Communication channel for an airport (phone, email, fax), including description or department.                                        |
| **Airport Status**          | Operational state of an airport (OPERATIONAL, CLOSED).                                                                                |
| **Runway Status**           | Operational state of a runway (OPEN, IN_USE, UNDER_MAINTENANCE).                                                                      |
| **Aircraft Status**         | Operational state of an aircraft (ACTIVE, INACTIVE, UNDER_MAINTENANCE, IN_FLIGHT).                                                    |
| **Contact Type**            | Type of contact method (PHONE, EMAIL, FAX).                                                                                           |
| **Technician**              | Agent that works in maintenance; multiple technicians can exist for the same maintenance record.                                      |
| **Supervisor**              | Agent that supervises maintenance, technicians, and manually manages parts inventory.                                                 |
| **Region**                  | A broad geographic zone encompassing multiple countries where airports are located.                                                   |
| **Fleet**                   | The total set of all airplanes managed by an Air Transport Company (ATC).                                                             |
| **Network**                 | The set of active routes operated by the company.                                                                                     |
|