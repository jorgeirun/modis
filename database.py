import psycopg2
from psycopg2 import Error
from psycopg2.extensions import ISOLATION_LEVEL_AUTOCOMMIT

def checkForDB():
  # Create DB
  create_db_query = "CREATE DATABASE modis;"
  try:
    dbConnection = psycopg2.connect(user = "postgres", password = "postgres", host = "127.0.0.1", port = "5432")
    dbConnection.set_isolation_level(ISOLATION_LEVEL_AUTOCOMMIT)
    cursor = dbConnection.cursor()
    cursor.execute(create_db_query)
    print("DB modis created successfully.")
  except (Exception, psycopg2.DatabaseError) as error :
    print ("Message:", error)

def createHeaderTable():
  # Create header table for pixel details
  try:
    tableConnection = psycopg2.connect(user = "postgres", password = "postgres", host = "127.0.0.1", port = "5432", dbname = "modis")
    tableConnection.set_isolation_level(ISOLATION_LEVEL_AUTOCOMMIT)
    cursor = tableConnection.cursor()
    create_table_query = '''
    	CREATE TABLE modis_data_pixels
            (ID                	BIGSERIAL     PRIMARY KEY     NOT NULL,
            FILENAME           	TEXT    NOT NULL,
            LATITUDE           	TEXT    NOT NULL,
            LONGITUDE           TEXT    NOT NULL); 
            '''
    cursor.execute(create_table_query)
    tableConnection.commit()
    print("Table modis_data_pixels created successfully.")
  except (Exception, psycopg2.DatabaseError) as error :
      print ("Message:", error)

def createDetailsTable():
	# Create header table for pixel details
  try:
    tableConnection = psycopg2.connect(user = "postgres", password = "postgres", host = "127.0.0.1", port = "5432", dbname = "modis")
    tableConnection.set_isolation_level(ISOLATION_LEVEL_AUTOCOMMIT)
    cursor = tableConnection.cursor()
    create_table_query = '''
    	CREATE TABLE modis_data_pixel_details
            (ID                		BIGSERIAL     PRIMARY KEY     NOT NULL,
            PIXEL_ID 				TEXT    NOT NULL,
            PIXELVALUEDATE			TEXT    NOT NULL,
            PIXELVALUE          	TEXT    NOT NULL, 
            PIXELBINARYVALUE        TEXT    NOT NULL, 
            CMFLAG        			TEXT    NOT NULL,
            CMCONFVALUE        			TEXT    NOT NULL); 
            '''
    cursor.execute(create_table_query)
    tableConnection.commit()
    print("Table modis_data_pixel_details created successfully.")
  except (Exception, psycopg2.DatabaseError) as error :
      print ("Message:", error)
