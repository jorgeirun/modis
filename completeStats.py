import os, fnmatch, time,sys
import subprocess
import psycopg2
from psycopg2 import Error
from psycopg2.extras import RealDictCursor

# user = "postgres"
# password = "postgres"
# dbname = "modis"
user = "modis"
password = "modis"
dbname = "modis"

connection = psycopg2.connect(user = user, password = password, host = "127.0.0.1", port = "5432", dbname = dbname)
cursor = connection.cursor()
query = "SELECT * from stats_day"
cursor.execute(query)
total = cursor.fetchall()
print(list(total))