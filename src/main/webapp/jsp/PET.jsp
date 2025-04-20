<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PETS</title>
    <!-- Bootstrap CSS -->
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
            rel="stylesheet">
    <!-- Font Awesome for icons -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>

<body>
<!-- Main Content -->
<main class="container my-4">
    <!-- Feedback Message -->
    <c:if test="${not empty sessionScope.message}">
        <div id="notification" class="alert alert-danger"
             style="background-color: #f8d7da; color: #721c24;" role="alert">
                ${sessionScope.message}</div>
        <c:remove var="message" scope="session"/>
    </c:if>


    <div class="row">
        <!-- PET Section -->
        <div class="col-md-8">
        <div class="mb-4" ><h4><a class="dropdown-item"
                   href="${pageContext.request.contextPath}/LoginController?route=logOut"><i
                        class="fas fa-sign-out-alt"></i> Logout</a></h4>
                        </div>
            <div class="d-flex justify-content-between align-items-center mb-3">

                <h1 class="mb-0">
                    Pets
                </h1>

                <a href="ManagePetsController?route=add"
                   class="btn btn-primary"> <i class="fas fa-plus-circle"></i>
                    Add Pet
                </a>

            </div>
            <div class="row">
                <c:forEach var="pet" items="${pets}">
                    <div class="col-md-6 mb-3">
                        <div class="card border-0 rounded-3">
                            <div class="card-body p-4">
                                <h2 class="card-title text-dark fw-bold">${pet.name}</h2>
                                <p class="card-text text-secondary small mb-4">
                                    <i class="fa-solid fa-question me-2"></i>${pet.species}
                                </p>
                                <p class="card-text text-secondary small mb-4">
                                    <i class="fa-solid fa-paw me-2"></i>${pet.breed}
                                </p>
                                <p class="card-text text-secondary small mb-4">
                                    <i class="fa-solid fa-rainbow me-2"></i>${pet.color}
                                </p>
                                <p class="card-text text-secondary small mb-4">
                                    <i class="fa-solid fa-person me-2"></i>${pet.ownerName}
                                </p>

                                <div class="d-flex justify-content-end">
                                    <!-- Botón de editar -->
                                    <a
                                            href="ManagePetsController?route=edit&idPet=${pet.idPet}"
                                            class="btn btn-primary btn-sm me-2 px-3"> <i
                                            class="fas fa-pen"></i> Edit
                                    </a>
                                    <!-- Botón de eliminar -->
                                    <a
                                            href="ManagePetsController?route=delete&idPet=${pet.idPet}"
                                            class="btn btn-danger btn-sm px-3"> <i
                                            class="fas fa-trash-alt"></i> Delete
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>

                </c:forEach>
            </div>
            <c:if test="${empty pets}">
                <div class="alert alert-warning text-center">You have not created any pet.</div>
            </c:if>
        </div>
    </div>
</main>

<!-- NEW PET FORM -->
<div class="modal fade" id="NEW_PET_FORM" tabindex="-1"
     aria-labelledby="NEW_PET_FORMLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title" id="NEW_PET_FORMLabel">
                    <i class="fa-solid fa-location-crosshairs"></i> Add Pet
                </h5>
                <a href="ManagePetsController?route=list" class="btn-close"
                   aria-label="Close"></a>
            </div>
            <form action="ManagePetsController?route=saveNew"
                  method="POST">
                <div class="modal-body">
                    <!-- Pet Form Fields -->
                    <div class="row g-4">
                        <div class="col-md-6">
                            <label for="NameAddPet" class="form-label fw-semibold">Name</label>
                            <input id="NameAddPet" type="text"
                                   class="form-control rounded-3" name="txtName"
                                   placeholder="Enter name" required>
                        </div>
                        <div class="col-md-6">
                            <label for="AddSpecies" class="form-label fw-semibold">Species
                                </label> <input id="AddSpecies" type="text"
                                                          class="form-control rounded-3" name="txtSpecies"
                                                          placeholder="Enter species">
                        </div>
                    </div>
                    <div class="row g-4 mt-2">
                        <div class="col-md-6">
                            <label for="AddBreed" class="form-label fw-semibold">Breed</label>
                            <input id="AddBreed" type="text"
                                   class="form-control rounded-3" name="txtBreed"
                                   placeholder="Enter breed" required>
                        </div>
                        <div class="col-md-6">
                            <label for="AddColor" class="form-label fw-semibold">Color</label>
                            <input id="AddColor" type="text"
                                   class="form-control rounded-3" name="txtColor"
                                   placeholder="Enter color" required>
                        </div>
                    </div>
                    <div class="row g-4 mt-2">
                        <div class="col-md-6">
                            <label for="AddOwnerName" class="form-label fw-semibold">Owner Name
                            </label> <input id="AddOwnerName" type="text"
                                            class="form-control rounded-3" name="txtOwnerName"
                                            placeholder="Enter owner name" required>
                        </div>
                    </div>
                </div>

                <div class="modal-footer justify-content-center">
                    <a href="ManagePetsController?route=list"
                       class="btn btn-danger"> Cancel </a>
                    <button type="submit" class="btn btn-primary">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Modal for Edit Pet -->
