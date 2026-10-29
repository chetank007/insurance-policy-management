# Mini Project Report Content (transfer this into your faculty's report template)

**Project Title:** Insurance Policy Management System  
**Assignment:** ESDL Assignment No. 7  
**Technology:** Java 17, Spring Boot, Spring Web, Spring Data JPA, Spring Security, Bean Validation, H2 Database  
**Interface:** Backend REST API only; no frontend/UI/HTML.

## Abstract
The Insurance Policy Management System is a backend application that lets users browse insurance policies, purchase policies, and submit claims with incident details and supporting documents. An administrator can inspect submitted claims and approve or reject them with remarks. The application demonstrates layered architecture and Spring Boot annotations through REST controllers, services, JPA repositories, entity mapping, validation, authentication, and role-based authorization.

## Problem Statement
Manual insurance policy and claim processing can be difficult to track and prone to inconsistent records. The project models a centralized backend for policy discovery, policy purchases, claim submission, and administrator review.

## Objectives
1. Provide REST endpoints to browse policies.
2. Allow authenticated users to purchase policies.
3. Accept claim details and a required supporting document.
4. Restrict claim review and policy creation to administrators.
5. Persist policies, purchases, and claims in an H2 database.
6. Demonstrate Spring Boot, Spring MVC, JPA, validation, and Spring Security.

## Modules
- Policy Management: browse policy catalogue; admin creates policies.
- Purchase Management: associates a policy with the authenticated username.
- Claim Management: validates and records claim details and uploaded evidence.
- Admin Review: lists all claims and records APPROVED or REJECTED status and remarks.
- Security: HTTP Basic demo authentication with USER and ADMIN roles.

## Architecture
Client (Postman/curl) -> REST Controller -> Service Layer -> Spring Data JPA Repository -> H2 Database. Claim evidence is stored in the local `uploads/claims` directory. Spring Security protects authenticated endpoints and admin-only operations.

## Database Entities
- Policy: id, name, description, category, premiumAmount, coverageAmount, active.
- Purchase: id, policy, username, purchasedAt, status.
- Claim: id, purchase, username, incidentDetails, claimAmount, documentPath, status, adminRemarks, submittedAt, reviewedAt.

## How to Demonstrate
1. Start the application.
2. GET `/api/policies` and show seeded policies.
3. POST `/api/purchases/1` as `user:user123`.
4. Submit a claim to `/api/claims` as multipart form-data, attaching a PDF/JPG/PNG.
5. GET `/api/claims/admin` as `admin:admin123`.
6. PATCH the claim review endpoint with `APPROVED` or `REJECTED`.
7. GET `/api/claims/mine` as the user and show the updated status.

## Testing Evidence to Capture
Take screenshots from Postman or terminal for: policy listing, successful purchase, claim submission, admin claim list, claim approval/rejection, and final user claim status. Include these screenshots in the report template if requested by the instructor.

## Conclusion
The project demonstrates a working backend workflow for insurance policy purchase and claim review using Spring Boot and REST APIs. It can be extended with persistent user accounts, secure document download, email notifications, and production-grade authentication.

**GitHub Repository:** Add your actual repository URL after pushing the source code.  
**Group Members:** Fill in names and roll numbers.  
**Faculty Template:** Transfer these sections into the official report template supplied by the faculty.
