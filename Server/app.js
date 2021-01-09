const MongoClient = require('mongodb').MongoClient;
const url = 'mongodb://localhost:27017';
const assert = require('assert');
const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const http = require('http');
const nodemailer = require('nodemailer');
const request = require('request');
var qs = require('querystring');
const app = express();
app.use(express.json());

var corsOptions = {
  origin: 'http://localhost:4200',
  methods: "GET,HEAD,PUT,PATCH,POST,DELETE",
  credentials: true,
  optionsSuccessStatus: 200
}

var transporter = nodemailer.createTransport({
  service: 'mailgun',
  auth: {
    user: 'postmaster@sandbox73a5a79852d441ab8f58f0639ffcd4fe.mailgun.org',
    pass: '5f9e518c6a75e178c0c350a92ce5be78-46ac6b00-a94340df'
  }
});

app.use(express.urlencoded({
  extended: true
}));
app.options('*', cors());
app.listen(8888);

const db = 'MySchool';

// Use connect method to connect to the server
MongoClient.connect(url, {
  useNewUrlParser: true,
  useUnifiedTopology: true
}, (err, client) => {
  let db = client.db("MySchool");
  console.log("MySchool server is listening on port 8888 ... ");

  app.get("/test", cors(corsOptions), (req, res) => {
    console.log("request done on /test");
    res.end(JSON.stringify("ok"));
  });

  app.post("/login", cors(corsOptions), (req, res) => {
    console.log("request done on /login");

    const query = {
      p_email: req.body.email,
      p_password: req.body.password
    }
    console.log("query -> login : " + query.email + " pass : " + query.password);
    db.collection("users").find(query).toArray((err, documents) => {
      if (documents.length > 0) {
        console.log("documents = " + JSON.stringify(documents[0]));
        const objToSend = {
          name: documents[0].p_firstname,
          email: documents[0].p_email,
          type: documents[0].p_acc_type
        }
        console.log("res send = status 200 " + JSON.stringify(objToSend));
        res.status(200).send(JSON.stringify(objToSend));
      } else {
        console.log("res send = status 400 ");
        res.status(404).send();
      }
    })

  });


  app.post("/getMemberByEmail", cors(corsOptions), (req, res) => {
    let email = req.body.email;
    console.log("request done on /getMemberByEmail with " + email);

    db.collection("users").find({
      "p_email": email
    }).toArray((err, documents) => {
      if (documents.length > 0) {
        //  console.log('found : ' + JSON.stringify(documents[0]));
        res.status(200).send(JSON.stringify(documents[0]));
      } else {
        console.log("res send = status 400 ");
        res.status(404).send();
      }
    })
  });

  app.post("/getCourseByGradeAndUE", cors(corsOptions), (req, res) => {
    let grade = req.body.grade;
    let ue = req.body.UE;

    console.log("request done on /getUEByGradeAndUE with " + grade + " " + ue);
    let toSend = {};
    db.collection("grades").find({
      "Grade": grade
    }).toArray((err, documents) => {
      console.log(JSON.stringify(documents[0]));
      for (var i = 0; i < documents[0].UE.length; i++) {
        if (documents[0].UE[i].label.localeCompare(ue) == 0) {
          console.log("SEND : " + JSON.stringify(documents[0].UE[i]));
          toSend = documents[0].UE[i];
        }
      }
      res.status(200).send(JSON.stringify(toSend));
    });
  });

  app.post("/getQuizzByGradeAndUE", cors(corsOptions), (req, res) => {
    let grade = req.body.grade;
    let ue = req.body.UE;
    let toSend = [];

    console.log("request done on /getQuizzByGradeAndUE with " + grade + " " + ue);
    db.collection("grades").find({
      "Grade": grade
    }).toArray((err, documents) => {
      for (var i = 0; i < documents[0].UE.length; i++) {
        if (documents[0].UE[i].label.localeCompare(ue) == 0) {
          console.log("QCM : " + JSON.stringify(documents[0].UE[i].Quizzs));
          for (var j = 0; j < documents[0].UE[i].Quizzs.length; j++) {
            toSend.push(documents[0].UE[i].Quizzs[j].Quizz);
          }
        }
      }
      res.status(200).send(JSON.stringify(toSend));
    })
  });

  app.post("/addLog", cors(corsOptions), (req, res) => {
    let user = req.body.email;
    let date = req.body.date;
    let type = req.body.type;
    let marks = req.body.marks;

    console.log("request done on addLog with " + user + ", " + date + ", " + marks);

    let logs = [];
    let log = {
      "date": date,
      "type": type,
      "marks": marks
    };

    let toSend = {
      "user": user,
      "logs": []
    };
    db.collection("logs").find({
      "user": user
    }).toArray((err, documents) => {
      if (documents.length <= 0) {
        toSend.logs.push(log);
        db.collection("logs").insertOne(toSend);
        res.status(200).send("ok");
      } else {
        logs = documents[0].logs;
        logs.push(log);
        toSend.logs = logs;
        db.collection("logs").update({
          "user": user
        }, toSend);
        res.status(200).send("ok");
      }
    })
  });

  app.post("/getLogs", cors(corsOptions), (req, res) => {
    let user = req.body.email;
    let toSend = [];

    console.log("Request done on /getLogs with " + user);
    db.collection("logs").find({
      "user": user
    }).toArray((err, documents) => {
      for (var i = 0; i < documents[0].logs.length; i++) {
        toSend.push(documents[0].logs[i]);
      }
      console.log("send :" + JSON.stringify(toSend));
      res.status(200).send(JSON.stringify(toSend));
    })
  });

  app.post("/getAdvices", cors(corsOptions), (req, res) => {
    let user = req.body.email;
    let toSend = [];

    console.log("Request done on /getAdvices with " + user);

    db.collection("advices").find({
      "user": user
    }).toArray((err, documents) => {
      for (var i = 0; i < documents[0].advices.length; i++) {
        toSend.push(documents[0].advices[i]);
      }
      console.log("send : " + JSON.stringify(toSend));
      res.status(200).send(JSON.stringify(toSend));
    })
  });

  app.post("/register", cors(corsOptions), (req, res) => {
    console.log("request done on /register");
    console.log("received : " + JSON.stringify(req.body));
    console.log("type : " + req.body.type);
    if (req.body.type == 1) {
      console.log("HERE 1: ")

      let toAdd = {
        p_acc_type: req.body.type,
        p_firstname: req.body.firstname,
        p_lastname: req.body.lastname,
        p_email: req.body.email,
        p_password: req.body.password,
        p_formula2: req.body.formule,
        p_students: req.body.students
      };
      console.log("add to users : " + JSON.stringify(toAdd));
      db.collection("users").insertOne(toAdd);
      res.status(200).send("ok");
    } else {
      console.log("HERE 2: ")

      let toAdd = {
        p_acc_type: req.body.type,
        p_firstname: req.body.firstname,
        p_lastname: req.body.lastname,
        p_email: req.body.email,
        p_password: req.body.password,
        p_formula1: req.body.formula1,
        p_grade: req.body.grade
      }
      console.log("add to users : " + JSON.stringify(toAdd));
      db.collection("users").insertOne(toAdd);
      res.status(200).send("ok");

    }
  });

  app.post("/forgotpass", cors(corsOptions), (req, res) => {
    console.log("request done on /forgotpass with " + req.body.email);
    db.collection("users").find({
      "p_email": req.body.email
    }).toArray((err, documents) => {
      if (documents.length > 0) {
        console.log("user found ");
        var mailOptions = {
          from: 'noreply@myschool.com',
          to: req.body.email,
          subject: '[MySchool] Your password',
          text: 'Hi, you asked for a new password, here is it :' + documents[0].p_password + ". "
        };
        console.log("password sent : " + documents[0].p_password);
        transporter.sendMail(mailOptions, function(error, info) {
          if (error) {
            res.status(404).send();
          } else {
            res.status(200).send(JSON.stringify("message sent to " + req.body.email));
          }
        });
      }
    });
  });

  app.get("/grades", cors(corsOptions), (req, res) => {
    console.log("request done on /grades with " + req.body.email);
    db.collection("grades").find().toArray((err, documents) => {
      res.status(200).send(JSON.stringify(documents));
    })
  });
});