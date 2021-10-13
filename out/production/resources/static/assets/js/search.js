function validate(data){
    var blank_pattern = /^\s+|\s+$/g;
    if( data.replace( blank_pattern, '' ) == "" ){
        return true;
    }
    else if(!data){
        return true;
    }
    else {
        return false;
    }
}
var result;
var query;
$(document).ready(function () {
    $.ajax({
        url: "http://localhost:8081/search",
        type: 'get',
        success: function (data) {
            result = data

        },
        error: function (err) {
        }
    });
    $("#query").keyup(function() {
        query=document.getElementById("query").value
        if(validate(query)){
            return;
        }


        var newResult= result.filter(function (elem){
                return elem.code.includes(query)||elem.name.includes(query)||elem.description.includes(query)||elem.category.includes(query)||elem.candidate.name.includes(query);
            },
        )


    });
});