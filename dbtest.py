import psycopg2
from psycopg2 import Error

# Server
user = "postgres"
password = "modis"
dbname = "modis"

connection = psycopg2.connect(user = user, password = password, host = "127.0.0.1", port = "5432", dbname = dbname)
query =  "SELECT * from modis_data_py_clouds LIMIT 10"
cursor = connection.cursor()
cursor.execute(query)
print(cursor.fetchall());
connection.commit()