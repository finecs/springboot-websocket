$("#textarea").keydown(function(event) {
    if (event.ctrlKey && event.keyCode == 13) {
        alert('Ctrl+Enter');
    };
})