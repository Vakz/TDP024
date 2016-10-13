var express = require('express');
var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database('database.db');

var app = express();

app.get("/log", function(req, res) {
  var tag = req.query.tag;
  var level = req.query.level;
  var message = req.query.message;
  if (!tag || !level || !message) {
    res.status(400).end();
  }
  else {
    var stmt = db.prepare("INSERT INTO log(tag, message, level) VALUES (?,?,?)");
    stmt.run(tag, message, level);
    stmt.finalize();
    res.status(204).end();
  }
});

var getDateParam = function(req) {
  // Present and formatted as expected? Should be formatted as "1990-10-10+10%3A02%3A45"
  if (req.query.date && /^\d{4}-\d{2}-\d{2}[+]\d{2}(%3A)\d{2}(%3A)\d{2}/.test(req.query.date)) {
    return req.query.date.replace(/[+]/, "T").replace(/(%3A)/g, ":");
  }
  return "1970-01-01T00:00:00";
};

var getDateSortParam = function(req) {
  switch(req.query.dtsort) {
    case "OLDER":
      return "<=";
    case "EXACT":
      return "=";
    default:
      return ">=";
  }
};

app.get("/entries", function(req, res) {
  // Lots of potential parameters
  var date = getDateParam(req);
  var dtsort = getDateSortParam(req);
  var tag = "";
  if (req.query.tag && /^[\dA-Za-z]+$/.test(req.query.tag)) {
    tag = req.query.tag;
  }
  console.log(tag);
  // This is a terrible way to insert the operator, but should be fine since we use the constants from the parsing function, and not the user input directly
  //var query = "SELECT * FROM log WHERE date dtsort (?date) AND tag REGEXP \'?tag\' LIMIT 100;";
  var query = "SELECT * FROM log WHERE tag LIKE \'%$tag%\' LIMIT 100;";
  query = query.replace(/dtsort/, dtsort).replace(/\$tag/, tag);

  db.all(query, {}, function(err, rows) {
    console.log(err);
    res.json(rows);
  });

});

app.use(express.static('static'));

db.serialize(function() {
  app.listen(8090);
});
