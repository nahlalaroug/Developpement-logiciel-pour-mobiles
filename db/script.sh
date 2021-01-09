#!/bin/bash
mongoimport --db MySchool --collection users --file users.json --jsonArray --drop
mongoimport --db MySchool --collection grades --file grade.json --jsonArray --drop
mongoimport --db MySchool --collection logs --file logs.json --jsonArray --drop
mongoimport --db MySchool --collection advices --file advices.json --jsonArray --drop
