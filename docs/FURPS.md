# Supplementary Specification (FURPS+)

## Functionality

_Specifies functionalities that:  
&nbsp; &nbsp; (i) are common across several US/UC;  
&nbsp; &nbsp; (ii) are not related to US/UC, namely: Audit, Reporting and Security._

(fill in here)
* **Authentication & Authorization:** The system must implement Role-Based Access Control (RBAC) for different actors: Admin, Backoffice Operator, ATCC, Maintenance Technician, and Maintenance Supervisor.
* **Security:** All authenticated requests must use JSON Web Tokens (JWT).
* **Reporting:** The system must support data exploration and reporting for metrics such as top 5 utilized aircraft, monthly average flights per route, and maintenance turnaround time.
* **Audit:** The system must keep track of route history.
* **Data Validation:** All input data must be validated, including IATA codes (3-letter format), aircraft registration numbers, and numeric ranges for specifications

## Usability 

_Evaluates the user interface. It has several subcategories,
among them: error prevention; interface aesthetics and design; help and
documentation; consistency and standards._

(fill in here)
**API Documentation:** The service layer must provide an OpenAPI specification for clear interface documentation.
**Error Handling:** The system must provide meaningful error messages and appropriate HTTP status codes.
**Consistency:** APIs must follow consistent naming conventions for endpoints.
**Navigation:** Resource representations must include links (HATEOAS) to guide the user/frontend through the application state.

## Reliability
Whenever the system fails, there should be no
data loss
(fill in here)

* **Data Integrity:** Whenever the system fails, there should be no data loss.
* **Concurrency Control:** The system must handle concurrent access with proper management of race conditions, specifically using optimistic locking for status updates.
* **Robustness:** The system must verify that both origin and destination airports exist and are operational before route creation.


## Performance
The system should start up in less than 10 seconds.

At certain times of the day, it is expected that the system will be overloaded. To avoid
potential problems, the system must be prepared so that the response time is at maximum of 5
seconds regardless of the existing load.
Overall system availability must be higher than 99% per year. It is also important that the system is
prepared to easily support data persistence on multiple target systems as, for instance, relational
databases, NoSQL databases or in memory databases.

(fill in here)
**Startup Time:** The system should start up in less than 10 seconds.
**Response Time:** The system must be prepared so that the response time is at maximum of 5 seconds regardless of the existing load.
**Availability:** Overall system availability must be higher than 99% per year.
**Scalability:** Long result lists in API responses must support pagination to maintain performance.

## Supportability
_The supportability requirements gathers several characteristics, such as:
testability, adaptability, maintainability, compatibility,
configurability, installability, scalability and more._ 

(fill in here)
**Testability:** The system requires automated tests, including unit tests and Postman collections with test scripts for all use cases.
**Maintainability:** The project must follow SOLID principles and best practices learned throughout the course.
**Adaptability:** The system should support data persistence on multiple target systems such as relational, NoSQL, or in-memory databases.

## +

### Design Constraints

_Specifies or constraints the system design process. Examples may include: programming languages, software process, mandatory standards/patterns, use of development tools, class library, etc._

(fill in here)
### Design Constraints
**Process:** The project development must follow an iterative software engineering process.
**Architecture:** The service layer must expose its functionality via RESTful APIs so that any frontend can interact with it.
**Documentation:** Design documentation must include a synchronized Domain Model and Glossary of Concepts.

### Implementation Constraints

_Specifies or constraints the code or construction of a system such
such as: mandatory standards/patterns, implementation languages,
database integrity, resource limits, operating system._

(fill in here)
**Version Control:** Regular commits to a Git repository with meaningful commit messages are mandatory.
**Technical Stack:** Third-party libraries may be used but must be justified.
**Data Bootstrapping:** The system must allow preloading of manufacturers, airport types, and maintenance templates.

### Interface Constraints
_Specifies or constraints the features inherent to the interaction of the
system being developed with other external systems._

(fill in here)
### Interface Constraints
**RESTful APIs:** Use of proper HTTP methods (GET, POST, PUT, PATCH, DELETE) is mandatory.
**External Data:** The system must support importing bulk airport data from CSV files.
**Data Export:** The system should support exporting route network data in standard aviation formats like GeoJSON or KML.

### Physical Constraints

_Specifies a limitation or physical requirement regarding the hardware used to house the system, as for example: material, shape, size or weight._

(fill in here)
**Hardware:** There are no specific physical limitations or hardware requirements identified for housing the backend system.