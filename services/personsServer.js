var express = require('express');
var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database('database.db');

var app = express();

app.get('/list', function(req, res) {
  var stmt = db.all("SELECT * FROM person;", function(err, rows) {
    res.send(rows);
  });
});

app.get('/find.name', function(req, res) {
  var name = req.query.name;
  if (name) {
    db.get("SELECT * FROM person WHERE name=(?) COLLATE NOCASE", {1: name}, function(err, row) {
      if (row) res.send({key: row.id, name: row.name});
      else {
        console.log("LOOKUP FAILED FOR PERSON \"" + req.query.name + "\"");
        if (err) console.log("FAILED WITH ERROR: " + err);
        res.send('null');
      }
    });
  } else {
    res.send('null');
  }
});

app.get("/find.key", function(req, res) {
  var id = req.query.key;
  if (id) {
    db.get("SELECT * FROM person WHERE id=(?)", {1: id}, function(err, row) {
      console.log(row);
      if (row) res.send({key: row.id, name: row.name});
      else res.send('null');
    });
  }
  else {
    res.send('null');
  }
});

db.serialize(function() {
  app.listen(8060);
});
