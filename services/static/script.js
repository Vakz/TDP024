// Pretty much straight up stolen from https://www.sitepoint.com/url-parameters-jquery/
$.urlParam = function(name){
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results===null){
       return null;
    }
    else{
       return results[1] || 0;
    }
};

function success(json) {
  var tableBody = $("#tableBody");

  json.forEach(function(entry) {
    var row = $("<tr />", {});
    tableBody.append(row);
    var date = $("<td />", {
      class: "date entry",
      text: entry.date
    });

    var level = $("<td />", {
      class: "level entry",
      text: entry.level
    });

    var tag = $("<td />", {
      class: "tag entry",
      text: entry.tag
    });

    var message = $("<td />", {
      class: "message entry",
      text: entry.message
    });


    row.append(date);
    row.append(level);

    row.append(tag);
    row.append(message);


    console.log(entry);
  });
}

function getParameters() {
  var params = {};
  var parameters = ["date", "tag", "limit", "lvlsort", "dtsort"];
  parameters.forEach(function(param) {
    var p = $.urlParam(param);
    if (p) params[param] = p;
  });
  return params;
}

$(document).ready(function() {
  $.getJSON("/entries", getParameters(), success);
  $("#dateInput").datetimepicker({format: 'Y-m-d h:m:s', lang: 'sv'});
});
