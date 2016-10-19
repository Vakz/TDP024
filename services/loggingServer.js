var express = require('express');
var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database('database.db');

var app = express();

app.get("/log", function(req, res) {
	console.log(req.query);
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
  console.log(req.query.date);
  if (req.query.date && /^\d{4}-\d{2}-\d{2}[+]\d{2}(%3A)\d{2}(%3A)\d{2}/.test(req.query.date)) {
    return req.query.date.replace(/[+]/, "T").replace(/(%3A)/g, ":");
  }
  return "1970-01-01T00:00:00";
}

var getDateSortParam = function(req) {
  switch(req.query.dtsort) {
    case "OLDER":
      return "<=";
    case "EXACT":
      return "="
    default:
      return ">=";
  }
}

app.get("/entries", function(req, res) {
  // Lots of potential parameters
  var date = getDateParam(req);
  var dtsort = getDateSortParam(req);

  // This is a terrible way to insert the operator, but should be fine since we use the constants from the parsing function, and not the user input directly
  var query = "SELECT * FROM log WHERE date dtsort (?) ORDER BY id DESC LIMIT 100;";
  query = query.replace(/dtsort/, dtsort);

  db.all(query, {1: date}, function(err, rows) {
    res.json(rows);
  });

});

app.use(express.static('static'));

db.serialize(function() {
  app.listen(8090);
});
