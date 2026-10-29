# Insurance Policy Management System
**ESDL Assignment No. 7 | Java Lab Mini Project**  
Backend-only REST API built with Java 17, Spring Boot, Spring Web, Spring Data JPA, Spring Security, Bean Validation and H2 database. No UI, HTML, or frontend is included.

## Features
- Browse active insurance policies and view policy details.
- Authenticated user can purchase a policy and view their purchases.
- Submit claims against policies the authenticated user purchased.
- Claim submission requires incident details, claim amount, and a supporting PDF/JPG/PNG document (maximum 5 MB).
- Admin can create policies, view all policies and claims, and approve/reject claims with remarks.
- Role-based access using HTTP Basic authentication.
- H2 file database, seeded demo policies, validation, REST exception handling, and JPA entities/repositories/services/controllers.

## Requirements
- JDK 17 or later
- Maven 3.9+ (or use the Maven wrapper supplied by your IDE if available)
- IntelliJ IDEA, Eclipse, Spring Tool Suite, or VS Code with Java extensions

## Run
1. Extract the ZIP and open the folder containing `pom.xml` in your IDE.
2. Set the project SDK/JDK to 17.
3. Let Maven import and download dependencies.
4. Run `InsurancePolicyManagementApplication.java`, or from a terminal run:
   ```bash
   mvn spring-boot:run
   ```
5. Base URL: `http://localhost:8080`

The database persists in `./data/insurance_db`. Uploaded claim documents are saved under `./uploads/claims/`.

## Demo accounts
| Role | Username | Password |
|---|---|---|
| User | `user` | `user123` |
| Admin | `admin` | `admin123` |

These are demonstration credentials only. Change them before any real deployment.

## API demonstration sequence
Use Postman, Insomnia, curl, or any REST client. Browser-based API calls are not required.

### 1. Browse available policies (public)
```bash
curl http://localhost:8080/api/policies
```
Expected: JSON array containing SecureLife Basic, HealthGuard Plus, and DriveSafe Motor.

### 2. Purchase policy ID 1 (login as user)
```bash
curl -u user:user123 -X POST http://localhost:8080/api/purchases/1
```
Record the returned purchase `id` for the next step.

### 3. View your purchases
```bash
curl -u user:user123 http://localhost:8080/api/purchases/mine
```

### 4. Submit a claim with a supporting document
Create a small test PDF, JPG, or PNG named `proof.pdf` in the current terminal directory, then run:
```bash
curl -u user:user123 -X POST http://localhost:8080/api/claims   -F "purchaseId=1"   -F "incidentDetails=Hospital admission due to an accident"   -F "claimAmount=25000.00"   -F "document=@proof.pdf;type=application/pdf"
```
Important: `purchaseId` must be the purchase ID returned by step 2, not the policy ID. Use a real PDF/JPG/PNG file for the document field.

### 5. Admin reviews claims
```bash
curl -u admin:admin123 http://localhost:8080/api/claims/admin
```
Approve:
```bash
curl -u admin:admin123 -X PATCH http://localhost:8080/api/claims/1/review   -H "Content-Type: application/json"   -d '{"decision":"APPROVED","remarks":"Documents verified"}'
```
Or reject:
```bash
curl -u admin:admin123 -X PATCH http://localhost:8080/api/claims/1/review   -H "Content-Type: application/json"   -d '{"decision":"REJECTED","remarks":"Required supporting details are missing"}'
```
Use the actual claim ID returned by step 4.

### 6. View your claim status
```bash
curl -u user:user123 http://localhost:8080/api/claims/mine
```

### 7. Admin adds a policy (optional)
```bash
curl -u admin:admin123 -X POST http://localhost:8080/api/policies   -H "Content-Type: application/json"   -d '{"name":"Travel Protect","description":"Coverage for eligible travel disruptions","category":"TRAVEL","premiumAmount":450.00,"coverageAmount":100000.00}'
```

## API summary
| Method | Endpoint | Access | Purpose |
|---|---|---|---|
| GET | `/api/policies` | Public | Browse active policies |
| GET | `/api/policies/{id}` | Public | Get policy details |
| GET | `/api/policies/admin/all` | Admin | List all policies |
| POST | `/api/policies` | Admin | Create a policy |
| POST | `/api/purchases/{policyId}` | Authenticated | Purchase a policy |
| GET | `/api/purchases/mine` | Authenticated | View own purchases |
| POST | `/api/claims` | Authenticated | Submit claim + document |
| GET | `/api/claims/mine` | Authenticated | View own claims |
| GET | `/api/claims/admin` | Admin | View all claims |
| PATCH | `/api/claims/{id}/review` | Admin | Approve/reject claim |

## H2 console
For local demonstration only: `http://localhost:8080/h2-console`  
JDBC URL: `jdbc:h2:file:./data/insurance_db`  
Username: `sa`  
Password: leave blank.

## Spring Boot concepts demonstrated
`@SpringBootApplication`, `@Configuration`, `@Bean`, `@RestController`, `@RestControllerAdvice`, `@RequestMapping`, `@GetMapping`, `@PostMapping`, `@PatchMapping`, `@PathVariable`, `@RequestParam`, `@RequestPart`, `@RequestBody`, `@Valid`, `@Entity`, `@Table`, `@Id`, `@GeneratedValue`, `@ManyToOne`, `@JoinColumn`, `@Enumerated`, `@Repository` via Spring Data interfaces, `@Service`, `@Transactional`, `@PreAuthorize`, validation annotations, and constructor-based dependency injection.

## Notes / limitations
- This is an academic demonstration project, not a production insurance platform.
- Demo users are in-memory accounts; user and admin accounts are not persisted in the database.
- File upload uses local disk storage. In a production system, add antivirus scanning, stricter content inspection, audit logging, secure document retrieval, password/secret management, HTTPS, and a formal identity/authorization model.
- Claim documents are stored on the server and their path is included in claim JSON for demonstration; a production API should not expose filesystem paths.
- The app intentionally has no UI or HTML pages. Use Postman/curl for all demonstrations.
