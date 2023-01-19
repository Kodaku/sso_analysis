import sys
import mariadb


def connect_to_sso_db():
    # Connect to MariaDB Platform
    try:
        conn = mariadb.connect(
            user="sso",
            password="9r72u0TG",
            host="10.4.1.13",
            port=3306,
            database="sso"

        )
    except mariadb.Error as e:
        print(f"Error connecting to MariaDB Platform: {e}")
        sys.exit(1)

    # Get Cursor
    return conn.cursor()
