var express = require('express');
var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database(':memory:');

var app = express();

app.get('/list', function(req, res) {
  var stmt = db.all("SELECT * FROM bank;", function(err, rows) {
    res.send(rows);
  });
})

app.get('/find.name', function(req, res) {
  var name = req.query.name;
  if (name) {
    db.get("SELECT * FROM bank WHERE name=(?) COLLATE NOCASE", {1: name}, function(err, row) {
      if (row) res.send({key: row.id, name: row.name});
      else {
        console.log("LOOKUP FAILED FOR BANK \"" + req.query.name + "\"");
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
    db.get("SELECT * FROM bank WHERE id=(?)", {1: id}, function(err, row) {
      if (row) res.send({key: row.id, name: row.name});
      else
      {
        res.send('null');
      }
    });
  }
  else {
    res.send('null');
  }
})

db.serialize(function() {
  var content = [
    ["7fe7b9a7b3a9168cfbd1a2af2c58aaa6", "Swedbank"],
    ["6cecd13127d3b6f6cb1e5838f98b45cf", "Handelsbanken"],
    ["d5f440167865dd4ff59bf4203e60d131", "SEB"],
    ["2cc223ccf22194b36219b6223311831a", "Min Spargris"],
    ["3cb936b8e4b3adc13f64bc0bf4b96d04", "SWEDBANK"],
    ["2cc223ccf22194b36219b6223311831a", "IKANOBANKEN"],
    ["38fe3dd0ec4092dd11cd9b8878344cba", "JPMORGAN"],
    ["226656f6f6674dec466c41638c09cffb", "NORDEA"],
    ["2bffb0f1bc8aec1e985e8a42415bcec6", "CITIBANK"],
    ["e659099fc8c554c7ee9c3dbd5c94425d", "HANDELSBANKEN"],
    ["e659099fc8c554c7ee9c3dbd5c94425d", "SBAB"],
    ["e659099fc8c554c7ee9c3dbd5c94425d", "HSBC"],
    ["4e87b6057e549ac5d55e84cf38e7ca3c", "NORDNET"]
  ];

  db.run("CREATE TABLE bank(id varchar(32), name varchar(30))");

  var stmt = db.prepare("INSERT INTO bank VALUES (?, ?)");
  for (var i = 0; i < content.length; i++) {
    stmt.run(content[i][0], content[i][1]);
  }
  stmt.finalize();

  app.listen(8070);
})
