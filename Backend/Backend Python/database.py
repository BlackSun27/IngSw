from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from dotenv import load_dotenv
from sqlalchemy.exc import SQLAlchemyError
import os

def setup_database():
    load_dotenv()
    user = os.getenv("PG_USER")
    password = os.getenv("PG_PASSWORD")

    if not user or not password:
        raise ValueError("Database credentials are not set in the environment variables.")

    DATABASE_URL = f"postgresql://{user}:{password}@localhost:5432/Progetto"

    try:
        engine = create_engine(DATABASE_URL)
        session_local = sessionmaker(autocommit=False, autoflush=False, bind=engine)

        def get_db():
            db = session_local()
            try:
                yield db
                print("Connessione")
            finally:
                db.close()

        return get_db, engine
    except SQLAlchemyError as e:
        raise RuntimeError(f"Error connecting to the database: {str(e)}")
