// Terminal disconnect callback - placeholder
(function() {
    if (window.term && window.term.onDisconnect) {
        window.term.onDisconnect(function() {
            if (window.TerminalApp) {
                window.TerminalApp.closeTab();
            }
        });
    }
})();
