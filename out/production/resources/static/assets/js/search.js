// function validate(data) {
//     var blank_pattern = /^\s+|\s+$/g;
//     if (data.replace(blank_pattern, '') == "") {
//         return true;
//     } else if (!data) {
//         return true;
//     } else {
//         return false;
//     }
// }
//
// var result;
// var query;
// $(document).ready(function () {
//     $.ajax({
//         url: "http://localhost:8081/search",
//         type: 'get',
//         success: function (data) {
//             result = data
//         },
//         error: function (err) {
//         }
//     });
//     $("#query").keyup(function () {
//         let click = document.getElementById("drop-content");
//         click.style.display = "block";
//
//         query = document.getElementById("query").value
//         if (validate(query)) {
//             return;
//         }
//         var newResult = result.filter(function (elem) {
//                 return elem.code.includes(query) || elem.name.includes(query) || elem.description.includes(query) || elem.category.includes(query) || elem.candidate.name.includes(query);
//             },
//         )
//         var elem = document.getElementById("searchResult")
//         elem.innerHTML = ""
//         var i=0;
//         var stockUrl='http://localhost:8081/candidate/stock/'
//
//         for(var r of newResult){
//             elem.innerHTML+= "<a href="+stockUrl+r.code+">"  +r.name+ "</a>";
//             i+=1;
//             if (i>5){break};}
//     });
// });
//
// function dp_menu() {
//     let click = document.getElementById("drop-content");
//     click.style.display = "block";
// }
// $(function(){
//     $(document).mousedown(function( e ){
//         if( $("#drop-content").is(":visible") ) {
//             $("#drop-content").each(function(){
//                 var l_position = $(this).offset();
//                 l_position.right = parseInt(l_position.left) + ($(this).width());
//                 l_position.bottom = parseInt(l_position.top) + parseInt($(this).height());
//                 if( ( l_position.left <= e.pageX && e.pageX <= l_position.right )
//                     && ( l_position.top <= e.pageY && e.pageY <= l_position.bottom ) ) {
//                 } else {
//                     $(this).hide();
//                 }
//             });
//         }
//     });
// });