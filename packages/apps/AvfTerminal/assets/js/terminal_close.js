// Terminal close handler - placeholder
(function() {
    if (window.term) {
        try { window.term.close(); } catch(e) {}
    }
})();