<div class="modal fade" id="EDIT_PET_FORM" tabindex="-1"
     aria-labelledby="EDIT_PET_FORMLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title" id="EDIT_PET_FORMLabel">
                    <i class="fas fa-edit"></i> Edit Pet
                </h5>
                <a href="ManagePetsController?route=list" class="btn-close"
                   aria-label="Close"></a>
            </div>
            <form action="ManagePetsController?route=saveExisting"
                  method="POST">
                <div class="modal-body">
                    <!-- Form fields with pre-filled values from the retrieved pet -->
                    <div class="row">
                        <input type="hidden" name="txtIdPet" value="${pet.idPet}">
                        <div class="col-md-6 mb-3">
                            <label for="NameEditPet" class="form-label fw-bold">Name</label>
                            <input id="NameEditPet" type="text" class="form-control"
                                   name="txtName" value="${pet.name}" placeholder="Enter name"
                                   required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="EditSpecies" class="form-label fw-bold">Species
                                </label> <input id="EditSpecies" type="text"
                                                          class="form-control" name="txtSpecies"
                                                          value="${pet.species}" placeholder="Enter company">
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="EditBreed" class="form-label fw-bold">Breed</label>
                            <input id="EditBreed" type="text" class="form-control"
                                   name="txtBreed" value="${pet.breed}"
                                   placeholder="Enter province" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="EditColor" class="form-label fw-bold">Color</label>
                            <input id="EditColor" type="text" class="form-control"
                                   name="txtColor" value="${pet.color}" placeholder="Enter city"
                                   required>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="EditOwnerName" class="form-label fw-bold">
                                Owner Name</label> <input id="EditOwnerName" type="text"
                                                          class="form-control" name="txtOwnerName"
                                                          value="${pet.ownerName}" placeholder="Enter main street"
                                                          required>
                        </div>

                    </div>

                </div>
                <div class="modal-footer justify-content-center">
                    <a href="ManagePetsController?route=list"
                       class="btn btn-danger"> Cancel </a>
                    <button type="submit" class="btn btn-primary">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Delete Pet Modal -->
<div class="modal fade" id="DELETE_PET" tabindex="-1"
     aria-labelledby="DELETE_PETLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header"
                 style="background-color: #dc3545; color: #fff;">
                <h5 class="modal-title" id="DELETE_PETLabel">
                    <i class="fa-solid fa-trash me-2"></i> Delete Pet
                </h5>
                <a href="ManagePetsController?route=list" class="btn-close"
                   aria-label="Close"></a>
            </div>
            <div class="modal-body text-center">
                <p>Are you sure you want to delete the pet?</p>
                <h3>${pet.name}</h3>
               <p class="card-text text-secondary small mb-4">
                                    <i class="fa-solid fa-question me-2"></i>${pet.species}
                                </p>
                                <p class="card-text text-secondary small mb-4">
						<i class="fa-solid fa-paw me-2"></i>${pet.breed}
					</p>
                                <p class="card-text text-secondary small mb-4">
                                    <i class="fa-solid fa-rainbow me-2"></i>${pet.color}
                                </p>
                                <p class="card-text text-secondary small mb-4">
                                    <i class="fa-solid fa-person me-2"></i>${pet.ownerName}
                                </p>
            </div>
            <div class="modal-footer justify-content-center">
                <!-- Cancel button, closes the modal -->
                <a href="ManagePetsController?route=list"
                   class="btn btn-danger">Cancel</a>

                <!-- Form to confirm the deletion -->
                <form
                        action="ManagePetsController?route=accept&idPet=${pet.idPet}"
                        method="POST">
                    <button type="submit" class="btn btn-success">Accept</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Modal para mensajes informativos y de error -->
<div class="modal modal-info" id="infoModal" tabindex="-1"
     aria-labelledby="infoModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body ${messageType == 'info' ? 'info' : 'error'}">
                <i
                        class="fas ${messageType == 'info' ? 'fa-info-circle text-info' : 'fa-exclamation-circle text-danger'}"></i>
                <span>${message}</span>
            </div>
        </div>
    </div>
</div>
<!-- Bootstrap JS -->
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    window.onload = function () {
        var route = "${param.route}";

        if (route === "add") {
            var myModal = new bootstrap.Modal(document
                .getElementById('NEW_PET_FORM'), {
                keyboard: false,
                backdrop: 'static'
            });
            document.body.classList.remove('modal-open');
            myModal.show();
        } else if (route === "edit" && "${param.idPet}") {
            var myModal = new bootstrap.Modal(document
                .getElementById('EDIT_PET_FORM'), {
                keyboard: false,
                backdrop: 'static'
            });
            document.body.classList.remove('modal-open');
            myModal.show();
        } else if (route === "delete" && "${param.idPet}") {
            var myModal = new bootstrap.Modal(document
                .getElementById('DELETE_PET'), {
                keyboard: false,
                backdrop: 'static'
            });
            document.body.classList.remove('modal-open');
            myModal.show();
        }
        // Mostrar modal informativo si hay mensaje
        const message = "${message}";
        if (message !== "") {
            const infoModalElement = document.getElementById("infoModal");
            if (infoModalElement) {
                const infoModal = new bootstrap.Modal(infoModalElement, {
                    backdrop: false, // Sin fondo oscuro
                    keyboard: false  // Desactiva cerrar con teclado
                });
                infoModal.show();

                // Cerrar automáticamente después de 1 segundo
                setTimeout(() => {
                    infoModal.hide();
                }, 1000);
            }
        }
    };

    document.addEventListener("DOMContentLoaded", function () {
        const notification = document.getElementById("notification");
        if (notification) {
            // Oculta el mensaje después de 2 segundos
            setTimeout(() => {
                notification.style.transition = "opacity 0.5s";
                notification.style.opacity = "0";
                setTimeout(() => notification.remove(), 1000); // Remueve el elemento después de la transición
            }, 2000);
        }
    });
</script>
</body>
</html>
