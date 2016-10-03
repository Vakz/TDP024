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
  tableBody.empty();

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

function getPrevParameters() {
  var params = {};
  var parameters = ["date", "tag", "limit", "lvlsort", "dtsort"];
  parameters.forEach(function(param) {
    var p = $.urlParam(param);
    if (p) params[param] = p;
  });
  return params;
}

function getEntries(parameters) {
  $.getJSON("/entries", parameters, success);
}

function getFormParameters() {
  var params = {};
  params["dtsort"] = $("#dateAgeFilter").val();
  if (dtsort) {

  }

  params["date"] = $("#dateInput").val();
  params["level"] = $("#levelInput").val();
  params["lvlsort"] = $("#lvlsort").val();
  console.log(params);
}

$(document).ready(function() {
  getEntries(getPrevParameters());
  $("#filterForm").submit(function(e) {
    e.preventDefault();
    getEntries(getFormParameters());
    return false;
  });
  $("#dateInput").datetimepicker({format: 'Y-m-d h:m:s', lang: 'sv'});
});
