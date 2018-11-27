import psycopg2
from psycopg2 import Error
from psycopg2.extensions import ISOLATION_LEVEL_AUTOCOMMIT

# Server
user = "modis"
password = "modis"
dbname = "modis"
# Local
# user = "postgres"
# password = "postgres"
# dbname = "modis"

def checkForDB():
  # Create DB
  create_db_query = "CREATE DATABASE modis;"
  try:
    dbConnection = psycopg2.connect(user = user, password = password, host = "127.0.0.1", port = "5432")
    dbConnection.set_isolation_level(ISOLATION_LEVEL_AUTOCOMMIT)
    cursor = dbConnection.cursor()
    cursor.execute(create_db_query)
    print("DB modis created successfully.")
  except (Exception, psycopg2.DatabaseError) as error :
    print ("Message:", error)

def createModisTable():
  # Create one table for all data
  try:
    tableConnection = psycopg2.connect(user = user, password = password, host = "127.0.0.1", port = "5432", dbname = dbname)
    tableConnection.set_isolation_level(ISOLATION_LEVEL_AUTOCOMMIT)
    cursor = tableConnection.cursor()
    create_table_query = '''
      CREATE TABLE modis_data
            (ID                 BIGSERIAL     PRIMARY KEY     NOT NULL,
            FILENAME            TEXT    NOT NULL,
            LATITUDE            TEXT    NOT NULL,
            LONGITUDE           TEXT    NOT NULL,
            DATEVALUE           TEXT    NOT NULL,
            PIXELVALUE          TEXT    NOT NULL,
            PIXELVALUEBINARY    TEXT    NOT NULL,
            CMFLAG              TEXT    NOT NULL,
            CMCONFVALUE         TEXT    NOT NULL,
            GEOM                GEOMETRY  NULL
            );
            '''
    cursor.execute(create_table_query)
    tableConnection.commit()
    print("Table modis_data created successfully.")
  except (Exception, psycopg2.DatabaseError) as error :
      print ("Message:", error)

# TODO: create 01 to 12 tables = per month