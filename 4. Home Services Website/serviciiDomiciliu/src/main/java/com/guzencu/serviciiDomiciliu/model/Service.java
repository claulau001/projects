package com.guzencu.serviciiDomiciliu.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

public class Service {

    // To change to your database credits
    private static String databaseURL = "jdbc:mysql://localhost:3306/serviciidomiciliu";
    private static String databaseUser = "root";
    private static String databasePassword = "da123";
    public static List<Serviciu> requestServicii(){
        // Create an empty service list to store the results obtained from the database
        List<Serviciu> servicii = new ArrayList<>();
        try {
            // Establish a connection to the MySQL database using DriverManager
            Connection conn = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);

            // Write the SQL query to get all the services from the 'Servicii' table
            String sql = "SELECT * FROM Servicii";
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Execute the SQL query and get the results in a ResultSet
            ResultSet rs = stmt.executeQuery();

            // Iterate through each row in the ResultSet and add a Serviciu object to the services list
            while (rs.next()) {
                servicii.add(new Serviciu(
                        rs.getInt("ServiciuId"),          // Retrieves the value from the 'ServiceId' column
                        rs.getString("NumeServiciu"),     // Retrieves the value from the 'NumeServiciu' column
                        rs.getString("Descriere"),        // Retrieves the value from the 'Descriere' column
                        rs.getInt("PretPeOra"),           // Retrieves the value from the 'PretPeOra' column
                        rs.getString("NumeImagine")       // Retrieves the value from the 'NumeImagine' column
                ));
            }

            // Close the connection, statement, and resultSet to free up resources
            conn.close();
            stmt.close();
            rs.close();

            // Returns the list of services obtained from the database
            return servicii;
        } catch (Exception e) {
            // In case of error, the error message is displayed and an empty list is returned.
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

    public static boolean verifyUserParameters(Model model, String nume, String prenume,
                                               String dataNastere, String judet, String oras, String strada,
                                               String bloc, String scara, String email, String telefon, String username, String password,
                                               String confirmedPassword){
        boolean validData = true;
        int n;

        // Check Last Name Policy
        if(nume != null && !nume.isEmpty()) {
            n = nume.length();
            boolean numeContainsOnlyLetters = true;
            if (n > 50) {
                model.addAttribute("longNameError", true);
                validData = false;
            }
            if (n < 2) {
                model.addAttribute("shortNameError", true);
                validData = false;
            }
            for (int i = 0; i < n; i++) {
                if (!((nume.charAt(i) >= 'a' && nume.charAt(i) <= 'z') || (nume.charAt(i) >= 'A' && nume.charAt(i) <= 'Z'))) {
                    numeContainsOnlyLetters = false;
                    break;
                }
            }
            if (!numeContainsOnlyLetters) {
                model.addAttribute("numeNotContainsOnlyLettersError", true);
                validData = false;
            } else {
                nume = nume.toUpperCase();
            }
        }

        // Check First Name Policy
        if (prenume != null && !prenume.isEmpty()) {
            n = prenume.length();
            boolean prenumeContainsOnlyLetters = true;
            if (n > 50) {
                model.addAttribute("longPrenumeError", true);
                validData = false;
            }
            if (n < 2) {
                model.addAttribute("shortPrenumeError", true);
                validData = false;
            }
            for (int i = 0; i < n; i++) {
                if (!((prenume.charAt(i) >= 'a' && prenume.charAt(i) <= 'z') || (prenume.charAt(i) >= 'A' && prenume.charAt(i) <= 'Z'))) {
                    prenumeContainsOnlyLetters = false;
                    break;
                }
            }
            if (!prenumeContainsOnlyLetters) {
                model.addAttribute("prenumeNotContainsOnlyLettersError", true);
                validData = false;
            } else {
                prenume = prenume.substring(0, 1).toUpperCase() + prenume.substring(1).toLowerCase();
            }
        }

        // County policy check
        if(judet != null && !judet.isEmpty()) {
            n = judet.length();
            boolean judetContainsOnlyLetters = true;
            for (int i = 0; i < n; i++) {
                if (!((judet.charAt(i) >= 'a' && judet.charAt(i) <= 'z') || (judet.charAt(i) >= 'A' && judet.charAt(i) <= 'Z'))) {
                    judetContainsOnlyLetters = false;
                    break;
                }
            }
            if (n > 50 || n < 2 || !judetContainsOnlyLetters) {
                model.addAttribute("judetError", true);
                validData = false;
            }
        }

        // Check birth date policy
        if(dataNastere != null && !dataNastere.isEmpty()) {
            int anNastereClient = Integer.parseInt(dataNastere.substring(0, 4));
            if (anNastereClient < 1900 || anNastereClient > 2025) {
                model.addAttribute("anNastereError", true);
                validData = false;
            }
            if (anNastereClient > 2006) {
                model.addAttribute("minorError", true);
                validData = false;
            }
        }

        // City policy check
        if(oras != null && !oras.isEmpty()) {
            n = oras.length();
            boolean orasContainsOnlyLetters = true;
            for (int i = 0; i < n; i++) {
                if (!((oras.charAt(i) >= 'a' && oras.charAt(i) <= 'z') || (oras.charAt(i) >= 'A' && oras.charAt(i) <= 'Z'))) {
                    orasContainsOnlyLetters = false;
                    break;
                }
            }
            if (n > 50 || n < 2 || !orasContainsOnlyLetters) {
                model.addAttribute("orasError", true);
                validData = false;
            }
        }

        // Street policy check
        if(strada != null && !strada.isEmpty()) {
            n = strada.length();
            int stradaLetterCount = 0;
            if (n > 50) {
                model.addAttribute("longStradaError", true);
                validData = false;
            }
            if (n < 2) {
                model.addAttribute("shortStradaError", true);
                validData = false;
            }
            for(int i=0;i<n;i++){
                if((strada.charAt(i) >= 'a' && strada.charAt(i) <='z') || (strada.charAt(i) >= 'A' && strada.charAt(i) <= 'Z')) {
                    stradaLetterCount++;
                }
            }
            if(stradaLetterCount < 4){
                model.addAttribute("lessLettersStradaError", true);
                validData = false;
            }
        }

        // Apartament Building policy check
        if(bloc != null && !bloc.isEmpty()){
            if(bloc.length() > 10){
                model.addAttribute("longBlocError", true);
                validData = false;
            }
        }

        // Check apartament building stair policy
        if(scara != null && !scara.isEmpty()){
            if(scara.length() > 10){
                model.addAttribute("longScaraError", true);
                validData = false;
            }
        }

        // Phone number policy check
        if(telefon != null && !telefon.isEmpty()) {
            n = telefon.length();
            boolean telefonValid = true;
            if (!((telefon.charAt(0) >= '0' && telefon.charAt(0) <= '9') || telefon.charAt(0) == '+'))
                telefonValid = false;
            for (int i = 1; i < n; i++) {
                if (!(telefon.charAt(i) >= '0' && telefon.charAt(i) <= '9')) {
                    telefonValid = false;
                    break;
                }
            }
            if (n > 15) {
                model.addAttribute("longTelefonError", true);
                validData = false;
            }
            if (!telefonValid) {
                model.addAttribute("notValidTelefonError", true);
                validData = false;
            }
        }

        // Check username policy
        if(username != null && !username.isEmpty()) {
            if (username.length() > 50) {
                model.addAttribute("longUsernameError", true);
                validData = false;
            }
        }

        // Password policy check
        if(password != null && !password.isEmpty()) {
            if (password.length() < 3) {
                model.addAttribute("shortPasswordError", true);
                validData = false;
            }
            if (password.length() > 255) {
                model.addAttribute("longPasswordError", true);
                validData = false;
            }
            if (!password.equals(confirmedPassword)) {
                model.addAttribute("differentPasswordError", true);
                validData = false;
            }
            boolean passwordContainsSmallLetter = false;
            boolean passwordContainsBigLetter = false;
            boolean passwordContainsMin4Digits = false;
            boolean passwordContainsSpecialCaracter = false;
            int digitCount = 0;
            n = password.length();
            for (int i = 0; i < n; i++) {
                if (password.charAt(i) >= 'a' && password.charAt(i) <= 'z') {
                    passwordContainsSmallLetter = true;
                }
                if (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z') {
                    passwordContainsBigLetter = true;
                }
                if (password.charAt(i) >= '0' && password.charAt(i) <= '9') {
                    digitCount++;
                    if (digitCount == 4) {
                        passwordContainsMin4Digits = true;
                    }
                }
                if ((password.charAt(i) >= '!' && password.charAt(i) <= '/') ||
                        (password.charAt(i) >= ':' && password.charAt(i) <= '@') ||
                        (password.charAt(i) >= '[' && password.charAt(i) <= '_') ||
                        (password.charAt(i) >= '{' && password.charAt(i) <= '~')) {
                    passwordContainsSpecialCaracter = true;
                }
            }
            if (!passwordContainsBigLetter) {
                model.addAttribute("passwordNotContainsBigLetterError", true);
                validData = false;
            }
            if (!passwordContainsSmallLetter) {
                model.addAttribute("passwordNotContainsSmallLetterError", true);
                validData = false;
            }
            if (!passwordContainsMin4Digits) {
                model.addAttribute("passwordNotContainsMin4DigitsError", true);
                validData = false;
            }
            if (!passwordContainsSpecialCaracter) {
                model.addAttribute("passwordNotContainsSpecialCaracterError", true);
                validData = false;
            }
        }

        // Check email policy
        if(email != null && !email.isEmpty()) {
            n = email.length();
            int arondCount = 0;
            int dotCount = 0;
            int arondIndex = -1;
            int dotIndex = -1;
            boolean tooManyAronds = false;
            boolean tooManyDots = false;
            for (int i = 0; i < n; i++) {
                if (email.charAt(i) == '@') {
                    arondCount++;
                    if (arondCount > 1) {
                        tooManyAronds = true;
                        break;
                    }
                    arondIndex = i;
                }
                if (email.charAt(i) == '.' && arondCount == 1) {
                    dotCount++;
                    if (dotCount > 1) {
                        tooManyDots = true;
                        break;
                    }
                    dotIndex = i;
                }
            }
            if (tooManyAronds) {
                model.addAttribute("tooManyArondsEmailError", true);
                validData = false;
            }
            if (tooManyDots) {
                model.addAttribute("tooManyDotsEmailError", true);
                validData = false;
            }
            if (arondIndex < 2) {
                model.addAttribute("shortEmailUsernameError", true);
                validData = false;
            }
            if (dotIndex - arondIndex - 1 < 3) {
                model.addAttribute("shortEmailServerError", true);
                validData = false;
            }
            if (n - dotIndex < 2) {
                model.addAttribute("shortEmailDomainError", true);
                validData = false;
            }
            if (n > 100) {
                model.addAttribute("longEmailError", true);
                validData = false;
            }
        }
        return validData;
    }

    public static boolean createUser(Model model, String nume, String prenume, int sexValue, String dataNastere,
                                     String judet, String oras, String strada, Integer numarStrada, String bloc,
                                     String scara, Integer apartament, String email, String telefon, String username,
                                     String password){
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);
            boolean success = true;
            // Verify existing username
            String sql = "SELECT * FROM users WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                model.addAttribute("usernameExistsError", true);
                success = false;
            }
            // Verify existing email
            sql = "SELECT * FROM users WHERE Email = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            if (rs.next()) {
                model.addAttribute("emailExistsError", true);
                success = false;
            }

            if (success) {
                sql = "INSERT INTO users (username, password, Nume, Prenume, Sex, Judet, DataNastere, Oras, Strada, NumarStrada, Bloc, Scara, Apartament, "
                        + "NumarTelefon, Email) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, nume);
                stmt.setString(4, prenume);

                if (!(sexValue == 1)) {
                    if (sexValue == 2) {
                        stmt.setString(5, "M");
                    } else {
                        stmt.setString(5, "F");
                    }
                } else {
                    stmt.setNull(5, java.sql.Types.CHAR);
                }

                stmt.setString(6, judet);
                stmt.setString(7, dataNastere);
                stmt.setString(8, oras);

                if (strada != null && !strada.isEmpty()) {
                    stmt.setString(9, strada);
                } else {
                    stmt.setNull(9, java.sql.Types.VARCHAR);
                }

                if (numarStrada != null) {
                    stmt.setInt(10, numarStrada);
                } else {
                    stmt.setNull(10, java.sql.Types.INTEGER);
                }

                if (bloc != null && !bloc.isEmpty()) {
                    stmt.setString(11, bloc);
                } else {
                    stmt.setNull(11, java.sql.Types.NVARCHAR);
                }

                if (scara != null && !scara.isEmpty()) {
                    stmt.setString(12, scara);
                } else {
                    stmt.setNull(12, java.sql.Types.NVARCHAR);
                }

                if (apartament != null) {
                    stmt.setInt(13, apartament);
                } else {
                    stmt.setNull(13, java.sql.Types.INTEGER);
                }

                stmt.setString(14, telefon);
                stmt.setString(15, email);

                int rowsAffected = stmt.executeUpdate();
                if(rowsAffected > 0) {
                    model.addAttribute("succes", true);
                    model.addAttribute("justRegistered", true);
                    return true;
                } else {
                    model.addAttribute("succes", false);
                    model.addAttribute("faraLiniiModificate",true);
                    return false;
                }
            }
            else {
                model.addAttribute("succes", false);
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                if(rs != null){
                    rs.close();
                }
                if(stmt != null){
                    stmt.close();
                }
                if(conn != null){
                    conn.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static boolean tryLogin(Model model, HttpSession session, String username, String password){
        try {
            // Establish a connection to the MySQL database using DriverManager
            Connection conn = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);

            // SQL query to check if a user with a matching username and password exists
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Sets the values ​​of the username and password parameters in the SQL query
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Execute the query and get the results in a ResultSet
            ResultSet rs = stmt.executeQuery();

            // If a user with that username and password exists
            if(rs.next()){
                // Check if the user is an administrator
                boolean isAdmin = rs.getString("IsAdmin").equals("DA");

                // Creates a User object using the values ​​extracted from the ResultSet
                User user = new User(
                        rs.getInt("UserId"), rs.getString("Nume"), rs.getString("Prenume"),
                        rs.getString("Sex"), rs.getString("DataNastere"), rs.getString("Judet"),
                        rs.getString("Oras"), rs.getString("Strada"), rs.getInt("NumarStrada"),
                        rs.getString("Bloc"), rs.getString("Scara"), rs.getInt("Apartament"),
                        rs.getString("Email"), rs.getString("NumarTelefon"),
                        rs.getString("username"), rs.getString("password"), isAdmin
                );

                // Stores the User object in the session for later access
                session.setAttribute("user", user);

                // Add the User object to the model to be available in the view
                model.addAttribute("user", user);

                // Close the database connection
                conn.close();

                // Get the list of services and add it to the model to be displayed
                List<Serviciu> servicii = Service.requestServicii();
                model.addAttribute("servicii", servicii);

                // Returns true, meaning the login was successful.
                return true;
            }
            else {
                // If no user with matching username and password was found
                model.addAttribute("errorWrongPasswordOrUsername", 1);

                // Close the database connection
                conn.close();

                // Returns false, meaning that the login failed.
                return false;
            }

        } catch (Exception e) {
            // In case of error, the exception is caught and the error message is displayed.
            e.printStackTrace();
            return false;
        }

    }

    public static boolean updateUserProfile(Model model, HttpSession session,String nume, String prenume, String judet, String oras, String strada,
                                            Integer numarStrada, String bloc, String scara, Integer apartament, String email, String telefon, String username, String password,
                                            String currentUsername) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);
            boolean success = true;
            // Verify existing username
            if (username != null && !username.isEmpty()) {
                String sql = "SELECT * FROM users WHERE username = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    model.addAttribute("usernameExistsError", true);
                    success = false;
                }
            }
            // Verify existing email
            if (email != null && !email.isEmpty()) {
                String sql = "SELECT * FROM users WHERE Email = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, email);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    model.addAttribute("emailExistsError", true);
                    success = false;
                }
            }
            if (success) {
                StringBuilder updateSql = new StringBuilder("UPDATE users SET ");
                boolean first = true;
                if (nume != null && !nume.isEmpty()) {
                    updateSql.append("Nume = ?");
                    first = false;
                }
                if (prenume != null && !prenume.isEmpty()) {
                    if (!first) updateSql.append(",");
                    updateSql.append("Prenume = ?");
                    first = false;
                }
                if (judet != null && !judet.isEmpty()) {
                    if (!first) updateSql.append(",");
                    updateSql.append("Judet = ?");
                    first = false;
                }
                if (oras != null && !oras.isEmpty()) {
                    if (!first) updateSql.append(",");
                    updateSql.append("Oras = ?");
                    first = false;
                }
                if (strada != null && !strada.isEmpty()) {
                    if (!first) updateSql.append(",");
                    updateSql.append("Strada = ?");
                    first = false;
                }
                if (numarStrada != null) {
                    if (!first) updateSql.append(",");
                    updateSql.append("NumarStrada = ?");
                    first = false;
                }
                if (bloc != null && !bloc.isEmpty()) {
                    if (!first) updateSql.append(",");
                    updateSql.append("Bloc = ?");
                    first = false;
                }
                if (scara != null && !scara.isEmpty()) {
                    if (!first) updateSql.append(",");
                    updateSql.append("Scara = ?");
                    first = false;
                }
                if (apartament != null) {
                    if (!first) updateSql.append(",");
                    updateSql.append("Apartament = ?");
                    first = false;
                }
                if (email != null && !email.isEmpty()) {
                    if (!first) updateSql.append(",");
                    updateSql.append("Email = ?");
                    first = false;
                }
                if (telefon != null && !telefon.isEmpty()) {
                    if (!first) updateSql.append(",");
                    updateSql.append("NumarTelefon = ?");
                    first = false;
                }
                if (username != null && !username.isEmpty()) {
                    if (!first) updateSql.append(",");
                    updateSql.append("username = ?");
                    first = false;
                }
                if (password != null && !password.isEmpty()) {
                    if (!first) updateSql.append(",");
                    updateSql.append("password = ?");
                }
                updateSql.append(" WHERE username = ?");
                stmt = conn.prepareStatement(updateSql.toString());
                int index = 1;
                if (nume != null && !nume.isEmpty()) stmt.setString(index++, nume);
                if (prenume != null && !prenume.isEmpty()) stmt.setString(index++, prenume);
                if (judet != null && !judet.isEmpty()) stmt.setString(index++, judet);
                if (oras != null && !oras.isEmpty()) stmt.setString(index++, oras);
                if (strada != null && !strada.isEmpty()) stmt.setString(index++, strada);
                if (numarStrada != null) stmt.setInt(index++, numarStrada);
                if (bloc != null && !bloc.isEmpty()) stmt.setString(index++, bloc);
                if (scara != null && !scara.isEmpty()) stmt.setString(index++, scara);
                if (apartament != null) stmt.setInt(index++, apartament);
                if (email != null && !email.isEmpty()) stmt.setString(index++, email);
                if (telefon != null && !telefon.isEmpty()) stmt.setString(index++, telefon);
                if (username != null && !username.isEmpty()) stmt.setString(index++, username);
                if (password != null && !password.isEmpty()) stmt.setString(index++, password);

                stmt.setString(index, currentUsername);
                if(!first) {
                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        model.addAttribute("succes", true);
                        List<Serviciu> servicii = Service.requestServicii();
                        model.addAttribute("servicii",servicii);
                        return true;
                    } else {
                        System.out.println("Nu exista contul in baza de date???");
                        model.addAttribute("succes", false);
                        return false;
                    }
                }
                else{
                    return false;
                }
            } else {
                model.addAttribute("succes", false);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateUserSession(Model model,HttpSession session, String username){
        try {
            // Connecting to the database using the URL, user, and password
            Connection conn = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);

            // SQL query to select users based on username
            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Setting the parameter value in the query
            stmt.setString(1, username);

            // Executing the query and getting the result
            ResultSet rs = stmt.executeQuery();

            // If there is a result in the database for the searched user
            if (rs.next()) {
                // Checking if the user is an administrator
                boolean isAdmin = rs.getString("IsAdmin").equals("DA");

                // Creating a User object based on data obtained from the database
                User user = new User(rs.getInt("UserId"), rs.getString("Nume"), rs.getString("Prenume"),
                        rs.getString("Sex"), rs.getString("DataNastere"), rs.getString("Judet"),
                        rs.getString("Oras"), rs.getString("Strada"), rs.getInt("NumarStrada"), rs.getString("Bloc"),
                        rs.getString("Scara"), rs.getInt("Apartament"), rs.getString("Email"),
                        rs.getString("NumarTelefon"), rs.getString("username"), rs.getString("password"),
                        isAdmin);

                // Setting the user object in the session
                session.setAttribute("user", user);

                // Adding the user object to the model to be accessible in the view
                model.addAttribute("user", user);

                // Closing the connection, statements, and result set
                conn.close();
                stmt.close();
                rs.close();
            }
        }
        catch (Exception e) {
            // Catching exceptions and displaying errors in the console
            e.printStackTrace();
        }

    }

    public static boolean insertServiciu(Model model, HttpSession session, String nume, String descriere, int pret){
        // Getting the User object from the session and adding it to the model for use in the view
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);

        try {
            // Connecting to the database using the URL, user, and password
            Connection conn = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);

            // Query to check if a service with the specified name already exists
            String sql = "Select * FROM servicii WHERE NumeServiciu = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nume);  // Setting the parameter value in the query
            ResultSet rs = stmt.executeQuery();

            // If a service with this name already exists, we add an error message to the model
            if (rs.next()) {
                model.addAttribute("ServiciuExistsError", true);
                return false; // If the service already exists, we return false.
            } else {
                // If the service does not exist, we proceed to insert a new service into the database
                sql = "INSERT INTO servicii (NumeServiciu, Descriere, PretPeOra) VALUES (?,?,?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, nume);
                stmt.setString(2, descriere);
                stmt.setInt(3, pret);

                // We execute the UPDATE query and get the number of rows affected
                int rowsAffected = stmt.executeUpdate();

                // We close the connection, the statements and the result set
                conn.close();
                stmt.close();

                // We get the updated list of services from the database
                List<Serviciu> servicii = Service.requestServicii();
                model.addAttribute("servicii", servicii);

                // If rows were modified, we add a success message to the model
                if (rowsAffected > 0) {
                    model.addAttribute("justInsertedService", true);
                    return true; // We return true if the insertion was successful.
                } else {
                    model.addAttribute("faraLiniiModificateServicii", true);
                    return false; // We return false if no rows have been modified.
                }
            }
        } catch (Exception e) {
            // Catching exceptions and displaying errors in the console
            e.printStackTrace();
            return false; // We return false in case of error
        }

    }

    public static Serviciu findServiciuByNumeServiciu(String numeServiciu){
        try {
            // Connecting to the database using the URL, user, and password
            Connection conn = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);

            // Preparing the query to select the service based on its name
            String sql = "SELECT * FROM servicii WHERE NumeServiciu = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, numeServiciu);  // Setting the parameter value in the query

            // Running the query to get the results
            ResultSet rs = stmt.executeQuery();

            // If a service with this name exists, we create a Serviciu object
            if (rs.next()) {
                Serviciu serviciu = new Serviciu(
                        rs.getInt("ServiciuId"),
                        rs.getString("NumeServiciu"),
                        rs.getString("Descriere"),
                        rs.getInt("PretPeOra"),
                        rs.getString("NumeImagine")
                );

                // We close the connection, the statement, and the result set to free up resources
                conn.close();
                stmt.close();
                rs.close();

                // We return the Service object
                return serviciu;
            }

            // If no service was found, we return null
            return null;
        } catch(Exception e) {
            // Catching exceptions and displaying errors in the console
            e.printStackTrace();
            return null;  // We return null in case of error
        }
    }

    public static boolean stergereServiciuByNumeServiciu(String numeServiciu){
        try {
            // Connecting to the database using the URL, user, and password
            Connection conn = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);

            // Preparing the query to delete a service based on its name
            String sql = "DELETE FROM servicii WHERE NumeServiciu = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, numeServiciu);  // Setting the parameter value in the query

            // Running the query to delete the service
            int rowsAffected = stmt.executeUpdate();  // Displays the number of rows affected

            // Closing the connection, statement, and resources to free memory
            conn.close();
            stmt.close();

            // Returns true if a service was deleted (i.e., rows were affected)
            return rowsAffected > 0;
        } catch (Exception e) {
            // Catching exceptions and displaying errors in the console
            e.printStackTrace();

            // Returns false if an error occurred.
            return false;
        }

    }

    public static boolean updateServiciu(String numeServiciuVechi, String numeServiciuModificat, String descriere, int pretPeOra, Model model){
        try {
            // Connecting to the database using the URL, user, and password
            Connection conn = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);

            // Preparing the query to get the service name based on its ID
            String sql = "SELECT NumeServiciu FROM servicii WHERE numeServiciu = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, numeServiciuVechi);  // Setting the service name parameter value
            ResultSet rs = stmt.executeQuery();

            String numeServiciuVechiCopy = numeServiciuVechi;
            numeServiciuVechi = numeServiciuVechi.toUpperCase(); // The variable that will store the old service name

            // Check if the changed name is different from the old one
            if (!numeServiciuModificat.toUpperCase().equals(numeServiciuVechi)) {
                // If the name has been changed, it is checked whether a service with this name already exists
                sql = "SELECT * FROM servicii WHERE NumeServiciu = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, numeServiciuModificat);  // Set the modified service name
                rs = stmt.executeQuery();

                if (rs.next()) {
                    model.addAttribute("numeServiciuExistaDeja", true);  // If the service already exists, an error message is added
                    return false;
                }
            }

            // If the service name is valid, the service information in the database is updated.
            sql = "UPDATE servicii SET NumeServiciu = ?, Descriere = ?, PretPeOra = ? WHERE NumeServiciu = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, numeServiciuModificat);  // Set the new service name
            stmt.setString(2, descriere);  // Set service description
            stmt.setInt(3, pretPeOra);  // Set hourly price
            stmt.setString(4, numeServiciuVechiCopy);  // Set the old service name

            // Run the query to update the service
            int rowsAffected = stmt.executeUpdate();

            // Close connection and resources
            conn.close();
            stmt.close();
            rs.close();

            // Returns true if the service was successfully updated
            return rowsAffected > 0;
        } catch (Exception e) {
            // Catching exceptions and displaying errors in the console
            e.printStackTrace();
            return false;  // Returns false if an error occurs.
        }

    }

    public static List<Angajat> requestAngajati(){
        // Creating a list to store employees
        List<Angajat> angajati = new ArrayList<>();

        try {
            // Connecting to the database using the URL, user, and password
            Connection conn = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);

            // Preparing the query to select all records from the 'Angajati' table
            String sql = "SELECT * FROM angajati";
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Executing the query and obtaining a ResultSet with the returned data
            ResultSet rs = stmt.executeQuery();

            // Looping through the query result and adding each employee to a list
            while (rs.next()) {
                angajati.add(new Angajat(
                        rs.getInt("AngajatId"),   // Get employee's ID
                        rs.getString("Nume"),     // Get employee's Last Name
                        rs.getString("Prenume"),  // Get employee's First Name
                        rs.getString("DataNastere"), // Get employee's date of birth
                        rs.getInt("Salariu"),     // Get employee's salary
                        rs.getString("NrTelefon") // Get employee's telephone number
                ));
            }

            // Closing the connection and resources
            conn.close();
            stmt.close();
            rs.close();

            // Returning the employee list
            return angajati;
        } catch (Exception e) {
            // Capturing errors and displaying them in the console
            e.printStackTrace();

            // Returning an empty list on error
            return new ArrayList<>();
        }
    }

    public static List<Serviciu> filtreazaServiciiDupaAngajatID(int angajatID){
        // Creating a list to store filtered services
        List<Serviciu> serviciiFiltrate = new ArrayList<>();

        try {
            // Connecting to the database using the URL, user, and password
            Connection conn = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);

            // Preparing the query to select services associated with a specific employee
            String sql = "SELECT * FROM servicii S JOIN serviciiprestatedeangajati SPA ON S.ServiciuId = SPA.ServiciuId WHERE SPA.AngajatId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            // Setting the value for the 'AngajatId' parameter in the query
            stmt.setInt(1, angajatID);

            // Executing the query and obtaining a ResultSet with the returned data
            ResultSet rs = stmt.executeQuery();

            // Going through the query result and adding each service to a list
            while (rs.next()) {
                serviciiFiltrate.add(new Serviciu(
                        rs.getInt("ServiciuId"),      // Get service's ID
                        rs.getString("NumeServiciu"), // Get service's name
                        rs.getString("Descriere"),    // Get service's description
                        rs.getInt("PretPeOra"),       // Get the hourly price of the service
                        rs.getString("NumeImagine")   // Get the name of the image associated with the service
                ));
            }

            // Closing the connection and resources
            conn.close();
            stmt.close();
            rs.close();

            // If the filtered service list is empty, returns null
            if (serviciiFiltrate.isEmpty()) {
                return null;
            } else {
                // Returns the filtered list of services
                return serviciiFiltrate;
            }
        } catch (Exception e) {
            // Capturing errors and displaying them in the console
            e.printStackTrace();

            // Returning null on error
            return null;
        }

    }

    public static List<Angajat> filtreazaAngajatiDupaServiciuID(int serviciuID){
        // Creating a list to store filtered employees
        List<Angajat> angajatiFiltrati = new ArrayList<>();

        try {
            // Connecting to the database using the URL, user, and password
            Connection conn = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);

            // Preparing the query to select employees who performed a specified service
            String sql = "SELECT * FROM angajati A JOIN serviciiprestatedeangajati SPA ON A.AngajatId = SPA.AngajatId WHERE SPA.ServiciuId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            // Setting the value for the 'ServiciuId' parameter in the query
            stmt.setInt(1, serviciuID);

            // Executing the query and obtaining a ResultSet with the returned data
            ResultSet rs = stmt.executeQuery();

            // Looping through the query result and adding each employee to a list
            while (rs.next()) {
                angajatiFiltrati.add(new Angajat(
                        rs.getInt("AngajatId"),        // Get employee's ID
                        rs.getString("Nume"),          // Get employee's last name
                        rs.getString("Prenume"),       // Get employee's first name
                        rs.getString("DataNastere"),   // Get employee's date of birth
                        rs.getInt("Salariu"),          // Get employee's salary
                        rs.getString("NrTelefon")      // Get employee's phone number
                ));
            }

            // Closing the connection and resources
            conn.close();
            stmt.close();
            rs.close();

            // If the filtered employee list is empty, return null
            if (angajatiFiltrati.isEmpty()) {
                return null;
            } else {
                // Returns the filtered list of employees
                return angajatiFiltrati;
            }
        } catch (Exception e) {
            // Capturing errors and displaying them in the console
            e.printStackTrace();

            // Returning null on error
            return null;
        }

    }

    public static String getNumeServiciuByServiciuID(int serviciuID){
        try {
            // Connecting to the database using the URL, user, and password
            Connection conn = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);

            // Preparing the query to select the service name based on the service ID
            String sql = "SELECT NumeServiciu FROM servicii WHERE ServiciuId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            // Setting the value for the 'ServiciuId' parameter in the query
            stmt.setInt(1, serviciuID);

            // Executing the query and obtaining a ResultSet with the returned data
            ResultSet rs = stmt.executeQuery();

            // If there is a result, return the service name
            if (rs.next()) {
                return rs.getString(1); // Get service name
            } else {
                // If the service with this ID does not exist, display an error message
                System.out.println("Nu exista serviciu cu acest ID");
                return null;
            }
        } catch (Exception e) {
            // Capturing errors and displaying them in the console
            e.printStackTrace();

            // Returning null on error
            return null;
        }

    }

    public static String getNumeAngajatByAngajatID(int angajatID){
        try {
            // Connecting to the database using the URL, user, and password
            Connection conn = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);

            // Preparing the query to select the employee's first and last name based on the employee ID
            String sql = "SELECT Nume,Prenume FROM angajati WHERE AngajatId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Setting the value for the 'AngajatId' parameter in the query
            stmt.setInt(1, angajatID);

            // Executing the query and obtaining a ResultSet with the returned data
            ResultSet rs = stmt.executeQuery();

            // If there is a result, return the concatenated employee's first and last name
            if(rs.next()){
                return rs.getString(1) + " " + rs.getString(2); // Combine first and last name
            }
            else {
                // If the employee with this ID does not exist, display an error message
                System.out.println("Nu exista angajat cu acest ID");
                return null;
            }
        } catch (Exception e) {
            // Capturing errors and displaying them in the console
            e.printStackTrace();

            // Returning null on error
            return null;
        }
    }

    public static boolean tryAndCreateProgramare(int serviciuID, int angajatID, String dataProgramare, int userID, Model model, HttpSession session){
        // Checks if the characters in the Scheduled date are valid digits between 0 and 9
        if(dataProgramare.charAt(0) < '0' || dataProgramare.charAt(0) > '9' || dataProgramare.charAt(1) <'0' || dataProgramare.charAt(1) > '9' ||
                dataProgramare.charAt(2) < '0' || dataProgramare.charAt(2) > '9' || dataProgramare.charAt(3) < '0' || dataProgramare.charAt(3) > '9' ||
                dataProgramare.charAt(5) < '0' || dataProgramare.charAt(5) > '9' || dataProgramare.charAt(6) < '0' || dataProgramare.charAt(6) > '9' ||
                dataProgramare.charAt(8) < '0' || dataProgramare.charAt(8) > '9' || dataProgramare.charAt(9) < '0' || dataProgramare.charAt(9) > '9' ||
                dataProgramare.charAt(11) < '0' || dataProgramare.charAt(11) > '9' || dataProgramare.charAt(12) < '0' || dataProgramare.charAt(12) > '9' ||
                dataProgramare.charAt(14) < '0' || dataProgramare.charAt(14) > '9' || dataProgramare.charAt(15) < '0' || dataProgramare.charAt(15) > '9') {
            // If there is an error in the date format, display a message and return false.
            System.out.println("Eroare de formatare a textului");
            return false;
        }

        // Check if the appointment time is between 08:00 and 20:00
        if (((dataProgramare.charAt(11) - '0') * 10 + (dataProgramare.charAt(12) - '0')) > 20 ||
                ((dataProgramare.charAt(11) - '0') * 10 + (dataProgramare.charAt(12) - '0')) < 8) {
            // If the time is too early or too late, set the model with an error attribute and return false
            model.addAttribute("preaTarziuSauPreaDevreme", true);
            User user = (User) session.getAttribute("user");
            model.addAttribute("user", user);
            model.addAttribute("serviciuSelectatID", -1);
            model.addAttribute("angajatulSelectatID", -1);
            List<Angajat> angajati = Service.requestAngajati();
            model.addAttribute("angajati", angajati);
            List<Serviciu> servicii = Service.requestServicii();
            model.addAttribute("servicii", servicii);
            List<Angajat> angajatiOcupati = Service.requestAngajatiOcupati();
            model.addAttribute("angajatiOcupati", angajatiOcupati);
            return false;
        } else {
            try {
                // Calculating days and minutes from dateProgramare to compare them
                int zileDataProgramare = ((dataProgramare.charAt(0) - '0') * 1000 + (dataProgramare.charAt(1) - '0') * 100 +
                        (dataProgramare.charAt(2) - '0') * 10 + (dataProgramare.charAt(3) - '0')) * 366 +
                        ((dataProgramare.charAt(5) - '0') * 10 + (dataProgramare.charAt(6) - '0')) * 31 +
                        (dataProgramare.charAt(8) - '0') * 10 + (dataProgramare.charAt(9) - '0');
                int minuteDataProgramare = ((dataProgramare.charAt(11) - '0') * 10 + (dataProgramare.charAt(12) - '0')) * 60 +
                        (dataProgramare.charAt(14) - '0') * 10 + (dataProgramare.charAt(15) - '0');

                // Connecting to the MySQL database
                Connection conn = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);

                // Search for existing employee appointments
                String sql = "SELECT P.DataProgramare FROM programari P JOIN serviciiprestatedeangajati SPA ON P.ServiciuPrestatDeAngajatId = SPA.ServiciuPrestatDeAngajatId"
                        + " WHERE SPA.AngajatId = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, angajatID);
                ResultSet rs = stmt.executeQuery();

                // Checks if the appointment date coincides with an existing appointment for the same employee
                while(rs.next()){
                    String dataProgramareBazaDeDate = rs.getString(1);
                    int zileDataProgramareBazaDeDate = ((dataProgramareBazaDeDate.charAt(0) - '0') * 1000 + (dataProgramareBazaDeDate.charAt(1) - '0') * 100 +
                            (dataProgramareBazaDeDate.charAt(2) - '0') * 10 + (dataProgramareBazaDeDate.charAt(3) -'0')) * 366 +
                            ((dataProgramareBazaDeDate.charAt(5) - '0') * 10 + (dataProgramareBazaDeDate.charAt(6) - '0')) * 31 +
                            (dataProgramareBazaDeDate.charAt(8) - '0') * 10 + (dataProgramareBazaDeDate.charAt(9)- '0');
                    if(zileDataProgramare == zileDataProgramareBazaDeDate){
                        int minuteDataProgramareBazaDeDate = ((dataProgramareBazaDeDate.charAt(11) - '0') * 10 + (dataProgramareBazaDeDate.charAt(12) - '0')) * 60 +
                                (dataProgramareBazaDeDate.charAt(14) - '0') * 10 + (dataProgramareBazaDeDate.charAt(15) - '0');
                        // If the time difference is less than 120 minutes, the employee is busy
                        if(Math.abs(minuteDataProgramare - minuteDataProgramareBazaDeDate) < 120){
                            model.addAttribute("angajatOcupat", true);
                            User user = (User) session.getAttribute("user");
                            model.addAttribute("user", user);
                            model.addAttribute("serviciulSelectatID", -1);
                            model.addAttribute("angajatulSelectatID", -1);
                            List<Angajat> angajati = Service.requestAngajati();
                            model.addAttribute("angajati", angajati);
                            List<Serviciu> servicii = Service.requestServicii();
                            model.addAttribute("servicii", servicii);
                            List<Angajat> angajatiOcupati = Service.requestAngajatiOcupati();
                            model.addAttribute("angajatiOcupati", angajatiOcupati);
                            conn.close();
                            stmt.close();
                            rs.close();
                            return false;
                        }
                    }
                }

                // Finding the employee's service ID
                sql = "SELECT ServiciuPrestatDeAngajatId FROM serviciiprestatedeangajati WHERE ServiciuId = ? AND AngajatId = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, serviciuID);
                stmt.setInt(2, angajatID);
                rs = stmt.executeQuery();
                int spaId;
                if(rs.next()){
                    spaId = rs.getInt(1);
                }
                else{
                    System.out.println("tryAndCreateProgram query pentru gasire spaId gresit");
                    conn.close();
                    stmt.close();
                    rs.close();
                    return false;
                }

                // Inserting a new schedule into the database
                sql = "INSERT INTO programari (ServiciuPrestatDeAngajatId, UserId, DataProgramare, StatusProgramare) VALUES (?, ?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, spaId);
                stmt.setInt(2, userID);
                stmt.setString(3, dataProgramare);
                stmt.setString(4, "In Asteptare");
                int rowsAffected = stmt.executeUpdate();

                conn.close();
                stmt.close();
                rs.close();

                // Check if the insertion was successful
                if(rowsAffected > 0){
                    model.addAttribute("programareSucces", true);
                    User user = (User) session.getAttribute("user");
                    model.addAttribute("user", user);
                    List<Serviciu> servicii = Service.requestServicii();
                    model.addAttribute("servicii", servicii);
                    return true;
                }
                else {
                    System.out.println("Query inserare programare gresit");
                    conn.close();
                    stmt.close();
                    rs.close();
                    return false;
                }
            }
            catch(Exception e){
                e.printStackTrace();
                return false;
            }
        }

    }

    public static boolean isValidDataProgramare(String dataProgramare){
        // Get the current date and time
        LocalDateTime dataCurenta = LocalDateTime.now();

        // Defining the date and time format to match the input format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Format dataProgramare using the format defined above.
        LocalDateTime dataProgramareFormatata = LocalDateTime.parse(dataProgramare, formatter);

        // Check if the appointment date is not before the current date and time
        return !dataProgramareFormatata.isBefore(dataCurenta);

    }

    public static List<Angajat> requestAngajatiOcupati() {
        // Create a list of busy employees
        List<Angajat> angajatiOcupati = new ArrayList<>();

        try {
            // Open a database connection
            Connection conn = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);

            // Create the SQL query to select employees who have "In lucru" appointments
            String sql = "SELECT * FROM Angajati A WHERE EXISTS (SELECT P.ProgramareID FROM Programari P JOIN ServiciiPrestateDeAngajati SP ON "+
                    "P.ServiciuPrestatDeAngajatID = SP.ServiciuPrestatDeAngajatID WHERE A.AngajatID = SP.AngajatID AND P.StatusProgramare = 'In lucru')";

            // Prepare and execute the SQL query
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // Add employees found in the list of angajatiOcupati
            while(rs.next()){
                angajatiOcupati.add(new Angajat(rs.getInt("AngajatId"),
                        rs.getString("Nume"), rs.getString("Prenume"),
                        rs.getString("DataNastere"),rs.getInt("Salariu"),
                        rs.getString("NrTelefon")));
            }

            // Closes the connection, statement, and result set
            conn.close();
            stmt.close();
            rs.close();

            // Checks if the list is empty and returns the corresponding result
            if(angajatiOcupati.isEmpty()){
                return null;
            }
            else {
                return angajatiOcupati;
            }
        } catch (Exception e) {
            // Capture and display any exceptions that occur during execution
            e.printStackTrace();
            return null;
        }

    }

    public static List<Factura> requestFacturiByUsername(String username){
        // Create a list of invoices
        List<Factura> facturi = new ArrayList<>();

        try {
            // Open a database connection
            Connection conn = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);

            // Create the SQL query to select invoices corresponding to a specific user
            String sql = "SELECT * FROM facturi F JOIN users U ON F.UserID = U.UserID WHERE U.username = ?";

            // Prepare the SQL statement with the username parameter
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            // Run the query and get the results
            ResultSet rs = stmt.executeQuery();

            // Add found invoices to the invoice list
            while (rs.next()) {
                facturi.add(new Factura(rs.getInt("FacturaId"), rs.getInt("UserId"), rs.getString("DataFactura"), rs.getString("TipPlata")));
            }

            // Closes the connection, statement, and result set
            conn.close();
            stmt.close();
            rs.close();

            // Checks if the invoice list is empty and returns the corresponding result
            if (facturi.isEmpty()) {
                return null;
            } else {
                return facturi;
            }
        } catch (Exception e) {
            // Capture and display any exceptions that occur during execution
            e.printStackTrace();
            return null;
        }

    }

    public static List<Programare> requestProgramariWithDetailsByUsername(String username){
        // Create a schedule list
        List<Programare> programari = new ArrayList<>();

        try {
            // Open a database connection
            Connection conn = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);

            // Create SQL query to select appointments for a specific user
            String sql = "SELECT P.ProgramareId, P.ServiciuPrestatDeAngajatId, P.DataProgramare, P.StatusProgramare, S.NumeServiciu, A.Nume, A.Prenume, U.UserId, " +
                    "A.NrTelefon FROM Programari P JOIN Users U ON U.UserID = P.UserID " +
                    "JOIN ServiciiPrestateDeAngajati SP ON P.ServiciuPrestatDeAngajatID=SP.ServiciuPrestatDeAngajatID JOIN " +
                    "Servicii S ON SP.ServiciuID = S.ServiciuID JOIN " +
                    "Angajati A ON SP.AngajatID = A.AngajatID " +
                    "WHERE U.Username = ?" +
                    "ORDER BY P.DataProgramare DESC";

            // Prepare the SQL statement with the username parameter
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            // Run the query and get the results
            ResultSet rs = stmt.executeQuery();

            // Add found appointments to the appointment list
            while (rs.next()) {
                programari.add(new Programare(rs.getInt("ProgramareId"), rs.getInt("ServiciuPrestatDeAngajatId"), rs.getInt("UserId"),
                        rs.getString("DataProgramare"), rs.getString("StatusProgramare"), rs.getString("NumeServiciu"),
                        rs.getString("Nume"), rs.getString("Prenume"), rs.getString("NrTelefon")));
            }

            // Closes the connection, statement, and result set
            conn.close();
            stmt.close();
            rs.close();

            // Checks if the schedule list is empty and returns the corresponding result
            if (programari.isEmpty()) {
                return null;
            } else {
                return programari;
            }
        } catch (Exception e) {
            // Capture and display any exceptions that occur during execution
            e.printStackTrace();
            return null;
        }

    }

    public static Programare requestProgramareByProgramareId(int programareId){
        try {
            // Open a database connection
            Connection conn = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);

            // Create the SQL query to select a schedule by its ID
            String sql = "SELECT U.UserId, P.ServiciuPrestatDeAngajatId, P.DataProgramare, P.StatusProgramare, S.NumeServiciu, A.Nume, A.Prenume, " +
                    "A.NrTelefon FROM Programari P JOIN Users U ON U.UserID = P.UserID " +
                    "JOIN ServiciiPrestateDeAngajati SP ON P.ServiciuPrestatDeAngajatID=SP.ServiciuPrestatDeAngajatID JOIN " +
                    "Servicii S ON SP.ServiciuID = S.ServiciuID JOIN " +
                    "Angajati A ON SP.AngajatID = A.AngajatID " +
                    "WHERE P.ProgramareId = ?";

            // Prepare the SQL statement with the programId parameter
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, programareId);

            // Run the query and get the results
            ResultSet rs = stmt.executeQuery();

            // If a schedule is found, create the Schedule object with the obtained data
            if (rs.next()) {
                Programare programare = new Programare(programareId, rs.getInt("ServiciuPrestatDeAngajatId"), rs.getInt("UserId"),
                        rs.getString("DataProgramare"), rs.getString("StatusProgramare"), rs.getString("NumeServiciu"),
                        rs.getString("Nume"), rs.getString("Prenume"), rs.getString("NrTelefon"));

                // Closes the connection, statement, and result set
                conn.close();
                stmt.close();
                rs.close();

                // Return the found schedule
                return programare;
            } else {
                // If the schedule is not found, close the connection and display a message
                conn.close();
                stmt.close();
                rs.close();
                System.out.println("requestProgramareByProgramareId - nu s-a gasit programare dupa programareId");

                return null;
            }
        } catch (Exception e) {
            // Capture and display any exceptions that occur during execution
            e.printStackTrace();
            return null;
        }

    }

    public static boolean actiuneProgramareByProgramareId(int programareId, String actiune){
        try {
            // DOpen a database connection
            Connection conn = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);

            // Create SQL query to update appointment status by ID
            String sql = "UPDATE Programari SET StatusProgramare = ? WHERE ProgramareId = ?";

            // Prepare the SQL statement with the necessary parameters
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, actiune);  // Set the new status (actiune) of the appointment
            stmt.setInt(2, programareId); // Sets the appointment ID to be updated

            // Executes the update in the database and gets the number of rows affected
            int rowsAffected = stmt.executeUpdate();

            // Close the connection and statement
            conn.close();
            stmt.close();

            // Checks if the query affected at least one row
            if (rowsAffected > 0) {
                return true;  // If yes, return true
            } else {
                // If the schedule is not found, display a message and return false.
                System.out.println("actiuneProgramareByProgramareId - nu s-a gasit programare dupa ProgramareId");
                return false;
            }
        } catch (Exception e) {
            // Capture and display any exceptions that occur during execution
            e.printStackTrace();
            return false;
        }

    }

    public static boolean stergereProgramareByProgramareId(int programareId){
        try {
            // Open a database connection
            Connection conn = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);

            // Create SQL query to delete schedule by ID
            String sql = "DELETE FROM Programari WHERE ProgramareId = ?";

            // Prepare the SQL statement with the necessary parameters
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, programareId); // Sets the ID of the appointment to be deleted.

            // Executes the update in the database and gets the number of rows affected
            int rowsAffected = stmt.executeUpdate();

            // Close the connection and statement
            conn.close();
            stmt.close();

            // Checks if the query affected at least one row
            if (rowsAffected > 0) {
                return true;  // If yes, return true
            } else {
                // If the appointment is not found, display a message and return false.
                System.out.println("actiuneProgramareByProgramareId - nu s-a gasit programare dupa ProgramareId");
                return false;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean adaugaFeedback(int programareId, String experienta, String descriere, int nota){
        // If the grade is 0, we change it to 10 because the grade begins with 1
        if (nota == 0) {
            nota = 10;
        }

        try {
            // Open a database connection
            Connection conn = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);

            // Create the SQL query to insert the feedback into the 'feedback' table
            String sql = "INSERT INTO feedback (ProgramareId, Experienta, Descriere, Nota) VALUES (?,?,?,?)";

            // Prepare the SQL statement with the necessary parameters
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, programareId);  // Set the appointment ID for feedback
            stmt.setString(2, experienta); // Set experience description
            stmt.setString(3, descriere); // Set detailed feedback description
            stmt.setInt(4, nota);         // Set the grade awarded

            // Execute the insert into the database and get the number of rows affected
            int rowsAffected = stmt.executeUpdate();

            // Close the connection and statement
            conn.close();
            stmt.close();

            // Checks if the query affected at least one row
            if (rowsAffected > 0) {
                return true;  // If yes, return true
            } else {
                // If no insertion was made, returns false.
                return false;
            }
        } catch (Exception e) {
            // Capture and display any exceptions that occur during execution
            e.printStackTrace();
            return false;
        }

    }

    public static List<Feedback> requestFeedbacksByServiciuId(int serviciuID){
        // Create a list to store feedback
        List<Feedback> feedbacks = new ArrayList<>();
        try{
            // Open a database connection with the required details
            Connection conn = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);

            // SQL query to select feedbacks based on serviciuID
            String sql = "SELECT P.ProgramareId, F.Experienta, F.Descriere, F.Nota " +
                    "FROM FeedBack F " +
                    "JOIN Programari P ON F.ProgramareId = P.ProgramareId " +
                    "JOIN ServiciiPrestateDeAngajati SP ON SP.ServiciuPrestatDeAngajatID = P.ServiciuPrestatDeAngajatID " +
                    "JOIN Servicii S ON S.ServiciuID = SP.ServiciuID " +
                    "WHERE S.ServiciuId = ?";

            // Prepare the SQL statement
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Sets the serviciuID parameter in the query
            stmt.setInt(1, serviciuID);

            // Execute the query and get the results in a ResultSet
            ResultSet rs = stmt.executeQuery();

            // Iterate through each result and add the feedback to the list
            while (rs.next()) {
                feedbacks.add(new Feedback(
                        rs.getInt("ProgramareId"),
                        rs.getString("Experienta"),
                        rs.getString("Descriere"),
                        rs.getInt("Nota")
                ));
            }

            // Close connection and resources
            conn.close();
            stmt.close();
            rs.close();

            // If the feedback list is empty, return null, otherwise return the list
            if(feedbacks.isEmpty()){
                return null;
            }
            else {
                return feedbacks;
            }
        }
        catch (Exception e){
            // Catch exceptions and display them
            e.printStackTrace();
            return null;
        }

    }

    public static List<Programare> filtreazaProgramariDupaData(String data1, String data2, String username){
        // Create a list to store appointments
        List<Programare> programari = new ArrayList<>();

        // Formats input data to convert it to LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate data1Formatata = LocalDate.parse(data1, formatter);
        LocalDate data2Formatata = LocalDate.parse(data2, formatter);

        // If data1 is after data2, reverse them
        if(data1Formatata.isAfter(data2Formatata)){
            String aux = data1;
            data1 = data2;
            data2 = aux;
        }

        try{
            // Open a database connection with the required details
            Connection conn = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);

            // SQL query to select appointments between two dates for a specific user
            String sql = "SELECT P.ProgramareId, SP.ServiciuPrestatDeAngajatId, P.DataProgramare, P.StatusProgramare, S.NumeServiciu, A.Nume, A.Prenume, A.NrTelefon, U.UserId " +
                    "FROM Programari P " +
                    "JOIN Users U ON U.UserID = P.UserID " +
                    "JOIN ServiciiPrestateDeAngajati SP ON P.ServiciuPrestatDeAngajatID=SP.ServiciuPrestatDeAngajatID " +
                    "JOIN Servicii S ON SP.ServiciuID = S.ServiciuID " +
                    "JOIN Angajati A ON SP.AngajatID = A.AngajatID " +
                    "WHERE P.DataProgramare BETWEEN ? AND ? AND U.username = ?" +
                    "ORDER BY P.DataProgramare DESC";

            // Prepare the SQL statement
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Set the parameters for the query: date range and userId
            stmt.setString(1, data1);
            stmt.setString(2, data2);
            stmt.setString(3, username);

            // Execute the query and get the results in a ResultSet
            ResultSet rs = stmt.executeQuery();

            // Iterate through each result and add the appointments to the list
            while(rs.next()){
                programari.add(new Programare(
                        rs.getInt("ProgramareId"),
                        rs.getInt("ServiciuPrestatDeAngajatId"),
                        rs.getInt("UserId"),
                        rs.getString("DataProgramare"),
                        rs.getString("StatusProgramare"),
                        rs.getString("NumeServiciu"),
                        rs.getString("Nume"),
                        rs.getString("Prenume"),
                        rs.getString("NrTelefon")
                ));
            }

            // If there are appointments, return the list; otherwise, return null
            if(!programari.isEmpty()){
                return programari;
            } else {
                return null;
            }
        }
        catch (Exception e){
            // Catch exceptions and display them
            e.printStackTrace();
            return null;
        }

    }
}
