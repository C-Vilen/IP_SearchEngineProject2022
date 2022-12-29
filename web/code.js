/*Variable for the input field and button*/
var input = document.getElementById('searchbox');
var button = document.getElementById('searchbutton');

/*Auto select input text field*/
input.focus();
input.select();

/*Execution of java program*/
button.onclick = () => {
    fetch("/search?q=" + document.getElementById('searchbox').value)
    .then((response) => response.json())
    .then((data) => {
        if (data.length == 0) {
            document.getElementById("responsesize").innerHTML = "<p>No web page contains the query word.</p>"; 
            document.getElementById("urllist").innerHTML = ``;
        } else {
            document.getElementById("responsesize").innerHTML = "<p>" + data.length + " websites retrieved</p>";
            let results = data.map((page) =>`<li><a href="${page.url}">${page.title}</a> - ${page.matches} word matches</li>`).join("\n");
            document.getElementById("urllist").innerHTML = `<ul>${results}</ul>`;
        } 
    });
};

/*Event listener on enter key*/
document.addEventListener("keypress", function onEvent(event) {
    if (event.key == "Enter" ) {
        event.preventDefault();
        button.click();
    }
});