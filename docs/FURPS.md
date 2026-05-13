# Supplementary Specification (FURPS+)

## Functionality

### Authentication & Authorization
The system must implement Role-Based Access Control (RBAC) for different actors:
- Admin
- Backoffice Operator
- ATCC
- Maintenance Technician
- Maintenance Supervisor

### Security
All authenticated requests must use JSON Web Tokens (JWT).

### Reporting
The system must support data exploration and reporting for metrics such as:
- Top 5 utilized aircraft
- Monthly average flights per route
- Maintenance turnaround time

### Audit
The system must keep track of route history.

### Data Validation
All input data must be validated, including:
- IATA codes (3-letter format)
- Aircraft registration numbers
- Numeric ranges for specifications

---

## Usability

### API Documentation
The service layer must provide an OpenAPI specification for clear interface documentation.

### Error Handling
The system must provide meaningful error messages and appropriate HTTP status codes.

### Consistency
APIs must follow consistent naming conventions for endpoints.

### Navigation
Resource representations must include links (HATEOAS) to guide the collaborator/frontend through the application state.

---

## Reliability

### Data Integrity
Whenever the system fails, there should be no data loss.

### Concurrency Control
The system must handle concurrent access with proper management of race conditions, specifically using optimistic locking for status updates.

### Robustness
The system must verify that both origin and destination airports exist and are operational before route creation.

---

## Performance

### Startup Time
The system should start up in less than 10 seconds.

### Response Time
The system must be prepared so that the response time is at maximum of 5 seconds regardless of the existing load.

### Availability
Overall system availability must be higher than 99% per year.

### Scalability
Long result lists in API responses must support pagination to maintain performance.

---

## Supportability

### Testability
The system requires automated tests, including unit tests and Postman collections with test scripts for all use cases.

### Maintainability
The project must follow SOLID principles and best practices learned throughout the course.

### Adaptability
The system should support data persistence on multiple target systems such as relational, NoSQL, or in-memory databases.

---

## Design Constraints

### Process
The project development must follow an iterative software engineering process.

### Architecture
The service layer must expose its functionality via RESTful APIs so that any frontend can interact with it.

### Documentation
Design documentation must include a synchronized Domain Model and Glossary of Concepts.

---

## Implementation Constraints

### Version Control
Regular commits to a Git repository with meaningful commit messages are mandatory.

### Technical Stack
Third-party libraries may be used but must be justified.

### Data Bootstrapping
The system must allow preloading of manufacturers, airport types, and maintenance templates.

---

## Interface Constraints

### RESTful APIs
Proper use of HTTP methods (GET, POST, PUT, PATCH, DELETE) is mandatory.

### External Data
The system must support importing bulk airport data from CSV files.

### Data Export
The system should support exporting route network data in standard aviation formats like GeoJSON or KML.

---

## Physical Constraints

### Hardware
There are no specific physical limitations or hardware requirements identified for housing the backend system.