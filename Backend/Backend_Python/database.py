from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from dotenv import load_dotenv
from sqlalchemy.exc import SQLAlchemyError
import os

load_dotenv()

DATABASE_URL = os.getenv("DB_URL_DOCKER")
DATABASE_URL = "postgresql://postgres:Blacks27@localhost:5432/postgres"

if not DATABASE_URL:
    raise ValueError("Database URL not set!")

try:
    engine = create_engine(DATABASE_URL)
    SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
except SQLAlchemyError as e:
    raise RuntimeError(f"Error connecting to the database: {str(e)}")

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()