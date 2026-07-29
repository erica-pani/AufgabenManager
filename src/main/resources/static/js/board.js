document.addEventListener('DOMContentLoaded', () => {
    // Registriere Event-Listener für alle Spalten-Header
    initColumnToggle();
});

function initColumnToggle() {
    const columns = document.querySelectorAll('.column');

    columns.forEach(column => {
        const header = column.querySelector('.column-header');
        
        if (!header) return;

        header.addEventListener('click', () => {
            // Umschalten der 'expanded' Klasse bei Klick
            column.classList.toggle('expanded');
        });
    });
}