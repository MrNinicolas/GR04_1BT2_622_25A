package controllers;

import java.io.IOException;
import java.io.Serial;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.entities.Pet;
import model.jpa.PetJPA;

@WebServlet("/ManagePetsController")
public class ManagePetsController extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.router(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.router(req, resp);
    }

    private void router(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Control logic
        String route = (req.getParameter("route") == null) ? "list" : req.getParameter("route");

        switch (route) {
            case "list":
                this.viewPets(req, resp);
                break;
            case "add":
                this.addPet(req, resp);
                break;
            case "saveNew":
                this.saveNewPet(req, resp);
                break;
            case "edit":
                this.editPet(req, resp);
                break;
            case "saveExisting":
                this.saveExistingPet(req, resp);
                break;
            case "delete":
                this.deletePet(req, resp);
                break;
            case "accept":
                this.accept(req, resp);
                break;
            default:
                throw new IllegalArgumentException("Unknown route: " + route);
        }
    }

    private void accept(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int idPet = Integer.parseInt(req.getParameter("idPet"));
        PetJPA petJPA = new PetJPA();
        if (petJPA.remove(idPet)) {
            req.setAttribute("messageType", "info");
            req.setAttribute("message", "Pet deleted successfully.");
            req.getRequestDispatcher("ManagePetsController?route=list").forward(req, resp);
        } else {
            req.setAttribute("messageType", "error");
            req.setAttribute("message", "Failed to delete pet.");
            req.getRequestDispatcher("ManagePetsController?route=list").forward(req, resp);
        }
    }

    private void addPet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Pet> pets = new PetJPA().getAllPets();
        req.setAttribute("pets", pets);
        req.setAttribute("route", "add");
        req.getRequestDispatcher("jsp/PET.jsp").forward(req, resp);
    }

    private void deletePet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int idPet = Integer.parseInt(req.getParameter("idPet"));
        HttpSession session = req.getSession();
        PetJPA petJPA = new PetJPA();
        Pet pet = petJPA.findPetById(idPet);
        List<Pet> pets = petJPA.getAllPets();

        if (pet != null) {
            req.setAttribute("pet", pet);
            req.setAttribute("pets", pets);
            req.getRequestDispatcher("jsp/PET.jsp").forward(req, resp);
        } else {
            session.setAttribute("message", "Pet could not be found");
            resp.sendRedirect("ManagePetsController?route=list");
        }
    }

    private void editPet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int idPet = Integer.parseInt(req.getParameter("idPet"));
        PetJPA petJPA = new PetJPA();
        Pet pet = petJPA.findPetById(idPet);

        List<Pet> pets = petJPA.getAllPets();
        req.setAttribute("pets", pets);

        if (pet != null) {
            req.setAttribute("pet", pet);
            req.getRequestDispatcher("jsp/PET.jsp").forward(req, resp);
        } else {
            session.setAttribute("message", "Pet not found");
            resp.sendRedirect("ManagePetsController?route=list");
        }
    }

    private void saveExistingPet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Pet pet = parsePetFromRequest(req);
        PetJPA petJPA = new PetJPA();

        if (petJPA.update(pet)) {
            req.setAttribute("messageType", "info");
            req.setAttribute("message", "Pet updated successfully.");
            req.getRequestDispatcher("ManagePetsController?route=list").forward(req, resp);
            //resp.sendRedirect("ManagePetsController?route=list");
        } else {
            req.setAttribute("messageType", "error");
            req.setAttribute("message", "Failed to update pet.");
            req.getRequestDispatcher("ManagePetsController?route=list").forward(req, resp);
        }
    }

    private void saveNewPet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Pet pet = parsePetFromRequest(req);
        PetJPA petJPA = new PetJPA();
        if (petJPA.create(pet)) {
            req.setAttribute("messageType", "info");
            req.setAttribute("message", "Pet created successfully.");
            req.getRequestDispatcher("ManagePetsController?route=list").forward(req, resp);
        } else {
            req.setAttribute("messageType", "error");
            req.setAttribute("message", "Failed to create pet.");
            req.getRequestDispatcher("ManagePetsController?route=list").forward(req, resp);
        }
    }

    private void viewPets(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Pet> pets;
        pets = new PetJPA().getAllPets();
        req.setAttribute("pets", pets);
        req.getRequestDispatcher("jsp/PET.jsp").forward(req, resp);
    }

    private Pet parsePetFromRequest(HttpServletRequest req) {
        int idPet = 0;
        String txtId = req.getParameter("txtIdPet");

        if (txtId != null && !txtId.trim().isEmpty()) {
            try {
                idPet = Integer.parseInt(txtId);
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir el ID: " + e.getMessage());
            }
        }

        String name = req.getParameter("txtName");
        String species = req.getParameter("txtSpecies");
        String breed = req.getParameter("txtBreed");
        String color = req.getParameter("txtColor");
        String ownerName = req.getParameter("txtOwnerName");
        return new Pet(idPet, name, species, breed, color, ownerName);
    }
}
