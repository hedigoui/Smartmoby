const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

if (localStorage.getItem('formState') === 'signUp') {
    container.classList.add("right-panel-active");
} else {
    container.classList.remove("right-panel-active");
}

signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
    localStorage.setItem('formState', 'signUp'); // Sauvegarde l'état du formulaire
});

signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
    localStorage.setItem('formState', 'signIn'); // Sauvegarde l'état du formulaire
});

function handleRoleChange() {
    const roleSelect = document.getElementById('utilisateur_role'); // Utilisez l'ID correct
    const extraInputContainer = document.getElementById('extra-input-container');
    const extraInput = document.getElementById('extra-input');

    if (!roleSelect || !extraInputContainer || !extraInput) {
        console.error('Role select or extra input elements not found');
        return;
    }

    const selectedRole = roleSelect.value;

    if (selectedRole === 'conducteur' || selectedRole === 'organisateur' || selectedRole === 'admin') {
        extraInputContainer.style.display = 'block';
        extraInput.placeholder = selectedRole === 'conducteur'
            ? 'Numéro de permis de conduire'
            : selectedRole === 'organisateur'
                ? 'Nom de l’organisation'
                : 'Département';
    } else {
        extraInputContainer.style.display = 'none';
    }
}

document.addEventListener('DOMContentLoaded', () => {
    const roleSelect = document.getElementById('utilisateur_role'); // Utilisez l'ID correct
    if (roleSelect) {
        roleSelect.addEventListener('change', handleRoleChange);
    } else {
        console.error('Role select element not found');
    }
});