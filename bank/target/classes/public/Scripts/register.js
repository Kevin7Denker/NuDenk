document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
    const password = document.getElementById("password");
    const confirmPassword = document.getElementById("confirm-password");

    form.addEventListener("submit", function (event) {
        let valid = true;

        if (password.value !== confirmPassword.value) {
            event.preventDefault();
            showModal("Passwords do not match.");
            valid = false;
        }

        const inputs = document.querySelectorAll("input[required]");
        inputs.forEach((input) => {
            if (!input.value) {
                valid = false;
                showModal(
                    `Please fill out the ${input.previousElementSibling.textContent}`
                );
            } else if (!input.checkValidity()) {
                valid = false;
                showModal(
                    `Invalid ${input.previousElementSibling.textContent}`
                );
            }
        });

        if (!valid) {
            event.preventDefault();
        }
    });

    const regexPatterns = {
        nome: /^[a-zA-Z\s]+$/,
        sobrenome: /^[a-zA-Z\s]+$/,
        email: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
        cpf: /^\d{3}\.\d{3}\.\d{3}-\d{2}$/,
    };

    form.addEventListener("input", function (event) {
        const target = event.target;
        const pattern = regexPatterns[target.name];
        if (pattern && !pattern.test(target.value)) {
            target.setCustomValidity("Invalid field.");
            showModal(`Invalid ${target.previousElementSibling.textContent}`);
        } else {
            target.setCustomValidity("");
        }
    });
});

function showModal(message) {
    const modal = document.getElementById("modal");
    const modalMessage = document.getElementById("modal-message");
    modalMessage.textContent = message;
    modal.classList.remove("hidden");
    setTimeout(closeModal, 3000);
}

function closeModal() {
    const modal = document.getElementById("modal");
    modal.classList.add("hidden");
}
