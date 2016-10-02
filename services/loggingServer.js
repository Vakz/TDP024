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

app.get("/entries", function(req, res) {
  // Lots of potential parameters

  // This is a terrible way to parse dates
  var date = req.query.date.replace(/[+]/, "T").replace(/(%3A)/g, ":");
  console.log(date);
  db.all("SELECT * FROM log WHERE date <= (?) LIMIT 100 ;", {1: date}, function(err, rows) {
    res.json(rows);
  });
});

app.use(express.static('static'));

db.serialize(function() {
  app.listen(8090);
});
