# Warmup-Email-Service

This project is a microservice-based email warmup service that simulates sending emails while enforcing daily quotas, validating email addresses, and re-queuing failed attempts. It includes a basic REST API to store credentials and a simple UI (using Thymeleaf) to facilitate manual testing.

# **Features**

## **Email Address Resolution:**

Combines tenantId and userId to create a valid email address for a company user.

## **Daily Quota Check:**

Ensures that a tenant does not exceed a predefined daily email quota. (For production, consider a distributed rate limiter such as Redis.)

## **Email Validation:**

Checks if an email is reachable and valid. Rejects throwaway email addresses (e.g., those ending with @tempmail.com).

## **Email Sending & Re-queuing:**

Attempts to send emails; on failure or if the quota is exceeded, the email event is re-queued with a specified delay.

## **Detailed Logging:**

Logs every step in the process (email resolution, quota check, validation, sending, re-queuing).

## **Credentials Management API:**

Provides REST endpoints to store and retrieve email provider credentials (e.g., Gmail and Outlook).

## **Simple UI:**

A basic Thymeleaf-based frontend allows you to link credentials and trigger email events for manual testing.

## **Prerequisites**

1. Java 17 or later
2. Maven
3. An IDE (e.g., IntelliJ IDEA) or command-line environment

A web browser (for the UI and H2 console)

Getting Started
1. Clone the Repository

   `git clone <your-repository-url>
   cd emailwarmup`

2. Build the Project
   `Use Maven to build the project:
mvn clean install 
cd api`
3. Run the Application
   You can run the application using `Maven: mvn spring-boot:run`

Alternatively, run the main class EmailWarmupApplication.java from your IDE.

Upon startup, you should see log messages indicating that:

The application is running on port 8080.

The H2 Console is available at /h2-console.

Detailed logs show each stage of the email processing workflow.

## **Using the UI**

Once the application is running, open your web browser and navigate to:
http://localhost:8080/
The UI page provides two main sections:

A. **Link Account / Add Credentials**
Fill the Form:
Enter details for linking an account:

`ID: e.g., tenant1_user1

Tenant ID: e.g., tenant1

User ID: e.g., user1

Provider: Select either "Gmail" or "Outlook"

Token: e.g., token1`

Generate Test Data:
Click the Generate Test Data button (next to "Link Credential") to automatically fill the fields with sample data:


`{
"id": "tenant1_user3",
"tenantId": "tenant2",
"userId": "user2@tenant1.company.com",
"provider": "Outlook",
"token": "token3"
}`


Link Credential:
Click the Link Credential button to save the credential.
The page will refresh and display the stored credentials in a table below the form.

B. Send Test Email Event
Fill the Form:
Enter the following details:

Tenant ID: e.g., tenant1

User ID: e.g., user1

Subject: e.g., Test Email

Body: e.g., Hello, this is a test.

Note: The email address will be resolved by combining userId and tenantId (e.g., user1@tenant1.company.com).

Send Email Event:
Click the Send Email Event button to trigger the event.

The event will be processed by the service:

The email address is resolved.

The quota is checked.

The email address is validated.

The email is sent or re-queued if sending fails.

Monitor the application console to view detailed log messages at each step.

H2 Console Access
You can also inspect the database using the H2 Console:

Open your browser and go to:
http://localhost:8080/h2-console

Use the following settings:

JDBC URL: jdbc:h2:mem:emailwarmup

User Name: sa

Password: pass

Click Connect and run queries (e.g., SELECT * FROM EMAIL_CREDENTIAL;) to verify stored data.


# **Manual Testing Steps Recap**

**Run the Application:**
Start the application via mvn spring-boot:run or your IDE.

**Use the UI:**

1. Open http://localhost:8080/ in a browser.

2. Link credentials using the form. Use the Generate Test Data button for sample input.

3. Send a test email event using the provided form.

4. Check the console logs for detailed output (email resolution, quota check, validation, sending/re-queuing).

**Inspect Data:**

1. Use the H2 Console at http://localhost:8080/h2-console to verify stored credentials.

2. Verify that the logs capture each processing step.

# **Troubleshooting**

**1. Application Startup Issues:**
Ensure that your Maven dependencies are aligned (preferably use a single Spring Boot version via the parent POM) and run mvn clean install to rebuild.

**2. UI Not Loading:**
Verify that the Thymeleaf dependency is included in your pom.xml and that the controller mappings are correct.

**3. Log Messages Missing:**
Check your logging configuration in application.properties (e.g., setting logging.level.com.warmup.email.service=DEBUG).

**4. H2 Console Issues:**
Ensure the H2 console is enabled with spring.h2.console.enabled=true in your application.properties.

## **Conclusion**

This project provides a fully functional email warmup service with a REST API and a user-friendly UI for manual testing. Follow the steps above to link credentials, simulate sending emails, and monitor processing through detailed logs. Feel free to extend or customize the service further for your needs.