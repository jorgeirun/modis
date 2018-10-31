import psycopg2
from psycopg2 import Error
from psycopg2.extensions import ISOLATION_LEVEL_AUTOCOMMIT

def main(argv):
    print(get_value(*argv[1:]))

if __name__ == "__main__":
    import sys
    main(sys.argv)

def saveData(*args):
	# Insert test data
	connection = psycopg2.connect(user = "postgres", password = "postgres", host = "127.0.0.1", port = "5432", database = "modis")
	query =  "INSERT INTO modis_data (filename, filedate, latitude, longitude, pixel_value, pixel_binary_value, cm_flag, cm_value) VALUES (%s, %s, %s, %s, %s, %s, %s, %s);"
	data = ("A124431", "2017001", "45,233424", "67,324252", "29", "01010011", "01", "11")
	cursor = connection.cursor()
	cursor.execute(query, data)
	connection.commit()
	sys.exit(1)