from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from dotenv import load_dotenv
from sqlalchemy.exc import SQLAlchemyError

load_dotenv()

DATABASE_URL = "postgresql://postgres_ingsw52_user:ZxyNuWqn3cmXwVBXbbr1id82MkTCRA4s@dpg-ctdvlk2lqhvc73daigl0-a.frankfurt-postgres.render.com/postgres_ingsw52"

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