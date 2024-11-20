from sqlalchemy.orm import Session
from dao.ProgettoDAO import ProjectDAO
from typing import List

class ProjectController:
    def __init__(self, db_session: Session):
        self.db = db_session

    def inserisci_progetto(self, cup: str, ref_sci: str, resp: str, nome: str, budget: float):
        self.dao.insert_project(self.db, cup, ref_sci, resp, nome, budget)

    def rimuovi_progetto(self, cup: str):
        self.dao.remove_project(self.db, cup)

    def aggiungi_laboratorio(self, cup: str, nome_lab: str):
        self.dao.add_lab_to_project(self.db, cup, nome_lab)

    def ottieni_impiegati_progetto(self, cup: str) -> List[dict]:
        impiegati = []
        impiegati = self.dao.get_project_employees(cup)
        return impiegati

    def ottieni_laboratori_progetto(self, cup: str) -> List[str]:
        labs = []
        labs = self.dao.get_project_labs(cup)
        return labs