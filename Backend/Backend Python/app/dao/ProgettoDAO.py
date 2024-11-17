from sqlalchemy.orm import Session
from sqlalchemy.sql import text
from typing import List

class ProjectDAO:
    def __init__(self, db: Session):
        self.db = db

    def insert_project(self, cup: str, ref_sci: str, resp: str, nome: str, budget: float):
        query = text("CALL inserisciprogetto(:cup, :ref_sci, :resp, :nome, :budget::NUMERIC);")
        try:
            self.db.execute(query, {"cup": cup, "ref_sci": ref_sci, "resp": resp, "nome": nome, "budget": budget})
            self.db.commit()
        except Exception as e:
            self.db.rollback()
            raise RuntimeError(f"Errore nell'inserimento del progetto: {e}")

    def remove_project(self, cup: str):
        query = text("CALL rimuovi_progetto(:cup);")
        try:
            self.db.execute(query, {"cup": cup})
            self.db.commit()
        except Exception as e:
            self.db.rollback()
            raise RuntimeError(f"Errore nella rimozione del progetto: {e}")

    def add_lab_to_project(self, cup: str, nome_lab: str):
        query = text("CALL inseriscilavora(:cup, :nome_lab);")
        try:
            self.db.execute(query, {"cup": cup, "nome_lab": nome_lab})
            self.db.commit()
        except Exception as e:
            self.db.rollback()
            raise RuntimeError(f"Errore nell'inserimento del laboratorio al progetto: {e}")

    def get_project_employees(self, cup: str) -> List[dict]:
        query = text("SELECT nome, cognome, cf FROM presenza WHERE cup = :cup;")
        try:
            result = self.db.execute(query, {"cup": cup}).fetchall()
            return [{"nome": row[0], "cognome": row[1], "cf": row[2]} for row in result]
        except Exception as e:
            raise RuntimeError(f"Errore durante il recupero dei dipendenti per il progetto {cup}: {e}")

    def get_project_labs(self, cup: str) -> List[str]:
        query = text("SELECT lab1, lab2, lab3 FROM lavora WHERE cup = :cup;")
        try:
            result = self.db.execute(query, {"cup": cup}).fetchone()
            if result:
                return [result[0], result[1], result[2]]
            return []
        except Exception as e:
            raise RuntimeError(f"Errore durante il recupero dei laboratori per il progetto {cup}: {e}")
