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
  });
}

function getPrevParameters() {
  var params = {};
  var parameters = ["date", "tag", "limit", "dtsort"];
  parameters.forEach(function(param) {
    var p = $.urlParam(param);
    if (p) params[param] = p;
  });
  return params;
}

function updateHistory(parameters) {
  if (Object.keys(parameters).length === 0) return;
  console.log(parameters);
  var url = "?";
  params = [];
  $.each(parameters, function(k,v) {
    params.push(k + "=" + v);
  });
  url += params.join("&");
  console.log(url);
  window.history.pushState(parameters, "Filtering", url);
}

function getEntries(parameters, ispop) {
  $.getJSON("/entries", parameters, success);
  if (!ispop) updateHistory(parameters);
}

function getFormParameters() {
  var params = {};
  params.dtsort = $("#dateAgeFilter").val();
  params.date = $("#dateInput").val();
  params.level = $("#levelInput").val();
  params.lvlsort = $("#lvlsort").val();
  params.tag = $("#tagInput").val();
  return params;
}

$(document).ready(function() {
  window.onpopstate = function(event) {
    getEntries(event.state, true);
  };

  getEntries(getPrevParameters());
  $("#filterForm").submit(function(e) {
    console.log("here");
    e.preventDefault();
    getEntries(getFormParameters());
    return false;
  });
  $("#dateInput").datetimepicker({format: 'Y-m-d h:m:s', lang: 'sv'});
});
