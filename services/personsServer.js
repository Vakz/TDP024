var express = require('express');
var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database(':memory:');

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

  var content = [
    ["1e8a4f8a29989789cbb6726f14934f2f", "Fredrik"],
    ["a9680b66edc99c248b6ad6020d485d16", "Fredric"],
    ["d73f0d6fd1dfaa8c86010fd8afb48b9d", "Eric"],
    ["77f76600dc6b02d307710dd4e7a0e61b", "Emelie"],
    ["f8478ebb3665fa15ae8f69a3f9e752b0", "Jakob Pogulis"],
    ["aa6724ccce7d470d5d96ecaa241fbf7e", "Xena"],
    ["e4f1435c3d891642c86d643a0601ca92", "Marcus Bendtsen"],
    ["eadd47fa78b68c0529d739b2b810c69a", "Zorro"],
    ["7b041153eb1909788e2e5d8486fdc01b", "Q"]
  ];

  db.run("CREATE TABLE person(id varchar(32), name varchar(30))");

  var stmt = db.prepare("INSERT INTO person VALUES (?, ?)");
  for (var i = 0; i < content.length; i++) {
    stmt.run(content[i][0], content[i][1]);
  }
  stmt.finalize();

  app.listen(8060);
});
