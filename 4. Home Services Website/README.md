# Home Services Website

This folder contains the Home Services Website project.

## Setup

If you want to download the project and test it on your own device, follow these steps:

1. Import the data from the `backup-DataBase` folder.
2. Open the `serviciiDomiciliu` folder in an IDE (I used IntelliJ).
3. Set the Java version in `serviciiDomiciliu/pom.xml` (tested and working with Java 17).
4. Configure the database URL, username, and password in `serviciiDomiciliu/src/main/java/com/guzencu/serviciiDomiciliu/model/Service.java`, using the credentials for the database where you imported the backup.
5. Run `serviciiDomiciliu/src/main/java/com/guzencu/serviciiDomiciliu/controller/ServiciiController.java` and open `localhost:8080` in a browser.

## Presentation

If you're unable to set up the project, I will present each feature below.

### 1. Navigation bar

![image](https://github.com/user-attachments/assets/d645d33c-4cb8-48d0-acb4-8c3cc49f8575)

The navigation bar is present on every page, allowing users to navigate between sections.

**Buttons:**

- **Servicii** – redirects you to the main page.
- **Programeaza** – takes you to the appointment booking page.
- **Login** – redirects you to the login page.
- **Your username** – replaces the **Login** button when you are logged in, and clicking on it reveals additional options:

  ![image](https://github.com/user-attachments/assets/f386c457-9fc5-40b5-a681-e85d8c3e7a01)

  - **Profile** – redirects to your profile page.
  - **Facturi** – takes you to the invoices page.
  - **Programari** – displays a list of your appointments.
  - **Admin panel** – available only for users with admin privileges.
  - **Logout** – logs you out of your account.

The navigation bar is responsive on smaller screens, transforming into a burger menu:

![image](https://github.com/user-attachments/assets/fa77bea7-2363-4473-bba9-1b7e4c82c5f0)
![image](https://github.com/user-attachments/assets/3e12bea6-d140-4e4f-9cb7-89705ff90712)

### 2. Principal page

![image](https://github.com/user-attachments/assets/0888d26c-297a-44d3-aa69-1c56f9ff4c83)

Displays a list of available services. Each service has a **Programeaza** button that redirects you to the appointment booking page with the service already selected.

https://github.com/user-attachments/assets/5bf85c1a-8317-4f91-83f0-778c384da5ac

Clicking **Logout** from the navigation bar redirects the user to the principal page and displays a confirmation dialog:

![image](https://github.com/user-attachments/assets/d07546f3-2be0-40ba-ba15-53fa6563f447)

### 3. Login page

![image](https://github.com/user-attachments/assets/9952f1e1-f9a2-4ea8-9a18-8b7b7696574a)

Users can log in using the username and password they provided during registration.

- If the login is successful, the user is redirected to the index page, where their username replaces the **Login** button in the navigation bar.
- If a user has just registered, they will be redirected to this page with a success message:

  ![image](https://github.com/user-attachments/assets/a516f71b-7456-4704-8747-393acab0cc1b)

- If an incorrect username-password combination is entered, an error message appears:

  ![image](https://github.com/user-attachments/assets/b849a81a-25c7-4535-b91b-c72c300d69c4)

- If a user tries to access a restricted page (e.g., "Programari") without being logged in, they will be redirected here with the following message:

  ![image](https://github.com/user-attachments/assets/6b6a02a1-260c-4bc1-990b-7fc7fa7e8090)

### 4. Register page

![image](https://github.com/user-attachments/assets/e56a3f4b-ec3d-4a10-adf7-5a63a60d3369)

If any input data is invalid or does not comply with the website’s policies, the account will not be created, and one or more of the following errors will be displayed:

- "Numele este prea lung" - "The name is too long"
- "Numele este prea scurt" - "The name is too short"
- "Numele trebuie să conțină doar litere din alfabetul englez" -"The name must contain only letters from the English alphabet"
- "Prenumele este prea lung" - "The first name is too long"
- "Prenumele este prea scurt" - "The first name is too short"
- "Prenumele trebuie să conțină doar litere din alfabetul englez" - "The first name must contain only letters from the English alphabet"
- "Județul trebuie să conțină doar litere din alfabetul englez și să fie între 2-50 caractere" - "The county must contain only letters from the English alphabet and be between 2-50 characters long"
- "Anul de naștere nu este posibil" - "The birth year is not possible"
- "Sunteți minor pentru a avea cont, chemați un adult" - "You are underage to have an account, call an adult"
- "Orașul trebuie să conțină doar litere din alfabetul englez și să fie între 2-50 caractere" - "The city must contain only letters from the English alphabet and be between 2-50 characters long"
- "Numele străzii este prea lung" - "The street name is too long"
- "Numele străzii este prea scurt" - "The street name is too short"
- "Numele străzii conține prea puține litere" - "The street name contains too few letters"
- "Numele blocului este prea lung" - "The building name is too long"
- "Numele scării de bloc este prea lung" - "The staircase name is too long"
- "Numărul de telefon este prea lung" - "The phone number is too long"
- "Numărul de telefon este invalid. Exemplu valid: 0712345678 sau +3913512457872" - "The phone number is invalid. Valid example: 0712345678 or +3913512457872"
- "Username este prea lung (max. 50 caractere)" - "The username is too long (max. 50 characters)"
- "Parola este prea mică" - "The password is too short"
- "Parola este prea mare" - "The password is too long"
- "Parolele introduse nu sunt identice" - "The entered passwords do not match"
- "Parola trebuie să conțină cel puțin o literă mare" - "The password must contain at least one uppercase letter"
- "Parola trebuie să conțină cel puțin o literă mică" - "The password must contain at least one lowercase letter"
- "Parola trebuie să conțină cel puțin 4 cifre" - "The password must contain at least 4 digits"
- "Parola trebuie să conțină cel puțin un caracter special (!,?,@,#,!,% etc.)" - "The password must contain at least one special character (!,?,@,#,!,% etc.)"
- "Email-ul conține prea multe caractere '@'" - "The email contains too many '@' characters"
- "Email-ul conține prea multe puncte după '@'" - "The email contains too many dots after '@'"
- "Username-ul email-ului este prea scurt" - "The email username is too short"
- "Server-ul email-ului este prea scurt, încearcă cu @gmail" - "The email server is too short, try @gmail"
- "Domeniul email-ului este prea scurt, încearcă .ro sau .com" - "The email domain is too short, try .ro or .com"
- "Email-ul este prea lung" - "The email is too long"
- "Deja există un cont cu acest username" - "An account with this username already exists"
- "Deja există un cont cu acest email" - "An account with this email already exists"

Example of errors:
![image](https://github.com/user-attachments/assets/414f3799-1e8c-4ed0-8337-c1d565ee7111)

### 5. Create an appointment page

![image](https://github.com/user-attachments/assets/6eb2f87f-8dda-4d28-a11c-e891a844f082)

If there are employees with an appointment status of **"In lucru"** (meaning they are currently busy), a list of those employees will be shown to inform users that these employees may take longer to become available.

![image](https://github.com/user-attachments/assets/fcc2a9e7-eb79-4d93-938e-14f327a05a56)

After selecting a service, employee, and date, a confirmation dialog will appear:

![image](https://github.com/user-attachments/assets/85c8743d-a36a-43c2-a836-8c124cc871b4)

If there is an issue, an error message will be displayed.
![image](https://github.com/user-attachments/assets/bf8e1bf9-87f1-4a31-b0eb-db9e79f7a041)
![image](https://github.com/user-attachments/assets/14bb2c7e-62e0-4534-b7dd-bed6b446567b)
![image](https://github.com/user-attachments/assets/7a499394-fe67-4f1b-a040-48657fa27311)

If you choose a service, the list of the employees that could be selected will be cut to only that employees that practises that service, and also will appear some feedbacks for that service:
https://github.com/user-attachments/assets/1bf15020-aaff-43fc-93e7-00da526bba8d

If you choose a employee, the list of services will be cut only to that services that are done by that employee:
https://github.com/user-attachments/assets/bf5a64a8-2d61-42b5-b116-ee14629fc141

In case everything is good and the appointment is registered in the database, a message of confirmation will appear and will redirect the user to principal page:
![image](https://github.com/user-attachments/assets/cb376fed-5f9c-48b9-9165-3d48d97c9475)

### 6. Profile page

Users can update their profile details:

![image](https://github.com/user-attachments/assets/a40a930d-3177-43d4-84d2-ae66ec032418)

If any data does not meet the required criteria, an error message will appear, listing the issues.
An example of error:
![image](https://github.com/user-attachments/assets/b4af7c7e-b1ec-4bc4-b8a7-75ef16330fce)

### 7. Invoices page

Users can view their invoices here:

![image](https://github.com/user-attachments/assets/6253dc5e-86b3-4cdd-85d6-2ba025897501)

### 8. Appointments Page

![image](https://github.com/user-attachments/assets/085b88e3-cff1-4533-a473-55d5fee2630a)

Appointments are sorted in descending order by date and can be filtered by start and end dates.

https://github.com/user-attachments/assets/8e23ad24-ea03-44e0-b008-83bf00556138

For every appointment with the status "In lucru" (In progress) or "In asteptare" (Waiting), there are four available buttons:

![image](https://github.com/user-attachments/assets/cee632f1-0a07-4af6-9dd9-b012358c3184)

- **Anulează** - Cancels the appointment and updates its status to "Eșuată" (Canceled).
- **Finalizează** - Marks the appointment as finished and asks the user if they want to provide feedback.
- **Cere timp** - Requests additional time, changing the appointment status from "În așteptare" (Waiting) to "În lucru" (In progress). The assigned employee will then appear as occupied when others attempt to book an appointment with them.
- **Șterge** - Permanently removes the appointment from the database.

For appointments with the status "Eșuată" (Canceled) or "Finalizată" (Finished), only the **Șterge** button is available to delete the appointment from the database:

![image](https://github.com/user-attachments/assets/d1fb73e1-8ab5-4782-834b-df4b645d5faf)

Every action button triggers a confirmation dialog before execution. After confirmation, a status message will be displayed:

- **Programare finalizată cu succes** (Appointment successfully completed)
- **Programare prelungită cu succes** (Appointment successfully extended)
- **Programare anulată cu succes** (Appointment successfully canceled)
- **Nu s-a putut finaliza programarea** (Appointment completion failed)
- **Nu s-a putut prelungi programarea** (Appointment extension failed)
- **Nu s-a putut anula programarea** (Appointment cancellation failed)

Example of a status message:

![image](https://github.com/user-attachments/assets/c8068970-9f10-40af-afc1-ec20173ce8d1)

If the user has no appointments, the page will display:

![image](https://github.com/user-attachments/assets/36ff1fa9-b607-41c6-9675-44e741f2de91)

After successfully completing an appointment, the user will be prompted to provide feedback. If they select "DA" (Yes), they will be redirected to the **Insert a Feedback** page.

![image](https://github.com/user-attachments/assets/bde215d2-304f-455d-bb81-11d684a8a831)

### 9. Insert a Feedback Page

Based on the service and the employee who completed the appointment, the feedback page will display the employee's name and service details, along with a form where the user can share their opinion. This feedback will be visible to users selecting the same service in the **Create an Appointment** page.

![image](https://github.com/user-attachments/assets/8cced8a9-46be-4757-8c58-76dfb41b91bd)

After pressing the **Adaugă Feedback** (Add Feedback) button, a confirmation message will be displayed on the **Principal Page**.

![image](https://github.com/user-attachments/assets/25140555-c5c8-4aa0-9b6a-8d44fdaee8b3)

### 10. Admin Page

This page is accessible only to users with admin privileges assigned in the database.

![image](https://github.com/user-attachments/assets/02addac7-90a4-4fbe-b629-5dfb67e7488e)

One of the features on this page is the ability to add a new service by clicking the plus button or the "Adaugă un nou serviciu" (Add a new service) button:

![image](https://github.com/user-attachments/assets/b071449c-845c-4f5b-801b-bea788990792)

This action redirects the user to a dedicated page for adding a new service:

![image](https://github.com/user-attachments/assets/f2124c84-75b5-4173-a701-6a3d4794a382)

If the user attempts to add a service with the same name as an existing one, an error message will be displayed:

![image](https://github.com/user-attachments/assets/8875ea65-0250-45ea-8743-f596dfb485a1)

Each service entry has two buttons:

- **Editează** (Edit) - Allows modification of the service details.
- **Șterge** (Delete) - Removes the service from the system.

Clicking the **Editează** button redirects the user to a form where they can modify the service details:

![image](https://github.com/user-attachments/assets/e9269f96-d5f2-4405-bc9f-2178e12963cd)

After pressing the **Salvează** (Save) button, a confirmation message will be displayed:

![image](https://github.com/user-attachments/assets/71fab7d6-7693-4ebf-832d-fe9b7fa863a3)

Clicking the **Șterge** button triggers a confirmation dialog before proceeding with the deletion:

![image](https://github.com/user-attachments/assets/a5041813-fe8e-477d-88e0-daef487a9771)

After confirmation, a status message will be displayed:

![image](https://github.com/user-attachments/assets/6ef5f7aa-f8f0-443e-9aaa-36ca63ae1cb6)

### Future Improvements

- Ability to insert, delete, and modify employees from the UI.
- Invoice management system.
- Support for inserting images and storing their paths in the database for services.
- Extended admin privileges for managing additional entities (delete, modify, edit).
