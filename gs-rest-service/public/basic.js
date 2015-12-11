$(document).ready(function() {
    $.ajax({
        url: "http://localhost:9000/greeting-javaconfig"
    }).then(function(data, status, jqxhr) {
       $('.greeting-id_b').append(data.id);
       $('.greeting-content_b').append(data.content);
       console.log(jqxhr);
    });
});
