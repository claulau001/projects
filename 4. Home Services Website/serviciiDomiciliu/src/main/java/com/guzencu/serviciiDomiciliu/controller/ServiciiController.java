/** Clasa pentru Controller
 * @author Guzencu Claudiu-Florentin
 * @version 3 ianuarie 2025
 */
package com.guzencu.serviciiDomiciliu.controller;

import com.guzencu.serviciiDomiciliu.model.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Controller
public class ServiciiController {
    @GetMapping("/")
    public String index(HttpSession session, Model model) {

        // We get the user from the session and add it to the model if it exists
        User user = (User) session.getAttribute("user");
        if(user != null){
            model.addAttribute("user",user);
        }

        // We obtain services from the database
        List<Serviciu> servicii = Service.requestServicii();
        model.addAttribute("servicii",servicii);
        return "index";
    }

    @GetMapping("/index.html")
    public String redirectToRoot() {
        return "redirect:/";
    }

    @GetMapping("/programeaza")
    public String redirectToProgrameaza() {
        return "redirect:/programeaza.html";
    }

    @GetMapping("/programeaza.html")
    public String programeaza(
            Model model,
            HttpSession session
    ){
        // We get the user from the session and add it to the model if it exists, if not we send to the login page
        User user = (User) session.getAttribute("user");
        if (user == null){
            model.addAttribute("accountNeed", true);
            return "login";
        }
        model.addAttribute("user",user);

        // We deselect the service and the selected employee
        model.addAttribute("serviciulSelectatID", -1);
        model.addAttribute("angajatulSelectatID", -1);

        // We get the list of services
        List<Serviciu> servicii = Service.requestServicii();
        model.addAttribute("servicii",servicii);

        // We get the list of employees
        List<Angajat> angajati = Service.requestAngajati();
        model.addAttribute("angajati",angajati);

        // We obtain the list of employees who have an appointment with the status "In lucru"
        List<Angajat> angajatiOcupati = Service.requestAngajatiOcupati();
        model.addAttribute("angajatiOcupati",angajatiOcupati);
        return "programeaza";
    }

    @GetMapping("/login.html")
    public String login(){
        return "login";
    }

    @GetMapping("/register.html")
    public String register(){
        return "register";
    }

    @GetMapping("/profile.html")
    public String profile(){
        return "redirect:/profile";
    }

    @GetMapping("/profile")
    public String profilee(Model model, HttpSession session) {
        // We get the user from the session and add it to the model if it exists
        User user = (User) session.getAttribute("user");
        if(user != null){
            model.addAttribute("user",user);
        }
        return "profile";
    }

