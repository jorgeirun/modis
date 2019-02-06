import os, fnmatch, time,sys
import subprocess
import psycopg2
from psycopg2 import Error

user = "postgres"
password = "postgres"
dbname = "modis"

connection = psycopg2.connect(user = user, password = password, host = "127.0.0.1", port = "5432", dbname = dbname)
query =  "SELECT * FROM modis_data_py_clouds LIMIT 100;"
cursor = connection.cursor()
cursor.execute(query)
connection.commit()