    @PostMapping("/login")
    public String tryLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session,
            Model model
    ) {
        // We check if the username and password are correct and exist in the database to log in.
        boolean valid = Service.tryLogin(model, session, username, password);
        if(valid){
            return "index";
        }
        else{
            return "login";
        }
    }

    @PostMapping("/register")
    public String register(
            @RequestParam("numeClient") String nume,
            @RequestParam("prenumeClient") String prenume,
            @RequestParam("sexOptionsClient") int sexValue,
            @RequestParam("dataNastereClient") String dataNastere,
            @RequestParam("judetClient") String judet,
            @RequestParam("orasClient") String oras,
            @RequestParam(name = "stradaClient", required = false) String strada,
            @RequestParam(name = "numarStradaClient", required = false) Integer numarStrada,
            @RequestParam(name = "blocClient", required = false) String bloc,
            @RequestParam(name = "scaraClient", required = false) String scara,
            @RequestParam(name = "apartamentClient", required = false) Integer apartament,
            @RequestParam("emailClient") String email,
            @RequestParam("telefonClient") String telefon,
            @RequestParam("usernameClient") String username,
            @RequestParam("parolaClient") String password,
            @RequestParam("parolaConfirmataClient") String confirmedPassword,
            Model model
    ){
        // We check the registry parameters
        boolean validData = Service.verifyUserParameters(model,nume,prenume,dataNastere,judet,oras,strada,bloc,scara,email,telefon,username,password,confirmedPassword);

        // In case of failure, we send the model information.
        if(!validData){
            //System.out.println("Erori: " + model.asMap());
            model.addAttribute("succes",false);
            return "register";
        }

        // If the data meets the requirements
        if(Service.createUser(model,nume,prenume,sexValue,dataNastere,judet,oras,strada,numarStrada,bloc,scara,apartament,email,telefon,username,password)){
            return "login";
        }
        else{
            return "register";
        }
    }

    @GetMapping("/logoutQuestion")
    public String logoutQuestion(Model model, HttpSession session){
        // We display a confirmation message for disconnection
        model.addAttribute("logoutQuestionMark",true);
        // We add the user from the session to the model
        model.addAttribute("user",session.getAttribute("user"));

        // We request the list of services
        List<Serviciu> servicii = Service.requestServicii();
        model.addAttribute("servicii",servicii);
        return "index";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session, Model model){
        // We delete the user from the session and add the services for the first page
        session.invalidate();
        List<Serviciu> servicii = Service.requestServicii();
        model.addAttribute("servicii",servicii);
        return "index";
    }

    @PostMapping("/cancelLogout")
    public String cancelLogout(Model model,HttpSession session){
        // We add the services and the user from the session to the model
        model.addAttribute("user", session.getAttribute("user"));
        List<Serviciu> servicii = Service.requestServicii();
        model.addAttribute("servicii",servicii);
        return "index";
    }

    @PostMapping("/updateUser")
    public String updateUser(Model model, HttpSession session,
                 @RequestParam(name="numeUserEdit", required = false) String nume,
                 @RequestParam(name="prenumeUserEdit", required = false) String prenume,
                 @RequestParam(name="judetUserEdit",required = false) String judet,
                 @RequestParam(name="orasUserEdit",required = false) String oras,
                 @RequestParam(name = "stradaUserEdit", required = false) String strada,
                 @RequestParam(name = "numarStradaUserEdit", required = false) Integer numarStrada,
                 @RequestParam(name = "blocUserEdit", required = false) String bloc,
                 @RequestParam(name = "scaraUserEdit", required = false) String scara,
                 @RequestParam(name = "apartamentUserEdit", required = false) Integer apartament,
                 @RequestParam(name="emailUserEdit",required = false) String email,
                 @RequestParam(name="telefonUserEdit",required = false) String telefon,
                 @RequestParam(name="usernameUserEdit",required = false) String username,
                 @RequestParam(name="parolaNouaUserEdit",required = false) String password,
                 @RequestParam(name="parolaNouaConfirmataUserEdit",required = false) String confirmedPassword){

        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);

        // We verify the validity of the modified profile data
        boolean validData = Service.verifyUserParameters(model,nume,prenume,"2003-04-17 00:00:00",judet,oras,strada,bloc,scara,email,telefon,username,password,confirmedPassword);

        // Adding disability information to the model
        if(!validData){
            model.addAttribute("succes", false);
            return "profile";
        }

        // If everything is successfully modified in the database, we also modify the session.
        if(Service.updateUserProfile(model,session,nume,prenume,judet,oras,strada,numarStrada,bloc,scara,apartament,email,telefon,username,password,user.getUsername())){
            Service.updateUserSession(model,session,user.getUsername());
            return "index";
        }
        else{
            return "profile";
        }
    }

    @GetMapping("/admin")
    public String admin(Model model, HttpSession session){

        // If the user exists in the session and is an admin, then we will display the admin page with all the necessary information.
        User user = (User) session.getAttribute("user");
        if(user != null && user.isAdmin()){
            List<Serviciu> servicii = Service.requestServicii();
            model.addAttribute("servicii",servicii);
            model.addAttribute("user",user);
            return "admin";
        }
        return "redirect:/";
    }

    @GetMapping("/adaugaServiciu")
    public String getAdaugaServiciu(Model model, HttpSession session){

        // Displaying the add service page
        User user = (User) session.getAttribute("user");
        if(user != null && user.isAdmin()){
            model.addAttribute("user",user);
            return "adaugaServiciu";
        }
        return "redirect:/";
    }

    @PostMapping("/insertServiciu")
    public String insertServiciu(
            @RequestParam("NumeServiciu") String nume,
            @RequestParam("DescriereServiciu") String descriere,
            @RequestParam("PretPeOra") int pret,
            Model model,
            HttpSession session
    ){
        // If the service is added successfully, it goes to the main page, if not, it remains on the same page where the errors 
        // are displayed.
        if(Service.insertServiciu(model,session,nume,descriere,pret)) {
            return "index";
        }
        else {
            return "adaugaServiciu";
        }
    }

    @PostMapping("/intreabaStergereServiciu")
    public String intreabaStergereServiciu(
            Model model,
            HttpSession session,
            @RequestParam("nume_serviciu") String numeServiciu
    ){
        // Displaying the confirmation message for deleting the service
        model.addAttribute("stergereServiciuQuestionMark", true);
        Serviciu serviciulSelectat = Service.findServiciuByNumeServiciu(numeServiciu);
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        model.addAttribute("serviciulSelectat",serviciulSelectat);
        List<Serviciu> servicii = Service.requestServicii();
        model.addAttribute("servicii",servicii);
        return "admin";
    }

    @PostMapping("/stergereServiciu")
    public String stergereServiciu(
            Model model,
            HttpSession session,
            @RequestParam("nume_serviciu") String numeServiciu
    ){
        // Check if the service was deleted successfully
        if(Service.stergereServiciuByNumeServiciu(numeServiciu)){
            model.addAttribute("succesStergereServiciu",true);
        }
        else{
            model.addAttribute("succesStergereServiciu", false);
        }
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        List<Serviciu> servicii = Service.requestServicii();
        model.addAttribute("servicii",servicii);
        return "admin";
    }

    @PostMapping("/editServiciu")
    public String editServiciu(
            Model model,
            HttpSession session,
            @RequestParam("nume_serviciu") String numeServiciu
    ){
        // Displaying the page for editing a service
        Serviciu serviciu = Service.findServiciuByNumeServiciu(numeServiciu);
        model.addAttribute("serviciulSelectat",serviciu);
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        return "editeazaServiciu";
    }

    @PostMapping("/updateServiciu")
    public String updateServiciu(
            Model model,
            HttpSession session,
            @RequestParam("nume_serviciu_vechi") String numeServiciuVechi,
            @RequestParam("NumeServiciu") String numeServiciu,
            @RequestParam("DescriereServiciu") String descriere,
            @RequestParam("PretPeOra") int pretPeOra
    ){
        // If a service has been successfully modified, the admin page is displayed with a success message.
        if(Service.updateServiciu(numeServiciuVechi,numeServiciu,descriere,pretPeOra,model)){
            model.addAttribute("succesUpdateServiciu", true);
            User user = (User) session.getAttribute("user");
            model.addAttribute("user",user);
            List<Serviciu> servicii = Service.requestServicii();
            model.addAttribute("servicii",servicii);
            return "admin";
        }
        // Otherwise, the page refreshes with an error message.
        else{
            model.addAttribute("succesUpdateServiciu", false);
            Serviciu serviciu = Service.findServiciuByNumeServiciu(numeServiciuVechi);
            model.addAttribute("serviciulSelectat",serviciu);
            User user = (User) session.getAttribute("user");
            model.addAttribute("user",user);
            return "editeazaServiciu";
        }
    }

    @GetMapping("/filtreaza-servicii")
    public String filtreazaServicii(
            Model model,
            HttpSession session,
            @RequestParam("angajatID") int angajatID,
            @RequestParam("serviciulSelectatID") int serviciulSelectatID
    ){
        // Filtering services after an employee has been selected
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        model.addAttribute("serviciulSelectatID", serviciulSelectatID);
        List<Serviciu> serviciiFiltrate = Service.filtreazaServiciiDupaAngajatID(angajatID);
        if(serviciiFiltrate == null){
            System.out.println("Nu exista servicii prestate de angajatul cu id-ul " + angajatID);
            return "redirect:/programeaza";
        }
        else {
            model.addAttribute("servicii", serviciiFiltrate);
        }
        List<Angajat> angajati = Service.requestAngajati();
        model.addAttribute("angajati",angajati);
        model.addAttribute("angajatulSelectatID",angajatID);
        List<Angajat> angajatiOcupati = Service.requestAngajatiOcupati();
        model.addAttribute("angajatiOcupati",angajatiOcupati);
        return "programeaza";
    }

    @GetMapping("/filtreaza-angajati")
    public String filtreazaAngajati(
            Model model,
            HttpSession session,
            @RequestParam("serviciuID") int serviciuID,
            @RequestParam("angajatulSelectatID") int angajatulSelectatID
    ){
         // Get the user object stored in the session
        User user = (User) session.getAttribute("user");

        // Check if the user is authenticated
        if(user == null){
            // Sets an attribute in the model to indicate the need for authentication
            model.addAttribute("accountNeed", true);
            // Redirects the user to the login page
            return "login";
        }

        // Adds the user object to the model for use in the view
        model.addAttribute("user", user);

        // Check if a specific employee is selected
        if(angajatulSelectatID != -1){
            // Sets the selected employee ID in the model
            model.addAttribute("angajatulSelectatID", angajatulSelectatID);
        } else {
            // Sets a default value (-1) if no employee is selected
            model.addAttribute("angajatulSelectatID", -1);
        }

        // Filter the employee list by serviciuID
        List<Angajat> angajatiFiltrati = Service.filtreazaAngajatiDupaServiciuID(serviciuID);

        // Check if there are employees for the selected service
        if(angajatiFiltrati == null){
            // Display a message in the console if there are no employees available
            System.out.println("Nu exista angajati pentru serviciul cu id-ul " + serviciuID);
            // Redirects the user back to the programming page
            return "redirect:/programeaza";
        } else {
            // Add the filtered employee list to the model
            model.addAttribute("angajati", angajatiFiltrati);
        }

        // Get the list of available services
        List<Serviciu> servicii = Service.requestServicii();
        // Add the list of services to the model
        model.addAttribute("servicii", servicii);

        // Sets the ID of the selected service in the model
        model.addAttribute("serviciulSelectatID", serviciuID);

        // Get the list of employees who are busy
        List<Angajat> angajatiOcupati = Service.requestAngajatiOcupati();
        // Add the list of busy employees to the model
        model.addAttribute("angajatiOcupati", angajatiOcupati);

        // Get the list of feedback for the selected service
        List<Feedback> feedbacks = Service.requestFeedbacksByServiciuId(serviciuID);
        // Add the feedback list to the model
        model.addAttribute("feedbacks", feedbacks);

        return "programeaza";

    }

    @PostMapping("/intreabaProgramare")
    public String intreabaProgramare(
            Model model,
            HttpSession session,
            @RequestParam("id_serviciu") int serviciuID,
            @RequestParam("id_angajat") int angajatID,
            @RequestParam("data-programare") String dataProgramare
    ){
        // Replaces the character 'T' in the appointment date with a space (format for display)
        dataProgramare = dataProgramare.replace('T', ' ');

        // Get the user object stored in the session
        User user = (User) session.getAttribute("user");
        // Add the user to the model for use in the view
        model.addAttribute("user", user);

        // Get the full list of employees
        List<Angajat> angajati = Service.requestAngajati();
        // Add the employee list to the model
        model.addAttribute("angajati", angajati);

        // Get the list of available services
        List<Serviciu> servicii = Service.requestServicii();
        // Add the list of services to the model
        model.addAttribute("servicii", servicii);

        // Get the list of busy employees
        List<Angajat> angajatiOcupati = Service.requestAngajatiOcupati();
        // Add the list of busy employees to the model
        model.addAttribute("angajatiOcupati", angajatiOcupati);

        // Check if the appointment date is valid (not in the past)
        if(Service.isValidDataProgramare(dataProgramare)) {
            // Add appointment details to the confirmation template
            model.addAttribute("numeServiciuIntrebare", Service.getNumeServiciuByServiciuID(serviciuID));
            model.addAttribute("numeAngajatIntrebare", Service.getNumeAngajatByAngajatID(angajatID));
            model.addAttribute("dataProgramareIntrebare", dataProgramare);
            // Indicates that the question to confirm the appointment will be displayed
            model.addAttribute("intreabaProgramare", true);
            // Add selected service and employee IDs
            model.addAttribute("serviciuID", serviciuID);
            model.addAttribute("angajatID", angajatID);
            // Return to the create an apointment page for confirmation
            return "programeaza";
        } else {
            // If the appointment date is invalid, reset the selections
            model.addAttribute("angajatulSelectatID", -1);
            model.addAttribute("serviciulSelectatID", -1);
            // Marks that the appointment date is in the past
            model.addAttribute("dataProgramareInTrecut", true);
            // Returns programming page with error message
            return "programeaza";
        }

    }

    @PostMapping("/confirmProgramare")
    public String confirmProgramare(
        Model model,
        HttpSession session,
        @RequestParam("id_serviciu") int serviciuID,
        @RequestParam("id_angajat") int angajatID,
        @RequestParam("data_programare") String dataProgramare
    ){
        // Try and create a new schedule
        User user = (User) session.getAttribute("user");
        if(Service.tryAndCreateProgramare(serviciuID,angajatID,dataProgramare,user.getUserId(),model,session)){
            return "index";
        }
        else{
            return "programeaza";
        }
    }

    @GetMapping("/afisareFacturi")
    public String afisareFacturi(
        Model model,
        HttpSession session
    ){
        // Get the user object stored in the session
        User user = (User) session.getAttribute("user");
        // Add the user to the model for use in the view
        model.addAttribute("user", user);

        // Get the list of invoices associated with the current user, using their ID
        List<Factura> facturi = Service.requestFacturiByUsername(user.getUsername());
        model.addAttribute("facturi", facturi);

        return "facturi";

    }

    @GetMapping("/afisareProgramari")
    public String afisareProgramari(
            Model model,
            HttpSession session
    ){
        // Get the user object stored in the session
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);

        // Get the list of appointments associated with the current user, with additional details, using their ID
        List<Programare> programari = Service.requestProgramariWithDetailsByUsername(user.getUsername());
        model.addAttribute("programari", programari);

        return "programari";

    }

    @PostMapping("/intreaba-actiune-programare")
    public String intreabaActiuneProgramare(
            Model model,
            HttpSession session,
            @RequestParam("programareSelectataId") int programareId,
            @RequestParam("valoareActiune") int valoareActiune
    ){
        // Get the user object stored in the session
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);

        // Get the list of appointments associated with the current user, with additional details
        List<Programare> programari = Service.requestProgramariWithDetailsByUsername(user.getUsername());
        model.addAttribute("programari", programari);

        // Get the selected schedule based on schedule ID
        Programare programareSelectata = Service.requestProgramareByProgramareId(programareId);
        model.addAttribute("programareSelectata", programareSelectata);

        // Indicates that the user will be asked to confirm an action on the appointment.
        model.addAttribute("intreabaActiuneProgramare", true);

        // Determines the action the user wants to perform on the selected schedule
        switch(valoareActiune){
            case 1:
                // If the user wants to cancel the appointment
                model.addAttribute("actiune", "anulezi");
                model.addAttribute("intreabaAnuleazaProgramare", true);
                break;
            case 2:
                // If the user wants to complete the programming
                model.addAttribute("actiune", "finalizezi");
                model.addAttribute("intreabaFinalizeazaProgramare", true);
                break;
            case 3:
                // If the user wants to request more time for the appointment
                model.addAttribute("actiune", "ceri timp pentru");
                model.addAttribute("intreabaCereTimpProgramare", true);
                break;
            case 4:
                // If the user wants to delete an appointment
                model.addAttribute("actiune", "stergi");
                model.addAttribute("intreabaStergereProgramare", true);
                break;
            default:
                // Default case for unexpected values
                System.out.println("intreabaActiuneProgramare - switch default");
        }

        return "programari";

    }

    @PostMapping("/anuleaza-programare")
    public String anuleazaProgramare(
            Model model,
            HttpSession session,
            @RequestParam("programareSelectataId") int programareId
    ){
        // Checks whether the action to cancel the schedule with the specified ID was successful.
        if (Service.actiuneProgramareByProgramareId(programareId, "Esuata")) {
            // If the cancellation was successful, add an attribute to the model to inform the user
            model.addAttribute("programareAnulataSucces", true);
        } else {
            // If the cancellation failed, add an attribute to the model to inform the user
            model.addAttribute("programareAnulataSucces", false);
        }

        // Get the user object stored in the session
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);

        // Get the list of appointments associated with the current user, with additional details
        List<Programare> programari = Service.requestProgramariWithDetailsByUsername(user.getUsername());
        model.addAttribute("programari", programari);

        return "programari";

    }

    @PostMapping("/finalizeaza-programare")
    public String finalizeazaProgramare(
            Model model,
            HttpSession session,
            @RequestParam("programareSelectataId") int programareId
    ){
        // Attempts to complete the appointment identified by programareId, setting the status to "Completed"
        if (Service.actiuneProgramareByProgramareId(programareId, "Finalizata")) {
            // If the completion was successful, add an attribute to inform the user
            model.addAttribute("finalizareProgramareSucces", true);

            // Get details about completed programming
            Programare programareSelectata = Service.requestProgramareByProgramareId(programareId);
            model.addAttribute("programareSelectata", programareSelectata);
        } else {
            // If completion failed, add an attribute to inform the user
            model.addAttribute("finalizareProgramareSucces", false);
        }

        // Get the current user from the session
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);

        // Get the list of appointments associated with the current user, including the necessary details
        List<Programare> programari = Service.requestProgramariWithDetailsByUsername(user.getUsername());
        model.addAttribute("programari", programari);

        return "programari";

    }

    @PostMapping("/cere-timp-programare")
    public String cereTimpProgramare(
            Model model,
            HttpSession session,
            @RequestParam("programareSelectataId") int programareId
    ){
        // Attempts to set the status of the appointment identified by programareId to "In Lucru"
        if (Service.actiuneProgramareByProgramareId(programareId, "In lucru")) {
            // If the action was successful, add an attribute to inform the user
            model.addAttribute("programareCereTimpSucces", true);
        } else {
            // If the action failed, add an attribute to inform the user
            model.addAttribute("programareCereTimpSucces", false);
        }

        // Get the current user from the session
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);

        // Get the list of appointments associated with the current user, including the necessary details
        List<Programare> programari = Service.requestProgramariWithDetailsByUsername(user.getUsername());
        model.addAttribute("programari", programari);

        return "programari";

    }

    @PostMapping("/sterge-programare")
    public String stergeProgramare(
            Model model,
            HttpSession session,
            @RequestParam("programareSelectataId") int programareId
    ){
        if(Service.stergereProgramareByProgramareId(programareId)) {
            model.addAttribute("programareStergereSucces", true);
        }
        else {
            model.addAttribute("programareStergereSucces", false);
        }

        // Get the current user from the session
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);

        // Get the list of appointments associated with the current user, including the necessary details
        List<Programare> programari = Service.requestProgramariWithDetailsByUsername(user.getUsername());
        model.addAttribute("programari", programari);

        return "programari";
    }

    @PostMapping("/get-insert-feedback")
    public String getInsertFeedback(
            Model model,
            HttpSession session,
            @RequestParam("programareSelectataId") int programareId
    ){
        // Retrieves the current user from the session
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);

        // Get the details of the selected appointment using programareId
        Programare programareSelectata = Service.requestProgramareByProgramareId(programareId);
        model.addAttribute("programareSelectata", programareSelectata);

        return "adaugaFeedBack";

    }

    @PostMapping("/insertFeedback")
    public String insertFeedback(
            Model model,
            HttpSession session,
            @RequestParam("programareSelectataId") int programareId,
            @RequestParam("experientaFeedback") String experienta,
            @RequestParam("descriereFeedback") String descriere,
            @RequestParam("notaFeedback") int nota
    ){
        // Attempts to add feedback for the appointment identified by programareId
        if (Service.adaugaFeedback(programareId, experienta, descriere, nota)) {
            // If the feedback was added successfully, sets a success attribute to use in the view
            model.addAttribute("feedbackAdaugatSucces", true);

            // Retrieves the current user from the session
            User user = (User) session.getAttribute("user");
            model.addAttribute("user", user);

            // Get the list of services in the system
            List<Serviciu> servicii = Service.requestServicii();
            model.addAttribute("servicii", servicii);

            return "index";
        } else {
            // If the feedback was not added successfully, set a failure attribute to use in the view
            model.addAttribute("feedbackAdaugatSucces", false);

            // Retrieves the current user from the session
            User user = (User) session.getAttribute("user");
            model.addAttribute("user", user);

            // Get the details of the appointment for which feedback was attempted
            Programare programareSelectata = Service.requestProgramareByProgramareId(programareId);
            model.addAttribute("programareSelectata", programareSelectata);

            return "adaugaFeedBack";
        }

    }

    @PostMapping("/filtreaza-programari-dupa-data")
    public String filtreazaProgramariDupaData(
        Model model,
        HttpSession session,
        @RequestParam("data1") String data1,
        @RequestParam("data2") String data2
    ){
        // Retrieves the current user from the session
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);

        // Filters user appointments based on the provided date range (data1 and data2)
        List<Programare> programari = Service.filtreazaProgramariDupaData(data1, data2, user.getUsername());
        model.addAttribute("programari", programari);

        return "programari";

    }
}
